package com.jhappy.jhappy4ai.aiexporter.handlers;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

import com.jhappy.jhappy4ai.Activator;

public class ExportConfigDialog extends TitleAreaDialog {
    private String allowed, excluded, ignoredFolders, maxChars;


    public ExportConfigDialog(Shell parentShell, String allowed, String excluded, String folders, String maxChars) {
        super(parentShell);
        this.allowed = allowed;
        this.excluded = excluded;
        this.ignoredFolders = folders;
        this.maxChars = maxChars;
     // ★ 標準のヘルプボタンを非表示にする
        setHelpAvailable(false);
    }

    @Override
    public void create() {
        super.create();
        setTitle(Messages.Handler_DialogTitle);
        setMessage(Messages.Handler_DialogMsg);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        container.setLayout(new GridLayout(2, false));

        createLabelText(container, Messages.PrefPage_AllowedExtensions, allowed, t -> allowed = t);
        createLabelText(container,  Messages.PrefPage_IgnoredExtensions, excluded, t -> excluded = t);
        createLabelText(container,  Messages.PrefPage_IgnoredFolders, ignoredFolders, t -> ignoredFolders = t);
        createLabelText(container, Messages.PrefPage_MaxChars, maxChars, t -> maxChars = t);
        createHelpLink(container);

        return area;
    }
    
    private void createHelpLink(Composite container) {
        new Label(container, SWT.NONE); 
        Link link = new Link(container, SWT.NONE);
        link.setText("<a>" + Messages.Handler_HowToUse + "</a>");
        link.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        
        link.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            @Override
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                // Eclipse内部のヘルプシステムを取得
                IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
                
                // 特定のリソースを表示
                // 引数は "/プラグインID/パス" の形式
                helpSystem.displayHelpResource("/" + Activator.PLUGIN_ID + "/html/main.html");
            }
        });
    }

    private void createLabelText(Composite container, String label, String value, java.util.function.Consumer<String> setter) {
        new Label(container, SWT.NONE).setText(label);
        Text text = new Text(container, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        text.setText(value != null ? value : "");
        text.addModifyListener(e -> setter.accept(text.getText()));
    }

    // 各値を取得するためのgetter
    public String getAllowed() { return allowed; }
    public String getExcluded() { return excluded; }
    public String getFolders() { return ignoredFolders; }
    public String getMaxChars() { return maxChars; }
}