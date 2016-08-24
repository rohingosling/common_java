package rohin.gameengine;

// ECSComponent should be extended to implement a specific data object. 

public class ECSComponent extends ECSObject
{
    // @formatter:off
    
    // Constants.
    
    private static final Integer M_DEFAULT_ID     = 0;
    private static final String  M_DEFAULT_NAME   = "COMPONENT";
    private static final Integer M_DEFAULT_FAMILY = 0;
   
    // Constructors
    
    public ECSComponent ()                                                           { Initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( Integer id )                                               { Initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( ECSEngine owner )                                          { Initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, ECSEngine owner )                              { Initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, String name )                                  { Initialize ( id,           name,           M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( Integer id, Integer family )                               { Initialize ( id,           M_DEFAULT_NAME, family,           null  ); }
    public ECSComponent ( Integer id, Integer family, ECSEngine owner )              { Initialize ( id,           M_DEFAULT_NAME, family,           owner ); }
    public ECSComponent ( Integer id, String name, ECSEngine owner )                 { Initialize ( id,           name,           M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, String name, Integer family, ECSEngine owner ) { Initialize ( id,           name,           family,           owner ); }
    
    // Function: Initialize.
    
    private void Initialize ( Integer id, String name, Integer family, ECSEngine owner )
    {
        this.id     = id;
        this.name   = name;
        this.family = family;
        this.owner  = owner;
    }
    
    // @formatter:on
}
