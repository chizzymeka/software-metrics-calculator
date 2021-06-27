package classes;

import com.github.javaparser.ast.expr.MethodCallExpr;

import java.nio.file.attribute.FileTime;
import java.util.*;

public class Sourcefile {

    private long sourceFileId;
    private String datasetVersion;
    private String filepath;
    private String sourceCodeContent;
    private HashMap<String, HashSet<MethodDeclarationAndAttributes>> classNameAndMethodDeclarationAndAttributes = new HashMap<>();
    private List<MethodCallExpr> methodCallExprs;
    private FileTime lastModifiedDateFileTime;
    private String lastModifiedDate;

    public long getSourceFileId() {
        return sourceFileId;
    }

    public void setSourceFileId(long sourceFileId) {
        this.sourceFileId = sourceFileId;
    }

    public String getDatasetVersion() {
        return datasetVersion;
    }

    public void setDatasetVersion(String datasetVersion) {
        this.datasetVersion = datasetVersion;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getSourceCodeContent() {
        return sourceCodeContent;
    }

    public void setSourceCodeContent(String sourceCodeContent) {
        this.sourceCodeContent = sourceCodeContent;
    }

    public HashMap<String, HashSet<MethodDeclarationAndAttributes>> getClassNameAndMethodDeclarationAndAttributes() {
        return classNameAndMethodDeclarationAndAttributes;
    }

    public void setClassNameAndMethodDeclarationAndAttributes(HashMap<String, HashSet<MethodDeclarationAndAttributes>> classNameAndMethodDeclarationAndAttributes) {
        this.classNameAndMethodDeclarationAndAttributes = classNameAndMethodDeclarationAndAttributes;
    }

    public List<MethodCallExpr> getMethodCallExprs() {
        return methodCallExprs;
    }

    public void setMethodCallExprs(List<MethodCallExpr> methodCallExprs) {
        this.methodCallExprs = methodCallExprs;
    }

    public FileTime getLastModifiedDateFileTime() {
        return lastModifiedDateFileTime;
    }

    public void setLastModifiedDateFileTime(FileTime lastModifiedDateFileTime) {
        this.lastModifiedDateFileTime = lastModifiedDateFileTime;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sourcefile that = (Sourcefile) o;
        return sourceFileId == that.sourceFileId && Objects.equals(datasetVersion, that.datasetVersion) && Objects.equals(filepath, that.filepath) && Objects.equals(sourceCodeContent, that.sourceCodeContent) && Objects.equals(classNameAndMethodDeclarationAndAttributes, that.classNameAndMethodDeclarationAndAttributes) && Objects.equals(methodCallExprs, that.methodCallExprs) && Objects.equals(lastModifiedDateFileTime, that.lastModifiedDateFileTime) && Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceFileId, datasetVersion, filepath, sourceCodeContent, classNameAndMethodDeclarationAndAttributes, methodCallExprs, lastModifiedDateFileTime, lastModifiedDate);
    }
}
