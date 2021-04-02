package tech.shunzi.algorithm;

public class SplitLog {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\lyh\\Desktop\\log.txt";
        String content = FileUtils.readFile(filePath);
        String splitString = "2019-07-22";

        for (String singleLog : content.split(splitString))
        {
            if (singleLog.contains("Read file with length"))
            {
                System.out.println();
            }
            System.out.println(splitString + singleLog);
        }
    }
}
