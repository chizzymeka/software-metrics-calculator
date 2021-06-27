package classes;

import java.util.HashSet;
import java.util.Objects;

public class CodeChurn {

    private int codeChurnObjectId;
    private String filepath;
    private String filepathSuffix;
    private String versionName;
    private String sourcefileName;
    private String className;
    private HashSet<Method> methods = new HashSet<>();

    public int getCodeChurnObjectId() {
        return codeChurnObjectId;
    }

    public void setCodeChurnObjectId(int codeChurnObjectId) {
        this.codeChurnObjectId = codeChurnObjectId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepathSuffix() {
        return filepathSuffix;
    }

    public void setFilepathSuffix(String filepathSuffix) {
        this.filepathSuffix = filepathSuffix;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSourcefileName() {
        return sourcefileName;
    }

    public void setSourcefileName(String sourcefileName) {
        this.sourcefileName = sourcefileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public HashSet<Method> getMethods() {
        return methods;
    }

    public void setMethods(HashSet<Method> methods) {
        this.methods = methods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeChurn codeChurn = (CodeChurn) o;
        return codeChurnObjectId == codeChurn.codeChurnObjectId && Objects.equals(filepath, codeChurn.filepath) && Objects.equals(filepathSuffix, codeChurn.filepathSuffix) && Objects.equals(versionName, codeChurn.versionName) && Objects.equals(sourcefileName, codeChurn.sourcefileName) && Objects.equals(className, codeChurn.className) && Objects.equals(methods, codeChurn.methods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeChurnObjectId, filepath, filepathSuffix, versionName, sourcefileName, className, methods);
    }
}
