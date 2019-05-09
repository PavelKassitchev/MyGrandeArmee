package forces;

public class General {
    private float moraleBonus;
    private float attackBonus;
    private float defenseBonus;
    private float infantryLeader;
    private float cavalryLeader;
    private float artilleryLeader;
    private float supplyExpert;

    public General() {
        moraleBonus = 0;
        attackBonus = 0;
        defenseBonus = 0;
        infantryLeader = 0;
        cavalryLeader = 0;
        artilleryLeader = 0;
        supplyExpert = 0;
    }

    public General(float moraleBonus, float attackBonus, float defenseBonus,
                   float infantryLeader, float cavalryLeader, float artilleryLeader, float supplyExpert) {
        this.moraleBonus = moraleBonus;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.infantryLeader = infantryLeader;
        this.cavalryLeader = cavalryLeader;
        this.artilleryLeader = artilleryLeader;
        this.supplyExpert = supplyExpert;
    }
}
