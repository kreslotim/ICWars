package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer {
    private final static int MOVE_DURATION = 1;
    private final String[] tab = new String[]{"icwars/allyCursor", "icwars/enemyCursor"};
    private Unit selectedUnit;
    protected final ICWarsPlayerGUI gui = new ICWarsPlayerGUI(0, this); // @TODO
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

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

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
        sprite.draw(canvas);
        gui.draw(canvas);
    }

    public void selectUnit(int unitIndex) {
        if (unitsList != null) {
            gui.setSelectedUnit(unitsList.get(unitIndex));
        }
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }
}
