package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.List;

public class AIPlayer extends ICWarsPlayer {
    private List<Unit> aiPlayerUnitList; // un joueur AI // a deux units comme RealPlayer
    private Action action;
    private boolean counting;
    private float counter;


    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param units
     */
    public AIPlayer(ICWarsArea area, DiscreteCoordinates position, Unit... units) {
        super(area, position, Faction.ENEMY); // je hardcode faction de ICWarsPlayer à Faction.ENEMY
        /**
         * une for loop pour mettre toutes les CHAQUE unit
         */
        for (Unit u : units) { // select each of its units, in sequence
            if (u != null && u.getFaction().equals(Faction.ENEMY) && waitFor(1, delt)) // set dt = 1
                aiPlayerUnitList.add(u);
            action.doAutoAction(1, 1);

        }

//                unit.getRange().nodeExists(newPositionSelectedUnit())
//            if (unit.getRange() && move(getCurrentCells().indexOf(unit))) { // ?
        // creer une methode qui determine la nouvelle position de l'unite selectionnee
        // trouve la plus proche distance

        // AIPlayer a deux units aussi.
        DiscreteCoordinates.distanceBetween(area.getPlayerSpawnPosition(), area.getEnemySpawnPosition());


    }


    /**
     * determine la nouvelle position de l'unite selectionnee
     */
    public void newPositionSelectedUnit() {
        // position entre ennemie et unite selectionnee
        DiscreteCoordinates positionEnemy = new DiscreteCoordinates(getCurrentMainCellCoordinates().x, getCurrentMainCellCoordinates().y);

        if (getRange) {

        }


    }


    /**
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();

        if (getPlayerState().equals(PlayerStates.NORMAL)
                || getPlayerState().equals(PlayerStates.SELECT_CELL)
                || getPlayerState().equals(PlayerStates.MOVE_UNIT)) {

//            moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
//            moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
//            moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
//            moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        }

        //doAction();

        switchPlayerStates(getPlayerState());


        super.update(deltaTime);
    }


    /**
     * Automaton that changes the behaviour of the enemy
     *
     * @param deltaTime (float)
     */
    public void updateAIPlayerStates(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        switch (getPlayerState()) {
            case IDLE:

                break;
            case NORMAL:
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

                    if (!getCurrentMainCellCoordinates().equals(getLeftCells().get(0))
                            && selectedUnit.getRange().nodeExists(getCurrentMainCellCoordinates())) {

                        //If the unit was repositioned and is in range
                        setPlayerState(PlayerStates.ACTION_SELECTION);
                        System.out.println("State: ACTION_SELECTION");
                    }
                }
                break;

            case ACTION_SELECTION:

                for (Action act : selectedUnit.getAction()) {
                    if (keyboard.get(act.getKey()).isReleased()) {
                        action = act;
                        setPlayerState(PlayerStates.ACTION);
                        System.out.println("State: ACTION");

                        //selectedUnit = null;
                    }
                }
                break;
            case ACTION:
                if (action != null) action.doAction(deltaTime, this, keyboard);
                break;
        }
    }


    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

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

    }


    /**
     * Ensures that value time elapsed before returning true
     *
     * @param dt    elapsed time
     * @param value waiting time (in seconds)
     * @return true if value seconds has elapsed , false otherwise
     */
    private boolean waitFor(float value, float dt) {
        if (counting) {
            counter += dt;
            if (counter > value) {
                counting = false;
                return true;
            }
        } else {
            counter = 0f;
            counting = true;
        }
        return false;
    }

}
