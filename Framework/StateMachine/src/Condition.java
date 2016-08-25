//-------------------------------------------------------------------------
// Title:   Condition
// Version: 1.0
//
// Description:
//   Condition to be used with StateMachine
//-------------------------------------------------------------------------

public class Condition extends Base
{
	public Condition ()
	{
		setName ( "" );
		setText ( getName () );
	}
	
	public Condition ( String name )
	{
		setName ( name );
		setText ( getName () );
	}
	
	public Condition ( String name, String text )
	{
		setName ( name );
		setText ( text );
	}
}