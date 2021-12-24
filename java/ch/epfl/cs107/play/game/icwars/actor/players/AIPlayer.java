package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer extends ICWarsPlayer {
    private List<Unit> aiPlayerUnitList;
    private List<Unit> attackableUnits;
    private final String[] cursors = new String[]{"icwars/allyCursor", "icwars/enemyCursor"};

    private Sprite sprite;
    private Unit selectedUnit;
    private Action action;
    private int countIndex;
    private boolean counting;
    private float counter;

    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param units
     */
    public AIPlayer(ICWarsArea area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, Faction.ENEMY);
        faction = Faction.ENEMY;
        for (Unit unit : units) {
            area.registerActor(unit);
            area.registerUnit(unit);
            aiPlayerUnitList = new ArrayList<>(List.of(units));
        }

        String cursorName = cursors[1];
        Sprite sprite = new Sprite(cursorName, 1f, 1f, this, null, new Vector(0f, 0f));
        this.sprite = sprite;

        this.setPlayerState(PlayerStates.IDLE);


        /**
         * une for loop pour mettre toutes les CHAQUE unit
         *
         * for (Unit u : units) { // select each of its units, in sequence
         * if (u != null && u.getFaction().equals(Faction.ENEMY) && waitFor(1, 1) &&
         * action!=null) {
         * // set dt = 1
         * aiPlayerUnitList.add(u);
         * action.doAutoAction(1,this);
         * }
         *
         * }
         */

    }

    /**
     * determine la nouvelle position de l'unite selectionnee
     */
    public void newPositionSelectedUnit() {
        // position entre ennemie et unite selectionnee
        DiscreteCoordinates positionEnemy = new DiscreteCoordinates(getCurrentMainCellCoordinates().x,
                getCurrentMainCellCoordinates().y);

    }

    public DiscreteCoordinates findTarget(Unit selectedUnit) {
        return new DiscreteCoordinates((int) selectedUnit.getPosition().x, (int) selectedUnit.getPosition().y);
    }

    /**
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateAIPlayerStates(deltaTime);
    }

    /**
     * Automaton that changes the behaviour of the AIPlayer
     *
     * @param deltaTime (float)
     */
    public void updateAIPlayerStates(float deltaTime) {

        switch (getPlayerState()) {
            case IDLE:
                countIndex = 0;

                break;
            case NORMAL:
                selectedUnit = null;
                action = null;
                this.setPlayerState(PlayerStates.SELECT_CELL);

                break;
            case SELECT_CELL:
                if (countIndex < aiPlayerUnitList.size() && selectedUnit != null && !selectedUnit.isUsed()) {
                    selectedUnit = aiPlayerUnitList.get(countIndex);
                    changePosition(selectedUnit.getCurrentCells().get(0));
                    countIndex++;
                    this.setPlayerState(PlayerStates.MOVE_UNIT);

                } else if (waitFor(1, deltaTime)) {
                    this.setPlayerState(PlayerStates.IDLE);
                }

                break;

            case MOVE_UNIT:
                if (waitFor(1, deltaTime)) {
                    ((ICWarsArea) getOwnerArea()).centerCameraOnUnit(aiPlayerUnitList.indexOf(selectedUnit));
                    DiscreteCoordinates newPosition;
                    newPosition = findTarget(selectedUnit);

                    selectedUnit.changePosition(newPosition);

                    this.setPlayerState(PlayerStates.ACTION_SELECTION);
                }
                break;

            case ACTION_SELECTION:

                for (Action act : selectedUnit.getAction()) {
                    action = act;
                    this.setPlayerState(PlayerStates.ACTION);


                }
                break;
            case ACTION:
                if (action != null)
                    action.doAutoAction(deltaTime, this);
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!getPlayerState().equals(PlayerStates.IDLE))
            sprite.draw(canvas);

        if (getPlayerState().equals(PlayerStates.ACTION)) {
            if (action != null) {
                action.draw(canvas);
            }
        }
    }

    @Override
    public boolean takeCellSpace() {
        return true;
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