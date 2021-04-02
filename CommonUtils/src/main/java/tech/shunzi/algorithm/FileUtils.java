package tech.shunzi.algorithm;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Elvis Zhang
 */
public class FileUtils {

    /**
     * 返回文件夹下所有文件名
     * @param dirPath 文件夹路径
     * @return list
     */
    public static List<String> listFileNames(String dirPath) {
        return listFiles(dirPath).stream().map(File::getName).collect(Collectors.toList());
    }

    /**
     * 获取单个文件夹下所有的文件
     * @param dirPath 文件夹路径
     * @return 文件list
     */
    public static List<File> listFiles(String dirPath) {
        File dir = new File(dirPath);
        if (null == dir) {
            System.out.println("Empty directory");
            return Collections.emptyList();
        }
        if (dir.isDirectory()) {
            return Arrays.asList(dir.listFiles());
        } else {
            System.out.println("Not a directory");
            return Collections.emptyList();
        }
    }




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
