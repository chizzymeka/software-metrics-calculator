package ucl.cdt.cybersecurity;

import classes.CodeChurn;
import core.InitiateOperation;
import core.VersionNameManager;
import dataset.Dataset;
import dataset.JavaSourceFileCounter;
import utilities.CurrentTime;
import utilities.ValidateDirectoryPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class App {

    private static Path datasetPath;
    private static TreeSet<Path> paths = new TreeSet<>();
    private static boolean csvHeaderWritten;
    private static long totalNumberOfJavaSourceFilesInDataset;
    private static int progressInterval;
    private static long numberOfProcessedFiles;
    private static HashMap<String, HashSet<CodeChurn>> codeChurnDataMap = new HashMap<>();
    private static HashSet<CodeChurn> codeChurnObjects = new HashSet<>();
    private static ArrayList<String> versionNamesList;
    private static HashMap<Integer, Integer> numberOfChangesMap = new HashMap<>();
    private static boolean isCountingJavaSourceFiles = false;
    private static boolean isProcessingCodeChurnData = false;
    private static boolean isCalculatingSoftwareMetrics = false;
    private static HashMap<String, Integer> keyMap = new HashMap<>();

    // ORDER OF METHOD CALLS
    // askUserForDatasetPath()
    // ↓
    // countJavaSourceFiles(datasetPath)
    // ↓
    // traverseDataset(Path datasetPath)
    // ↓
    // buildSourceFileObject(path.toString())
    // ↓
    // visit(compilationUnitNode, null) (MethodDeclarationVisitor)
    // ↓
    // visit(compilationUnitNode, null) (MethodCallVisitor)
    // ↓
    // writeSoftwareMetricsReportRow(Sourcefile sourcefile)

    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i = 0; i < 5; i++) {
            if (i == 1) {
                new App().askUserForDatasetPath();
            } else if (i == 2) {
                System.out.println("Looking up Java source files in the dataset...[" + new CurrentTime().getCurrentTimeStamp() + "]");
                isCountingJavaSourceFiles = true;
                isProcessingCodeChurnData = false;
                isCalculatingSoftwareMetrics = false;
                totalNumberOfJavaSourceFilesInDataset = new JavaSourceFileCounter().countJavaSourceFiles(datasetPath);
                new App().buildProgressInterval();
                new Dataset().traverseDataset(datasetPath);
            } else if (i == 3) {
                isCountingJavaSourceFiles = false;
                isProcessingCodeChurnData = true;
                isCalculatingSoftwareMetrics = false;
                new InitiateOperation().startProgramExecution();
            } else if (i == 4) {
                isCountingJavaSourceFiles = false;
                isProcessingCodeChurnData = false;
                isCalculatingSoftwareMetrics = true;
                new InitiateOperation().startProgramExecution();
            }
        }
        System.out.println("Software metrics report generated...[" + new CurrentTime().getCurrentTimeStamp() + "]");
    }

    void askUserForDatasetPath() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Required Format: C:" + File.separator + "Users" + File.separator + "Dataset" + "(Where the 'Dataset' folder contains sub folders comprising all the system versions.)");
        // TODO: Remove this line after completing the program.
        System.out.println("Current Dataset Location: E:" + File.separator + "Year 1 Project Dataset" + File.separator + "Dataset");
        System.out.println("Test Dataset Location: E:" + File.separator + "Year 1 Project Dataset" + File.separator + "Test Dataset");
        System.out.print("Enter Dataset Path: ");

        String path = scanner.nextLine();
        scanner.close();
        path = path.trim();
        boolean isPathValid = new ValidateDirectoryPath().isValidDirectoryPath(path);

        if (!isPathValid || path.equals("")) {
            System.out.println("Please provide a valid path.");
            return;
        }
        datasetPath = Paths.get(path);
        versionNamesList = new ArrayList<>(new VersionNameManager().getVersionNames()); // LinkedHashSet to ArrayList conversion.
    }

    void buildProgressInterval() {

        // This code sets and scales the progress interval depending on the total number of Java files found. For example: 'Processed 1000 out of 5119' or 'Processed 10000 out of 51119'.
        String totalNumberOfJavaSourceFilesInDataset_str = String.valueOf(totalNumberOfJavaSourceFilesInDataset);
        int numberOfZeros = totalNumberOfJavaSourceFilesInDataset_str.length() - 1;

        if (numberOfZeros >= 5) {
            numberOfZeros = 4;
        }

        StringBuilder progressInterval_str = new StringBuilder("1");

        for (int j = 0; j < numberOfZeros; j++) {
            progressInterval_str.append("0");
        }

        progressInterval = Integer.parseInt(progressInterval_str.toString());

    }

    public Path getDatasetPath() { return datasetPath; }

    public TreeSet<Path> getPaths() { return paths; }

    public void setPaths(TreeSet<Path> paths) { App.paths = paths; }

    public boolean isCsvHeaderWritten() { return csvHeaderWritten; }

    public void setCsvHeaderWritten(boolean csvHeaderWritten) { App.csvHeaderWritten = csvHeaderWritten; }

    public long getTotalNumberOfJavaSourceFilesInDataset() { return totalNumberOfJavaSourceFilesInDataset; }

    public int getProgressInterval() { return progressInterval; }

    public void setProgressInterval(int progressInterval) { App.progressInterval = progressInterval; }

    public long getNumberOfProcessedFiles() {return numberOfProcessedFiles;}

    public void setNumberOfProcessedFiles(long numberOfProcessedFiles) {App.numberOfProcessedFiles = numberOfProcessedFiles;}

    public ArrayList<String> getVersionNamesList() {return versionNamesList;}

    public static HashMap<Integer, Integer> getNumberOfChangesMap() {
        return numberOfChangesMap;
    }

    public static void setNumberOfChangesMap(HashMap<Integer, Integer> numberOfChangesMap) {
        App.numberOfChangesMap = numberOfChangesMap;
    }

    public boolean isCountingJavaSourceFiles() {
        return isCountingJavaSourceFiles;
    }

    public void isCountingJavaSourceFiles(boolean isCountingJavaSourceFiles) { App.isCountingJavaSourceFiles = isCountingJavaSourceFiles; }

    public boolean isProcessingCodeChurnData() {
        return isProcessingCodeChurnData;
    }

    public void setProcessingCodeChurnData(boolean isProcessingCodeChurnData) { App.isProcessingCodeChurnData = isProcessingCodeChurnData; }

    public boolean isCalculatingSoftwareMetrics() {
        return isCalculatingSoftwareMetrics;
    }

    public void setCalculatingSoftwareMetrics(boolean isCalculatingSoftwareMetrics) { App.isCalculatingSoftwareMetrics = isCalculatingSoftwareMetrics; }

    public HashMap<String, HashSet<CodeChurn>> getCodeChurnDataMap() { return codeChurnDataMap; }

    public void setCodeChurnDataMap(HashMap<String, HashSet<CodeChurn>> codeChurnDataMap) { App.codeChurnDataMap = codeChurnDataMap; }

    public HashSet<CodeChurn> getCodeChurnObjects() {
        return codeChurnObjects;
    }

    public void setCodeChurnObjects(HashSet<CodeChurn> codeChurnObjects) {
        App.codeChurnObjects = codeChurnObjects;
    }

    public static HashMap<String, Integer> getKeyMap() {
        return keyMap;
    }

    public static void setKeyMap(HashMap<String, Integer> keyMap) {
        App.keyMap = keyMap;
    }

    // TODO: Look at his link for hyperlinking fie paths: https://stackoverflow.com/questions/7930844/is-it-possible-to-have-clickable-class-names-in-console-output-in-intellij/29881239#29881239
}
