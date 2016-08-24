

import java.io.FileWriter;
import java.io.IOException;

public class NeuralNetworkLayer
{
    // Enumerations.
    
    public enum OutputUnitType
    {   
        PERCEPTRON,
        SIGMOID,
        TANH
    };
    
    // Public Members.
    //
    // Note: We are not implementing accessors and mutators, in favor of c++11 style public members.
    
    public OutputUnitType outputUnitType;   // Type of unit function.
    public int            inputUnitCount;   // Number of input units. i.e. Number of elements in input vector.
    public int            outputUnitCount;  // Number of output units. i.e. Number of elments in output vector.
    public double[]       inputUnits;       // Neural network input units (Input vector).
    public double[][]     inputWeights;     // inputWeights [ outputUnits ][ inputUnits ].
    
    // Constructor/s.
    
    public NeuralNetworkLayer ()                                  { initialize ( 0,          0           ); }    
    public NeuralNetworkLayer ( int inputCount, int outputCount ) { initialize ( inputCount, outputCount ); }
    
    
    // Methods.
    
    // Initialize the layer.
    
    public void initialize ( int inputUnitCount, int outputUnitCount )
    {    
        // Initialize this layer's weight matrix (Dendritic tree network). 
        
        this.inputWeights    = new double [ outputUnitCount ][ inputUnitCount ];
        
        // Save the input/output unit counts.
        
        this.inputUnitCount  = inputUnitCount;
        this.outputUnitCount = outputUnitCount;
        
        // Initialize unit type.
        
        this.outputUnitType = OutputUnitType.SIGMOID;
    }
    
    // Set the Weights to a user specified value in the range, 0.0 and 1.0
    
    public void setWeights ( double weight )
    {   
        for ( int outputUnit = 0; outputUnit < this.inputWeights.length; outputUnit++ )
        {
            for ( int inputUnit = 0; inputUnit < this.inputWeights[outputUnit].length; inputUnit++ )
            {
                this.inputWeights[outputUnit][inputUnit] = weight;
            }
        }
    }
    
    // Set the Weights to a user specified value in the range, 0.0 and 1.0
    
    public void randomizeWeights ()
    {
        for ( int outputUnit = 0; outputUnit < this.inputWeights.length; outputUnit++ )
        {
            for ( int inputUnit = 0; inputUnit < this.inputWeights[outputUnit].length; inputUnit++ )
            {
                this.inputWeights[outputUnit][inputUnit] = Math.random ();
            }
        }
    }
    
    // Compute output unit vector.
    
    public double[] computeOutputVector ()
    {
        double[] outputUnitVector = null;
        double[] HadamardProduct  = null;
        double   sum              = 0;
        double   f                = 0;
        
        for ( int outputUnitIndex = 0; outputUnitIndex < this.inputWeights.length; outputUnitIndex++ )
        {
            double[] w = this.inputWeights [ outputUnitIndex ];     // Input weight vector.
            double[] x = this.inputUnits;                           // Input unit vector.
            
            // TODO NeuralNetworkLayer: Debug output vector computation.
            
            // Calculate input sum.
                        
            HadamardProduct = AIMath.HadamardProduct ( w, x );
            sum             = 0.0;
            for ( double wx : HadamardProduct ) sum += wx;
            
            // Calculate transfer function.
            
            switch ( outputUnitType )
            {
                case PERCEPTRON: f = AIMath.sgn     ( sum ); break;
                case SIGMOID:    f = AIMath.sigmoid ( sum ); break;
                case TANH:       f = 0.0;                    break;
            }
            
            outputUnitVector [ outputUnitIndex ] = f;            
        }
        
        return outputUnitVector;
    }
    
    
    // Save inputWeights to CSV file.
    
    public void saveToCSV ( String fileName )
    {
        saveToDSV ( fileName, ',' );
    }
    
    // Save inputWeights to TSV file.
    
    public void saveToTSV ( String fileName )
    {
        saveToDSV ( fileName, '\t' );
    }
    
    // Save inputWeights to delimiter-separated text file.
    
    public void saveToDSV ( String fileName, char delimiter )
    {
        try
        {
            // Open a new text file.
            
            FileWriter writer = new FileWriter ( fileName );
     
            // Compile data.
            
            int outputUnitCount = this.inputWeights.length;
            
            for ( int outputUnitIndex = 0; outputUnitIndex < outputUnitCount; outputUnitIndex++ )
            {
                writer.append ( String.valueOf ( outputUnitIndex ) );
                writer.append ( delimiter );
                
                int inputUnitCount = this.inputWeights [ outputUnitIndex ].length;
                
                for ( int inputUnitIndex = 0; inputUnitIndex < inputUnitCount; inputUnitIndex++ )
                {                
                    writer.append ( String.format ( "%.3f", this.inputWeights [ outputUnitIndex ][ inputUnitIndex ] ) );
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
    
    // Save inputWeights to binary file.
    
    public void saveToCSV ( String fileName, int bytesPerWeight )
    {
        //TODO NeuralNetworkLayer: Implement binary file save.
    }
    
    // Override: toString
    
    public String toString ()
    {   
        String stringWeights = new String ( "" );
        
        int outputUnitCount = this.inputWeights.length;
        
        for ( int outputUnitIndex = 0; outputUnitIndex < outputUnitCount; outputUnitIndex++ )
        {
            stringWeights += String.valueOf ( outputUnitIndex );
            stringWeights += ":";
            stringWeights += "\t\t";
            
            int inputUnitCount = this.inputWeights [ outputUnitIndex ].length;
            
            for ( int inputUnitIndex = 0; inputUnitIndex < inputUnitCount; inputUnitIndex++ )
            {                
                stringWeights += String.format ( "%.3f", this.inputWeights [ outputUnitIndex ][ inputUnitIndex ] );
                if ( inputUnitIndex < inputUnitCount - 1 )
                {
                    stringWeights += "\t";
                }
            }
            stringWeights += "\n";
        }
        
        return stringWeights;
    }
}






























