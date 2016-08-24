
package rohin.gameengine.Application;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import rohin.gameengine.CommandManager;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.ICommand;
import rohin.gameengine.Command.*;

///////////////////////////////////////////////////////////////////////////////
/**
 * KeyboardEventHandlerTemplate:
 * <translate>
 * Keyboard event handler.
 * 
 * @author Rohin Gosling
 * @version 1.0
 * @since 2014-04-18
 */
///////////////////////////////////////////////////////////////////////////////

public class KeyboardEventHandler extends KeyAdapter
{
    //@formatter:off
    
    // /////////////////////////////////////////////////////////////////////////
    //
    // CONSTANTS
    //
    // /////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unused")
    private static final String M_INCOMPLETE_SWITCH = "incomplete-switch";
    
    // /////////////////////////////////////////////////////////////////////////
    //
    // KEY DOWN FLAGS. Used to prevent undesired key press repeat. 
    //
    // /////////////////////////////////////////////////////////////////////////
    
    private Boolean vkSpaceDepressed = false;
    private Boolean vkUpDepressed    = false;
    private Boolean vkDownDepressed  = false;
    private Boolean vkLeftDepressed  = false;
    private Boolean vkRightDepressed = false;
    private Boolean vkQDepressed     = false;
    private Boolean vkWDepressed     = false;
    private Boolean vkEDepressed     = false;

    // /////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    // /////////////////////////////////////////////////////////////////////////
        
    Application application;

    // /////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////
    
    // Accessors    

    public Application getApplication () { return application;         }
    
    // Mutators    

    public void setApplication ( Application application ) { this.application = application; }

    // -------------------------------------------------------------------------
    /**
     * Constructor 1
     */
    // -------------------------------------------------------------------------

    public KeyboardEventHandler ()
    {   
        super ();       // Call base class constructor.
    }
    
    //@formatter:off    

    // -------------------------------------------------------------------------
    /**
     * Overridden KeyAdapter.KeyPress event.
     * <translate>
     * Handles acceleration Key press/down event.
     */
    // -------------------------------------------------------------------------

    //@ SuppressWarnings ( M_INCOMPLETE_SWITCH )
    @ Override
    public void keyPressed ( KeyEvent e )
    {
        switch ( e.getKeyCode () )
        {
            case KeyEvent.VK_LEFT:
                if ( !vkLeftDepressed )
                {
                    PostCommand ( new CommandPlayerMoveLeft ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                    vkLeftDepressed = true;
                }
                break;
    
            case KeyEvent.VK_RIGHT:
                if ( !vkRightDepressed )
                {
                    PostCommand ( new CommandPlayerMoveRight ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                    vkRightDepressed = true;
                }
                break;
    
            case KeyEvent.VK_UP:
                if ( !vkUpDepressed )
                {
                    PostCommand ( new CommandPlayerMoveUp ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                    vkUpDepressed = true;
                }
                break;
    
            case KeyEvent.VK_DOWN:
                if ( !vkDownDepressed )
                {
                    PostCommand ( new CommandPlayerMoveDown ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                    vkDownDepressed = true;
                }
                break;
    
            case KeyEvent.VK_SPACE:
                if ( !vkSpaceDepressed )
                {
                    PostCommand ( new CommandPlayerCrouch ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                    vkSpaceDepressed = true;
                }
                break;
                
            case KeyEvent.VK_Q:
                if ( !vkQDepressed )
                {
                    PostCommand ( new CommandPlayerMoveLeft ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                    vkQDepressed = true;
                }
                break;
    
            case KeyEvent.VK_E:
                if ( !vkEDepressed )
                {
                    PostCommand ( new CommandPlayerMoveRight ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                    vkEDepressed = true;
                }
                break;
                
            case KeyEvent.VK_W:
                if ( !vkWDepressed )
                {
                    PostCommand ( new CommandPlayerCrouch ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                    vkWDepressed = true;
                }
                break;
        }
    }

    // -------------------------------------------------------------------------
    /**
     * Overridden KeyAdapter.keyReleased event.
     * <translate>
     * Handles acceleration Key release/up event.
     */
    // -------------------------------------------------------------------------

    @ Override
    public void keyReleased ( KeyEvent e )
    {
        switch ( e.getKeyCode () )
        {
            case KeyEvent.VK_LEFT:
                PostCommand ( new CommandPlayerBreakLeft ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                vkLeftDepressed = false;
                break;
    
            case KeyEvent.VK_RIGHT:
                PostCommand ( new CommandPlayerBreakRight ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                vkRightDepressed = false;
                break;
    
            case KeyEvent.VK_UP:
                PostCommand ( new CommandPlayerBreakUp ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                vkUpDepressed = false;
                break;
    
            case KeyEvent.VK_DOWN:
                PostCommand ( new CommandPlayerBreakDown ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                vkDownDepressed = false;
                break;
    
            case KeyEvent.VK_SPACE:
                PostCommand ( new CommandPlayerJump ( this.application.getGameTest (), Constants.ENTITY_BALL_BLUE ) );
                vkSpaceDepressed = false;
                break;
            
            case KeyEvent.VK_Q:
                PostCommand ( new CommandPlayerBreak ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                vkQDepressed = false;
                break;
    
            case KeyEvent.VK_E:
                PostCommand ( new CommandPlayerBreak ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                vkEDepressed = false;
                break;
            
            case KeyEvent.VK_W:
                PostCommand ( new CommandPlayerJump ( this.application.getGameTest (), Constants.ENTITY_BALL_RED ) );
                vkWDepressed = false;
                break;
        }
    }

    // -------------------------------------------------------------------------
    /**
     * Overridden KeyAdapter.keyTyped event.
     * <translate>
     * Handles acceleration Key typed (press, release) event.
     */
    // -------------------------------------------------------------------------

    //@ SuppressWarnings ( M_INCOMPLETE_SWITCH )
    @ Override
    public void keyTyped ( KeyEvent e )
    {
        switch ( e.getKeyChar () )
        {
            case KeyEvent.VK_ESCAPE:               
                PostCommand ( new CommandGameLoopExit ( this.application.getGameTest () ) );
                break;
    
            case KeyEvent.VK_SPACE:
                break;
            
            case KeyEvent.VK_PAUSE:
                break;
        }
    }
    
    // -------------------------------------------------------------------------
    /**
     * Overriden KeyAdapter.keyTyped event.
     * <translate>
     * Handles acceleration Key typed (press, release) event.
     */
    // -------------------------------------------------------------------------   
    
    private void PostCommand ( ICommand command )
    {
        ECSEngine      gameEngine     = this.application.getGameTest ();
        CommandManager commandInvoker = gameEngine.getCommandManager (); 
        commandInvoker.postCommand ( command );
    }
}




