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
    public static void main(String[] args) {
        Hex hex = new Hex();

        Unit unit1 = new Unit (Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit2 = new Unit (Nation.FRANCE, UnitType.ARTILLERY, hex);
        Unit unit3 = new Unit (Nation.FRANCE, UnitType.INFANTRY, hex);
        Unit unit4 = new Unit (Nation.FRANCE, UnitType.CAVALRY, hex);
        Unit unit5 = new Unit (Nation.FRANCE, UnitType.CAVALRY, hex);
        Unit unit6 = new Unit (Nation.FRANCE, UnitType.SUPPLY, hex);
        Unit unit7 = new Unit (Nation.FRANCE, UnitType.SUPPLY, hex);



        unit1.setFoodStock(1.0f);
        unit3.setFoodStock(0.5f);
        unit7.setFoodStock(15);
        Force force1 = new Force(unit1, unit2, unit7);
        Force force2 = new Force(unit3, unit4);
        force1.attach(force2);
        unit5.setFoodStock(0);
        unit5.setAmmoStock(0);
        force1.attach(unit5);
        unit6.setFoodStock(0);
        force1.attach(unit6);

        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());

        list(force1);

        /*force1.redistributeSupplies();

        System.out.println();
        System.out.println("REDISTRIBUTION!");
        System.out.println();

        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());

        list(force1);

        System.out.println();
        System.out.println("Empty Wagons!");

        System.out.println("Taken: " + force1.emptyWagonFood());
        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());

        list(force1);*/

        System.out.println();
        System.out.println("NEW!");
        System.out.println();

        force1.distributeSupplies(33, 0);
        System.out.println("After distribution");
        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());

        list(force1);



        /*float food = force1.fillCombatFood();
        System.out.println();

        System.out.println("Food to fill: " + food);
        System.out.println();
        list(force1);
        System.out.println();
        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());
        System.out.println();
        force1.takeWagonFood(5.5f);
        System.out.println("Wagons filled in! Extra 60 food!");
        System.out.println();
        list(force1);
        System.out.println("Corps food: " + force1.getFoodStock() + " ammo " + force1.getAmmoStock());
        System.out.println("2nd Corps food: " + force2.getFoodStock() + " ammo " + force2.getAmmoStock());

        /*force1.redistributeSupplies();
        System.out.println("After redistribution food = " + force1.getFoodStock() + " ammo = " + force1.getAmmoStock());

        list(force1);

        force1.receiveSupply(111.0f, 29.0f);
        System.out.println("After supply food = " + force1.getFoodStock() + " ammo = " + force1.getAmmoStock());

        list(force1);

        /*Force force1 = Unit.createCorps(unit1, unit2, unit3);
        System.out.println(force1.getFoodStock() + " Food Stock");
        System.out.println(force1.getAmmoStock() + " Ammo Stock");

        System.out.println("Infantry: food " + unit3.getFoodStock() + ", ammo " + unit3.getAmmoStock());
        System.out.println("Artillery: food " + unit1.getFoodStock() + ", ammo " + unit1.getAmmoStock());
        System.out.println("Artillery: food " + unit2.getFoodStock() + ", ammo " + unit2.getAmmoStock());



        Unit unit4 = new Unit(Nation.FRANCE, UnitType.CAVALRY, hex);
        unit4.setFoodStock(0);

        Force force2 = new Force(unit4);

        force1.attach(force2);

        Force force3 = new Force();
        force3.attach(new Unit(Nation.AUSTRIA, UnitType.INFANTRY, hex));
        force3.attach(new Unit(Nation.PRUSSIA, UnitType.INFANTRY, hex));

        force1.attach(force3);

        System.out.println(force1.getFoodStock() + " Food Stock");
        System.out.println(force1.getAmmoStock() + " Ammo Stock");
        System.out.println("Cavalry: food " + unit4.getFoodStock() + ", ammo " + unit4.getAmmoStock());

        force1.redistributeSupplies();

        System.out.println(force1.getFoodStock() + " Food Stock");
        System.out.println(force1.getAmmoStock() + " Ammo Stock");
        System.out.println("Cavalry: food " + unit4.getFoodStock() + ", ammo " + unit4.getAmmoStock());
        System.out.println("Infantry: food " + unit3.getFoodStock() + ", ammo " + unit3.getAmmoStock());
        System.out.println("Artillery: food " + unit1.getFoodStock() + ", ammo " + unit1.getAmmoStock());
        System.out.println("Artillery: food " + unit2.getFoodStock() + ", ammo " + unit2.getAmmoStock());
        System.out.println("Force 2 food " + force2.getFoodStock() + ", ammo " + force2.getAmmoStock());
        System.out.println("Force 3 food " + force3.getFoodStock() + ", ammo " + force3.getAmmoStock());



        force1.detach(unit1);
        System.out.println(force1.getFoodStock() + " Food Stock");
        System.out.println(force1.getAmmoStock() + " Ammo Stock");


        force2.receiveSupply(0.2f, 0);
        System.out.println("Cav after supply: food " + force2.getFoodStock() + ", ammo " + force2.getAmmoStock());

        System.out.println("InfCorps before supply: food " + force3.getFoodStock() + ", ammo " + force3.getAmmoStock());
        for (Force force: force3.getForces()) {
            Unit unit = (Unit)force;
            System.out.println("Type: " + unit.getType() + " food: " + unit.getFoodStock() + " ammo: " + unit.getAmmoStock());
        }

        force3.receiveSupply(0.2f, 0.4f);
        System.out.println("InfCorps after supply: food " + force3.getFoodStock() + ", ammo " + force3.getAmmoStock());
        for (Force force: force3.getForces()) {
            Unit unit = (Unit)force;
            System.out.println("Type: " + unit.getType() + " food: " + unit.getFoodStock() + " ammo: " + unit.getAmmoStock());
        }*/

    }
}
