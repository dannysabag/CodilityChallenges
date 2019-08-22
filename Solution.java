// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Solution {
    public static String string = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n" +
            "john.png, London, 2015-06-20 15:13:22\n" +
            "myFriends.png, Warsaw, 2013-09-05 14:07:13\n" +
            "Eiffel.jpg, Paris, 2015-07-23 08:03:02\n" +
            "pisatower.jpg, Paris, 2015-07-22 23:59:59\n" +
            "BOB.jpg, London, 2015-08-05 00:02:03\n" +
            "notredame.png, Paris, 2015-09-01 12:00:00\n" +
            "me.jpg, Warsaw, 2013-09-06 15:40:22\n" +
            "a.png, Warsaw, 2016-02-13 13:33:50\n" +
            "b.jpg, Warsaw, 2016-01-02 15:12:22\n" +
            "c.jpg, Warsaw, 2016-01-02 14:34:30\n" +
            "d.jpg, Warsaw, 2016-01-02 15:15:01\n" +
            "e.png, Warsaw, 2016-01-02 09:49:09\n" +
            "f.png, Warsaw, 2016-01-02 10:55:32\n" +
            "g.jpg, Warsaw, 2016-02-29 22:13:11";

    public static String solution(String S) {
        // write your code in Java SE 8
        List<String> listOfRows = new ArrayList<>(Arrays.asList(S.split("\\n")));
        List<List<String>> listOfElementsInEachRow = new ArrayList<>();
        Map<String, Map<String, LocalDateTime>> locationsMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (String row : listOfRows) { //create list of elements
            listOfElementsInEachRow.add(Arrays.asList(row.split(",")));
        }

        for (List<String> list : listOfElementsInEachRow) { //build the map by geo locations
            Map<String, LocalDateTime> itemsMap = locationsMap.get(list.get(1));
            if (itemsMap == null) {
                itemsMap = new HashMap<>();
                itemsMap.put(list.get(0), LocalDateTime.parse(list.get(2).trim(), formatter));
                locationsMap.put(list.get(1), itemsMap);
            } else {
                if (!itemsMap.containsKey(list.get(0)))
                    itemsMap.put(list.get(0), LocalDateTime.parse(list.get(2).trim(), formatter));
            }
        }

        locationsMap.forEach((k, v) -> sortByValue(v));
        Map<Integer, String> newMapOfFileNamesOrdered = new HashMap();
        for (int i = 0; i < listOfRows.size(); i++) {
            newMapOfFileNamesOrdered.put(i, ""); //build indexes
        }

        for (String key : locationsMap.keySet()) {
            Map<String, LocalDateTime> fileNameAndDateMap = locationsMap.get(key);
            int counter = 1;
            fileNameAndDateMap = sortByValue(fileNameAndDateMap);
            for (String item : fileNameAndDateMap.keySet()) {
                String newFileName = key + String.format("%02d", counter) + "." + (item.substring(item.length() - 3));
                String s = item + "," + key + ", " + locationsMap.get(key).get(item).format(formatter);
                newMapOfFileNamesOrdered.put(listOfRows.indexOf(s), newFileName + "\n");
                counter++;
            }
        }
        StringBuilder builder = new StringBuilder();
        newMapOfFileNamesOrdered.forEach((k, v) -> builder.append(v));
        System.out.println(builder.toString());
        return builder.toString();
    }

    public static void main(String[] args) {
        solution(string);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}