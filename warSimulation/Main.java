package warSimulation;

import java.util.Scanner;

/**
 * Title: ���� �ùķ��̼� ������Ʈ
 * 
 * @author Jinyong Park
 *         <p/>
 * 
 *         ���۽��۳�¥: 2018�� 5�� 23�� �������ᳯ¥: 2018�� 5�� 28��
 */
public class Main {
	public static Scanner input = new Scanner(System.in);
	public static boolean turn = true; // �÷��̾� 1�� ����, �÷��̾�1 true, �÷��̾�2 false

	public static void main(String[] args) {
		boolean gameover = false;
		boolean run = true;

		while (run) {
			System.out.println("~~~~~ ���� �ùķ��̼� ~~~~~");
			GameManager.selectPlayersNation(GameManager.player1);
			GameManager.selectPlayersNation(GameManager.player2);
			Board.setUnitsInBoard(GameManager.player1, GameManager.player1.king_axis);
			Board.setUnitsInBoard(GameManager.player2, GameManager.player2.king_axis);
			Board.setAllUnits();

			/*
			 * System.out.println("~~ ������ ���� ~~"); System.out.println("�÷��̾� 1: " +
			 * GameManager.player1.toKorNation()); System.out.println("�÷��̾� 2: " +
			 * GameManager.player2.toKorNation()); System.out.println();
			 */

			while (!gameover) {
				Board.showBoard();
				if (!GameManager.playTurn()) {
					gameover = true;
				}
				turn = turn ? false : true;
			}
			String restart = null;
			System.out.println("������ ������ұ��?");
			do {
				System.out.print("yes or no >> ");
				restart = input.nextLine();
			} while (!(restart.equals("yes") || restart.equals("no")));
			if (restart.equals("no"))
				run = false;
			System.out.println();
		}

		System.out.println("������ �����մϴ�.");
		System.exit(0);
	}

}
