package utilities;

import java.nio.file.Paths;

public class SourcefileNameExtractor {

    public String extractSourcefileName(String sourcefilePath) {

        return Paths.get(sourcefilePath).getFileName().toString();

    }
}
