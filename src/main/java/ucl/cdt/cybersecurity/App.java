package ucl.cdt.cybersecurity;

import dataset.Dataset;
import dataset.JavaSourceFileCounter;
import classes.CodeChurn;
import utilities.CurrentTime;
import utilities.TaskProgressReporter;
import utilities.ValidateDirectoryPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class App {

    private static Path datasetPath;
    private static boolean csvHeaderWritten;
    private static long totalNumberOfJavaSourceFilesInDataset;
    private static long numberOfProcessedFiles;
    private static HashMap<String, HashSet<CodeChurn>> codeChurnDataMap = new HashMap<>();
    private static HashSet<CodeChurn> codeChurnObjects = new HashSet<>();
    private static HashMap<String, Long> numberOfChangesMap = new HashMap<>();
    private static boolean isCountingJavaSourceFiles = false;
    private static boolean isProcessingCodeChurnData = false;
    private static boolean isCalculatingSoftwareMetrics = false;

    // ORDER OF METHOD CALLS
    // askUserForDatasetPath()
    // ↓
    // countJavaSourceFiles(datasetPath)
    // ↓
    // traverseDataset(Path datasetPath)
    // ↓
    // buildSourcefilebject(path.toString())
    // ↓
    // visit(compilationUnitNode, null) (MethodDeclarationVisitor)
    // ↓
    // visit(compilationUnitNode, null) (MethodCallVisitor)
    // ↓
    // buildCodeChurnData(Sourcefile sourcefile)
    // ↓
    // writeSoftwareMetricsReportRow(Sourcefile sourcefile)

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 5; i++) {

            if (i == 1) {

                new App().askUserForDatasetPath();

            } else if (i == 2) {

                System.out.println("Looking up Java source files in the dataset...[" + new CurrentTime().getCurrentTimeStamp() + "]");

                isCountingJavaSourceFiles = true;
                isProcessingCodeChurnData = false;
                isCalculatingSoftwareMetrics = false;
                totalNumberOfJavaSourceFilesInDataset = new JavaSourceFileCounter().countJavaSourceFiles(datasetPath);

                new TaskProgressReporter().count();

            } else if (i == 3) {

                System.out.println("Building code churn data...[" + new CurrentTime().getCurrentTimeStamp() + "]");

                isCountingJavaSourceFiles = false;
                isProcessingCodeChurnData = true;
                isCalculatingSoftwareMetrics = false;

                new Dataset().traverseDataset(datasetPath);
                new TaskProgressReporter().count();

            } else if (i == 4) {

                System.out.println("Calculating software metrics...[" + new CurrentTime().getCurrentTimeStamp() + "]");

                // Once the program progresses to write the software metrics report, clear out
                // the data structures to help memory.
                codeChurnDataMap = null;
                codeChurnObjects = null;
                isCountingJavaSourceFiles = false;
                isProcessingCodeChurnData = false;
                isCalculatingSoftwareMetrics = true;

                new Dataset().traverseDataset(datasetPath);
                new TaskProgressReporter().count();

            }
        }
        System.out.println("Software metrics report generated...[" + new CurrentTime().getCurrentTimeStamp() + "]");
    }

    void askUserForDatasetPath() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Required Format: C:" + File.separator + "Users" + File.separator + "Dataset" + "(Where the 'Dataset' folder contains subfolders comprising all the system versions.)");
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
    }

    public Path getDatasetPath() {
        return datasetPath;
    }

    public boolean isCsvHeaderWritten() {
        return csvHeaderWritten;
    }

    public void setCsvHeaderWritten(boolean csvHeaderWritten) {
        App.csvHeaderWritten = csvHeaderWritten;
    }

    public long getNumberOfprocessedFiles() {
        return numberOfProcessedFiles;
    }

    public void incrementNumberOfprocessedFiles(long numberOfprocessedFiles) {
        App.numberOfProcessedFiles += numberOfprocessedFiles;
    }

    public long getTotalNumberOfJavaSourcefilesInDataset() {
        return totalNumberOfJavaSourceFilesInDataset;
    }

    public void setTotalNumberOfJavaSourcefilesInDataset(long totalNumberOfJavaSourcefilesInDataset) {
        App.totalNumberOfJavaSourceFilesInDataset = totalNumberOfJavaSourcefilesInDataset;
    }

    public HashMap<String, HashSet<CodeChurn>> getCodeChurnDataMap() {
        return codeChurnDataMap;
    }

    public void setCodeChurnDataMap(HashMap<String, HashSet<CodeChurn>> codeChurnDataMap) {
        App.codeChurnDataMap = codeChurnDataMap;
    }

    public HashSet<CodeChurn> getCodeChurnObjects() {
        return codeChurnObjects;
    }

    public void setCodeChurnObjects(HashSet<CodeChurn> codeChurnObjects) {
        App.codeChurnObjects = codeChurnObjects;
    }

    public long getTotalNumberOfJavaSourceFilesInDataset() {
        return totalNumberOfJavaSourceFilesInDataset;
    }

    public void setTotalNumberOfJavaSourceFilesInDataset(long totalNumberOfJavaSourceFilesInDataset) {
        App.totalNumberOfJavaSourceFilesInDataset = totalNumberOfJavaSourceFilesInDataset;
    }

    public long getNumberOfProcessedFiles() {
        return numberOfProcessedFiles;
    }

    public void setNumberOfProcessedFiles(long numberOfProcessedFiles) {
        App.numberOfProcessedFiles = numberOfProcessedFiles;
    }

    public HashMap<String, Long> getNumberOfChangesMap() {
        return numberOfChangesMap;
    }

    public void setNumberOfChangesMap(HashMap<String, Long> numberOfChangesMap) {
        App.numberOfChangesMap = numberOfChangesMap;
    }

    public boolean isIsCountingJavaSourceFiles() {
        return isCountingJavaSourceFiles;
    }

    public void setIsCountingJavaSourceFiles(boolean isCountingJavaSourceFiles) {
        App.isCountingJavaSourceFiles = isCountingJavaSourceFiles;
    }

    public boolean isIsProcessingCodeChurnData() {
        return isProcessingCodeChurnData;
    }

    public void setIsProcessingCodeChurnData(boolean isProcessingCodeChurnData) {
        App.isProcessingCodeChurnData = isProcessingCodeChurnData;
    }

    public boolean isIsCalculatingSoftwareMetrics() {
        return isCalculatingSoftwareMetrics;
    }

    public void setIsCalculatingSoftwareMetrics(boolean isCalculatingSoftwareMetrics) {
        App.isCalculatingSoftwareMetrics = isCalculatingSoftwareMetrics;
    }

    public boolean isCountingJavaSourcefiles() {
        return isCountingJavaSourceFiles;
    }

    public void setCountingJavaSourcefiles(boolean isCountingJavaSourcefiles) {
        App.isCountingJavaSourceFiles = isCountingJavaSourcefiles;
    }

    public boolean isProcessingCodeChurnData() {
        return isProcessingCodeChurnData;
    }

    public void setProcessingCodeChurnData(boolean isProcessingCodeChurnData) {
        App.isProcessingCodeChurnData = isProcessingCodeChurnData;
    }

    public boolean isCalculatingSoftwareMetrics() {
        return isCalculatingSoftwareMetrics;
    }

    public void setCalculatingSoftwareMetrics(boolean isCalculatingSoftwareMetrics) {
        App.isCalculatingSoftwareMetrics = isCalculatingSoftwareMetrics;
    }

    public void setDatasetPath(Path datasetPath) {
        App.datasetPath = datasetPath;
    }

    public void setNumberOfprocessedFiles(long numberOfprocessedFiles) {
        App.numberOfProcessedFiles = numberOfprocessedFiles;
    }

    // TODO: Look at his link for hyperlinking fie paths:
    // https://stackoverflow.com/questions/7930844/is-it-possible-to-have-clickable-class-names-in-console-output-in-intellij/29881239#29881239

}
