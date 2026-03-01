package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jhappy.jhappy4ai.aiexporter.logic.ExportService;



@DisplayName("Integration Test: Real Java Project Operations")
 class ExportIntegrationTest {

    public IProject project;
    private IJavaProject javaProject;
    private ExportService service;
    
    public IFile file1 = null;
    public IFile file2 =null;

    @BeforeEach
     void setup() throws CoreException {
        service = new ExportService();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        String projectName = "TestJavaProject_" + System.currentTimeMillis();
        project = root.getProject(projectName);
        project.create(null);
        project.open(null);

        // --- Javaプロジェクトとしての設定を追加 ---
        IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID });
        project.setDescription(description, null);

        javaProject = JavaCore.create(project);

        // クラスパス（srcフォルダとJRE）の設定
        IFolder srcFolder = project.getFolder("src");
        srcFolder.create(true, true, null);

        List<IClasspathEntry> entries = new ArrayList<>();
        entries.add(JavaCore.newSourceEntry(srcFolder.getFullPath()));
     // 1. JREコンテナを「デフォルトパス」から明示的に取得する
        org.eclipse.core.runtime.IPath jrePath = JavaRuntime.newDefaultJREContainerPath();
        entries.add(JavaCore.newContainerEntry(jrePath));
        
        IFolder binFolder = project.getFolder("bin");
        binFolder.create(true, true, null);
        javaProject.setOutputLocation(binFolder.getFullPath(), null);

        
        javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[0]), null);
        
         file1 = createFile("src/Sample.java", "public class Sample {}"); 
         file2 = createFile("Config.properties", "key=value");
         
         createMassiveDummyFiles(50);
      // 3. プロジェクトをフルビルドして、本物の .class ファイルを bin フォルダに作らせる
         System.out.println("親クラス(setup): Eclipseコンパイラでビルドを実行中...");
         project.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
         System.out.println("親クラス(setup): ビルド完了！テストを開始します。");

        
    }

    @AfterEach
     void tearDown() throws CoreException {
        //
        if (project.exists()) { project.delete(true, true, null); } 
    }

    @Test
     void testExportRealJavaFiles() throws Exception {
        // 2. srcフォルダの中にJavaファイルを作成
      
        // 3. 結合処理の実行
        StringWriter out = new StringWriter();
        try (BufferedWriter writer = new BufferedWriter(out)) {
            service.performExport(List.of(file1, file2), writer, 0);
            writer.flush();
        }

        // 4. 検証
        String result = out.toString();
        assertAll("Verify combined output",
            () -> assertTrue(result.contains("Sample.java")),
            () -> assertTrue(result.contains("public class Sample {}"))
        );

        // --- 30秒間待機（UIイベントを処理し続ける） ---
        int waitSeconds = 3;
        System.out.println("Javaプロジェクト作成完了。目視確認してください...");
        long endTime = System.currentTimeMillis() + (waitSeconds * 1000);
        while (System.currentTimeMillis() < endTime) {
            if (!Display.getDefault().readAndDispatch()) {
                Thread.sleep(100);
            }
        }
    }

     IFile createFile(String path, String content) throws CoreException {
        IFile file = project.getFile(path);
        InputStream source = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        if (!file.exists()) {
            file.create(source, IResource.NONE, new NullProgressMonitor());
        }
        return file;
    }
    
    
    
    /**
     * パス文字列（例: "src/main/java/com/test"）から深い階層のフォルダを一気に作成する
     */
     IFolder createFolderRecursively(String folderPath) throws CoreException {
        String[] segments = folderPath.split("/");
        IFolder currentFolder = null;
        for (String segment : segments) {
            if (currentFolder == null) {
                currentFolder = project.getFolder(segment);
            } else {
                currentFolder = currentFolder.getFolder(segment);
            }
            if (!currentFolder.exists()) {
                currentFolder.create(true, true, new NullProgressMonitor());
            }
        }
        return currentFolder;
    }

    /**
     * パス文字列（例: "src/main/java/com/test"）から深い階層のフォルダを一気に作成する
     */
     IFolder createFolderRecursively2(String folderPath) throws CoreException {
        String[] segments = folderPath.split("/");
        IFolder currentFolder = null;
        for (String segment : segments) {
            if (currentFolder == null) {
                currentFolder = project.getFolder(segment);
            } else {
                currentFolder = currentFolder.getFolder(segment);
            }
            if (!currentFolder.exists()) {
                currentFolder.create(true, true, new NullProgressMonitor());
            }
        }
        return currentFolder;
    }
    /**
     * Java、Webリソース、CI/CD設定など、多様なファイルをリアルな内容で自動生成する
     */
     void createMassiveDummyFiles(int count) throws CoreException {
        System.out.println(count + " 個の多様なファイルを生成中...");

        String[] javaPackages = {
            "com/jhappy/demo/controller",
            "com/jhappy/demo/service",
            "com/jhappy/demo/model",
            "com/jhappy/demo/utils"
        };

        for (int i = 1; i <= count; i++) {
            String filePath;
            String content;
            int type = i % 10;

            if (type < 6) { 
                // --- 【Javaソースコード】 (0, 1, 2, 3, 4, 5) ---
                String pkgPath = javaPackages[i % javaPackages.length];
                String folder = "src/" + pkgPath;
                createFolderRecursively(folder);
                String pkgName = pkgPath.replace('/', '.');

                if (type == 0) { // Launcher (Executable)
                    String className = "AppLauncher" + i;
                    filePath = folder + "/" + className + ".java";
                    content = "package " + pkgName + ";\n\npublic class " + className + " {\n" +
                              "    public static void main(String[] args) {\n" +
                              "        System.out.println(\"JHappy Instance " + i + " running...\");\n" +
                              "    }\n}\n";
                } else if (type == 1) { // Service logic
                    String className = "ProcessService" + i;
                    filePath = folder + "/" + className + ".java";
                    content = "package " + pkgName + ";\n\nimport java.util.*;\n\npublic class " + className + " {\n" +
                              "    public List<String> filter(List<String> in) {\n" +
                              "        return in.stream().filter(s -> s.length() > " + (i%5) + ").toList();\n" +
                              "    }\n}\n";
                } else { // Generic Component
                    String className = "Component" + i;
                    filePath = folder + "/" + className + ".java";
                    content = "package " + pkgName + ";\n\npublic class " + className + " {\n" +
                              "    private final int id = " + i + ";\n" +
                              "    public int getId() { return id; }\n}\n";
                }

            } else if (type == 7) { 
                // --- 【フロントエンド Webファイル】 (js, css, html) ---
                String ext = (i % 3 == 0) ? "js" : (i % 3 == 1) ? "css" : "html";
                String folder = "src/main/resources/static/" + (ext.equals("html") ? "pages" : ext);
                createFolderRecursively(folder);
                filePath = folder + "/app_" + i + "." + ext;

                if (ext.equals("js")) {
                    content = "function initApp" + i + "() {\n  console.log('App " + i + " initialized');\n  alert('Hello from JHappy!');\n}";
                } else if (ext.equals("css")) {
                    content = ".container-" + i + " {\n  display: flex;\n  background-color: #f0f0" + (i%9) + ";\n  padding: 10px;\n}";
                } else {
                    content = "<!DOCTYPE html>\n<html>\n<head><title>Test " + i + "</title></head>\n" +
                              "<body><h1>Welcome to JHappy Test Page " + i + "</h1></body>\n</html>";
                }

            } else if (type == 8) {
                // --- 【設定ファイル】 (.properties, .json) ---
                String folder = "src/main/resources";
                createFolderRecursively(folder);
                if (i % 2 == 0) {
                    filePath = folder + "/config_" + i + ".properties";
                    content = "server.port=80" + (i%10) + "\napp.name=JHappyDemo" + i + "\nlogging.level.root=INFO";
                } else {
                    filePath = folder + "/data_" + i + ".json";
                    content = "{\n  \"id\": " + i + ",\n  \"status\": \"active\",\n  \"tags\": [\"test\", \"demo\"]\n}";
                }

            } else { 
                // --- 【インフラ・CI/CD設定】 (GitHub Actions, Docker) ---
                if (i % 2 == 0) {
                    String folder = ".github/workflows";
                    createFolderRecursively(folder);
                    filePath = folder + "/ci_" + i + ".yml";
                    content = "name: Java CI " + i + "\non: [push]\njobs:\n  build:\n    runs-on: ubuntu-latest\n    steps:\n      - uses: actions/checkout@v4";
                } else {
                    filePath = "Dockerfile_" + i;
                    content = "FROM eclipse-temurin:17-jdk-alpine\nCOPY target/*.jar app.jar\nENTRYPOINT [\"java\",\"-jar\",\"/app.jar\"]";
                }
            }
            
            // ファイルを書き込む
            createFile(filePath, content);
        }
    
    }
}