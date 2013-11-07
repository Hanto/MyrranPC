package Mobiles;
import Graphics.PixiePersonaje;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author Ivan Delgado Huerta
 */

// PC incluye aquellos personajes controlados por el jugador
public class PC extends Personaje
{
    protected int connectionID;                         //ID de la conexion con el servidor
    protected PixiePersonaje pixie;                     //PIXIE Actor que contiene al personaje Jugador, su equipo y todas sus animaciones, lo usamos para modificar sus atributos
    protected Group GroupPixie = new Group();           //El mismo Pixie dentro de un Grupo para que sea Escalable, zoomable y Rotable, lo usamos para dibujarlo
    
    public int getConnectionID()                    { return connectionID; }
    public PixiePersonaje getPixie ()               { return pixie; }
    public Group getGroupPixie()                    { return GroupPixie; }
    
        
    public PC (int numRaza)
    {
        pixie = new PixiePersonaje(numRaza);
        GroupPixie.addActor(pixie);
    }
}
