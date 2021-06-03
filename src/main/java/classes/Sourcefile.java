package classes;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.nio.file.attribute.FileTime;
import java.util.List;

public class Sourcefile {

    private String datasetVersion;
    private String filepath;
    private String sourceCodeContent;
    private List<ClassOrInterfaceDeclaration> classDeclarations;
    private List<MethodDeclaration> methodDeclarations;
    private List<MethodCallExpr> methodCallExprs;
    private FileTime lastModifiedDateFileTime;
    private String lastModifiedDate;

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

    public List<ClassOrInterfaceDeclaration> getClassDeclarations() {
        return classDeclarations;
    }

    public void setClassDeclarations(List<ClassOrInterfaceDeclaration> classDeclarations) {
        this.classDeclarations = classDeclarations;
    }

    public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
        this.methodDeclarations = methodDeclarations;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classDeclarations == null) ? 0 : classDeclarations.hashCode());
        result = prime * result + ((datasetVersion == null) ? 0 : datasetVersion.hashCode());
        result = prime * result + ((filepath == null) ? 0 : filepath.hashCode());
        result = prime * result + ((lastModifiedDate == null) ? 0 : lastModifiedDate.hashCode());
        result = prime * result + ((lastModifiedDateFileTime == null) ? 0 : lastModifiedDateFileTime.hashCode());
        result = prime * result + ((methodCallExprs == null) ? 0 : methodCallExprs.hashCode());
        result = prime * result + ((methodDeclarations == null) ? 0 : methodDeclarations.hashCode());
        result = prime * result + ((sourceCodeContent == null) ? 0 : sourceCodeContent.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sourcefile other = (Sourcefile) obj;
        if (classDeclarations == null) {
            if (other.classDeclarations != null)
                return false;
        } else if (!classDeclarations.equals(other.classDeclarations))
            return false;
        if (datasetVersion == null) {
            if (other.datasetVersion != null)
                return false;
        } else if (!datasetVersion.equals(other.datasetVersion))
            return false;
        if (filepath == null) {
            if (other.filepath != null)
                return false;
        } else if (!filepath.equals(other.filepath))
            return false;
        if (lastModifiedDate == null) {
            if (other.lastModifiedDate != null)
                return false;
        } else if (!lastModifiedDate.equals(other.lastModifiedDate))
            return false;
        if (lastModifiedDateFileTime == null) {
            if (other.lastModifiedDateFileTime != null)
                return false;
        } else if (!lastModifiedDateFileTime.equals(other.lastModifiedDateFileTime))
            return false;
        if (methodCallExprs == null) {
            if (other.methodCallExprs != null)
                return false;
        } else if (!methodCallExprs.equals(other.methodCallExprs))
            return false;
        if (methodDeclarations == null) {
            if (other.methodDeclarations != null)
                return false;
        } else if (!methodDeclarations.equals(other.methodDeclarations))
            return false;
        if (sourceCodeContent == null) {
            if (other.sourceCodeContent != null)
                return false;
        } else if (!sourceCodeContent.equals(other.sourceCodeContent))
            return false;
        return true;
    }

}
