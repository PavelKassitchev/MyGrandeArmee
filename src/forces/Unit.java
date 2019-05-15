
package forces;

import nations.Nation;
import terrains.Hex;

public class Unit extends Force{

    private UnitType type;

    public Unit(Nation nation, UnitType type, Hex hex) {
        super();
        this.nation = nation;
        setType(type);
        isUnit = true;
        if (type == UnitType.SUPPLY) {
            isSupply = true;
            wagons = 1;
        }
        strength = (type.MAX_STRENGTH);
        foodStock = type.FOOD_LIMIT;
        ammoStock = type.AMMO_LIMIT;
        speed = hex.getSpeed(this);
        this.hex = hex;
        xp = 0;
        morale = nation.getNationalMorale();
        fatigue = 0;
        foodLimit = type.FOOD_LIMIT;
        foodNeed = type.FOOD_NEED;
        ammoLimit = type.AMMO_LIMIT;
        ammoNeed = type.AMMO_NEED;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }


    public static Force createCorps(Unit... units) {
        return new Force(units);
    }

    public Force createCorps() {
        return new Force(this);
    }

    public boolean belongsToTypes(UnitType[]types, int i) {

        for (int j = i; j < types.length; j++) {
            if (getType() == types[j]) return true;
        }
        return false;
    }

}
