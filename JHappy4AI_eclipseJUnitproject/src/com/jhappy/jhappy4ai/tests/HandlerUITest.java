package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jhappy.jhappy4ai.aiexporter.handlers.Handler;

@DisplayName("UI Test: Command Execution")
public class HandlerUITest extends ExportIntegrationTest { // 前回のJavaプロジェクト作成機能を継承
//
//	@Test
//    void testMenuCommandExecution() throws Exception {
//        // 1. テスト用ファイルを作成（src/UITest.java）
//        var file = createFile("src/UITest.java", "public class UITest {}");
//
//        Display.getDefault().syncExec(() -> {
//            try {
//                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//                IWorkbenchPage page = window.getActivePage();
//
//                // 2. Package Explorer を強制的に表示してアクティブにする
//                IViewPart view = page.showView("org.eclipse.jdt.ui.PackageExplorer");
//                page.activate(view);
//
//                // 3. ビューのセレクションプロバイダーに対して、選択状態をセットする
//                // これをやらないと、Handler側で HandlerUtil.getCurrentSelection() が空になります
//                var selection = new StructuredSelection(file);
//                view.getSite().getSelectionProvider().setSelection(selection);
//
//                // 4. 少しだけ待機（UIの更新を待つ）
//                while (Display.getDefault().readAndDispatch());
//
//                System.out.println("コマンド実行を開始します: com.jhappy.jhappy4ai.copyCommand");
//
//                // 5. コマンド実行
//                IHandlerService handlerService = window.getService(IHandlerService.class);
//                // 第2引数のエントリ（Event）をnullにすると、現在のUIコンテキストが自動で使われます
//                handlerService.executeCommand("com.jhappy.jhappy4ai.copyCommand", null);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                fail("Command execution failed: " + e.getMessage());
//            }
//        });
//
//        // 6. ダイアログが表示されていることを確認するために長めに待機
//        Thread.sleep(5000); 
//    }
	
	@Test
	public void testMenuCommandExecution() throws Exception {
   
        
      

        Display.getDefault().syncExec(() -> {
            try {
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                IWorkbenchPage page = window.getActivePage();

                // 2. 「プロジェクト・エクスプローラー」を表示・アクティブ化
                IViewPart pview = page.showView("org.eclipse.ui.navigator.ProjectExplorer");
                page.activate(pview);
                // 3. 選択状態をセット
                StructuredSelection pselection = new StructuredSelection(file1);
                pview.getSite().getSelectionProvider().setSelection(pselection);
                
             // ★ 単に選択するだけでなく、ツリーを展開して該当ファイルにスクロールさせる魔法の命令
                if (pview instanceof ISetSelectionTarget) {
                    ((ISetSelectionTarget) pview).selectReveal(pselection);
                } else {
                	pview.getSite().getSelectionProvider().setSelection(pselection);
                }
                
                
                
                
                
                IViewPart jview = page.showView("org.eclipse.jdt.ui.PackageExplorer");
                page.activate(jview);

                // 3. 選択状態をセット
                StructuredSelection selection = new StructuredSelection(file1);
                jview.getSite().getSelectionProvider().setSelection(selection);
                
             // ★ 単に選択するだけでなく、ツリーを展開して該当ファイルにスクロールさせる魔法の命令
                if (jview instanceof ISetSelectionTarget) {
                    ((ISetSelectionTarget) jview).selectReveal(selection);
                } else {
                	jview.getSite().getSelectionProvider().setSelection(selection);
                }
                
                // ツリーが展開されるアニメーションを処理させるためにUIを回す
                while (Display.getDefault().readAndDispatch());
                
                Thread.sleep(1000);
                
                StructuredSelection selection2 = new StructuredSelection(project);
             // 単なる setSelection ではなく、プロジェクトに対しても Reveal を使って確実にフォーカスを戻す
                if (jview instanceof ISetSelectionTarget) {
                    ((ISetSelectionTarget) jview).selectReveal(selection2);
                } else {
                	jview.getSite().getSelectionProvider().setSelection(selection2);
                }

                // ★ 超重要：プロジェクトが選択されたという「情報」がEclipse全体に行き渡るまで待つ！
                while (Display.getDefault().readAndDispatch());

                // プロジェクトに選択が戻ったのを人間が目視確認するための待機
                Thread.sleep(500);
                
                System.out.println("コマンド実行を開始します...");
                
                Handler.isTestMode = true;
                
                String outputTestFileName = "test_ui_output_"+System.currentTimeMillis()+".txt";
                
                IPath outputPath = project.getLocation().append(outputTestFileName);
                Handler.testSavePath = outputPath.toOSString();

                // 5. コマンド実行
                IHandlerService handlerService = window.getService(IHandlerService.class);
                handlerService.executeCommand("com.jhappy.jhappy4ai.copyCommand", null);
                
                java.io.File generatedFile = outputPath.toFile();
                assertTrue(generatedFile.exists(), "物理ファイルが正しく生成されていること");
                
                // 6. Eclipseにファイルが出来たことを認識させる（リフレッシュ）
                project.refreshLocal(IResource.DEPTH_INFINITE, null);

                // 7. プロジェクト直下のファイルを Eclipseの形式（IFile）で取得
                IFile generatedIFile = project.getFile(outputTestFileName);
                assertTrue(generatedIFile.exists(), "Eclipse上でファイルが認識されていること");
                
                
                StructuredSelection pselection2 = new StructuredSelection(generatedIFile);
                pview.getSite().getSelectionProvider().setSelection(pselection2);
                
             // ★ 単に選択するだけでなく、ツリーを展開して該当ファイルにスクロールさせる魔法の命令
                if (pview instanceof ISetSelectionTarget) {
                    ((ISetSelectionTarget) pview).selectReveal(pselection2);
                } else {
                	pview.getSite().getSelectionProvider().setSelection(pselection2);
                }
                

                // 8. Eclipse標準のテキストエディタでファイルを開く！
                IDE.openEditor(page, generatedIFile);
             
                System.out.println("エディタを開きました！6秒後にテストを終了して片付けます...");

                //to check by human
                long endTime = System.currentTimeMillis() + 1000;
                Display display = Display.getDefault();
                while (System.currentTimeMillis() < endTime) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                fail("UI Test failed: " + e.getMessage());
            } finally {
                // ★ テストが終わったら必ずフラグを元に戻す
                Handler.isTestMode = false;
                Handler.testSavePath = null;
            }
        });
        
        // ※ syncExec の外にあった Thread.sleep は削除しました
    }
}