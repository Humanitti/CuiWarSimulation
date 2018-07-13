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
	 * 	출력 형식: (0, 1)
	 * 
	 * toAxis(1)
	 * 	출력 형식: (1, 2)
	 * 
	 * @param offset 원래 좌표의 x, y에 offset을 각각 더합니다.
	 * @return
	 * <pre/>
	 */
	public String toAxis(int offset) {
		return "(" + (x + offset) + ", " + (y + offset) + ")";
	}
	
	/**
	 * <pre>
	 * 
	 * 호출된 좌표와 매개 좌표를 비교하여 같으면 true, 다르면 false를 반환합니다.
	 * 
	 * @param axis 비교하고 싶은 좌표를 넣어주세요.
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
