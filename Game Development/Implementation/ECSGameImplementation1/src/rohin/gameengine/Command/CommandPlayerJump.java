package rohin.gameengine.Command;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentTransform;

public class CommandPlayerJump implements ICommand
{
    private ECSEngine gameEngine;
    private int       playerID;
    
    public CommandPlayerJump ( ECSEngine gameEngine, int playerID )
    {
        this.gameEngine = gameEngine;
        this.playerID   = playerID;
    }
    
    @ Override
    public void execute ()
    {        
        // Retrieve player entity.
        
        ECSEntity entity = this.gameEngine.getEntity ( this.playerID );
        
        // REtrieve the component/s we wish to work with.
        
        ComponentTransform transform = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
        
        // Perform operations on component/s.
         
        double scaleXY = 1.0;
        
        transform.scale.setVector ( scaleXY, scaleXY );        
    }
}
