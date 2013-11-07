package Mobiles;

import Graphics.Pixie;

/**
 * @author Ivan Delgado Huerta
 */

//La clase Mob incluye todas las entidades que se mueven del juego: (Jugadores, NPCs, Proyectiles)
public abstract class Mob 
{
    //Posicion:
    protected float X=0;                    // Coordenadas X:
    protected float Y=0;                    // Coordenadas Y:
    protected float oldPosX;                // Coordenadas X, de la ultima posicion X segura
    protected float oldPosY;                // Coordenadas Y, de la ultima posicion Y segura
    
    //Velocidad y Direccion:
    protected double velocidadMod=1;        // Modificadores de Velocidad: debido a Snares, a Sprints, Roots
    protected double velocidadMax;          // Velocidad Maxima:
    protected double velocidad;             // Velocidad Actual:
    protected double direccion;             // Direccion Actual en Radianes
    
    //Animaciones
    protected Pixie cuerpo;                 // Pixie que contiene todos los graficos con las animaciones
    
    //SET:
    public void setX (float x)              { X = x; }
    public void setY (float y)              { Y = y; }
    public void setVelocidadMax (double v)  { velocidadMax = v; }
    public void setVelocidad (double v)     { velocidad = v; }
    public void setDireccion (double d)     { direccion = d; }
    
    //GET:
    public int getX()                     { return (int)X; }
    public int getY()                     { return (int)Y; }
}
