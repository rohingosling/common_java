package rohin.gameengine;

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
//
// Vector2D:
//
// 2D vector object.
//
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2009-05-10
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Vector2D
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // CONSTANTS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final static int X = 0;
    final static int Y = 1;
    
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // FIELDS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private double[] v;    
 
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public double   getX      () { return this.v[X]; }
    public double   getY      () { return this.v[Y]; }
    public double[] getVector () { return this.v;    }    
    
    public void setX      ( double x )           { this.v[X] = x;                                }
    public void setY      ( double y )           { this.v[Y] = y;                                }
    public void setVector ( double x, double y ) { this.v[X] = x;         this.v[Y] = y;         }
    public void setVector ( double[] v )         { this.v[X] = v[X];      this.v[Y] = v[Y];      }
    public void setVector ( Vector2D v )         { this.v[X] = v.getX (); this.v[Y] = v.getY (); }
     
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // METHODS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor/s
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D ()                     { initialize ( 0.0,       0.0       ); }
    public Vector2D ( double x, double y ) { initialize ( x,         y         ); }
    public Vector2D ( double[] v )         { initialize ( v[X],      v[Y]      ); }
    public Vector2D ( Vector2D v )         { initialize ( v.getX (), v.getY () ); }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Initialize
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void initialize ( double x, double y)
    {
        v = new double[] { x, y };        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Vector addition.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D add ( Vector2D v )
    {
        return new Vector2D ( this.v[X] + v.getX (), this.v[Y] + v.getY () );        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Vector subtraction.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D subtract ( Vector2D v )
    {
        return new Vector2D ( this.v[X] - v.getX (), this.v[Y] - v.getY () );        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Vector multiplication.
    //
    // Note:
    // - This is neither a dot product or a cross product.
    // - It is a strait element by element multiply.  
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D multiply ( Vector2D v )
    {
        return new Vector2D ( this.v[X] * v.getX (), this.v[Y] * v.getY () );        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Vector division.
    //
    // Note: 
    // - This is a strait element by element devision.  
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D divide ( Vector2D v )
    {
        return new Vector2D ( this.v[X] / v.getX (), this.v[Y] / v.getY () );        
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Vector magnitude.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public double magnitude ()
    {
        // Retrieve vector.
        
        double x = this.v[X];
        double y = this.v[Y];
        
        // Calculate magnitude.
        
        return Math.sqrt ( x*x + y*y );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Normalized vector.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D norm ()
    {      
        // Retrieve vector.
        
        double x  = this.v[X];
        double y  = this.v[Y];
        
        // Calculate the magnitude of the vector.
        
        double m  = Math.sqrt ( x*x + y*y );
        
        // Initialize working variables.
        
        double Nx = 0;
        double Ny = 0;
        
        // Calculate the normalized vector.
        // If the magnitude of the vector is zero, then the normalized vector shall just remain as the zero vector (0,0).
        
        if ( m > 0 )
        {
            Nx = x / m;
            Ny = y / m;
        }
        
        // Return an instance of the normalized vector.
        
        return new Vector2D ( Nx, Ny );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Scale vector.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Vector2D scale ( double s )
    {   
        // Retrieve vector.
        
        double x  = this.v[X];
        double y  = this.v[Y];
        
        // Scale vector by s.
        
        double Sx = x * s;
        double Sy = y * s;
        
        // Return the scaled vector. 
        
        return new Vector2D ( Sx, Sy );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Dot product.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public double dotProduct ( Vector2D v )
    {
        // Retrieve vectors A and B.
        
        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Bx = v.getX ();
        double By = v.getY ();
        
        // Calculate the dot product.
        
        return Ax*Bx + Ay*By;
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Angle between vectors A and B.
    //
    // Note:
    //
    // - Mathematically, the condition where Am or Bm is zero, should return an error, since the scenarios of an angle between a vector and a zero vector,
    //   or the angle between a zero vector and another zero vector, are mathematically meaningless.
    //
    // - However, for the sake of robustness, we will allow the error to fall through, and simply set the angle to zero.
    //
    // - The conditions for zero magnitude vectors A and B are handled separately to the condition of a zero magnitude vector C, just in case we wish to
    //   modify this method at a later stage, to handle the error condition of zero magnitude vectors A and B.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public double angleBetween ( Vector2D v )
    {
        // Retrieve vectors A and B.
        
        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Bx = v.getX ();
        double By = v.getY ();
        
        // Calculate the difference between vectors A and B.
        
        double Cx = Bx - Ax;
        double Cy = By - Ay;
        
        // Calculate the magnitudes of vectors A, B and C.
        
        double Am = Math.sqrt ( Ax*Ax + Ay*Ay );
        double Bm = Math.sqrt ( Bx*Bx + By*By );
        double Cm = Math.sqrt ( Cx*Cx + Cy*Cy );
        
        // Initialize working variables.
        
        double angle = 0.0;
        double dx    = 0.0;
        double dy    = 1.0;
        
        // Calculate the angle between vectors A and B.
        //
        // Note:
        //
        // - We handle the zero conditions for vectors A and B, separately to the zero condition for vector C.
        //
        // - The reason we do this, is simply to high light the fact that within the context of correct linear algebra rules, the condition where either
        //   vector A or vector B is zero, is an error. Where as the condition where vector C is zero, simply results in an angle of zero.
        //
        // - In the conditional code complex below, we are going to allow the conditions of vectors A and B being zero, to fall through with an angle of zero.
        //
        // - This is not mathematically correct, but has negligible effect on the logic of programs who wish to use this method, and improves the robustness 
        //   of the method.
        
        if ( ( Am > 0 ) && (Bm > 0 ) )
        {
            if ( Cm > 0 )
            {
                dx    = Am*Am + Bm*Bm - Cm*Cm;
                dy    = 2*Am*Bm;                
                angle = Math.acos ( dx / dy );
            }            
        }
        
        // Return the calculated angle.
        
        return angle;
    }
}



















