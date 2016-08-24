package rohin.machinelearning;

import java.io.FileWriter;
import java.io.IOException;

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
//
// Terminology:
//
// - Unit
//   + Refers to a single neural unit.
//   + Other names used in literature include; neuron, perceptron, perceptron unit, or in the context of vectors, an element.
//
// - Input and Output Vectors
//   Vectors who's elements represent individual unit values (perceptron values).
//
// - Weight Vector
//   A vector who's elements represent the weights of all input dendrites to a single unit (perceptron, neuron, etc).
//
// - Layer Operations
//   Calculations that carry and transform the input vector into a layer, through the various stages that lead to the output vector.
//   + Weight Function.
//   + Network Function, or Net Function.
//   + Transfer Function.
//
// - Weight Function
//   + Function used to compute the relationship between the input vector and the weight vector.
//   + This is most commonly performed by calculating the Hadamard Product of the input and weight vectors.
//
// - Network Function (Net Function)
//   + The network function integrates all the inputs and bias values, into a single scalar value to be used as the input into the transfer function.
//   + This is typically performed by summing up all the results of the weight function and the bias unit (neuron).
//
// - Transfer Function
//   The transfer function defines the activation behavior of a perceptron unit (neuron).
//
//-------------------------------------------------------------------------------------------------------------------------------------------------------------

public class NeuralNetworkLayer
{
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constants.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
        
    final String M_EMPTY            = "";
    final String M_TAB_1            = "\t";
    final String M_TAB_2            = "\t\t";        
    final String M_NEW_LINE         = "\n";
    final String M_SPACE            = " ";
    final String M_PRECISION_3      = "%.3f";    
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Enumerations.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public enum TransferFunctionType
    {   
        STEP,       // Step function.                   y = step(x)
        SGN,        // Sign function.                   y = sgn(x)
        LINEAR,     // Direct weight function + bias.   y = x
        SIGMOID,    // Sigmoid logistics function.      y = sig(x)
        TANH,       // Hyperbolic tangent.              y = tanh(x)
        TANHx2      // Hyperbolic tangent.              y = tanh(2x)
    };
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Public Members.
    //
    // Note: We are not implementing accessors and mutators, in favor of c++11 style public members.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public TransferFunctionType transferFunctionType;   // Type of transfer function used to compute output units.
    public int                  inputVectorSize;        // Number of input units. i.e. Number of elements in input vector.
    public int                  outputVectorSize;       // Number of output units. i.e. Number of elements in output vector.    
    public double[][]           inputWeightVectors;     // inputWeightVectors [ outputUnits ][ inputUnits ].
    public double[]             biasVector;             // Bias vector.
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public NeuralNetworkLayer ()                                  { initialize ( 0,          0           ); }    
    public NeuralNetworkLayer ( int inputCount, int outputCount ) { initialize ( inputCount, outputCount ); }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // Methods.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Initialize the layer.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void initialize ( int inputUnitCount, int outputUnitCount )
    {    
        // Initialize this layer's weight matrix (Dendritic tree network). 
        
        this.inputWeightVectors = new double [ outputUnitCount ][ inputUnitCount ];
        this.biasVector         = new double [ outputUnitCount ];          
        
        // Save the input/output unit counts.
        
        this.inputVectorSize  = inputUnitCount;
        this.outputVectorSize = outputUnitCount;
        
        // Initialize default layer parameters.
        
        this.transferFunctionType = TransferFunctionType.SIGMOID;        
    }
 
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Set the Weights to a user specified value in the range, 0.0 and 1.0
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void setWeights ( double weight )
    {   
        for ( int outputUnit = 0; outputUnit < this.outputVectorSize; outputUnit++ )
        {
            for ( int inputUnit = 0; inputUnit < this.inputVectorSize; inputUnit++ )
            {
                this.inputWeightVectors[outputUnit][inputUnit] = weight;
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Set the Weights to a random value in the range, 0.0 and 1.0
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void randomizeWeights ( double min, double max )
    {
        for ( int outputUnit = 0; outputUnit < this.outputVectorSize; outputUnit++ )
        {
            for ( int inputUnit = 0; inputUnit < this.inputVectorSize; inputUnit++ )
            {
                this.inputWeightVectors[outputUnit][inputUnit] = min + ( max - min ) * Math.random ();
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Set the Weights to a user specified value in the range, 0.0 and 1.0
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void gaussianWeights ( double min, double max )
    {
        for ( int outputUnit = 0; outputUnit < this.outputVectorSize; outputUnit++ )
        {
            for ( int inputUnit = 0; inputUnit < this.inputVectorSize; inputUnit++ )
            {
                this.inputWeightVectors[outputUnit][inputUnit] = min + ( max - min ) * AIMath.gaussianDistribution ();
            }
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Set bias vector.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void setbiasVector ( double biasTerm )
    {   
        for ( int outputUnit = 0; outputUnit < this.outputVectorSize; outputUnit++ )
        {
            this.biasVector [ outputUnit ] = biasTerm;
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Randomize bias vector.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void randomizeBiasVector ( double min, double max )
    {   
        for ( int outputUnit = 0; outputUnit < this.outputVectorSize; outputUnit++ )
        {
            this.biasVector [ outputUnit ] = min + ( max - min ) * Math.random ();
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Compute output unit vector.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public double[] computeLayer ( double[] x )
    {
        double[] y                = new double [ this.outputVectorSize ];       // Output vector.
        double[] weightFunction   = null;                                       // Weight function vector result.
        double   networkFunction  = 0.0;                                        // Network function result.
        double   transferFunction = 0.0;                                        // Transfer function result.
        
        for ( int outputUnitIndex = 0; outputUnitIndex < this.outputVectorSize; outputUnitIndex++ )
        {
            // Retrieve input vector and weight vector.
            
            double[] w = this.inputWeightVectors [ outputUnitIndex ];           // Input weight vector.
            
            // Evaluate weight function. 
                        
            weightFunction = AIMath.HadamardProduct ( w, x );                   // Apply relationship between the input vector and the weight vector.
            
            // Evaluate network function.
                        
            networkFunction = AIMath.vectorSum ( weightFunction );              // Sum up the elements of the weight function vector.
            networkFunction += this.biasVector [ outputUnitIndex ];             // Add the bias term.
            
            // Evaluate transfer function.
            
            switch ( transferFunctionType )
            {
                case LINEAR:    transferFunction = networkFunction;                           break;
                case SGN:       transferFunction = AIMath.sgn     ( networkFunction       );  break;
                case STEP:      transferFunction = AIMath.step    ( networkFunction       );  break;
                case SIGMOID:   transferFunction = AIMath.sigmoid ( networkFunction       );  break;                
                case TANH:      transferFunction = Math.tanh      ( networkFunction       );  break;                
                case TANHx2:    transferFunction = Math.tanh      ( networkFunction * 2.0 );  break;
            }
            
            y [ outputUnitIndex ] = transferFunction;            
        }
        
        return y;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Save inputWeightVectors to CSV file.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void saveToCSV ( String fileName )
    {
        saveToDSV ( fileName, ',' );
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Save inputWeightVectors to TSV file.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void saveToTSV ( String fileName )
    {
        saveToDSV ( fileName, '\t' );
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Save inputWeightVectors to delimiter-separated text file.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void saveToDSV ( String fileName, char delimiter )
    {
        try
        {
            // Open a new text file.
            
            FileWriter writer = new FileWriter ( fileName );
     
            // Compile data.
            
            int outputUnitCount = this.inputWeightVectors.length;
            
            for ( int outputUnitIndex = 0; outputUnitIndex < outputUnitCount; outputUnitIndex++ )
            {
                writer.append ( String.valueOf ( outputUnitIndex ) );
                writer.append ( delimiter );
                
                int inputUnitCount = this.inputWeightVectors [ outputUnitIndex ].length;
                
                for ( int inputUnitIndex = 0; inputUnitIndex < inputUnitCount; inputUnitIndex++ )
                {                
                    writer.append ( String.format ( "%.3f", this.inputWeightVectors [ outputUnitIndex ][ inputUnitIndex ] ) );
                    if ( inputUnitIndex < inputUnitCount - 1 )
                    {
                        writer.append ( delimiter );
                    }
                }
                writer.append ( "\n" );
            }
     
            // Save and close the file.
     
            writer.flush();
            writer.close();
        }
        catch ( IOException e )
        {
             e.printStackTrace();
        } 
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Save inputWeightVectors to binary file.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void saveToBIN ( String fileName, int bytesPerWeight )
    {
        //TODO NeuralNetworkLayer: Implement binary file save.
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Override: toString.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public String toString ()
    {        
        // Display options.
        
        boolean rowHeaderVisible  = true;
        boolean columnIDVisible   = true;
        boolean columnBiasVisible = true;
        
        // Initialize local variables.
        
        String s = new String ( M_EMPTY );
                
        // Compile data table.        
        
        for ( int outputUnitIndex = 0; outputUnitIndex < this.outputVectorSize; outputUnitIndex++ )
        {   
            // Add header row.
            
            if ( outputUnitIndex == 0 )
            {
                s += ( rowHeaderVisible ) ? printTableHeader () : M_EMPTY;
            }
            
            // Output unit ID column.
            
            s += ( columnIDVisible ) ? printIDColumn ( outputUnitIndex ) : M_EMPTY;
            
            // Bias weight column.
            
            s += ( columnBiasVisible ) ? printBiasColumn ( outputUnitIndex ) : M_EMPTY;
            
            // Weight columns.
            
            for ( int inputUnitIndex = 0; inputUnitIndex < this.inputVectorSize; inputUnitIndex++ )
            { 
                // Tab to next column.
                
                if ( inputUnitIndex < this.inputVectorSize )
                {
                    s += M_TAB_1;
                }
                
                // Print layer weight.
                
                s += printWeight ( inputUnitIndex, outputUnitIndex );
            }
            
            // Carriage return and new line.
            
            s += M_NEW_LINE;
        }
        
        // Return output string.
        
        return s;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Print ID Column.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private String printIDColumn ( int id )
    {
        String s = new String ( M_EMPTY );
        
        s += String.valueOf ( id );
        s += ":";
        s += M_TAB_2;
        
        return s;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Print Bias column.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private String printBiasColumn ( int outputUnitIndex )
    {
        // Constants.
        
        final String PARENTHESUS_OPEN  = "(";
        final String PARENTHESUS_CLOSE = ")";
        
        // Initialize local variables.
                 
        String  s    = new String ( M_EMPTY );
        double  bias = this.biasVector [ outputUnitIndex ];
        boolean wide = true;
        
        s += PARENTHESUS_OPEN;
        if ( bias >= 0.0 )
        {
            // Positive bias value.
            
            s += M_SPACE;
            s += String.format ( M_PRECISION_3, bias );
        }
        else
        {
            // Negative bias value.
            
            s += String.format ( M_PRECISION_3, bias );
        }
        s += ( wide ) ? M_SPACE : M_EMPTY;
        s += PARENTHESUS_CLOSE;
        
        return s;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Print weights.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private String printWeight ( int inputUnitIndex, int outputUnitIndex )
    {
        // Initialize local variables.
        
        String s      = new String ( M_EMPTY );        
        double weight = this.inputWeightVectors [ outputUnitIndex ][ inputUnitIndex ];
        
        // Output the weight value.
        
        if ( weight >= 0.0 ) 
        {   
            s += M_SPACE;         
        }
        
        s += String.format ( M_PRECISION_3, weight );
        
        return s;
    }
        
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Print table header.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private String printTableHeader ()    
    {   
        // Constants
        
        final String COL_NAME_TERMINATOR = ":";
        final String COL_NAME_ID         = "ID";
        final String COL_NAME_BIAS       = "BIAS";
        
        // Initialize variables.
        
        String s = new String ( M_EMPTY );
        
        // Compile output string. 
               
        s += COL_NAME_ID;
        s += M_TAB_2;
        s += COL_NAME_BIAS;
        
        for ( int i = 0; i < this.inputVectorSize; i++ )
        {
            if ( i < this.inputVectorSize )
            {
                s += M_TAB_2 + M_SPACE;
            }
            s += String.valueOf ( i );
            s += COL_NAME_TERMINATOR;
        }
        
        s += M_NEW_LINE;
        
        // Return string.
        
        return s;
    }
}






























