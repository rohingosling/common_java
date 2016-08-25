import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//-------------------------------------------------------------------------
// Title:   StateMachine
// Version: 1.0
//
// Description:
//   General purpose light weight state machine.
//-------------------------------------------------------------------------

public class StateMachine extends Base
{
	//-------------------------------------------------------------------------
	// Private data types
	//-------------------------------------------------------------------------
	
	enum Status
	{
		stopped,
		running,
	}
	
	
	//-------------------------------------------------------------------------
	// Private Data
	//-------------------------------------------------------------------------
	
	private Map <String, State>     stateList;			// List of available states
	private Map <String, Condition> conditionList;		// List of available conditions
	private ArrayList <Transition>  transitionTable;	// Transition Table that describes the state machines behaviour.
	private State                   startState;			// The initial state to start with
	private State                   currentState;		// The current active state	
	private Stack <State>           stateStack;			// State stack to enable rolling back to previous state
	private Status                  status;				// State machine status.	
	
		
	//-------------------------------------------------------------------------
	// Constructor 1
	//-------------------------------------------------------------------------
	
	public StateMachine ()
	{
		// Initialise a StateMachine
		
		setName ( "" );
		setText ( getName() );
		
		this.stateList       = new HashMap <String, State> ();
		this.conditionList   = new HashMap <String, Condition> ();
		this.transitionTable = new ArrayList <Transition> ();
		this.startState      = null;
		this.currentState    = null;
		this.stateStack      = new Stack <State> ();
		this.status          = Status.stopped; 		
	}	
	
	//-------------------------------------------------------------------------
	// Constructor 2
	//-------------------------------------------------------------------------
	
	public StateMachine ( String name )
	{
		// Initialise a StateMachine
		
		setName ( name );
		setText ( getName() );
		
		this.stateList       = new HashMap <String, State> ();
		this.conditionList   = new HashMap <String, Condition> ();
		this.transitionTable = new ArrayList <Transition> ();
		this.startState      = null;
		this.currentState    = null;
		this.stateStack      = new Stack <State> ();
		this.status          = Status.stopped;		
	}
	
	
	//-------------------------------------------------------------------------
	// Constructor 3
	//-------------------------------------------------------------------------
	
	public StateMachine ( String name, String text )
	{
		// Initialise a StateMachine
		
		setName ( name );
		setText ( text );

		this.stateList       = new HashMap <String, State> ();
		this.conditionList   = new HashMap <String, Condition> ();
		this.transitionTable = new ArrayList <Transition> ();
		this.startState      = null;
		this.currentState    = null;
		this.stateStack      = new Stack <State> ();
		this.status          = Status.stopped;		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clear
	//
	// Description:
	//   Clear the state machine. 
	//   Clear all lists and stacks and reset all internal properties to their
	//   initial values.
	//-------------------------------------------------------------------------
			
	public void clear ()
	{
		// Clear lists and stacks

		this.stateList.clear ();
		this.conditionList.clear ();
		this.transitionTable.clear ();		
		this.endStateList.clear ();
		this.stateStack.clear ();
		
		// Initialise and reset all internal properties.
		
		this.startState   = null;
		this.currentState = null;
		this.status       = Status.stopped; 
	}
	
	
	//-------------------------------------------------------------------------
	// Method: reset
	//
	// Description:
	//   Reset a StateMachine to it's initial state.
	//-------------------------------------------------------------------------
			
	public void reset ()
	{
		// We will only allow the state machine to be reset if it is stopped.
		
		if ( this.status == Status.stopped )
		{		
			this.currentState = this.startState;	// Reset the start state
			this.status       = Status.stopped;		// Stop the state machine
		}
	}
	
	
	//-------------------------------------------------------------------------
	// Method: isValid
	//
	// Description:
	//   Test to see if the state machine is configured correctly.
	//-------------------------------------------------------------------------
			
	public boolean isValid ()
	{
		// Initialise a validity flag to true by default.
		// We will set it to false if we find something wrong.
		
		boolean isValid = true;
		
		
		// Check validation conditions
		
		if ( this.stateList == null )      isValid = false;	// We need to create state list and have at least 1 start state.
		if ( this.stateList.size () <= 0 ) isValid = false;	// We gota have some states for a state machine.
		if ( this.startState == null )     isValid = false;	// Can't start with out a start state
		
		// Send the caller our result.
		
		return isValid;
	}
	
	//-------------------------------------------------------------------------
	// Method: start
	//
	// Description:
	//   Start the state machine.
	//-------------------------------------------------------------------------
			
	public boolean start ()
	{
		// Reset the state machine.
		
		reset ();
		
		
		// We will only start the state machine if it passes all the validity checks.
		
		boolean isValid = isValid ();
		
		if ( isValid == true )
		{
			// Start the state machine
			
			this.status = Status.running;
		}
		
		
		// Return our validity result in case the caller wants it for anything.
		
		return isValid;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: stop
	//
	// Description:
	//   Stop the state machine.
	//-------------------------------------------------------------------------
			
	public void stop ()
	{
		// Stop the state machine
		
		this.status = Status.stopped;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: resume
	//
	// Description:
	//   Resume execution of the state machine from where it was last stopped.
	//-------------------------------------------------------------------------
			
	public boolean resume ()
	{
		// We will only resume execution of the state machine if it passes all the validity checks.
		// Although is was valid when we initially started it, someone may have messed with it
		// since we last stopped it.
		
		boolean isValid = isValid ();
		
		if ( isValid == true )
		{
			// Re-start the state machine.
			
			this.status = Status.running;
		}
		
		
		// Return our validity result in case the caller wants it for anything.
		
		return isValid;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getStateList
	//
	// Description:
	//   Returns the state list.
	//-------------------------------------------------------------------------
			
	public Collection <State> getStateList ()
	{		
		return this.stateList.values ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getConditionList
	//
	// Description:
	//   Returns the condition list.
	//-------------------------------------------------------------------------
			
	public Collection <Condition> getConditionList ()
	{
		return this.conditionList.values ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getTransitionList
	//
	// Description:
	//   Returns the transition table
	//-------------------------------------------------------------------------
			
	public ArrayList <Transition> getTransitionList ()
	{
		return this.transitionTable;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: setStartState
	//
	// Description:
	//   Sets the start state.
	//-------------------------------------------------------------------------
			
	public void setStartState ( String stateName )
	{
		if ( this.stateList.containsKey ( stateName ) == true )
		{
			this.startState = this.stateList.get ( stateName );
		}
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getStateName
	//
	// Description:
	//   Returns the name of a state.
	//-------------------------------------------------------------------------
			
	public String getStateName ( State state )
	{
		return state.name;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getConditionName
	//
	// Description:
	//   Returns the name of a condition.
	//-------------------------------------------------------------------------
			
	public String getConditionName ( Condition condition )
	{
		return condition.name;
	}
	
	//-------------------------------------------------------------------------
	// Method: getTransitionInitialStateName
	//
	// Description:
	//   Returns the name of the initial state specified in a transition.
	//-------------------------------------------------------------------------
			
	public String getTransitionInitialStateName ( Transition transition )
	{
		return transition.initialState;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getTransitionConditionName
	//
	// Description:
	//   Returns the name of the condition specified in a transition.
	//-------------------------------------------------------------------------
			
	public String getTransitionConditionName ( Transition transition )
	{
		return transition.condition;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getTransitionGoalStateName
	//
	// Description:
	//   Returns the name of the goal state specified in a transition.
	//-------------------------------------------------------------------------
			
	public String getTransitionGoalStateName ( Transition transition )
	{
		return transition.goalState;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getStartStateName
	//
	// Description:
	//   Gets the start state name.
	//-------------------------------------------------------------------------
			
	public String getStartStateName ()
	{
		return this.startState.name;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getCurrentStateName
	//
	// Description:
	//   Gets current state name.
	//-------------------------------------------------------------------------
			
	public String getCurrentStateName ()
	{
		return this.currentState.name;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getStartState
	//
	// Description:
	//   Returns the start state.
	//-------------------------------------------------------------------------
			
	public State getStartState ()
	{
		return this.startState;
	}
	
	//-------------------------------------------------------------------------
	// Method: getCurrentState
	//
	// Description:
	//   Returns the current state.
	//-------------------------------------------------------------------------
			
	public State getCurrentState ()
	{
		return this.currentState;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getState
	//
	// Description:
	//   Returns a state based on a state name.
	//
	// Return Value:
	//   Returns the state mapped to the state name we specify.
	//   Returns null if there is nothing mapped to the name we specify.
	//
	//-------------------------------------------------------------------------
			
	public State getState ( String stateName )
	{
		return this.stateList.get ( stateName );
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getCondition
	//
	// Description:
	//   Returns a condition based on a condition name.
	//
	// Return Value:
	//   Null if we don't find a condition.
	//   If the search returns a result then the transition we've found will be returned.
	//
	//-------------------------------------------------------------------------
			
	public Condition getCondition ( String conditionName )
	{
		return this.conditionList.get ( conditionName );
	}
	
	
	//-------------------------------------------------------------------------
	// Method: getTransition
	//
	// Description:
	//   Returns a transition based on initial state, condition, and goal state.
	//
	// Return Value:
	//   Null if we don't find a transition with these properties.
	//   If the search returns a result then the transition we've found will be returned.
	//
	//-------------------------------------------------------------------------
			
	public Transition getTransition (  String initialState, String condition, String goalState  )
	{
		// Initialise a transition existence flag, and set it to false.
		// We will presume that the new transition does not exist until proven otherwise.
		
		Transition returnTransition = null;
		
		
		// Search the transition table list for a transition that has the same state transition properties
		// as our new transition.
		// If we get to the end of the for-each loop with out finding a transition with the properties
		// as our new transition, then our existence flag will still be false, and we will know that its 
		// safe to add the new transition.
				
		for ( Transition transition : this.transitionTable )
		{
			if 
			(
				( transition.initialState == initialState )
				&&
				( transition.condition    == condition )
				&&
				( transition.goalState    == goalState )
			) 
			{
				// If we find a transition with the same properties as our new transition, then
				// set the transition existence flag to reflect this fact
				
				returnTransition = transition;
				
				// We've found what we were looking for so we can exit the loop now.
				
				break;
			}
		}
		
		// Return the negation of our existence flag in case the caller wants to 
		// know if we added the new transition or not.
		
		return returnTransition;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: stateCount
	//
	// Description:
	//   Returns the number of states in the state list.
	//-------------------------------------------------------------------------
			
	public int stateCount ()
	{
		return this.stateList.size ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: conditionCount
	//
	// Description:
	//   Returns the number of conditions in the condition list.
	//-------------------------------------------------------------------------
			
	public int conditionCount ()
	{
		return this.conditionList.size ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: transitionCount
	//
	// Description:
	//   Returns the number of transitions in the transition table.
	//-------------------------------------------------------------------------
			
	public int transitionCount ()
	{
		return this.transitionTable.size ();
	}
	
	//-------------------------------------------------------------------------
	// Method: containsState
	//
	// Description:
	//   Determine if a state already exists in the state list or not.
	//
	// Return Value:
	//   return value = true, if the state does exist in the list.
	//   return value = false, if the state does not exist in the list.
	//-------------------------------------------------------------------------
			
	public boolean containsState ( String stateName )
	{
		return this.stateList.containsKey ( stateName );
	}
	
	
	//-------------------------------------------------------------------------
	// Method: containsCondition
	//
	// Description:
	//   Determine if a condition already exists in the condition list or not.
	//
	// Return Value:
	//   return value = true, if the condition does exist in the list.
	//   return value = false, if the condition does not exist in the list.
	//-------------------------------------------------------------------------
			
	public boolean containsCondition ( String conditionName )
	{
		return this.conditionList.containsKey ( conditionName );
	}
	
	
	//-------------------------------------------------------------------------
	// Method: containsTransition
	//
	// Description:
	//   Determine if a transition already exists in the transition table or not.
	//
	// Return Value:
	//   return value = true, if the transition does exist in the transition table.
	//   return value = false, if the transition does not exist in the transition table.
	//-------------------------------------------------------------------------
			
	public boolean containsTransition ( String initialState, String condition, String goalState )
	{
		// Initialise a transition existence flag, and set it to false.
		// We will presume that the new transition does not exist until proven otherwise.
		
		boolean transitionExists = false;
		
		
		// Search the transition table list for a transition that has the same state transition properties
		// as our new transition.
		// If we get to the end of the for-each loop with out finding a transition with the properties
		// as our new transition, then our existence flag will still be false, and we will know that its 
		// safe to add the new transition.
				
		for ( Transition transition : this.transitionTable )
		{
			if 
			(
				( transition.initialState == initialState )
				&&
				( transition.condition    == condition )
				&&
				( transition.goalState    == goalState )
			) 
			{
				// If we find a transition with the same properties as our new transition, then
				// set the transition existence flag to reflect this fact
				
				transitionExists = true;
				
				// We've found what we were looking for so we can exit the loop now.
				
				break;
			}
		}
		
		// Return the negation of our existence flag in case the caller wants to 
		// know if we added the new transition or not.
		
		return transitionExists;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: addState
	//
	// Description:
	//   Add a new state to the state list.
	//-------------------------------------------------------------------------
			
	public void addState ( String stateName )
	{		
		// Add the new state if it does not already exist
		
		if ( containsState ( stateName ) == false )
		{
			this.stateList.put ( stateName, new State ( stateName ) );
		}		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: addCondition
	//
	// Description:
	//   Add a new condition to the condition list.
	//
	// Return Value:
	//   return value = true, if the condition was added.
	//   return value = false, if the condition was not added.
	//-------------------------------------------------------------------------
			
	public void addCondition ( String conditionName )
	{
		// Add the new condition if it does not already exist
		
		if ( containsCondition ( conditionName ) == false )
		{
			this.conditionList.put ( conditionName, new Condition ( conditionName ) );
		}		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: addTransition
	//
	// Description:
	//   Add a new transition to the transition table.
	//
	// Return Value:
	//   return value = true, if the transition was added.
	//   return value = false, if the transition was not added.
	//-------------------------------------------------------------------------
			
	public void addTransition ( String initialState, String condition, String goalState )
	{
		// Add the new transition if it does not already exist
		
		if ( containsTransition ( initialState, condition, goalState ) == false )
		{
			this.transitionTable.add ( new Transition ( initialState, condition, goalState ) );
		}	
	}
	
	//-------------------------------------------------------------------------
	// Method: nextState
	//
	// Description:
	//   Change to the next valid state by applying a condition to our
	//   current state.
	//-------------------------------------------------------------------------
			
	public State nextState ( String condition )
	{
		// Find the state we should transition to if we apply this condition
		
		State goalState = findNextState ( this.currentState.name, condition );
		
		
		// We will only transition to the next state if the state machine is
		// currently running.
		
		if ( this.status == Status.running )
		{			
			// If we find a valid state to change to, then set out current state to 
			// the new goal state
			
			if ( goalState != null )
			{
				// Push our current state on the state stack in case we ever 
				// feel like rolling back at a later stage.
				
				this.stateStack.push ( this.currentState );
				
				// Assign our current state to the new state to transition to.
				
				this.currentState = goalState;
			}
		}
		
		// Return the goal state to the caller so that they are able to tell if 
		// there was a valid state to transition to or not.
		// If we never found a valid goal state to transition to, then we send 
		// the caller a null.
			
		return goalState;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: findNextState
	//
	// Description:
	//   Search the transition table for the next state to goto, given a
	//   specified initial state and condition.
	//-------------------------------------------------------------------------
			
	public State findNextState ( String initialState, String condition )
	{
		// Initialise a goal state to return the result of our search to the caller.
		// We initialise it to null, so that if we never find a goal state
		// in the transition table, we can send a null to the caller to let them
		// know that we never found anything.
		
		String goalState = null;			
		
		// Iterate through all transitions in our transition table, until
		// we find a transition where the initial state is equal to our
		// initial state, and the condition is equal to the condition we wish
		// to test for. Then assign our goal state the value of the transitions
		// goal state.
		
		for ( Transition transition : this.transitionTable )
		{			
			if ( (transition.initialState == initialState) && (transition.condition == condition) )
			{
				// If we find the initial state we're looking for in the transition, and the condition 
				// is the same as the condition we would like to test for, then set the our goal state 
				// to the goal state of the transition.
				
				goalState = transition.goalState;
				
				// We've found what we were looking for, so we can exit the for-each loop now.
				
				break;				
			}
		}		
		
		// Return our goal state to the caller.
		// If no goal state was found, then we shall send the caller a null.
		
		return getState ( goalState );
	}
	
	
	//-------------------------------------------------------------------------
	// Method: previousState
	//
	// Description:
	//   Roll back to the previous state stored on our state stack.
	//
	// Return value:
	//   null if the state stack is empty, else we return the last state.
	//-------------------------------------------------------------------------
			
	public State previousState ()
	{
		// Initialise a State variable to hold the value of the last State
		// we were at.
		
		State lastState = null;
		
		
		// We will only attempt to pop the last state from the state stack
		// if there are actually states in the stack.
		
		if ( this.stateStack.isEmpty () == false )
		{		
			// Retrieve the last state from out state stack
		
			lastState = this.currentState = this.stateStack.pop ();
		}
		
		
		// Send the caller the last state we were at.
		
		return lastState;
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clearStateList
	//
	// Description:
	//   Clear the state list.
	//-------------------------------------------------------------------------
			
	public void clearStateList ()
	{
		this.stateList.clear ();		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clearConditionList
	//
	// Description:
	//   Clear the condition list.
	//-------------------------------------------------------------------------
			
	public void clearConditionList ()
	{
		this.conditionList.clear ();		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clearTransitionTable
	//
	// Description:
	//   Clear the transition table.
	//-------------------------------------------------------------------------
			
	public void clearTransitionTable ()
	{
		this.transitionTable.clear ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clearEndStates
	//
	// Description:
	//   Clear the list of end states.
	//-------------------------------------------------------------------------
			
	public void clearEndStates ()
	{
		this.endStateList.clear ();		
	}
	
	
	//-------------------------------------------------------------------------
	// Method: clearStateStack
	//
	// Description:
	//   Clear the contents of the state stack.
	//-------------------------------------------------------------------------
			
	public void clearStateStack ()
	{
		this.stateStack.clear ();
	}
	
	
	//-------------------------------------------------------------------------
	// Method: load
	//
	// Description:
	//   Loads a state machine file.
	//-------------------------------------------------------------------------
			
	public void load ( String fileName )
	{
		// To-Do
	}
}










