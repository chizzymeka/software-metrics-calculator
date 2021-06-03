// How to format FileTime in Java: https://mkyong.com/java/how-to-format-filetime-in-java/
package utilities;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertFileTimeToString {

    public String formatDateTime(FileTime fileTime) {

        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime localDateTime = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime.format(DATE_FORMATTER);
    }
}
