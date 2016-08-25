package rohin.gameengine.Command;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentPhysics;

public class CommandPlayerBreakUp implements ICommand
{
    private ECSEngine gameEngine;
    private int       playerID;
    
    public CommandPlayerBreakUp ( ECSEngine gameEngine, int playerID )
    {
        this.gameEngine = gameEngine;
        this.playerID   = playerID;
    }
    
    @ Override
    public void execute ()
    {   
        // Retrieve player entity.
        
        ECSEntity entity = this.gameEngine.getEntity ( this.playerID );
        
        // Retrieve the component/s we wish to work with.
        
        ComponentPhysics physics = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );
        
        // Perform operations on component/s.        
        
        physics.accelerateUp = false;
    }
}
