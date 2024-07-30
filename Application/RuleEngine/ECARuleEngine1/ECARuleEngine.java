
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//------------------------------------------------------------------------------------------------------------------------------------------------------------
/**
 * ECARuleEngine is a class that implements an Event-Condition-Action rule engine.
 * 
 * Rules are specified in a CSV file.
 * 
 * - Columns:
 *   - rule_name           ...The name of the rule that is triggered by an event. The rule name serves the dule role of being both the first condition, and the 
 *   - condition_name_1,      identifier for the set of conditions. 
 *   - condition_name_2,
 *   - condition_name_3,
 *   - ...
 *   - condition_name_N,    ...Where N is the number of conditions. The rule name is effectively also a condition, in which case N+1 is the effective condition count. 
 *   - action_name_1,
 *   - action_name_2,
 *   - action_name_3,
 *   - ...
 *   - action_name_M,       ...Where M is the number of actions.
 * 
 * - All values for the rule name, conditions, and actions are all free format strings.
 * 
 * - A wild card may be used to specify that a column is ignored for a particular rule, as identified by the rule name.
 *   - By default the wild card is the "_" character. 
 * 
 */
//------------------------------------------------------------------------------------------------------------------------------------------------------------

public class ECARuleEngine
{
    // Member variable. 

    private List <String []> rules;             // List to store the rules from the CSV file.
    private int              conditionCount;    // Number of condition columns in the CSV.
    private int              actionCount;       // Number of action columns in the CSV.

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    // Accessors and Mutators.
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public int getConditionCount () { return this.conditionCount; }
    public int getActionCount    () { return this.actionCount;    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor to initialize the rule engine.
     * 
     * @precondition None.
     * @postcondition Initializes the rules list, conditionCount, and actionCount to 0.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public ECARuleEngine ()
    {
        this.rules          = new ArrayList <> ();
        this.conditionCount = 0;
        this.actionCount    = 0;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Loads rules from a CSV file.
     * 
     * @precondition filePath is a valid path to a CSV file with a header and rules.
     * @postcondition Populates the rules list with rules from the CSV file and sets conditionCount and actionCount.
     * @param filePath the path to the CSV file.
     * @throws IOException if an I/O error occurs.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public void loadRules ( String filePath ) throws IOException
    {
        try ( BufferedReader br = new BufferedReader ( new FileReader ( filePath ) ) )
        {
            String line;

            // Read the header line and count the number of conditions and actions.

            if ( ( line = br.readLine () ) != null )
            {
                countConditionsAndActions ( line );
            }

            // Read the rest of the file. 
            
            while ( ( line = br.readLine () ) != null )
            {
                String [] rule = line.split ( "," );
                this.rules.add ( rule );
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Counts the number of condition and action columns in the header line.
     * 
     * @precondition headerLine is a valid CSV header line.
     * @postcondition Sets conditionCount and actionCount based on the header line.
     * @param headerLine the header line from the CSV file.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    private void countConditionsAndActions ( String headerLine )
    {
        String [] headers = headerLine.split ( "," );

        for ( String header : headers )
        {
            if ( header.toLowerCase ().contains ( "condition" ) )
            {
                conditionCount++;
            }
            else if ( header.toLowerCase ().contains ( "action" ) )
            {
                actionCount++;
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Queries the rule engine with the given conditions.
     * 
     * @precondition conditions is a valid array of conditions to match against the rules.
     * @postcondition Returns an array of actions if a matching rule is found, otherwise an empty array.
     * @param conditions the array of conditions to match.
     * @return the array of actions if a matching rule is found, otherwise an empty array.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public String [] queryRule ( String [] conditions )
    {
        for ( String [] rule : this.rules )
        {
            if ( matchesConditions ( rule, conditions ) )
            {
                return extractActions ( rule );
            }
        }

        return new String [ 0 ];  // No matching rule found
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Checks if the given rule matches the provided conditions.
     * 
     * @precondition rule and conditions are valid arrays of the same length.
     * @postcondition Returns true if the rule matches the conditions, false otherwise.
     * @param rule the rule to check.
     * @param conditions the conditions to match against.
     * @return true if the rule matches the conditions, false otherwise.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    private boolean matchesConditions ( String [] rule, String [] conditions )
    {
        for ( int i = 0; i < conditions.length; i++ )
        {
            if ( !rule [ i ].equals ( "_" ) && !rule [ i ].equals ( conditions [ i ] ) )
            {
                return false;
            }
        }

        return true;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Extracts the actions from the given rule.
     * 
     * @precondition rule is a valid array with at least actionCount elements.
     * @postcondition Returns an array of actions extracted from the rule.
     * @param rule the rule to extract actions from.
     * @return an array of actions
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    private String [] extractActions ( String[] rule )
    {        
        String [] actions = new String [ this.actionCount ];

        System.arraycopy ( rule, rule.length - this.actionCount, actions, 0, this.actionCount );

        return actions;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Converts the array of actions to a comma-separated string.
     * 
     * @precondition actions is a valid array of strings.
     * @postcondition Returns a comma-separated string of actions.
     * @param actions the array of actions to convert.
     * @return a comma-separated string of actions.
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    public String actionsToString ( String [] actions )
    {
        String action_string = String.join ( ", ", actions );
        
        return action_string;
    }
}
