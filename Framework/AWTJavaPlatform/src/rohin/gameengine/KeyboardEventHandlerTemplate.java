
package rohin.gameengine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

///////////////////////////////////////////////////////////////////////////////
/**
 * KeyboardEventHandlerTemplate:
 * <p>
 * Keyboard event handler.
 * 
 * @author Rohin Gosling
 * @version 1.0
 * @since 2014-04-18
 */
///////////////////////////////////////////////////////////////////////////////

public class KeyboardEventHandlerTemplate extends KeyAdapter
{
    //@formatter:off
    
    // /////////////////////////////////////////////////////////////////////////
    //
    // Constants
    //
    // /////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unused")
    private static final String M_INCOMPLETE_SWITCH = "incomplete-switch";
       

    // /////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // /////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////
    
    // Accessors
    
    // Mutators

    // -------------------------------------------------------------------------
    /**
     * Constructor 1
     */
    // -------------------------------------------------------------------------

    public KeyboardEventHandlerTemplate ()
    {
        // Call parent constructor.

        super ();
    }
    

    // -------------------------------------------------------------------------
    /**
     * Overridden KeyAdapter.KeyPress event.
     * <p>
     * Handles a Key press/down event.
     */
    // -------------------------------------------------------------------------

    //@ SuppressWarnings ( M_INCOMPLETE_SWITCH )
    @ Override
    public void keyPressed ( KeyEvent e )
    {
        switch ( e.getKeyCode () )
        {
            case KeyEvent.VK_LEFT:
                break;
    
            case KeyEvent.VK_RIGHT:
                break;
    
            case KeyEvent.VK_UP:
                break;
    
            case KeyEvent.VK_DOWN:
                break;
    
            case KeyEvent.VK_SPACE:
                break;
        }
    }

    // -------------------------------------------------------------------------
    /**
     * Overridden KeyAdapter.keyReleased event.
     * <p>
     * Handles a Key release/up event.
     */
    // -------------------------------------------------------------------------

    @ Override
    public void keyReleased ( KeyEvent e )
    {
        switch ( e.getKeyCode () )
        {
            case KeyEvent.VK_LEFT:
                break;
    
            case KeyEvent.VK_RIGHT:
                break;
    
            case KeyEvent.VK_UP:
                break;
    
            case KeyEvent.VK_DOWN:
                break;
    
            case KeyEvent.VK_SPACE:
                break;
        }
    }

    // -------------------------------------------------------------------------
    /**
     * Overriden KeyAdapter.keyTyped event.
     * <p>
     * Handles a Key typed (press, release) event.
     */
    // -------------------------------------------------------------------------

    //@ SuppressWarnings ( M_INCOMPLETE_SWITCH )
    @ Override
    public void keyTyped ( KeyEvent e )
    {
        switch ( e.getKeyChar () )
        {
            case KeyEvent.VK_ESCAPE:
                break;
    
            case KeyEvent.VK_SPACE:
                break;
            
            case KeyEvent.VK_PAUSE:
                break;
        }
    }
}
