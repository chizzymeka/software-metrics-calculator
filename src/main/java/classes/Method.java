package classes;

public class Method {

    private String filepath;
    private String versionName;
    private String sourcefileName;
    private String methodSignature;
    private String methodContent;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public String getMethodContent() {
        return methodContent;
    }

    public void setMethodContent(String methodContent) {
        this.methodContent = methodContent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((filepath == null) ? 0 : filepath.hashCode());
        result = prime * result + ((methodContent == null) ? 0 : methodContent.hashCode());
        result = prime * result + ((methodSignature == null) ? 0 : methodSignature.hashCode());
        result = prime * result + ((sourcefileName == null) ? 0 : sourcefileName.hashCode());
        result = prime * result + ((versionName == null) ? 0 : versionName.hashCode());
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
        Method other = (Method) obj;
        if (filepath == null) {
            if (other.filepath != null)
                return false;
        } else if (!filepath.equals(other.filepath))
            return false;
        if (methodContent == null) {
            if (other.methodContent != null)
                return false;
        } else if (!methodContent.equals(other.methodContent))
            return false;
        if (methodSignature == null) {
            if (other.methodSignature != null)
                return false;
        } else if (!methodSignature.equals(other.methodSignature))
            return false;
        if (sourcefileName == null) {
            if (other.sourcefileName != null)
                return false;
        } else if (!sourcefileName.equals(other.sourcefileName))
            return false;
        if (versionName == null) {
            if (other.versionName != null)
                return false;
        } else if (!versionName.equals(other.versionName))
            return false;
        return true;
    }

}
