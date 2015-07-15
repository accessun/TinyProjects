/***********************************************************************
 *  Compilation: javac FunctionalCalculator.java
 *  Execution:   java FunctionalCalculator < filename
 *  Test files:  data-fc-1.txt
 *               data-fc-2.txt
 *               data-fc-3.txt
 *               data-fc-4.txt
 *
 *  Calculates the result of a functional math expression (Four basic
 *  operations are supported: addition, subtraction, mutiplication, and
 *  division)
 *
 *  Examples:
 *
 *  $ more data-fc-1.txt
 *  ( + 1 2 3 4 5 )
 *
 *  $ java FunctionalCalculator < data-fc-1.txt
 *  Result: 15.0
 *  
 *  $ more data-fc-2.txt
 *  ( * ( + 1 2 ) 3 4 )
 *
 *  $ java FunctionalCalculator < data-fc-2.txt
 *  Result: 36.0
 *
 *  $ java FunctionalCalculator <<< '( + ( / 10 2 ) ( - 10 5 ) 2 3 )'
 *  Result: 15.0
 *
 **********************************************************************/

import java.util.Scanner;
import java.util.Stack;

/**
 *  The <tt>FunctionalCalculator</tt> class provides a static method
 *  <tt>calc()</tt> to read from standard input the functional math expression
 *  and then calculate the corresponding result. Note that the expression must
 *  satisfy that every number (operand), operator, and parenthsis be separated
 *  by a space.
 *
 *  @author Xin Sun
 */
public class FunctionalCalculator {
    public static double calc() {
        Scanner scannerObject = new Scanner(System.in);
        Stack<String> readInStack = new Stack<String>();
        Stack<Double> tempOperandsStack = new Stack<Double>();

        while (scannerObject.hasNext()) {
             String readIn = scannerObject.next();
             if (readIn.equals("(")) { /* do nothing */ }
             else if (readIn.equals(")")) {
                 // do the calculation in this block of code
                 String s = readInStack.pop();
                 while (!isOperator(s)) {
                     tempOperandsStack.push(Double.parseDouble(s));
                     s = readInStack.pop();
                 }

                 double val = tempOperandsStack.pop();
                 if (s.equals("+")) {
                     while (!tempOperandsStack.empty())
                         val += tempOperandsStack.pop();
                 }
                 else if (s.equals("-")) {
                     while (!tempOperandsStack.empty())
                         val -= tempOperandsStack.pop();
                 }
                 else if (s.equals("*")) {
                     while (!tempOperandsStack.empty())
                         val *= tempOperandsStack.pop();
                 }
                 else { // divide "/"
                     while (!tempOperandsStack.empty())
                         val /= tempOperandsStack.pop();
                 }
                 readInStack.push("" + val);
             }
             else {
                 readInStack.push(readIn);
             }
        }

        return Double.parseDouble(readInStack.pop());
    }

    private static boolean isOperator(String op) {
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Result: " + calc());
    }
}
