package core;

import ucl.cdt.cybersecurity.App;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeSet;

public class InitiateOperation {

    public void startProgramExecution() throws IOException, InterruptedException {

        TreeSet<Path> paths = new App().getPaths();

        for (Path path : paths) {
            // Process all Java files.
            if (path.toString().endsWith(".java")) {
                new SourceFileObjectBuilder().buildSourceFileObject(path.toString());
            }
        }
    }
}
