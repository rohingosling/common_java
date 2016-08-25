package rohin.gameengine.Application;

public class Constants
{            
    // String tables.
    
    public static final String FILE_STRING_TABLE_ENGLISH = "string_table_english.xml";
    public static final String FILE_STRING_TABLE_GREEK   = "string_table_greek.xml";
    public static final String FILE_STRING_TABLE_KLINGON = "string_table_klingon.xml";
    public static final String FILE_STRING_TABLE_SNOOP   = "string_table_snoop.xml";
    
    // Application properties File: Application settings.
    
    public static final String FILE_APPLICATION_SETTINGS              = "game_engine_1.0.properties"; 
    public static final String APPLICATION_NAME                       = "Application.Name";
    public static final String APPLICATION_VERSION_MAJOR              = "Application.Version.Major";
    public static final String APPLICATION_VERSION_MINOR              = "Application.Version.Minor";
    public static final String APPLICATION_SCREEN_WIDTH               = "Application.Screen.Width";
    public static final String APPLICATION_SCREEN_HEIGHT              = "Application.Screen.Height";
    public static final String APPLICATION_LANGUAGE_CODE              = "Application.Language.Code";
    public static final String APPLICATION_LANGUAGE_STRING_TABLE_PATH = "Application.Language.StringTable.Path";
    public static final String APPLICATION_LOGGING_ENABLED            = "Application.Logging.Enabled";
    
    // Application properties file: Renderer settings.
    
    public static final String RENDERER_ENTITY_ROTATION_ENABLED        = "Renderer.Entity.Rotation.Enabled";
    public static final String RENDERER_ENTITY_SCALE_ENABLED           = "Renderer.Entity.Scale.Enabled";    
    public static final String RENDERER_SPRITES_VISIBLE                = "Renderer.Sprites.Visible";
    public static final String RENDERER_GEOMETRY_VISIBLE               = "Renderer.Geometry.Visible";
    public static final String RENDERER_TRANSLATION_HISTORY_VISIBLE    = "Renderer.TranslationHistory.Visible";
    public static final String RENDERER_TRANSLATION_HISTORY_DEPTH      = "Renderer.TranslationHistory.Depth";
    public static final String RENDERER_HUD_VISIBLE                    = "Renderer.HUD.Visible";
    public static final String RENDERER_GRID_VISIBLE                   = "Renderer.Grid.Visible";
    public static final String RENDERER_GRID_AXIS_X_VISIBLE            = "Renderer.Grid.Axis.X.Visible";
    public static final String RENDERER_GRID_AXIS_Y_VISIBLE            = "Renderer.Grid.Axis.Y.Visible";
    public static final String RENDERER_GRID_SUBDEVISION_MAJOR_VISIBLE = "Renderer.Grid.Subdevision.Major.Visible";
    public static final String RENDERER_GRID_SUBDEVISION_MINOR_VISIBLE = "Renderer.Grid.Subdevision.Minor.Visible";
    public static final String RENDERER_GRID_SUBDEVISION_MAJOR_X       = "Renderer.Grid.Subdevision.Major.X";
    public static final String RENDERER_GRID_SUBDEVISION_MAJOR_Y       = "Renderer.Grid.Subdevision.Major.Y";
    public static final String RENDERER_GRID_SUBDEVISION_MINOR_X       = "Renderer.Grid.Subdevision.Minor.X";
    public static final String RENDERER_GRID_SUBDEVISION_MINOR_Y       = "Renderer.Grid.Subdevision.Minor.Y";
            
    // Application properties file: Game Engine.
    
    public static final String GAME_ENGINE_RESOURCE_PATH         = "GameEngine.Resource.Path";
    public static final String GAME_ENGINE_DEBUG_OVERLAY_VISIBLE = "GameEngine.DebugOverlay.Visible";
    public static final String GAME_ENGINE_LOGGING_ENABLED       = "GameEngine.Logging.Enabled";
    
    // Application properties File: Game Loop.
                                                               
    public static final String GAME_LOOP_FPS_TARGET_ENABLED = "GameLoop.FrameRate.Target.Enabled";
    public static final String GAME_LOOP_FPS_TARGET         = "GameLoop.FrameRate.Target";
    public static final String GAME_LOOP_DELAY_FIXED        = "GameLoop.Delay.Fixed";
    public static final String GAME_LOOP_DELAY_MIN          = "GameLoop.Delay.Min";
    
    // World to screen projection parameters.
    
    public static final double WINDOW_UNIT   =  1.0;
    public static final double WINDOW_MIN_X  = -WINDOW_UNIT;
    public static final double WINDOW_MIN_Y  = -WINDOW_UNIT;
    public static final double WINDOW_MAX_X  =  WINDOW_UNIT;
    public static final double WINDOW_MAX_Y  =  WINDOW_UNIT;
    public static final double WINDOW_WIDTH  =  WINDOW_MAX_X - WINDOW_MIN_X;
    public static final double WINDOW_HEIGHT =  WINDOW_MAX_Y - WINDOW_MIN_Y;
    public static final double WINDOW_ZOOM   =  1.0;
    
    // Screen coordinate parameters.
    
    public static final double SCREEN_ASPECT_X     = 1.0;
    public static final double SCREEN_ASPECT_Y     = 1.0;
    public static final double SCREEN_ASPECT_RATIO = SCREEN_ASPECT_X / SCREEN_ASPECT_Y;
    
    // Screen object layers. Drawing is performed from largest to smallest. i.e. z increases away from the view point.
    
    public static final double LAYER_HUD         = 0.0;                       // HUD = Heads Up Display. e.g. Score, health, lives, stats, and other info.  
    public static final double LAYER_FORGROUND   = 0.1;                       // Trees, rocks, fences, etc.
    public static final double LAYER_CONTAINERS  = LAYER_FORGROUND + 1.0;     // Houses, boxes, clouds, etc.
    public static final double LAYER_PROJECTILES = LAYER_CONTAINERS + 1.0;    // Bullets, arrows, plasma bolts, sticks, stones, etc.       
    public static final double LAYER_PLAYER      = LAYER_PROJECTILES + 1.0;   // Human players.    
    public static final double LAYER_AI          = LAYER_PLAYER + 1.0;        // AI players.
    public static final double LAYER_MIDGROUND   = LAYER_AI + 1.0;            // More trees, rocks, and fences, etc. 
    public static final double LAYER_BACKGROUND  = LAYER_MIDGROUND + 1.0;     // Sky, mountains, etc.
    
    // Systems ID's. ( Must be unique, positive integers, greater than or equal to zero ).
    
    private static int systemIndex = 0;
    
    public final static Integer SYSTEM_EXAMPLE        = systemIndex++;
    public final static Integer SYSTEM_PHYSICS_ENGINE = systemIndex++;
    public final static Integer SYSTEM_COLLIDER       = systemIndex++;
    public final static Integer SYSTEM_RENDERER       = systemIndex++;
    
    // Component ID's. ( Must be unique, positive integers, greater than or equal to zero ).
    
    private static int componentIndex = 0;
    
    public final static Integer COMPONENT_TRANSFORM       = componentIndex++;
    public final static Integer COMPONENT_PHYSICS         = componentIndex++;
    public final static Integer COMPONENT_GEOMETRY        = componentIndex++;
    public final static Integer COMPONENT_RESOURCE_SPRITE = componentIndex++;
    public final static Integer COMPONENT_PROJECTION_2D   = componentIndex++;
    
    // Entities ID's. ( Must be unique, positive integers, greater than or equal to zero ).
    
    private static int entityIndex = 0;
    
    public final static Integer ENTITY_EXAMPLE    = entityIndex++;
    public final static Integer ENTITY_BALL_RED   = entityIndex++;
    public final static Integer ENTITY_BALL_GREEN = entityIndex++;
    public final static Integer ENTITY_BALL_BLUE  = entityIndex++;
    
    // Entity family ID's. ( Must be unique, positive integers, greater than or equal to zero ).
    
    private static int familyIndex = 0;
    
    public final static Integer FAMILY_PROP = familyIndex++;
    public final static Integer FAMILY_BALL = familyIndex++;
    
        
    // Resource ID's. ( Must be unique, positive integers, greater than or equal to zero ).
    
    private static int resourceIndex = 0;
    
    public final static Integer RESOURCE_STAR_WHITE_250  = resourceIndex++;
    public final static Integer RESOURCE_STAR_RED_250    = resourceIndex++;
    public final static Integer RESOURCE_STAR_GREEN_250  = resourceIndex++;
    public final static Integer RESOURCE_STAR_BLUE_250   = resourceIndex++;
    
    public final static Integer RESOURCE_BALL_BLUE_64    = resourceIndex++;
    public final static Integer RESOURCE_BALL_BLUE_128   = resourceIndex++;
    public final static Integer RESOURCE_BALL_BLUE_256   = resourceIndex++;
    
    public final static Integer RESOURCE_BALL_GREEN_64   = resourceIndex++;
    public final static Integer RESOURCE_BALL_GREEN_128  = resourceIndex++;
    public final static Integer RESOURCE_BALL_GREEN_256  = resourceIndex++;
    
    public final static Integer RESOURCE_BALL_RED_64     = resourceIndex++;
    public final static Integer RESOURCE_BALL_RED_128    = resourceIndex++;
    public final static Integer RESOURCE_BALL_RED_256    = resourceIndex++;
    
    public final static Integer RESOURCE_BALL_SHADOW_64  = resourceIndex++;
    public final static Integer RESOURCE_BALL_SHADOW_128 = resourceIndex++;
    public final static Integer RESOURCE_BALL_SHADOW_256 = resourceIndex++;
    
    
    // Debug.
    
    public final static double DEBUG_MOVE_DISPLACEMENT = 1.0 / 12.0;
    public final static double DEBUG_ACCELERATION      = 0.002 / 600.0;
    public final static double DEBUG_VELOCITY          = 0.5 / 600.0;
}





