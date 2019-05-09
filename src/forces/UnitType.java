package forces;

public enum UnitType {
    INFANTRY("Battalion", 800, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f,
            1.0f),
    CAVALRY("Squadron", 250, 1.0f, 0.3f, 3.0f, 1.0f, 0.2f, 3.0f, 3.0f,
            2.0f),
    ARTILLERY("Battery", 100, 1.3f, 1.6f, 0.05f, 0.2f, 2.0f, 2.0f, 2.0f,
            0.7f),
    SUPPLY("Wagon", 200, 0.0f, 0.1f, 0.0f, 0.2f, 0.05f, 25.0f, 25.0f,
            0.75f);

    private final String NAME;
    private final int MAX_STRENGTH;
    private final float ATTACK;
    private final float DEFENSE;
    private final float PURSUIT;
    private final float FOOD_NEED;
    private final float AMMO_NEED;
    private final float MAX_FOOD_LOAD;
    private final float MAX_AMMO_LOAD;
    private final float SPEED;

    public String getNAME() {
        return NAME;
    }

    public int getMAX_STRENGTH() {
        return MAX_STRENGTH;
    }

    public float getATTACK() {
        return ATTACK;
    }

    public float getDEFENSE() {
        return DEFENSE;
    }

    public float getPURSUIT() {
        return PURSUIT;
    }

    public float getFOOD_NEED() {
        return FOOD_NEED;
    }

    public float getAMMO_NEED() {
        return AMMO_NEED;
    }

    public float getMAX_FOOD_LOAD() {
        return MAX_FOOD_LOAD;
    }

    public float getMAX_AMMO_LOAD() {
        return MAX_AMMO_LOAD;
    }

    public float getSPEED() {
        return SPEED;
    }

    UnitType(String name, int maxStrength, float attack, float defense, float pursuit, float foodNeed, float ammoNeed,
             float foodMax, float ammoMax, float speed) {
        NAME = name;
        MAX_STRENGTH = maxStrength;
        ATTACK = attack;
        DEFENSE = defense;
        PURSUIT = pursuit;
        FOOD_NEED = foodNeed;
        AMMO_NEED = ammoNeed;
        MAX_FOOD_LOAD = foodMax;
        MAX_AMMO_LOAD = ammoMax;
        SPEED = speed;

    }


}
