//-------------------------------------------------------------------------
// Title:   State
// Version: 1.0
//
// Description:
//   State to be used with StateMachine
//-------------------------------------------------------------------------

public class State extends Base
{
	public boolean endState;	// = true if this is an end state, = false if not.
	
	public State ()
	{	
		this.endState = false;		// Not an end state by default
	}
	
	public State ( String name )
	{
		setName ( name );			// Initialise the internal name
		setText ( getName() );		// Set the display name to the internal name by default.

		this.endState = false;		// Not an end state by default
	}
	
	public State ( String name, boolean endState )
	{
		setName ( name );			// Initialise the internal name
		setText ( getName() );		// Set the display name to the internal name by default.
		
		this.endState = endState;	// Initialise the end state
	}
	
	public State ( String name, String text, boolean endState )
	{
		setName ( name );			// Initialise the internal name
		setText ( text );			// Set the display name to the internal name by default.
		
		this.endState = endState;	// Initialise the end state
	}
}