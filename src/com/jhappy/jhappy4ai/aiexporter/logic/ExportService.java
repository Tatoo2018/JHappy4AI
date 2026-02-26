package com.jhappy.jhappy4ai.aiexporter.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.jhappy.jhappy4ai.aiexporter.handlers.Messages;

public class ExportService {

    /**
     * 実行結果を保持する構造体
     */
    public static class ExportResult {
        public int fileCount = 0;
        public long totalBytes = 0;
        public boolean limitReached = false;
    }

    /**
     * @param maxSizeBytes 0の場合は無制限。
     */
    public ExportResult performExport(List<IFile> targetFiles, BufferedWriter writer, long maxSizeBytes) throws Exception {
        ExportResult result = new ExportResult();
        long[] currentBytes = { 0 };
        boolean[] limitReached = { false };

        // 1. 注釈とヘッダーの出力
        writeAndCount(writer, "> " + Messages.Handler_EncodingNote + "\n\n", currentBytes);
        writeAndCount(writer, Messages.Handler_FileHeader, currentBytes);
        writeAndCount(writer, Messages.Handler_TOCHeader, currentBytes);

        // 目次の出力
        for (IFile file : targetFiles) {
            String tocLine = "- " + file.getFullPath().toPortableString() + "\n";
            writeAndCount(writer, tocLine, currentBytes);
        }
        writeAndCount(writer, "\n---\n\n", currentBytes);

        // 2. ファイル本文の書き出し
        for (IFile file : targetFiles) {
            if (limitReached[0]) break;
            appendFileContent(file, writer, currentBytes, maxSizeBytes, limitReached);
            result.fileCount++;
        }

        // 3. 制限到達時の警告
        if (limitReached[0]) {
            long displayMB = maxSizeBytes / (1024 * 1024);
            String limitMsg = "\n\n" + MessageFormat.format(Messages.Handler_LimitReachedWarning, displayMB);
            writer.write(limitMsg);
            currentBytes[0] += limitMsg.getBytes(StandardCharsets.UTF_8).length;
        }
        
        writer.flush();
        
        result.totalBytes = currentBytes[0];
        result.limitReached = limitReached[0];
        return result;
    }

    private void appendFileContent(IFile file, BufferedWriter writer, long[] currentBytes, long maxSizeBytes, boolean[] limitReached) throws java.io.IOException {
        String ext = file.getFileExtension() != null ? file.getFileExtension() : "";
        String fileHeader = "## " + file.getFullPath().toPortableString() + "\n```" + ext + "\n";
        
        if (isOverLimit(fileHeader, currentBytes, maxSizeBytes)) {
            limitReached[0] = true;
            return;
        }
        writeAndCount(writer, fileHeader, currentBytes);

        String charset;
        try {
            charset = file.getCharset();
        } catch (CoreException e) {
            charset = "UTF-8";
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getContents(), charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String lineWithNewline = line + "\n";
                if (isOverLimit(lineWithNewline, currentBytes, maxSizeBytes)) {
                    limitReached[0] = true;
                    break;
                }
                writeAndCount(writer, lineWithNewline, currentBytes);
            }
        } catch (Exception e) {
            String errorMsg = MessageFormat.format(Messages.Handler_ErrorRead, e.getMessage()) + "\n";
            writeAndCount(writer, errorMsg, currentBytes);
        }
        
        String footer = "```\n\n";
        if (!limitReached[0]) {
            if (!isOverLimit(footer, currentBytes, maxSizeBytes)) {
                writeAndCount(writer, footer, currentBytes);
            } else {
                limitReached[0] = true;
            }
        }
    }

    private void writeAndCount(BufferedWriter writer, String text, long[] currentBytes) throws java.io.IOException {
        writer.write(text);
        currentBytes[0] += text.getBytes(StandardCharsets.UTF_8).length;
    }

    private boolean isOverLimit(String text, long[] currentBytes, long maxSizeBytes) {
        if (maxSizeBytes <= 0) return false;
        long nextBytes = currentBytes[0] + text.getBytes(StandardCharsets.UTF_8).length;
        return nextBytes > maxSizeBytes;
    }
}