package utilities;

import java.util.regex.Pattern;

public class FileSeparator {

    private String fileSeparator = Pattern.quote(System.getProperty("file.separator"));

    public String getFileSeparator() {
        return fileSeparator;
    }
}
