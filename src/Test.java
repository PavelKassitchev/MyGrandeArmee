import forces.Force;
import forces.Unit;
import forces.UnitType;
import nations.Nation;
import terrains.Hex;

public class Test {
    public static void main(String[] args) {
        Hex hex = new Hex();

        Unit unit1 = new Unit (Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit2 = new Unit (Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit3 = new Unit (Nation.FRANCE, UnitType.INFANTRY, hex);

        Force force1 = Unit.createCorps(unit1, unit2, unit3);

        Unit unit4 = new Unit(Nation.FRANCE, UnitType.CAVALRY, hex);
        Force force2 = new Force(unit4);

        force1.attach(force2);

        Force force3 = new Force();
        force3.attach(new Unit(Nation.AUSTRIA, UnitType.INFANTRY, hex));
        force3.attach(new Unit(Nation.PRUSSIA, UnitType.INFANTRY, hex));

        Battle battle = new Battle(force1, force3);
        System.out.println(battle.battleResolve());
    }
}
