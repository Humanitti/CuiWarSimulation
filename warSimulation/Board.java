package warSimulation;

import java.util.Random;

import warSimulation.units.Unit;

public class Board {
	public static final int BOARD_LENGTH = 11;
	public static Unit[][] board = new Unit[BOARD_LENGTH][BOARD_LENGTH];
	public static Unit[] allUnits = null;

	/**
	 * public static void showBoard()
	 * 
	 * ���� ���� ��Ȳ���� �ð������� �׷����ϴ�. cmd ȯ�濡�� ������������ ���� ��� ���� ������ �䱸�˴ϴ�.
	 */
	public static void showBoard() {
		for (int y = 0; y < BOARD_LENGTH; y++) {
			if (y == 0) {
				System.out.print("����");
				for (int x = 0; x < BOARD_LENGTH; x++) {
					if (x < 9) {
						System.out.print(" " + (x + 1) + "��");
					} else {
						System.out.print((x + 1) + "��");
					}
				}
				System.out.println();
				for (int i = 0; i < 24; i++) {
					if (i % 2 == 0) {
						System.out.print("����");
					} else {
						System.out.print("��");
					}
				}
				System.out.println();
			}
			if (y < 9) {
				System.out.print(" " + (y + 1) + "��");
			} else {
				System.out.print((y + 1) + "��");
			}

			for (int x = 0; x < BOARD_LENGTH; x++) {
				if (board[x][y] == null) {
					System.out.print("  " + "��");
				} else {
					System.out.print(board[x][y].toCode() + "��");
				}
			}
			System.out.println();
			for (int i = 0; i < 24; i++) {
				if (i % 2 == 0) {
					System.out.print("����");
				} else {
					System.out.print("��");
				}
			}
			System.out.println();
		}
	}

	/**
	 * <pre>
	 * public static void setUnitsInBoard(Player player, Axis kingAxis)
	 * 
	 * �÷��̾��� ������ �����ǿ� ��ġ�մϴ�.
	 * 
	 * @param player
	 *            �� �÷��̾��� ������ �����ǿ� ��ġ�մϴ�.
	 * @param kingAxis
	 *            �����ǿ� ���� �� ��ǥ�� ��ġ�մϴ�.
	 * 
	 *            <pre/>
	 */
	public static void setUnitsInBoard(Player player, Axis kingAxis) {
		Unit[] units = player.nation.units;
		Axis min = new Axis(player.deploy_min_x, 0);
		Axis max = new Axis(player.deploy_max_x, 11);
		setAxisOfUnit(units[0], new Axis(kingAxis));
		Axis[] randAxis = setRandomAxis(min, max, units.length);
		for (int i = 1; i < randAxis.length; i++) {
			if (!randAxis[i].equals(units[0].axis)) {
				setAxisOfUnit(units[i], new Axis(randAxis[i].x, randAxis[i].y));
			} else {
				units = null;
				setUnitsInBoard(player, kingAxis);
				return;
			}
		}
	}

	/**
	 * <pre>
	 * private static Axis[] setRandomAxis(Axis min, Axis max, int length)
	 * 
	 * �� ��ǥ ������ ������ ���� ������ �迭�� �����մϴ�.
	 * 
	 * @param min
	 *            �� ��ǥ �� �ּ� Axis ��ǥ�Դϴ�.
	 * @param max
	 *            �� ��ǥ �� �ִ� Axis ��ǥ�Դϴ�.
	 * @param length
	 *            �����Ǵ� �迭�� ũ���Դϴ�.
	 * @return length ũ�⸦ ������, ������ ��ǥ���� ������ �迭�� �����մϴ�.
	 * 
	 *         <pre/>
	 */
	private static Axis[] setRandomAxis(Axis min, Axis max, int length) {
		Random rand = new Random();
		Axis[] buf = new Axis[(max.x - min.x) * (max.y - min.y)];
		Axis[] randAxis = new Axis[length];
		if (length > buf.length) {
			length = buf.length;
		}
		int c = 0;
		for (int y = min.y; y < max.y; y++) {
			for (int x = min.x; x < max.x; x++) {
				buf[c++] = new Axis(x, y);
			}
		}
		Integer[] randN = new Integer[buf.length];
		for (int i = 0; i < randN.length; i++) {
			randN[i] = i;
		}
		for (int i = 0; i < buf.length * 2; i++) {
			int temp;
			int randA = rand.nextInt(randN.length);
			int randB = rand.nextInt(randN.length);

			temp = randN[randA];
			randN[randA] = randN[randB];
			randN[randB] = temp;
		}
		for (int i = 0; i < length; i++) {
			randAxis[i] = buf[randN[i]];
		}
		return randAxis;
	}

	/**
	 * <pre>
	 * private static void setUnitInAxis(Unit unit, Axis a)
	 * 
	 * �����ϰ� �������� �ʱ�ȭ�� ���� ���˴ϴ�. �� �� ������ �ڽ��� ��ǥ�� �˰� �˴ϴ�.
	 * 
	 * @param unit
	 *            �����ǿ� ��ġ�� ���������Դϴ�.
	 * @param axis
	 *            ������ axis ��ǥ�� ��ġ�մϴ�.
	 * 
	 *            <pre/>
	 */
	private static void setAxisOfUnit(Unit unit, Axis axis) {
		Board.board[axis.x][axis.y] = unit;
		unit.setPos(axis);
	}

	/**
	 * <pre>
	 * public static void setAllUnits()
	 * 
	 * ���� ���� �� ��� ������ Unit �迭Ÿ���� allUnits�� ����ϴ�.
	 * 
	 * <pre/>
	 */
	public static void setAllUnits() {
		Unit[] p1Units = GameManager.player1.nation.units;
		Unit[] p2Units = GameManager.player2.nation.units;
		allUnits = new Unit[p1Units.length + p2Units.length];
		int index = 0;
		for (int i = 0; i < p1Units.length; i++) {
			allUnits[i] = p1Units[index++];
		}
		index = 0;
		for (int j = p1Units.length; j < allUnits.length; j++) {
			allUnits[j] = p2Units[index++];
		}
	}
}
