package com.elvis.utils;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PropertyDuplicatedValueScan {

    public static void main(String[] args) {
        final String path = System.getProperty("user.dir");
        System.out.println(path);
        new PropertyDuplicatedValueScan().recursionDirectory(path);
    }

    private void readProperty(String filePath) {
        Map<String, String> keys = new HashMap<>();
        Set<String> keySet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            reader.lines().forEach(line -> {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String key = line.split("=")[0];
                    if (!keySet.add(key)) {
                        keys.put(key, filePath);
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Map.Entry<String, String> entry : keys.entrySet()) {
            System.out.println("Repeated Key : " + entry.getValue() + " : " + entry.getKey());
        }
    }

    private void recursionDirectory(String path) {
        File file = new File(path);
        String[] files = file.list();
        for (String str : files) {
            if (str.startsWith("bin") || str.startsWith("test") || str.startsWith("target")) {
                continue;
            }

            String tempPath = path + File.separator + str;
            File tempFile = new File(tempPath);
            if (tempFile.isDirectory()) {
                recursionDirectory(tempPath);
            } else {
                if (str.endsWith("i18n.properties")) {
                    readProperty(tempPath);
                }
            }
        }
    }

}

