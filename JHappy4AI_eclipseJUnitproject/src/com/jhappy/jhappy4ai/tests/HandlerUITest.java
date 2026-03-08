package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import org.osgi.framework.Bundle;

import com.jhappy.jhappy4ai.aiexporter.handlers.Handler;

@DisplayName("UI Test: Command Execution")
class HandlerUITest extends SimpleJDTBuildTest {

	public static String SAMPLENONASCIIUTF8 = "src/com/test/samplenonasciiutf8.properties";
	
	//
	public static String SAMPLENONASCIIShiftJIShtml = "src/com/test/sampleshiftJIS.html";
	
	//xmlの場合はxml内部encodeing宣言でEclipseが自動で文字コードを判定しているので
	//個別に設定は不要みたい
	public static String SAMPLENONASCIIShiftJISxml = "src/com/test/sampleshiftJIS.xml";
	


	@Test
	public void execTest() throws Exception {

		com.jhappy.jhappy4ai.tests.util.TestResourceUtility.copyBundleResources(
				BUNDLE_NAME,
				TEST_RESOURCES_FOLDER+"/samplefiles",
				project);

		//
		IFile targetJavaFile = project.getFile(SAMPLENONASCIIUTF8);
		assertTrue(targetJavaFile.exists(), "コピーされたファイルが存在すること");

		//propertiesはEclipseでデフォルトISO-8859-1の文字コードとして
		//認識されるので、テスト用にUTF-8で記述されたpropereisファイルを
		//手動でUTF-8とファイルに設定し、正しく読み込まれるか確認
		IFile utf8propertiesFile = project.getFile(new Path(SAMPLENONASCIIUTF8));
		System.out.println("default charaset utf8propertiesFile : " + utf8propertiesFile.getCharset());
		utf8propertiesFile.setCharset("UTF-8", null);

		//明示的にShift_JISと指定
		IFile shiftJisHtmlFile = project.getFile(new Path(SAMPLENONASCIIShiftJIShtml));
		System.out.println("default charaset shiftJisHtmlFile : " + shiftJisHtmlFile.getCharset());
		shiftJisHtmlFile.setCharset("Shift_JIS", null);

		// 
		Display.getDefault().syncExec(() -> {

			//
			try {

				//
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();

				//
				IViewPart jview = page.showView("org.eclipse.jdt.ui.PackageExplorer");
				page.activate(jview);

				//
				StructuredSelection selection = new StructuredSelection(samplejavafile);
				jview.getSite().getSelectionProvider().setSelection(selection);

				// 
				if (jview instanceof ISetSelectionTarget) {
					((ISetSelectionTarget) jview).selectReveal(selection);
				} else {
					jview.getSite().getSelectionProvider().setSelection(selection);
				}

				//
				while (Display.getDefault().readAndDispatch());

				// 
				StructuredSelection selection2 = new StructuredSelection(project);

				//
				if (jview instanceof ISetSelectionTarget) {
					((ISetSelectionTarget) jview).selectReveal(selection2);
				} else {
					jview.getSite().getSelectionProvider().setSelection(selection2);
				}

				// 
				while (Display.getDefault().readAndDispatch());


				System.out.println("コマンド実行を開始します...");

				// ファイルの出力先を指定
				String outputTestFileName = "test_ui_output_" + System.currentTimeMillis() + ".txt";
				IPath outputPath = project.getLocation().append(outputTestFileName);
				
				// ファイルの出力のためテストモードに設定
				Handler.isTestMode = true;
				Handler.testSavePath = outputPath.toOSString();

				//テストモードでコマンドを起動（各種確認ダイアログなどをスキップして一気に出力）
				IHandlerService handlerService = window.getService(IHandlerService.class);
				handlerService.executeCommand("com.jhappy.jhappy4ai.copyCommand", null);
				
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
				
				while (Display.getDefault().readAndDispatch());
				
				Thread.sleep(1000);

				//出力されたか確認
				File generatedFile = outputPath.toFile();
				assertTrue(generatedFile.exists(), "物理ファイルが正しく生成されていること");
				//
				IFile generatedIFile = project.getFile(outputTestFileName);
				assertTrue(generatedIFile.exists(), "Eclipse上でファイルが認識されていること");

				StructuredSelection pselection2 = new StructuredSelection(generatedIFile);
				jview.getSite().getSelectionProvider().setSelection(pselection2);

				// 出力されたファイルを選択してツリーを展開
				if (jview instanceof ISetSelectionTarget) {
					((ISetSelectionTarget) jview).selectReveal(pselection2);
				} else {
					jview.getSite().getSelectionProvider().setSelection(pselection2);
				}

				// 出力されたファイルをエディタで開く
				IDE.openEditor(page, generatedIFile);
				while (Display.getDefault().readAndDispatch());
				System.out.println("エディタを開きました！");

				// --- コンソールへの出力処理を追加 ---
				try (InputStream is = generatedIFile.getContents()) {
					String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
					System.out.println("========== Generated File Content ==========");
					System.out.println(content);
					System.out.println("============================================");
				} catch (Exception e) {
					System.err.println("ファイルの読み込み中にエラーが発生しました: " + e.getMessage());
					fail("UI Test failed : failes load outputfile : " + outputTestFileName+" : "+ e.getMessage());
				}
				
				
				String exportedFileContent = readFileContent(generatedIFile, "UTF-8");
				
				Bundle bundle = Platform.getBundle(BUNDLE_NAME);
				URL expectedFileUrl = FileLocator.find(bundle, new Path(TEST_RESOURCES_FOLDER+"/resulttextforchecking.txt"), null);
				
				if (expectedFileUrl == null) {
					System.err.println("--- BUNDLE CONTENT DEBUG ---");
					Enumeration<URL> entries = bundle.findEntries("/", "*", true);
					while (entries != null && entries.hasMoreElements()) {
						System.err.println("Found entry: " + entries.nextElement().getPath());
					}
					fail("チェック用ファイルが見つかりません: " + "\n上記ログでパスを確認してください。");
				}
				
				
				assertNotNull(expectedFileUrl, "チェック用ファイルがありません");
				
				String expectedContent;
				try (InputStream is = expectedFileUrl.openStream()) {
					expectedContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				}
				
				//文字化けしてないか確認
				assertEquals(exportedFileContent, expectedContent, "生成された内容が期待値ファイルの内容と一致すること");

				System.out.println("検証成功: 出力内容はプラグイン内の期待値データと完全に一致しました。");

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
				//テスト完了後モードをもとに戻す
				Handler.isTestMode = false;
				Handler.testSavePath = null;
			}
			
		});

	}
	
	private String readFileContent(IFile file, String charsetName) throws Exception {
		try (InputStream is = file.getContents()) {
			return new String(is.readAllBytes(), charsetName);
		}
	}
}