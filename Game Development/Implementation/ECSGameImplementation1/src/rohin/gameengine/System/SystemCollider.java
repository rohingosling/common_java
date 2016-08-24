package rohin.gameengine.System;

import rohin.gameengine.*;
import rohin.gameengine.Application.*;
import rohin.gameengine.Component.*;
import rohin.gameengine.GameEngine.GameEngineTest;

public class SystemCollider extends ECSSystem
{
    // @formatter:off
    
    // Constants - Array Indices.
            
    static final int        X = 0;              // 2D vector index for vector element, X ordinate. 
    static final int        Y = 1;              // 2D vector index for vector element, Y ordinate.
    static final int        Z = 2;              // 2D vector index for vector element, Z ordinate.
    
    // Components used by this system.
    
    ComponentTransform      transform;          // Mathematical transformation. Translation, rotation, scale, etc.
    ComponentGeometry       geometry;           // Geometric structure.
    ComponentPhysics        physics;            // Physics. Mass, velocity, acceleration, etc.
    
    // Application and game engine objects.
    
    private Application     application;        // Parent application.
    private GameEngineTest  engine;             // Parent game engine.
    
    // Fields - Application administration.
    
    private Boolean         loggingEnabled;     // true = enable logging. false = disable logging.
    private ConsoleLogger   logger;             // Console logging object.    
    
    // @formatter:off
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public SystemCollider ( ECSEngine owner )
    {
        // Initialize ECS object parameters.
        
        this.id          = Constants.SYSTEM_COLLIDER;
        this.name        = "SYSTEM_COLLIDER";        
        this.owner       = owner;
        
        // Initialize components.
        
        this.transform  = new ComponentTransform ();                
        this.physics    = new ComponentPhysics ();
                
        // Initialize application and game engine parameters.
        
        this.application = (Application) owner.getOwner ();        
        this.engine      = (GameEngineTest) this.owner;
        
        // Initialize system management parameters.
        
        this.loggingEnabled = false;
        this.logger         = new ConsoleLogger ( this, this.loggingEnabled );        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Description:
    // - Override implementation of ECSSystem.Update.
    //
    // Arguments:
    // 
    // - t
    //   Game loop tick time. i.e Current game loop lap time.
    //
    // Return Value:
    //
    // - N/A
    //
    // Preconditions:
    //
    // - The game engine object must have already been initialized to the game engine we would like to reference.
    //   This will usually be done via the constructor, when an instance of SystemRenderer is created.
    //
    // Postconditions:
    //
    // - All renderable and enabled entities have been rendered to the current graphics context (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void update ( long t )
    {
        try
        {   
            // Iterate through all entities, filtering by those that contain the specific set of components, that this system is intended to work with.
            
            for ( ECSEntity entity : engine.getEntities ().values () )
            {   
                if ( entity.hasComponents ( transform, physics ) )
                {   
                    // Perform physics simulation.
                    
                    updatePhysics ( entity, t );
                    
                    // Console logger.
                    
                    logger.log ();
                }
            } 
            
            // Swap the double buffers.
                    
            engine.swapBuffer ();
        }
        catch ( Exception e )
        {   
            TextFormat.printFormattedException ( e, true );
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // renderGeometry 
    //
    // Description:
    //
    // - Render entity geometry.
    //
    // Arguments:
    //
    // - entity
    //   The ECS entity that we would like to render.
    //
    // Return Value:
    //
    // - N/A
    //
    // Preconditions:
    //
    // - Component objects must already be initialized.
    //
    // Postconditions:
    //
    // - The selected renderable objects have been rendered on the current graphics context (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void updatePhysics ( ECSEntity entity, long t )
    {
        // Constants.
        
        final double SCREEN_HEIGHT        = 600.0;
        final double FRICTION_COEFFICIENT = 0.0001 / SCREEN_HEIGHT;
        final double ACCELERATION         =  0.001 / SCREEN_HEIGHT;
        
        // Initialize working variables.
        
        double ax = 0.0;
        double ay = 0.0;
        
        // Retrieve the entity's components.
        
        this.transform = ( ComponentTransform ) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
        this.physics   = ( ComponentPhysics )   entity.getComponent ( Constants.COMPONENT_PHYSICS );
        
        // Initialize calculation variables.
        
        Vector2D a = new Vector2D ( this.physics.acceleration );
        Vector2D v = new Vector2D ();    
        Vector2D d = new Vector2D ();
        double   p = FRICTION_COEFFICIENT;
        
        // Apply accelerator.
        
        if ( this.physics.accelerateUp    ) { ay += ACCELERATION; }
        if ( this.physics.accelerateDown  ) { ay -= ACCELERATION; }
        if ( this.physics.accelerateRight ) { ax += ACCELERATION; }
        if ( this.physics.accelerateLeft  ) { ax -= ACCELERATION; }
        
        a.setVector ( ax, ay );
        
        // Apply Newtonian mechanics.
        
        v = a.scale ( t );      // acceleration: a = v/t  ↔  v = a·t (Newtonian acceleration). 
        d = v.scale ( t );      // velocity:     v = d/t  ↔  d = v·t (Newtonian velocity).
        
        // Apply friction coefficient.
        
        if ( ax > 0.0 ) { if ( ax - p > 0.0 ) ax -= p; else ax = 0.0; }
        if ( ay > 0.0 ) { if ( ay - p > 0.0 ) ay -= p; else ay = 0.0; }
        if ( ax < 0.0 ) { if ( ax + p < 0.0 ) ax += p; else ax = 0.0; }
        if ( ay < 0.0 ) { if ( ay + p < 0.0 ) ay += p; else ay = 0.0; }
        
        a.setVector ( ax, ay );
        
        // Update physics.
        
        this.physics.acceleration.setVector ( a );
        this.physics.velocity.setVector     ( v );
        
        // Update translation.
        
        this.transform.translation = this.transform.translation.add ( d );
    }
}









