public class Expression {
	private static final int
		// Used to signifiy
		OPCODE_ADD = 1 << 20,
		OPCODE_MUL = OPCODE_ADD + 1,
		OPCODE_SUB = OPCODE_ADD + 2,
		OPCODE_DIV = OPCODE_ADD + 3,
		// Used to signifiy the end / beginning of an expression.
		OPCODE_NEW_EXPR = 1 << 21,
		OPCODE_END_EXPR = OPCODE_NEW_EXPR + 1,
		// Used to signifiy that the token isn't a valid operator.
		OPCODE_ERR = 1 << 22;
	private static final char
		TOKEN_ADD = '+',
		TOKEN_SUB = '-',
		TOKEN_MUL = '*',
		TOKEN_DIV = '/',
		TOKEN_NEW_EXPR = '(',
		TOKEN_END_EXPR = ')',
		// Used when translating OPCODEs. Signifies invalid OPCODE translation.
		INVALID_TOKEN = 'E';

	public static int evaluate(String expr) {
		return new Expression(expr).getResult();
	}

	private String expression;
	private int result;
	private BinaryTree<Integer> exprtree;

	public Expression(String expr) {
		// Replace all the spaces with nothing.
		this.expression = expr.replaceAll("\\s", "");
		this.exprtree = new BinaryTree<>();

		try {
			char[] tokens = expression.toCharArray();

			// Start building the expression tree, using a default root value.
			BinaryTree<Integer>.Node root = exprtree.addRoot(OPCODE_ERR);
			buildTree(tokens, 0, root);

			this.result = solve(root, 0);
		} catch (InvalidExpressionException e) {
			System.err.println("WARNING: " + e.getMessage());
			this.result = OPCODE_ERR;
		} catch (IllegalArgumentException e) {
			System.err.println("WARNING: " + e.getMessage());
			this.result = OPCODE_ERR;
		}
	}

	public void printPreorderTree() {
		String tree = exprtree.preorder(exprtree.root());
		tree = translateOpCode(tree);

		System.out.print(tree);
	}

	public void printInorderTree() {
		String tree = exprtree.inorder(exprtree.root());
		tree = translateOpCode(tree);

		System.out.print(tree);
	}

	/**
	 * Builds the expression tree, recursively.
	 *
	 * @param tokens The expression, converted to a character array
	 * @param index The index of {@code tokens}
	 * @param noderef The current node in the tree
	 * @throws InvalidExpressionException If the expression has invalid opcodes.
	 */
	private void buildTree(char[] tokens, int index, BinaryTree<Integer>.Node noderef) {
		// If all of the tokens have been visited, return.
		if (index > tokens.length - 1)
			return;

		int opcode = tokenToOPCode(tokens[index]);

		try {
			switch (opcode) {
				// In the case of a new expression
				case OPCODE_NEW_EXPR: {
					exprtree.addLeft(noderef, OPCODE_ERR);
					buildTree(tokens, index + 1, exprtree.left(noderef));
				} break;

				case OPCODE_END_EXPR: {
					buildTree(tokens, index + 1, exprtree.parent(noderef));
				} break;

				// In the case of an operand (any number)
				case OPCODE_ERR: {
					int number = Character.getNumericValue(tokens[index]);
					exprtree.set(noderef, number);
					buildTree(tokens, index + 1, exprtree.parent(noderef));
				} break;

				// In the case of a valid operator (OPCODE_ADD, OPCODE_DIV, etc)
				default: {
					exprtree.set(noderef, opcode);
					exprtree.addRight(noderef, OPCODE_ERR);
					buildTree(tokens, index + 1, exprtree.right(noderef));
				} break;
			}
		} catch (NullPointerException e) {
			throw new InvalidExpressionException(expression, tokens[index], index + 1);
		}
	}

	/**
	 * Solves the equation by recursively traversing through the expression tree.
	 * This function will return OPCODE_ERR on a parsing error (such as an
	 * unparameterized expression).
	 *
	 * @return The result by the expression tree.
	 * @throws IllegalArgumentException If an expression wasn't fully parethesized.
	 * @throws InvalidExpressionException If the expression has extra operands.
	 */
	private int solve(BinaryTree<Integer>.Node noderef, int index) {
		if (noderef.isLeaf())
			return noderef.data;

		BinaryTree<Integer>.Node left = exprtree.left(noderef);
		BinaryTree<Integer>.Node right = exprtree.right(noderef);

		int result = OPCODE_ERR;

		try {
			result = express(noderef.data, solve(left, index + 1), solve(right, index + 1));
		} catch (NullPointerException e) {
			throw new InvalidExpressionException(expression, opcodeToToken(noderef.data), index);
		}

		if (result == OPCODE_ERR)
			throw new IllegalArgumentException("Expression not parethesized.");

		return result;
	}

	/** Converts all the opcodes in a comma-seperated string into tokens. */
	private String translateOpCode(String order) {
		String[] treearr = order.split(",");
		char rep;
		for (int i = 0; i < treearr.length - 1; i++) {
			rep = opcodeToToken(Integer.parseInt(treearr[i]));
			if (rep != INVALID_TOKEN)
				treearr[i] = Character.toString(rep);
		}

		order = "";
		for (String s : treearr) {
			order += s;
		}

		return order;
	}

	private int express(int operator, int operand1, int operand2) {
		switch (operator) {
			case OPCODE_ADD: return operand1 + operand2;
			case OPCODE_MUL: return operand1 * operand2;
			case OPCODE_DIV: return operand1 / operand2;
			case OPCODE_SUB: return operand1 - operand2;
			default: return OPCODE_ERR;
		}
	}

	/** Translates a token into one of the OPCODE constants. */
	private int tokenToOPCode(char token) {
		switch (token) {
			case TOKEN_ADD: return OPCODE_ADD;
			case TOKEN_SUB: return OPCODE_SUB;
			case TOKEN_MUL: return OPCODE_MUL;
			case TOKEN_DIV: return OPCODE_DIV;
			case TOKEN_NEW_EXPR: return OPCODE_NEW_EXPR;
			case TOKEN_END_EXPR: return OPCODE_END_EXPR;

			default: return OPCODE_ERR;
		}
	}

	/** Translates OPCODEs back to tokens. Returns 'E' if invalid. */
	private char opcodeToToken(int opcode) {
		switch (opcode) {
			case OPCODE_ADD: return TOKEN_ADD;
			case OPCODE_SUB: return TOKEN_SUB;
			case OPCODE_MUL: return TOKEN_MUL;
			case OPCODE_DIV: return TOKEN_DIV;
			default: return INVALID_TOKEN;
		}
	}

	public String getExpression() {
		return expression;
	}

	public int getResult() {
		return result;
	}
}
