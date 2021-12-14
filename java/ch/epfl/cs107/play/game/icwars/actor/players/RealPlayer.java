package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.List;

public class RealPlayer extends ICWarsPlayer {
    private final static int MOVE_DURATION = 1;
    private final String[] tab = new String[]{"icwars/allyCursor", "icwars/enemyCursor"};
    private Sprite sprite;







    /**
     * Default RealPlayer constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param faction  (Faction): Faction of unity. Not null
     */
    public RealPlayer(Area area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction, units);

//        player = new ICWarsActor(getOwnerArea(), getCurrentMainCellCoordinates(), faction);
        String image_name = tab[faction.ordinal()];
        Sprite sprite = new Sprite(image_name, 1f, 1f, this, null, new Vector(0f, 0f));
        this.sprite = sprite;

    }

    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();

        if  (getPlayerState().equals(PlayerStates.NORMAL)
                || getPlayerState().equals(PlayerStates.SELECT_CELL)
                || getPlayerState().equals(PlayerStates.MOVE_UNIT)) {

            moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
            moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        }

        switchPlayerStates(getPlayerState());


        super.update(deltaTime);
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                System.out.println(orientation);
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!getPlayerState().equals(PlayerStates.IDLE)) sprite.draw(canvas); // Cursor Draw // Make draw ENEMY CURSOR
        if (getPlayerState().equals(PlayerStates.MOVE_UNIT)) gui.draw(canvas);    // GUI Draw
    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    } // Get rid of Interactions with ENEMY CURSOR

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(new ICWarsPlayerInteractionHandler());
    }


    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {


        public void interactWith(Unit unit) {
            if (getPlayerState().equals(PlayerStates.SELECT_CELL) && getFaction().equals(unit.getFaction()) && !unit.isUsed()) {
                selectedUnit = unit;
                System.out.println("test of interaction");
                gui.setSelectedUnit(selectedUnit);
                memorisedUnits.add(selectedUnit);
                System.out.println("memorized!");


            }
        }
    }
}
