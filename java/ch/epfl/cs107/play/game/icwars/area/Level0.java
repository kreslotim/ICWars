package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.Tank;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Level0 extends ICWarsArea {


    protected void createArea() {
        // Base
        registerActor(new Background(this));
        Tank tank1 = new Tank(this,new DiscreteCoordinates(2,5), ICWarsActor.Faction.ALLY);
        Soldier soldier1 = new Soldier(this, new DiscreteCoordinates(3,5), ICWarsActor.Faction.ALLY);
        RealPlayer player = new RealPlayer( this, getPlayerSpawnPosition(), ICWarsActor.Faction.ALLY, tank1, soldier1);
        //registerActor(player);  //enregistrement deux fois
        //unregisterActor(player);
    }

    @Override
    public String getTitle() {
        return "icwars/Level0";
    }

    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0,0);
    }
}
