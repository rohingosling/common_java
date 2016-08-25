package rohin.gameengine.Command;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ICommand;

public class CommandGameLoopExit implements ICommand
{
    private ECSEngine gameEngine;
    
    public CommandGameLoopExit ( ECSEngine gameEngine )
    {
        this.gameEngine = gameEngine;
    }
    
    @ Override
    public void execute ()
    {
        this.gameEngine.setLoopRunning ( false );
    }
}
