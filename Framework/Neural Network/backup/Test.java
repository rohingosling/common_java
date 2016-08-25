

import rohin.machinelearning.NeuralNetworkLayer.OutputUnitType;

public class Test
{
    public static void main ( String [] args )
    {        
        final String fileNameDSV = "R:\\Projects\\Machine Learning\\Applications\\MLJavaApplication1\\Test Data\\test.tsv";
        
        int                inputCount  = 2;
        int                outputCount = 1;
        NeuralNetworkLayer layer       = new NeuralNetworkLayer ( inputCount, outputCount );
        
        // layer.setWeights ( 0.0 );
        //layer.randomizeWeights ();
        layer.inputUnits     = new double[]   { 1.0, 1.0 };
        layer.inputWeights   = new double[][] { {0.5, -0.5} };        
        layer.outputUnitType = OutputUnitType.PERCEPTRON;
        
        
        System.out.println ( layer.toString () );
        
        double[] outputUnitVector = layer.computeOutputVector ();
        
        for ( double f : outputUnitVector )
        {
            System.out.println ( String.format ( "%.3f", f ) );    
        }
                
        // Exit application.
        
        System.out.println ( "Application: exit(0)" );
    }
}
