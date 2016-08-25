package rohin.gameengine.Component;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;

public class ComponentPhysics extends ECSComponent
{  
    // Component attributes.
    
    // Newtonian parameters.
        
    public Vector2D velocity;           // Velocity vector.
    public Vector2D acceleration;       // Acceleration.
    public Vector2D force;              // External force vector.
    public double   mass;               // Mass
    public double   vMin;               // Minimum velocity.
    public double   vMax;               // Maximum velocity.
    
    // Acceleration flags.
    
    public Boolean  accelerateUp;      // Acceleration flag.
    public Boolean  accelerateDown;    // Acceleration flag.
    public Boolean  accelerateLeft;    // Acceleration flag.
    public Boolean  accelerateRight;   // Acceleration flag.
    
    // Constructor/s
    
    public ComponentPhysics ()
    {
        initialize
        (
            0.0,
            new Vector2D ( 0.0, 0.0 ),
            new Vector2D ( 0.0, 0.0 ),
            new Vector2D ( 0.0, 0.0 )
        );
    }
    
    public ComponentPhysics ( double m, Vector2D v, Vector2D a, Vector2D f )
    {
        initialize ( m, v, a, f );
    }
    
    // Component initializer.
    
    private void initialize ( double m, Vector2D v, Vector2D a, Vector2D f )
    {
        this.mass = m;
        
        this.velocity     = new Vector2D ( v );
        this.acceleration = new Vector2D ( a );
        this.force        = new Vector2D ( f );
        
        // Constraints.
        
        this.vMin = 0.0;
        this.vMax = 1.5;
        
        // Initialize accelerator pedals.
        
        Boolean accelerator  = false;        
        this.accelerateUp    = accelerator;
        this.accelerateDown  = accelerator;
        this.accelerateLeft  = accelerator;
        this.accelerateRight = accelerator;
    }
}
