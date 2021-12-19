package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class RealPlayer extends ICWarsPlayer {
    private final static int MOVE_DURATION = 1; // Time is money
    private final String[] cursors = new String[]{"icwars/allyCursor", "icwars/enemyCursor"};
    private Sprite sprite;

    private final ICWarsPlayerGUI gui = new ICWarsPlayerGUI(0, this);

    private Unit selectedUnit;
    private List<Unit> playerUnitsList;
    private List<Area> areasList = new ArrayList<>();
    private boolean defeated = false;
    private Action action;




    /**
     * Default RealPlayer constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param faction  (Faction): Faction of unity. Not null
     * @param units    (Unit) Ellipse of all units, of a player
     */
    public RealPlayer(ICWarsArea area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);

        for (Unit unit : units) {
            area.registerActor(unit);
            area.registerUnit(unit);
            playerUnitsList = new ArrayList<>(List.of(units));
        }

        // choosing the Cursor for a player, and constructing the corresponding image
        String cursorName = cursors[faction.ordinal()];
        Sprite sprite = new Sprite(cursorName, 1f, 1f, this, null, new Vector(0f, 0f));
        this.sprite = sprite;

        // Player starts in state : IDLE
        setPlayerState(PlayerStates.IDLE);
    }


    /**
     * drawing method for GUI and Cursor
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        gui.draw(canvas);                                                      // GUI Draw
        if (!getPlayerState().equals(PlayerStates.IDLE)) sprite.draw(canvas); // Cursor Draw

        if (getPlayerState().equals(PlayerStates.ACTION)) {
            if (action!=null) {
                action.draw(canvas);
            }
        }
    }



    /**
     * General update method, for a player,
     * allowing to navigate on the grid
     *
     * @param deltaTime (float) frequency of the update
     */
    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();

        if (getPlayerState().equals(PlayerStates.NORMAL)
                || getPlayerState().equals(PlayerStates.SELECT_CELL)
                || getPlayerState().equals(PlayerStates.MOVE_UNIT)) {

            moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
            moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        }

        updatePlayerStates(deltaTime);

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







    /******************************************************************************************************************
     ****************************************    INTERACTIONS    ******************************************************
     ******************************************************************************************************************/

    /**
     * Main Interaction method, allowing interactions between Interactors (players), and Interactable objects (units)
     *
     * @param other (Interactable). Not null
     */
    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(new ICWarsPlayerInteractionHandler());
    }

    /**
     * Nested class, handling all the Interactions
     */
    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {
        /**
         * InteractWith method redefined in the Interface ICWarsInteractionVisitor
         * @param unit (Unit)
         */
        @Override
        public void interactWith(Unit unit) {
            if (getPlayerState().equals(PlayerStates.SELECT_CELL) && getFaction().equals(unit.getFaction()) && !unit.isUsed()) {
                setSelectedUnit(unit);
                System.out.println("test of interaction");
                gui.setSelectedUnit(unit);
                getMemorisedUnits().add(unit);
                System.out.println("memorized!");

            }

            if (getPlayerState().equals(PlayerStates.NORMAL) || getPlayerState().equals(PlayerStates.SELECT_CELL)) {
                gui.setPanelOfInfoForUnit(unit);

            }
        }

        @Override
        public void interactWith(ICWarsBehavior.ICWarsCell cell) {
            if (getPlayerState().equals(PlayerStates.NORMAL) || getPlayerState().equals(PlayerStates.SELECT_CELL)) {

                gui.setCurrentCell(cell.getCellType());

            }
        }
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false; // Allows to get rid of Interactions with ENEMY CURSOR
    }

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
        return true; // The player wishes to Interact with an Interactable object (unit)
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }




    /******************************************************************************************************************
     ***********************************     FINITE STATE AUTOMATON     ***********************************************
     ******************************************************************************************************************/

    /**
     * Automaton that changes the behaviour of the player
     *
     * @param deltaTime (float)
     */
    public void updatePlayerStates(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        switch (getPlayerState()) {
            case IDLE:
                gui.setPanelOfInfoForUnit(null);
                gui.setCurrentCell(null);
                break;
            case NORMAL:
                //System.out.println("Normal");
                gui.setPanelOfInfoForUnit(null);
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    setPlayerState(PlayerStates.SELECT_CELL);
                    System.out.println("State: SELECT_CELL");
                }

                break;
            case SELECT_CELL:

                if (selectedUnit != null && !selectedUnit.isUsed()) {

                    setPlayerState(PlayerStates.MOVE_UNIT);
                    System.out.println("State: MOVE_UNIT");
                } else {
                    onLeaving(getLeftCells());
                }

                break;

            case MOVE_UNIT:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {

                    selectedUnit.changePosition(new DiscreteCoordinates(getCurrentMainCellCoordinates().x, getCurrentMainCellCoordinates().y));

                    if (!getCurrentMainCellCoordinates().equals(getLeftCells().get(0))) { //If the unit was not repositioned
                        setPlayerState(PlayerStates.ACTION_SELECTION);
                        System.out.println("State: ACTION_SELECTION");
                        System.out.println("Choosing Attack or Wait");
                    }
                }
                break;

            case ACTION_SELECTION:

                for (Action act : selectedUnit.getAction()) {
                    if (keyboard.get(act.getKey()).isReleased()) {
                        action = act;
                        setPlayerState(PlayerStates.ACTION);
                        System.out.println("State: Action");

                        //selectedUnit = null;
                    }
                }
                break;
            case ACTION:
                action.doAction(deltaTime,this, keyboard);
                break;
        }
    }

    /******************************************************************************************************************
     *                                    All methods used in the Automaton
     ******************************************************************************************************************/

    /**
     * Sets the selectedUnit
     * @param selectedUnit (Unit)
     */
    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    /**
     * Gets the list of units, of a specific player
     * @return playerUnitsList (List)
     */
    public List<Unit> getPlayerUnitsList() {
        return playerUnitsList;
    }

    /**
     * Tests if a player is defeated, depending if it's list of usable units is empty or not
     * @return defeated (Boolean)
     */
    public boolean isDefeated() {

        if (playerUnitsList != null) {
            for (Unit unit : playerUnitsList) {
                if (unit.getHp() <= 0) {
                    getOwnerArea().unregisterActor(unit);
                    areasList.remove(unit);

                    if (playerUnitsList.isEmpty()) {
                        defeated = true;
                    }
                }
            }

        }
        return defeated;
    }


    /**
     *  Method allowing to a player to start his round
     */
    public void startTurn() {
        setPlayerState(PlayerStates.NORMAL);
        this.centerCamera();

        for (Unit u : playerUnitsList) {
            u.setIsUsedUnit(false);
        }

    }

    /**
     * Tests if a player has moved out of a cell, without selecting any Unit inside of it
     * @param coordinates (List<DiscreteCoordinates>)
     */
    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {

        if (getCurrentMainCellCoordinates().equals(coordinates.get(0)) && getPlayerState().equals(PlayerStates.SELECT_CELL)) {
            setPlayerState(PlayerStates.NORMAL);
        }
    }
}
