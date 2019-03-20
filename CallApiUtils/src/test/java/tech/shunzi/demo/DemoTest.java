package tech.shunzi.demo;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import tech.shunzi.utils.FileUtils;
import tech.shunzi.utils.MapSortUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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

    @Test
    public void analyzeSentences()
    {
        String filePath = "C:\\Users\\i348910\\Desktop\\sql log.txt";
        Map<String, Integer> sentenceMap = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            reader.lines().forEach(line -> {
                if (!sentenceMap.keySet().contains(line))
                {
                    int count = 1;
                    sentenceMap.put(line, count);
                }
                else
                {
                    int count = sentenceMap.get(line);
                    sentenceMap.put(line, ++count);
                }
            });

            MapSortUtils.sortMapByIntValue(sentenceMap).entrySet().forEach(entry -> {
                System.out.println("[" + entry.getValue() + "] " + entry.getKey());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testBCrypt()
    {
        String password = "shunzi+1S";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed);
    }
}
