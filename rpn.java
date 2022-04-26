import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class rpn {

    // passar queue e já dar push?
    static boolean isOp(String str) {
        if (str.equals("+")) {
            return true;
        } else if (str.equals("-")) {
            return true;
        } else if (str.equals("*")) {
            return true;
        } else if (str.equals("/")) {
            return true;
        }
        return false;
    }

    // push op
    static Queue<Token> pushOp(Queue<Token> queue, String str) {
        if (str.equals("+")) {
            queue.add(new Token(TokenType.PLUS, "+"));
        } else if (str.equals("*")) {
            queue.add(new Token(TokenType.STAR, "*"));
        } else if (str.equals("-")) {
            queue.add(new Token(TokenType.MINUS, "-"));
        } else if (str.equals("/")) {
            queue.add(new Token(TokenType.SLASH, "/"));
        }

        return queue;

    }

    static boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get("Calc1.stk"))) {
            String str;

            Queue<Token> tokens = new LinkedList<Token>();

            while ((str = br.readLine()) != null) {
                if (isOp(str)) {
                    tokens = rpn.pushOp(tokens, str);
                } else if (isNum(str)) {
                    tokens.add(new Token(TokenType.NUM, str));
                } else {
                    throw new Error("Input invalido");
                }
            }

            Stack<Double> stack = new Stack<Double>();

            while (!tokens.isEmpty()) {

                Token exp = tokens.remove();
                Double temp1 = 0.0;
                Double temp2 = 0.0;

                if (exp.lexeme.equals("+")) {
                    temp1 = stack.pop();
                    temp2 = stack.pop();
                    stack.push(temp1 + temp2);
                } else if (exp.lexeme.equals("-")) {
                    temp1 = stack.pop();
                    temp2 = stack.pop();
                    stack.push(temp1 - temp2);
                } else if (exp.lexeme.equals("/")) {
                    temp1 = stack.pop();
                    temp2 = stack.pop();
                    stack.push(temp1 / temp2);
                } else if (exp.lexeme.equals("*")) {
                    temp1 = stack.pop();
                    temp2 = stack.pop();
                    stack.push(temp1 * temp2);
                } else {
                    Double d = Double.parseDouble(exp.lexeme);
                    stack.push(d);
                }

            }

            System.out.println(stack.pop());

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

    }

}