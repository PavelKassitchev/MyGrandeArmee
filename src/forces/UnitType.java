package forces;

public enum UnitType {
    ARTILLERY("Battery", 100, 1.3f, 1.6f, 0.0f, 0.2f, 2.0f, 2.0f, 2.0f,
            0.7f),
    INFANTRY("Battalion",
            900, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f,
            1.0f),
    CAVALRY("Squadron", 300, 1.0f, 0.3f, 3.0f, 0.75f, 0.2f, 3.0f, 3.0f,
            2.0f),
    SUPPLY("Wagon", 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 25.0f, 25.0f,
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
