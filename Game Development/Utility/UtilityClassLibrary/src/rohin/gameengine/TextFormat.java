package rohin.gameengine;

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// TextFormat:
//
// A suite of various text formatting and manipulation functions.
//
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
//
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class TextFormat
{
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // DATA TYPES
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static enum TextJustification
    {
        CENTER, LEFT, RIGHT
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTANTS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // String formatting

                                    private static final String M_EMPTY         = "";
    @ SuppressWarnings ( "unused" ) private static final String M_SPACE         = " ";
    @ SuppressWarnings ( "unused" ) private static final String M_TAB           = "\t";
                                    private static final String M_NEW_LINE      = "\n";
    @ SuppressWarnings ( "unused" ) private static final String M_START_STRING  = "%";
    @ SuppressWarnings ( "unused" ) private static final String M_END_STRING    = "s";
    @ SuppressWarnings ( "unused" ) private static final String M_LEFT_JUSTIFY  = "-";
    @ SuppressWarnings ( "unused" ) private static final String M_RIGHT_JUSTIFY = "";

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // FIELDS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // METHODS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 1
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public TextFormat ()
    {
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // @formatter:off
    //
    // Pad a string with white space, and right align.
    //
    // String format = "%1$<length>s"
    // where <length> = maximum length of white space.
    //
    // "%"       = Start string format
    // "1$"      = Select argument 1, the optional string to display in this case. Optional when only using 1 argument.
    // <length>  = The maximum length of the string, after white space has been added.
    // "-" or "" = Alignment specifier. "-" = Left align, empty string "" = right align.
    // "s"       = End string format, and specify the argument string to be of type String.
    //
    // Examples: "12345678"
    //                                  ↓↓↓↓↓↓↓↓ 
    // e.g. ( "%1$8s-", "XYZ"      ) = "XYZ     "
    // e.g. ( "%1$8s",  "XYZ"      ) = "     XYZ"
    // e.g. ( "%8s",    "XYZ"      ) = "     XYZ" ...Optional argument specifier omitted for the sake of example.
    // e.g. ( "%1$4s,   "ABCDEFGH" ) = "ABCD"
    // e.g. ( "%1$4s-,  "ABCDEFGH" ) = "ABCD"
    //
    //
    // string - The string to pad.
    // length - The string width to pad up to.
    //
    // return - The padded string.
    //
    // @formatter:on
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static String padRight ( String string, int length )
    {
        return String.format ( "%1$" + length + "s", string );
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // @formatter:off
    //
    // Pad a string with white space, and left align.
    //
    // String format = "%1$<length>s" where <length> = maximum length of white space.
    //
    // "%"       = Start string format
    // "1$"      = Select argument 1, the optional string to display in this case. Optional when only using 1 argument.
    // <length>  = The maximum length of the string, after white space has been added.
    // "-" or "" = Alignment specifier. "-" = Left align, empty string "" = right align.
    // "s"       = End string format, and specify the argument string to be of type String.
    //
    // Examples: "12345678"
    //                                  ↓↓↓↓↓↓↓↓
    // e.g. ( "%1$8s-", "XYZ"      ) = "XYZ     "
    // e.g. ( "%1$8s",  "XYZ"      ) = "     XYZ"
    // e.g. ( "%8s",    "XYZ"      ) = "     XYZ" ...Optional argument specifier omitted for the sake of example.
    // e.g. ( "%1$4s,   "ABCDEFGH" ) = "ABCD"
    // e.g. ( "%1$4s-,  "ABCDEFGH" ) = "ABCD"
    //
    //
    // s      - The string to pad.
    // length - The string width to pad up to.
    //
    // return - The padded string.
    //
    // @formatter:on
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static String padLeft ( String string, int length )
    {
        return String.format ( "%1$-" + length + "s", string );
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // @formatter:off
    //
    // Pad a string with white space and apply text justification (right, left, or center alignment ).
    // 
    // We will re-use our previously defined padLeft and padRigt to implement this method.
    //
    // string            - The string to pad.
    // paddedLength      - The new string width, including padding characters.
    // textJustification - The text justification, right left, or center.
    //
    // return            - The padded string.
    //
    // @formatter:on
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static String padString ( String string, int paddedLength, TextJustification textJustification )
    {
        // Create a new string to be used to pass back to the caller.

        String justifiedString = new String ();

        // Apply text justification logic.

        switch ( textJustification )
        {
        case CENTER:            
            justifiedString = padLeft ( padRight ( string, paddedLength / 2 + string.length() / 2 ), paddedLength );             
            break;

        case LEFT:
            justifiedString = padLeft ( string, paddedLength );
            break;

        case RIGHT:
            justifiedString = padRight ( string, paddedLength );
            break;
        }

        // REturn the resultant justified text back to the caller.

        return justifiedString;
    }

    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Print a formated exception to the console.
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @ SuppressWarnings ( "unused" )
    public static void printFormattedException ( Exception e, Boolean exitApplication )
    {   
        final String LABEL_EXCEPTION   = "EXCEPTION:";
        final String LABEL_APPLICATION = "APPLICATION:";
        final String LABEL_STACK_TRACE = "STACK TRACE:";
        final String MESSAGE_SHUT_DOWN = "Shutting down application.";
        final String BRACKET_OPEN      = "[";
        final String BRACKET_CLOSE     = "]: ";
        final String NEW_LINE          = "\n";
        final String TAB               = "\t";
        final String TAB_1             = TAB;
        final String TAB_2             = TAB + TAB;
        final String TAB_3             = TAB + TAB + TAB;
        final String TAB_4             = TAB + TAB + TAB + TAB;
        
        System.out.println ( NEW_LINE );
        if ( exitApplication )  System.out.println ( LABEL_APPLICATION + TAB_1 + MESSAGE_SHUT_DOWN );
        System.out.println ( LABEL_EXCEPTION   + TAB_2 + e );            
        System.out.println ( LABEL_EXCEPTION   + TAB_2 + e.getMessage () );
        System.out.print   ( LABEL_STACK_TRACE + TAB_1 );
        
        int index = 0;
        for ( StackTraceElement stackTraceElement : e.getStackTrace () )
        {
            System.out.print   ( BRACKET_OPEN + index + BRACKET_CLOSE );
            System.out.println ( stackTraceElement.toString () );
            System.out.print   ( TAB_4 );
            index++;
        }        
        
        if ( exitApplication ) System.exit (0);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // @formatter:off
    /**
     * Indent a body of text by a fixed amount.
     * <p>
     * The body of text is simply a string, with new line characters separating the individual lines.
     * <P>
     * The actual indent size is specified via the internal private field,
     * {@link fixedWidthTab}.
     * <p>
     * Example:<br>
     * <br>
     * Given the flowing input string.
     * 
     * <pre>
     * &quot;Hello World!\nXYZ\n0123456789\n&quot;
     * </pre>
     * 
     * If this.fixedWidthTab = 0, then the text will not be indented, as shown below.
     * 
     * <pre>
     * │Hello World!
     * │XYZ
     * │0123456789
     * </pre>
     * 
     * If this.fixedWidthTab = 4, for instance, then the text will not be indented 4 spaces, as shown bellow.
     * 
     * <pre>
     * │    Hello World!
     * │    XYZ
     * │    0123456789
     * </pre>
     * 
     * @param message
     *        The input body of text to indent.
     * @param indent
     *        the number of characters to indent by.
     * @return A new indented instance of the input text.
     * 
     * <br><br>
     * @author Rohin Gosling
     * @since  2008-05-20
     */
    // @formatter:on
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public String indentString ( String message, int indent )
    {
        // Create a new string to store our indented output string.

        String indentedMessage = new String ();
        int    indentSize      = indent;

        // Pre-append an indent to the beginning for the string.

        indentedMessage = TextFormat.padLeft ( M_EMPTY, indentSize ) + message;

        // Replace all new line characters, with a new line character + and
        // indent.

        String substitute = M_NEW_LINE + TextFormat.padLeft ( M_EMPTY,    indentSize );
        indentedMessage   = indentedMessage.replaceAll      ( M_NEW_LINE, substitute );

        // Return the indented result to the caller.

        return indentedMessage;
    }    

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OVERRIDEBLE METHODS
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
