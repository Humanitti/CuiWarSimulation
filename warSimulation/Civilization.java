package warSimulation;

import warSimulation.units.*;

public class Civilization {
	public String �̸� = null;
	public Unit[] units = null;
	public int infantryman_num;
	public int archer_num;
	public int cavalry_num;

	/**
	 * public Civilization(String name, int unit_num, int i, int a, int c)
	 * <p/>
	 * 
	 * ������ �����մϴ�.
	 * 
	 * @param i
	 *            ������ ���Դϴ�.
	 * @param a
	 *            �ú��� ���Դϴ�.
	 * @param c
	 *            �⺴�� ���Դϴ�.
	 */
	public Civilization(int i, int a, int c) {
		infantryman_num = i;
		archer_num = a;
		cavalry_num = c;
		units = new Unit[1 + i + a + c]; // �� ����
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
	 * public void setKorName(String �̸�)
	 * 
	 * @param �̸�
	 *            �ѱ��̸��� �����մϴ�.
	 */
	public void setKorName(String �̸�) {
		this.�̸� = �̸�;
	}

	/**
	 * public Axis[] getUnitsAxis()
	 * 
	 * @return �ش� ������ ���� ���ֵ��� ��ǥ�� ��ȯ�մϴ�.
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
	 *            ������ ���� ���ֵ鿡�� �� ��ȣ�� �˷��ݴϴ�.
	 * @param team_num
	 *            �� ��ȣ�� �÷��̾��� ��ȣ�Դϴ�.
	 * 
	 *            <pre/>
	 */
	public void setUnitTeam(Unit[] units, int team_num) {
		for (int i = 0; i < units.length; i++) {
			units[i].team = team_num;
		}
	}
}
