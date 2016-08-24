package rohin.gameengine.Component;

import rohin.gameengine.ECSComponent;

public class ComponentShape extends ECSComponent
{   
    // Constants
    
    static final int X = 0;
    static final int Y = 1;
    static final int Z = 2;
    
    // Fields (ECS Component Data).
    
    public ComponentTransform transform;    
    
    // Constructor/s.
    
    public ComponentShape ()                               { initialize ( null      ); }
    public ComponentShape ( ComponentTransform transform ) { initialize ( transform ); }
    
    // Initialize object.
    
    private void initialize ( ComponentTransform transform )
    {
        if ( transform == null )
        {
            this.transform = new ComponentTransform ();
        }
        else
        {
            this.transform = transform;
        }
    }
}
