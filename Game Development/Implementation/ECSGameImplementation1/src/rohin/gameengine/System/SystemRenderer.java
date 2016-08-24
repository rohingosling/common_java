package rohin.gameengine.System;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;

import rohin.gameengine.*;
import rohin.gameengine.Application.Application;
import rohin.gameengine.Application.Constants;
import rohin.gameengine.Component.ComponentGeometry;
import rohin.gameengine.Component.ComponentProjection2D;
import rohin.gameengine.Component.ComponentResourceSprite;
import rohin.gameengine.Component.ComponentShape;
import rohin.gameengine.Component.ComponentShapeCircle;
import rohin.gameengine.Component.ComponentShapeLine;
import rohin.gameengine.Component.ComponentShapePoint;
import rohin.gameengine.Component.ComponentShapeBox;
import rohin.gameengine.Component.ComponentShapeTriangle;
import rohin.gameengine.Component.ComponentTransform;
import rohin.gameengine.GameEngine.GameEngineTest;

public class SystemRenderer extends ECSSystem
{
    // @formatter:off
    
    // Constants - Array Indices.
    
    static final int X0    = 0;                         // Generic vector element index, 0.
    static final int X1    = 1;                         // Generic vector element index, 1.
    static final int X2    = 2;                         // Generic vector element index, 2.
    static final int X3    = 3;                         // Generic vector element index, 3.        
    static final int X     = X0;                        // 2D vector index for vector element, X ordinate. 
    static final int Y     = X1;                        // 2D vector index for vector element, Y ordinate.
    static final int Z     = X2;                        // 2D vector index for vector element, Z ordinate.
    static final int PITCH = X0;                        // 2D vector index for vector element, rotation about the x axis. 
    static final int YAW   = X1;                        // 2D vector index for vector element, rotation about the y axis.
    static final int ROLL  = X2;                        // 2D vector index for vector element, rotation about the z axis.
    static final int XMIN  = X0;                        // 4D vector index for vector element, minimum X boundary.
    static final int YMIN  = X1;                        // 4D vector index for vector element, minimum Y boundary.
    static final int XMAX  = X2;                        // 4D vector index for vector element, maximum X boundary.
    static final int YMAX  = X3;                        // 4D vector index for vector element, maximum Y boundary.
    static final int TRANSLATION_HISTORY_STEP = 4;      // Number of frames between each translation history entry.
    
    // Constants - Geometry.
    
    static final double M_GEOMETRY_CROSSHAIR_SIZE = 6.0;    // Geometry guide, cross hair size in pixels.    
    
    // Enumerations
    
    public enum GeometryType                            // Used for implementing shape specific rendering logic.
    {
        SHAPE,
        POINT,
        LINE,
        CIRCLE,
        RECTANGLE,
        TRIANGLE
    };
    
    // Components used by this system.
    
    ComponentTransform          transform;              // Mathematical transformation. Translation, rotation, scale, etc.
    ComponentGeometry           geometry;               // Wire frame geometry. Used mostly for physics and collision detection. 
    ComponentResourceSprite     sprite;                 // Sprite data. e.g. images, sprite parameters, etc.
    ComponentProjection2D       projection;             // World-space to screen-space projection.
    
    // Application and game engine objects.
    
    private Application         application;            // Parent application.
    private GameEngineTest      engine;                 // Parent game engine.
    
    // Fields - Graphics API's
    
    private Graphics2D          graphicsAPI;            // Standard Java graphics API.
    private Graphics2DExtended  extendedGraphicsAPI;    // Extended graphics API.     
    
    // Fields - Application administration.
    
    private Boolean             loggingEnabled;         // true = enable logging. false = disable logging.
    private ConsoleLogger       logger;                 // Console logging object.
    
    // Projection variables.
    
    private double[]            window;                         // Window boundaries for the current projection.
    private double[]            origin;                         // Geometric origin of the current projection.
    private double[]            scale;                          // Scaling vector.
    private double[]            viewPort;                       // View port vector.
    private double              aspectRatio;                    // Aspect ratio.
    private double              layer;                          // Projection layer. Used as acceleration z-buffer.
    private double[]            viewPortMin;                    // View ports minimum boundary vector.                                          
    private double[]            viewPortMax;                    // View ports maximum boundary vector.
    private double[]            screenSize;                     // Screen size. Usually this will be set equal to the component's view port size.
    private int                 frameCounterTranslationHistory; // Used for counting frames.
    private int                 frameCountTranslationHistory;   // Used for reseting frame counter.
    
    // @formatter:off
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public SystemRenderer ( ECSEngine owner )
    {
        // Initialize ECS object parameters.
        
        this.id          = Constants.SYSTEM_RENDERER;
        this.name        = "SYSTEM_RENDERER";        
        this.owner       = owner;
        
        // Initialize components.
        
        this.transform  = new ComponentTransform ();
        this.geometry   = new ComponentGeometry ();        
        this.sprite     = new ComponentResourceSprite ();
        this.projection = new ComponentProjection2D ();
                
        // Initialize application and game engine parameters.
        
        this.application = (Application) owner.getOwner ();        
        this.engine      = (GameEngineTest) this.owner;
        
        // Initialize graphics API's.
        
        this.graphicsAPI         = application.getGraphicsAPI ();
        this.extendedGraphicsAPI = new Graphics2DExtended ( this.graphicsAPI );
        
        // Initialize system management parameters.
        
        this.loggingEnabled = false;
        this.logger         = new ConsoleLogger ( this, this.loggingEnabled );
        
        // Projection variables.
        
        this.window       = null;
        this.origin       = null;
        this.scale        = null;
        this.viewPort     = null;
        this.aspectRatio  = 0.0;
        this.layer        = 0;
        this.viewPortMin  = null;                                          
        this.viewPortMax  = null;
        this.screenSize   = null;
        
        // Frame Counters
        
        this.frameCounterTranslationHistory = 0;
        this.frameCountTranslationHistory   = TRANSLATION_HISTORY_STEP;
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Description:
    // - Override implementation of ECSSystem.Update.
    //
    // Arguments:
    // 
    // - t
    //   Game loop tick time. i.e Current game loop lap time.
    //
    // Return Value:
    //
    // - N/A
    //
    // Preconditions:
    //
    // - The game engine object must have already been initialized to the game engine we would like to reference.
    //   This will usually be done via the constructor, when an instance of SystemRenderer is created.
    //
    // Postconditions:
    //
    // - All renderable and enabled entities have been rendered to the current graphics context (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void update ( long t )
    {
        try
        {  
            // Clear the screen.
            
            extendedGraphicsAPI.clearScreen ( Color.BLACK );
            
            // Draw grid.
            
            this.screenSize = new double[] { this.application.getScreenWidth (), this.application.getScreenHeight () }; 
            renderGrid ();
            
            // Iterate through all entities, filtering by those that contain the specific set of components, that this system is intended to work with.
            
            for ( ECSEntity entity : engine.getEntities ().values () )
            {   
                if ( entity.hasComponents ( transform, geometry, sprite, projection ) )
                {   
                    // Render entity geometry.
                    
                    renderGeometry ( entity );
                    
                    // Update frame counters.
                    
                    if ( this.frameCounterTranslationHistory >= this.frameCountTranslationHistory ) this.frameCounterTranslationHistory = 0;                    
                    else ++this.frameCounterTranslationHistory;
                    
                    // Console logger.
                    
                    logger.log ();
                }
            } 
            
            // Swap the double buffers.
                    
            engine.swapBuffer ();
        }
        catch ( Exception e )
        {   
            TextFormat.printFormattedException ( e, true );
        }
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // renderGeometry 
    //
    // Description:
    //
    // - Render entity geometry.
    //
    // Arguments:
    //
    // - entity
    //   The ECS entity that we would like to render.
    //
    // Return Value:
    //
    // - N/A
    //
    // Preconditions:
    //
    // - Component objects must already be initialized.
    //
    // Postconditions:
    //
    // - The selected renderable objects have been rendered on the current graphics context (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void renderGeometry ( ECSEntity entity )
    {
        // Retrieve the entity's components.
        
        this.transform      = ( ComponentTransform )      entity.getComponent ( Constants.COMPONENT_TRANSFORM );
        this.geometry       = ( ComponentGeometry )       entity.getComponent ( Constants.COMPONENT_GEOMETRY );
        this.sprite         = ( ComponentResourceSprite ) entity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
        this.projection     = ( ComponentProjection2D )   entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );
        
        // Retrieve projection and rendering data.
        
        this.window         = projection.window;                                                            // Get the window boundaries for the current projection.
        this.origin         = projection.origin;                                                            // Get the geometric origin of the current projection.
        this.scale          = projection.scale;                                                             // Get the scaling vector.
        this.viewPort       = projection.viewPort;                                                          // Get the view port vector.        
        this.layer          = projection.layer;                                                             // Get the projection layer. Used as acceleration z-buffer.        
        this.viewPortMin    = new double [] { viewPort [ XMIN ], viewPort [ YMIN ] };                       // Get the view ports minimum boundary vector.                                          
        this.viewPortMax    = new double [] { viewPort [ XMAX ], viewPort [ YMAX ] };                       // Get the view ports maximum boundary vector.
        this.screenSize     = GMath.subVector ( viewPortMax, viewPortMin );                                 // Set screen size equal to the component's view port size.
        
        // Calculate aspect ratio.
        
        this.aspectRatio = projection.aspect[Y] / projection.aspect[X];
                
        // Retrieve the entity's geometry.
        
        ComponentShape shape = geometry.shapes.get ( 0 );                                   // Get first shape in the entity's geometry list.
        
        // Identify the type of geometry we are working with.
        
        GeometryType geometryType = GeometryType.SHAPE;                                     // Set the geometry to acceleration generic shape by default.         
        
        if ( shape.getClass () == ComponentShapePoint.class )     geometryType = GeometryType.POINT;
        if ( shape.getClass () == ComponentShapeLine.class )      geometryType = GeometryType.LINE;
        if ( shape.getClass () == ComponentShapeCircle.class )    geometryType = GeometryType.CIRCLE;
        if ( shape.getClass () == ComponentShapeBox.class )       geometryType = GeometryType.RECTANGLE;
        if ( shape.getClass () == ComponentShapeTriangle.class )  geometryType = GeometryType.TRIANGLE;
        
        // Render translation history
        
        renderTranslationHistory ();
        
        // Save translation history.
        
        if ( this.frameCounterTranslationHistory <= 0 )
        {
            this.transform.saveTranslation ( new Vector2D ( this.transform.translation ) );
        }
                        
        // Render geometry.        
        
        switch ( geometryType )
        {
            case SHAPE:                                                             break;
            case POINT:                                                             break;
            case LINE:                                                              break;
            case CIRCLE:    renderGeometryCircle ( (ComponentShapeCircle) shape );  break;
            case RECTANGLE:                                                         break;
            case TRIANGLE:                                                          break;
        }   
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // renderGeometryCircle
    //
    // Description:
    //
    // - Render an instance of circle entity geometry.
    //
    // Arguments:
    //
    // - circle
    //   ECS circle shape components.
    //
    // Return Value:
    //
    // - N/A
    // 
    // Preconditions:
    //
    // - Component objects must already be initialized.
    // - Projection variables must already have been initialized during acceleration previous call to renderGeometry.
    // - Array index constants should be properly configured.
    //
    // Postconditions:
    // 
    // - An instance of circle geometry has been rendered to the current graphics context (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void renderGeometryCircle ( ComponentShapeCircle circle )
    {
        // Rendering options.
        
        Boolean rotationEnabled = this.application.isRendererEntityRotationEnabled ();
        Boolean scaleEnabled    = this.application.isRendererEntityScakeEnabled ();
        
        // Retrieve transformation parameters
                
        Vector2D o  = new Vector2D ( transform.origin      );   // o = origin (Center of rotation).
        Vector2D d  = new Vector2D ( transform.translation );   // d = displacement (linear translation).
        double   a  = transform.rotation[2];                    // a = angular displacement. 
        Vector2D s  = new Vector2D ( transform.scale       );   // s = scale.
        Vector2D p  = new Vector2D ( 0.0, 0.0 );                // Calculated position.
        double   ro = o.magnitude ();                           // radius of displacement, between geometric center and translation.
        
        // Retrieve circle parameters.
        
        double r = circle.r;                            // Retrieve circle radius.
        
        // Initialize working variables.
        
        double px    = 0.0;
        double py    = 0.0;
        double ox    = o.getX ();
        double oy    = o.getY ();
        double sx    = s.getX ();
        double sy    = s.getY ();
        
        // Apply rotation.
        
        if ( rotationEnabled )
        {
            px = ox + ro * Math.cos ( a );
            py = ox + ro * Math.sin ( a );
            
            p.setVector ( px,  py );
        }
                
        // Apply entity scale, if required. The default will usually be set to 1.0 (i.e. no scaling).
        
        if ( scaleEnabled )
        {
            r *= ( sx + sy ) / 2.0;                 // Set the radius of circle geometry to be the average of the X and Y scaling factors..
        }
        
        // Apply translation. (Linear displacement).
        
        p = p.add ( d );
        
        // Scale entity geometry and location, in proportion to the screen size. And invert vertical axis to conform to screen coordinates. 
        
        p.setX ( p.getX() *  screenSize[X] / 2.0 );
        p.setY ( p.getY() * -screenSize[Y] / 2.0 );
                
        r = ( screenSize[X] > screenSize[Y] ) ? r * screenSize[Y] : r * screenSize[X];
        
        // Apply aspect ratio.
        
        if ( projection.aspect[X] >= projection.aspect[Y] ) p.setX ( p.getX () * this.aspectRatio );        
        if ( projection.aspect[X] <  projection.aspect[Y] ) p.setY ( p.getY () / this.aspectRatio );        
        
        // Adjust the entity's origin relative to the view port.
        
        p.setVector ( p.add ( new Vector2D ( origin[X], origin[Y] ) ) );
        
        // Initialize drawing colors.
        
        Color geometryColor = geometry.color;        
        
        // Draw the entity's geometry.
        
        graphicsAPI.setColor ( geometryColor );
        Shape graphics2Dcircle = new Ellipse2D.Double ( p.getX () - r/2, p.getY () - r/2, r, r );
        graphicsAPI.draw ( graphics2Dcircle );
        
        // Draw guide geometry. i.e. Cross hairs, geometry data, etc.
        
        double crossHairRadius = M_GEOMETRY_CROSSHAIR_SIZE;
        graphicsAPI.setColor ( geometryColor );
        extendedGraphicsAPI.drawCrossHair ( p.getX (), p.getY (), crossHairRadius, geometryColor );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // renderTranslationHistory
    //
    // Description:
    //
    // - Render the entities translation history.
    //
    // Arguments:
    //
    // - None
    //
    // Return Value:
    //
    // - None.
    // 
    // Preconditions:
    //
    // - None.
    //
    // Postconditions:
    // 
    // - The entities translation history.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void renderTranslationHistory ()
    {           
        if ( this.application.isRendererTranslationHistoryVisible () )
        {         
            Color color = Color.WHITE;
            int   x     = 0;
            int   y     = 0;            
            
            for ( Vector2D p : this.transform.translationHistory )
            {
                Vector2D v = new Vector2D ( p );
                
                // Scale entity geometry and location, in proportion to the screen size. And invert vertical axis to conform to screen coordinates. 
                
                v.setX ( v.getX() *  screenSize[X] / 2.0 );
                v.setY ( v.getY() * -screenSize[Y] / 2.0 );
                
                // Apply aspect ratio.
                
                if ( projection.aspect[X] >= projection.aspect[Y] ) v.setX ( v.getX () * this.aspectRatio );        
                if ( projection.aspect[X] <  projection.aspect[Y] ) v.setY ( v.getY () / this.aspectRatio );        
                
                // Adjust the entity's origin relative to the view port.
                
                v.setVector ( v.add ( new Vector2D ( origin[X], origin[Y] ) ) );
                
                // Draw translation key.
                
                x = (int) v.getX ();
                y = (int) v.getY ();
                
                graphicsAPI.setColor ( color );
                graphicsAPI.drawLine ( x, y, x, y);
            }             
        }       
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // renderGrid
    //
    // Description:
    //
    // - Render a grid.
    //
    // Arguments:
    //
    // - None
    //
    // Return Value:
    //
    // - None.
    // 
    // Preconditions:
    //
    // - None.
    //
    // Postconditions:
    // 
    // - A grid has been drawn to the active graphics contex (screen).
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void renderGrid ()
    {
        if ( this.application.isRendererGridVisible () )
        {
            Color   axisColor    = Color.DARK_GRAY;
            Color   majorColor   = Color.DARK_GRAY;        
            Color   minorColor   = Color.DARK_GRAY;
            double  majorX       = this.application.getRendererGridSubdevisionMajorX ();
            double  majorY       = this.application.getRendererGridSubdevisionMajorY ();
            double  minorX       = this.application.getRendererGridSubdevisionMinorX ();
            double  minorY       = this.application.getRendererGridSubdevisionMinorY ();
            Boolean visibleAxisX = this.application.isRendererGridAxisXVisible ();
            Boolean visibleAxisY = this.application.isRendererGridAxisYVisible ();
            Boolean visibleMajor = this.application.isRendererGridSubdevisionMajorVisible ();
            Boolean visibleMinor = this.application.isRendererGridSubdevisionMinorVisible ();
            
            extendedGraphicsAPI.drawGrid
            (
                0, 0, screenSize[X], screenSize[Y], 
                majorX, majorY, minorX, minorY, 
                visibleAxisX, visibleAxisY, visibleMajor, visibleMinor, 
                axisColor, majorColor, minorColor
            );
        }
    }
    
    // @formatter:off
}














