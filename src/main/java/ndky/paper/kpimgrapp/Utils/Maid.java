package ndky.paper.kpimgrapp.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Maid {
    /**
     * map "your_name" to "yourName" or "YourName"
     *
     * @param target               target string
     * @param firstLetterUpperCase true for "YourName" while false for "yourName"
     * @return camelCase result
     */
    public static String mapUnderCoreStringToCamelCase(String target, boolean firstLetterUpperCase) {
        if (!target.contains("_"))
            return target;
        String result = target;
        if (firstLetterUpperCase)
            result = result.substring(0, 1).toUpperCase() + result.substring(1);
        StringBuilder builder = new StringBuilder(result);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }
        return builder.toString();
    }

    /**
     * map `["your_name", "name"]` to `[{"_": "your_name", "aA": "yourName"}, {"_": "name", "aA": "name"}]` or `[{"_": "your_name", "aA": "YourName"}, {"_": "name", "aA": "Name"}]`
     *
     * @return List<Map < String, String>>
     */
    public static List<Map<String, String>> batchMapUnderCoreStringToCamelCase(List<String> target, boolean firstLetterUpperCase) {
        return target.stream().map(ele -> {
            Map<String, String> map = new HashMap<>();
            map.put("_", ele);
            map.put("aA", mapUnderCoreStringToCamelCase(ele, firstLetterUpperCase));
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * check if a file name has valid extension to upload
     *
     * @param filename target file's name
     * @param exts     valid extension list
     * @return true for valid while false otherwise
     */
    public static boolean validFileExtension(String filename, String... exts) {
        if (filename == null || "".equals(filename.trim()) || !filename.contains("."))
            return false;
        String fileExt = getFilenameExt(filename);
        for (String ext : exts) {
            if (ext.equals(fileExt))
                return true;
        }
        return false;
    }

    /**
     * @return extension like .jpg, .vsqx
     */
    public static String getFilenameExt(String filename) {
        if (filename == null)
            return "";
        return filename.substring(filename.lastIndexOf("."));
    }

    public static String getUniqueString() {
        return UUID.randomUUID().toString() + new Date().getTime();
    }
}
