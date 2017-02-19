package com.teamlunardi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

   private static String[] data_ =
            {       "line 1",
                    "line 2 2",
                    "line 3 3 3",
                    "line 4 4 4",
                    "line 5 5 5",
            };

    public static void main(String[] args) {
        try {
            FileSystem zipFps = openFile(Paths.get("mydata.zip"));
            writeToZip(zipFps, data_);
            writeToZip2(zipFps,data_);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static FileSystem openFile(Path filePath) throws IOException, URISyntaxException {
        Map<String, String> providerProps = new HashMap<>();
        providerProps.put("create", "true");

        URI zipURI = new URI("jar:file", filePath.toUri().getPath(),null);
        FileSystem zipfps = FileSystems.newFileSystem(zipURI, providerProps);

        return zipfps;
    }

    private static void writeToZip(FileSystem zipFs, String[] message) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(zipFs.getPath("/text1.txt"))) {
            for (String s : message) {
                writer.write(s);
                writer.newLine();
            }
        }
    }
    private static void writeToZip2(FileSystem zipFs, String[] message) throws IOException{
        Files.write(zipFs.getPath("/text2.txt"), Arrays.asList(message),
                Charset.defaultCharset(), StandardOpenOption.CREATE);
    }


}
