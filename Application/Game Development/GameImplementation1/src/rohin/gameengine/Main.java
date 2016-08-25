
package rohin.gameengine;

// -----------------------------------------------------------------------------
/**
 * Program entry point.<br>
 * Standard Java static main function.
 * 
 * @author  Rohin Gosling
 * @version 1.0
 * @since   2014-04-18
 */
// -----------------------------------------------------------------------------

public class Main
{
    // -------------------------------------------------------------------------
    /**
     * Java main function.
     * <p>
     * Program entry point.
     * 
     * @param args
     *        ICommand line arguments.
     */
    // -------------------------------------------------------------------------

    public static void main ( String [] args )
    {
        Application application = new Application ();   // Create a new application object.

        application.Run ();                             // Start the application.

        System.exit ( 0 );                              // Force application shutdown.
    }
}
