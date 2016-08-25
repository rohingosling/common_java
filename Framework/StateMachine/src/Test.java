import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//-------------------------------------------------------------------------
// Title:   Test ( StateMachine )
// Version: 1.0
//
// Description:
//   Test driver for StateMachine
//-------------------------------------------------------------------------

public class Test
{	
	//-------------------------------------------------------------------------
	// Method: run
	//
	// Description:
	//   Program entry point
	//-------------------------------------------------------------------------
			
	public void run ()
	{
		System.out.println ( "State Machine Test...\n");
		
		// Initialise new state machine
		
		StateMachine stateMachine = new StateMachine ();
		
		
		// Add states
		
		stateMachine.addState ( "A" );
		stateMachine.addState ( "B" );
		stateMachine.addState ( "C" );
		stateMachine.addState ( "D" );
		

		// Add conditions
		
		stateMachine.addCondition ( "x" );
		stateMachine.addCondition ( "y" );
		stateMachine.addCondition ( "z" );
		
		
		// Add transitions
		
		stateMachine.addTransition ( "A", "x", "B" );
		stateMachine.addTransition ( "A", "y", "D" );
		stateMachine.addTransition ( "A", "z", "C" );
		stateMachine.addTransition ( "B", "x", "C" );
		stateMachine.addTransition ( "B", "y", "A" );
		stateMachine.addTransition ( "C", "x", "D" );
		stateMachine.addTransition ( "C", "y", "B" );
		stateMachine.addTransition ( "D", "x", "A" );
		stateMachine.addTransition ( "D", "y", "C" );
		
		
		// Set the start state
		
		stateMachine.setStartState ( "A" );
		
		
		// Show states
		
		Collection <State> stateList = stateMachine.getStateList ();
		
		System.out.println ( "States" );
		for ( State state : stateList )
		{
			System.out.println ( "  " + state.name );
		}
		System.out.println ();
		
		
		// Show conditions
		
		Collection <Condition> conditionList = stateMachine.getConditionList ();		 
		
		System.out.println ( "Conditions" );
		for ( Condition condition : conditionList )
		{
			System.out.println ( "  " + condition.name );
		}
		System.out.println ();
		
		
		// Show transitions
		
		ArrayList <Transition> transitionTable = stateMachine.getTransitionList ();
		
		System.out.println ( "Transitions" );
		for ( Transition transition : transitionTable )
		{
			System.out.print ( "  ( " );
			System.out.print ( transition.initialState );
			System.out.print ( ", " );
			System.out.print ( transition.condition );
			System.out.print ( ", " );
			System.out.print ( transition.goalState );
			System.out.print ( " )\n" );
		}
		System.out.println ();
		
		
		// Show the start state
		
		System.out.println ( "Start State = " + stateMachine.getStartStateName () );
		
		
		// Start the state machine
		
		stateMachine.start ();
		System.out.println ( "State Machine Status = Running" );
		
			// Apply some conditions
		
			System.out.println ();
		
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "z" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "x" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "z" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			stateMachine.nextState ( "z" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "x" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "x" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "z" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.nextState ( "y" );
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			System.out.println ();
			
			// Start rolling back
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			stateMachine.previousState ();
			System.out.println ( "  State = " + stateMachine.getCurrentStateName () );
			
			System.out.println ();
		
		stateMachine.stop ();
		System.out.println ( "State Machine Status = Stopped" );
		
		stateMachine.reset ();
		System.out.println ( "State = " + stateMachine.getCurrentStateName () );
	}
	
	//-------------------------------------------------------------------------
	// main
	//-------------------------------------------------------------------------

	public static void main ( String [] args )
	{
		Test testStateMachine = new Test ();
		
		testStateMachine.run ();
	}

}
