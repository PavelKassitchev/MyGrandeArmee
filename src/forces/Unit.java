
package forces;

import nations.Nation;
import terrains.Hex;

public class Unit extends Force{

    private UnitType type;

    public Unit(Nation nation, UnitType type, Hex hex) {
        super();
        setNation(nation);
        setType(type);
        setUnit(true);
        if (type == UnitType.SUPPLY) {
            setSupply(true);
            setWagons(1);
        }
        setStrength(type.MAX_STRENGTH);
        setFoodStock(type.FOOD_LIMIT);
        setAmmoStock(type.AMMO_LIMIT);
        setSpeed(hex.getSpeed(this));
        setHex(hex);
        setXp(0);
        setMorale(nation.getNationalMorale());
        setFatigue(0.0f);
        setFoodLimit(type.FOOD_LIMIT);
        setFoodNeed(type.FOOD_NEED);
        setAmmoLimit(type.AMMO_LIMIT);
        setAmmoNeed(type.AMMO_NEED);
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

}
