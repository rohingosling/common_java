package rohin.gameengine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;

public class Graphics2DExtended
{
    // Constants.
    
    protected final static int M_DEFAULT_VIEW_WIDTH  = 800;
    protected final static int M_DEFAULT_VIEW_HEIGHT = 600;
    protected final static int M_INDEX_HUE           = 0;
    protected final static int M_INDEX_SATURATION    = 1;
    protected final static int M_INDEX_LIGHTNESS     = 2;
    
    // Fields.
    
    private int        viewWidth;
    private int        viewHeight;
    private Graphics2D g;   
    
    // Accessors and mutators.
    
    public int  getViewWidth  () { return viewWidth;  }
    public int  getViewHeight () { return viewHeight; }
    
    public void setViewWidth  ( int viewWidth )  { this.viewWidth  = viewWidth;  }
    public void setViewHeight ( int viewHeight ) { this.viewHeight = viewHeight; }
    
    // Constructor/s
    
    public Graphics2DExtended ()               { initialize ( M_DEFAULT_VIEW_WIDTH, M_DEFAULT_VIEW_HEIGHT, null ); }    
    public Graphics2DExtended ( Graphics2D g ) { initialize ( M_DEFAULT_VIEW_WIDTH, M_DEFAULT_VIEW_HEIGHT, g    ); }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Class initializer.
    //
    // Used to more conveniently initialize multiple constructors.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initialize ( int width, int height, Graphics2D g )
    {
        this.viewWidth  = width;
        this.viewHeight = height;
        this.g          = g;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Clear the screen to a specified color.
    //
    // Most often used to clear the screen and prepare the drawing surface for a new animation frame.
    // 
    // Arguments:
    //
    // - color
    //   An AWT Color object, used to specify the color to clear the screen to.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void clearScreen ( Color color )
    {
        g.setColor ( color );       
        g.fillRect ( 0, 0, this.viewWidth, this.viewHeight );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Clear the screen to a specified color, specified by HSL color model parameters..
    //
    // Most often used to clear the screen and prepare the drawing surface for a new animation frame.
    // 
    // Arguments:
    //
    // - h
    //   Hue, in the range;         0.0 ≤ h ≤ 1.0
    //
    // - s
    //   Saturation, in the range;  0.0 ≤ s ≤ 1.0
    //
    // - l
    //   Lightness, in the range;   0.0 ≤ l ≤ 1.0
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void clearScreenHSL ( double h, double s, double l )
    {   
        clearScreen ( Color.getHSBColor ( ( float ) h, ( float ) s, ( float ) l ) );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Clear the screen to a specified color, specified by 8-bit, integer, fixed point, RGB color parameters..
    //
    // Most often used to clear the screen and prepare the drawing surface for a new animation frame.
    // 
    // Arguments:
    //
    // - r
    //   Red, in the range;     0 ≤ r ≤ 255
    //
    // - g
    //   Green, in the range;   0 ≤ g ≤ 255
    //
    // - b
    //   Blue, in the range;    0 ≤ b ≤ 255
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
        
    public void clearScreenRGB ( int r, int g, int b )
    {
        // Configure color range constraints.
        
        int colorRangeMin = 0;
        int colorRangeMax = 255;
        
        // Clip any color range overflows that may be found.
        
        r = ( r < colorRangeMin ) ? colorRangeMin : r;
        g = ( g < colorRangeMin ) ? colorRangeMin : g;
        b = ( b < colorRangeMin ) ? colorRangeMin : b;
        
        r = ( r > colorRangeMax ) ? colorRangeMax : r;
        g = ( g > colorRangeMax ) ? colorRangeMax : g;
        b = ( b > colorRangeMax ) ? colorRangeMax : b;
        
        // Convert RGB color model, to HSL color model. 
        
        float[] hsl = Color.RGBtoHSB ( r, g, b, null );     // RGB to HSL.
        float   h   = hsl [ M_INDEX_HUE        ];           // Hue. 
        float   s   = hsl [ M_INDEX_SATURATION ];           // Saturation.
        float   l   = hsl [ M_INDEX_LIGHTNESS  ];           // Lightness.
        
        // Clear the screen using the HSL color model.
        
        clearScreenHSL ( h, s, l );
    }    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Clear the screen to a specified color, specified by unit normalized, real, floating point, RGB color parameters.
    //
    // Most often used to clear the screen and prepare the drawing surface for a new animation frame.
    //
    // Note:
    //
    // - clearScreenRGB ( double, double, double ), is just a floating point overloaded version of clearScreenRGB ( int, int, int ).
    //
    // - double's are used instead of actual floats, and then cast internally within the method, for the sake of convenience.
    //   - Floating point literals in Java, require a post-fix 'f' to specify that a real number literal, happens to be a float, as opposed to a double.
    //   - We are going to presume that Java takes advantage of modern 64 bit machine architecture to process doubles as fast as floats, so that we don't
    //     have to pepper our code with little 'f's' when ever we wish to work with real numbers.
    // 
    // Arguments:
    //
    // - r
    //   Red, in the range;     0.0 ≤ r ≤ 1.0
    //
    // - g
    //   Green, in the range;   0.0 ≤ g ≤ 1.0
    //
    // - b
    //   Blue, in the range;    0.0 ≤ b ≤ 1.0
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void clearScreenRGB ( double fR, double fG, double fB )
    {   
        // Specify 8-bit integer color range.
        
        int integerColorRangeMax = 255;
        
        // Convert unit ranged real RGB color elements, to 8-bit ranged integer RGB color elements.
        
        int iR = (int) ( integerColorRangeMax * fR );
        int iG = (int) ( integerColorRangeMax * fG );
        int iB = (int) ( integerColorRangeMax * fB );
        
        // Clear the screen, using the calculated integer values.
        
        clearScreenRGB ( iR, iG, iB );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Draw a cross hair shape, at a specific location on the screen.
    //
    // Most often used for visualization of an objects geometric center, or cernter of mass. 
    // 
    // Arguments:
    //
    // - x
    //   X ordinate.
    //
    // - y
    //   Y ordinate.
    //
    // - r
    //   Radius.
    //
    // - color
    //   The color of the cross hair.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void drawCrossHair ( double x, double y, double r, Color color )
    {
        double dr2 = r / 2.0;
        
        g.setColor ( color );
        
        g.draw ( new Line2D.Double ( x,       y - dr2, x,       y + dr2 ) );
        g.draw ( new Line2D.Double ( x - dr2, y,       x + dr2, y       ) );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Draw a guide grid.
    //
    // Most often used for analysis and debugging geometry. 
    // 
    // Arguments:
    //
    // - ( x0, y0 )
    //   Top left corner.
    //
    // - ( x1, y1 )
    //   Bottom right corner.
    //
    // - ( majorSX, majorSY )
    //   Major grid sub-devisions.
    //
    // - ( visibleAxisX, visibleAxisY )
    //   X and Y axis visibility.
    //
    // - ( visibleMajor, visibleMinor )
    //   Major and minor sub-devision line visibility.
    //
    // - ( colorAxis, colorMajor, colorMinor )
    //   Grid element colors.
    //
    // Return Value:
    //
    // - N/A
    //
    // Preconditions:
    //
    // - Values for points, p0(x0,y0) and p1(x1,y1), must be selected such that p0 is the top left hand corner, and p1 is the bottom right hand corner.  
    //
    // Postconditions:
    //
    // - A grid has been drawn to the current graphics context (screen).
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void drawGrid
    (
        double  x0, 
        double  y0, 
        double  x1, 
        double  y1, 
        double  majorSX, 
        double  majorSY, 
        double  minorSX, 
        double  minorSY,
        Boolean visibleAxisX,
        Boolean visibleAxisY,
        Boolean visibleMajor,
        Boolean visibleMinor,
        Color   colorAxis, 
        Color   colorMajor, 
        Color   colorMinor
    )
    {   
        // Initialize working coordinate variables.
        
        double x           = 0.0;
        double y           = 0.0;
        double sx2         = 0.0;
        double sy2         = 0.0;
        double columnWidth = 0.0;
        double rowHeight   = 0.0;
        
        // Calculate Cartesian range.
        
        double dx = x1 - x0;
        double dy = y1 - y0;
        
        // Configure line styles.
        
        final float       lineStyleFormatDotted[]  = { 1.0f, 4.0f };
        final BasicStroke lineStyleDefault         = (BasicStroke) g.getStroke ();
        final BasicStroke lineStyleSolid           = new BasicStroke ( 1.0f );
        final BasicStroke lineStyleSolidBold       = new BasicStroke ( 3.0f );
        final BasicStroke lineStyleDitted          = new BasicStroke ( 1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, lineStyleFormatDotted, 0.0f );
        
        
        // Draw minor column lines.
        
        if ( visibleMinor )
        {
            // Calculate column and row sizes.
            
            sx2         = 2.0 * minorSX;        // Double sub-devisions, to accommodate for positive and negative side of the axis.
            sy2         = 2.0 * minorSY;        // Double sub-devisions, to accommodate for positive and negative side of the axis.            
            columnWidth = dx / sx2;             // Column width.
            rowHeight   = dy / sy2;             // Row height.
            
            // Set drawing parameters for minor sub-devisions.
            
            g.setColor  ( colorMinor );
            g.setStroke ( lineStyleDitted );
            
            // Draw minor columns.
            
            for ( int columnCount = 0; columnCount <= sx2; columnCount++ )
            {
                x = x0 + columnWidth * columnCount;            
                g.draw ( new Line2D.Double ( x, y0, x, y1 ) );
            }
            
            // Draw minor rows.
            
            for ( int rowCount = 0; rowCount <= sy2; rowCount++ )
            {
                y = x0 + rowHeight * rowCount;            
                g.draw ( new Line2D.Double ( x0, y, x1, y ) );
            }
        }
        
        // Draw major column lines.
        
        if ( visibleMajor )
        {
            // Calculate column and row sizes.
            
            sx2         = 2.0 * majorSX;        // Double sub-devisions, to accommodate for positive and negative side of the axis.
            sy2         = 2.0 * majorSY;        // Double sub-devisions, to accommodate for positive and negative side of the axis.            
            columnWidth = dx / sx2;             // Column width.
            rowHeight   = dy / sy2;             // Row height.
            
            // Set drawing parameters for major sub-devisions.
            
            g.setColor  ( colorMajor );
            g.setStroke ( lineStyleSolid );
            
            // Draw major columns.
            
            for ( int columnCount = 0; columnCount <= sx2; columnCount++ )
            {
                x = x0 + columnWidth * columnCount;            
                g.draw ( new Line2D.Double ( x, y0, x, y1 ) );
            }
            
            // Draw major rows.
            
            for ( int rowCount = 0; rowCount <= sy2; rowCount++ )
            {
                y = x0 + rowHeight * rowCount;            
                g.draw ( new Line2D.Double ( x0, y, x1, y ) );
            }
        }
        
        // Draw axes.
        
        g.setColor ( colorAxis );
        g.setStroke ( lineStyleSolidBold );
        
        double dx2 = ( x1 - x0 ) / 2.0;
        double dy2 = ( y1 - y0 ) / 2.0;
        
        if ( visibleAxisX ) g.draw ( new Line2D.Double ( dx2, y0,  dx2, y1  ) );
        if ( visibleAxisY ) g.draw ( new Line2D.Double (  x0, dy2, x1,  dy2 ) );
        
        // Restore line style.
        
        g.setStroke ( lineStyleDefault );
    }
}


