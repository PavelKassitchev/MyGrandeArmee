import forces.Force;
import terrains.Hex;

public class Battle {
    private Force part1;
    private Force part2;
    private Hex hex;


    public String battleResolve() {
        String result = "Battle between " + part1.getStrength() + " combatants and " + part2.getStrength() +
                " combatants. The result is: Victory of ";
        if (part1.getStrength() > part2.getStrength()) {
            result += "Part One";
        }
        else {
            result += "Part Two";
        }
        return result;
    }

    public Battle(Force part1, Force part2) {
        this.part1 = part1;
        this.part2 = part2;
    }
}
