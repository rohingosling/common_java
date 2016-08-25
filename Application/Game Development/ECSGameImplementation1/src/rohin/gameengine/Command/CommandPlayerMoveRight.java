package rohin.gameengine.Command;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentPhysics;

public class CommandPlayerMoveRight implements ICommand
{
    private ECSEngine gameEngine;
    private int       playerID;
    
    public CommandPlayerMoveRight ( ECSEngine gameEngine, int playerID )
    {
        this.gameEngine = gameEngine;
        this.playerID   = playerID;
    }
    
    @ Override
    public void execute ()
    {
        // Array indices.
        
        final int X = 0;
        final int Y = 1;        
        
        // Retrieve player entity.
        
        ECSEntity entity = this.gameEngine.getEntity ( this.playerID );
        
        // Retrieve the component/s we wish to work with.
        
        ComponentPhysics physics = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );
        
        // Perform operations on component/s.        
        
        physics.accelerateRight = true; 
    }

}
