package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {
    public final static float FONT_SIZE = 20f;
    private Unit selectedUnit;
    private ICWarsPlayer player;

    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player) {
        this.player = player;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    /**
     * invoking the method
     * drawRangeAndPathTo on the unit selected by the player whose display it is in charge
     * of, so that it draws the unit’s range of action and the path separating the unit from
     * the player
     **/
    @Override
    public void draw(Canvas canvas) {
        if (selectedUnit != null) {
            selectedUnit.drawRangeAndPathTo(new DiscreteCoordinates((int) player.getPosition().x,
                    (int) player.getPosition().y), canvas);
        }


    }
}
