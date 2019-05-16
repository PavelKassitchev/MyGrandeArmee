package forces;

import forces.Force;
import forces.Unit;
import forces.UnitType;
import nations.Nation;
import terrains.Hex;

import java.sql.SQLOutput;

public class Test {

    static void list(Force force1) {

        for (Force force : force1.forces) {
            if (force.isUnit) {
                Unit unit = (Unit) force;
                System.out.println("Name: " + unit.getType() + " food: " + unit.foodStock + " ammo: "
                        + unit.ammoStock);
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


        unit5.foodStock =0;
        unit5.ammoStock = 0;
        unit6.foodStock = 0;
        unit1.foodStock = 1.0f;
        unit3.foodStock = 0.5f;
        unit7.foodStock = 1;
        unit2.foodStock = 0.1f;

        Force force1 = new Force(unit1, unit2, unit7);
        Force force2 = new Force(unit3, unit4);
        Force force3 = new Force(unit5);
        force2.attach(force3);
        force1.attach(force2);


        force1.attach(unit6);


        list(force1);
        System.out.println();
        System.out.println("Army: food - " + force1.foodStock + " food need - " +
                force1.foodNeed + " food limit without wagons: " + (force1.foodLimit - force1.wagons * 25) + " ammo - " + force1.ammoStock);
        System.out.println("2nd Corps: food - " + force2.foodStock + " ammo - " + force2.ammoStock);
        System.out.println();

        //force1.distributeSupplies(0f, 0);
        force1.distributeFood(65);
        list(force1);
        System.out.println();
        System.out.println("Army: food - " + force1.foodStock + " food need - " +
                force1.foodNeed + " food limit without wagons: " + (force1.foodLimit - force1.wagons * 25) + " ammo - " + force1.ammoStock);
        System.out.println("2nd Corps: food - " + force2.foodStock + " ammo - " + force2.ammoStock);
        System.out.println();

        System.out.println();


        System.out.println(force1.collectFromUnits(UnitType.SUPPLY, UnitType.INFANTRY, UnitType.CAVALRY,
                UnitType.ARTILLERY));

    }
}
