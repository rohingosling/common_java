// -------------------------------------------------------------------------
// Title:   Test Program
// Version: 1.0
//
// Description:
//   Test Program.
// -------------------------------------------------------------------------

public class TestProgram
{
	// -------------------------------------------------------------------------
	// Method: run
	//
	// Description:
	//   Program entry point.
	// -------------------------------------------------------------------------

	public void run ()
	{
		Console console = new Console();
		
		String contactCode = extractContactCode ( "......[1234567]......" );
		
		
	}
	
	// -------------------------------------------------------------------------
	// Method: run
	//
	// Description:
	//   Program entry point.
	// -------------------------------------------------------------------------

	public String extractContactCode  ( String inputString )
	{
		
		
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Method: stateMachineTest
	//
	// Description:
	//   StateMachine class test driver.
	// -------------------------------------------------------------------------

	public void stateMachineTest ()
	{
		//StateMachine stateMachine = new StateMachine ();
			
		//stateMachine.reset ();
	}
	
	// -------------------------------------------------------------------------
	// Method: consoleTest
	//
	// Description:
	//   Console class test driver.
	// -------------------------------------------------------------------------

	public void consoleTest ()
	{
		Console console = new Console();
		
		console.drawLine ();
		console.setTextAlignment ( Console.TextAlignment.center );
		console.textOut ( "Hello World !" );
		console.textOut ( "Version 1.0" );
		console.drawLine ();
		console.newLine ();
		console.setTextAlignment ( Console.TextAlignment.left );
		
			// Indents
		
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.indent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.indent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.indent ( 9 );
			console.indent ();
			console.textOut ( "Indent = " + console.getIndent () );
		
			console.indent ( 8 );
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.indent ( 3 );
			console.textOut ( "Indent = " + console.getIndent () );
			console.textOut ( "Indent = " + console.getIndent () );
			
			// Out dents
			
			console.outdent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.outdent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.outdent ();
			console.outdent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.outdent ();
			console.textOut ( "Indent = " + console.getIndent () );
			
			console.outdent ();
			console.textOut ( "Indent = " + console.getIndent () );
		
		console.newLine ();		
		console.drawLine ();	
	}

	// -------------------------------------------------------------------------
	// main
	// -------------------------------------------------------------------------

	public static void main ( String [] args )
	{
		TestProgram program = new TestProgram ();

		program.run ();
	}
}
