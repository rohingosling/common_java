package rohin.gameengine.Component;

public class ComponentShapeCircle extends ComponentShape
{
    private final double DEFAULT_RADIUS = 0.1;
    
    public double r;  // Radius.
    
    public ComponentShapeCircle ()           { initialize ( DEFAULT_RADIUS ); }
    public ComponentShapeCircle ( double r ) { initialize ( r              ); }    
    
    private void initialize ( double r )
    {   
        this.r = r;
    }
}
