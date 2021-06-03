package utilities;

import ucl.cdt.cybersecurity.App;

public class TaskProgressReporter {

    public void count() {

        long numberOfProcessedFiles = new App().getNumberOfprocessedFiles();
        numberOfProcessedFiles++;
        new App().setNumberOfprocessedFiles(numberOfProcessedFiles);
        long totalNumberOfJavaSourceFilesInDataset = new App().getTotalNumberOfJavaSourcefilesInDataset();

        if (numberOfProcessedFiles % 10000 == 0) {
            System.out.println("\r" + "Processed: " + numberOfProcessedFiles + " out of " + totalNumberOfJavaSourceFilesInDataset + "...[" + new CurrentTime().getCurrentTimeStamp() + "]");
        }
    }
}
