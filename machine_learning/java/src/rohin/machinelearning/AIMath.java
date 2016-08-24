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
        
        double ex = Math.exp ( -x );     // Raise e to the power of -x. 
        double y  = 1 / ( 1 + ex );         // Calculate the sigmoid function.
        
        // Return the result.
        
        return y;
    }
    
    // Parameterized Sigmoid function.
    
    public static double sigmoid ( double x, double k )
    {
        // Compute the sigmoid function.
        
        double t  = -k*x;                   // Exponent.           
        double ex = Math.exp ( t );         // Raise e to the power of t. 
        double y  = 1 / ( 1 + ex );         // Calculate the sigmoid function.
        
        // Return the result.
        
        return y;
    }
    
    // Parameterized Sigmoid function.
    
    public static double dsigmoid ( double x )
    {
        // Compute the sigmoid function.
                      
        double d    = Math.exp ( x );
        double dx   = ( Math.exp ( x ) + 1 );
        double dxdy = d / dx*dx;                    // Calculate the derivative of the sigmoid function.
        
        // Return the result.
        
        return dxdy;
    }
    
    // Sign function.
    
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
    
    // Step function.
    
    public static double step ( double x )
    {
        double y = 0.0;
        
        // Compute the sign function.
        
        if ( x >  0.0  ) y =  1.0;
        if ( x <= 0.0  ) y =  0.0;        
        
        // Return the result.
        
        return y;
    }
    
    // hyperbolic secant
    
    public static double sech ( double x )
    {
        // Compute hyperbolic secant.
        
        double y = 1.0 / Math.cosh ( x );
        
        // Return the result.
        
        return y;
    }
    
    // Vector Sum.
    // Calculates the sum of all the elements in vector v.
    
    public static double vectorSum ( double[] v )
    {
        double sum = 0.0;
        
        // Calculate sum.
        
        for ( double x : v )
        {
            sum += x;
        }
        
        // Return the result.
        
        return sum;
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
        
    // Gaussian distribution about the axis 0.5.
    
    public static double gaussianDistribution ()
    {   
        double x = Math.random ();        
        double y = Math.pow ( 2*x - 1, 3 ) / 2 + 0.5;
        
        return y;
    }
}
