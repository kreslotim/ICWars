package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable {

    protected List<Unit> unitsList;
    protected List<Area> areasList = new ArrayList<>();
    protected List<Unit> memorisedUnits = new ArrayList<>();
    private playerStates state;
    protected Unit selectedUnit; // à utiliser avec Interactable
    protected final ICWarsPlayerGUI gui = new ICWarsPlayerGUI(0, this); // @TODO


    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);
        for (Unit unit : units) {
            area.registerActor(unit);
            unitsList = new ArrayList<>(List.of(units));
        }
        //player = new ICWarsPlayer(area, position, ICWarsActor.Faction.values());
        state = playerStates.IDLE;
    }



    /**
     * get the state of the player
     */
    public playerStates getState() {
        return state;
    }

    /**
     * set the state of the player
     * @param state
     */
    public void setState(playerStates state) {
        this.state = state;
    }

    public void startTurn() {
        setState(playerStates.NORMAL);

    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {

        if (getCurrentMainCellCoordinates().equals(coordinates.get(0)) && getState() == playerStates.SELECT_CELL) {
            setState(playerStates.NORMAL);
            System.out.println("onLeaving works!");
        }
        // Entreprendre les traitements nécessaires lorsque player quitte cellule
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        if (unitsList != null) {
            for (Unit unit : unitsList) {
                if (unit.getCurrentHp() <= 0) {
                    getOwnerArea().unregisterActor(unit);
                    areasList.remove(unit);
                }
            }

        }
        super.update(deltaTime);
    }



    public enum playerStates {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION
    }


    public boolean used(Unit selectedUnit) {
        return memorisedUnits.contains(selectedUnit);
    }


    public void selectUnit(int unitIndex) {
        if (unitsList.size() > unitIndex) {
            selectedUnit = unitsList.get(unitIndex);
            gui.setSelectedUnit(unitsList.get(unitIndex));
            this.unitsList.get(unitIndex).setUsedUnit();
        }
    }

    public int getUnitIndex() {

        int index = 0;
        int myX = getCurrentMainCellCoordinates().x;
        int myY = getCurrentMainCellCoordinates().y;

        for (Unit unit : unitsList) {

            if ((int) unit.getPosition().x == myX && (int) unit.getPosition().y == myY) {
                return index;
            }
            ++index;
        }
        return 99999;
    }



    /**
     * Method that switches the states of the player
     *
     * @param playerStates
     */
    public void switchStates(playerStates playerStates) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        switch (playerStates) {
            case IDLE:
                // do nothing
                break;
            case NORMAL:
                System.out.println("Normal");
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    setState(playerStates.SELECT_CELL);
                    System.out.println("State: SELECT_CELL");

                }
                if (keyboard.get(Keyboard.TAB).isReleased()) {
                    setState(playerStates.IDLE);
                }
                break;
            case SELECT_CELL:
                this.selectUnit(getUnitIndex());
                if (selectedUnit != null) {

                    memorisedUnits.add(selectedUnit);
                    System.out.println("memorized!");
                    setState(playerStates.MOVE_UNIT);
                    System.out.println("State: MOVE_UNIT");
                }

                else {
                    onLeaving((getLeftCells()));
                    System.out.println("Left: "+getLeftCells());
                    System.out.println("Cursor: "+getCurrentMainCellCoordinates());
                } // onLeaving() sur la position de l'unité...
                break;

            case MOVE_UNIT:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    // move the selectedUnit to currentSpace
                    // mark memorisedUnit as used

                    selectedUnit.changePosition(new DiscreteCoordinates(getCurrentMainCellCoordinates().x,getCurrentMainCellCoordinates().y));
                    selectedUnit = null;
                    setState(playerStates.NORMAL);
                    System.out.println("State: NORMAL");
                }
                break;
            case ACTION_SELECTION:
                break;
            case ACTION:
                break;


        }
    }
}
