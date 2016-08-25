//-------------------------------------------------------------------------
// Title:   Base
// Version: 1.0
//
// Description:
//   Base Class from which all other StateMachine classes are derived.
//-------------------------------------------------------------------------


public class Base
{
	//-------------------------------------------------------------------------
	// Private data members
	//-------------------------------------------------------------------------
	
	private String name;	// Internal name
	private String text;	// External display name
	
	
	//-------------------------------------------------------------------------
	// Getters and Setters
	//-------------------------------------------------------------------------
	
	public String getName ()
	{
		return name;
	}
	public void setName ( String name )
	{
		this.name = name;
	}

	public String getText ()
	{
		return text;
	}
	public void setText ( String text )
	{
		this.text = text;
	}

	
	//-------------------------------------------------------------------------
	// Constructor 1
	//-------------------------------------------------------------------------
	
	public Base ()
	{
		this.name = "";
		this.text = this.name;
	}
	
	
	//-------------------------------------------------------------------------
	// Constructor 2
	//-------------------------------------------------------------------------
	
	public Base ( String name )
	{
		this.name = name;
		this.text = this.name;	
	}
	
	
	//-------------------------------------------------------------------------
	// Constructor 3
	//-------------------------------------------------------------------------
	
	public Base ( String name, String text )
	{
		this.name = name;
		this.text = text;
	}
}
