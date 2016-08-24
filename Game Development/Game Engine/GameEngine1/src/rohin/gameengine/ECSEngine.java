
package rohin.gameengine;

import java.util.*;

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Main Application program.
//
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
//
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public abstract class ECSEngine extends ECSObject
{
    // @formatter:off
    
    // Constants
    
    public static final Boolean M_GAME_LOOP_DEFAULT_TARGET_ENABLED   = true;
    public static final double  M_GAME_LOOP_DEFAULT_FPS_TARGET       = 90;
    public static final int     M_GAME_LOOP_DEFAULT_LOOP_DELAY_FIXED = 1000;
    public static final int     M_GAME_LOOP_DEFAULT_LOOP_DELAY_MIN   = 5;
    public static final int     M_DEFAULT_SYSTEM_HASH_MAP_SIZE       = 16;      // Number of systems before the hash map needs to be resized.
    public static final int     M_DEFAULT_ENTITY_HASH_MAP_SIZE       = 1024;    // Number of entities before the hash map needs to be resized.
    
    
    // Fields.
    
    // Game engine parameters.

    private HashMap <Integer, ECSSystem> systems;           // List of game entities.
    private HashMap <Integer, ECSEntity> entities;          // List of game systems.    
    private CommandManager               commandManager;    // Command invocation manager.
    private ResourceManager              resourceManager;   // Resource cache manager.
    private Boolean                      loggingEnabled;    // logging switch.
    private ConsoleLogger                logger;            // Console logger.
    private String                       resourcePath;      // Disk location for all game assets. e.g. Images, sounds, vector models, etc.
    
    // Game loop management.
    
    private Boolean                     loopRunning;        // Loop state. Set to true to run the game loop. Set to false to exit the game loop.
    private int                         loopDelayFixed;     // Game loop, frame rate regulator. Fixed delay, measured in ms.
    private int                         loopDelayVariable;  // Game loop, frame rate regulator. Dynamic delay, that adjusts to maintain target FPS.    
    private int                         loopDelayMin;       // Minimum loop iteration delay.
    private double                      fpsTarget;          // Target Frames Per Second.
    private Boolean                     fpsTargetEnabled;   // Choose whether to use a fixed loop delay, or a target FPS.
    
    // Game loop monitoring
    
    private double           fps;                       // Measured frames Per Second. (Animation frequency).
    private long             loopIterationTime;         // Loop iteration time. Used for display and debugging.
    
    // Accessors and mutators.

    public HashMap <Integer, ECSSystem> getSystems         () { return this.systems;          }
    public HashMap <Integer, ECSEntity> getEntities        () { return this.entities;         }    
    public Boolean                      isloggingEnabled   () { return this.loggingEnabled;   }
    public Boolean                      isLoopRunning      () { return this.loopRunning;      }
    public Boolean                      isFPSTargetEnabled () { return this.fpsTargetEnabled; }    
    public int                          getLoopDelayFixed  () { return this.loopDelayFixed;   }
    public int                          getLoopDelayMin    () { return this.loopDelayMin;     }
    public double                       getFPSTarget       () { return this.fpsTarget;        }        
    public CommandManager               getCommandManager  () { return this.commandManager;   }
    public String                       getResourcePath    () { return this.resourcePath;     }
    public ResourceManager              getResourceManager () { return this.resourceManager;  }    
    
    public void setCommandManager   ( CommandManager  commandManager   ) { this.commandManager   = commandManager;   }
    public void setloggingEnabled   ( Boolean         loggingEnabled   ) { this.loggingEnabled   = loggingEnabled;   }
    public void setLoopRunning      ( Boolean         loopRunning      ) { this.loopRunning      = loopRunning;      }
    public void setLoopDelayFixed   ( int             loopDelayFixed   ) { this.loopDelayFixed   = loopDelayFixed;   }
    public void setLoopDelayMin     ( int             loopDelayMin     ) { this.loopDelayMin     = loopDelayMin;     }
    public void setFPSTargetEnabled ( Boolean         fpsTargetEnabled ) { this.fpsTargetEnabled = fpsTargetEnabled; }
    public void setFPSTarget        ( double          fpsTarget        ) { this.fpsTarget        = fpsTarget;        }
    public void setResourcePath     ( String          resourcePath     ) { this.resourcePath     = resourcePath;     }
    public void setResourceManager  ( ResourceManager resourceManager  ) { this.resourceManager  = resourceManager;  }
    
    // Abstract methods.
    
    protected abstract void swapBuffer ();     // Swap the double buffers in order to show the most recently drawn animation frame.
    
    // Constructors.

    public ECSEngine ()                  { initialize ( null  ); }
    public ECSEngine ( ECSEngine owner ) { initialize ( owner ); }    
    
    // Initialize
    
    private void initialize ( ECSEngine owner )
    {
        // Initialize game engine parameters.
        
        this.owner           = owner;
        this.systems         = new HashMap <Integer, ECSSystem> ( M_DEFAULT_SYSTEM_HASH_MAP_SIZE );
        this.entities        = new HashMap <Integer, ECSEntity> ( M_DEFAULT_ENTITY_HASH_MAP_SIZE );
        this.commandManager  = new CommandManager ();
        this.resourceManager = new ResourceManager ();
        this.loggingEnabled  = true;
        this.logger          = new ConsoleLogger ( this, this.loggingEnabled );
        
        // Initialize game loop parameters.
        
        this.loopRunning       = true;
        this.fpsTargetEnabled  = M_GAME_LOOP_DEFAULT_TARGET_ENABLED;
        this.fpsTarget         = M_GAME_LOOP_DEFAULT_FPS_TARGET;
        this.loopDelayFixed    = M_GAME_LOOP_DEFAULT_LOOP_DELAY_FIXED;       
        this.loopDelayMin      = M_GAME_LOOP_DEFAULT_LOOP_DELAY_MIN;
        this.loopDelayVariable = 0;        
        
        // Initialize loop monitoring points.
                
        this.loopIterationTime = 0;
        this.fps               = 0;                  
    }
    
    // @formatter:on

    // Methods.

    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Game loop.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public void run ()
    {
        this.logger.log ();
        
        long start = 0;
        long stop  = 0;
        long t     = 0;

        this.loopRunning = true;

        while ( this.loopRunning )
        {
            start = System.currentTimeMillis ();                // Start the clock.
            
            this.commandManager.flush ();                       // Flush and execute commands in the command queue.
            
            for ( ECSSystem system : this.systems.values () )   // Loop through all game systems.
            {
                system.update ( t );                            // Call Update on each of them. 
            }
            
            regulateFrameRate ();                               // Give the CPU some time to do other things other than spin the loop.

            stop = System.currentTimeMillis ();                 // Stop the clock.
            t    = stop - start;                                // Calculate the lap time for this iteration.
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Add system.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void addSystem ( Integer key, ECSSystem system )
    {
        this.systems.put ( key, system );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Add entity.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
        
    public void addEntity ( Integer key, ECSEntity entity )
    {
        this.entities.put ( key, entity );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // ECS entity.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSEntity getEntity ( int entityID)
    {
        return this.entities.get ( entityID );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------    
    // Variable frame rate regulation delay.
    //
    // Used to implement a variable period frame rate regulation delay, that
    // attempts to normalize the frame rate to a specified target frame rate.
    // 
    // The target frame rate is specified internally through, fpsTarget.    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    private void regulateFrameRate ()
    {
        if ( this.fpsTargetEnabled )
        {
            // Calculate dynamic period loop delay.
            //
            // - If we are spinning faster than the target FPS, then delay the loop for the remaining time, until target FPS is reached..
            // - If we are spinning slower than the target FPS, then just force the minimum delay.
            //
            //   Note:
            //   We don't allow a minimum delay of zero, for risk of the game loop hogging the the CPU and potentially hanging the main application thread.                                   

            if ( this.fpsTarget > 0.0 )
            {
                this.loopDelayVariable = ( int ) ( 1000.0 / this.fpsTarget );
            }
            else
            {
                this.loopDelayVariable = this.loopDelayMin;
            }

            delay ( this.loopDelayVariable );             
        }
        else
        {
            // Fixed period loop delay.

            delay ( this.loopDelayFixed );
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------    
    // Frame rate delay.
    // 
    // Used to implement a fixed period frame rate delay.
    // 
    // Arguments:
    // - period
    //   The period to dealy in milliseconds.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void delay ( long period )
    {           
        try
        {
            Thread.sleep ( period );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace ();
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------    
    // Override: toString
    // 
    // Used to implement a fixed period frame rate delay.
    // 
    // Arguments:
    // - period
    //   The period to delay in milliseconds.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public String printGameObjects ()
    {
        final String EMPTY             = "";
        final String SPACE             = " ";
        final String NEW_LINE          = "\n";        
        final String INDENT            = SPACE + SPACE;
        final String BULLET1           = "- ";
        final String BULLET2           = "+ ";
        final String PARENTHESUS_OPEN  = " ( ";
        final String PARENTHESUS_CLOSE = " )";
        final String DELIMITER         = ", ";
        final String LABEL_TERMINATOR  = ": ";
        final String LABEL_GAME_ENGINE = "ECSEngine" + LABEL_TERMINATOR;
        final String LABEL_SYSTEMS     = "Systems"   + LABEL_TERMINATOR;
        final String LABEL_ENTITIES    = "Entities"  + LABEL_TERMINATOR;
        
        String gameObjects = new String();
        
        // List header and title.
        
        gameObjects =  EMPTY;
        gameObjects += NEW_LINE + NEW_LINE;
        gameObjects += INDENT + LABEL_GAME_ENGINE + this.getClass ().getSimpleName ();
        gameObjects += NEW_LINE + NEW_LINE;
        gameObjects += INDENT + BULLET1 + LABEL_SYSTEMS;
        gameObjects += NEW_LINE;
        
        // List systems.
        
        for ( ECSSystem system : this.systems.values () )
        {
            gameObjects += INDENT + INDENT + BULLET2 + system.getClass ().getSimpleName ();
            gameObjects += NEW_LINE;
        }
        
        // List Entities and their components.
        
        gameObjects += NEW_LINE;        
        gameObjects += INDENT + BULLET1 + LABEL_ENTITIES;
        gameObjects += NEW_LINE;
        
        for ( ECSEntity entity : this.entities.values () )
        {            
            int componentCount = entity.getComponents ().size ();
            
            gameObjects += INDENT + INDENT + BULLET2 + entity.getClass ().getSimpleName ();
            
            if ( componentCount > 0)
            {
                // List components.
                
                int i = 0;
                gameObjects += PARENTHESUS_OPEN;
                
                for ( ECSComponent component : entity.getComponents ().values () )
                {
                    gameObjects += component.getClass ().getSimpleName ();
                    
                    if ( i < componentCount - 1 )
                    {
                        gameObjects += DELIMITER;           // If there are still more components, add a delimiter, and continue.
                    }
                    else
                    {
                        gameObjects += PARENTHESUS_CLOSE;   // If there are no more components to show, then close the parentheses, and exit the loop.
                    }
                    ++i;
                }
            }
            gameObjects += NEW_LINE;
        }
        
        // Return our nicely formatted string.
        
        return gameObjects;
    }
}











