package warSimulation.units;

public class Archer extends Unit {
	public Archer() {
		super("�ú�", 12, 3, 16, 8);
		code = "A";
		mod = false;
		move_len = 2;
		attack_len = 4;
	}
}
