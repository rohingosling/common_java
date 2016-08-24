package rohin.gameengine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

public class ApplicationSettings
{
    private Properties settings;
    private String     fileName;  
    
    public Properties GetSettings () { return this.settings; }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor 1    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ApplicationSettings ()
    {
        this.settings = null;
        this.fileName = "";
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor 2    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ApplicationSettings ( String fileName)
    {
        load ( fileName );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // OpenApplicationSettingsFile
    //
    // Open an old school *.properties file for reading.
    // 
    // Arguments:
    //
    // - fileName
    //   The file fileName of the properties file to open.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public void load ( String fileName )
    {
        // Load the properties file.

        try
        {
            this.fileName = fileName;
            this.settings = new Properties ();
            this.settings.load ( new FileInputStream ( fileName ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace ();
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetSetting
    // 
    // Get an application setting from the application setting member object.
    //
    // Arguments:
    //
    // - key
    //   The access key for the requested application setting.
    //
    // Return Value:
    //
    // - The application setting referenced via key.    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public String getSetting ( String key )
    {   
        // We will set to null, in order to trigger a null pointer exception by default.
        // Unless we find a valid settings value string in the application properties file, in which case we will return what we have retrieved from the file.
        
        String value = null;
        
        // Attempt to retrieve a setting value from the settings file.
        
        try
        {
            value = this.settings.getProperty ( key );                  // Attempt to retrieve settings value string from properties file.            
            if ( value == null ) throw new NullPointerException ();     // Throw a null pointer exception if we couldn't find the requested setting string.
        }
        catch ( Exception e )
        {
            Boolean exitApplicationOnException = true;
            TextFormat.printFormattedException ( e, exitApplicationOnException );            
        }
        
        // Return the setting value string that we retrieved, or null if we failed to find the requested setting in the application properties file.
        
        return value;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetString
    // 
    // Get an application string setting from the application setting member object.
    //
    // Arguments:
    //
    // - key
    //   The access key for the requested application setting.
    //
    // Return Value:
    //
    // - The application setting referenced via key.    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public String getString ( String key )
    {
        return getSetting ( key );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetInteger
    // 
    // Get an integer application setting from the application setting member object.
    //
    // Arguments:
    //
    // - key
    //   The access key for the requested application setting.
    //
    // Return Value:
    //
    // - The application setting referenced via key.    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public int getInteger ( String key )
    {
        return Integer.parseInt ( getSetting ( key ) );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetBoolean
    // 
    // Get an boolean application setting from the application setting member object.
    //
    // Arguments:
    //
    // - key
    //   The access key for the requested application setting.
    //
    // Return Value:
    //
    // - The application setting referenced via key.    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public Boolean getBoolean ( String key )
    {
        return Boolean.valueOf ( getSetting ( key ) );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetLong
    // 
    // Get an long application setting from the application setting member object.
    //
    // Arguments:
    //
    // - key
    //   The access key for the requested application setting.
    //
    // Return Value:
    //
    // - The application setting referenced via key.    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
     
    public long getLong ( String key )
    {
        return Long.parseLong ( getSetting ( key ) );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Generate a formatted list of application settings from an application.propertties file. 
    // The output in this case, might look like;.
    //   
    // [ 10:33:04.142 ] - Application Settings: game_engine_1.0.properties 
    //     
    // GameEngine.Application.Name           = Game Engine
    // GameEngine.DataOverlay.Visible        = true
    // GameEngine.FPS.Targit                 = 90
    // GameEngine.FixedGameLoopDelay.Enabled = false
    // GameEngine.GameLoop.Delay             = 10
    // GameEngine.LanguageCode               = 0
    // GameEngine.Logging.Enabled            = true
    // GameEngine.Screen.Height              = 600
    // GameEngine.Screen.Width               = 800
    // GameEngine.StartState                 = MENU_MAIN
    // GameEngine.Version.Major              = 1
    // GameEngine.Version.Minor              = 0 
    // 
    // - applicationSettings
    //   Application.properties file fileName.
    //
    // - fileName
    //   Application.properties file fileName.
    //
    // - Return
    //   Generated settings list string.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public String toString ()
    {
        // Constants.

        final String S_EMPTY                     = "";
        final String S_NEW_LINE                  = "\n";
        final String S_QUOTE_OPEN                = "\"";
        final String S_QUOTE_CLOSE               = "\"";        
        final String S_BRACKET_OPEN              = "(";
        final String S_BRACKET_CLOSE             = ")";
        final String S_ASSIGNMENT                = " = ";
        final String S_INDENT                    = "  ";
        final String S_START_STRING              = "%";
        final String S_END_STRING                = "s";
        final String S_LEFT_JUSTIFY              = "-";
        final String S_RIGHT_JUSTIFY             = "";
        final String S_TEXT_APPLICATION_SETTINGS = "Application settings file: ";

        // Message configuration.

        Boolean sortPropertyList      = true;               // Do we want to sort the list or not.
        Boolean enclosePropertyValues = false;              // Enclose property values within user specified bracketing characters.
        Boolean rightAlign            = false;              // Right align the property fileName column. We will make the default, left alignment

        // Configure tabulation and column alignment.
        
        String alignmentFlag            = S_LEFT_JUSTIFY;   // Set to left alignment by default. "-" = Left Align, Empty string "" = Right Align.        
        if ( rightAlign ) alignmentFlag = S_RIGHT_JUSTIFY;  // Set to right align if flagged to do so.

        // Configure property value bracketing format.

        String bracketOpen;
        String bracketClose;

        if ( enclosePropertyValues )
        {
            bracketOpen  = S_BRACKET_OPEN;  // Set to an open bracket string, e.g "'", "(", "( ", "< ", "[ ", etc.
            bracketClose = S_BRACKET_CLOSE; // Set to an close bracket string, e.g "'", ")", " )", " >", " ]", etc.
        }
        else
        {
            bracketOpen  = S_EMPTY;         // Set to an empty string.
            bracketClose = S_EMPTY;         // Set to an empty string.
        }

        // Initialize the settings list header.

        String textBuffer = S_EMPTY;

        textBuffer += S_NEW_LINE + S_NEW_LINE;
        textBuffer += S_INDENT + S_TEXT_APPLICATION_SETTINGS;
        textBuffer += S_QUOTE_OPEN + this.fileName + S_QUOTE_CLOSE;
        textBuffer += S_NEW_LINE + S_NEW_LINE;

        // It easer to sort an array of strings, than a collection, a map or a set.
        // Therefore, we shall get a set of property names from the collection, and then 
        // convert the set into an array of strings.

        Set <String> keySet = this.settings.stringPropertyNames ();                 // Get set of property fileName strings.
        String [] keyArray  = ( String [] ) keySet.toArray ( new String [ 0 ] );    // Convert the set into an array of strings, so that we can easily sort the array.

        // Sort the application settings property names.

        if ( sortPropertyList ) Arrays.sort ( keyArray );                       

        // Get the string length of the longest property fileName, so that we know where to place the aligned property column.
        // At the end of the loop, longestKey will be the value the longest property fileName.

        int longestKey = 0;                                         

        for ( String key : keyArray )
        {
         // Get the length of the current property fileName.
            
            int keyLength = key.length ();
            
            // If its longer than any of the others, then keep its length.
            
            if ( keyLength > longestKey ) longestKey = keyLength;
        }

        // Add the formatted list of property names (keys) and their property values to the output text list..

        for ( String key : keyArray )
        {
            String formatedkey = S_INDENT;
            
            // Align text and pad with white space up to the length of the longest property fileName.
            
            formatedkey += String.format ( S_START_STRING + alignmentFlag + longestKey + S_END_STRING, key );
            
            // Add the formatted property fileName with white space padding.
            
            textBuffer += formatedkey + S_ASSIGNMENT;
            
            // Add the property value.
            
            textBuffer += bracketOpen + getSetting ( key ) + bracketClose;
            textBuffer += S_NEW_LINE;
        }

        // Send the completed logger message back to the caller.

        return textBuffer;
    }
}
