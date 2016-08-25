//-------------------------------------------------------------------------
// Title:   Console
// Version: 1.0
//
// Description:
//   General purpose console class.
//-------------------------------------------------------------------------

import java.util.Stack;

public class Console
{
	//-------------------------------------------------------------------------
	// Data types
	//-------------------------------------------------------------------------
	
	public enum TextAlignment
	{
		center,
		left,
		right
	}
	
	//-------------------------------------------------------------------------
	// Private Data
	//-------------------------------------------------------------------------
	
	// Console properties
	
	private int           consoleWidth;		// Width of the console.
	private int           tabSize;			// Tab size.
	private int           indent;			// Current indent length;
	private char          lineCharacter;	// Character used to draw lines with.
	private boolean       newLine;			// Add a carriage return if 'true'.
	private TextAlignment textAlignment;	// Text alignment
	
	// Control data
	
	private Stack <Integer> indentStack;
	
	
	//-------------------------------------------------------------------------
	// Getters and Setters
	//-------------------------------------------------------------------------

	// textAlignment
	
	public TextAlignment getTextAlignment ()
	{
		return textAlignment;
	}
	public void setTextAlignment ( TextAlignment textAlignment )
	{
		this.textAlignment = textAlignment;
	}
	
	// consoleWidth
	
	public int getConsoleWidth ()
	{
		return consoleWidth;
	}
	public void setConsoleWidth ( int consoleWidth )
	{
		this.consoleWidth = consoleWidth;
	}

	// tabSize
	
	public int getTabSize ()
	{
		return tabSize;
	}
	public void setTabSize ( int tabSize )
	{
		this.tabSize = tabSize;
	}

	// indent
	
	public int getIndent ()
	{
		return indent;
	}
	public void setIndent ( int indent )
	{
		this.indent = indent;
	}

	// lineCharacter
	
	public char getLineCharacter ()
	{
		return lineCharacter;
	}
	public void setLineCharacter ( char lineCharacter )
	{
		this.lineCharacter = lineCharacter;
	}

	// new
	
	public boolean isNewLine ()
	{
		return newLine;
	}
	public void setNewLine ( boolean newLine )
	{
		this.newLine = newLine;
	}

	
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------
	
	public Console ()
	{
		// Initialise the console to it's default state.
		
		reset ();
	}
	
	//-------------------------------------------------------------------------
	// Method: reset
	//
	// Description:
	//   Rest the console to it's default state.
	//-------------------------------------------------------------------------
			
	public void reset ()
	{
		// Set all console properties to their default values.
		
		this.consoleWidth  = 80;
		this.tabSize       = 2;	
		this.indent        = 0;
		this.lineCharacter = '-';	
		this.newLine       = true;
		this.textAlignment = TextAlignment.left;
		
		
		// Reset indent stack
		
		if ( this.indentStack == null )
		{
			// If this is the first time we are resetting, then create a new indent stack.
			
			this.indentStack = new Stack <Integer> ();
		}
		else
		{
			// If we have already created an indent stack, then just clear it.
			
			this.indentStack.clear ();
		}
	}
	
	//-------------------------------------------------------------------------
	// Method: textOut
	//
	// Arguments:
	//   text           - Text string to send to console.
	//   indent         - The number of characters to indent by.
	//   textAlignement - ALign text, left, right, or center.
	//   newLine        - true = end with carriage return, false = no carriage return.
	//
	// Description:
	//   Send a text string to the console.
	//-------------------------------------------------------------------------
			
	public void textOut ( String text, int indent, TextAlignment textAlignment, boolean newLine )
	{
		// Perform substitutions.
		// We do this for readability in order to have neater looking calculations.
		
		int textLength   = text.length ();
		int consoleWidth = this.consoleWidth;
		
		
		// Calculate text alignment factors
		
		int leftAlign   = indent;
		int rightAlign  = consoleWidth - textLength - indent;
		int centerAlign = ( consoleWidth / 2 ) - ( textLength / 2 ); 
		

		// Apply text alignment
		
		if      ( textAlignment == TextAlignment.left   ) whiteSpace ( leftAlign   );
		else if ( textAlignment == TextAlignment.right  ) whiteSpace ( rightAlign  );
		else if ( textAlignment == TextAlignment.center ) whiteSpace ( centerAlign );
		
		
		// Send text to the console.
		
		System.out.print ( text );
		
		
		// End with a carriage return if specified through newLine.
		
		if ( newLine == true ) newLine ();
	}
	
//-------------------------------------------------------------------------
	
	public void textOut ( String text, int indent, boolean newLine )
	{
		textOut ( text, indent, this.textAlignment, newLine );
	}
	
	//-------------------------------------------------------------------------
	
	public void textOut ( String text, int indent )
	{
		textOut ( text, indent, this.textAlignment, this.newLine );
	}
	
	//-------------------------------------------------------------------------
	
	public void textOut ( String text, boolean newLine )
	{
		textOut ( text, this.indent, this.textAlignment, newLine );
	}	
	
	//-------------------------------------------------------------------------
	
	public void textOut ( String text )
	{
		textOut ( text, this.indent, this.textAlignment, this.newLine );
	}
	
	//-------------------------------------------------------------------------
	// Method: newLine
	//
	// Description:
	//   Send a carriage return to the console.   
	//-------------------------------------------------------------------------
	
	public void newLine ()
	{
		System.out.println ();
	}
	
	//-------------------------------------------------------------------------
	// Method: whiteSpace
	//
	// Arguments:
	//   length - The number of characters to write to the console by.
	//
	// Description:
	//   Sends 'length' number of white space characters to the console.
	//-------------------------------------------------------------------------
	
	public void whiteSpace ( int length )
	{
		// Sends 'length' number of white space characters to the console.
		
		for ( int i=0; i < length; i++ )
		{
			System.out.print ( ' ' );
		}
	}
	
	//-------------------------------------------------------------------------
	// Method: indent
	//
	// Arguments:
	//   indent - The number of characters to indent by.
	//
	// Description:
	//   - indent (int indent) : Forces an implicate indents by 'indent' whitespace characters.
	//   - indent ()           : Indents by 'this.indent' whitespace characters.
	//-------------------------------------------------------------------------
	
	public void indent ( int indent )
	{
		// Update indent
		
		this.indent += indent;
		
		// Push current indent increment
		
		this.indentStack.push ( indent );
	}
	
	//-------------------------------------------------------------------------
	
	public void indent ()
	{		
		// Update indent
		
		this.indent += this.tabSize;
		
		// Push current indent increment
		
		this.indentStack.push ( this.tabSize );
	}
	
	//-------------------------------------------------------------------------
	// Method: outdent
	//
	// Description:
	//   Outdents a text line by 'tabSize' number of characters.
	//-------------------------------------------------------------------------
	
	public void outdent ()
	{
		// Pop last indent from the indent stack
		
		int lastIndent = this.indentStack.pop ();
		
		
		// If the new indent is'nt going to result in a negative value then do 
		// the indent. Otherwise just set this.indent = 0.
		
		if ( this.indent - lastIndent >= 0 )		
		{
			this.indent -= lastIndent;
		}
		else
		{
			this.indent = 0;
		}
	}
	
	//-------------------------------------------------------------------------
	// Method: line
	//
	// Arguments:
	//   ch     - Character to use when drawing the line.
	//   length - The length of the line.
	//   newLine - true = end with carriage return, false = no carriage return.
	//
	// Description:
	//   Draws a line across the console.
	//-------------------------------------------------------------------------

	public void drawLine ( char ch, int length, boolean newLine )
	{
		// Draw line
		
		for ( int i=0; i<length; i++ )
		{
			System.out.print ( ch );
		}
		
		// Add Carriage return if specified by newLine
		
		if ( newLine == true )
		{
			System.out.println ();
		}
	}
	
	//-------------------------------------------------------------------------
	
	public void drawLine ( int length, boolean newLine )
	{
		drawLine ( this.lineCharacter, length, newLine );
	}
	
	//-------------------------------------------------------------------------

	public void drawLine ( char ch, int length )
	{
		drawLine ( ch, length, true );
	}
	
	//-------------------------------------------------------------------------

	public void drawLine ( char ch )
	{
		drawLine ( ch, this.consoleWidth, true );
	}
	
	//-------------------------------------------------------------------------

	public void drawLine ( int length )
	{
		drawLine ( this.lineCharacter, length, true );
	}
	
	//-------------------------------------------------------------------------

	public void drawLine ()
	{
		drawLine ( this.lineCharacter, this.consoleWidth, true );
	}
}
