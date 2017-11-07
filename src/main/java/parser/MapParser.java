package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class MapParser {
    public static Map<String, String> parseMap(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(path);
        Scanner sc = new Scanner(inputStream);
        sc.useLocale(Locale.US);

        Map<String, String> configurationMap = new HashMap<>();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.startsWith("%") || line.trim().isEmpty())
                continue;
            String[] split = line.split("=");
            String key = split[0].trim();
            String value = split[1].trim();
            configurationMap.put(key, value);
        }
        sc.close();
        return configurationMap;
    }
}