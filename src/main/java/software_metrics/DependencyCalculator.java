// Extract methods calls from Java code: https://tomassetti.me/getting-started-with-javaparser-analyzing-java-code-programmatically/
package software_metrics;

import classes.Sourcefile;
import com.github.javaparser.Position;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.IOException;
import java.util.List;

public class DependencyCalculator {

    public int calculateDependency(Sourcefile sourcefile, MethodDeclaration methodDeclaration) throws IOException {

        int numberOfMethodCalls = 0; // fanOut

        Position methodBeginPosition = methodDeclaration.getBegin().get();
        Position methodEndPosition = methodDeclaration.getEnd().get();

        List<MethodCallExpr> methodExprs = sourcefile.getMethodCallExprs();

        for (MethodCallExpr methodExpr : methodExprs) {

            Position methodCallPosition = methodExpr.getBegin().get();

            // Find out what method (methodDeclaration.toString()) made the method call (methodExpr.toString()).
            if (((methodBeginPosition.compareTo(methodCallPosition)) == -1) && ((methodEndPosition.compareTo(methodCallPosition)) == 1) || ((methodBeginPosition.compareTo(methodCallPosition)) == 0) || ((methodEndPosition.compareTo(methodCallPosition)) == 0)) {

                // This String to Char[] mechanism takes things further by counting the number of chained method calls for example, 'Arrays.asList(descs).iterator()' will be two method calls instead of one.
                String methodCall = methodExpr.toString();
                char[] methodCallToChar = methodCall.toCharArray();

                for (int i = 0; i < methodCallToChar.length; i++) {

                    if (methodCallToChar[i] == '.') {
                        numberOfMethodCalls++;
                    }

                }

            }

        }
        return numberOfMethodCalls;
    }
}
