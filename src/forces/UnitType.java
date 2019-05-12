package forces;

public enum UnitType {
    INFANTRY("Battalion", 800, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f,
            1.0f),
    CAVALRY("Squadron", 250, 1.0f, 0.3f, 3.0f, 1.0f, 0.2f, 3.0f, 3.0f,
            2.0f),
    ARTILLERY("Battery", 100, 1.3f, 1.6f, 0.05f, 0.2f, 2.0f, 2.0f, 2.0f,
            0.7f),
    SUPPLY("Wagon", 150, 0.0f, 0.05f, 0.0f, 0.1f, 0.05f, 25.0f, 25.0f,
            0.7f);

    public final String NAME;
    public final int MAX_STRENGTH;
    public final float ATTACK;
    public final float DEFENSE;
    public final float PURSUIT;
    public final float FOOD_NEED;
    public final float AMMO_NEED;
    public final float FOOD_LIMIT;
    public final float AMMO_LIMIT;
    public final float SPEED;


    UnitType(String name, int maxStrength, float attack, float defense, float pursuit, float foodNeed, float ammoNeed,
             float foodMax, float ammoMax, float speed) {
        NAME = name;
        MAX_STRENGTH = maxStrength;
        ATTACK = attack;
        DEFENSE = defense;
        PURSUIT = pursuit;
        FOOD_NEED = foodNeed;
        AMMO_NEED = ammoNeed;
        FOOD_LIMIT = foodMax;
        AMMO_LIMIT = ammoMax;
        SPEED = speed;

    }


}
