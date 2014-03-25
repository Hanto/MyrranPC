package Actores;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author Ivan Delgado Huerta
 */

//La clase Mob incluye todas las entidades que se mueven del juego: (Jugadores, NPCs, Proyectiles)
public abstract class Mob
{
    //Posicion:
    protected float x;                      // Coordenadas X:
    protected float y;                      // Coordenadas Y:
    protected float oldPosX;                // Coordenadas X, de la ultima posicion X segura
    protected float oldPosY;                // Coordenadas Y, de la ultima posicion Y segura
    
    //Velocidad y Direccion:
    protected double velocidadMod=1;        // Modificadores de Velocidad: debido a Snares, a Sprints, Roots
    protected double velocidadMax;          // Velocidad Maxima:
    protected double velocidad;             // Velocidad Actual:
    protected double direccion;             // Direccion Actual en Radianes
    
    //Actor que representa el Mobile
    protected Group actor = new Group();
    
    //No tenemos metodos para alterar la posicion X,Y, ya que de estos se encargaran las clases hijo que tienen actores a su cargo
    //puesto que dichos metodos deben alterar las coordenadas X,Y tanto de la entidad como del actor, y es mejor que este todo en un
    //solo sitio para no olvidarnos uno del otro
    
    //SET:
    public void setVelocidadMax (double v)  { velocidadMax = v; }
    public void setVelocidad (double v)     { velocidad = v; }
    public void setDireccion (double d)     { direccion = d; }
    
    //GET:
    public int getX()                       { return (int)x; }
    public int getY()                       { return (int)y; }
    public Group getActor()                 { return actor; }
}
