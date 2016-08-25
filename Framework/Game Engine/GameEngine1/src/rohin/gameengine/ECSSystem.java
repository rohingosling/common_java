package rohin.gameengine;

public abstract class ECSSystem extends ECSObject
{
    // @formatter:off
    
    // Constants
    
    private final static ECSEngine M_DEFAULT_OWNER   = null;
    private final static Integer   M_DEFAULT_ID      = 0;
    private final static String    M_DEFAULT_NAME    = "SYSTEM";    
    private final static Boolean   M_DEFAULT_ENABLED = true;
    
    // Fields.
    
    protected Boolean enabled;
        
    // Accessors and mutators.
    
    public Boolean isEnabled () { return this.enabled; }
    
    public void setEnabled ( Boolean enabled ) { this.enabled = enabled; }
    
    // Abstract method decelerations.
    
    public abstract void update ( long t );     // Override in child class to implement update functionality. t = time elapsed during last loop iteration.
    
    // Constructors
    
    public ECSSystem ()                                           { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_OWNER ); }
    public ECSSystem ( Integer id )                               { initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_OWNER ); }
    public ECSSystem ( String name )                              { initialize ( M_DEFAULT_ID, name,           M_DEFAULT_OWNER ); }
    public ECSSystem ( ECSEngine owner )                          { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, owner           ); }
    public ECSSystem ( Integer id, String name )                  { initialize ( id,           name,           M_DEFAULT_OWNER ); }
    public ECSSystem ( Integer id, ECSEngine owner )              { initialize ( id,           M_DEFAULT_NAME, owner           ); }
    public ECSSystem ( Integer id, String name, ECSEngine owner ) { initialize ( id,           name,           owner           ); }
        
    // Methods
    
    // Function: Initialize.
    
    private void initialize ( Integer id, String name, ECSEngine owner )
    {
        this.owner   = owner;
        this.id      = id;
        this.name    = name;                
        this.enabled = M_DEFAULT_ENABLED;
    }
    
    // @formatter:on
}
