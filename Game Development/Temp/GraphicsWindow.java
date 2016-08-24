
package rohin.gameengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

// /////////////////////////////////////////////////////////////////////////////
//
// GameGraphicsWindow:
//
// The GameGraphicsWindow class, implements an extended JFrame application
// window, with a double buffered AWT canvas control serving as the primary
// drawing surface.
//
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
//
// /////////////////////////////////////////////////////////////////////////////

@ SuppressWarnings ( "serial" )
public class GraphicsWindow extends JFrame
{
    // @formatter:off

    // /////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    // /////////////////////////////////////////////////////////////////////////

    private Canvas screen;              // AWT canvas control. Primary graphics drawing surface.
    private int    screenBufferCount;   // Number of graphics buffers. 2 for

    // standard double buffering.

    // /////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////

    // Accessors
    
    public Canvas         GetScreen       () { return this.screen;                      }
    public BufferStrategy GetScreenBuffer () { return this.screen.getBufferStrategy (); }
    
    // Mutators
    
    //@formatter:off
    
    ////////////////////////////////////////////////////////////////////////////
    // 
    // METHODS
    //
    ////////////////////////////////////////////////////////////////////////////

    // -------------------------------------------------------------------------    
    // Constructor 1
    // 
    // Arguments:
    //
    // - width
    //   Screen width.
    //
    // - height
    //   Screen height.
    //
    // -------------------------------------------------------------------------

    public GraphicsWindow ( int width, int height )
    {
        // Initialize low level graphics window parameters.
        //
        // 1. We will be using a standard double buffer strategy by default.

        this.screenBufferCount = 2;                                             

        
        // Get the content pane from the JFrame, configure it, and add the screen Canvas to it.
        //
        // 1. Set the dimensions.
        // 2. No layout, because we will do our own layout management.
        // 3. Set the content pane color.

        getContentPane ().setPreferredSize ( new Dimension ( width, height ) );
        getContentPane ().setLayout        ( null );
        getContentPane ().setBackground    ( Color.BLACK );

        
        // Initialize a new AWT Canvas to be used as a graphics screen.
        //
        // 1. Set default screen dimensions.
        // 2. Disable repainting. We will manually perform the paint operation during double buffering.
        // 3. Add the graphics canvas to the JFram content pane.

        this.screen = new Canvas ();
        
        this.screen.setBounds        ( 0, 0, width, height );  
        this.screen.setIgnoreRepaint ( true );
        getContentPane ().add        ( this.screen );

        
        // Initialize our JFrame based graphics window.
        //
        // 1. Disable resizing of the window.
        // 2. Force the application window to be resized to fit the game engine drawing area.
        // 3. Set default title.
        // 4. Show the application window.
        // 5. Position the window in the center of the parent control, the desktop screen in this case.

        setResizable          ( false );                        
        pack                  ();                                               
        setTitle              ( "Graphics Application" );
        setVisible            ( true );
        setLocationRelativeTo ( null );

        // Initialize the display buffer strategy, to double buffering.

        this.screen.createBufferStrategy ( this.screenBufferCount );

        // Add  window listener.
        //
        // - Add a window listener to handle window closes, so that we can 
        //   shut down the main application thread gracefully.
        //
        // - In the case where a user happens to close the application using 
        //   the window close button, rather than our application and game 
        //   loop control events.

        addWindowListener
        (
            new WindowAdapter ()
            {
                public void windowClosing ( WindowEvent e )
                {
                    System.exit ( 0 );
                }
            }
        );
    }

    // @formatter:on
}
