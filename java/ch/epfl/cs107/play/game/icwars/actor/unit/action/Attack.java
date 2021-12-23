package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.List;

public class Attack extends Action{

    private int indexOfAttack = 0;
    private int target = 0;
    private ICWarsArea myArea;
    private Unit myUnit;

    private ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f,
                                                new RegionOfInterest(2*36, 26*18, 16, 16));

    private List<Integer> enemyList;

    /**
     * Default Attack constructor
     * @param myUnit
     * @param myArea
     */
    public Attack(Unit myUnit, ICWarsArea myArea) {
        super(myUnit, myArea, "(A)ttack", Keyboard.A);
        this.myUnit = myUnit;
        this.myArea = myArea;
    }


    /**
     * Inflicts damage to a target, by a unit from opposite faction.
     * @param dt
     * @param player
     * @param keyboard
     */
    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {


        //enemyList.addAll(myArea.getIndex(unit.getFaction())); // Why does this doesn't work???
        enemyList = myArea.getTargetIndex(myUnit);

        if (!enemyList.isEmpty()) {
            target = enemyList.get(indexOfAttack%enemyList.size());

            if (keyboard.get(Keyboard.RIGHT).isReleased()) {
                indexOfAttack++;
                indexOfAttack = Math.floorMod(indexOfAttack,enemyList.size());
            }

            else if (keyboard.get(Keyboard.LEFT).isReleased()) {
                indexOfAttack--;
                indexOfAttack = Math.floorMod(indexOfAttack,enemyList.size());
            }

            if (keyboard.get(Keyboard.ENTER).isReleased()) {

                myArea.makeDamageLink(target, myArea.getUnitIndex(myUnit));
                myUnit.setIsUsedUnit(true);

                player.centerCamera();
                player.setPlayerState(RealPlayer.PlayerStates.NORMAL);
            }
        }

        else if (enemyList.isEmpty() || keyboard.get(Keyboard.TAB).isReleased()) {
            System.out.println("No enemies in range: must wait");
            player.centerCamera();
            player.setPlayerState(ICWarsPlayer.PlayerStates.ACTION_SELECTION);

        }
    }

    @Override
    public void doAutoAction(float dt, ICWarsPlayer player) {

    }

    /**
     * draws attacking cursor
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        if (enemyList != null) {

            myArea.centerCameraOnUnit(target);
            cursor.setAnchor(canvas.getPosition().add(1,0));
            cursor.draw(canvas);
        }

    }
}
