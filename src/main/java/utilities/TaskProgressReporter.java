package utilities;

import ucl.cdt.cybersecurity.App;

public class TaskProgressReporter {

    public void showProgress() {

        String conditionalText = null;

        if (new App().isProcessingCodeChurnData()) {
            conditionalText = "Building code churn data: ";
        } else if (new App().isCalculatingSoftwareMetrics()) {
            conditionalText = "Writing software metrics CSV report: ";
        }

        long numberOfProcessedFiles = new App().getNumberOfProcessedFiles();
        numberOfProcessedFiles++;
        new App().setNumberOfProcessedFiles(numberOfProcessedFiles);
        long totalNumberOfJavaSourceFilesInDataset = new App().getTotalNumberOfJavaSourceFilesInDataset();

        int progressInterval = new App().getProgressInterval();

        if (numberOfProcessedFiles % progressInterval == 0) {
            System.out.println(conditionalText + numberOfProcessedFiles + " out of " + totalNumberOfJavaSourceFilesInDataset + "...[" + new CurrentTime().getCurrentTimeStamp() + "]");
        }
    }
}
