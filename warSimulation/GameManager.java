
package warSimulation;

import java.util.Scanner;

import warSimulation.units.King;
import warSimulation.units.Unit;

/**
 * ������ ������ ����մϴ�.
 * 
 * @author Jinyong Park
 *
 */

public class GameManager {
	public static Scanner input = new Scanner(System.in);
	public static Player player1 = new Player(1, new Axis(0, 4));
	public static Player player2 = new Player(2, new Axis(10, 4));

	/**
	 * public static void selectPlayersNation(Player player)
	 * 
	 * �Ű������� Player Ŭ���� Ÿ���� �����Դϴ�.
	 * 
	 * �÷��̾��� ������ �����մϴ�. ������ ���� ��ü�� ������ �Ҽӵ� ���� ��ü�� �����մϴ�.
	 * 
	 * @param player
	 */
	public static void selectPlayersNation(Player player) {
		player1.setDeployX(0, 4);
		player2.setDeployX(7, 10);

		String ch = null;
		System.out.println(player.playerNum() + "�� ������ �������ּ���.");
		System.out.println("1. �ѱ� (���� 2, �ú� 5, �⺴ 2)");
		System.out.println("2. �Ϻ� (���� 5, �ú� 3, �⺴ 2)");
		do {
			System.out.print("����>> ");
			ch = input.nextLine();
			if (ch.equals("1")) {
				System.out.println(player.playerNum() + "��/�� �ѱ��� �����߽��ϴ�.\n");
				player.setNation(new Civilization(2, 5, 2));
				player.nation.setKorName("�ѱ�");
			} else if (ch.equals("2")) {
				System.out.println(player.playerNum() + "��/�� �Ϻ��� �����߽��ϴ�.\n");
				player.setNation(new Civilization(5, 3, 2));
				player.nation.setKorName("�Ϻ�");
			}
			try {
				player.nation.setUnitTeam(player.nation.units, player.num);
			} catch (NullPointerException e) {
				continue;
			}
		} while (!(ch.equals("1") || ch.equals("2")));
	}

	/**
	 * <pre>
	 * public static void playTurn(Player player)
	 * 
	 * ������ ������ ����մϴ�.
	 * 
	 * @param player
	 *            �� �÷��̾ ������ �����Դϴ�.
	 * @return ������� ���� ������ false�� ��ȯ�˴ϴ�.
	 * 
	 *         <pre/>
	 */
	public static boolean playTurn() {
		Player player = Main.turn ? player1 : player2;
		System.out.println(player.playerNum() + "�� �����Դϴ�.\n");
		Unit unit = null;
		Axis[] attackableAxis = null;
		Axis[] movableAxis = null;
		do {
			unit = selectUnit();
			if (unit == null) {
				System.out.println(player.playerNum() + "�� ���ʸ� �Ѱ���ϴ�.\n");
				return true;
			}
			System.out
					.println(player.playerNum() + "�� " + unit.axis.toAxis(1) + "�� " + unit.toKorName() + "�� �����߽��ϴ�.\n");
			attackableAxis = checkAttackable(unit);
			movableAxis = checkMovable(unit);
			if (attackableAxis == null && movableAxis == null)
				System.out.println("������ ������ �� �� �ִ� ���� �ƹ��͵� �����ϴ�.");
			if (attackableAxis == null)
				System.out.println("���� ������ �� ���� ����");
			if (movableAxis == null)
				System.out.println("�̵� �Ұ���");
		} while (attackableAxis == null && movableAxis == null);

		if (attackableAxis != null) {
			System.out.println("���� ������ ���� ��ǥ");
			for (int i = 0; i < attackableAxis.length; i++) {
				if (attackableAxis[i] != null) {
					Unit enemyUnit = searchUnitByAxis(attackableAxis[i]);
					System.out.println(enemyUnit.axis.toAxis(1) + " ��ġ�� " + enemyUnit.toKorName());
				}
			}
		}
		if (attackableAxis == null) {
			System.out.print("�̵��� ��ǥ�� �Է����ּ���.");
		} else if (movableAxis == null) {
			System.out.print("������ ��ǥ�� �Է����ּ���.");
		} else {
			System.out.print("�̵�/������ ��ǥ�� �Է����ּ���.");
		}
		System.out.println("(0, 0) �Է��� ���ʸ� �ѱ�ϴ�.");
		Axis inputAxis = getAxis();
		if (inputAxis.equals(-1, -1)) {
			System.out.println(player.playerNum() + "�� ���ʸ� �Ѱ���ϴ�.\n");
			return true;
		}
		if (attackableAxis != null && Board.board[inputAxis.x][inputAxis.y] != null) {
			return attackAction(unit, attackableAxis, inputAxis);
		} else if (movableAxis != null && Board.board[inputAxis.x][inputAxis.y] == null) {
			moveAction(unit, movableAxis, inputAxis);
		}
		System.out.println();
		return true;
	}

	/**
	 * <pre>
	 * private static Axis getAxis()
	 * 
	 * x, y ��ǥ�� ����ڷκ��� �Է¹޽��ϴ�. (0, 0)�� �Է¹ް� ��ȯ�� �� �ֽ��ϴ�.
	 * 
	 * [�������] x��ǥ �Է�: y��ǥ �Է�:
	 * 
	 * @return x, y ��ǥ�� Axis Ÿ������ ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	private static Axis getAxis() {
		String tpx = null, tpy = null;
		int x = 0, y = 0;
		do {
			System.out.print("x��ǥ �Է�: ");
			tpx = input.nextLine();
			System.out.print("y��ǥ �Է�: ");
			tpy = input.nextLine();
			try {
				x = Integer.parseInt(tpx);
				y = Integer.parseInt(tpy);
				if (x == 0 && y == 0)
					return new Axis(--x, --y);
			} catch (Exception e) {
				System.out.println("�ùٸ� ���ڸ� �Է����ּ���.");
				return getAxis();
			}
			if (!((x > 0 && x <= Board.BOARD_LENGTH) && (y > 0 && y <= Board.BOARD_LENGTH))) {
				System.out.println("�������� ��� ��ǥ�Դϴ�.");
				continue;
			}
			break;
		} while (true);

		return new Axis(x - 1, y - 1);
	}

	/**
	 * <pre>
	 * private static boolean attackAction(Unit unit, Axis[] attackableAxis, Axis
	 * inputAxis)
	 * 
	 * @param unit
	 *            ���� ���õ� �����Դϴ�.
	 * @param attackableAxis
	 *            ���� ���õ� ������ ���ݹ��� ���� �����ϴ� ���� ��ǥ����Դϴ�.
	 * @param inputAxis
	 *            ����ڰ� �Է��� ��ǥ�Դϴ�.
	 * @return ���� �׾����� true, ���� ��������� false�� ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	private static boolean attackAction(Unit unit, Axis[] attackableAxis, Axis inputAxis) {
		Player player = Main.turn ? player1 : player2;
		Player enemy = Main.turn ? player2 : player1;
		for (int i = 0; i < attackableAxis.length; i++) {
			if (attackableAxis[i] != null && attackableAxis[i].equals(inputAxis)) {
				Unit enemyUnit = searchUnitByAxis(attackableAxis[i]);
				boolean iskill = unit.attack(enemyUnit);
				System.out.println(player.playerNum() + "�� " + unit.toKorName() + "�� " + enemyUnit.toKorName()
						+ enemyUnit.axis.toAxis(1) + "���� " + unit.damage + "�� ���ظ� �־����ϴ�.");
				System.out.println(enemyUnit.toKorName() + enemyUnit.axis.toAxis(1) + "�� ü��: " + enemyUnit.toHp());
				System.out.println();
				if (iskill) {
					Board.board[enemyUnit.axis.x][enemyUnit.axis.y] = null;
					enemyUnit.setPos(Board.BOARD_LENGTH, Board.BOARD_LENGTH);
					System.out.println(enemy.playerNum() + "�� ������ ���" + enemyUnit.toKorName() + " óġ!!");
					if (enemyUnit instanceof King) {
						System.out.println("\n~~~" + player.playerNum() + "�� �¸�~~~\n");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * <pre>
	 * private static boolean moveAction(Unit unit, Axis[] movableAxis, Axis
	 * inputAxis)
	 * 
	 * @param unit
	 *            ���� ���õ� �����Դϴ�.
	 * @param movableAxis
	 *            ���� ���õ� ������ ������ �� �ִ� �����Դϴ�.
	 * @param inputAxis
	 *            ����ڰ� �Է��� ��ǥ�Դϴ�.
	 * @return ��Ϳ�
	 * 
	 *         <pre/>
	 */
	private static boolean moveAction(Unit unit, Axis[] movableAxis, Axis inputAxis) {
		for (int i = 0; i < movableAxis.length; i++) {
			if (movableAxis[i] != null && movableAxis[i].equals(inputAxis)) {
				System.out.println(unit.axis.toAxis(1) + "��ġ���� " + inputAxis.toAxis(1) + "��ġ�� �̵��߽��ϴ�.");
				Board.board[unit.axis.x][unit.axis.y] = null;
				unit.setPos(movableAxis[i]);
				Board.board[unit.axis.x][unit.axis.y] = unit;
				return true;
			}
		}
		System.out.println("�̵� ������ ������ϴ�. �ٽ� �Է����ּ���.");
		Axis re_inputAxis = getAxis();
		return moveAction(unit, movableAxis, re_inputAxis);
	}

	/**
	 * private static int selectUnit()
	 * 
	 * @return �ൿ�� ���� ������ ��ǥ�� �Է¹ް� �� ��ǥ�� �ִ� ������ ��ȯ�մϴ�.
	 */
	private static Unit selectUnit() {
		System.out.println("�ൿ�� ������ ��ǥ�� �˷��ּ���. (0, 0) �Է��� ���ʸ� �ѱ�ϴ�.");
		Axis inputAxis = getAxis();
		if (inputAxis.equals(-1, -1))
			return null;
		Unit unit = searchUnitByAxis(inputAxis);
		if (unit == null) {
			System.out.println("�ش� ��ǥ�� ������ �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.\n");
			return selectUnit();
		}
		Player enemy = Main.turn ? player2 : player1;
		for (int i = 0; i < enemy.nation.units.length; i++) {
			if (enemy.nation.units[i] == null)
				continue;
			if (unit.axis.equals(enemy.nation.units[i].axis)) {
				System.out.println("�ش� ��ǥ�� ��� �÷��̾��� �����Դϴ�. �ٽ� �Է����ּ���.\n");
				return selectUnit();
			}
		}
		return unit;
	}

	/**
	 * <pre>
	 * private static Unit searchUnitByAxis(Axis axis)
	 * 
	 * ��� ������ ��ġ�� �����Ͽ� �Ű����� ��ǥ�� ������ �ش� ������ ã���ϴ�.
	 * 
	 * @param axis
	 *            �� ��ǥ�� ������ �ִ� ������ ��ȯ�� �̴ϴ�.
	 * @return ���� �������� �ʴٸ� null�� ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	private static Unit searchUnitByAxis(Axis axis) {
		for (int i = 0; i < Board.allUnits.length; i++) {
			Unit unit = Board.allUnits[i].callByAxis(axis);
			if (unit != null) {
				return unit;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * private static Axis[] checkMovable(Unit unit)
	 * 
	 * �ش� ������ �̵� ������ ��ǥ �迭�� ��ȯ�մϴ�. �̵��� �Ұ����� ��ǥ�� ���� �迭�� ���Ҷ�� null�� �����մϴ�. �̵��� �ƿ� �Ұ�����
	 * ��� �迭�� ���� null�� �����ϹǷ� �����սô�.
	 * 
	 * @param unit
	 *            ������ move_axis�迭�� �����Ͽ� �̵� ������ ��ǥ�� �����մϴ�.
	 * @return �̵� ������ ��ǥ���� �����ϰ� ��ǥ ����� Axis Ŭ���� �迭 Ÿ������ ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	private static Axis[] checkMovable(Unit unit) {
		Axis[] movableAxis = new Axis[unit.move_range];
		// unit.setMove_axis();
		for (int i = 0; i < unit.move_axis.length; i++) {
			movableAxis[i] = unit.move_axis[i];
		}
		for (int i = 0; i < Board.allUnits.length; i++) {
			for (int j = 0; j < movableAxis.length; j++) {
				if (Board.allUnits[i].axis.equals(unit.move_axis[j])) {
					movableAxis[j] = null;
				}
				if (unit.move_axis[j].x < 0 || unit.move_axis[j].x > 10) {
					movableAxis[j] = null;
				}
				if (unit.move_axis[j].y < 0 || unit.move_axis[j].y > 10) {
					movableAxis[j] = null;
				}
			}
		}
		return movableAxis;
	}

	/**
	 * <pre>
	 * private static Axis checkAttackable(Unit unit)
	 * 
	 * �ش� ������ ���� ������ ���� ���� ���� �����ϴ� ��ǥ�� ��ȯ�մϴ�. ���� ������ ���� ���ٸ� null�� ��ȯ�˴ϴ�.
	 * 
	 * @param unit
	 *            ������ attack_axis�� �����Ͽ� ���� ���� ��ǥ ���ο� �����ϴ��� Ȯ���մϴ�.
	 * @return ���ݰ����� ���� ��ǥ�� Axis Ŭ���� �迭 Ÿ������ ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	private static Axis[] checkAttackable(Unit unit) {
		Player enemy = Main.turn ? player2 : player1;
		// unit.setAttack_axis();
		Axis[] attackableAxis = new Axis[unit.attack_range];
		Axis[] enemyAxis = enemy.nation.getUnitsAxis();

		int index = 0;
		for (int i = 0; i < unit.attack_axis.length; i++) {
			for (int j = 0; j < enemyAxis.length; j++) {
				if (unit.attack_axis[i].equals(enemyAxis[j])) {
					attackableAxis[index++] = enemyAxis[j];
				} else {
					attackableAxis[i] = null;
				}
			}
		}
		if (index == 0)
			return null;
		return attackableAxis;
	}

	/**
	 * public static void showUnitsState(Player player)
	 * <p>
	 * 
	 * �׽�Ʈ��
	 * 
	 * @param player
	 */
	public static void showUnitsState(Player player) {
		System.out.println();
		System.out.println(player.playerNum() + "�� ������ �ҷ��ɴϴ�.");
		System.out.println("������ ������ ��: " + player.nation.units.length);
		System.out.println("������ ������ ����(��ǥ)");
		for (Unit i : player.nation.units) {
			System.out.println(i.korName + ". " + i.axis.toAxis(0));
		}
	}
}
