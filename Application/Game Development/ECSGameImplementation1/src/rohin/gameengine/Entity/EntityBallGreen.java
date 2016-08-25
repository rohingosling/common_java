package rohin.gameengine.Entity;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentPhysics;
import rohin.gameengine.Component.ComponentProjection2D;
import rohin.gameengine.Component.ComponentResourceSprite;
import rohin.gameengine.Component.ComponentTransform;

public class EntityBallGreen extends ECSEntity
{
    // Constructor/s
    
    public EntityBallGreen ( ECSEngine owner )
    {   
        // Initialize ECS object parameters.
        
        this.id    = Constants.ENTITY_BALL_GREEN;   // Unique entity ID.
        this.name  = "ENTITY_BALL_GREEN";           // Internal fileName.        
        this.owner = owner;                         // PArent object. For in case we want to talk with the parent game engine.
        
        // Add components.
        
        this.addComponent ( Constants.COMPONENT_TRANSFORM,       new ComponentTransform      () );        
        this.addComponent ( Constants.COMPONENT_PHYSICS,         new ComponentPhysics        () );
        this.addComponent ( Constants.COMPONENT_RESOURCE_SPRITE, new ComponentResourceSprite () );
        this.addComponent ( Constants.COMPONENT_PROJECTION_2D,   new ComponentProjection2D   () );
    }
}
