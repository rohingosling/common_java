package rohin.machinelearning;

public class AIMath
{
    static final double e = Math.E;     // Euler's number.
    
    public AIMath()
    {        
    }
    
    // Sigmoid function.
    
    public static double sigmoid ( double x )
    {
        // Compute the sigmoid function.
        
        double ex = Math.pow ( e, -x );     // Raise e to the power of -x. 
        double y  = 1 / ( 1 + ex );         // Calculate the sigmoid function.
        
        // Return the result.
        
        return y;
    }
    
    // Parameterized Sigmoid function.
    
    public static double sigmoid ( double x, double k )
    {
        // Compute the sigmoid function.
        
        double t  = -k*x;                   // Exponent.           
        double ex = Math.pow ( e, t );      // Raise e to the power of t. 
        double y  = 1 / ( 1 + ex );         // Calculate the sigmoid function.
        
        // Return the result.
        
        return y;
    }
    
    // Sign function, y = sgn(x)
    
    public static double sgn ( double x )
    {
        double y = 0.0;
        
        // Compute the sign function.
        
        if ( x > 0.0  ) y =  1.0;
        if ( x < 0.0  ) y = -1.0;
        if ( x == 0.0 ) y =  0.0;
        
        // Return the result.
        
        return y;
    }
    
    // Hadamard vector product.
    
    public static double[] HadamardProduct ( double[] u, double[] v )
    {   
        double[] p = null;
        int      s = 0;
        
        if ( u.length == v.length )
        {
            s = u.length;               // Get the size of the vectors. Once we are in the conditional branch, we know u and v are of equal size.
            p = new double [ s ];       // Initialize the Hadamard product vector.
            
            // Calculate the Hadamard product of vectors u and v.
            
            for ( int i = 0; i < s; i++ )
            {
                p[i] = u[i] * v[i];
            }
        }
        
        // Return the result. 
        
        return p;
    }
}
