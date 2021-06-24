// Read write CSV file in Java – OpenCSV tutorialg: https://howtodoinjava.com/java/library/parse-read-write-csv-opencsv/
// How do I get a URL or URI to a file?: http://www.avajava.com/tutorials/lessons/how-do-i-get-a-url-or-uri-to-a-file.html
package report;

import au.com.bytecode.opencsv.CSVWriter;
import classes.Sourcefile;
import com.github.javaparser.Position;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import software_metrics.*;
import ucl.cdt.cybersecurity.App;
import utilities.TaskProgressReporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SoftwareMetricsReport {

    // CSV HEADER
    // 1 - Method Signature
    // 2 - Class Name
    // 3 - Confidence Level - VERY HIGH, HIGH, MODERATE, LOW, VERY LOW
    // 4 - Age (in weeks)
    // 5 - Code Churn (since source file creation)
    // 6 - Cyclomatic Complexity
    // 7 - Dependency (Number of Method Calls Per Method)
    // 8 - Number of Lines of Code
    // 9 - Version
    // 10 - Source File Path
    // 11 - Line

    public void writeSoftwareMetricsReportRow(Sourcefile sourcefile) throws IOException {

        // TODO: Change the report filename ("software_metrics_report.csv") to include the name of the system/dataset as input by the user.
//        String csv = "src" + File.separator + "main" + File.separator + "java" + File.separator + "output"
//                + File.separator + "software_metrics_report.csv";
        String csv = "E:" + File.separator + "Year 1 Project Dataset" + File.separator + "CSV Report" + File.separator + "software_metrics_report.csv";

        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, true));

        String[] csvData = new String[11];
        csvData[0] = "Method Signature";
        csvData[1] = "Class Name";
        csvData[2] = "Confidence Level";
        csvData[3] = "Age (in weeks)";
        csvData[4] = "Code Churn (since last source file modification)";
        csvData[5] = "Cyclomatic Complexity";
        csvData[6] = "Dependency (Number of Method Calls Per Method)";
        csvData[7] = "Number of Lines of Code";
        csvData[8] = "Version";
        csvData[9] = "Source File Path";
        csvData[10] = "Line";

        if (!new App().isCsvHeaderWritten()) {
            csvWriter.writeNext(csvData);
        }

        new App().setCsvHeaderWritten(true);

        String filePath = sourcefile.getFilepath();

        List<ClassOrInterfaceDeclaration> classDeclarations = sourcefile.getClassDeclarations();

        for (ClassOrInterfaceDeclaration classDeclaration : classDeclarations) {

            List<MethodDeclaration> methodDeclarations = classDeclaration.getMethods();

            for (MethodDeclaration methodDeclaration : methodDeclarations) {

                String className = classDeclaration.getNameAsString();
                String methodSignature = methodDeclaration.getSignature().toString();
                String methodContent = methodDeclaration.toString();
                Position methodBeginPosition = methodDeclaration.getBegin().get();
                int line = methodBeginPosition.line;

                // Software Metrics
                long ageInWeeks = new AgeCalculator().calculateAgeInWeeks(sourcefile);
                int codeChurn = new CodeChurnLookUp().lookupCodeChurnValue(sourcefile, classDeclaration, methodDeclaration);
                int cyclomaticComplexity = new CyclomaticComplexityCalculator().calculateCyclomaticComplexityValue(methodDeclaration);
                int dependency = new DependencyCalculator().calculateDependency(sourcefile, methodDeclaration);
                int numberOfLinesOfCode = new NumberOfLinesOfCodeCalculator().calculateNumberOfLinesOfCode(methodDeclaration);

                csvData[0] = methodSignature;
                csvData[1] = className;
                csvData[2] = "NA";
                csvData[3] = Long.toString(ageInWeeks);
                csvData[4] = Integer.toString(codeChurn);
                csvData[5] = Integer.toString(cyclomaticComplexity);
                csvData[6] = Integer.toString(dependency);
                csvData[7] = Integer.toString(numberOfLinesOfCode);
                csvData[8] = sourcefile.getDatasetVersion();
                csvData[9] = filePath;
                csvData[10] = Integer.toString(line);
                csvWriter.writeNext(csvData);
            }
        }
        csvWriter.close();
        new TaskProgressReporter().showProgress();
    }
}