package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.ArrayList;
import java.util.List;

public class Attack extends Action{

    private int count = 0;


    private ImageGraphics cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f,
                                                new RegionOfInterest(2*18, 26*18, 16, 16));

    private List<Integer> enemyList = new ArrayList<>();

    public Attack(Unit unit, ICWarsArea area) {
        super(unit, area, "(A)ttack", Keyboard.A);

        enemyList = area.unitIndexList(unit.getFaction());

    }


    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {

        
        if (keyboard.get(Keyboard.RIGHT).isReleased() || keyboard.get(Keyboard.LEFT).isReleased()) {
            count++;
            enemyList.get(count%enemyList.size());
        }

        if (keyboard.get(Keyboard.ENTER).isReleased()) {
            //attack();
        }



    }

    @Override
    public void draw(Canvas canvas) {
     cursor.setAnchor(canvas.getPosition().add(1,0));
     cursor.draw(canvas);
    }
}
