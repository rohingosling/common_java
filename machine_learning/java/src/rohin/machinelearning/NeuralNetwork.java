package rohin.machinelearning;

public class NeuralNetwork
{  
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Public Members.
    //
    // Note: We are not implementing accessors and mutators, in favor of c++11 style public members.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public NeuralNetworkLayer[] layers;
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s
    //---------------------------------------------------------------------------------------------------------------------------------------------------------    
    
    public NeuralNetwork ()                { initialize ( null );    }
    public NeuralNetwork ( int[] network ) { initialize ( network ); }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Methods
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Initialize
    //
    // Arguments:
    //
    // - network
    //
    //   Array of integers, that specify the number of perceptron units in each layer, from input layer (i=0), through hidden layers (i=[1..N-2], to output 
    //   layer (i=N-1).
    //
    //   Example:
    //     Given: network { 4, 4, 3, 2 }
    //     Then:  Input layer (layer 3) has 4 units,
    //            hidden layer (layer 2) has 4 units,
    //            hidden layer (layer 1) has 3 units, 
    //            and the output layer (layer 0) has 2 units.
    //
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void initialize ( int[] network )
    {
        // Calculate the number of layers.
        
        int layerCount = network.length - 1;
        
        // Create an uninitialized array of layers.
        
        this.layers = new NeuralNetworkLayer [ layerCount ];
        
        // Initialize all the layers in the network.
        
        for ( int i = 0; i < layerCount; i++ )
        {
            // Create a new layer.
            
            this.layers [ i ] = new NeuralNetworkLayer ( network [ i ], network [ i + 1 ] );            
            
            // Configure a default transfer function architecture, that employs a linear transfer function on the output layer, and hyperbolic tangents on 
            // all the other layers. 
            
            if ( i < layerCount - 1 ) 
            {
                this.layers [ i ].transferFunctionType = NeuralNetworkLayer.TransferFunctionType.TANH;
            }
            else
            {
                this.layers [ i ].transferFunctionType = NeuralNetworkLayer.TransferFunctionType.LINEAR;
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Compute network output.
    //
    // TODO: Investigate the use of the softplus function.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    public double[] computeNetwork ( double[] x )
    {
        // Initialize local variables.
        
        double[] y          = null;                     // Output vector.
        int      layerCount = this.layers.length;       // Number of layers in this network.
        
        // Feed forward through the network and compute network output.
        
        for ( int i = 0; i < layerCount; i++ )
        {         
            y = this.layers[i].computeLayer ( x );      // Compute the output of the current layer.
            x = y;                                      // Set the input of the next layer, to the output of this layer.
        }
        
        // Return the network output to the caller.
        
        return y;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Train network
    //
    // Training algorithm: Propagation.
    //
    // Arguments:
    //
    // - d
    //   List of N input training vectors.
    //
    // - t
    //   List of N output target vectors, corresponding to the input vectors of d.
    //
    // Return Value:
    //
    // - MSE
    //   Mean squared error.
    //
    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    public double trainBackpropagation ( double[][] X, double[][] Y )
    {
        double[]   x   = null;                  // Network input training vector.
        double[]   y   = null;                  // Network output training vector.
        double[][] h   = null;                  // Network output predicted hypotheses vectors per layer. i.e. o[layer][output].
        double[][] e   = null;                  // Error vector.
        double     mse = 0.0;                   // Mean squared error.
        int        L   = this.layers.length;    // Number of layers in the network.
        int        N   = X.length;              // Number of training cases. 
        
        // Loop through all training cases.
        
        for ( int n = 0; n < N; n++ )
        {   
            // Retrieve input vector for the current training case.
            
            x = X[n];
                        
            // Predict network output based on training input.
            //
            // 1. Propagate layer outputs forward, and collect the results.  
            
            h = new double [L][];
            
            for ( int l = 0; l < L; l++ )
            {   
                // Calculate output vector size for this layer.
                
                int s = this.layers[l].outputVectorSize;
                h[l]  = new double [ s ];
                
                // Compute outputs of input layer.
                
                if ( l == 0 ) h[l] = this.layers[l].computeLayer ( x );         // Compute outputs of input layer.
                if ( l > 0  ) h[l] = this.layers[l].computeLayer ( h[l-1] );    // Compute outputs of hidden layers.
            }
            
            // Propagate errors backward through the network.
            
            e = new double [L][];
            
            for ( int l = L-1; l >= 0; l-- )
            {
                // Calculate output vector size for this layer.
                
                int vectorSize = this.layers[l].outputVectorSize;
                e[l]  = new double [ vectorSize ];
                
                // 2. Calculate error term for output layer.
                
                if ( l == L-1 )
                {
                    for ( int i = 0; i < vectorSize; i++ )
                    {
                        double ox = h [ l-1 ][ i ];     // Output from previous layer.
                        double oy = h [ l   ][ i ];     // Output from this layer.
                        double ty = Y [ n   ][ i ];     // Target output.
                        
                        double dx = 2 * Math.cosh ( ox ) / ( Math.cosh ( 2 * ox ) + 1.0 );      // Derivative of, y = tanh (x).
                        double dy = ty - oy;                                                    // Error between calculated output, and target output.
                        
                        e[l][i]   = dx * dy;;           // Error term for the current unit, in the current layer.
                    }
                }
                
                // 3. Calculate error term for all hidden layers.
                
                if ( l < L-1 )
                {
                    for ( int i = 0; i < vectorSize; i++ )
                    {
                        double   ox = h [ l-1 ][ i ];     // Output from previous layer.
                        double[] w  = this.layers[l].inputWeightVectors[i];
                        double[] ey = e[l];
                        double[] p  = AIMath.HadamardProduct ( w, ey );                        
                        double   dy = AIMath.vectorSum ( p );
                        
                        double dx = 2 * Math.cosh ( ox ) / ( Math.cosh ( 2 * ox ) + 1.0 );      // Derivative of, y = tanh (x).
                        
                        e[l][i]   = dx * dy;;           // Error term for the current unit, in the current layer.
                    }
                }
            }
            
            // 4. Update network weights.
            
            
        }
        
        return mse;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Train: Backpropagation
    //
    // Arguments:
    //
    // - x
    //   Training Set: Input vector.
    //
    // - t
    //   Training Set: Target vector.
    //
    // - maxE
    //   Maximum allowable error.
    //
    // - epochMin
    //   Minimum number of epochs.
    //
    // - epochMax
    //   Maximum number of epochs.
    //   
    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    public double[] trainBackpropagation ( double[] x, double[] t, double maxE, int epochMin, int epochMax )
    {
        double[] o = null;
        
        return null;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Override: toString
    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    public String toString ()
    {
        String outputString = new String ( "" );
        
        int i = this.layers.length - 1;
        for ( NeuralNetworkLayer layer : this.layers )
        {
            outputString += "Layer " + i + ": ";
            outputString += layer.transferFunctionType.toString ();
            outputString += "\n\n";
            outputString += layer.toString ();
            outputString += "\n";
            i--;
        }
        
        return outputString;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // 
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
}
