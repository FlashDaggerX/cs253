public class Driver
{
	public static void main(String[] args)
	{
		DoubleStack<Integer> ds = new DoubleStack<>(10);

		ds.redPush(1);
		ds.bluePush(10);
		ds.print();
		ds.redPush(2);
		ds.bluePush(20);
		ds.print();
		System.out.println("Popping from the red stack: " + ds.redPop());
		ds.print();
		System.out.println("Popping from the blue stack: " + ds.bluePop());
		ds.print();
		ds.redPush(3);
		ds.bluePush(30);
		System.out.println("Peeking at the blue stack: " + ds.blueTop());
		ds.print();

		try {
			for (int i = 4; i < 7; i++)
			{
				ds.bluePush(i*10);
				ds.print();
				ds.redPush(i);
				ds.print();
			}
		} catch (IllegalStateException e) {
			System.out.println("Cannot add any more elements to the stack. It's full!");
		}

		System.out.println("Clearing out both stacks...");
		ds.redClear();
		ds.print();
		ds.blueClear();
		ds.print();
	}
}
