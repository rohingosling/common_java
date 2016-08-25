package rohin.gameengine;

public class Resource
{
    //@formatter:off
    
    // Constants.
    
    protected final static Object M_DEFAULT_OBJECT    = null;
    protected final static String M_DEFAULT_FILE_NAME = null;
    
    // Fields.
    
    protected Object object;
    protected String fileName;
    
    // Accessors and mutators.
    
    public Object getObject   () { return this.object;   }
    public String getFileName () { return this.fileName; }
    
    public void setObject   ( Object object   ) { this.object   = object;   }
    public void setFileName ( String fileName ) { this.fileName = fileName; }
    
    // Constructor/s.
        
    public Resource ()                                 { initialize ( M_DEFAULT_FILE_NAME, M_DEFAULT_OBJECT ); }
    public Resource ( String fileName )                { initialize ( fileName,            M_DEFAULT_OBJECT ); }    
    public Resource ( String fileName, Object object ) { initialize ( fileName,            object           ); }
    
    // Methods.
    
    // Initialization.
    
    private void initialize ( String fileName, Object object )
    {   
        this.object   = object;
        this.fileName = fileName;
    }
    
    // Load a resource. Should be overridden in the implementing class.
    
    public void load ()
    {        
    }
    
    // Unload a resource. Should be overridden in the implementing class.
    
    public void unload ()
    {        
    }
    
    //@formatter:on    
}
