// Java DirectoryStream tutorial: https://zetcode.com/java/directorystream/
// Java NIO Path: http://tutorials.jenkov.com/java-nio/path.html

package dataset;

import core.SourceFileObjectBuilder;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeSet;

public class Dataset {

    public void traverseDataset(Path datasetPath) throws IOException {

        if (datasetPath != null) {

            TreeSet<Path> paths = new TreeSet<>();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(datasetPath)) {

                for (Path entry : stream) {

                    if (Files.isDirectory(entry)) {
                        traverseDataset(entry);
                    }

                    paths.add(entry);

                }

            }

            for (Path path : paths) {

                // Process all Java files.
                if (path.toString().endsWith(".java")) {
                    new SourceFileObjectBuilder().buildSourceFileObject(path.toString());
                }

            }

        }
    }
}
