package warSimulation;

public class Player {
	public Civilization nation = null;
	public int num;
	public int deploy_min_x;
	public int deploy_max_x;
	public Axis king_axis;

	public Player(int num, Axis king_axis) {
		this.num = num;
		this.king_axis = king_axis;
	}

	public void setNation(Civilization nation) {
		this.nation = nation;
	}

	public String toKorNation() {
		return nation.이름;
	}

	public void setDeployX(int min, int max) {
		deploy_min_x = min;
		deploy_max_x = max;
	}

	public String playerNum() {
		if (nation == null) {
			return "플레이어" + num;
		} else {
			return "플레이어" + num + "(" + nation.이름 + ")";
		}
	}
}
