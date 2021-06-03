package core;

import ucl.cdt.cybersecurity.App;
import utilities.AlphanumComparator;
import utilities.FileSeparator;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class VersionNameManager {

    public LinkedHashSet<String> getVersionNames() throws IOException {

        FileSeparator fileSeparator = new FileSeparator();
        String pathSeparator = fileSeparator.getFileSeparator();
        ArrayList<String> versionNames = new ArrayList<>();
        LinkedHashSet versionNames_sorted = null;

        // Consider modifying this data structure to use a temp file.
        // Traverse the top level folders in the dataset folder to obtain the system
        // version names from the folder names.
        App App = new App();
        Path datasetPath = App.getDatasetPath();

        if (datasetPath != null) {

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(datasetPath)) {
                for (Path path : paths) {
                    if (Files.isDirectory(path)) {

                        String[] directoryPath = path.toString().split(pathSeparator);
                        String versionName = directoryPath[directoryPath.length - 1];
                        try {
                            versionNames.add(versionName);
                        } catch (OutOfMemoryError ome) {
                            System.err.println("Too many version names!");
                        }
                    }
                }
            }
            List<String> values = versionNames;
            List<String> versionNamesList = values.stream().sorted(new AlphanumComparator()).collect(Collectors.toList());
            versionNames_sorted = new LinkedHashSet(versionNamesList);
        }
        return versionNames_sorted;
    }

    public String getVersionName(String sourcefilePath) throws IOException {

        String version = "Version not found!";
        LinkedHashSet<String> versionNames = getVersionNames();

        for (String versionName : versionNames) {

            if (sourcefilePath.contains(versionName)) {
                version = versionName;
            }

        }

        return version;
    }

    public int getTotalNumberOfVersions() throws IOException {

        int totalNumberOfVersions = 0;
        totalNumberOfVersions = getVersionNames().size();
        return totalNumberOfVersions;

    }
}
