package tech.shunzi.model.utils;

import java.util.*;

public class MapSortUtils {

    /**
     * 使用 Map按value进行排序
     * @param oriMap
     * @return
     */
    public static Map<String, String> sortMapStringByValue(Map<String, String> oriMap) {
        return sortMapByValue(oriMap, new MapStringValueComparator<>());
    }

    public static Map<String, Integer> sortMapByIntValue(Map<String, Integer> oriMap) {
        return sortMapByValue(oriMap, new MapIntValueComparator<>());
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        return sortMapByKey(map, new MapStringKeyComparator());
    }

    /**
     * Sort by value
     * @param oriMap original map
     * @param comparator self-define entry comparator
     * @param <K> key param type
     * @param <V> value param type
     * @return sorted map
     */
    private static <K, V> Map<K, V> sortMapByValue(Map<K, V> oriMap, Comparator<Map.Entry<K, V>> comparator)
    {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<K, V> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<K, V>> entryList = new ArrayList<>(oriMap.entrySet());
        Collections.sort(entryList, comparator);
        Iterator<Map.Entry<K, V>> iterator = entryList.iterator();
        Map.Entry<K, V> tempEntry = null;
        while (iterator.hasNext()) {
            tempEntry = iterator.next();
            sortedMap.put(tempEntry.getKey(), tempEntry.getValue());
        }
        return sortedMap;
    }


    /**
     * Sort by Key
     * @param oriMap original map
     * @param comparator self-define comparator
     * @param <K> key param type
     * @param <V> value param type
     * @return sorted map
     */
    private static <K, V> Map<K, V> sortMapByKey(Map<K, V> oriMap, Comparator<K> comparator)
    {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<K, V> sortMap = new TreeMap<K, V>(comparator);
        sortMap.putAll(oriMap);
        return sortMap;
    }


    static class MapStringKeyComparator implements Comparator<String>{

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    static class MapStringValueComparator<K> implements Comparator<Map.Entry<K, String>>
    {
        @Override
        public int compare(Map.Entry<K, String> o1, Map.Entry<K, String> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    static class MapIntValueComparator<K> implements Comparator<Map.Entry<K, Integer>> {

        @Override
        public int compare(Map.Entry<K, Integer> me1, Map.Entry<K, Integer> me2) {

            return me1.getValue().compareTo(me2.getValue());
        }
    }
}
