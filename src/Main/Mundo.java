package Main;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Terreno;
import Mobiles.Player;
import Mobiles.Proyectil;
import Skills.Spell;
import TiposSpell.AbstractTipoSpell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
 //* @author Ivan Delgado Huerta
 
public class Mundo 
{
    public static Player player;
        
    public static Array<Spell> listaDeSpells = new Array<>();
    public static Array<AbstractTipoSpell> listaDeTiposSpell = new Array<>();    
    public static Array<Terreno> listaDeTerrenos = new Array<>();
    public static Array<Player> listaDePlayers = new Array<>();
    public static Array<Proyectil> listaDeProyectiles = new Array<>();
        
    public static Celda [][] mapa = new Celda [MiscData.MAPA_Max_X][MiscData.MAPA_Max_Y];
    public static TiledMap tiledMap = new TiledMap();
    public static OrthogonalTiledMapRenderer mapRenderer;
    public static Stage stageMundo;
    
    //Opciones varias:
    public static boolean dibujarNameplatesPlayer = true;
    
    
    public static Player añadirPlayer (int numRaza, int posX, int posY, String nombre)
    {
        Player pc = new Player(0, posX, posY, nombre);
        Mundo.listaDePlayers.add(pc);
        stageMundo.addActor(pc.getActor());
        return pc;
    }
    
    public static void añadirProyectil (Proyectil proyectil)
    {
        Mundo.listaDeProyectiles.add(proyectil);
        //proyectil.getPixie().setColor(0f, 0f, 0f, 0f);
        //proyectil.getPixie().addAction(Actions.fadeOut(1.5f, Interpolation.linear));
        stageMundo.addActor(proyectil.getPixie()); 
    }
    
    public static void eliminarProyectil (Proyectil proyectil)
    {
        Mundo.listaDeProyectiles.removeValue(proyectil, true);
        stageMundo.getRoot().removeActor(proyectil.getPixie());
    }
        
    public static void actualizarPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        { listaDePlayers.get(i).actualizar(delta); }
    }
    
    public static void actualizarProyectiles (float delta)
    {
        for (int i=0; i<listaDeProyectiles.size; i++)
        { listaDeProyectiles.get(i).actualizar(delta); }
    }
    
    
    public static void añadirTerreno (String nombreTerreno)
    {
        Terreno terreno = new Terreno();
        terreno.setNombre(nombreTerreno);
        terreno.setTextura(nombreTerreno);
        terreno.setColor(Color.GRAY);
        Mundo.listaDeTerrenos.add(terreno);
    }
}
