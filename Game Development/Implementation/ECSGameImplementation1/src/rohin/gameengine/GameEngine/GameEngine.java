package rohin.gameengine.GameEngine;

import java.awt.image.BufferStrategy;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.Application.Application;

public class GameEngine extends ECSEngine
{
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public GameEngine ( Object owner ) { this.owner = owner; }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Override: Implement the double buffer swap used in the base class game loop.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    final public void swapBuffer ()
    {
        Application    application  = (Application) this.owner;         // Get acceleration reference to the owning application.
        BufferStrategy screenBuffer = application.getScreenBuffer ();   // Get acceleration reference to the double screen buffer aggregated by the owning application.
        
        screenBuffer.show ();                                           // Swap the buffers.
    }
}
