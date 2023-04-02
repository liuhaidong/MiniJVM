package cc.codeup.minijvm.compiler;

import cc.codeup.minijvm.Lexer;
import cc.codeup.minijvm.Parser;
import cc.codeup.minijvm.token.AST;
import cc.codeup.minijvm.token.BinOp;
import cc.codeup.minijvm.token.Num;
import cc.codeup.minijvm.token.Print;

import java.util.ArrayList;
import java.util.List;

import static cc.codeup.minijvm.compiler.OpCode.DIV;
import static cc.codeup.minijvm.compiler.OpCode.MUL;
import static cc.codeup.minijvm.token.TokenType.MINUS;
import static cc.codeup.minijvm.token.TokenType.PLUS;

public class Compiler {
    List<ByteCode> byteCodeList;

    public Compiler() {
        this.byteCodeList = new ArrayList<>();
    }

    private void visit(AST node) {
        if (node instanceof Num) {
            visitNum((Num) node);
        } else if (node instanceof BinOp) {
            visitBinOp((BinOp) node);
        }else if (node instanceof Print) {
            visit(((Print) node).expr);
            byteCodeList.add(new ByteCode(OpCode.PRINT,null));
        } else {
            throw new RuntimeException("Invalid node type");
        }
    }

    private void visitNum(Num node) {
        byteCodeList.add(new ByteCode(OpCode.PUSH, Integer.parseInt(node.value)));
    }

    private void visitBinOp(BinOp node) {
        visit(node.left);
        visit(node.right);

        switch (node.token.type) {
            case PLUS:
                byteCodeList.add(new ByteCode(OpCode.ADD, null));
                break;
            case MINUS:
                byteCodeList.add(new ByteCode(OpCode.SUB, null));
                break;
            case MUL:
                byteCodeList.add(new ByteCode(MUL, null));
                break;
            case DIV:
                byteCodeList.add(new ByteCode(DIV, null));
                break;
            default:
                throw new RuntimeException("Invalid operator");
        }
    }

    public List<ByteCode> compile(AST ast) {
        visit(ast);
        byteCodeList.add(new ByteCode(OpCode.HALT, null));
        return byteCodeList;
    }

    public static void main(String[] args) {
        String input = "3 + 5 * (10 - 2)";
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        AST ast = parser.parse();

        Compiler compiler = new Compiler();
        List<ByteCode> byteCodeList = compiler.compile(ast);
        for (ByteCode byteCode : byteCodeList) {
            System.out.println(byteCode);
        }
    }
}
