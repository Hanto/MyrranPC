package Geo;

import Constantes.MiscData;
import static Recursos.Recursos.atlas;
import Main.Mundo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Ivan Delgado Huerta
 */

//Clase que define los distintos tipos de terreno
public class Terreno 
{
    private int id;                                 //ID del tipo de Terreno para poder cargar su template
    private String nombre;                          //Nombre del Terreno ("Jungla", "Camino", "Cesped"...)
    private Color color;                            //Color que se le asigna en su representacion en el minimapa
    private TextureRegion textura;                  //Textura que contiene el grafico del terreno
    private boolean isSolido = false;               //Flag que controla si el terreno es solido o no, y por tanto atravesable por los objetos que pueblan el mundo
    
    //SET:
    public void setNombre (String s)                { nombre = s; }
    public void setColor (Color c)                  { color = c; }
    public void setTextura (String nombreTextura)   { textura = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+nombreTextura)); }
    public void setIsSolido (boolean b)             { isSolido = b; }
    //GET:
    public int getID()                              { return id; }
    public String getNombre()                       { return nombre; }
    public Color getColor()                         { return color; }
    public TextureRegion getTextura()               { return textura; }
    public boolean getIsSolido()                    { return isSolido; }
        
    public Terreno ()
    {   //Si no hay ningun terreno creado su ID es el 0, si ya existe, cogemos el ultimo ID de la lista y le asignamos el siguiente
        if (Mundo.listaDeTerrenos.size == 0) id = 0;
        else {id = Mundo.listaDeTerrenos.peek().id+1;}
        nombre = MiscData.TERRENO_Nombre_Nuevo+"_"+id;
        isSolido = false;
    }
}
