package Main;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Muro;
import Geo.Terreno;
import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import static Resources.Recursos.atlas;
import Skill.Aura.BDebuff;
import UI.BarraTerrenos;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
 //* @author Ivan Delgado Huerta
 
public class Mundo 
{
    public static Player player;
        
    public static Array<Terreno> listaDeTerrenos = new Array<>();
    public static Array<Muro> listaDeMuros = new Array<>();
    public static Array<Player> listaDePlayers = new Array<>();
    public static Array<Proyectil> listaDeProyectiles = new Array<>();
        
    public static Celda [][] mapa = new Celda [MiscData.MAPA_Max_X][MiscData.MAPA_Max_Y];
    public static TiledMap tiledMap = new TiledMap();
    public static OrthogonalTiledMapRenderer mapRenderer;
    public static Stage stageMundo;
    public static World world;
    public static RayHandler rayHandler;
    
    public static BarraTerrenos barraTerrenos;
    
    //Opciones varias:
    public static boolean dibujarNameplatesPlayer = true;
    
    public static void inicializarMundo ()
    {
        
    }
    
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
    
    public static void actualizarAurasPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        {
            Array<BDebuff> listaDeAuras = listaDePlayers.get(i).listaDeAuras;
            for (int j=0; j<listaDeAuras.size;j++)
            {   listaDeAuras.get(j).actualizarDebuff(); }
        }
    }
    
    public static void añadirTerreno (String nombreTerreno)
    {
        Terreno terreno = new Terreno();
        terreno.setNombre(nombreTerreno);
        terreno.setTextura(nombreTerreno);
        terreno.setColor(Color.GRAY);
        Mundo.listaDeTerrenos.add(terreno);
    }
    
    public static void añadirMuro (String muroBase, String muroMedio, String muroTecho)
    {
        TextureRegion muroBaseTex = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroBase));
        TextureRegion muroMedioTex = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroMedio));
        TextureRegion muroTechoTex = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroTecho));
        Muro muro = new Muro (muroBaseTex, muroMedioTex, muroTechoTex);
        Mundo.listaDeMuros.add(muro);
    }
}
