package rohin.gameengine.Component;

import java.util.LinkedList;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;

public class ComponentTransform extends ECSComponent
{       
    // Constants.
    
    private static final int PITCH                             = 0;
    private static final int YAW                               = 1;
    private static final int ROLL                              = 2;
    private static final int DEFAULT_TRANSLATION_HISTORY_DEPTH = 16;
    
    // Component attributes.    
    
    public Vector2D              origin;                    // Origin vector      (geometric center).
    public Vector2D              scale;                     // Scaling vector     (size).
    public double[]              rotation;                  // Rotation vector    (orientation). rotation = ( Pitch, Yaw, Roll ).
    public Vector2D              translation;               // Translation vector (position).
    public LinkedList <Vector2D> translationHistory;        // History of where this component has been.
    public int                   translationHistoryDepth;   // Number of translations to stor in the translation history.
    
    // Constructor/s.
    
    public ComponentTransform ()
    {
        initialize
        (
            new Vector2D ( 0.0, 0.0 ),      // Origin.
            new Vector2D ( 0.0, 0.0 ),      // Scale.
            new double[] { 0.0, 0.0, 0.0 }, // Rotation.
            new Vector2D ( 0.0, 0.0 )       // Translation.
        );
    }
    
    public ComponentTransform ( Vector2D origin, Vector2D scale, double[] rotation, Vector2D translation )
    {
        initialize ( origin, scale, rotation, translation );
    }
    
    // Component initializer.
    
    private void initialize ( Vector2D origin, Vector2D scale, double[] rotation, Vector2D translation )
    {
        // Initialize transform attributes.
        
        this.origin      = new Vector2D ( origin );
        this.scale       = new Vector2D ( scale  );
        this.rotation    = new double[] { rotation [ PITCH ], rotation [ YAW ], rotation [ ROLL ] };
        this.translation = new Vector2D ( translation );
        
        // Initialize translation history.
        
        this.translationHistory      = new LinkedList <Vector2D> ();
        this.translationHistoryDepth = DEFAULT_TRANSLATION_HISTORY_DEPTH; 
    }
    
    // Add a translation record to the translation history.
    
    public void saveTranslation ( Vector2D v )
    {
        // If we have reached our history limit, then delete the last entry to make space for the new entry, that we shall add to the front of the list.
        
        if ( this.translationHistory.size () >= this.translationHistoryDepth )
        {
            this.translationHistory.removeFirst ();
        }
        
        // Save the latest translation to the translation history.
        
        this.translationHistory.addLast ( v );        
    }
}
