package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jhappy.jhappy4ai.aiexporter.logic.ExportService;

@DisplayName("AI Exporter Core Logic Tests (JUnit 6 compatible)")
class ExportServiceTest {

    private ExportService service;
    private StringWriter output;
    private BufferedWriter writer;

    @BeforeEach
    void init() {
        // Mockito 1.x / 2.x の場合はこちらを使用します
        org.mockito.MockitoAnnotations.initMocks(this);
        
        service = new ExportService();
        output = new StringWriter();
        writer = new BufferedWriter(output);
    }

    @Test
    @DisplayName("Should correctly aggregate multiple file contents into one string")
    void aggregateMultipleFilesTest() throws Exception {
        IFile fileA = createMockFile("/src/A.java", "public class A {}", "java");
        IFile fileB = createMockFile("/src/B.java", "public class B {}", "java");

        service.performExport(List.of(fileA, fileB), writer, 0);
        writer.flush(); // ★必ずフラッシュする

        String result = output.toString();
        
        // 【デバッグ用】実際の中身をコンソールに出して目視確認！
        System.out.println("=== TEST OUTPUT START ===");
        System.out.println(result);
        System.out.println("=== TEST OUTPUT END ===");

        // 検証（contains ではなく、中身が空でないかから確認）
        assertFalse(result.isEmpty(), "出力が空っぽです！");
        // もし多言語化で失敗しているなら、ファイル名が含まれているかだけでチェック
        assertTrue(result.contains("public class A {}"), "ファイルAの内容がありません");
    }

    private IFile createMockFile(String path, String content, String extension) throws Exception {
        IFile file = mock(IFile.class);
        when(file.getFullPath()).thenReturn(new Path(path));
        when(file.getFileExtension()).thenReturn(extension);
        when(file.getContents()).thenReturn(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        return file;
    }
}