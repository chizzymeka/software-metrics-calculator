package core;

import classes.Sourcefile;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java_parser.ClassDeclarationVisitor;
import java_parser.MethodCallVisitor;
import java_parser.MethodDeclarationVisitor;
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
import java.util.LinkedHashSet;
import java.util.List;

public class SourceFileObjectBuilder {

    public void buildSourceFileObject(String sourceFilePath) throws IOException {

        LinkedHashSet<String> versionNames = new VersionNameManager().getVersionNames();
        Sourcefile sourcefile = new Sourcefile();

        for (String versionName : versionNames) {

            if (sourceFilePath.contains(versionName)) {

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
                sourcefile.setClassDeclarations(classOrInterfaceDeclarations);

                List<MethodDeclaration> methodDeclarations = new MethodDeclarationVisitor().visit(compilationUnitNode, null);
                sourcefile.setMethodDeclarations(methodDeclarations);

                List<MethodCallExpr> methodCalls = new MethodCallVisitor().visit(compilationUnitNode, null);
                sourcefile.setMethodCallExprs(methodCalls);

                if (new App().isProcessingCodeChurnData()) {
                    new CodeChurnDataBuilder().buildCodeChurnData(sourcefile);
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
