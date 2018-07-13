package warSimulation.units;

public class Infantryman extends Unit {
	public Infantryman() {
		super("º¸º´", 24, 4, 8, 8);
		code = "I";
		mod = true;
		move_len = 1;
		attack_len = 1;
	}
}
