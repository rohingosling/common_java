package rohin.gameengine.Component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import rohin.gameengine.ECSComponent;

public class ComponentGeometry extends ECSComponent
{
    public List <ComponentShape> shapes;
    public Color                 color;
    
    public ComponentGeometry ()
    {
        this.shapes = new ArrayList <ComponentShape> ();
        this.color  = Color.GREEN;
    }
}
