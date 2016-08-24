package rohin.gameengine.Component;

import rohin.gameengine.ECSComponent;

public class ComponentProjection2D extends ECSComponent
{
    // Terminology.
    //
    // - Window
    //   World window, in world coordinates.
    //   window = { xMin, yMin, xMax, yMax }
    //
    // - View Port
    //   Screen view port (window), in screen coordinates. We project from the world window, to the screen view port.
    //   viewPort = { xMin, yMin, xMax, yMax }
    //   Note:
    //   - Screen coordinates are in fact integers. However, we use floating point values in order to maintain mathematical compatibility
    //     with the rest of our calculations.
    //   - Once we have completed our calculations, then we can just cast to integers when populating the arguments of our graphics hardware calls. 
    //
    // - Origin
    //   World coordinate to screen coordinate translation vector.
    //   origin = { 1.0, 1.0 }
    //
    // - Scale
    //   World coordinate to screen coordinate scaling vector. This will usually just be { 1.0, 1.0 }. 
    //   scale = { 1.0, -1.0 }
    //   Note: 
    //   - We usually flip the y axis, since computer screens usually count y from top to bottom.
    //   - As opposed to world coordinates, which are usually defined through traditional Cartesian geometry, counting y from bottom to top.
    // 
    // - Aspect
    //   Screen coordinate aspect ratio.
    //   aspect = { xAspect, yAspect }
    //   e.g. { 1.618, 1.0 } = The golden rectangle ( Golden Ratio aspect ratio).
    //
    // - Layer
    //   This is effectively the z coordinate of the final screen coordinates.
    //   graphics elements are drawing in order of +z max, down to 0.0. Negative values are discouraged.
    
    private final static double M_DEFAULT_SCREEN_WIDTH  = 800;
    private final static double M_DEFAULT_SCREEN_HEIGHT = 600;
    
    public double[] window;
    public double[] origin;
    public double[] scale;
    public double[] viewPort;
    public double[] aspect;
    public double   layer;
    
    public ComponentProjection2D ()
    {
        initialize
        (
            new double[] { -1.0, -1.0, 1.0, 1.0 },                                          // Word window in world coordinates.
            new double[] {  0.0,  0.0 },                                                    // World coordinate to screen coordinate translation vector.
            new double[] {  1.0, -1.0 },                                                    // World coordinate to screen coordinate scaling vector.
            new double[] {  0.0,  0.0, M_DEFAULT_SCREEN_WIDTH, M_DEFAULT_SCREEN_HEIGHT },   // Screen view port (window), in screen coordinates.
            new double[] {  1.0,  1.0 },                                                    // Aspect ratio vector.
            0.0                                                                             // Drawing layer (z coordinate).
        );
    }
    
    public ComponentProjection2D ( double[] window, double[] origin, double[] scale, double[] viewPort, double[] aspect, double layer )
    {
        initialize ( window, origin, scale, viewPort, aspect, layer );
    }
    
    private void initialize ( double[] window, double[] origin, double[] scale, double[] viewPort, double[] aspect, double layer )
    {
        this.window   = window;
        this.origin   = origin;
        this.scale    = scale;
        this.viewPort = viewPort;
        this.aspect   = aspect;
        this.layer    = layer;                        
    }
}



