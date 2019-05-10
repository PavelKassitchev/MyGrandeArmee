package forces;

import nations.Nation;
import terrains.Hex;

import java.util.ArrayList;

public class Force {

    public Force() {
        forces = new ArrayList<>();
    }

    public Force(Unit... units) {
        forces = new ArrayList<>();
        setUnit(false);
        float speed = 10.f;
        for (Unit unit: units) {
            forces.add(unit);
            strength += unit.getStrength();
            foodStock += unit.getFoodStock();
            ammoStock += unit.getAmmoStock();
            if (unit.getSpeed() < speed) {
                speed = unit.getSpeed();
            }
        }
        setSpeed(speed);
        setNation(units[0].getNation());
        setHex(units[0].getHex());

        general = new General();
    }

    private Nation nation;
    private General general;
    private ArrayList<Force> forces;


    private boolean isUnit;
    private Hex hex;

    private int strength;
    private int xp;
    private float morale;
    private float foodStock;
    private float ammoStock;
    private float fatigue;
    private float speed;

    public ArrayList<Force> getForces() {
        return forces;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public boolean isUnit() {
        return isUnit;
    }

    public void setUnit(boolean unit) {
        isUnit = unit;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Hex getHex() {
        return hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public float getFoodStock() {
        return foodStock;
    }

    public void setFoodStock(float foodStock) {
        this.foodStock = foodStock;
    }

    public float getAmmoStock() {
        return ammoStock;
    }

    public void setAmmoStock(float ammoStock) {
        this.ammoStock = ammoStock;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public General getGeneral() {
        return general;
    }

    public General detachGeneral() {
        General general = this.general;
        setGeneral(new General());
        //TODO
        return general;
    }


    public boolean attach(Force force) {
        setStrength(strength + force.getStrength());
        setFoodStock(foodStock + force.getFoodStock());
        setAmmoStock(ammoStock + force.getAmmoStock());
        if (getSpeed() > force.getSpeed()) {
            setSpeed(force.getSpeed());
        }
        forces.add(force);
        return true;
    }
    public Force detach (Force force) {
        if (forces.contains(force)) {
            forces.remove(force);
            divideSupply(force);
            setStrength(getStrength() - force.getStrength());
            if (getSpeed() == force.getSpeed()) {
                updateSpeed();
            }
            return force;
        }
        else {
            for (Force f: forces) {
                if (!f.isUnit()) {
                    f.detach(force);
                }
            }
        }

        return null;
    }


    private void updateAll() {
        //TODO

    }
    private void divideSupply (Force force, float food, float ammo) {
        force.setFoodStock(food);
        force.setAmmoStock(ammo);
        setFoodStock(foodStock - food);
        setAmmoStock(ammoStock - ammo);
    }
    private void divideSupply(Force force, boolean maxFood, boolean maxAmmo) {
        if (maxAmmo) {
            float ammoLoad = force.getAmmoLoad();
            if (ammoLoad <= getAmmoStock()) {
                force.setAmmoStock(ammoLoad);
                setAmmoStock(ammoStock - ammoLoad);
            }
            else {
                force.setAmmoStock(getAmmoStock());
                setAmmoStock(0.0f);
            }
        }
        else {
            float stock = getAmmoStock() * force.getAmmoNeed() / getAmmoNeed();
            force.setAmmoStock(stock);
            setAmmoStock(ammoStock - stock);
        }

        if (maxFood) {
            float foodLoad = force.getFoodLoad();
            if (foodLoad <= getFoodStock()) {
                force.setFoodStock(foodLoad);
                setFoodStock(foodStock - foodLoad);
            }
            else {
                force.setFoodStock(getFoodStock());
                setFoodStock(0.0f);
            }
        }
        else {
            float stock = getFoodStock() * force.getFoodNeed() / getFoodNeed();
            force.setFoodStock(stock);
            setFoodStock(foodStock - stock);
        }

    }

    private void divideSupply(Force force) {
        divideSupply(force, false, false);
    }
    private void updateSpeed() {
        float speed = 10.0f;
        for (Force force: forces) {
            if (force.isUnit()) {

                if (force.getSpeed() < speed) {
                    speed = force.getSpeed();
                }
            }
            else {
                force.updateSpeed();
            }
            setSpeed(speed);
        }
    }

    private float getFoodLoad() {
        float load = 0.0f;
        for (Force force: forces) {
            if (force.isUnit()) {
                Unit unit = (Unit)force;
                load += unit.getType().MAX_FOOD_LOAD * unit.getStrength() / unit.getType().MAX_STRENGTH;
            }
            else {
                force.getFoodLoad();
            }
        }
        return load;
    }
    private float getAmmoLoad() {
        float load = 0.0f;
        for (Force force: forces) {
            if (force.isUnit()) {
                Unit unit = (Unit)force;
                load += unit.getType().MAX_AMMO_LOAD * unit.getStrength() / unit.getType().MAX_STRENGTH;
            }
            else {
                force.getAmmoLoad();
            }
        }
        return load;
    }

    private float getFoodNeed() {
        float need = 0.0f;
        for (Force force: forces) {
            if (force.isUnit()) {
                Unit unit = (Unit)force;
                need += unit.getType().FOOD_NEED * unit.getStrength() / unit.getType().MAX_STRENGTH;
            }
            else {
                force.getFoodNeed();
            }
        }
        return need;
    }

    private float getAmmoNeed() {
        float need = 0.0f;
        for (Force force: forces) {
            if (force.isUnit()) {
                Unit unit = (Unit)force;
                need += unit.getType().AMMO_NEED * unit.getStrength() / unit.getType().MAX_STRENGTH;
            }
            else {
                force.getAmmoNeed();
            }
        }
        return need;
    }

}
