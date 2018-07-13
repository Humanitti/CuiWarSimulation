package warSimulation;

import warSimulation.units.*;

public class Civilization {
	public String 이름 = null;
	public Unit[] units = null;
	public int infantryman_num;
	public int archer_num;
	public int cavalry_num;

	/**
	 * public Civilization(String name, int unit_num, int i, int a, int c)
	 * <p/>
	 * 
	 * 국가를 생성합니다.
	 * 
	 * @param i
	 *            보병의 수입니다.
	 * @param a
	 *            궁병의 수입니다.
	 * @param c
	 *            기병의 수입니다.
	 */
	public Civilization(int i, int a, int c) {
		infantryman_num = i;
		archer_num = a;
		cavalry_num = c;
		units = new Unit[1 + i + a + c]; // 왕 포함
		units[0] = new King();
		for (int k = 1; k < units.length; k++) {
			if (k <= i) {
				units[k] = new Infantryman();
			} else if (k <= i + a) {
				units[k] = new Archer();
			} else if (k <= i + a + c) {
				units[k] = new Cavalry();
			}
		}
	}

	/**
	 * public void setKorName(String 이름)
	 * 
	 * @param 이름
	 *            한글이름을 설정합니다.
	 */
	public void setKorName(String 이름) {
		this.이름 = 이름;
	}

	/**
	 * public Axis[] getUnitsAxis()
	 * 
	 * @return 해당 국가에 속한 유닛들의 좌표를 반환합니다.
	 */
	public Axis[] getUnitsAxis() {
		Axis[] axis = new Axis[units.length];

		for (int i = 0; i < axis.length; i++) {
			axis[i] = units[i].axis;
		}
		return axis;
	}

	/**
	 * public boolean isKing(King king)
	 * 
	 * @param king
	 * @return
	 */
	public boolean isKing(King king) {
		if (king.hp <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * <pre>
	 * public void setUnitTeam(Unit[] units, int team_num)
	 * 
	 * @param units
	 *            국가에 속한 유닛들에게 팀 번호를 알려줍니다.
	 * @param team_num
	 *            팀 번호는 플레이어의 번호입니다.
	 * 
	 *            <pre/>
	 */
	public void setUnitTeam(Unit[] units, int team_num) {
		for (int i = 0; i < units.length; i++) {
			units[i].team = team_num;
		}
	}
}
