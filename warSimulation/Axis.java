package warSimulation;

public class Axis {
	// Field
	public int x = 0;
	public int y = 0;
	
	// Constructor
	public Axis(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Axis(Axis a) {
		this.x = a.x;
		this.y = a.y;
	}
	
	/**
	 * <pre>
	 * public String toAxis(int offset)
	 * 
	 * toAxis(0)
	 * 	��� ����: (0, 1)
	 * 
	 * toAxis(1)
	 * 	��� ����: (1, 2)
	 * 
	 * @param offset ���� ��ǥ�� x, y�� offset�� ���� ���մϴ�.
	 * @return
	 * <pre/>
	 */
	public String toAxis(int offset) {
		return "(" + (x + offset) + ", " + (y + offset) + ")";
	}
	
	/**
	 * <pre>
	 * 
	 * ȣ��� ��ǥ�� �Ű� ��ǥ�� ���Ͽ� ������ true, �ٸ��� false�� ��ȯ�մϴ�.
	 * 
	 * @param axis ���ϰ� ���� ��ǥ�� �־��ּ���.
	 * @return
	 * <pre/>
	 */
	public boolean equals(Axis axis) {
		return this.x == axis.x && this.y == axis.y;
	}
	
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
}
