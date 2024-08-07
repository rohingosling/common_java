package rohin.gameengine.test;

public class TestEngine
{
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static final int M_DEFAULT_A       = 0;
    private static final int M_DEFAULT_B       = 10;
    private static final int M_DEFAULT_TRIGGER = 5;
 
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private int a;
    private int b;
    private int trigger;
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public int  geta ()        { return this.a; }
    public void seta ( int a ) { this.a = a; }
    
    public int  getb ()        { return this.b; }
    public void setb ( int b ) { this.b = b; }
    
    public int  gettrigger ()              { return this.trigger; }
    public void settrigger ( int trigger ) { this.trigger = trigger; }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public TestEngine ()
    {        
        initialize
        (
            M_DEFAULT_A,
            M_DEFAULT_B,
            M_DEFAULT_TRIGGER
        );        
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public TestEngine ( int a, int b, int trigger )
    {        
        initialize ( a, b, trigger );        
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void initialize ( int a, int b, int trigger )
    {
        this.a       = a;
        this.b       = b;
        this.trigger = trigger;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void run()
    {
        int a = this.a;
        int b = this.b;
        int t = this.trigger;
        
        for ( int x = a; x <= b; x++ )
        {
            if ( x < t )
            {
                System.out.println ( "x = " + x + ",\tx < " + t );
            }
            else if ( x > t )
            {
                System.out.println ( "x = " + x + ",\tx > " + t );
            }
            else
            {
                System.out.print ( "x = " + x + ",\tx = " + t );
                onTrigger ();
            }
        }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void onTrigger ()
    {        
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
