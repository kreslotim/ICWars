package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Unit extends ICWarsActor {

    private int currentHp;
    private int maxHp;
    private int damage;
    private int rayon;
    private Faction faction;
    private Sprite sprite;

    /**
     * Default Unit constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    Unit (Area area, DiscreteCoordinates position, int rayon, int damage, int maxHp, Faction faction) {
        super(area, position, faction);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damage = damage;
        this.rayon = rayon;
        this.faction = faction;

    }

    int getDamage () {
        return damage;
    }

    void receiveDamage(int damage) {
        // check if hp < 0
        int newHp = this.currentHp - damage;
        this.currentHp = newHp < 0 ? 0 : newHp;
    }

    void repairDamage(int heal) {
        // check if hp > maxHp
        int newHp = this.currentHp + heal;
        this.currentHp = newHp > maxHp ? maxHp : newHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }
}
