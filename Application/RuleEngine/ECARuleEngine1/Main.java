import java.io.IOException;

//------------------------------------------------------------------------------------------------------------------------------------------------------------
/**
 * The Main class is the entry point for the application.
 * - It initializes the rule engine, loads rules from a CSV file, and executes a set of predefined test cases.
 */
//------------------------------------------------------------------------------------------------------------------------------------------------------------

public class Main 
{
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * The main method initializes the rule engine, loads the rule file, and executes predefined test cases.
     *
     * @param args Command-line arguments (not used)
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void main ( String [] args ) 
    {
        try 
        {
            // Initialize local Constants.            

            final String FILE_NAME_RULES     = "test_rules_01.csv";
            final String RULE_NAME_UNDEFINED = "UNDEFINED_RULE";
            final String CONDITION_ANY_VALUE = "X";

            final String [] [] TEST_CASES =
            {
                // Rule Name (Event),  Region,              Issue Type,          Delivery Type

                { "RULE_1",            CONDITION_ANY_VALUE, "NEW",               "BRANCH"            },
                { "RULE_1",            CONDITION_ANY_VALUE, "NEW",               "STANDARD"          },
                { "RULE_1",            CONDITION_ANY_VALUE, "RENEWAL",           "SCHEDULED"         },
                { "RULE_1",            CONDITION_ANY_VALUE, CONDITION_ANY_VALUE, "SCHEDULED"         },
                { "RULE_2",            CONDITION_ANY_VALUE, "RENEWAL",           "BRANCH"            },
                { "RULE_3",            CONDITION_ANY_VALUE, "RENEWAL",           CONDITION_ANY_VALUE },
                { "RULE_3",            "KZN",               "RENEWAL",           "BRANCH"            },
                { "RULE_REGIONAL",     "CAPE_TOWN",         "NEW",               "STANDARD"          },
                { "RULE_REGIONAL",     CONDITION_ANY_VALUE, "NEW",               "STANDARD"          },
                { RULE_NAME_UNDEFINED, "CAPE_TOWN",         "NEW",               "STANDARD"          },
                { "RULE_DEFAULT",      "KZN",               "RENEWAL",           "BRANCH"            },
            };
            
            // Initialize rule engine and load rule file. 
            
            ECARuleEngine ruleEngine = new ECARuleEngine ();
            ruleEngine.loadRules ( FILE_NAME_RULES );

            // Execute test cases.            

            System.out.println ( "\nTest Cases\n" );
            executeTestCases ( TEST_CASES, ruleEngine );
        }
        catch ( IOException e )
        {
            System.err.println ( "Failed to load rules: " + e.getMessage () );
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Executes the given test cases using the specified rule engine.
     *
     * @param testCases 2D array of test cases, where each test case is an array of strings representing the event (rule name) and its conditions.
     * @param ruleEngine The rule engine to be used for querying rules
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    static public void executeTestCases ( String [] [] testCases, ECARuleEngine ruleEngine )
    {
        String [] actions = null;

        for ( String [] event_and_conditions : testCases )
        {
            // Query rule engine. 

            actions = ruleEngine.queryRule ( event_and_conditions );

            // Isolate only the conditions, by removing the event (Rule name) from the `event_and_conditions` array.
            
            String [] conditions = new String [ ruleEngine.getConditionCount () ];
            System.arraycopy ( event_and_conditions, 1, conditions, 0, conditions.length );  

            // Get event name. i.e. Rule name. 

            String eventName    = new String ( event_and_conditions [ 0 ] );
            String actionString = ( ruleEngine.actionsToString ( actions ).length () > 0 ) ? ruleEngine.actionsToString ( actions ) : "...No match found.";

            // Write results to terminal. 

            System.out.println ( "Rule Name (Event): " + eventName );
            System.out.println ( "- Conditions:      " + String.join ( ", ", conditions ) );
            System.out.println ( "- Actions:         " + actionString );
            System.out.println ();
        }
    }
}
