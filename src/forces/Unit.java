
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
        setStrength(type.MAX_STRENGTH);
        setFoodStock(type.MAX_FOOD_LOAD);
        setAmmoStock(type.MAX_AMMO_LOAD);
        setSpeed(hex.getSpeed(this));
        setHex(hex);
        setXp(0);
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
