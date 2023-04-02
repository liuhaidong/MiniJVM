package cc.codeup.minijvm.token;

public class Print extends AST {
    public AST expr;

    public Print(AST expr) {
        this.expr = expr;
    }
}
