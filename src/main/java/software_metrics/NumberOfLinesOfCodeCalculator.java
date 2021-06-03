// Simple Source Line Counter in Java for Java: https://www.codeproject.com/Tips/139036/Simple-Source-Line-Counter-in-Java-for-Java-2
package software_metrics;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.Scanner;

public class NumberOfLinesOfCodeCalculator {

    public int calculateNumberOfLinesOfCode(MethodDeclaration methodDeclaration) {

        String methodDeclarationAsString = methodDeclaration.toString().trim();
        Scanner scanner = new Scanner(methodDeclarationAsString);

        int numberOfLinesOfCode = 0;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line != null) {

                line = line.replaceAll("\\n|\\t|\\s", "");

                if ((!line.equals("")) && (!line.startsWith("//") && (!line.startsWith("/*") && (!line.startsWith("*") && (!line.startsWith("*/")))))) {
                    numberOfLinesOfCode++;
                }

            }

        }

        scanner.close();

        return numberOfLinesOfCode;
    }
}
