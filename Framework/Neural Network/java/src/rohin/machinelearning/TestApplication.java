package rohin.machinelearning;

import rohin.machinelearning.NeuralNetworkLayer.TransferFunctionType;

public class TestApplication
{
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public TestApplication()
    {        
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // run
    //
    // Primary execution method.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void run()
    {
        int testCase = 2;
        
        switch ( testCase )
        {
            case 0: TestNeuralNetworkLayer (); break;
            case 1: TestBooleanFunction    (); break;
            case 2: TestNeuralNetwork      (); break;
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Test Case: Test neural network.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
        
    private void TestNeuralNetwork ()
    {
        // Display test case title.
        
        System.out.println ( "Test Case ID:\t\t\t2\nTest Case Description:\tNeural network computation test.\n" ); 
        
        // Neural network configuration.
        
        int      inputUnitCount   = 2;
        int      outputCount      = 1;
        int[]    networkStructure = { inputUnitCount, inputUnitCount, outputCount };        
        double[] x                = null;
        double[] y                = null;
        
        NeuralNetwork net = new NeuralNetwork ( networkStructure );
        
        // Initialize weight and bias vectors.
        
        for ( NeuralNetworkLayer layer : net.layers )
        {
            layer.randomizeWeights ( -0.5, 0.5 );
            layer.setbiasVector    (  0.0 );
        }
        
        // Compute network output.
        
        x = new double[] { 1.0, 1.0 };        
        y = net.computeNetwork ( x );
        
        // Display results.
        
        System.out.println ( net.toString () );
        
        for ( double value : y )
        {
            System.out.println ( "Neural Network: y = " + String.format ( "%.3f", value ) + "\n" );
        }
        
        // Initialize training data.
        
        double [][] d = null;
        double [][] t = null;
        int         trainingCaseCount = 100;
        
        d = new double [trainingCaseCount][];
        for ( double[] dx : d )
        {
            dx = new double[] { 1, 2 };
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Test Case: Test boolean function.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------

    
    public void TestBooleanFunction ()
    {     
        boolean  autoInitialize = false;        
        int[]    network        = { 2, 2, 1 };
        double[] x              = { 1.0, 1.0 };
        double[] y              = null;
        
        NeuralNetwork net = new NeuralNetwork ( network );
        
        if ( autoInitialize )
        {
            for ( NeuralNetworkLayer layer : net.layers )
            {
                layer.setWeights    ( 0.0 );
                layer.setbiasVector ( 0.0 );
            }
        }
        else
        {   
            int i = 0;
            
            net.layers [ i ].inputWeightVectors   = new double[][] { { -1.0, 1.0 }, { -1.0, 1.0 } };
            net.layers [ i ].biasVector           = new double[]   {    0.5,          -0.5        };
            net.layers [ i ].transferFunctionType = TransferFunctionType.TANH; 
            
            i = 1;
            
            net.layers [ 1 ].inputWeightVectors   = new double[][] { {  -1.0,  1.0 } };
            net.layers [ i ].biasVector           = new double[]   {     0.5         };
            net.layers [ i ].transferFunctionType = TransferFunctionType.LINEAR;
        }
        
        y = net.computeNetwork ( x );
        
        System.out.println ( net.toString () );
        for ( double value : y ) System.out.println ( "Neural Network: y = " + String.format ( "%.3f", value ) + "\n" );
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Test Case: Test neural network layer.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public void TestNeuralNetworkLayer ()
    {
        // Constants.
        
        @ SuppressWarnings ( "unused" )
        final String fileNameDSV = "R:\\Projects\\Machine Learning\\Applications\\MLJavaApplication1\\Main Data\\test.tsv";
        
        // Initialize variables.
        
        int                inputCount  = 8;
        int                outputCount = 8;
        NeuralNetworkLayer layer       = new NeuralNetworkLayer ( inputCount, outputCount );
                        
        // Configure network layer.
        
        //layer.inputWeightVectors   = new double[][] { { 1.0, 1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0, 1.0 } };
        layer.randomizeWeights ( -1.0, 1.0 );
        layer.transferFunctionType = TransferFunctionType.TANH;
        layer.setbiasVector ( 1.0 );
        //layer.randomizeBiasVector ( -1.0, 1.0 );
        
        
        // Initialize input and output vectors.
        
        double[] inputVector  = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        double[] outputVector = layer.computeLayer ( inputVector );
        
        // Display results.
        
        System.out.println ( "Neural Network - Weight Vectors:\n");
        System.out.println ( layer.toString () );
        System.out.println ( "Input Units:       " + layer.inputVectorSize                  );
        System.out.println ( "Output Units:      " + layer.outputVectorSize                 );
        System.out.println ( "Transfer Function: " + layer.transferFunctionType.toString () );
        
        System.out.println ();
        System.out.println ( "Input:");
        for ( int unitIndex = 0; unitIndex < inputCount; unitIndex++ )
        {
            System.out.print ( "\t" + String.format ( "%.3f", inputVector [ unitIndex ] ) );
        }
        
        System.out.println ( "\n" );
        System.out.println ( "Output:");        
        for ( int unitIndex = 0; unitIndex < outputCount; unitIndex++ )        
        {
            double output = outputVector [ unitIndex ];
            if ( output >= 0.0 )
            {
                System.out.println ( "\t" + unitIndex + ":\t " + String.format ( "%.3f", output ) );
            }
            else
            {
                System.out.println ( "\t" + unitIndex + ":\t" + String.format ( "%.3f", output ) );
            }
        }   
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Display Results.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @ SuppressWarnings ( "unused" )
    private void printResults ()
    {
    }
}
