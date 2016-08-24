package rohin.gameengine;

import java.util.*;

public class ECSEntity extends ECSObject
{    
    // @formatter:off
    
    // Constants.
    
    static final ECSEngine M_DEFAULT_OWNER         = null;
    static final Integer   M_DEFAULT_ID            = 0;
    static final String    M_DEFAULT_NAME          = "ENTITY";    
    static final Boolean   M_DEFAULT_ENABLED       = true;
    static final int       M_DEFAULT_HASH_MAP_SIZE = 8;
    
    // Fields.
                
    private HashMap <Integer, ECSComponent> components;
    private Boolean                         enabled;
        
    // Accessors and mutators.
    
    public HashMap <Integer, ECSComponent> getComponents () { return this.components; }
    public Boolean                         isEnabled     () { return this.enabled;    }
    
    public void setEnabled ( Boolean enabled ) { this.enabled = enabled; }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Constructor/s.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSEntity ()                                           { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id )                               { initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_OWNER  ); }
    public ECSEntity ( ECSEngine owner )                          { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, owner            ); }
    public ECSEntity ( String name )                              { initialize ( M_DEFAULT_ID, name,           M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id, String name )                  { initialize ( id,           name,           M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id, ECSEngine owner )              { initialize ( id,           M_DEFAULT_NAME, owner            ); }
    public ECSEntity ( Integer id, String name, ECSEngine owner ) { initialize ( id,           name,           owner            ); }    
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Function: Initialize.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void initialize ( Integer id, String name, ECSEngine owner )
    {
        this.owner      = owner;
        this.id         = id;
        this.name       = name;                
        this.components = new HashMap <Integer, ECSComponent> ( M_DEFAULT_HASH_MAP_SIZE );
        this.enabled    = M_DEFAULT_ENABLED;   
    }
    
    // @formatter:on
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adds a components to the component list.
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void addComponent ( Integer key, ECSComponent component )
    {   
        this.components.put ( key, component );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetComponent
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public ECSComponent getComponent ( int index )    
    {   
        return this.components.get ( index );
    }
    
    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // GetComponents
    // 
    // Query whether or not an entity has a specified set of components.
    // This is used by systems, to automatically detect whether or not they are able to operate on a particular type of entity.
    //
    // Arguments:
    //
    // - ECSComponent...systemComponents
    //
    //   Variable argument list, specifying the system components our system is interested in working with.
    //        
    // Return Value:
    //
    // - returns true, if every component passed in through the variable argument list 'systemComponents', has been matched by components in 
    //   this entities components list.
    //
    // - Returns false in the event that one or more components passed in through the variable argument list 'systemComponents', have not been matched
    //   by components from this entities components list.
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    public Boolean hasComponents ( ECSComponent... systemComponents )    
    {   
        Collection <ECSComponent> entityComponents = this.components.values ();
        int                       componentCount   = 0;
        
        try
        {        
            // for: Iterate through all components passed in through the variable argument list.
            //   for: Iterate through all components in this entity.
            //     if else: Compare each component from this entity, with each component passed in through the variable argument list.
            //              - If we find a match, then increment the component count.
            //              - Else just ignore this component. 

            for ( ECSComponent systemComponent : systemComponents )
            {
                for ( ECSComponent entityComponent : entityComponents )
                {   
                    if ( entityComponent.getClass () == systemComponent.getClass () )
                    {                        
                        componentCount++;
                    }
                }
            }        
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
        
        // If we have matched all input components with components from the entity, then return true.
        // Else, if we never collected enough components, then return false.
        
        return ( componentCount == systemComponents.length ) ? true : false;
    }     
}
