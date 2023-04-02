package cc.codeup.minijvm;

import cc.codeup.minijvm.compiler.ByteCode;
import cc.codeup.minijvm.compiler.Compiler;
import cc.codeup.minijvm.token.AST;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class VirtualMachine {
    private List<ByteCode> byteCodeList;
    private int programCounter;
    private Stack<Integer> stack;

    VirtualMachine() {
        this.byteCodeList = new ArrayList<>();
        this.programCounter = 0;
        this.stack = new Stack<>();
    }

    void loadByteCode(List<ByteCode> byteCodeList) {
        this.byteCodeList = byteCodeList;
    }

    void execute() {
        while (programCounter < byteCodeList.size()) {
            ByteCode byteCode = byteCodeList.get(programCounter);

            switch (byteCode.opCode) {
                case PUSH:
                    stack.push((Integer) byteCode.operand);
                    programCounter++;
                    break;
                case ADD:
                    stack.push(stack.pop() + stack.pop());
                    programCounter++;
                    break;
                case SUB:
                    int rightSub = stack.pop();
                    int leftSub = stack.pop();
                    stack.push(leftSub - rightSub);
                    programCounter++;
                    break;
                case MUL:
                    stack.push(stack.pop() * stack.pop());
                    programCounter++;
                    break;
                case DIV:
                    int rightDiv = stack.pop();
                    int leftDiv = stack.pop();
                    stack.push(leftDiv / rightDiv);
                    programCounter++;
                    break;
                case PRINT:
                    System.out.println(stack.peek());
                    programCounter++;
                    break;
                case HALT:
                    return;
                default:
                    throw new RuntimeException("Invalid opcode");
            }
        }
    }

    public static void main(String[] args) {
        String input = "print 3";
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        AST ast = parser.parse();

        Compiler compiler = new Compiler();
        List<ByteCode> byteCodeList = compiler.compile(ast);

        VirtualMachine vm = new VirtualMachine();
        vm.loadByteCode(byteCodeList);
        vm.execute(); // 执行字节码并输出结果
    }
}
