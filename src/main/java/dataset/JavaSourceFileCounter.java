package dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class JavaSourceFileCounter {

    public long countJavaSourceFiles(Path datasetPath) throws IOException {

        long totalNumberOfJavaSourceFilesInDataset = 0;

        try (Stream<Path> walk = Files.walk(datasetPath)) {
            totalNumberOfJavaSourceFilesInDataset = walk.filter(p -> !Files.isDirectory(p)).map(p -> p.toString().toLowerCase()).filter(f -> f.endsWith(".java")).count();
        }

        return totalNumberOfJavaSourceFilesInDataset;
    }
}
