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
    private ICWarsArea myArea;

    private ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f,
                                                new RegionOfInterest(2*36, 26*18, 16, 16));

    private List<Integer> enemyList;

    public Attack(Unit unit, ICWarsArea area) {
        super(unit, area, "(A)ttack", Keyboard.A);
        myArea = area;

    }


    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {


        //enemyList.addAll(myArea.getIndex(unit.getFaction())); // Why does this doesn't work???
        enemyList = myArea.getIndex(unit.getFaction());

        if (keyboard.get(Keyboard.RIGHT).isReleased() || keyboard.get(Keyboard.LEFT).isReleased()) {

            indexOfAttack++;
            System.out.println(enemyList.get(indexOfAttack %enemyList.size()));
        }

        if (keyboard.get(Keyboard.ENTER).isReleased()) {
            System.out.println("Attacking");
            myArea.doDamage(indexOfAttack% enemyList.size());
            unit.setIsUsedUnit(true);

            player.centerCamera();
            player.setPlayerState(RealPlayer.PlayerStates.NORMAL);
        }

        if (enemyList.isEmpty() || keyboard.get(Keyboard.TAB).isReleased()) {
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
