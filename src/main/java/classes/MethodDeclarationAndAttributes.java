package classes;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.Objects;

public class MethodDeclarationAndAttributes {

    private MethodDeclaration methodDeclaration;
    private int methodId;

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodDeclarationAndAttributes that = (MethodDeclarationAndAttributes) o;
        return methodId == that.methodId && Objects.equals(methodDeclaration, that.methodDeclaration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodDeclaration, methodId);
    }
}
