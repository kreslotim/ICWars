package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.List;

public class Attack extends Action{

    private int indexOfAttack = 0;
    private ICWarsArea myArea;
    private Unit myUnit;

    private ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f,
                                                new RegionOfInterest(2*36, 26*18, 16, 16));

    private List<Integer> enemyList;

    public Attack(Unit myUnit, ICWarsArea myArea) {
        super(myUnit, myArea, "(A)ttack", Keyboard.A);
        this.myUnit = myUnit;
        this.myArea = myArea;
    }


    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {


        //enemyList.addAll(myArea.getIndex(unit.getFaction())); // Why does this doesn't work???
        enemyList = myArea.getIndex(myUnit.getFaction());

        /**for (int unitIndex : myArea.getIndex(myUnit.getFaction())) {
            DiscreteCoordinates enemyCoords = myArea.getUnitCoords(unitIndex);
            if (myUnit.getRange().nodeExists(enemyCoords)) enemyList.add(unitIndex);
        }
         */

        if (keyboard.get(Keyboard.RIGHT).isReleased() || keyboard.get(Keyboard.LEFT).isReleased()) {

            indexOfAttack++;
            //System.out.println(enemyList.get(indexOfAttack %enemyList.size()));
        }

        if (keyboard.get(Keyboard.ENTER).isReleased() && enemyList != null) {
            myArea.doDamage(enemyList.get(indexOfAttack% enemyList.size()));
            myUnit.setIsUsedUnit(true);

            player.centerCamera();
            player.setPlayerState(RealPlayer.PlayerStates.NORMAL);
        }

        if (enemyList == null || keyboard.get(Keyboard.TAB).isReleased()) {
            System.out.println("No enemies in range: must wait");
            player.centerCamera();
            player.setPlayerState(ICWarsPlayer.PlayerStates.ACTION_SELECTION);

        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemyList != null) {
            myArea.centerCameraOnUnit(enemyList.get(indexOfAttack%enemyList.size()));
            cursor.setAnchor(canvas.getPosition().add(1,0));
            cursor.draw(canvas);
        }

    }
}
