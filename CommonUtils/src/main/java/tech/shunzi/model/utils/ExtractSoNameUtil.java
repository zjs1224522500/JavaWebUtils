package tech.shunzi.model.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Elvis Zhang
 */
public class ExtractSoNameUtil {

    final static String LIB_DIR = "C:\\Users\\Elvis Zhang\\Desktop\\hcs_obj_sdk\\hcs_obj_sdk\\lib\\CentOS64";
    public static List<String> getLibName(String dir) {
        return FileUtils.listFileNames(dir).stream().filter(name -> name.contains("lib") && name.contains(".so")).
                map(name -> name.substring(name.indexOf("lib") + "lib".length(), name.indexOf(".so"))).collect(Collectors.toList());
    }
    public static String generateGccLink(List<String> libNames) {
        StringBuilder result = new StringBuilder();
        libNames.forEach(name -> {
            result.append(String.format("\"-l\",\n\"%s\",\n", name));
        });
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateGccLink(getLibName(LIB_DIR)));
    }
}
