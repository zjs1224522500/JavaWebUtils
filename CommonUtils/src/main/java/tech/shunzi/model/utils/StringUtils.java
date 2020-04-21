package tech.shunzi.model.utils;

import java.util.Date;

/**
 * @author Elvis Zhang
 */
public class StringUtils {

    public static void main(String[] args) {
        String format = "你的课程【%s】即将于【%tR】开始";
        System.out.println(String.format(format, "数学", new Date()));
    }
}
