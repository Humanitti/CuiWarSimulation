package warSimulation;

import java.util.Scanner;

/**
 * Title: 전쟁 시뮬레이션 프로젝트
 * 
 * @author Jinyong Park
 *         <p/>
 * 
 *         제작시작날짜: 2018년 5월 23일 제작종료날짜: 2018년 5월 28일
 */
public class Main {
	public static Scanner input = new Scanner(System.in);
	public static boolean turn = true; // 플레이어 1이 선공, 플레이어1 true, 플레이어2 false

	public static void main(String[] args) {
		boolean gameover = false;
		boolean run = true;

		while (run) {
			System.out.println("~~~~~ 전쟁 시뮬레이션 ~~~~~");
			GameManager.selectPlayersNation(GameManager.player1);
			GameManager.selectPlayersNation(GameManager.player2);
			Board.setUnitsInBoard(GameManager.player1, GameManager.player1.king_axis);
			Board.setUnitsInBoard(GameManager.player2, GameManager.player2.king_axis);
			Board.setAllUnits();

			/*
			 * System.out.println("~~ 선택한 국가 ~~"); System.out.println("플레이어 1: " +
			 * GameManager.player1.toKorNation()); System.out.println("플레이어 2: " +
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
			System.out.println("게임을 재시작할까요?");
			do {
				System.out.print("yes or no >> ");
				restart = input.nextLine();
			} while (!(restart.equals("yes") || restart.equals("no")));
			if (restart.equals("no"))
				run = false;
			System.out.println();
		}

		System.out.println("게임을 종료합니다.");
		System.exit(0);
	}

}
