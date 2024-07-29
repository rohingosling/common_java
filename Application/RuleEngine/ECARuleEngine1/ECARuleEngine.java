import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ECARuleEngine
{
    // Member variable. 

    private List <String []> rules;
    private int              conditionCount;
    private int              actionCount;

    // Accessors and Mutators.

    public int getConditionCount () { return conditionCount; }
    public int getActionCount ()    { return actionCount; }

    // Constructor. 

    public ECARuleEngine ()
    {
        this.rules          = new ArrayList <> ();
        this.conditionCount = 0;
        this.actionCount    = 0;
    }

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

    private String [] extractActions ( String[] rule )
    {        
        String [] actions = new String [ this.actionCount ];

        System.arraycopy ( rule, rule.length - this.actionCount, actions, 0, this.actionCount );

        return actions;
    }

    public String actionsToString ( String [] actions )
    {
        String action_string = String.join ( ", ", actions );
        
        return action_string;
    }
}
