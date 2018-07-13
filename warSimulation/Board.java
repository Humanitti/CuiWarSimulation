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
	 * 현재 게임 상황판을 시각적으로 그려냅니다. cmd 환경에서 비정상적으로 보일 경우 값의 수정이 요구됩니다.
	 */
	public static void showBoard() {
		for (int y = 0; y < BOARD_LENGTH; y++) {
			if (y == 0) {
				System.out.print("　│");
				for (int x = 0; x < BOARD_LENGTH; x++) {
					if (x < 9) {
						System.out.print(" " + (x + 1) + "│");
					} else {
						System.out.print((x + 1) + "│");
					}
				}
				System.out.println();
				for (int i = 0; i < 24; i++) {
					if (i % 2 == 0) {
						System.out.print("──");
					} else {
						System.out.print("┼");
					}
				}
				System.out.println();
			}
			if (y < 9) {
				System.out.print(" " + (y + 1) + "│");
			} else {
				System.out.print((y + 1) + "│");
			}

			for (int x = 0; x < BOARD_LENGTH; x++) {
				if (board[x][y] == null) {
					System.out.print("  " + "│");
				} else {
					System.out.print(board[x][y].toCode() + "│");
				}
			}
			System.out.println();
			for (int i = 0; i < 24; i++) {
				if (i % 2 == 0) {
					System.out.print("──");
				} else {
					System.out.print("┼");
				}
			}
			System.out.println();
		}
	}

	/**
	 * <pre>
	 * public static void setUnitsInBoard(Player player, Axis kingAxis)
	 * 
	 * 플레이어의 유닛을 게임판에 배치합니다.
	 * 
	 * @param player
	 *            이 플레이어의 유닛을 게임판에 배치합니다.
	 * @param kingAxis
	 *            게임판에 왕을 이 좌표에 배치합니다.
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
	 * 두 좌표 사이의 무작위 값을 가지는 배열을 생성합니다.
	 * 
	 * @param min
	 *            두 좌표 중 최소 Axis 좌표입니다.
	 * @param max
	 *            두 좌표 중 최대 Axis 좌표입니다.
	 * @param length
	 *            생성되는 배열의 크기입니다.
	 * @return length 크기를 가지는, 랜덤한 좌표값을 가지는 배열을 생성합니다.
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
	 * 시작하고 게임판을 초기화할 때만 사용됩니다. 이 때 유닛은 자신의 좌표를 알게 됩니다.
	 * 
	 * @param unit
	 *            게임판에 배치될 단일유닛입니다.
	 * @param axis
	 *            유닛을 axis 좌표에 배치합니다.
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
	 * 현재 게임 내 모든 유닛을 Unit 배열타입의 allUnits에 담습니다.
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
