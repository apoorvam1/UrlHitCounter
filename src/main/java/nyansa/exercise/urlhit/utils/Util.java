package nyansa.exercise.urlhit.utils;

import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
public class Util {

    public static String getGMTDate(String unixSeconds) {
        if (unixSeconds.isEmpty() || Objects.isNull(unixSeconds)) return "";
        Instant instant = Instant.ofEpochSecond(Long.parseLong(unixSeconds));
        DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime d = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        return shortFormat.format(d) + " GMT";
    }

    public static HashMap<String, Long> sortByValue(Map<String, Long> hm) {
        log.trace("start sort map by value");
        // Create a list from elements of HashMap
        List<Map.Entry<String, Long>> list = new LinkedList<>(hm.entrySet());

        /* Sort the list */
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Long> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list) {
            temp.put(entry.getKey(), entry.getValue());
        }

        log.trace("end sort map by value");
        return temp;
    }
}
