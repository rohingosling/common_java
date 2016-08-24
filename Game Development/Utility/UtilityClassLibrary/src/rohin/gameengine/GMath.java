package rohin.gameengine;


// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// GMath:
//
// A suite of various game related math functions.
//
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2009-05-10
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class GMath
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // DATA TYPES
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // CONSTANTS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // FIELDS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // METHODS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 1
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public GMath ()
    {    
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // setVector
    //
    // Initializes a vector using values passed in through a variable argument list.
    //
    // Arguments:
    //
    //   vx
    //   Variable argument list, used to input the individual elements of the vector to initialize.
    //
    // Return:
    //
    //   A new vector, initialized with the input elements.
    //
    //   Example 1:
    //
    //       double x = 0.0;
    //       double y = 1.0;
    //       double[] v2D = GMath.setVector ( x, y );
    //
    //   Example 2:
    //
    //       double x = 0.0;
    //       double y = 1.0;
    //       double z = 2.0;
    //       double[] v3D = GMath.setVector ( x, y, z );
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] setVector ( double[] v, double... vx )
    {   
        for ( int i=0; i < vx.length; i++ )
        {
            v[i] = vx[i];
        }
        
        return v;
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // AddVector
    //
    // Description:
    //
    // - Performs a vector addition on two vectors.
    // - The vectors are represented as an array.
    //
    // Arguments:
    //
    // - v1[]
    //   Vector v1.
    //
    // - v2[]
    //   Vector v2.
    //
    // Return:
    //
    // - Vector sum of vectors v1 and v2.
    //
    // Preconditions:
    // 
    // - v1 should be of equal or lower dimension to v2.
    //   This is to increase flexibility by accommodating operations between vectors of mismatched dimension.
    //    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] addVector ( double v1[], double v2[] )
    {   
        if ( v1.length <= v2.length )
        {               
            double[] vectorSum = new double [ v1.length ];
                        
            for ( int i = 0; i < v1.length; i++ )
            {
                vectorSum[i] = v1[i] + v2[i];
            }
            
            return vectorSum;   
        }
        else
        {
            return null;
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // subVector
    //
    // Performs a vector subtraction on two vectors.
    // The vectors are represented as an array.
    //
    // Arguments:
    //
    //   v1[]
    //   Vector v1.
    //
    //   v2[]
    //   Vector v2.
    //
    // Return:
    //
    //   Vector subtraction of vectors v1 and v2.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] subVector ( double v1[], double v2[] )
    {   
        if ( v1.length == v2.length )
        {               
            double[] vectorSum = new double [ v1.length ];
                        
            for ( int i = 0; i < v1.length; i++ )
            {
                vectorSum[i] = v1[i] - v2[i];
            }
            
            return vectorSum;   
        }
        else
        {
            return null;
        }
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // ScaleVector
    //
    // Multiply a vector by a scalar.
    // Scales a vector by a factor of s.
    //
    // Arguments:
    //
    //   v
    //   Vector.
    //
    //   s
    //   Scalar factor.
    //
    // Return:
    //
    //   Vector v, scaled by scalar s.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] scaleVector ( double v[], double s )
    {      
        double[] scaledVector = new double [ v.length ];
               
        for ( int i = 0; i < v.length; i++ )
        {
            scaledVector[i] = v[i] * s;
        }
            
        return scaledVector; 
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // hadamardProduct
    //
    // Calculates the Hadamard product of two vectors. 
    // i.e. Element wise vector product. 
    //
    // Arguments:
    //
    //   v1
    //   Vector.
    //
    //   v2
    //   Vector.
    //
    // Return:
    //
    //   Scale Vector v1 by Vector v2.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] hadamardProduct ( double v1[], double v2[] )
    {      
        if ( v1.length == v2.length )
        {               
            double[] scaledVector = new double [ v1.length ];
                        
            for ( int i = 0; i < v1.length; i++ )
            {
                scaledVector[i] = v1[i] * v2[i];
            }
            
            return scaledVector;   
        }
        else
        {
            return null;
        } 
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Magnitude
    //
    // Magnitude of vector v.
    //
    // Arguments:
    //
    //   v
    //   Vector.
    //
    // Return:
    //
    //   Magnitude of vector v.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double magnitude ( double v[] )
    {     
        double s = 0;
               
        for ( int i = 0; i < v.length; i++ )
        {
            s += v[i] * v[i];
        }
            
        return Math.sqrt ( s ); 
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // NormalizeVector
    //
    // REturn the normal 
    //
    // Arguments:
    //
    //   v
    //   Vector.
    //
    // Return:
    //                              _    v 
    //   Normalized vector of v:    v = ───
    //                                  |v|
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] normalizeVector ( double v[] )
    {               
        double m = magnitude ( v ); 
               
        if ( m != 0)
        {
            return scaleVector ( v, 1/m );
        }
        else
        {
            return null;
        }
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // DotProduct
    //
    // Return the scalar (Dot) product of two vectors. 
    //
    // Arguments:
    //
    //   v1
    //   Vector 1.
    //
    //   v2
    //   Vector 2.
    //
    // Return:
    //
    //   Dot Product of vectors v1 and v2.  
    //                                                     
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double dotProduct ( double v1[], double v2[] )
    {           
        double dotProduct = 0;
                    
        for ( int i = 0; i < v1.length; i++ )
        {
            dotProduct += v1[i] * v2[i];
        }
        
        return dotProduct;         
    }
    
    
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 
    // OVERRIDEBLE METHODS
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
}




















