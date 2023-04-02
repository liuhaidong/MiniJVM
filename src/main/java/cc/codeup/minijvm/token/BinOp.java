package cc.codeup.minijvm.token;

import cc.codeup.minijvm.token.AST;
import cc.codeup.minijvm.token.Token;

public class BinOp extends AST {
    public AST left;
    public Token token;
    public AST right;

    public BinOp(AST left, Token token, AST right) {
        this.left = left;
        this.token = token;
        this.right = right;
    }
}