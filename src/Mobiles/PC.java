package Mobiles;
import Graficos.Nameplate;
import Graficos.PixiePC;
import Graficos.Recursos;
import Graficos.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
//* @author Ivan Delgado Huerta

// PC incluye aquellos personajes controlados por el jugador
public class PC extends Personaje
{
    protected int connectionID;                     //ID de la conexion con el servidor
    protected int raza;                             //id de la raza;
    protected PixiePC pixiePC;                      //PIXIE Actor que contiene al personaje Jugador, su equipo y todas sus animaciones, lo usamos para modificar sus atributos
    
    public int getConnectionID()                    { return connectionID; }
    public int getRaza ()                           { return raza; }
    public PixiePC getPixiePC ()                    { return pixiePC; }
            
    public PC (int numRaza, String nombre)
    {
        this.nombre = nombre;
        raza = numRaza;
        pixiePC = new PixiePC(numRaza, this);
        nameplate = new Nameplate(((Personaje)this));
        
        nameplate.setPosition(pixiePC.getWidth()/2-nameplate.getWidth()/2, pixiePC.getHeight()+2);
        Texto.printTexto(nombre, Recursos.font14, Color.WHITE, Color.BLACK, pixiePC.getWidth()/2, pixiePC.getHeight()+2+10, Align.center, Align.bottom, 1, actor );
        
        actor.addActor(pixiePC);
        actor.addActor(nameplate);
    }
}
