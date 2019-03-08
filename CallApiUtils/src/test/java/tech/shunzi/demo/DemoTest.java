package tech.shunzi.demo;

import org.junit.Test;
import tech.shunzi.utils.FileUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DemoTest {

    @Test
    public void test()
    {
        String filePath = "C:\\Users\\i348910\\Desktop\\Chinese Culture.txt";
        String outputFilePath = "C:\\Users\\i348910\\Desktop\\Chinese Culture Copy.txt";
        Set<String> keySet = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath))))
        {
            keySet = reader.lines().collect(Collectors.toSet());
            int count = 1;
            StringBuilder content = new StringBuilder();
            for (String key : keySet)
            {
                content.append(String.format("%d. %s \n", count, key));
                ++count;
            }
            FileUtils.writeFile(outputFilePath, content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
