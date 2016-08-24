package rohin.gameengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

// /////////////////////////////////////////////////////////////////////////////
/**
* StringTable:
* <p>
* The StringTable class implements a multi-language key based string mapping
* table. Support for hot keys associated with particular strings is also 
* supported.
* <p>
* Strings are loaded in from an XML file. To support multiple languages, one
* creates a file for each language, using the same string ID's for each equivalent string.
* An application then simply loads in the file for the language it wishes to use.
* <p>
* Example:
* <p>
* File: string_table_english.xml
* <pre>
* {@code
* 
* <?xml version="1.0" encoding="UTF-8"?>
* <StringTable language="English" languageNativeName="English" >
*
*     <String id="IDS_YES"         value="Yes"          hotkey="Y" />
*     <String id="IDS_NO"          value="No"           hotkey="N" />
*     <String id="IDS_HELLO_WORLD" value="Hello World!" hotkey=""  />
*         
* </StringTable>
*     
* }
* </pre>
* 
* File: string_table_klingon.xml
* <pre>
* {@code
* 
* <?xml version="1.0" encoding="UTF-8"?>
* <StringTable language="English" languageNativeName="English" >
*    
*     <String id="IDS_YES"              value="HIja"      hotkey="H" />
*     <String id="IDS_NO"               value="Qo"        hotkey="Q" />
*     <String id="IDS_TEST_HELLO_WORLD" value="qo' vIvan" hotkey=""  />
*
* </StringTable>
* }
* </pre>
* 
* @author      Rohin Gosling
* @version     1.0
* @since   2014-04-18
*/
// /////////////////////////////////////////////////////////////////////////////

public class StringTable
{    
    //@formatter:off
    
    // /////////////////////////////////////////////////////////////////////////
    //
    // Constants
    //
    // /////////////////////////////////////////////////////////////////////////
 
    static final String XML_TAG_STRING_TABLE                            = "StringTable";
    static final String XML_ATTRIBUTE_STRING_TABLE_LANGUAGE_NATIVE_NAME = "languageNativeName";
    static final String XML_ATTRIBUTE_STRING_TABLE_LANGUAGE             = "language";
    static final String XML_TAG_STRING                                  = "String";
    static final String XML_ATTRIBUTE_STRING_ID                         = "id";
    static final String XML_ATTRIBUTE_STRING_VALUE                      = "value";
    static final String XML_ATTRIBUTE_STRING_HOT_KEY                    = "hotkey";
 

    // /////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // /////////////////////////////////////////////////////////////////////////

    private String                      fileName;           // The string table file fileName.
    private String                      language;           // Language fileName loaded in with the string table file.
    private String                      languageNativeName; // Native language fileName loaded in with the string table file.    
    private String                      nullString;         // Default string returned, if no string is found in the string table.
    private HashMap < String, String >  stringMap;          // Hash table based string map. Populated from an XML string table file.
    private HashMap < String, String >  hotkeyMap;          // Hash table based hotkey map. Populated from an XML string table file.
    
    // /////////////////////////////////////////////////////////////////////////
    // 
    // ACCESSORS and MUTATORS
    //
    // /////////////////////////////////////////////////////////////////////////
    
    // Accessors 
 
    public String getFileName           () { return this.fileName; }
    public String getLanguage           () { return this.language; }
    public String getLanguageNativeName () { return this.languageNativeName; }
    
    // Mutators
    
    //@formatter:on
    
    // /////////////////////////////////////////////////////////////////////////
    // 
    // METHODS
    //
    // /////////////////////////////////////////////////////////////////////////
    
    // -------------------------------------------------------------------------
    /**
     * Constructor 1
     */
    // -------------------------------------------------------------------------
    
    public StringTable ()
    {
        initialize ();
    }
         
    // -------------------------------------------------------------------------
    /**
     * Clear and reset the string table.
     */
    // -------------------------------------------------------------------------
    
    private void initialize ()
    {
        // Initialize of clear the string map.
         
        if ( this.stringMap == null ) this.stringMap = new HashMap <String, String> ();         
        else                          this.stringMap.clear ();
         
        // Initialize or clear the hot key map.
         
        if ( this.hotkeyMap == null ) this.hotkeyMap = new HashMap <String, String> ();
        else                          this.stringMap.clear ();
        
        // Initialize default values.
        
        this.nullString = "NULL";
    }
     
    // -------------------------------------------------------------------------
    /**
     * Get the hot key for a particular string.
     * 
     * @param ids   The string ID for the string to search for.
     * @return      The hot-key string referenced by the string ID.
     */
    // -------------------------------------------------------------------------
    
    public String getHotkey ( String ids )
    {
        String string = this.hotkeyMap.get ( ids );        // Get the string from the string map.
        if ( string == null ) string = this.nullString;    // If the string can not be found, then return the default null string.
        return string;  
    }
    
    // -------------------------------------------------------------------------
    /**
     * Get the string for a particular string ID.
     * 
     * @param ids   The string ID for the string to search for.
     * @return      The string referenced by the string ID.
     */
    // -------------------------------------------------------------------------
    
    public String getString ( String ids )
    {
        String string = this.stringMap.get ( ids );        // Get the string from the string map.
        if ( string == null ) string = this.nullString;    // If the string can not be found, then return the default null string.
        return string;                                     // Return what we have to the caller.
    }
     
    // -------------------------------------------------------------------------
    /**
     * Retrieve the language from an XML file.
     * 
     * @param xmlEvent  The XML event required to begin searching for the 
     *                  desired XML element.
     */
    // -------------------------------------------------------------------------

    public void xmlGetLanguage ( XMLEvent xmlEvent )
    {
        if ( xmlEvent.isStartElement () )
        {
            // Start parsing the XML file at the first element.
    
            StartElement startElement = xmlEvent.asStartElement ();
    
            // Parse the main StringTable tag.
    
            if ( startElement.getName ().getLocalPart () == XML_TAG_STRING_TABLE )
            {
                // Read the attributes from the StringTable tag, and update the language names.
     
                @SuppressWarnings ( "unchecked" )
                Iterator < Attribute > attributes = startElement.getAttributes();
     
                while ( attributes.hasNext () )
                {
                    Attribute attribute     = attributes.next ();               
                    String    attributeName = attribute.getName ().toString ();
         
                    switch ( attributeName )
                    {
                        // Get the language fileName from the StringTable tag.
     
                        case XML_ATTRIBUTE_STRING_TABLE_LANGUAGE: this.language =  attribute.getValue (); break;
         
                        // Get the native language fileName from the StringTable tag.
             
                        case XML_ATTRIBUTE_STRING_TABLE_LANGUAGE_NATIVE_NAME: this.languageNativeName = attribute.getValue (); break;
         
                    } // switch
         
                } // while
     
            } // if
         
        } // if
    }
    
 
    // -------------------------------------------------------------------------
    /**
     * Retrieve the string ID, value and hot key from an open XML file.
     * 
     * @param xmlEvent  The XML event required to begin searching for the 
     *                  desired XML element.
     */
    // -------------------------------------------------------------------------
     
    public void xmlGetStringData ( XMLEvent xmlEvent )
    {
        if ( xmlEvent.isStartElement () )
        {     
            // Start parsing the XML file at the first element.
    
            StartElement startElement = xmlEvent.asStartElement ();
    
            // Parse the main StringTable tag.
    
            if ( startElement.getName ().getLocalPart () == XML_TAG_STRING )
            {
                // Initialize strings that we will use to collect the attribute values.
     
                String ids    = this.nullString;
                String value  = this.nullString;
                String hotkey = this.nullString;
     
                // Initialize attribute
     
                Attribute attribute     = null;             
                String    attributeName = null;
     
                // Read the attributes from a String tag, and update the string data.
     
                @SuppressWarnings ( "unchecked" )
                Iterator < Attribute > attributes = startElement.getAttributes();
     
                while ( attributes.hasNext () )
                {
                    attribute     = attributes.next ();             
                    attributeName = attribute.getName ().toString ();           
         
                    switch ( attributeName )
                    {
                        case XML_ATTRIBUTE_STRING_ID:      ids    = attribute.getValue (); break;   // Get the ID string.
                        case XML_ATTRIBUTE_STRING_VALUE:   value  = attribute.getValue (); break;   // Get the string.    
                        case XML_ATTRIBUTE_STRING_HOT_KEY: hotkey = attribute.getValue (); break;   // Get the hot-key.              
                    }
                } // while
         
                // Load Hash maps.
     
                if ( attributeName != null)
                {
                    this.stringMap.put ( ids, value  );
                    this.hotkeyMap.put ( ids, hotkey );
                }
            } // if
        } // if
    }

    // -------------------------------------------------------------------------
    /**
     * Load an XML string table.
     * <p>
     * Example XML File:
     * <p>
     * File: string_table_english.xml
     * <pre>
     * {@code
     *  
     *     <?xml version="1.0" encoding="UTF-8"?>
     *     <StringTable language="English" languageNativeName="English" >
     *     
     *         <String id="IDS_YES"         value="Yes"          hotkey="Y" />
     *         <String id="IDS_NO"          value="No"           hotkey="N" />
     *         <String id="IDS_HELLO_WORLD" value="Hello World!" hotkey=""  />
     *         
     *     </StringTable>     
     * }
     * </pre>
     * 
     * @param fileName  File fileName of XML file to open.
     */
    // -------------------------------------------------------------------------
    
    public void loadStringTable ( String fileName )
    {
        // clear the string table.
     
        initialize ();
     
        // Load in the file.
     
        try
        {
            // Create a new XML input factory.
    
            XMLInputFactory inputFactory = XMLInputFactory.newInstance ();
    
            // Setup a new XML event reader.
    
            InputStream    inputStream    = new FileInputStream               ( fileName    );
            XMLEventReader xmlEventReader = inputFactory.createXMLEventReader ( inputStream );
    
            // Get the language fileName out of the StringTable tag.
    
            while ( xmlEventReader.hasNext () )
            {
                XMLEvent xmlEvent = xmlEventReader.nextEvent ();
    
                xmlGetLanguage   ( xmlEvent );
                xmlGetStringData ( xmlEvent );
         
            } // while
            
            // Update the file fileName of the last successfully loaded string table.
            
            this.fileName = fileName;
        }
        catch ( FileNotFoundException e )
        {   
            TextFormat.printFormattedException ( e, true );
        }
        catch ( XMLStreamException e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }
    
    // -------------------------------------------------------------------------
    /**
     *  OVERRIDE: toString
     * 
     *  Overrides the standard java object toString method.
     */
    // -------------------------------------------------------------------------
    
    public String toString ()
    {   
        return "File fileName = " + this.fileName;        
    }

    // /////////////////////////////////////////////////////////////////////////
    // 
    // OVERRIDEBLE METHODS
    //
    // /////////////////////////////////////////////////////////////////////////

}


