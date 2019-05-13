import forces.Force;
import forces.Unit;
import forces.UnitType;
import nations.Nation;
import terrains.Hex;

import java.sql.SQLOutput;

public class Test {

    static void list(Force force1) {

        for (Force force : force1.getForces()) {
            if (force.isUnit()) {
                Unit unit = (Unit) force;
                System.out.println("Name: " + unit.getType() + " food: " + unit.getFoodStock() + " ammo: " + unit.getAmmoStock());
            }
            else {

                list(force);
            }
        }
    }

    static void tell(){

    }
    public static void main(String[] args) {
        Hex hex = new Hex();

        Unit unit1 = new Unit(Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit2 = new Unit(Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit3 = new Unit(Nation.FRANCE, UnitType.INFANTRY, hex);
        Unit unit4 = new Unit(Nation.FRANCE, UnitType.CAVALRY, hex);
        Unit unit5 = new Unit(Nation.FRANCE, UnitType.CAVALRY, hex);
        Unit unit6 = new Unit(Nation.FRANCE, UnitType.SUPPLY, hex);
        Unit unit7 = new Unit(Nation.FRANCE, UnitType.SUPPLY, hex);


        unit5.setFoodStock(0);
        unit5.setAmmoStock(0);
        unit6.setFoodStock(0);
        unit1.setFoodStock(1.0f);
        unit3.setFoodStock(0.5f);
        unit7.setFoodStock(0);
        unit2.setFoodStock(0.1f);

        Force force1 = new Force(unit1, unit2, unit7);
        Force force2 = new Force(unit3, unit4);
        force1.attach(force2);

        force1.attach(unit5);

        force1.attach(unit6);


        list(force1);
        System.out.println();
        System.out.println("Army: food - " + force1.getFoodStock() + " food need - " +
                force1.getFoodNeed() + " ammo - " + force1.getAmmoStock());
        System.out.println("2nd Corps: food - " + force2.getFoodStock() + " ammo - " + force2.getAmmoStock());
        System.out.println();

        force1.distributeSupplies(100, 0);
        list(force1);
        System.out.println();
        System.out.println("Army: food - " + force1.getFoodStock() + " food need - " +
                force1.getFoodNeed() + " ammo - " + force1.getAmmoStock());
        System.out.println("2nd Corps: food - " + force2.getFoodStock() + " ammo - " + force2.getAmmoStock());
        System.out.println();

        System.out.println();
        force1.foodToCombatants(0);

    }
}
