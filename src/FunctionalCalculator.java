/***********************************************************************
 *  Compilation:  javac FunctionalCalculator.java
 *  Execution:    java FunctionalCalculator < filename
 *  Dependencies: ValidateExpression.class
 *  Test files:   data-fc-1.txt
 *                data-fc-2.txt
 *                data-fc-3.txt
 *                data-fc-4.txt
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

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.NoSuchElementException;

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

    private ArrayList<String> expressionList = new ArrayList<String>();
    private Stack<String> readInStack = new Stack<String>();
    private Stack<Double> tempOperandsStack = new Stack<Double>();

    public static void main(String[] args) {
        new FunctionalCalculator().calc();
    }

    public void calc() {

        readInExpression();
        if (expressionList.isEmpty())
            throw new NoSuchElementException("Expression must not be empty!");

        // validate the format of the expression stored in the ArrayList
        // before performing the calculation procedure
        if (!(new ValidateExpression().validate(expressionList)))
            throw new UnsupportedOperationException("Expression is misformed. Check again.");

        for (String readIn : expressionList) {

             if (readIn.equals("(")) { /* do nothing */ }
             else if (readIn.equals(")")) {
                // do the calculation in this block of code
                // the core calculation block relies on the assumption that the
                // input items must be "(", ")", operators, and double values
                // (they are separated by spaces. No other kind of input is 
                // permitted.)
                String s = readInStack.pop();
                while (!isOperator(s)) {
                    // If it is not an operator, it must be a double value.
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

        Double result =  Double.parseDouble(readInStack.pop());
        System.out.println("Result: " + result);
    }

    private boolean isOperator(String op) {
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))
            return true;
        return false;
    }

    private void readInExpression() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext())
            expressionList.add(sc.next());
        sc.close();
    }
}
