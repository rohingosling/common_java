
package rohin.gameengine.Application;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.*;

import rohin.gameengine.ApplicationSettings;
import rohin.gameengine.ConsoleLogger;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.GraphicsWindow;
import rohin.gameengine.StringTable;
import rohin.gameengine.TextFormat;
import rohin.gameengine.Vector2D;
import rohin.gameengine.Component.ComponentGeometry;
import rohin.gameengine.Component.ComponentPhysics;
import rohin.gameengine.Component.ComponentProjection2D;
import rohin.gameengine.Component.ComponentResourceSprite;
import rohin.gameengine.Component.ComponentShapeCircle;
import rohin.gameengine.Component.ComponentTransform;
import rohin.gameengine.Entity.EntityBallBlue;
import rohin.gameengine.Entity.EntityBallGreen;
import rohin.gameengine.Entity.EntityBallRed;
import rohin.gameengine.Entity.EntityExample;
import rohin.gameengine.GameEngine.GameEngineLevel1;
import rohin.gameengine.GameEngine.GameEngineLevel2;
import rohin.gameengine.GameEngine.GameEngineLevel3;
import rohin.gameengine.GameEngine.GameEngineMenuSystem;
import rohin.gameengine.GameEngine.GameEngineTest;
import rohin.gameengine.System.SystemExample;
import rohin.gameengine.System.SystemPhysicsEngine;
import rohin.gameengine.System.SystemRenderer;

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Main Application program.
// 
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
//
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Application
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
        EXIT,               // Terminate Application loop.
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
    // FIELDS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Application properties file: Application settings.
    
    private String                  applicationName;                // Application name. Used for UI, logging, and other display purposes.
    private String                  applicationWindowTitle;         // Display string for Application window title bar.
    private int                     versionMajor;                   // Major version number.
    private int                     versionMinor;                   // Minor version number.
    private int                     screenWidth;                    // Main Application window width.
    private int                     screenHeight;                   // Main Application window height.
    private int                     languageCode;                   // UI language code.    
    private ApplicationState        applicationState;               // Used to control FA (Finite Automata) style program flow.
    
    // Application properties file: Renderer.
    
    private Boolean                 rendererEntityRotationEnabled;
    private Boolean                 rendererEntityScaleEnabled;
    private Boolean                 rendererSpritesVisible;
    private Boolean                 rendererGeometryVisible;
    private Boolean                 rendererTranslationHistoryVisible;
    private int                     rendererTranslationHistoryDepth;
    private Boolean                 rendererHUDVisible;
    private Boolean                 rendererGridVisible;
    private Boolean                 rendererGridAxisXVisible;
    private Boolean                 rendererGridAxisYVisible;
    private Boolean                 rendererGridSubdevisionMajorVisible;
    private Boolean                 rendererGridSubdevisionMinorVisible;
    private int                     rendererGridSubdevisionMajorX;
    private int                     rendererGridSubdevisionMajorY;
    private int                     rendererGridSubdevisionMinorX;
    private int                     rendererGridSubdevisionMinorY;
    
    
    // Application properties file: Game engine.
    
    private String                  gameEngineResourcePath;         // Disk location, where game assets are loaded from.
    private Boolean                 gameEngineDebugOverlayVisible;  // Debugging information overlay switch.
    private Boolean                 gameEngineLoggingEnabled;       // Game engine specific logging switch.
    
    // Application properties file: Game loop.

    private int                     gameLoopDelayFixed;             // Game loop, frame rate regulator. Fixed delay, measured in ms.        
    private int                     gameLoopDelayMin;               // Minimum loop iteration delay.
    private double                  gameLoopFPSTarget;              // Target Frames Per Second.
    private Boolean                 gameLoopFPSTargetEnabled;       // Choose whether to use acceleration fixed loop delay, or acceleration target FPS.
    
    // String table
    
    private String                  languageStringTablePath;        // File path where string table/s are stored.
    private String                  languageStringTableFile;        // The fully qualified file fileName and path of the language string table file.
    private StringTable             stringTable;                    // String table to accommodate multi-language support.
    
    // Logging.
    
    private ConsoleLogger           logger;                         // Console logger.
    private Boolean                 loggingEnabled;                 // Enable Application logging.
    
    // Application resources.
    
    private ApplicationSettings     settings;                       // Application settings manager.
    private GraphicsWindow          applicationWindow;              // Application window. 
    private Graphics2D              graphicsAPI;                    // Java 2D graphics API.
    private BufferStrategy          screenBuffer;                   // Display buffer.
    private KeyboardEventHandler    eventHandlerKeyboard;           // Keyboard event handler.
        
    // ECS Game engine.
    
    private GameEngineMenuSystem    menuSystem;
    private GameEngineLevel1        gameLevel1;
    private GameEngineLevel2        gameLevel2;
    private GameEngineLevel3        gameLevel3;
    private GameEngineTest          gameTest;   

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:off
    
    // Accessors (Getters)
    
    public GameEngineTest       getGameTest                           () { return this.gameTest;                            }
    public GameEngineLevel1     getGameEngineLevel1                   () { return this.gameLevel1;                          }
    public GameEngineLevel2     getGameEngineLevel2                   () { return this.gameLevel2;                          }
    public GameEngineLevel3     getGameEngineLevel3                   () { return this.gameLevel3;                          }
    public GameEngineMenuSystem getGameEngineMenuSystem               () { return this.menuSystem;                          }
    public BufferStrategy       getScreenBuffer                       () { return this.screenBuffer;                        }
    public Graphics2D           getGraphicsAPI                        () { return this.graphicsAPI;                         }
    public int                  getScreenWidth                        () { return this.screenWidth;                         }
    public int                  getScreenHeight                       () { return this.screenHeight;                        }
    public Boolean              isRendererEntityRotationEnabled       () { return this.rendererEntityRotationEnabled;       }
    public Boolean              isRendererEntityScakeEnabled          () { return this.rendererEntityScaleEnabled;          }
    public Boolean              isRendererSpritesVisible              () { return this.rendererSpritesVisible;              }
    public Boolean              isRendererGeometryVisible             () { return this.rendererGeometryVisible;             }    
    public Boolean              isRendererTranslationHistoryVisible   () { return this.rendererTranslationHistoryVisible;   }
    public int                  getRendererTranslationHistoryDepth    () { return this.rendererTranslationHistoryDepth;     }
    public Boolean              isRendererHUDVisible                  () { return this.rendererHUDVisible;                  }
    public Boolean              isRendererGridVisible                 () { return this.rendererGridVisible;                 }
    public Boolean              isRendererGridAxisXVisible            () { return this.rendererGridAxisXVisible;            }
    public Boolean              isRendererGridAxisYVisible            () { return this.rendererGridAxisYVisible;            }
    public Boolean              isRendererGridSubdevisionMajorVisible () { return this.rendererGridSubdevisionMajorVisible; }
    public Boolean              isRendererGridSubdevisionMinorVisible () { return this.rendererGridSubdevisionMinorVisible; }
    public int                  getRendererGridSubdevisionMajorX      () { return this.rendererGridSubdevisionMajorX;       }
    public int                  getRendererGridSubdevisionMajorY      () { return this.rendererGridSubdevisionMajorY;       }
    public int                  getRendererGridSubdevisionMinorX      () { return this.rendererGridSubdevisionMinorX;       }
    public int                  getRendererGridSubdevisionMinorY      () { return this.rendererGridSubdevisionMinorY;       }
    
    // Mutators (Setters)
    
    public void setRendererSpritesVisible            ( Boolean visible ) { this.rendererSpritesVisible            = visible; };
    public void setRendererGeometryVisible           ( Boolean visible ) { this.rendererGeometryVisible           = visible; };
    public void setRendererGridVisible               ( Boolean visible ) { this.rendererGridVisible               = visible; };
    public void setRendererHUDVisible                ( Boolean visible ) { this.rendererHUDVisible                = visible; };
    public void setRendererTranslationHistoryVisible ( Boolean visible ) { this.rendererTranslationHistoryVisible = visible; };
    public void setRendererTranslationHistoryDepth   ( int     depth   ) { this.rendererTranslationHistoryDepth   = depth;   };
    
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
        // Call initialization methods.
        
        initializeApplicationSettings ();       // Initialize Application settings.
        initializeStringTable ();               // Load the string table, for the appropriate language retrieved from the Application settings file.
        initializeGraphicsWindow ();            // Initialize graphics window.       
        initializeGameEngine ();                // Initialize ECS game engine/s.
        initializeGameLoops ();                 // Initialize the game loops of all our game engines.
        initializeSystems ();                   // Initialize ECS systems.
        initializeEntities ();                  // Initialize ECS entities.
        initializeKeyboard ();                  // Initialize keyboard handler.
        initializeResources ();                 // Initialize resources.
        
        // Console logger.

        logger.log ( this.settings.toString () + this.gameTest.printGameObjects () );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeApplicationSettings
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeApplicationSettings ()
    {   
        // Load settings from disk.
        
        this.settings = new ApplicationSettings ();
        this.settings.load ( Constants.FILE_APPLICATION_SETTINGS );
        
        // Load Application settings.
        
        this.applicationName         = this.settings.getString  ( Constants.APPLICATION_NAME );
        this.versionMajor            = this.settings.getInteger ( Constants.APPLICATION_VERSION_MAJOR );
        this.versionMinor            = this.settings.getInteger ( Constants.APPLICATION_VERSION_MINOR );
        this.screenWidth             = this.settings.getInteger ( Constants.APPLICATION_SCREEN_WIDTH );
        this.screenHeight            = this.settings.getInteger ( Constants.APPLICATION_SCREEN_HEIGHT );
        this.languageCode            = this.settings.getInteger ( Constants.APPLICATION_LANGUAGE_CODE );
        this.languageStringTablePath = this.settings.getString  ( Constants.APPLICATION_LANGUAGE_STRING_TABLE_PATH );
        this.loggingEnabled          = this.settings.getBoolean ( Constants.APPLICATION_LOGGING_ENABLED );
        
        // Load application renderer settings.
        
        this.rendererEntityRotationEnabled       = this.settings.getBoolean ( Constants.RENDERER_ENTITY_ROTATION_ENABLED );
        this.rendererEntityScaleEnabled          = this.settings.getBoolean ( Constants.RENDERER_ENTITY_SCALE_ENABLED );
        this.rendererSpritesVisible              = this.settings.getBoolean ( Constants.RENDERER_SPRITES_VISIBLE );
        this.rendererGeometryVisible             = this.settings.getBoolean ( Constants.RENDERER_GEOMETRY_VISIBLE );        
        this.rendererHUDVisible                  = this.settings.getBoolean ( Constants.RENDERER_HUD_VISIBLE );
        this.rendererGridVisible                 = this.settings.getBoolean ( Constants.RENDERER_GRID_VISIBLE );
        this.rendererGridAxisXVisible            = this.settings.getBoolean ( Constants.RENDERER_GRID_AXIS_X_VISIBLE );
        this.rendererGridAxisYVisible            = this.settings.getBoolean ( Constants.RENDERER_GRID_AXIS_Y_VISIBLE );
        this.rendererGridSubdevisionMajorVisible = this.settings.getBoolean ( Constants.RENDERER_GRID_SUBDEVISION_MAJOR_VISIBLE );
        this.rendererGridSubdevisionMinorVisible = this.settings.getBoolean ( Constants.RENDERER_GRID_SUBDEVISION_MINOR_VISIBLE );
        this.rendererGridSubdevisionMajorX       = this.settings.getInteger ( Constants.RENDERER_GRID_SUBDEVISION_MAJOR_X );
        this.rendererGridSubdevisionMajorY       = this.settings.getInteger ( Constants.RENDERER_GRID_SUBDEVISION_MAJOR_Y );
        this.rendererGridSubdevisionMinorX       = this.settings.getInteger ( Constants.RENDERER_GRID_SUBDEVISION_MINOR_X );
        this.rendererGridSubdevisionMinorY       = this.settings.getInteger ( Constants.RENDERER_GRID_SUBDEVISION_MINOR_Y );
        this.rendererTranslationHistoryVisible   = this.settings.getBoolean ( Constants.RENDERER_TRANSLATION_HISTORY_VISIBLE );
        this.rendererTranslationHistoryDepth     = this.settings.getInteger ( Constants.RENDERER_TRANSLATION_HISTORY_DEPTH );
        
        // Load game engine settings.
        
        this.gameEngineResourcePath        = this.settings.getString  ( Constants.GAME_ENGINE_RESOURCE_PATH );
        this.gameEngineDebugOverlayVisible = this.settings.getBoolean ( Constants.GAME_ENGINE_DEBUG_OVERLAY_VISIBLE );
        this.gameEngineLoggingEnabled      = this.settings.getBoolean ( Constants.GAME_ENGINE_LOGGING_ENABLED );
        
        // Load game loop settings.
        
        this.gameLoopFPSTargetEnabled =          this.settings.getBoolean ( Constants.GAME_LOOP_FPS_TARGET_ENABLED );
        this.gameLoopFPSTarget        = (double) this.settings.getInteger ( Constants.GAME_LOOP_FPS_TARGET );
        this.gameLoopDelayFixed       =          this.settings.getInteger ( Constants.GAME_LOOP_DELAY_FIXED );
        this.gameLoopDelayMin         =          this.settings.getInteger ( Constants.GAME_LOOP_DELAY_MIN );
        
        // Initialize Application.
                        
        this.applicationState = ApplicationState.EXIT;
        this.logger           = new ConsoleLogger ( this, this.loggingEnabled );
        
        // Compile Application window title string.
        
        compileApplicationWindowTitle ();
        
        // Console logger.
        
        logger.log ();
    }
    
 // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // InitializeStringTable
    //
    // Load the string table file, referenced in the Application settings file
    // through the language code property.
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeStringTable ()
    {
        // Create acceleration new string table for storing and managing language dependent UI text.
        
        this.stringTable = new StringTable ();
        
        // Select the appropriate string table file, based on the language code retrieved from the Application settings file.
        
        switch ( this.languageCode )
        {
            case 0: this.languageStringTableFile = Constants.FILE_STRING_TABLE_ENGLISH; break;
            case 1: this.languageStringTableFile = Constants.FILE_STRING_TABLE_GREEK;   break;
            case 2: this.languageStringTableFile = Constants.FILE_STRING_TABLE_KLINGON; break;
            case 3: this.languageStringTableFile = Constants.FILE_STRING_TABLE_SNOOP;   break;
        }
        
        // Load the selected string table file.
        
        String filePath = this.languageStringTablePath + "\\" + this.languageStringTableFile;
        
        this.stringTable.loadStringTable ( filePath );
        
        // Console Logger.

        logger.log ( this.stringTable.toString () ); 
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
    
    private void initializeGraphicsWindow ()
    {
        try
        {
            // Initialize Graphics window.
        
            this.applicationWindow = new GraphicsWindow ( this.screenWidth, this.screenHeight );
            this.applicationWindow.setTitle             ( this.applicationWindowTitle );    
            
            // Get the double buffered screen buffer.
            
            this.screenBuffer = this.applicationWindow.getScreenBuffer ();
            
            // Bind graphics platform to our screen buffer.
            
            this.graphicsAPI = ( Graphics2D ) this.screenBuffer.getDrawGraphics ();
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
               
        // Console Logger.

        logger.log (); 
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeKeyboard
    //
    // Add and configure acceleration keyboard event handler to the game engine.
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeKeyboard ()
    {
        // Create acceleration new input device event handler.
        // - Give the keyboard Event handler access to acceleration command invocation interpreter, or and event manager.
        // - Pass in acceleration handle to the game engine, so that the keyboard handler hass access to all game assets that may be required by commands.
        
        this.eventHandlerKeyboard = new KeyboardEventHandler ();
        this.eventHandlerKeyboard.setApplication ( this );
        
        // Add the keyboard handler to our Application screen.
        
        this.applicationWindow.getScreen ().addKeyListener ( this.eventHandlerKeyboard );
        
        // Give the graphics screen focus, in order to receive keyboard events.
        
        this.applicationWindow.getScreen ().requestFocus ();
        
        // Console Logger.

        logger.log (); 
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeGameEngine
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeGameEngine ()
    {
        // Initialize game levels/.
        
        this.menuSystem = new GameEngineMenuSystem ( this );
        this.gameLevel1 = new GameEngineLevel1     ( this );            
        this.gameLevel2 = new GameEngineLevel2     ( this );  
        this.gameLevel3 = new GameEngineLevel3     ( this );
        this.gameTest   = new GameEngineTest       ( this );
        
        // Console logger.
                  
        logger.log ();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeGameLoops
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeGameLoops ()
    {   
        // Initialize game engine.
        
        this.gameTest.setResourcePath   ( this.gameEngineResourcePath );
        this.gameTest.setloggingEnabled ( this.gameEngineLoggingEnabled );
        
        // Initialize game loop.
        
        this.gameTest.setFPSTargetEnabled ( this.gameLoopFPSTargetEnabled );
        this.gameTest.setFPSTarget        ( this.gameLoopFPSTarget );
        this.gameTest.setLoopDelayFixed   ( this.gameLoopDelayFixed );
        this.gameTest.setLoopDelayMin     ( this.gameLoopDelayMin );
               
        // Console logger.
                  
        logger.log ();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeSystems.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeSystems ()
    {  
        // Add systems to game engine. Game engine level ≡ GameEngineTest.
        
        GameEngineTest gameTest = this.gameTest;
        
        gameTest.addSystem ( Constants.SYSTEM_EXAMPLE,        new SystemExample       ( gameTest ) );
        gameTest.addSystem ( Constants.SYSTEM_PHYSICS_ENGINE, new SystemPhysicsEngine ( gameTest ) );
        gameTest.addSystem ( Constants.SYSTEM_COLLIDER,       new SystemExample       ( gameTest ) );
        gameTest.addSystem ( Constants.SYSTEM_RENDERER,       new SystemRenderer      ( gameTest ) );
            
        // Console logger.
                  
        logger.log ();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeEntities.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeResources ()
    {   
        // Console logger.
                  
        logger.log ();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // InitializeEntities.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeEntities ()
    {
        // Add entities to game engine. Game engine level ≡ GameEngineTest.
        
        GameEngineTest gameTest = this.gameTest;
        
        gameTest.addEntity ( Constants.ENTITY_EXAMPLE,    new EntityExample   ( gameTest ) );
        gameTest.addEntity ( Constants.ENTITY_BALL_RED,   new EntityBallRed   ( gameTest ) );
        gameTest.addEntity ( Constants.ENTITY_BALL_GREEN, new EntityBallGreen ( gameTest ) );
        gameTest.addEntity ( Constants.ENTITY_BALL_BLUE,  new EntityBallBlue  ( gameTest ) );
        
        // Console logger.
        
        logger.log ();
        
        // Configure entities.
        
        initializeEntityBallBlue ();
        initializeEntityBallRed ();
    }
        
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // initializeEntityBallBlue
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeEntityBallBlue ()
    {
        try
        {        
            // Local working variables.
            
            ECSEngine                    gameEngine = null;
            HashMap <Integer, ECSEntity> entities   = null;
            ComponentTransform           transform  = null;            
            ComponentPhysics             physics    = null;
            ComponentGeometry            geometry   = null;
            ComponentResourceSprite      sprite     = null;
            ComponentProjection2D        projection = null;
            
            // Select game engine entities to work with.
            
            gameEngine = this.gameTest;
            entities   = gameEngine.getEntities ();
            
            // Select entity components we would like to work with.
            
            EntityBallBlue ballBlue = ( EntityBallBlue ) entities.get ( Constants.ENTITY_BALL_BLUE );
            
            transform  = ( ComponentTransform )      ballBlue.getComponent ( Constants.COMPONENT_TRANSFORM );
            physics    = ( ComponentPhysics )        ballBlue.getComponent ( Constants.COMPONENT_PHYSICS );
            geometry   = ( ComponentGeometry )       ballBlue.getComponent ( Constants.COMPONENT_GEOMETRY );
            sprite     = ( ComponentResourceSprite ) ballBlue.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
            projection = ( ComponentProjection2D )   ballBlue.getComponent ( Constants.COMPONENT_PROJECTION_2D );
            
            // Initialize transform.
            
            transform.origin                  = new Vector2D ( 0.0, 0.0 );
            transform.scale                   = new Vector2D ( 1.0, 1.0 );
            transform.rotation                = new double[] { 0.0, 0.0, 0.0 };
            transform.translation             = new Vector2D ( 0.0, 0.0 );
            transform.translationHistoryDepth = this.rendererTranslationHistoryDepth;
            
            // Initialize physics.
            
            physics.mass            = 1.0;
            physics.acceleration    = new Vector2D ( 0.0, 0.0 );
            physics.velocity        = new Vector2D ( 0.0, 0.0 );
            physics.force           = new Vector2D ( 0.0, 0.0 );
            physics.vMax            = 0.01;
            
            physics.accelerateUp    = false;
            physics.accelerateDown  = false;
            physics.accelerateRight = false;
            physics.accelerateLeft  = false;
            
            // Initialize projection.
            
            double zoom = Constants.WINDOW_ZOOM;    // Remember to flip the Y axis for projection from world coordinates to screen coordinates.
            
            projection.window   = new double[] { Constants.WINDOW_MIN_X, Constants.WINDOW_MIN_Y, Constants.WINDOW_MAX_X, Constants.WINDOW_MAX_Y };
            projection.origin   = new double[] { this.screenWidth / 2.0, this.screenHeight / 2.0 };
            projection.scale    = new double[] { zoom, -zoom };
            projection.viewPort = new double[] { 0.0, 0.0, this.screenWidth, this.screenHeight };
            projection.aspect   = new double[] { this.screenWidth, this.screenHeight };
            projection.layer    = Constants.LAYER_PLAYER;
            
            // Initialize geometry
                        
            double               radius        = 1.0 / 12.0;
            ComponentShapeCircle circle        = new ComponentShapeCircle ( radius );
            Color                geometryColor = Color.WHITE;
            
            circle.transform.translation.setVector ( 0.0,  0.0 );
            
            geometry.shapes.add ( circle );
            geometry.color = geometryColor;
            
            // sprite.
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }        
        
        // Console logger.
                  
        logger.log ();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------    
    // 
    // initializeEntityBallRed
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initializeEntityBallRed ()
    {
        try
        {        
            // Local working variables.
            
            ECSEngine                    gameEngine = null;
            HashMap <Integer, ECSEntity> entities   = null;
            ComponentTransform           transform  = null;            
            ComponentPhysics             physics    = null;
            ComponentGeometry            geometry   = null;
            ComponentResourceSprite      sprite     = null;
            ComponentProjection2D        projection = null;
            
            // Select game engine entities to work with.
            
            gameEngine = this.gameTest;
            entities   = gameEngine.getEntities ();
            
            // Select entity components we would like to work with.
            
            EntityBallRed ballRed = ( EntityBallRed ) entities.get ( Constants.ENTITY_BALL_RED );
            
            transform  = ( ComponentTransform )      ballRed.getComponent ( Constants.COMPONENT_TRANSFORM );
            physics    = ( ComponentPhysics )        ballRed.getComponent ( Constants.COMPONENT_PHYSICS );
            geometry   = ( ComponentGeometry )       ballRed.getComponent ( Constants.COMPONENT_GEOMETRY );
            sprite     = ( ComponentResourceSprite ) ballRed.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
            projection = ( ComponentProjection2D )   ballRed.getComponent ( Constants.COMPONENT_PROJECTION_2D );
            
            // Initialize transform.
            
            transform.origin                  = new Vector2D ( 0.0,  0.0 );
            transform.scale                   = new Vector2D ( 1.0,  1.0 );
            transform.rotation                = new double[] { 0.0,  0.0, 0.0 };
            transform.translation             = new Vector2D ( 0.0, -0.5 );
            transform.translationHistoryDepth = this.rendererTranslationHistoryDepth;
            
            // Initialize physics.
            
            physics.mass            = 1.0;
            physics.acceleration    = new Vector2D ( 0.0, 0.0 );
            physics.velocity        = new Vector2D ( 0.0, 0.0 );
            physics.force           = new Vector2D ( 0.0, 0.0 );
            
            physics.accelerateUp    = false;
            physics.accelerateDown  = false;
            physics.accelerateRight = false;
            physics.accelerateLeft  = false;
            
            // Initialize projection.
            
            double zoom = Constants.WINDOW_ZOOM;    // Remember to flip the Y axis for projection from world coordinates to screen coordinates.
            
            projection.window   = new double[] { Constants.WINDOW_MIN_X, Constants.WINDOW_MIN_Y, Constants.WINDOW_MAX_X, Constants.WINDOW_MAX_Y };
            projection.origin   = new double[] { this.screenWidth / 2.0, this.screenHeight / 2.0 };
            projection.scale    = new double[] { zoom, -zoom };
            projection.viewPort = new double[] { 0.0, 0.0, this.screenWidth, this.screenHeight };
            projection.aspect   = new double[] { this.screenWidth, this.screenHeight };
            projection.layer    = Constants.LAYER_PLAYER;
            
            // Initialize geometry
                        
            double               radius        = 2.0 / 12.0;
            ComponentShapeCircle circle        = new ComponentShapeCircle ( radius );
            Color                geometryColor = Color.RED;
            
            circle.transform.translation.setVector ( 0.0, 0.0 );
                        
            geometry.shapes.add ( circle );
            geometry.color = geometryColor;
            
            // sprite.
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }        
        
        // Console logger.
                  
        logger.log ();
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Call Run() to start the Application.
    //
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @ SuppressWarnings ( "incomplete-switch" )
    public void run ()
    {
        // Console logger.

        logger.log ();

        // Begin Application state management loop.

        this.applicationState = ApplicationState.START_UP;

        while ( this.applicationState != ApplicationState.EXIT )
        {
            switch ( this.applicationState )
            {
            case START_UP:
                logger.log ( "State = START_UP" );
                this.applicationState = ApplicationState.MENU_MAIN;
                break;

            case SHUT_DOWN:
                logger.log (  "State = SHUT_DOWN" );
                this.applicationState = ApplicationState.EXIT;
                break;

            case MENU_MAIN:
                logger.log (  "State = MENU_MAIN" );               
                                
                this.applicationState = ApplicationState.GAME_TEST;
                break;

            case MENU_SETTINGS:
                logger.log (  "State = MENU_SETTINGS" );
                break;

            case MENU_GAME_SETUP:
                logger.log (  "State = MENU_GAME_SETUP" );
                break;

            case GAME_LEVEL_1:
                logger.log (  "State = GAME_LEVEL_1" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
            
            case GAME_LEVEL_2:
                logger.log (  "State = GAME_LEVEL_2" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
                
            case GAME_LEVEL_3:
                logger.log (  "State = GAME_LEVEL_3" );
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;

            case GAME_TEST:
                logger.log (  "State = GAME_TEST" );
                this.gameTest.run ();
                this.applicationState = ApplicationState.SHUT_DOWN;
                break;
            }
        }

        // Console logger.

        logger.log (  "State = EXIT" );
    }
    
    
    

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // CompileApplicationWindowTitle
    //
    // Compiles the string to be displayed in th title bar of the Application
    // window.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void compileApplicationWindowTitle ()
    {
        String S_EMPTY                     = "";
        String S_SPACE                     = " ";        
        String S_VERSION                   = "Version ";
        String S_VERSION_PARENTHESES_OPEN  = "(";
        String S_VERSION_PARENTHESES_CLOSE = ")";
        String S_VERSION_DECIMAL_MARK      = ".";
        
        // Compile Application title string.

        this.applicationWindowTitle =  S_EMPTY;
        this.applicationWindowTitle += this.applicationName;
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
