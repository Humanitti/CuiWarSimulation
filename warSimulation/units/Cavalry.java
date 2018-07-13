package warSimulation.units;

public class Cavalry extends Unit {
	public Cavalry() {
		super("±âº´", 15, 6, 8, 48);
		code = "C";
		mod = true;
		move_len = 3;
		attack_len = 1;
	}
}
