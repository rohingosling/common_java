package rohin.gameengine.Component;

public class ComponentShapeTriangle extends ComponentShape
{   
    public double[] v0;     // Vertex 0.
    public double[] v1;     // Vertex 1.
    public double[] v2;     // Vertex 2.
    
    public ComponentShapeTriangle ()
    {
        initialize ( 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 );
    }
    
    public ComponentShapeTriangle ( double[] v0, double[] v1, double[] v2 )
    {
        initialize ( v0[X], v0 [Y], v1[X], v1[Y], v2[X], v2[Y] );
    }
    
    public ComponentShapeTriangle ( double x0, double y0, double x1, double y1, double x2, double y2 )
    {
        initialize ( x0, y0, x1, y1, x2, y2 );
    }
    
    private void initialize ( double x0, double y0, double x1, double y1, double x2, double y2 )
    {
        this.v0 = new double[] { x0, y0 };
        this.v1 = new double[] { x1, y1 };
        this.v2 = new double[] { x2, y2 };
    }
}
