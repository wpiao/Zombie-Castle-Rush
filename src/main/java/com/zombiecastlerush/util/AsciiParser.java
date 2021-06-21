package com.zombiecastlerush.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AsciiParser {
    public static List<String> parse(Path path) throws IOException {
        List<String> asciiLines = new ArrayList<>();
        Files.lines(path).forEach(asciiLines::add);
        return asciiLines;
    }
    public static void write(File src, List<String> asciiLines) throws IOException {
        // clear existing contents of file
        AsciiParser.clearFile(src);
        PrintWriter pw = new PrintWriter(new FileWriter(src, true));
        // append each line to empty file
        for (String line : asciiLines) {
            pw.println(line);
        }
        pw.close();
    }

    public static void clearFile(File src) throws IOException {
        FileWriter fw = new FileWriter(src, false);
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
    }
}