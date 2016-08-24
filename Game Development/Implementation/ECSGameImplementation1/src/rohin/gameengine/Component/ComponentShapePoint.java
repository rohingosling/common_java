package rohin.gameengine.Component;

public class ComponentShapePoint extends ComponentShape
{   
    public double[] v;
    
    public ComponentShapePoint ()                     { initialize ( 0.0,     0.0     ); }
    public ComponentShapePoint ( double[] v )         { initialize ( v [ X ], v [ Y ] ); }
    public ComponentShapePoint ( double x, double y ) { initialize ( x,       y       ); }
    
    private void initialize ( double x, double y )
    {
        this.v = new double[] { x, y };        
    }
}
