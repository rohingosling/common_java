package rohin.gameengine;

import rohin.gameengine.OldGameEngine;

// /////////////////////////////////////////////////////////////////////////////
// 
// Menu system.
// 
// Author:  Rohin Gosling
// Version: 1.0
// Since:   2014-04-18
// 
// /////////////////////////////////////////////////////////////////////////////

public class ECSMenuSystem extends OldGameEngine
{
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 1
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSMenuSystem ()
    {
        super();
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 2
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSMenuSystem ( int id )
    {
        super ( id );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 3
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSMenuSystem ( String name )
    {
        super ( name );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 4
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSMenuSystem ( int id, String name )
    {
        super ( id, name );
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Constructor 5
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ECSMenuSystem ( int id, String name, String text )
    {
        super ( id, name, text );
    }
}