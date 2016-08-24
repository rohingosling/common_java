package rohin.machinelearning;

public class Main
{
    public static void main ( String [] args )
    {
        try
        {
            Application application = new Application ();   // Create acceleration new Application object.
            
            application.run ();                             // Start the Application.
            
            System.exit ( 0 );                              // Force Application shutdown.            
        }
        catch ( Exception e )
        {               
        }
    }
}
