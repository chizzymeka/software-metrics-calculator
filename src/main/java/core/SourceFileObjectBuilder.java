package core;

import classes.MethodDeclarationAndAttributes;
import classes.Sourcefile;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java_parser.ClassDeclarationVisitor;
import java_parser.MethodCallVisitor;
import report.SoftwareMetricsReport;
import software_metrics.CodeChurnDataBuilder;
import ucl.cdt.cybersecurity.App;
import utilities.ConvertFileTimeToString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class SourceFileObjectBuilder {

    public void buildSourceFileObject(String sourceFilePath, int sourceFileId) throws IOException {

        LinkedHashSet<String> versionNames = new VersionNameManager().getVersionNames();
        Sourcefile sourcefile = new Sourcefile();

        for (String versionName : versionNames) {

            if (sourceFilePath.contains(versionName)) {

                sourcefile.setSourceFileId(sourceFileId);

                sourcefile.setDatasetVersion(versionName);

                sourcefile.setFilepath(sourceFilePath);

                String content = new SourceFileObjectBuilder().getSourceFileContent(sourceFilePath);
                sourcefile.setSourceCodeContent(content);

                Path file = Paths.get(sourceFilePath);
                BasicFileAttributes basicFileAttributes = Files.readAttributes(file, BasicFileAttributes.class);
                sourcefile.setLastModifiedDateFileTime(basicFileAttributes.lastModifiedTime());

                String lastModifiedDate = new ConvertFileTimeToString().formatDateTime(basicFileAttributes.lastModifiedTime());
                sourcefile.setLastModifiedDate(lastModifiedDate);

                CompilationUnit compilationUnitNode = StaticJavaParser.parse(new File(sourceFilePath));
                List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = new ClassDeclarationVisitor().visit(compilationUnitNode, null);

                for (ClassOrInterfaceDeclaration classOrInterfaceDeclaration : classOrInterfaceDeclarations) {

                    HashSet<MethodDeclarationAndAttributes> methodDeclarationAndAttributesList = new HashSet<>();
                    HashMap<String, HashSet<MethodDeclarationAndAttributes>> classNameAndMethodDeclarationAndAttributes = new HashMap<>();

                    String className = classOrInterfaceDeclaration.getNameAsString();
                    int counter_outer = ++sourceFileId;

                    List<MethodDeclaration> methodDeclarations = classOrInterfaceDeclaration.getMethods();

                    for (MethodDeclaration methodDeclaration : methodDeclarations) {

                        int counter_inner = ++sourceFileId;
                        int methodId = sourceFileId + counter_outer + counter_inner;

                        MethodDeclarationAndAttributes methodDeclarationAndAttributes = new MethodDeclarationAndAttributes();
                        methodDeclarationAndAttributes.setMethodId(methodId);
                        methodDeclarationAndAttributes.setMethodDeclaration(methodDeclaration);
                        methodDeclarationAndAttributesList.add(methodDeclarationAndAttributes);
                        HashMap<String, Integer> keyMap = App.getKeyMap();

                        String filepathSuffix = sourceFilePath.split(versionName)[1];
                        String methodSignature = methodDeclaration.getSignature().toString();

                        /*
                            'keyMap' maps the string key to the methodId. In 'CodeChurnLookup.java', we formulate the string keys from the sourcefile objects and use it to obtain the methodId which we then use to obtain the code churn value.
                            This approach allows the use of an int key in 'CodeChurnDataBuilder'.java to avoid the 'GC overhead limit exceeded' OutOfMemory exception that occurs when we use the string key directly.
                         */
                        String key = versionName + filepathSuffix + className + methodSignature.intern();
                        keyMap.put(key, methodId);
                    }
                    classNameAndMethodDeclarationAndAttributes.put(className, methodDeclarationAndAttributesList);
                    sourcefile.setClassNameAndMethodDeclarationAndAttributes(classNameAndMethodDeclarationAndAttributes);
                }

                // List<MethodDeclaration> methodDeclarations = new MethodDeclarationVisitor().visit(compilationUnitNode, null);
                // sourcefile.setMethodDeclarations(methodDeclarations);

                List<MethodCallExpr> methodCalls = new MethodCallVisitor().visit(compilationUnitNode, null);
                sourcefile.setMethodCallExprs(methodCalls);

                if (new App().isProcessingCodeChurnData()) {
                    new CodeChurnDataBuilder().buildCodeChurnData(sourcefile, sourceFileId);
                } else if (new App().isCalculatingSoftwareMetrics()) {
                    new SoftwareMetricsReport().writeSoftwareMetricsReportRow(sourcefile);
                }
            }
        }
    }

    String getSourceFileContent(String sourceFilePath) throws IOException {

        File sourceFile = new File(sourceFilePath);
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        byte[] data = new byte[(int) sourceFile.length()];
        fileInputStream.read(data);
        fileInputStream.close();

        return new String(data, StandardCharsets.UTF_8);
    }
}
