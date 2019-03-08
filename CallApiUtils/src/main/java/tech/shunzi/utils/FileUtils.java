package tech.shunzi.utils;

import java.io.*;

public class FileUtils {

    /**
     * 写文件
     *
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, String content) {
        FileWriter output = null;
        BufferedWriter writer = null;
        try {
            output = new FileWriter(fileName);
            writer = new BufferedWriter(output);
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        StringBuffer sb = new StringBuffer("");
        FileReader input = null;
        BufferedReader reader = null;
        try {
            input = new FileReader(fileName);
            reader = new BufferedReader(input);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
