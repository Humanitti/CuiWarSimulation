
package warSimulation;

import java.util.Scanner;

import warSimulation.units.King;
import warSimulation.units.Unit;

/**
 * 게임의 진행을 담당합니다.
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
	 * 매개변수는 Player 클래스 타입의 변수입니다.
	 * 
	 * 플레이어의 국가를 선택합니다. 생성된 국가 객체는 국가에 소속될 유닛 객체를 생성합니다.
	 * 
	 * @param player
	 */
	public static void selectPlayersNation(Player player) {
		player1.setDeployX(0, 4);
		player2.setDeployX(7, 10);

		String ch = null;
		System.out.println(player.playerNum() + "의 국가를 선택해주세요.");
		System.out.println("1. 한국 (보병 2, 궁병 5, 기병 2)");
		System.out.println("2. 일본 (보병 5, 궁병 3, 기병 2)");
		do {
			System.out.print("선택>> ");
			ch = input.nextLine();
			if (ch.equals("1")) {
				System.out.println(player.playerNum() + "은/는 한국을 선택했습니다.\n");
				player.setNation(new Civilization(2, 5, 2));
				player.nation.setKorName("한국");
			} else if (ch.equals("2")) {
				System.out.println(player.playerNum() + "은/는 일본을 선택했습니다.\n");
				player.setNation(new Civilization(5, 3, 2));
				player.nation.setKorName("일본");
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
	 * 게임의 진행을 담당합니다.
	 * 
	 * @param player
	 *            이 플레이어가 게임의 차례입니다.
	 * @return 상대측의 왕이 죽으면 false가 반환됩니다.
	 * 
	 *         <pre/>
	 */
	public static boolean playTurn() {
		Player player = Main.turn ? player1 : player2;
		System.out.println(player.playerNum() + "의 차례입니다.\n");
		Unit unit = null;
		Axis[] attackableAxis = null;
		Axis[] movableAxis = null;
		do {
			unit = selectUnit();
			if (unit == null) {
				System.out.println(player.playerNum() + "은 차례를 넘겼습니다.\n");
				return true;
			}
			System.out
					.println(player.playerNum() + "은 " + unit.axis.toAxis(1) + "의 " + unit.toKorName() + "을 선택했습니다.\n");
			attackableAxis = checkAttackable(unit);
			movableAxis = checkMovable(unit);
			if (attackableAxis == null && movableAxis == null)
				System.out.println("선택한 유닛은 할 수 있는 것이 아무것도 없습니다.");
			if (attackableAxis == null)
				System.out.println("공격 가능한 적 유닛 없음");
			if (movableAxis == null)
				System.out.println("이동 불가능");
		} while (attackableAxis == null && movableAxis == null);

		if (attackableAxis != null) {
			System.out.println("공격 가능한 적의 좌표");
			for (int i = 0; i < attackableAxis.length; i++) {
				if (attackableAxis[i] != null) {
					Unit enemyUnit = searchUnitByAxis(attackableAxis[i]);
					System.out.println(enemyUnit.axis.toAxis(1) + " 위치의 " + enemyUnit.toKorName());
				}
			}
		}
		if (attackableAxis == null) {
			System.out.print("이동할 좌표를 입력해주세요.");
		} else if (movableAxis == null) {
			System.out.print("공격할 좌표를 입력해주세요.");
		} else {
			System.out.print("이동/공격할 좌표를 입력해주세요.");
		}
		System.out.println("(0, 0) 입력은 차례를 넘깁니다.");
		Axis inputAxis = getAxis();
		if (inputAxis.equals(-1, -1)) {
			System.out.println(player.playerNum() + "은 차례를 넘겼습니다.\n");
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
	 * x, y 좌표를 사용자로부터 입력받습니다. (0, 0)을 입력받고 반환할 수 있습니다.
	 * 
	 * [출력형식] x좌표 입력: y좌표 입력:
	 * 
	 * @return x, y 좌표를 Axis 타입으로 반환합니다.
	 * 
	 *         <pre/>
	 */
	private static Axis getAxis() {
		String tpx = null, tpy = null;
		int x = 0, y = 0;
		do {
			System.out.print("x좌표 입력: ");
			tpx = input.nextLine();
			System.out.print("y좌표 입력: ");
			tpy = input.nextLine();
			try {
				x = Integer.parseInt(tpx);
				y = Integer.parseInt(tpy);
				if (x == 0 && y == 0)
					return new Axis(--x, --y);
			} catch (Exception e) {
				System.out.println("올바른 숫자를 입력해주세요.");
				return getAxis();
			}
			if (!((x > 0 && x <= Board.BOARD_LENGTH) && (y > 0 && y <= Board.BOARD_LENGTH))) {
				System.out.println("게임판을 벗어난 좌표입니다.");
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
	 *            현재 선택된 유닛입니다.
	 * @param attackableAxis
	 *            현재 선택된 유닛의 공격범위 내에 존재하는 적의 좌표목록입니다.
	 * @param inputAxis
	 *            사용자가 입력한 좌표입니다.
	 * @return 왕이 죽었으면 true, 왕이 살아있으면 false를 반환합니다.
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
				System.out.println(player.playerNum() + "의 " + unit.toKorName() + "이 " + enemyUnit.toKorName()
						+ enemyUnit.axis.toAxis(1) + "에게 " + unit.damage + "의 피해를 주었습니다.");
				System.out.println(enemyUnit.toKorName() + enemyUnit.axis.toAxis(1) + "의 체력: " + enemyUnit.toHp());
				System.out.println();
				if (iskill) {
					Board.board[enemyUnit.axis.x][enemyUnit.axis.y] = null;
					enemyUnit.setPos(Board.BOARD_LENGTH, Board.BOARD_LENGTH);
					System.out.println(enemy.playerNum() + "의 유닛이 상대" + enemyUnit.toKorName() + " 처치!!");
					if (enemyUnit instanceof King) {
						System.out.println("\n~~~" + player.playerNum() + "의 승리~~~\n");
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
	 *            현재 선택된 유닛입니다.
	 * @param movableAxis
	 *            현재 선택된 유닛이 움직일 수 있는 범위입니다.
	 * @param inputAxis
	 *            사용자가 입력한 좌표입니다.
	 * @return 재귀용
	 * 
	 *         <pre/>
	 */
	private static boolean moveAction(Unit unit, Axis[] movableAxis, Axis inputAxis) {
		for (int i = 0; i < movableAxis.length; i++) {
			if (movableAxis[i] != null && movableAxis[i].equals(inputAxis)) {
				System.out.println(unit.axis.toAxis(1) + "위치에서 " + inputAxis.toAxis(1) + "위치로 이동했습니다.");
				Board.board[unit.axis.x][unit.axis.y] = null;
				unit.setPos(movableAxis[i]);
				Board.board[unit.axis.x][unit.axis.y] = unit;
				return true;
			}
		}
		System.out.println("이동 범위를 벗어났습니다. 다시 입력해주세요.");
		Axis re_inputAxis = getAxis();
		return moveAction(unit, movableAxis, re_inputAxis);
	}

	/**
	 * private static int selectUnit()
	 * 
	 * @return 행동을 취할 유닛의 좌표를 입력받고 그 좌표에 있는 유닛을 반환합니다.
	 */
	private static Unit selectUnit() {
		System.out.println("행동할 유닛의 좌표를 알려주세요. (0, 0) 입력은 차례를 넘깁니다.");
		Axis inputAxis = getAxis();
		if (inputAxis.equals(-1, -1))
			return null;
		Unit unit = searchUnitByAxis(inputAxis);
		if (unit == null) {
			System.out.println("해당 좌표에 유닛이 존재하지 않습니다. 다시 입력해주세요.\n");
			return selectUnit();
		}
		Player enemy = Main.turn ? player2 : player1;
		for (int i = 0; i < enemy.nation.units.length; i++) {
			if (enemy.nation.units[i] == null)
				continue;
			if (unit.axis.equals(enemy.nation.units[i].axis)) {
				System.out.println("해당 좌표는 상대 플레이어의 유닛입니다. 다시 입력해주세요.\n");
				return selectUnit();
			}
		}
		return unit;
	}

	/**
	 * <pre>
	 * private static Unit searchUnitByAxis(Axis axis)
	 * 
	 * 모든 유닛의 위치를 참조하여 매개값의 좌표와 동일한 해당 유닛을 찾습니다.
	 * 
	 * @param axis
	 *            이 좌표를 가지고 있는 유닛을 반환할 겁니다.
	 * @return 만약 존재하지 않다면 null을 반환합니다.
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
	 * 해당 유닛이 이동 가능한 좌표 배열을 반환합니다. 이동이 불가능한 좌표를 가진 배열의 원소라면 null을 참조합니다. 이동이 아예 불가능할
	 * 경우 배열은 전부 null을 참조하므로 유의합시다.
	 * 
	 * @param unit
	 *            유닛의 move_axis배열을 참조하여 이동 가능한 좌표를 참조합니다.
	 * @return 이동 가능한 좌표만을 저장하고 좌표 목록을 Axis 클래스 배열 타입으로 반환합니다.
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
	 * 해당 유닛의 공격 가능한 범위 내에 적이 존재하는 좌표를 반환합니다. 공격 가능한 적이 없다면 null이 반환됩니다.
	 * 
	 * @param unit
	 *            유닛의 attack_axis를 참조하여 적이 공격 좌표 내부에 존재하는지 확인합니다.
	 * @return 공격가능한 적의 좌표를 Axis 클래스 배열 타입으로 반환합니다.
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
	 * 테스트용
	 * 
	 * @param player
	 */
	public static void showUnitsState(Player player) {
		System.out.println();
		System.out.println(player.playerNum() + "의 정보를 불러옵니다.");
		System.out.println("보유한 유닛의 수: " + player.nation.units.length);
		System.out.println("보유한 유닛의 종류(좌표)");
		for (Unit i : player.nation.units) {
			System.out.println(i.korName + ". " + i.axis.toAxis(0));
		}
	}
}
