package forces;

import nations.Nation;
import sun.misc.Cache;
import terrains.Hex;

import java.util.ArrayList;

import static forces.UnitType.*;

public class Force {

    Nation nation;
    General general;
    ArrayList<Force> forces;


    boolean isUnit;
    boolean isSupply;
    int wagons;
    Hex hex;
    int strength;
    int xp;
    float morale;
    float foodStock;
    float ammoStock;
    float foodNeed;
    float ammoNeed;
    float foodLimit;
    float ammoLimit;
    float fatigue;
    float speed;

    //STATIC SECTION

    public Force() {
        forces = new ArrayList<>();
    }

    public Force(Force... units) {
        forces = new ArrayList<>();
        isSupply = true;
        float speed = 10.f;
        long experience = 0;
        double moraleLevel = 0.0;
        double fatigueLevel = 0.0;
        for (Force unit : units) {
            forces.add(unit);
            strength += unit.strength;
            foodStock += unit.foodStock;
            ammoStock += unit.ammoStock;
            foodLimit += unit.foodLimit;
            ammoLimit += unit.ammoLimit;
            foodNeed += unit.foodNeed;
            ammoNeed += unit.ammoNeed;
            wagons += unit.wagons;
            isSupply &= unit.isSupply;
            experience += unit.strength * unit.xp;
            moraleLevel += unit.strength * unit.morale;
            fatigueLevel += unit.strength * unit.fatigue;

            if (unit.speed < speed) {
                speed = unit.speed;
            }
        }
        xp = (int) (experience / strength);
        morale = (float) (moraleLevel / strength);
        fatigue = (float) (fatigueLevel / strength);
        nation = units[0].nation;
        hex = units[0].hex;

        general = new General();
    }

    public General detachGeneral() {
        General oldGeneral = this.general;
        general = new General();
        //TODO
        return oldGeneral;
    }


    public boolean attach(Force force) {
        long experience = xp * strength;
        double moraleLevel = morale * strength;
        double fatigueLevel = fatigue * strength;
        strength += force.strength;
        foodStock += force.foodStock;
        ammoStock += force.ammoStock;
        foodLimit += force.foodLimit;
        ammoLimit += force.ammoLimit;
        foodNeed += force.foodNeed;
        ammoNeed += force.ammoNeed;
        wagons += force.wagons;
        isSupply = isSupply && force.isSupply;
        xp = (int) ((experience + force.xp * force.strength) / strength);
        morale = (float) ((moraleLevel + force.morale * force.strength) / strength);
        fatigue = (float) (fatigueLevel + force.fatigue * force.strength) / strength;

        if (speed > force.speed) {
            speed = force.speed;
        }
        forces.add(force);
        //TODO
        return true;
    }

    public Force detach(Force force) {

        forces.remove(force);
        force.hex = hex;
        long experience = xp * strength;
        double moraleLevel = morale * strength;
        double fatigueLevel = fatigue * strength;
        strength = strength - force.strength;
        foodLimit = foodLimit - force.foodLimit;
        ammoLimit = ammoLimit - force.ammoLimit;
        foodNeed = foodNeed - force.foodNeed;
        ammoNeed = ammoNeed - force.ammoNeed;
        foodStock = foodStock - force.foodStock;
        ammoStock = ammoStock - force.ammoStock;
        wagons -= force.wagons;
        if (force.speed == speed) {
            updateSpeed();
        }
        xp = (int) (experience - force.xp * force.strength / strength);
        morale = (float) (moraleLevel - force.morale * force.strength / strength);
        fatigue = (float) (fatigueLevel - force.fatigue * force.strength / strength);


        return force;
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
            } else {
                foodToDistribute = fillCombatFood();
                if (food - foodToDistribute > 0) {
                    fillWagonFood(food - foodToDistribute);
                    foodToDistribute = 0;
                } else {
                    takeWagonFood(foodToDistribute - food);
                    foodToDistribute = 0;
                }
            }
        } else {
            foodToDistribute = 0;
            float totalFood = foodStock + food;
            System.out.println("Total food: " + totalFood);
            emptyWagonFood();
            emptyCombatFood();
            foodToCombatants(totalFood);
        }
        System.out.println("Food To Distribute: " + foodToDistribute);
        System.out.println();
        //TODO remember about AMMO
        while (foodToDistribute > 0) {
            Unit wagon = new Unit(nation, UnitType.SUPPLY, hex);
            if (foodToDistribute > UnitType.SUPPLY.FOOD_LIMIT) {
                foodToDistribute -= UnitType.SUPPLY.FOOD_LIMIT;
            } else {
                wagon.foodStock = foodToDistribute;
                foodToDistribute = 0;
            }
            attach(wagon);
            wagons++;
        }
    }
    //TODO should be modified

    public float foodToCombatants(float food) {
        float toDistribute = food;
        float distributed = 0;
        float ratio = food / foodNeed;

        if (ratio < 2) {
            distributed = foodToUnits(ratio, UnitType.INFANTRY, UnitType.CAVALRY, UnitType.ARTILLERY);
        } else if (ratio < 4) {
            System.out.println("OLD RATIO : " + ratio);
            distributed = foodToUnits(UnitType.INFANTRY);
            ratio = (food - distributed) / (foodNeed - distributed / 2);
            System.out.println("NEW RATIO : " + ratio);
            toDistribute -= distributed;
            if (ratio < 4) {
                distributed = foodToUnits(ratio, UnitType.CAVALRY, UnitType.ARTILLERY);
            } else {
                distributed = foodToUnits(UnitType.CAVALRY);
                ratio = 10 * (toDistribute - distributed) / (foodLimit - wagons * 25 - foodStock);
                distributed = foodToUnits(ratio, UnitType.ARTILLERY);
            }
        } else {
            distributed = foodToUnits(UnitType.INFANTRY, UnitType.CAVALRY);
            ratio = 10 * (food - distributed) / (foodLimit - 25.0f * wagons - foodStock);
            distributed = foodToUnits(ratio, UnitType.ARTILLERY);
        }

        return distributed;
    }

    public float foodToUnits(UnitType... type) {
        float distributed = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (((Unit) force).belongsToTypes(type, 0)) {
                    float d = force.foodLimit;
                    force.foodStock = d;
                    foodStock += d;
                    distributed += d;
                }
            } else {
                float d = force.foodToUnits(type);
                foodStock += d;
                distributed += d;

            }
        }
        return distributed;
    }

    public float foodToUnits(float ratio, UnitType... type) {
        float distributed = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (((Unit) force).belongsToTypes(type, 0)) {
                    float d = force.foodNeed * ratio;
                    force.foodStock = d;
                    foodStock += d;
                    distributed += d;
                }
            } else {
                float d = force.foodToUnits(ratio, type);
                foodStock += d;
                distributed += d;
            }
        }
        return distributed;
    }


    public float emptyCombatFood() {
        float food = 0;
        for (Force force : forces) {
            if (!force.isSupply) {
                if (force.isUnit) {
                    food += force.foodStock;
                    foodStock -= force.foodStock;
                    force.foodStock = 0;
                } else {
                    float f = force.emptyCombatFood();
                    food += f;
                    foodStock -= f;
                }
            }
        }
        return food;
    }

    public float fillWagonFood() {
        float food = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    food += force.foodLimit - force.foodStock;
                    foodStock += (force.foodLimit - force.foodStock);
                    force.foodStock = force.foodLimit;

                }
            } else {
                float f = force.fillWagonFood();
                food += f;
                foodStock += f;
            }
        }
        return food;
    }

    public float fillWagonAmmo() {
        float ammo = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    ammo += force.ammoLimit - force.ammoStock;
                    ammoStock += (force.ammoLimit - force.ammoStock);
                    force.ammoStock = force.ammoLimit;

                }
            } else {
                float a = force.fillWagonAmmo();
                ammo += a;
                ammoStock += a;
            }
        }
        return ammo;
    }

    public float emptyWagonFood() {
        float foodToDistribute = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    float f = force.foodStock;
                    force.foodStock = 0;
                    foodStock -= f;
                    foodToDistribute += f;
                }
            }
            float f = force.emptyWagonFood();
            foodStock -= f;
            foodToDistribute += f;
        }
        return foodToDistribute;
    }

    public float emptyWagonAmmo() {
        float ammoToDistribute = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    float a = force.ammoStock;
                    force.ammoStock = 0;
                    ammoStock -= a;
                    ammoToDistribute += a;
                }
            }
            float a = force.emptyWagonAmmo();
            ammoStock -= a;
            ammoToDistribute += a;
        }
        return ammoToDistribute;
    }

    public float takeWagonFood(float food) {
        float foodToDistribute = food;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    if (foodToDistribute <= force.foodStock) {
                        foodStock -= foodToDistribute;
                        force.foodStock -= foodToDistribute;
                        foodToDistribute = 0;
                        break;
                    } else {
                        float f = force.foodStock;
                        foodToDistribute -= f;
                        force.foodStock = 0;
                        foodStock -= f;
                    }
                }
            } else {
                float f = force.takeWagonFood(foodToDistribute);
                foodToDistribute -= f;
                foodStock -= f;
            }
        }
        return foodToDistribute;
    }

    public float takeWagonAmmo(float ammo) {
        float ammoToDistribute = ammo;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    if (ammoToDistribute <= force.ammoStock) {
                        ammoStock -= ammoToDistribute;
                        force.ammoStock -= ammoToDistribute;
                        ammoToDistribute = 0;
                        break;
                    } else {
                        float a = force.ammoStock;
                        ammoToDistribute -= a;
                        force.ammoStock = 0;
                        ammoStock -= a;
                    }
                }
            } else {
                float a = force.takeWagonAmmo(ammoToDistribute);
                ammoToDistribute -= a;
                ammoStock -= a;
            }
        }
        return ammoToDistribute;
    }

    public float fillWagonFood(float food) {
        float foodToDistribute = food;
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    if (foodToDistribute < force.foodLimit - force.foodStock) {
                        force.foodStock += foodToDistribute;
                        foodStock += foodToDistribute;
                        foodToDistribute = 0;
                        break;
                    } else {
                        foodToDistribute -= (force.foodLimit - force.foodStock);
                        foodStock += (force.foodLimit - force.foodStock);
                        force.foodStock = force.foodLimit;
                    }
                }
            } else {
                float f = foodToDistribute - force.fillWagonFood(foodToDistribute);
                foodToDistribute -= f;
                foodStock += f;
            }
        }
        return foodToDistribute;
    }

    public float fillCombatFood() {
        float food = 0;
        for (Force force : forces) {
            if (!force.isSupply) {
                if (force.isUnit) {
                    food += force.foodLimit - force.foodStock;
                    foodStock += (force.foodLimit - force.foodStock);
                    force.foodStock = force.foodLimit;
                } else {
                    float f = force.fillCombatFood();
                    food += f;
                    foodStock += f;
                }
            }
        }
        return food;
    }

    public float fillCombatAmmo() {

        float ammo = 0;
        for (Force force : forces) {
            if (!force.isSupply) {
                if (force.isUnit) {
                    ammo += force.ammoLimit - force.ammoStock;
                    ammoStock += (force.ammoLimit - force.ammoStock);
                    force.ammoStock = force.ammoLimit;
                } else {
                    float a = force.fillCombatAmmo();
                    ammo += a;
                    ammoStock += a;
                }
            }
        }
        return ammo;
    }

    private void updateAll() {
        //TODO

    }

    private void updateSpeed() {
        float spd = 10.0f;
        for (Force force : forces) {
            if (force.isUnit) {

                if (force.speed < spd) {
                    spd = force.speed;
                }
            } else {
                force.updateSpeed();
            }
            speed = spd;
        }
    }

    //This is an attempt
    //to make a shorter version
    //
    //
    public float distributeFood(float food) {
        UnitType[] types = {SUPPLY, INFANTRY, CAVALRY, ARTILLERY};
        float free = food;
        free += collectFromUnits(SUPPLY, INFANTRY, CAVALRY, ARTILLERY);
        if (free > foodLimit) {
            free -= distributeToUnits(SUPPLY, INFANTRY, CAVALRY, ARTILLERY);
            while (free > 0) {
                Unit wagon = new Unit(nation, SUPPLY, hex);
                if (wagon.foodLimit >= free) {
                    wagon.foodStock = free;
                    free = 0;
                } else {
                    wagon.foodStock = wagon.foodLimit;
                    free -= wagon.foodLimit;
                }
                attach(wagon);
                wagons++;
                foodLimit += SUPPLY.FOOD_LIMIT;
            }
        } else if (free > foodLimit - wagons * SUPPLY.FOOD_LIMIT) {
            free -= distributeToUnits(INFANTRY, CAVALRY, ARTILLERY);
            distributeToWagons(free);
        }
        else {
            if (free < foodNeed * INFANTRY.FOOD_LIMIT / INFANTRY.FOOD_NEED) {
                float ratio = free / foodNeed;
                distributeToUnits(ratio, INFANTRY, CAVALRY, ARTILLERY);
                free = 0;
            }
            else if (free > foodNeed * CAVALRY.FOOD_LIMIT / CAVALRY.FOOD_NEED) {
                free -= distributeToUnits(INFANTRY, CAVALRY);
                float ratio = ARTILLERY.FOOD_LIMIT / ARTILLERY.FOOD_NEED * free / (foodLimit - wagons * SUPPLY.FOOD_LIMIT - foodStock);
                distributeToUnits(ratio, ARTILLERY);
                free = 0;
            }
            else {
                float distributed = distributeToUnits(INFANTRY);
                free -= distributed;
                float ratio = free / (foodNeed - distributed * INFANTRY.FOOD_NEED / INFANTRY.FOOD_LIMIT);
                if (ratio < CAVALRY.FOOD_LIMIT / CAVALRY.FOOD_NEED) {
                    distributeToUnits(ratio, CAVALRY, ARTILLERY);
                    free =0;
                }
                else {
                    free -= distributeToUnits(CAVALRY);
                    ratio = ARTILLERY.FOOD_LIMIT / ARTILLERY.FOOD_NEED * free / (foodLimit - wagons * SUPPLY.FOOD_LIMIT - foodStock);
                    distributeToUnits(ratio, ARTILLERY);
                    free = 0;
                }
            }
        }
        return free;
    }

    public float distributeToUnits(float ratio, UnitType... types) {
        float need = 0;
        for (Force force: forces) {
            if (force.isUnit) {
                if (((Unit)force).belongsToTypes(types, 0)) {
                    float f = ratio * force.foodNeed;
                    force.foodStock = f;
                    foodStock += f;
                    need += f;
                }
            }
            else {
                float f = force.distributeToUnits(ratio, types);
                foodStock += f;
                need += f;
            }
        }
        return need;
    }

    public float distributeToWagons(float food) {
        for (Force force : forces) {
            if (force.isUnit) {
                if (force.isSupply) {
                    if (food > SUPPLY.FOOD_LIMIT) {
                        food -= SUPPLY.FOOD_LIMIT;
                        force.foodStock = SUPPLY.FOOD_LIMIT;
                        foodStock += SUPPLY.FOOD_LIMIT;
                    }
                    else {
                        force.foodStock = food;
                        foodStock += food;
                        food = 0;
                    }
                }

            }
            else {
                float f = force.distributeToWagons(food);
                food -= f;
                foodStock += f;
            }
        }
        return food;
    }

    public float distributeToUnits(UnitType... types) {
        float need = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (((Unit) force).belongsToTypes(types, 0)) {
                    float f = force.foodLimit;
                    need += f;
                    force.foodStock = f;
                    foodStock += f;
                }
            } else {
                float n = force.distributeToUnits(types);
                need += n;
                foodStock += n;
            }
        }
        return need;
    }

    public float collectFromUnits(UnitType... types) {
        float collected = 0;
        for (Force force : forces) {
            if (force.isUnit) {
                if (((Unit) force).belongsToTypes(types, 0)) {
                    float f = force.foodStock;
                    foodStock -= f;
                    force.foodStock = 0;
                    collected += f;
                }
            } else {
                float f = force.collectFromUnits(types);
                collected += f;
                foodStock -= f;
            }
        }

        return collected;
    }


}
