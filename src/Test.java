import forces.Force;
import forces.Unit;
import forces.UnitType;
import nations.Nation;
import terrain.Hex;

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
        System.out.println(force1);
        System.out.println(force1.getSpeed());
        System.out.println(force2.getSpeed());
        Unit unit5 = (Unit)force1.detach(unit1);
        System.out.println(unit5.getSpeed());
        System.out.println(force1.getSpeed());
        Force force3 = force1.detach(unit2);
        System.out.println(force3.getSpeed());
        System.out.println(force1.getSpeed());
    }
}
