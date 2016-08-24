package rohin.gameengine.Command;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ICommand;

public class CommandPlayerFire implements ICommand
{
    private ECSEngine gameEngine;
    private int       playerID;
    
    public CommandPlayerFire ( ECSEngine gameEngine, int playerID )
    {
        this.gameEngine = gameEngine;
        this.playerID   = playerID;
    }
    
    @ Override
    public void execute ()
    {   
    }
}
