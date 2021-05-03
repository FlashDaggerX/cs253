import java.util.Scanner;

public class ExpressionTester {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		System.out.print("Enter a fully parametized expression: ");
		String expression = stdin.nextLine();

		Expression expr = new Expression(expression);

		System.out.print("Printing out the expression tree (preorder): ");
		expr.printPreorderTree();
		System.out.println();

		System.out.print("Printing out the expression tree (inorder): ");
		expr.printInorderTree();
		System.out.println();

		System.out.print("The Answer: ");
		System.out.println(expr.getResult());

		stdin.close();
	}
}
