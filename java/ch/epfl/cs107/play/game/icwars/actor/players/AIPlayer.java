package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.List;

public class AIPlayer extends ICWarsPlayer {
    private List<Unit> aiPlayerUnitList; // un joueur AI // a deux units comme RealPlayer

    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param units
     */
    public AIPlayer(ICWarsArea area, DiscreteCoordinates position, Unit... units) {
        super(area, position, Faction.ENEMY, units); // je hardcode faction de ICWarsPlayer à Faction.ENEMY
        /**
         * une for loop pour mettre toutes les CHAQUE unit
         */
        for (Unit u : units) { // select each of its units, in sequence
            if (u != null && u.getFaction().equals(Faction.ENEMY)) {
                aiPlayerUnitList.add(u);
            }
        }
        for
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

        //doAction();

        switchPlayerStates(getPlayerState());


        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
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

//    @Override
//    public boolean takeCellSpace() {
//        return false;
//    }
//
//    @Override
//    public boolean isCellInteractable() {
//        return false;
//    }
//
//    @Override
//    public boolean isViewInteractable() {
//        return false;
//    }
//
//    @Override
//    public void acceptInteraction(AreaInteractionVisitor v) {
//
//    }


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
