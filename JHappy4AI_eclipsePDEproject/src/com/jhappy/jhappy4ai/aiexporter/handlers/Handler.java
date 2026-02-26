package com.jhappy.jhappy4ai.aiexporter.handlers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.jhappy.jhappy4ai.Activator;
import com.jhappy.jhappy4ai.aiexporter.logic.ExportService;

public class Handler extends AbstractHandler {

    public static final String PREF_ALLOWED_EXTENSIONS = "AI_EXPORTER_ALLOWED_EXTENSIONS";
    public static final String PREF_EXCLUDED_EXTENSIONS = "AI_EXPORTER_EXCLUDED_EXTENSIONS";
    public static final String PREF_IGNORED_FOLDERS = "AI_EXPORTER_IGNORED_FOLDERS";
    public static final String PREF_MAX_CHARS = "AI_EXPORTER_MAX_CHARS";
    
 // ★追加: UIテスト用のフラグと変数
    public static boolean isTestMode = false;
    public static String testSavePath = null;
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (!(selection instanceof IStructuredSelection)) return null;

        Shell activeShell = HandlerUtil.getActiveShell(event);
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        // 1. デフォルト設定の読み込み
        String defAllowed = store.getString(PREF_ALLOWED_EXTENSIONS);
        String defExcluded = store.getString(PREF_EXCLUDED_EXTENSIONS);
        String defFolders = store.getString(PREF_IGNORED_FOLDERS);
        String defMaxMB = store.getString(PREF_MAX_CHARS);

        // --- 変数の準備 ---
        String[] allowedExtensions, excludedExts, ignoredFolders;
        long maxSizeBytes;
        String savePath;

        // ★ テストモードの分岐
        if (isTestMode) {
            // テスト時はダイアログを出さず、デフォルト値とテスト用パスを強制適用する
            allowedExtensions = parseCsv(defAllowed);
            excludedExts = parseCsv(defExcluded);
            ignoredFolders = parseCsv(defFolders);
            maxSizeBytes = 10L * 1024L * 1024L; // テスト用は適当に10MB
            savePath = testSavePath; // HandlerUITestから渡されたパス
            
        } else {
            // ========== 通常のフロー（ダイアログを表示） ==========
            // 2. ダイアログ表示
            ExportConfigDialog configDialog = new ExportConfigDialog(activeShell, defAllowed, defExcluded, defFolders, defMaxMB);
            if (configDialog.open() != Window.OK) return null;

            // 3. 設定値の確定
            allowedExtensions = parseCsv(configDialog.getAllowed());
            excludedExts = parseCsv(configDialog.getExcluded());
            ignoredFolders = parseCsv(configDialog.getFolders());

            try {
                long maxMB = Long.parseLong(configDialog.getMaxChars());
                maxSizeBytes = maxMB * 1024L * 1024L;
            } catch (NumberFormatException e) {
                maxSizeBytes = 2L * 1024L * 1024L;
            }

            // 4. 保存先ダイアログ
            savePath = askSavePath(activeShell);
            if (savePath == null) return null;
        }

        // 5. 実行ロジックはそのまま
        List<IFile> targetFiles = new ArrayList<>();
        try {
            IStructuredSelection ss = (IStructuredSelection) selection;
            for (Object element : ss.toList()) {
                IResource res = adaptToResource(element);
                if (res != null) {
                    collectFiles(res, targetFiles, allowedExtensions, excludedExts, ignoredFolders);
                }
            }
            
            ExportService service = new ExportService();
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(savePath), StandardCharsets.UTF_8))) {
                
                ExportService.ExportResult result = service.performExport(targetFiles, writer, maxSizeBytes);
                
                // ★ 完了ダイアログもテスト時はスキップする
                if (!isTestMode) {
                    activeShell.getDisplay().asyncExec(() -> {
                        String pattern = result.limitReached ? Messages.Handler_SuccessMsgLimit : Messages.Handler_SuccessMsg;
                        double sizeInMB = (double) result.totalBytes / (1024 * 1024);
                        String formattedSize = String.format("%.2f MB", sizeInMB);
                        String finalMsg = java.text.MessageFormat.format(pattern, result.fileCount, formattedSize);
                        MessageDialog.openInformation(activeShell, Messages.Handler_SuccessTitle, finalMsg);
                    });
                }
            }
        } catch (Exception e) {
            if (!isTestMode) { // エラーダイアログもスキップ
                showError(activeShell, Messages.Handler_ErrorSaveTitle, e);
            } else {
                e.printStackTrace(); // テスト時はコンソールに出すだけ
            }
        }

        return null;
    }

    private String[] parseCsv(String csv) {
        if (csv == null || csv.trim().isEmpty()) return new String[0];
        return csv.split(",");
    }

    private void showError(Shell shell, String title, Exception e) {
        e.printStackTrace();
        shell.getDisplay().asyncExec(() -> {
            MessageDialog.openError(shell, title, e.getMessage() != null ? e.getMessage() : e.toString());
        });
    }

    private String askSavePath(Shell shell) {
        FileDialog dialog = new FileDialog(shell, SWT.SAVE);
        dialog.setText(Messages.Handler_SaveDialogTitle);
        dialog.setFileName("source_context.txt");
        return dialog.open();
    }

    private IResource adaptToResource(Object element) {
        if (element instanceof IResource) return (IResource) element;
        if (element instanceof IAdaptable) return ((IAdaptable) element).getAdapter(IResource.class);
        return Platform.getAdapterManager().getAdapter(element, IResource.class);
    }

    private void collectFiles(IResource resource, List<IFile> targetFiles, 
            String[] allowed, String[] excluded, String[] ignoredFolders) throws CoreException {

        if (resource instanceof IContainer) {
            String name = resource.getName();
            for (String skip : ignoredFolders) {
                if (!skip.trim().isEmpty() && skip.trim().equalsIgnoreCase(name)) return;
            }
            for (IResource child : ((IContainer) resource).members()) {
                collectFiles(child, targetFiles, allowed, excluded, ignoredFolders);
            }
        } else if (resource instanceof IFile) {
            String fExt = resource.getFileExtension();
            if (fExt == null) return;
            fExt = fExt.trim();

            for (String e : excluded) {
                if (!e.trim().isEmpty() && e.trim().equalsIgnoreCase(fExt)) return;
            }

            boolean hasAllowedSetting = false;
            for (String a : allowed) {
                if (!a.trim().isEmpty()) {
                    hasAllowedSetting = true;
                    break;
                }
            }

            if (!hasAllowedSetting) {
                if (!targetFiles.contains((IFile) resource)) targetFiles.add((IFile) resource);
            } else {
                for (String a : allowed) {
                    if (a.trim().equalsIgnoreCase(fExt)) {
                        if (!targetFiles.contains((IFile) resource)) targetFiles.add((IFile) resource);
                        break;
                    }
                }
            }
        }
    }
}