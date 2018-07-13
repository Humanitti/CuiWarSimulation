package warSimulation.units;

public class King extends Unit {
	public King() {
		super("±¹¿Õ", 15, 0, 0, 8);
		code = "K";
		mod = true;
		move_len = 1;
		attack_len = 0;
	}
}
