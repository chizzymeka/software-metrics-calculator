//Cyclomatic Complexity Shortcut: https://www.youtube.com/watch?v=PlCGomvu-NM&t=179s
// JavaNCSS: https://javancss.github.io/specification.html
// Skip to 3:37
// Skip to 4:30
package software_metrics;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.Scanner;

public class CyclomaticComplexityCalculator {

    public int calculateCyclomaticComplexityValue(MethodDeclaration methodDeclaration) {
        /*
         * CYCLIOMATIC COMPLEXITY SHORTCUT: Count the number of 'if', 'else if', cases
         * and default case (in a switch statement), 'for', 'while', 'do-while' and add
         * one to the sum.
         */
        String methodBodyAsString = methodDeclaration.getBody().toString().trim();
        Scanner scanner = new Scanner(methodBodyAsString);

        int cyclomaticComplexity = 1;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line != null) {

                // Check that line is not empty, not a comment or other irrelevant notations.
                if ((!line.equals("")) && (!line.startsWith("//") && (!line.startsWith("/*") && (!line.startsWith("*") && (!line.startsWith("*/") && (!line.contains("<?>") && (!line.contains("? extends Object")))))))) {

                    // Proceed to check for keywords consistent with cyclomatic complexity calculation.
                    if (line.contains("if") || line.contains("for") || line.contains("while") || line.contains("case") || line.contains("catch") || line.contains("&&") || line.contains("||") || line.contains("?")) {
                        cyclomaticComplexity++;
                    }

                }

            }

        }

        scanner.close();

        return cyclomaticComplexity;
    }
}
