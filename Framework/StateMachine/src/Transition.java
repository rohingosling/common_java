//-------------------------------------------------------------------------
// Title:   Transition
// Version: 1.0
//
// Description:
//   Transition to be used with StateMachine
//-------------------------------------------------------------------------

public class Transition
{
	public State     initialState;	// Initial state to move from
	public Condition condition;		// Condition to invoke transition.
	public State     goalState;		// Goal state to move to from initial state.
	
	public Transition ()
	{
		this.initialState = null;
		this.condition    = null;
		this.goalState    = null;
	}
	
	public Transition ( State initialState, Condition condition, State goalState )
	{	
		this.initialState = initialState;
		this.condition    = condition;
		this.goalState    = goalState;
	}
}