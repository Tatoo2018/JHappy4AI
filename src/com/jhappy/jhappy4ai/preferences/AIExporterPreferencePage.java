package com.jhappy.jhappy4ai.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

import com.jhappy.jhappy4ai.Activator;
import com.jhappy.jhappy4ai.aiexporter.handlers.Handler;
import com.jhappy.jhappy4ai.aiexporter.handlers.Messages; // Messagesをインポート

public class AIExporterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public AIExporterPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		//
		setDescription(Messages.PrefPage_Description);
	}
	
	// 画像オブジェクトを保持（後で破棄するため）
    private Image headerImage;

	@Override
	public void createFieldEditors() {
	    Composite parent = getFieldEditorParent();
	    
	    
	 // --- 0. 画像の表示 ---
        try {
            // Activator経由で画像をロード（パスはプラグインルートからの相対）
            headerImage = Activator.getImageDescriptor("images/image1.jpg").createImage();
            Label imageLabel = new Label(parent, SWT.NONE);
            imageLabel.setImage(headerImage);
            GridData imageGd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
            imageGd.horizontalSpan = 3;
            imageLabel.setLayoutData(imageGd);
        } catch (Exception e) {
            // 画像がない場合はスキップ
        }
	    
	    Label encodingLabel = new Label(parent, SWT.WRAP);
	    encodingLabel.setText(Messages.PrefPage_EncodingNotice);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    gd.horizontalSpan = 3;
	    encodingLabel.setLayoutData(gd);

	    // --- 1. ファイルフィルタの設定グループ ---
	    Group fileFilterGroup = createGroup(parent, Messages.PrefPage_GroupFilter);
	    
	    
	    
	    // 足し算：許可リスト
	    addField(new StringFieldEditor(
	            Handler.PREF_ALLOWED_EXTENSIONS,
	            Messages.PrefPage_AllowedExtensions,
	            fileFilterGroup));

	    // 引き算：無視（除外）リスト
	    addField(new StringFieldEditor(
	            Handler.PREF_EXCLUDED_EXTENSIONS, 
	            Messages.PrefPage_IgnoredExtensions,
	            fileFilterGroup));
	    
	    // --- 2. フォルダフィルタの設定グループ ---
	    Group folderFilterGroup = createGroup(parent, Messages.PrefPage_GroupFolder);

	    // 無視フォルダ
	    addField(new StringFieldEditor(
	            Handler.PREF_IGNORED_FOLDERS, 
	            Messages.PrefPage_IgnoredFolders,
	            folderFilterGroup));

	    // --- 3. 出力制限の設定グループ ---
	    Group outputGroup = createGroup(parent, Messages.PrefPage_GroupOutput);
	    
	    createHelpLink(parent);

	    IntegerFieldEditor maxCharsEditor = new IntegerFieldEditor(
	            Handler.PREF_MAX_CHARS,
	            Messages.PrefPage_MaxChars,
	            outputGroup);
	    maxCharsEditor.setValidRange(0, 2047);
	    addField(maxCharsEditor);

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

	private Group createGroup(Composite parent, String label) {
	    Group group = new Group(parent, SWT.NONE);
	    group.setText(label);
	    
	    // グループ自体を横いっぱいに広げる
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    gd.horizontalSpan = 3; 
	    group.setLayoutData(gd);
	    
	    // FieldEditorは内部でGridLayout(3列)を想定することが多いため、3に設定
	    group.setLayout(new GridLayout(3, false));
	    return group;
	}

	@Override
	public void init(IWorkbench workbench) {
	}
	
	@Override
    public void dispose() {
        // 重要：作成したImageオブジェクトは明示的に破棄が必要
        if (headerImage != null && !headerImage.isDisposed()) {
            headerImage.dispose();
        }
        super.dispose();
    }

	@Override
	protected Control createContents(Composite parent) {

		//
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "JHappy4AI.pref_page_context");
		return super.createContents(parent);
	}
}