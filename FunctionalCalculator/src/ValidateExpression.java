import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to validate the format of the expression fed to
 * <tt>FunctionalCalculator</tt>. In order to rule out most of the misformed
 * cases, the following four requirements must be satisfied:
 *
 *  1. Each part of the expression must be left parenthesis, right parenthesis,
 *  one of the four operators, or String of double value format;
 *
 *  2. The number of left parenthesis must equal to the number of right
 *  parenthesis;
 *
 *  3. The part that closely follows a left parenthesis must be an operator;
 *
 *  4. The part that closely follows an operator must be either a String of
 *  double value format or a left parenthesis.
 *
 * The method in this class implements the procedures to check these four rule.
 * One can test this the correctness of this class in a terminal.
 * 
 * Examples:
 *
 * $ java ValidateExpression <<< '( + 1 2 3 4 5 )'
 * Expression is valid.
 *
 * $ java ValidateExpression <<< '( * ( + 1 2 ) 3 4 )'
 * Expression is valid.
 *
 * $ java ValidateExpression <<< '( * (+ 1 2 ) 3 4 )'
 * Expression is of wrong format.
 *
 * $ java ValidateExpression <<< '( + 1 2.s 3 4 5 ))'
 * Expression is of wrong format.
 *
 * $ java ValidateExpression <<< '( + 1 2 3 4 5 ))'
 * Expression is of wrong format.
 * 
 * $ java ValidateExpression <<< '( + 1 2 3 ( % 4 5 ) )'
 * Expression is of wrong format.
 *
 * @author Xin Sun
 */
public class ValidateExpression {
	public boolean validate(ArrayList<String> expressionList) {
		int numberOfLeftParen = 0;
		int numberOfRightParen = 0;
		String oldpart = null;

		for (String part : expressionList) {
			if (part.equals("(")) {
				numberOfLeftParen++;
			}
			else if (part.equals(")")) {
				numberOfRightParen++;
			}
			else if (isOperator(part)) {
				// check if the oldpart is an left paren
				if (!oldpart.equals("("))
					return false;
			}
			else {
				// check if it is a String of double value format
				if (!isDouble(part))
					return false;
			}

			oldpart = part;
		}

		if (numberOfLeftParen != numberOfRightParen)
			return false;

		return true;
	}

	private boolean isDouble(String s) {
		try {
			double d = Double.parseDouble(s);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	private boolean isOperator(String op) {
		if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))
			return true;
		return false;
	}
    
	public static void main(String[] args) {
		ArrayList<String> expressionList = new ArrayList<String>();

		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
			expressionList.add(sc.next());

		ValidateExpression ve = new ValidateExpression();
		if (ve.validate(expressionList)) {
			System.out.println("Expression is valid.");
		}
		else {
			System.out.println("Expression is of wrong format.");
		}
	}
}
