package classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class CodeChurn {

    private String filepath;
    private String filepathSuffix;
    private String versionName;
    private String sourcefileName;
    private String className;
    private HashMap<String, String> methods = new HashMap<>();
    private HashSet<String> comparedVersions = new HashSet<>();

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

    public HashMap<String, String> getMethods() {
        return methods;
    }

    public void setMethods(HashMap<String, String> methods) {
        this.methods = methods;
    }

    public HashSet<String> getComparedVersions() {
        return comparedVersions;
    }

    public void setComparedVersions(HashSet<String> comparedVersions) {
        this.comparedVersions = comparedVersions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeChurn codeChurn = (CodeChurn) o;
        return Objects.equals(filepath, codeChurn.filepath) && Objects.equals(filepathSuffix, codeChurn.filepathSuffix) && Objects.equals(versionName, codeChurn.versionName) && Objects.equals(sourcefileName, codeChurn.sourcefileName) && Objects.equals(className, codeChurn.className) && Objects.equals(methods, codeChurn.methods) && Objects.equals(comparedVersions, codeChurn.comparedVersions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filepath, filepathSuffix, versionName, sourcefileName, className, methods, comparedVersions);
    }
}
