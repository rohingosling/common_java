package rohin.gameengine;

import java.util.Collection;
import java.util.LinkedList;

//------------------------------------------------------------------------------
/**
* <b>Description:</b><br>
* <ul>
* <b>Event</b> is a generic event class, managed by instances of the 
* <b>EventManager</b> class.
* </ul>
* <p>
* <b>Features:</b><br>
* <ul>
* <li>An event fileName string, which is used as a unique event identifier for the 
* event.<br>
* <li>A payload object, which can be cast to accommodate any kind of data a 
* consumer wishes to carry on the event.<br>
* <li>A time stamp, used for sequencing events.<br>
* </ul>
* <p>
* <b>Note:</b><br>
* <ul> 
* It is the responsibility of the EventManager class, to ensure that event 
* names are unique.
* </ul>
*  
* @author      Rohin Gosling
* @version     1.0
* @since       2001-01-10
*/
//------------------------------------------------------------------------------

public class Event 
{
    //@formatter:off
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    private String                      name;           // Unique identifier for this event.
    private Object                      payload;        // Generic pay load object.
    private long                        time;           // The time at which the event was posted.
    private Collection <IEventListener> listenerList;   // A list of all listeners registered with this event.
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTORS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Constructor 1
     * </ul>
     * @param fileName
     * <br>
     *        As string value that serves as the unique identifier of this event.
     */
    //--------------------------------------------------------------------------
    
    public Event ( String name )
    {
        reset ( name, null );
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Constructor 2
     * </ul>
     * @param fileName
     * <br>
     *      As string value that serves as the unique identifier of this event.
     * <br><br>
     * @param payload
     * <br>
     *      A generic payload object, that can be used to carry any kind of data
     *      the consumer wishes to ship with the event.
     * <br>       
     */
    //--------------------------------------------------------------------------
    
    public Event ( String name, Object payload )
    {
        reset ( name, payload );
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    // Accessors
    
    public String getName    () { return this.name;    }
    public Object getPayload () { return this.payload; }
    public long   getTime    () { return this.time;    }
    
    // Mutators
    
    public void setName    ( String name    ) { this.name    = name;    }
    public void setPayload ( Object payload ) { this.payload = payload; }
    public void setTime    ( long time      ) { this.time    = time;    }
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // METHODS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Initialize or reset the object.
     * </ul>
     * @param fileName
     * <br>
     *      As string value that serves as the unique identifier of this event.
     * <br><br>
     * @param payload
     * <br>
     *      A generic payload object, that can be used to carry any kind of data
     *      the consumer wishes to ship with the event.
     * <br>  
     */
    //--------------------------------------------------------------------------
    
    public void reset ( String name, Object payload )
    {
        this.name         = name;
        this.payload      = payload;
        this.time         = 0;
        this.listenerList = new LinkedList <IEventListener>();
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Used to register a listener with an event.
     * <p>
     * A listener can be any class than implements <b>IEventListener</b>, and 
     * overrides the <b>IEventListener OnEvent</b> method.
     * <p>
     * When the event is dispatched, all <b>OnEvent</b> methods overriden in all 
     * registered listeners will be called. 
     * </ul>
     * @param listener
     * <br>
     *      The class to register as a listener.<br>
     *      The registered class must have implemented <b>IEventListener</b>, and 
     *      overriden the <b>IEventListener OnEvent</b> method.<br>
     * <br>
     */
    //--------------------------------------------------------------------------
            
    public void register ( IEventListener listener )
    {
        this.listenerList.add ( listener );
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Used to de-register a previously registered listener.
     * </ul>
     * @param listener
     * <br>
     *      The class to deregister as a listener.<br>
     * <br>
     */
    //--------------------------------------------------------------------------
    
    public void deregister ( IEventListener listener )
    {
        this.listenerList.remove ( listener );
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Dispatch the event. The event is dispatched, by calling the <b>OnEvent</b>
     * of all registered listeners.
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void dispatch ()
    {
        for ( IEventListener listener : this.listenerList )
        {
            listener.OnEvent ( this );
        }
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Deregister all listeners.
     * of all registered listeners.
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void clear ()
    {
        this.listenerList.clear ();
    }
    
    //@formatter:on
}

