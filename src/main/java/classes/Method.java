package classes;

import java.util.Objects;

public class Method {

    private int methodId;
    private String methodSignature;
    private String methodContent;

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return methodId == method.methodId && Objects.equals(methodSignature, method.methodSignature) && Objects.equals(methodContent, method.methodContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodId, methodSignature, methodContent);
    }
}
