package rohin.gameengine;

//------------------------------------------------------------------------------
/**
* <b>Description:</b><br>
* <ul>
* <b>IEventListener</b> is a the event subscriber interface, used to register event 
* handlers for specified <b>Event</b> objects.
* </ul>
*  
* @author      Rohin Gosling
* @version     1.0
* @since       2001-01-10
*/
//------------------------------------------------------------------------------

interface IEventListener
{
  public void OnEvent ( Event event );
}


