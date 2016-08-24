
package rohin.gameengine;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import rohin.gameengine.ConsoleLogger;

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Main application program.
// 
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
//
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Application extends ECSObject
{
    // @formatter:off
    
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // DATA TYPES
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    enum ApplicationState
    {        
        START_UP,           // Start up and initialization.
        SHUT_DOWN,          // Shut down and clean up.
        EXIT,               // Terminate application loop.
        MENU_MAIN,          // Menu ECSSystem: Main menu.
        MENU_SETTINGS,      // Menu ECSSystem: Main menu / Settings.
        MENU_GAME_SETUP,    // Menu ECSSystem: Main menu / Game Setup.
        GAME_LEVEL_1,       // Execute game loop. Level 1.
        GAME_LEVEL_2,       // Execute game loop. Level 2.
        GAME_LEVEL_3,       // Execute game loop. Level 2.
        GAME_TEST           // Execute game loop. Test level.
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTANTS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Application Settings.
    
    private static final int M_DEFAULT_SCREEN_WIDTH  = 800;
    private static final int M_DEFAULT_SCREEN_HEIGHT = 600;
    
    // Application settings file.
    
    private static final String M_FILE_APPLICATION_SETTINGS   = "game_engine_1.0.properties"; 
    private static final String M_APPLICATION_NAME            = "Application.Name";
    private static final String M_APPLICATION_VERSION_MAJOR   = "Application.Version.Major";
    private static final String M_APPLICATION_VERSION_MINOR   = "Application.Version.Minor";
    private static final String M_APPLICATION_SCREEN_WIDTH    = "Application.Screen.Width";
    private static final String M_APPLICATION_SCREEN_HEIGHT   = "Application.Screen.Height";
    private static final String M_APPLICATION_LANGUAGE_CODE   = "Application.Language.Code";
    private static final String M_APPLICATION_LOGGING_ENABLED = "Application.Logging.Enabled";    
        
    // String tables.
    
    private static final String M_FILE_STRING_TABLE_ENGLISH = "string_table_english.xml";
    private static final String M_FILE_STRING_TABLE_GREEK   = "string_table_greek.xml";
    private static final String M_FILE_STRING_TABLE_KLINGON = "string_table_klingon.xml";
    private static final String M_FILE_STRING_TABLE_SNOOP   = "string_table_snoop.xml";
    
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Application settings.
    
    private String                  applicationWindowTitle;     // Display string for application window title bar.
    private int                     versionMajor;               // Major version number.
    private int                     versionMinor;               // Minor version number.
    private int                     screenWidth;                // Main application window width.
    private int                     screenHeight;               // Main application window height.
    private int                     languageCode;               // UI language code.
    private ApplicationState        applicationState;           // Used to control FA (Finite Automata) style program flow.
    
    // String table
    
    private String                  languageStringTableFile;    // The fully qualified file fileName and path of the language string table file.
    private StringTable             stringTable;                // String table to accommodate multi-language support.
    
    // Application resources.
    
    private ApplicationSettings     settings;                   // Application settings manager.
    private GraphicsWindow          applicationWindow;          // Application window.
    private BufferStrategy          screenBuffer;               // Display buffer.
    private KeyboardEventHandlerTemplate    eventHandlerKeyboard;       // Keyboard event handler.
    
    // Game engine.
    
    private ECSEngineMenuSystem     menuSystem;
    private ECSEngineGameLevel1     gameLevel1;
    private ECSEngineGameLevel2     gameLevel2;
    private ECSEngineGameLevel3     gameLevel3;
    private ECSEngineGameTest       gameTest;    
    
    // Logging.
    
    private ConsoleLogger           logger;
    private Boolean                 loggingEnabled;
    

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:on
    
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // METHODS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 1
    //
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public Application ()
    {        
        InitializeApplicationSettings ();       // Initialize application settings.
        InitializeStringTable ();               // Load the string table, for the appropriate language retrieved from the application settings file.
        InitializeGraphicsWindow ();            // Initialize graphics window.
        InitializeKeyboard ();                  // Initialize keyboard handler.
        InitializeGameEngine ();                // Initialize games engine/s. 
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeApplicationSettings
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void InitializeApplicationSettings ()
    {   
        // Load settings from disk.
        
        this.settings = new ApplicationSettings ();
        this.settings.Load ( M_FILE_APPLICATION_SETTINGS );
        
        this.name            = this.settings.GetString  ( M_APPLICATION_NAME );
        this.versionMajor    = this.settings.GetInteger ( M_APPLICATION_VERSION_MAJOR );
        this.versionMinor    = this.settings.GetInteger ( M_APPLICATION_VERSION_MINOR );
        this.screenWidth     = this.settings.GetInteger ( M_APPLICATION_SCREEN_WIDTH );
        this.screenHeight    = this.settings.GetInteger ( M_APPLICATION_SCREEN_HEIGHT );
        this.languageCode    = this.settings.GetInteger ( M_APPLICATION_LANGUAGE_CODE );
        this.loggingEnabled  = this.settings.GetBoolean ( M_APPLICATION_LOGGING_ENABLED );
        
        // Initialize application.
        
        this.id               = 0;
        this.text             = "Test Game";        
        this.applicationState = ApplicationState.EXIT;
        this.logger           = new ConsoleLogger ( this, this.loggingEnabled );
        
        // Compile application window title string.
        
        CompileApplicationWindowTitle ();
        
        // Console logger.
        
        logger.Log ();
    }
    
 // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // InitializeStringTable
    //
    // Load the string table file, referenced in the application settings file
    // through the language code property.
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void InitializeStringTable ()
    {
        // Create a new string table for storing and managing language dependent UI text.
        
        this.stringTable = new StringTable ();
        
        // Select the appropriate string table file, based on the language code retrieved from the application settings file.
        
        switch ( this.languageCode )
        {
            case 0: this.languageStringTableFile = M_FILE_STRING_TABLE_ENGLISH; break;
            case 1: this.languageStringTableFile = M_FILE_STRING_TABLE_GREEK;   break;
            case 2: this.languageStringTableFile = M_FILE_STRING_TABLE_KLINGON; break;
            case 3: this.languageStringTableFile = M_FILE_STRING_TABLE_SNOOP;   break;
        }
        
        // Load the selected string table file.
        
        this.stringTable.loadStringTable ( this.languageStringTableFile );
        
        // Console Logger.

        logger.Log ( this.stringTable.toString () ); 
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeGraphicsWindow
    // 
    // Arguments:
    //
    // - width
    //   Screen width.
    //
    // - height
    //   Screen height.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void InitializeGraphicsWindow ()
    {        
        // Initialize Graphics window.
    
        this.applicationWindow = new GraphicsWindow ( this.screenWidth, this.screenHeight );
        this.applicationWindow.setTitle             ( this.applicationWindowTitle );    
        
        // Get the double buffered screen buffer.
        
        this.screenBuffer = this.applicationWindow.GetScreenBuffer ();   
        
        // Console Logger.

        logger.Log (); 
    }
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeKeyboard
    //
    // Add and configure a keyboard event handler to the game engine.
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void InitializeKeyboard ()
    {
        // Create a new input device event handler.
        
        this.eventHandlerKeyboard = new KeyboardEventHandlerTemplate ();
        
        // Give the keyboard Event handler access to a command interpreter or event manager.
        
        //this.eventHandlerKeyboard.setGameEngine ( this );
        
        // Add the keyboard handler.
        
        this.applicationWindow.GetScreen ().addKeyListener ( this.eventHandlerKeyboard );
        
        // Give the graphics screen focus, in order to receive keyboard events.
        
        this.applicationWindow.GetScreen ().requestFocus ();
        
        // Console Logger.

        logger.Log (); 
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeGameEngine
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void InitializeGameEngine ()
    {
        // Initialize game engine components.
        
        this.menuSystem = new ECSMenuSystem ( 0, "GAME_ENGINE_MENU_SYSTEM" );
        this.gameLevel1 = new ECSGameLevel1       ( 1, "GAME_ENGINE_LEVEL_1"     );            
        this.gameLevel2 = new ECSGameLevel1       ( 2, "GAME_ENGINE_LEVEL_2"     );  
        this.gameLevel3 = new ECSGameLevel1       ( 3, "GAME_ENGINE_LEVEL_3"     );
        this.gameTest   = new ECSGameLevel1       ( 4, "GAME_ENGINE_TEST"        );
        
        // Set game engine view port to the same dimentions as the graphics window.
        
        this.gameTest.SetWidth  ( this.screenWidth );
        this.gameTest.SetHeight ( this.screenHeight );
        
        // Initialize graphics context handle, and pass to game engine objects.
        
        Graphics2D graphicsHandle = ( Graphics2D ) this.screenBuffer.getDrawGraphics ();
        
        this.menuSystem.SetGraphicsHandle ( graphicsHandle );
        this.gameTest.SetGraphicsHandle   ( graphicsHandle );
        
        // Console logger.
                  
        logger.Log ( this.toString () + this.settings.toString () );
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Call Run() to start the application.
    //
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @ SuppressWarnings ( "incomplete-switch" )
    public void Run ()
    {
        // Console logger.

        logger.Log ();

        // Begin application state management loop.

        this.applicationState = ApplicationState.START_UP;

        while ( this.applicationState != ApplicationState.EXIT )
        {
            switch ( this.applicationState )
            {
            case START_UP:
                logger.Log ( "State = START_UP" );
                this.applicationState = ApplicationState.MENU_MAIN;
                break;

            case SHUT_DOWN:
                logger.Log (  "State = SHUT_DOWN" );
                this.applicationState = ApplicationState.EXIT;
                break;

            case MENU_MAIN:
                logger.Log (  "State = MENU_MAIN" );               
                                
                this.applicationState = ApplicationState.GAME_TEST;
                break;

            case MENU_SETTINGS:
                logger.Log (  "State = MENU_SETTINGS" );
                break;

            case MENU_GAME_SETUP:
                logger.Log (  "State = MENU_GAME_SETUP" );
                break;

            case GAME_LEVEL_1:
                logger.Log (  "State = GAME_LEVEL_1" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
            
            case GAME_LEVEL_2:
                logger.Log (  "State = GAME_LEVEL_2" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
                
            case GAME_LEVEL_3:
                logger.Log (  "State = GAME_LEVEL_3" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;

            case GAME_TEST:
                logger.Log (  "State = GAME_TEST" );
                this.gameTest.Run ();
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
            }
        }

        // Console logger.

        logger.Log (  "State = EXIT" );
    }
    
    
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // CompileApplicationWindowTitle
    //
    // Compiles the string to be displayed in th title bar of the application
    // window.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void CompileApplicationWindowTitle ()
    {
        String S_EMPTY                     = "";
        String S_SPACE                     = " ";        
        String S_VERSION                   = "Version ";
        String S_VERSION_PARENTHESES_OPEN  = "(";
        String S_VERSION_PARENTHESES_CLOSE = ")";
        String S_VERSION_DECIMAL_MARK      = ".";
        
        // Compile application title string.

        this.applicationWindowTitle =  S_EMPTY;
        this.applicationWindowTitle += this.name;
        this.applicationWindowTitle += S_SPACE;
        this.applicationWindowTitle += S_VERSION_PARENTHESES_OPEN;
        this.applicationWindowTitle += S_VERSION;
        this.applicationWindowTitle += Integer.toString ( this.versionMajor );
        this.applicationWindowTitle += S_VERSION_DECIMAL_MARK;
        this.applicationWindowTitle += Integer.toString ( this.versionMinor );
        this.applicationWindowTitle += S_VERSION_PARENTHESES_CLOSE;        
    }
    
    // @formatter:on

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OVERRIDEBLE METHODS
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
