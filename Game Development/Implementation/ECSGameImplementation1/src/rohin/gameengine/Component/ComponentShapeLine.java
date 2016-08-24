package rohin.gameengine.Component;

public class ComponentShapeLine extends ComponentShape
{    
    public double[] v0;     // Vertex 0.
    public double[] v1;     // Vertex 1.
    
    public ComponentShapeLine ()                                             { initialize ( 0.0,   0.0,    0.0,   0.0   ); }
    public ComponentShapeLine ( double[] v0, double[] v1 )                   { initialize ( v0[X], v0 [Y], v1[X], v1[Y] ); }
    public ComponentShapeLine ( double x0, double y0, double x1, double y1 ) { initialize ( x0,    y0,     x1,    y1    ); }
    
    private void initialize ( double x0, double y0, double x1, double y1 )
    {
        this.v0 = new double[] { x0, y0 };
        this.v1 = new double[] { x1, y1 };
    }
}
