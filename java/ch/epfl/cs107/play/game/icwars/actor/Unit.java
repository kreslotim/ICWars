package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Queue;


public abstract class Unit extends ICWarsActor {

    private int currentHp;
    private int maxHp;
    private int damage;
    private int radius;
    //private Faction faction;
    private Sprite sprite;
    private ICWarsRange range = new ICWarsRange();
    private boolean usedUnit = false;

    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */

    Unit(Area area, DiscreteCoordinates position, int radius, int damage, int maxHp, Faction faction, String spriteName) {
        super(area, position, faction);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damage = damage;
        this.radius = radius;

        addEdge(getCurrentMainCellCoordinates()); // appel addNode
        Sprite sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.sprite = sprite;
        setOwnerArea(area);
    }

    public void setIsUsedUnit(boolean used) {
        sprite.setAlpha(used ? 0.5f : 1f);
        usedUnit = used;
    }

    public boolean isUsed() {
        return usedUnit;
    }


    int getDamage() {
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

    private void addEdge(DiscreteCoordinates from) {

        boolean hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge;

        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {

                if (x + from.x <= getOwnerArea().getWidth() - 1 && x + from.x >= 0 && // getOwnerArea associé au Level0
                        y + from.y <= getOwnerArea().getHeight() - 1 && y + from.y >= 0) {

                    hasLeftEdge = x > -radius && from.x + x >= 0;
                    hasRightEdge = x < radius && from.x + x >= 0;
                    hasUpEdge = y < radius && from.y + y >= 0;
                    hasDownEdge = y > -radius && from.y + y >= 0;

                    range.addNode(new DiscreteCoordinates(x + from.x, y + from.y),
                            hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                }


            }
        }
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

        ((ICWarsInteractionVisitor) v).interactWith(this);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas); // l'affichage du sprite sur l'ecran
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        if (!range.nodeExists(newPosition) || !super.changePosition(newPosition)) {
            return false;
        }
        // méthode qui recalcule la nouvelle position
        // adapter le rayon d'action à la newPosition
        range = new ICWarsRange();
        addEdge(newPosition);
        return true;
    }


    /**
     * Draw the unit's range and a path from the unit position to
     * destination
     *
     * @param destination path destination
     * @param canvas      canvas
     */

    public void drawRangeAndPathTo(DiscreteCoordinates destination, Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path = range.shortestPath(getCurrentMainCellCoordinates(), destination);

        //Draw path only if it exists (destination inside the range)
        if (path != null) {
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
        }
    }

}