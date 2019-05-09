
package forces;

import nations.Nation;
import terrain.Hex;

public class Unit extends Force{

    private UnitType type;

    public Unit(Nation nation, UnitType type, Hex hex) {
        super();
        setNation(nation);
        setType(type);
        setUnit(true);
        setStrength(type.getMAX_STRENGTH());
        setFoodStock(type.getMAX_FOOD_LOAD());
        setAmmoStock(type.getMAX_AMMO_LOAD());
        setSpeed(hex.getSpeed(this));
        setHex(hex);
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
