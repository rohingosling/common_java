package org.hanoo.loggerExample;
 
import java.io.IOException; 
 
public class LoggerTest
{
    private final static Logger logger = Logger.getLogger ( LoggerTest.class.getName () );
    
    private static FileHandler fh = null;

    public static void init()
    {
        try
        {
            fh = new FileHandler ( "loggerExample.log", false );
        }
        catch ( SecurityException | IOException e)
        {
            e.printStackTrace();
        }
        
        Logger l = Logger.getLogger ( "" );
        
        fh.setFormatter ( new SimpleFormatter() );
        l.addHandler    ( fh );
        l.setLevel      ( Level.CONFIG );
    }

    public static void main ( String[] args )
    {
        LoggerTest.init();

        logger.log ( Level.INFO,   "message 1" );
        logger.log ( Level.SEVERE, "message 2" );
        logger.log ( Level.FINE,   "message 3" );
        
        LoggerTest2.thing();
    }
}

package org.hanoo.loggerExample;
 
import java.util.logging.Level;
 
public class LoggerTest2
{
    private final static Logger logger = Logger.getLogger ( LoggerTest.class.getName () );
 
    public static void thing ()
    {
        logger.log ( Level.WARNING,"something to log" );
    }
}

// févr. 24, 2012 6:09:19 PM org.hanoo.loggerExample.LoggerTest main
// Infos: message 1
// févr. 24, 2012 6:09:19 PM org.hanoo.loggerExample.LoggerTest main
// Grave: message 2
// févr. 24, 2012 6:09:19 PM org.hanoo.loggerExample.LoggerTest2 thing
// Avertissement: something to log





























