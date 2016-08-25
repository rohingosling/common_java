package rohin.gameengine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

//------------------------------------------------------------------------------
/**
* <b>Description:</b><br>
* <ul>
* <b>EventManager</b>, is a generic event management class, used to manage
* instances of the <b>Event</b> class.
* </ul>
* <p>
* <b>Features:</b><br>
* <ul>
* <li>An event cache, used to store pre-loaded events for quick retrieval and
* event validation.<br>
* <li>A primary event dispatching queue.<br>
* <li>Ability to cache, de-cache, register and de-register events.<br>
* <li>A <b>DispatchAll</b> method that can be used to dispatch all events
* with out clearing the event queue. This is useful for situations where one
* may wish to re-dispatch all events multiple times.<br>
* <li>A <b>Flush</b> method used for dispatching all events, and then clearing
* the event queue after the all events have been dispatched.<br>
* </ul>
*  
* @author      Rohin Gosling
* @version     1.0
* @since       2001-01-10
*/
//------------------------------------------------------------------------------

class EventManager implements Comparator <Event>
{
    //@formatter:off
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // CONSTANTS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    private static final int M_DEFAULT_INITIAL_EVENT_CACHE_CAPACITY = 100;
    private static final int M_DEFAULT_INITIAL_EVENT_QUEUE_CAPACITY = 1000;
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    private HashMap       <String, Event> eventCache;                   // Stores cached events for quick retrieval and validation.
    private PriorityQueue <Event>         eventQueue;                   // Events posted to the event queue are ready to be dispatched.
    private int                           initialEventQueueCapacity;    // Initial event queue capacity.
    private int                           initialEventCacheCapacity;    // Initial event cache capacity.
    
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
     */
    //--------------------------------------------------------------------------
    
    public EventManager ()
    {
        reset ();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //
    // METHODS
    //
    ////////////////////////////////////////////////////////////////////////////
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * The <b>Comparator</b> used by the event queue <b>PriorityQueue</b>, to
     * order events in the priority queue. 
     * </ul>
     * @param a
     * <br>
     *      Comparitor argument A.<br>
     * <br>
     *         
     * @param b
     * <br>
     *      Comparitor argument B<br>
     * <br>
     */
    //--------------------------------------------------------------------------
    
    // @Override
    public int compare ( Event a, Event b )
    {
        int comparisonBias = 0;
        
        if (a.getTime () < b.getTime () )
        {
            comparisonBias = -1;
        }
        
        if (a.getTime () > b.getTime () )
        {
            comparisonBias = 1;
        }
        
        return comparisonBias;
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Initialize or reset the object. 
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void reset ()
    {
        // Set the initial capacities for the event cache and event queue.
        
        this.initialEventCacheCapacity = M_DEFAULT_INITIAL_EVENT_CACHE_CAPACITY;
        this.initialEventQueueCapacity = M_DEFAULT_INITIAL_EVENT_QUEUE_CAPACITY;
        
        // create a new event cache and event queue.
        
        this.eventCache = new HashMap       <String, Event> ( this.initialEventCacheCapacity );
        this.eventQueue = new PriorityQueue <Event>         ( this.initialEventQueueCapacity, this );
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Cache an object for later use. 
     * </ul>
     * @param event
     * <br>
     *      The event to cache.
     * <br>
     */
    //--------------------------------------------------------------------------
    
    public void cache ( Event event )
    {   
        // Create an exception, to handel the case that the selected event fileName
        // is not unique. Event names are used as unique identifiers, and as 
        // such are required to be unique.
        
        Exception exceptionNameNotUnique = new Exception ( compileExceptionString ( "Event", event.getName () ) );
        
        // Try to cache the event. And if the event fileName is not unique, then 
        // raise the "exceptionNameNotUnique" exception.
        
        try
        {
            // Determine if the event fileName is already in the list.
            
            Boolean uniqueKey = ! this.eventCache.containsKey ( event.getName () );
            
            // If the new event fileName is unique, then we add the event.
            // Otherwise we throw the exception.
            
            if ( uniqueKey )
            {
                this.eventCache.put ( event.getName (), event );
            }
            else
            {
                throw ( exceptionNameNotUnique );
            }
        }
        catch ( Exception e)
        {
            System.out.println ( e );
        }
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Remove an event from the event cache. 
     * </ul>
     * @param event
     * <br>
     *      The event to remove.
     * <br>
     */
    //--------------------------------------------------------------------------
    
    public void decache ( String eventName )
    {   
        this.eventCache.remove ( eventName );
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Register a listener with an event.
     * <p>
     * A listener is any class that implements <b>IEventListener</b>, and overrides
     * the <b>OnEvent</b> method.  
     * </ul>
     * @param listener
     * <br>
     *      The listener class to register. 
     * <br><br>
     * @param eventName
     * <br>
     *      The event fileName of the event to register the listener class to.
     * <br><br>
     */
    //--------------------------------------------------------------------------
    
    public Event register ( IEventListener listener, String eventName )
    {   
        // Get the event object for the named event, that we would like to 
        // register a listener with.
        
        Event event = this.eventCache.get ( eventName );
        
        // Register the listener with the selected event.
        
        event.register ( listener );
        
        // Return the event, just in case the caller would like to use it for
        // anything
        
        return event;
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * De-register a listener from an event. 
     * </ul>
     * @param listener
     * <br>
     *      The listener class to de-register. 
     * <br><br>
     * @param eventName
     * <br>
     *      The event fileName of the event to de-register the listener class from.
     * <br><br>
     */
    //--------------------------------------------------------------------------
    
    public Event deregister ( IEventListener listener, String eventName )
    {
        // Get the event object for the named event, that we would like to 
        // deregister a listener from.
        
        Event event = this.eventCache.get ( eventName );
        
        // Deregister the listener from the selected event.
        
        event.deregister ( listener );
        
        // Return the event, just in case the caller would like to use it for
        // anything
        
        return event;
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Post an event to the active event queue.
     * <p>
     * Posted events will be dispatched on the next call to <b>DispatchAll</b>,
     * or <b>Flush<b/>.
     * </ul> 
     * <b>Note:</b>
     * <ul>
     * </b>DispatchAll</b>, dispatches all events in the event queue, but 
     * does not clear the event queue after. Subsequent calls to 
     * <b>DispatchAll</b> will continue to dispatch all events in the event 
     * queue, until a call to <b>Flush</b> is made, or a call to 
     * <b>ClearEventQueue</b>. 
     * </ul>
     * @param eventName
     * <br>
     *      The event fileName of the event to post.
     * <br><br>
     */
    //--------------------------------------------------------------------------
    
    public Event post ( String eventName )
    {
        // Get the event object for the named event, that we would like to post.
        
        Event event = this.eventCache.get ( eventName );
        
        // Record the time that we are posting this event.
        
        event.setTime ( System.currentTimeMillis() );
        
        // Post the event. i.e. Add the event to the event queue.
        
        this.eventQueue.add ( event );
        
        // Return the event, just in case the caller would like to use it for
        // anything.
        
        return event;
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Dispatches all events in the event queue.
     * <p>
     * Posted events are dispatched on the next call to <b>DispatchAll</b>,
     * or <b>Flush<b/>.
     * </ul> 
     * <b>Note:</b>
     * <ul>
     * </b>DispatchAll</b>, dispatches all events in the event queue, but 
     * does not clear the event queue after. Subsequent calls to 
     * <b>DispatchAll</b> will continue to dispatch all events in the event 
     * queue, until a call to <b>Flush</b> is made, or a call to 
     * <b>ClearEventQueue</b>. 
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void dispatchAll ()
    {
        // Call the Dispatch mothod of each event in the event queue.
        
        for ( Event event : this.eventQueue )
        {
            event.dispatch ();
        }
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Dispatches all events in the event queue. And then clears the event queue
     * when done.<br>
     * Equivalent to calling, <b>DispatchAll</b>, followed by 
     * <b>ClearEventQueue</b>.
     * <p>
     * Posted events are dispatched on the next call to <b>DispatchAll</b>,
     * or <b>Flush<b/>.
     * </ul> 
     * <b>Note:</b>
     * <ul>
     * </b>DispatchAll</b>, dispatches all events in the event queue, but 
     * does not clear the event queue after. Subsequent calls to 
     * <b>DispatchAll</b> will continue to dispatch all events in the event 
     * queue, until a call to <b>Flush</b> is made, or a call to <b>Clear</b>. 
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void flush ()
    {
        // Dispatch all events in the evnet queue.
        
        dispatchAll ();
        
        // And then clear the event queue when we done.
        
        this.eventQueue.clear ();
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Clears the event queue.
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void clearEventQueue ()
    {
        this.eventQueue.clear ();
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Clears the event cache.
     * </ul>
     */
    //--------------------------------------------------------------------------
    
    public void clearEventCache ()
    {
        this.eventCache.clear ();
    }
    
    //--------------------------------------------------------------------------
    /**
     * <b>Description:</b>
     * <ul>
     * Utility method used to compile an exception string for when an exception
     * is raised after an Event is defined using a fileName that has already been 
     * used for another event. 
     * </ul>
     * @param componentType
     * <br>
     *      A string representing the component type involved.<br>
     *      e.g. Component, Event, Actor, etc. 
     * 
     * <br><br>
     * @param componentName
     * <br>
     *      The fileName that is not unique. Is would be this fileName that needs to be
     *      changed to something unique..
     * <br><br>
     */
    //--------------------------------------------------------------------------
    
    private String compileExceptionString (String componentType, String componentName )
    {
        String exceptionString = new String ();
        
        exceptionString += "\n\n"; 
        exceptionString += "EventManager - Exception ( Name not unique ):\n";
        exceptionString += "- " + componentType + " fileName, \"" + componentName + "\", already exists.\n";
        exceptionString += "- " + componentType + " names are used as entity ID's, and should be unique.\n";
        
        return exceptionString;
    }
    
    //@formatter:on
}
