package rohin.gameengine.System;

import rohin.gameengine.ConsoleLogger;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentProjection2D;
import rohin.gameengine.Component.ComponentTransform;

public class SystemExample extends ECSSystem
{       
    // Fields.
        
    Boolean       loggingEnabled;
    ConsoleLogger logger;
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public SystemExample( ECSEngine owner )
    {
        // Initialize ECS object parameters.
        
        this.id    = Constants.SYSTEM_EXAMPLE;
        this.name  = "SYSTEM_EXAMPLE";        
        this.owner = owner;
        
        // Initialize system management parameters.
        
        this.loggingEnabled = false;
        this.logger         = new ConsoleLogger ( this, this.loggingEnabled );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Override implementation of ECSSystem.Update. 
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void update ( long t )
    {   
        ECSEngine             engine     = (ECSEngine) this.owner;
        ComponentTransform    transform  = new ComponentTransform ();
        ComponentProjection2D projection = new ComponentProjection2D ();
        
        // Iterate through all entities, filtering by those that contain the specific set of components, that this system requires to work with.
        
        for ( ECSEntity entity : engine.getEntities ().values () )
        {   
            if ( entity.hasComponents ( transform, projection ) )
            {
                transform  = ( ComponentTransform )    entity.getComponent ( Constants.COMPONENT_TRANSFORM );
                projection = ( ComponentProjection2D ) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );
                
                // Start working with the entities components.
                
                // ...
                
                // Console logger.
                
                logger.log ();
            }
        }        
    }
}

