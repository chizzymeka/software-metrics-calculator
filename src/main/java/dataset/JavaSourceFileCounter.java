package dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class JavaSourceFileCounter {

    public long countJavaSourceFiles(Path path) throws IOException {

        long totalNumberOfJavaSourceFiles = 0;

        try (Stream<Path> walk = Files.walk(path)) {
            totalNumberOfJavaSourceFiles = walk.filter(p -> !Files.isDirectory(p)).map(p -> p.toString().toLowerCase()).filter(f -> f.endsWith(".java")).count();
        }

        return totalNumberOfJavaSourceFiles;
    }
}
