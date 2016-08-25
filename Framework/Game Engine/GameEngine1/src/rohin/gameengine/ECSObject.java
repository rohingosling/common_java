package rohin.gameengine;

public class ECSObject
{   
    // Fields
    
    protected Integer id;           // Object ID. Should be unique.
    protected String  name;         // Internal fileName. e.g. "SOME_GAME_OBJECT".
    protected Integer family;       // The family ID is used for hierarchical association of entities.     
    protected Object  owner;        // Handle to the objects owner.
    
    // Accessors and mutators.
    
    public long   getId     () { return this.id;     }
    public String getName   () { return this.name;   }
    public long   getFamily () { return this.family; }
    public Object getOwner  () { return this.owner;  }
    
    public void setId     ( Integer id     ) { this.id     = id;     }
    public void setName   ( String  name   ) { this.name   = name;   }
    public void setFamily ( Integer family ) { this.family = family; }
    public void setOwner  ( Object  owner  ) { this.owner  = owner;  }
    
    // Generate a text representation of the object. 
    
    public String toString()
    {        
        return "[" + this.family + "." + this.id  + "] " + this.name;
    }
}
