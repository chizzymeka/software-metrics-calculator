package java_parser;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MethodCallVisitor extends GenericListVisitorAdapter<MethodCallExpr, Object> {

    @Override
    public List<MethodCallExpr> visit(MethodCallExpr methodCallExpr, Object arg) {

        super.visit(methodCallExpr, arg);
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        methodCallExprs.add(methodCallExpr);

        return methodCallExprs;
    }
}
