package warSimulation.units;

import warSimulation.Axis;

/**
 * 
 * @author Jinyong Park
 *
 */

public class Unit {
	// Field
	public String korName;
	public String code;
	public int team;
	public final int HP;
	public int hp;
	public int damage;
	protected int move_len;
	protected int attack_len;
	protected boolean mod; // true �����, false ������
	public int move_range;
	public Axis[] move_axis = null;
	public int attack_range;
	public Axis[] attack_axis = null;
	public Axis axis = null;

	// Constructor
	public Unit(String korName, int hp, int damage, int attack_range, int move_range) {
		this.korName = korName;
		this.HP = hp;
		this.hp = hp;
		this.damage = damage;
		this.attack_range = attack_range;
		this.move_range = move_range;
		attack_axis = new Axis[attack_range];
		move_axis = new Axis[move_range];
	}

	// Method
	public void setPos(Axis axis) {
		this.axis = axis;
		setMove_axis();
		setAttack_axis();
	}
	
	public void setPos(int x, int y) {
		axis.x = x;
		axis.y = y;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * public void setMove_axis()
	 * <p/>
	 * �̵� ������ ��ǥ�迭�� �����մϴ�.
	 */
	public void setMove_axis() {
		int x, y, i = 0;
		Axis tempAxis = null;

		if (mod) {
			if (axis != null) {
				for (y = axis.y - move_len; y <= axis.y + move_len; y++) {
					for (x = axis.x - move_len; x <= axis.x + move_len; x++) {
						tempAxis = new Axis(x, y);
						if (!axis.equals(tempAxis)) {
							move_axis[i++] = tempAxis;
						}
					}
				}
			} else {
				return;
			}
		} else {
			if (axis != null) {
				for (y = axis.y - move_len; y <= axis.y + move_len; y++) {
					x = axis.x;
					tempAxis = new Axis(x, y);
					if (!axis.equals(tempAxis)) {
						move_axis[i++] = tempAxis;
					}
				}
				for (x = axis.x - move_len; x <= axis.x + move_len; x++) {
					y = axis.y;
					tempAxis = new Axis(x, y);
					if (!axis.equals(tempAxis)) {
						move_axis[i++] = tempAxis;
					}
				}
			} else {
				return;
			}
		}
	}

	/**
	 * public void setAttack_axis()
	 * <p/>
	 * 
	 * ���� ������ ��ǥ�迭�� �����մϴ�.
	 */
	public void setAttack_axis() {
		int x, y, i = 0;
		Axis tempAxis = null;

		if (mod) {
			if (axis != null) {
				for (y = axis.y - attack_len; y <= axis.y + attack_len; y++) {
					for (x = axis.x - attack_len; x <= axis.x + attack_len; x++) {
						tempAxis = new Axis(x, y);
						if (!axis.equals(tempAxis)) {
							attack_axis[i++] = tempAxis;
						}
					}
				}
			} else {
				return;
			}
		} else {
			if (axis != null) {
				for (y = axis.y - attack_len; y <= axis.y + attack_len; y++) {
					x = axis.x;
					tempAxis = new Axis(x, y);
					if (!axis.equals(tempAxis)) {
						attack_axis[i++] = tempAxis;
					}
				}
				for (x = axis.x - attack_len; x <= axis.x + attack_len; x++) {
					y = axis.y;
					tempAxis = new Axis(x, y);
					if (!axis.equals(tempAxis)) {
						attack_axis[i++] = tempAxis;
					}
				}
			} else {
				return;
			}
		}

	}

	/**
	 * <pre>
	 * public Unit callByAxis(Axis axis)
	 * 
	 * @param axis
	 *            ���� ������ ��ǥ�� ���մϴ�.
	 * @return �����ϸ� ���� ������, �ٸ��� null�� ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	public Unit callByAxis(Axis axis) {
		if (this.axis.equals(axis)) {
			return this;
		}
		return null;
	}

	/**
	 * <pre>
	 * public boolean attack(Unit enemy)
	 * 
	 * ���� �����մϴ�.
	 * 
	 * @param enemy
	 *            �� ������ ������ �̴ϴ�.
	 * @return ���� ������ ture, ��Ƴ������� false�� ��ȯ�մϴ�.
	 * 
	 *         <pre/>
	 */
	public boolean attack(Unit enemy) {

		enemy.hp -= damage;

		if (enemy.hp <= 0) {
			enemy.hp = 0;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * public String toKorName()
	 * 
	 * @return �ѱ��̸��� ��ȯ�մϴ�.
	 */
	public String toKorName() {
		return korName;
	}

	public String toHp() {
		return hp + "/" + HP;
	}

	/**
	 * <pre>
	 * public String toCode()
	 * 
	 * �������: K1
	 * 
	 * @return �����ǿ� ǥ���� ������ �ڵ�����Դϴ�.
	 * 
	 *         <pre/>
	 */
	public String toCode() {
		return code + team;
	}
}
