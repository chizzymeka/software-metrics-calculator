package core;

import ucl.cdt.cybersecurity.App;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeSet;

public class InitiateOperation {

    public void startProgramExecution() throws IOException, InterruptedException {

        TreeSet<Path> paths = new App().getPaths();

        // TODO: Change data type to long for scalability.
        int sourceFileId = 0;
        for (Path path : paths) {
            // Process all Java files.
            if (path.toString().endsWith(".java")) {
                ++sourceFileId;
                new SourceFileObjectBuilder().buildSourceFileObject(path.toString(), sourceFileId);
            }
        }
    }
}
