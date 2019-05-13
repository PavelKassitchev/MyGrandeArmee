package forces;

import nations.Nation;
import terrains.Hex;

import java.util.ArrayList;

public class Force {

    public Force() {
        forces = new ArrayList<>();
    }

    public Force(Force... units) {
        forces = new ArrayList<>();
        setUnit(false);
        isSupply = true;
        float speed = 10.f;
        long experience = 0;
        double moraleLevel = 0.0;
        double fatigueLevel = 0.0;
        for (Force unit : units) {
            forces.add(unit);
            strength += unit.getStrength();
            foodStock += unit.getFoodStock();
            ammoStock += unit.getAmmoStock();
            foodLimit += unit.getFoodLimit();
            ammoLimit += unit.getAmmoLimit();
            foodNeed += unit.getFoodNeed();
            ammoNeed += unit.getAmmoNeed();
            wagons += unit.getWagons();
            isSupply &= unit.isSupply();
            experience += unit.getStrength() * unit.getXp();
            moraleLevel += unit.getStrength() * unit.getMorale();
            fatigueLevel += unit.getStrength() * unit.getFatigue();

            if (unit.getSpeed() < speed) {
                speed = unit.getSpeed();
            }
        }
        setSpeed(speed);
        setXp((int) (experience / strength));
        setMorale((float) (moraleLevel / strength));
        setFatigue((float) (fatigueLevel / strength));
        setNation(units[0].getNation());
        setHex(units[0].getHex());

        general = new General();
    }

    private Nation nation;
    private General general;
    private ArrayList<Force> forces;


    private boolean isUnit;
    private boolean isSupply;
    private int wagons;
    private Hex hex;
    private int strength;
    private int xp;
    private float morale;
    private float foodStock;
    private float ammoStock;
    private float foodNeed;
    private float ammoNeed;
    private float foodLimit;
    private float ammoLimit;
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

    public boolean isSupply() {
        return isSupply;
    }

    public void setSupply(boolean supply) {
        isSupply = supply;
    }

    public int getWagons() {
        return wagons;
    }

    public void setWagons(int wagons) {
        this.wagons = wagons;
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

    public float getMorale() {
        return morale;
    }

    public void setMorale(float morale) {
        this.morale = morale;
    }

    public float getFatigue() {
        return fatigue;
    }

    public void setFatigue(float fatigue) {
        this.fatigue = fatigue;
    }

    public float getFoodNeed() {
        return foodNeed;
    }

    public void setFoodNeed(float foodNeed) {
        this.foodNeed = foodNeed;
    }

    public float getAmmoNeed() {
        return ammoNeed;
    }

    public void setAmmoNeed(float ammoNeed) {
        this.ammoNeed = ammoNeed;
    }

    public float getFoodLimit() {
        return foodLimit;
    }

    public void setFoodLimit(float foodLimit) {
        this.foodLimit = foodLimit;
    }

    public float getAmmoLimit() {
        return ammoLimit;
    }

    public void setAmmoLimit(float ammoLimit) {
        this.ammoLimit = ammoLimit;
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
        long experience = xp * strength;
        double moraleLevel = morale * strength;
        double fatigueLevel = fatigue * strength;
        setStrength(strength + force.getStrength());
        setFoodStock(foodStock + force.getFoodStock());
        setAmmoStock(ammoStock + force.getAmmoStock());
        setFoodLimit(foodLimit + force.getFoodLimit());
        setAmmoLimit(ammoLimit + force.getAmmoLimit());
        setFoodNeed(foodNeed + force.getFoodNeed());
        setAmmoNeed((ammoNeed + force.getAmmoNeed()));
        setWagons(wagons + force.getWagons());
        setSupply(isSupply && force.isSupply());
        setXp((int) ((experience + force.getXp() * force.getStrength())) / strength);
        setMorale((float) ((moraleLevel + force.getMorale() * force.getStrength())) / strength);
        setFatigue((float) (fatigueLevel + force.getFatigue() * force.getStrength()) / strength);

        if (getSpeed() > force.getSpeed()) {
            setSpeed(force.getSpeed());
        }
        forces.add(force);
        //TODO
        return true;
    }

    public Force detach(Force force) {

        forces.remove(force);
        force.setHex(getHex());
        long experience = xp * strength;
        double moraleLevel = morale * strength;
        double fatigueLevel = fatigue * strength;
        setStrength(strength - force.getStrength());
        setFoodLimit(foodLimit - force.getFoodLimit());
        setAmmoLimit(ammoLimit - force.getAmmoLimit());
        setFoodNeed(foodNeed - force.getFoodNeed());
        setAmmoNeed(ammoNeed - force.getAmmoNeed());
        setFoodStock(foodStock - force.getFoodStock());
        setAmmoStock(ammoStock - force.getAmmoStock());
        setWagons(wagons - force.getWagons());
        if (force.getSpeed() == speed) {
            updateSpeed();
        }
        setXp((int) ((experience - force.getXp() * force.getStrength()) / strength));
        setMorale((float) ((moraleLevel - force.getMorale() * force.getStrength()) / strength));
        setFatigue((float) ((fatigueLevel - force.getFatigue() * force.getStrength()) / strength));


        return force;
    }

    public void redistributeSupplies() {

        float foodToDistribute;
        float ammoToDistribute;

        if (foodStock > foodLimit - wagons * UnitType.SUPPLY.FOOD_LIMIT) {
            foodToDistribute = fillCombatFood();
            takeWagonFood(foodToDistribute);
            foodToDistribute = 0;
        }
        else {
            foodToDistribute = emptyWagonFood();
        }

        if (ammoStock > ammoLimit - wagons * UnitType.SUPPLY.AMMO_LIMIT) {
            ammoToDistribute = fillCombatAmmo();
            takeWagonAmmo(ammoToDistribute);
            ammoToDistribute = 0;
        }
        else {
            ammoToDistribute = emptyWagonAmmo();
        }

        distributeSupplies(foodToDistribute, ammoToDistribute);

    }

    public void distributeSupplies(float food, float ammo) {
        float foodToDistribute = 0;
        float ammoToDistribute;

        if (foodStock + food >= foodLimit - wagons * UnitType.SUPPLY.FOOD_LIMIT) {
            if (foodStock + food > foodLimit) {
                foodToDistribute = fillCombatFood();
                foodToDistribute += fillWagonFood();
                foodToDistribute = food - foodToDistribute;
                //TODO new Wagons
            }
            else {
                foodToDistribute = fillCombatFood();
                if (food - foodToDistribute > 0) {
                    fillWagonFood(food - foodToDistribute);
                    foodToDistribute = 0;
                }
                else {
                    takeWagonFood(foodToDistribute - food);
                    foodToDistribute = 0;
                }
            }
        }
        else {
            foodToDistribute = 0;
            float foodRatio = (foodStock + food) / getFoodNeed();
            System.out.println("Total food: " + (getFoodStock() + food));
            emptyWagonFood();
            emptyCombatFood();
            System.out.println("After emptying the food stock is " + getFoodStock());


            //TODO RESTORE IF THE NEW VERSION, FOODTOCOMBATANTS, DOESN'T WORK
            if (foodRatio <= UnitType.ARTILLERY.FOOD_LIMIT / UnitType.ARTILLERY.FOOD_NEED) {
                distributeCombatFood(foodRatio);
            }

            //System.out.println(foodRatio * getFoodNeed() + "... for distr");
            //foodToCombatants(foodRatio * getFoodNeed());
        }
        System.out.println("Food To Distribute: " + foodToDistribute);
        System.out.println();
        //TODO remember about AMMO
        while (foodToDistribute > 0) {
            Unit wagon = new Unit(nation, UnitType.SUPPLY, hex);
            if (foodToDistribute > UnitType.SUPPLY.FOOD_LIMIT) {
                foodToDistribute -= UnitType.SUPPLY.FOOD_LIMIT;
            }
            else {
                wagon.setFoodStock(foodToDistribute);
                foodToDistribute = 0;
            }
            attach(wagon);
            wagons++;
        }
    }

    public float foodToCombatants(float food) {
        float foodToCombat = food;
        float used = 0;
        float ratio;
        UnitType[] types = {UnitType.INFANTRY, UnitType.CAVALRY, UnitType.ARTILLERY};

        return used;
    }

    public void distributeCombatFood(float ratio) {
        for (Force force: forces) {
            if (!force.isSupply()) {
                if (force.isUnit()) {
                    force.setFoodStock(force.getFoodNeed() * ratio);
                    setFoodStock(getFoodStock() + force.getFoodNeed() * ratio);
                    System.out.println("Current stock: " + getFoodStock() + " Type: " + ((Unit)force).getType());
                }
                else {
                    force.distributeCombatFood(ratio);
                    setFoodStock(getFoodStock() + force.getFoodStock());
                }
            }
        }
    }

    public float emptyCombatFood() {
        float food = 0;
        for (Force force: forces) {
            if (!force.isSupply()) {
                if (force.isUnit()) {
                    food += force.getFoodStock();
                    setFoodStock(getFoodStock() - force.getFoodStock());
                    force.setFoodStock(0);
                }
                else {
                    float f = force.emptyCombatFood();
                    food += f;
                    setFoodStock(getFoodStock() - f);
                }
            }
        }
        return food;
    }

    public float fillWagonFood() {
        float food = 0;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    food += force.getFoodLimit() - force.getFoodStock();
                    setFoodStock(getFoodStock() + force.getFoodLimit() - force.getFoodStock());
                    force.setFoodStock(force.getFoodLimit());

                }
            }
            else {
                float f = force.fillWagonFood();
                food += f;
                setFoodStock(getFoodStock() + f);
            }
        }
        return food;
    }

    public float fillWagonAmmo() {
        float ammo = 0;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    ammo += force.getAmmoLimit() - force.getAmmoStock();
                    setAmmoStock(getAmmoStock() + force.getAmmoLimit() - force.getAmmoStock());
                    force.setAmmoStock(force.getAmmoLimit());

                }
            }
            else {
                float a = force.fillWagonAmmo();
                ammo += a;
                setAmmoStock(getAmmoStock() + a);
            }
        }
        return ammo;
    }



    public float emptyWagonFood() {
        float foodToDistribute = 0;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    float f = force.getFoodStock();
                    force.setFoodStock(0);
                    setFoodStock(getFoodStock() - f);
                    foodToDistribute += f;
                }
            }
            float f = force.emptyWagonFood();
            setFoodStock(getFoodStock() - f);
            foodToDistribute += f;
        }
        return foodToDistribute;
    }

    public float emptyWagonAmmo() {
        float ammoToDistribute = 0;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    float a = force.getAmmoStock();
                    force.setAmmoStock(0);
                    setAmmoStock(getAmmoStock() - a);
                    ammoToDistribute += a;
                }
            }
            float a = force.emptyWagonAmmo();
            setAmmoStock(getAmmoStock() - a);
            ammoToDistribute += a;
        }
        return ammoToDistribute;
    }

    public float takeWagonFood(float food) {
        float foodToDistribute = food;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    if (foodToDistribute <= force.getFoodStock()) {
                        setFoodStock(getFoodStock() - foodToDistribute);
                        force.setFoodStock(force.getFoodStock() - foodToDistribute);
                        foodToDistribute = 0;
                        break;
                    }
                    else {
                        float f = force.getFoodStock();
                        foodToDistribute -= f;
                        force.setFoodStock(0);
                        setFoodStock(getFoodStock() - f);
                    }
                }
            }
            else {
                float f = force.takeWagonFood(foodToDistribute);
                foodToDistribute -= f;
                setFoodStock(getFoodStock() - f);
            }
        }
        return foodToDistribute;
    }

    public float takeWagonAmmo(float ammo) {
        float ammoToDistribute = ammo;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    if (ammoToDistribute <= force.getAmmoStock()) {
                        setAmmoStock(getAmmoStock() - ammoToDistribute);
                        force.setAmmoStock(force.getAmmoStock() - ammoToDistribute);
                        ammoToDistribute = 0;
                        break;
                    }
                    else {
                        float a = force.getAmmoStock();
                        ammoToDistribute -= a;
                        force.setAmmoStock(0);
                        setAmmoStock(getAmmoStock() - a);
                    }
                }
            }
            else {
                float a = force.takeWagonAmmo(ammoToDistribute);
                ammoToDistribute -= a;
                setAmmoStock(getAmmoStock() - a);
            }
        }
        return ammoToDistribute;
    }

    public float fillWagonFood(float food) {
        float foodToDistribute = food;
        for (Force force: forces) {
            if (force.isUnit()) {
                if (force.isSupply()) {
                    if (foodToDistribute < force.getFoodLimit() - force.getFoodStock()) {
                        force.setFoodStock(force.getFoodStock() + foodToDistribute);
                        setFoodStock(getFoodStock() + foodToDistribute);
                        foodToDistribute = 0;
                        break;
                    }
                    else {
                        foodToDistribute -= (force.getFoodLimit() - force.getFoodStock());
                        setFoodStock(getFoodStock() + force.getFoodLimit() - force.getFoodStock());
                        force.setFoodStock(force.getFoodLimit());
                    }
                }
            }
            else {
                float f = foodToDistribute - force.fillWagonFood(foodToDistribute);
                foodToDistribute -= f;
                setFoodStock(getFoodStock() + f);
            }
        }
        return foodToDistribute;
    }



    public float fillCombatFood() {
        float food = 0;
        for (Force force : forces) {
            if (!force.isSupply()) {
                if (force.isUnit()) {
                    food += force.getFoodLimit() - force.getFoodStock();
                    setFoodStock(getFoodStock() + force.getFoodLimit() - force.getFoodStock());
                    force.setFoodStock(force.getFoodLimit());
                } else {
                    float f = force.fillCombatFood();
                    food += f;
                    setFoodStock(getFoodStock() + f);
                }
            }
        }
        return food;
    }

    public float fillCombatAmmo() {

        float ammo = 0;
        for (Force force : forces) {
            if (!force.isSupply()) {
                if (force.isUnit()) {
                    ammo += force.getAmmoLimit() - force.getAmmoStock();
                    setAmmoStock(getAmmoStock() + force.getAmmoLimit() - force.getAmmoStock());
                    force.setAmmoStock(force.getAmmoLimit());
                } else {
                    float a = force.fillCombatAmmo();
                    ammo += a;
                    setAmmoStock(getAmmoStock() + a);
                }
            }
        }
        return ammo;
    }

    /*public void redistributeSupplies() {


        float foodRatio = foodStock / foodNeed;
        float ammoRatio = ammoStock / ammoNeed;
        float foodToDistribute = foodStock;
        float ammoToDistribute = ammoStock;
        for (Force force : forces) {

            if (force.getFoodLimit() >= foodRatio * force.getFoodNeed()) {
                force.setFoodStock(foodRatio * force.getFoodNeed());
                foodToDistribute -= foodRatio * force.getFoodNeed();
            } else {
                force.setFoodStock(force.getFoodLimit());
                foodToDistribute -= force.getFoodLimit();
            }
            if (force.getAmmoLimit() >= ammoRatio * force.getAmmoNeed()) {
                force.setAmmoStock(ammoRatio * force.getAmmoNeed());
                ammoToDistribute -= ammoRatio * force.getAmmoNeed();
            } else {
                force.setAmmoStock(force.getAmmoLimit());
                ammoToDistribute -= force.getAmmoLimit();
            }

            if (!force.isUnit()) {
                force.redistributeSupplies();
            }
        }
        System.out.println(this + " food to distribute: " + foodToDistribute);
        System.out.println(this + " ammo to distribute: " + ammoToDistribute);

        if (foodToDistribute > 0 || ammoToDistribute > 0) {
            System.out.println("food: " + foodToDistribute + " ammo: " + ammoToDistribute);
            //setFoodStock(getFoodStock() - foodToDistribute);
            //setAmmoStock(getAmmoStock() - ammoToDistribute);
            //receiveSupply(foodToDistribute, ammoToDistribute);
        }
    }*/
    public void receiveSupplies(float food, float ammo) {
        float foodToDistribute = food;
        float ammoToDistribute = ammo;
        //TODO
    }


    public void receiveSupply(float food, float ammo) {

        setFoodStock(food + getFoodStock());
        setAmmoStock(ammo + getAmmoStock());


        for (Force force : forces) {
            if (food > 0 || ammo > 0) {
                float foodExtra = food;
                float ammoExtra = ammo;
                if (force.isUnit()) {
                    if (food <= force.getFoodLimit() - force.getFoodStock()) {
                        force.setFoodStock(food + force.getFoodStock());
                        food = 0;
                    } else {
                        food -= (force.getFoodLimit() - force.getFoodStock());
                        force.setFoodStock(force.getFoodLimit());

                    }

                    if (ammo <= (force.getAmmoLimit() - force.getAmmoStock())) {
                        force.setAmmoStock(ammo + force.getAmmoStock());
                        ammo = 0;
                    } else {
                        ammo -= (force.getAmmoLimit() - force.getAmmoStock());
                        force.setAmmoStock(force.getAmmoLimit());

                    }
                } else {
                    float foodToDistribute;
                    float ammoToDistribute;
                    if (food > 0) {
                        foodToDistribute = force.getFoodLimit() - force.getFoodStock();
                    } else {
                        foodToDistribute = foodExtra;
                    }
                    if (ammo > 0) {
                        ammoToDistribute = force.getAmmoLimit() - force.getAmmoStock();
                    } else {
                        ammoToDistribute = ammoExtra;
                    }
                    force.receiveSupply(foodToDistribute, ammoToDistribute);
                }
            }
        }
        while (food > 0 || ammo > 0) {
            Unit wagon = new Unit(nation, UnitType.SUPPLY, hex);


            if (food < wagon.getFoodLimit()) {
                wagon.setFoodStock(food);
                setFoodStock(getFoodStock() - food);
                food = 0;
            } else {
                food -= wagon.getFoodLimit();
                setFoodStock(getFoodStock() - wagon.getFoodLimit());
            }
            if (ammo < wagon.getAmmoLimit()) {
                wagon.setAmmoStock(ammo);
                setAmmoStock(getAmmoStock() - ammo);
                ammo = 0;
            } else {
                ammo -= wagon.getAmmoLimit();
                setAmmoStock(getAmmoStock() - wagon.getAmmoLimit());
            }
            attach(wagon);
        }
    }


    private void updateAll() {
        //TODO

    }

    private void divideSupply(Force force, float food, float ammo) {
        force.setFoodStock(food);
        force.setAmmoStock(ammo);
        setFoodStock(foodStock - food);
        setAmmoStock(ammoStock - ammo);
    }

    private void divideSupply(Force force, boolean maxFood, boolean maxAmmo) {
        if (maxAmmo) {
            float ammoLoad = force.getAmmoLimit();
            if (ammoLoad <= getAmmoStock()) {
                force.setAmmoStock(ammoLoad);
                setAmmoStock(ammoStock - ammoLoad);
            } else {
                force.setAmmoStock(getAmmoStock());
                setAmmoStock(0.0f);
            }
        } else {
            float stock = getAmmoStock() * force.getAmmoNeed() / getAmmoNeed();
            force.setAmmoStock(stock);
            setAmmoStock(ammoStock - stock);
        }

        if (maxFood) {
            float foodLoad = force.getFoodLimit();
            if (foodLoad <= getFoodStock()) {
                force.setFoodStock(foodLoad);
                setFoodStock(foodStock - foodLoad);
            } else {
                force.setFoodStock(getFoodStock());
                setFoodStock(0.0f);
            }
        } else {
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
        for (Force force : forces) {
            if (force.isUnit()) {

                if (force.getSpeed() < speed) {
                    speed = force.getSpeed();
                }
            } else {
                force.updateSpeed();
            }
            setSpeed(speed);
        }
    }

    /*private float getFoodLoad() {
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
    }*/

}
