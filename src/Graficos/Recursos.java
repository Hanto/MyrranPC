package Graficos;
import Constantes.MiscData;
import Main.Mundo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.utils.Array;
//@author Ivan Delgado Huerta
//Esta clase se encarga de generar las texturas a partir de ficheros sueltos, asi como de cargarlas en memoria
//Todas las texturas, graficos y animaciones se guardan y se acceden desde aqui:
public class Recursos 
{
    public static TextureAtlas atlas;
    //Graficos de las armaduras para los jugadores, contienen los graficos de las armaduras de cada uno de esos slots en cada una de las distintas animaciones de los personajes:
    //Cada Array, contiene un Array con todas las armaduras para cada tipo de cuerpo, la posicion 0, contiene todas las armaduras del cuerpo 0 por ejemplo.
    public static Array<ArrayPixies> listaDeRazas = new Array<>();
    public static class ArrayPixies
    {   //Este Array contiene todas las armaduras de un solo tipo de cuerpo
        public Array<Pixie> listaDeCuerpos = new Array<>();
        public Array<Pixie> listaDeCabezas = new Array<>();
        public Array<Pixie> listaDeYelmos = new Array<>();
        public Array<Pixie> listaDePetos = new Array<>();
        public Array<Pixie> listaDePantalones = new Array<>();
        public Array<Pixie> listaDeGuantes = new Array<>();
        public Array<Pixie> listaDeBotas = new Array<>();
        public Array<Pixie> listaDeHombreras = new Array<>();
        public Array<Pixie> listaDeCapasTraseras = new Array<>();
        public Array<Pixie> listaDeCapasFrontales = new Array<>();
    }
    public static Array<Pixie> listaDeSpells = new Array<>();
    public static Array<TextureRegion> listaIconos = new Array<>();
    
    public static Array<TroncoTemplate> listaDeTroncos = new Array<>();
    public static class TroncoTemplate
    {
        public TextureRegion textura;
        public Vector2 enganche1 = new Vector2(0,0);
        public Vector2 enganche2 = new Vector2(0,0);
        public Vector2 enganche3 = new Vector2(0,0);
    }
    public static Array<Pixie> listaDeCopas = new Array<>();
       
    public static TextureRegion sombraPlayer;
    public static TextureRegion nameplateTotal;
    public static TextureRegion nameplateActual;
    public static TextureRegion grid;
    
    public static Image troncon;
    public static Image hojas;
    public static Image sombraArbol1;
    public static BitmapFont font14;  
    
    public static void crearRecursos()
    {
        crearAtlas();
        sombraPlayer = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Sombra"));
        nameplateTotal = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Nameplate"));
        nameplateActual = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"NameplateFondo"));
        
        Recursos.añadirRaza();
        Recursos.salvarCuerpo(0, "Golem");
        Recursos.salvarCuerpo(0, "Golem2");
        Recursos.salvarCuerpo(0, "Golem3");
        
        Recursos.salvarYelmo(0, "Desnudo");
        Recursos.salvarBotas(0, "Desnudo");
        Recursos.salvarGuantes(0, "Desnudo");
        Recursos.salvarHombreras(0, "Desnudo");
        Recursos.salvarPantalones(0, "Desnudo");
        Recursos.salvarPeto(0, "Desnudo");
        Recursos.salvarCapasTraseras(0, "Desnudo");
        Recursos.salvarCapasFrontales(0, "Desnudo");
        
        Recursos.salvarCabeza(0, "Cabeza1");
        Recursos.salvarYelmo(0, "Casco1");
        Recursos.salvarBotas(0, "Botas1");
        Recursos.salvarGuantes(0, "Guantes1");
        Recursos.salvarHombreras(0, "Hombreras1");
        Recursos.salvarPantalones(0, "Pantalones1");
        Recursos.salvarPeto(0, "Peto1");
        Recursos.salvarCapasTraseras(0, "CapaTrasera1");
        Recursos.salvarCapasFrontales(0, "CapaFrontal1");
        
        Recursos.salvarCabeza(0, "Cabeza2");
        
        Recursos.salvarTronco("Tronco2", -50, 50, -45, 65, 40, 65);
        Recursos.salvarCopa("BolaGrandeArbol2");
        Recursos.salvarCopa("BolaMedianaArbol2");
        Recursos.salvarCopa("Bolapequeñaarbol2");
        
        Recursos.salvarIcono("FireBall");
        Recursos.salvarIcono("Editar");
        
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC+"Fireball"));
        Pixie fireball = new Pixie (texture, 1, 3, 3, 0.15f, false, true);
        listaDeSpells.add(fireball);
        
        troncon = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"Tronco1"));
        hojas = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"Hojas1"));
        sombraArbol1 = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"SombraArbol1")); 
        font14 = new BitmapFont (Gdx.files.internal("fonts/14.fnt"), false);
        grid = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"grid"));
        
        Mundo.añadirTerreno("Cesped");
        Mundo.añadirTerreno("Cesped2");
    }
        
    public static void crearAtlas()
    {   //Creamos un atlas con todas las imagenes que tengamos sueltas, util para el modo edicion/desarrollador
        TexturePacker2.process(MiscData.ATLAS_Carpeta_Imagenes_Origen, MiscData.ATLAS_Carpeta_Imagenes_Destino, MiscData.ATLAS_Atlas_Extension);
        //Cargamos el atlas en memoria
        atlas = new TextureAtlas(Gdx.files.internal(MiscData.ATLAS_Carpeta_Imagenes_Destino+MiscData.ATLAS_Atlas_Extension+".atlas"));
    }
    
    public static void añadirRaza ()
    {
        ArrayPixies aPixes = new ArrayPixies();
        listaDeRazas.add(aPixes);
    }
            
    public static void salvarCuerpo (int numRaza, String nombreCuerpo)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+nombreCuerpo));
        Pixie pixieCuerpo = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);        
        listaDeRazas.get(numRaza).listaDeCuerpos.add(pixieCuerpo);
    }
    
    public static void salvarCabeza (int numRaza, String nombreCabeza)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+nombreCabeza));
        Pixie pixieCabeza = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);        
        listaDeRazas.get(numRaza).listaDeCabezas.add(pixieCabeza);
    }
    
    public static void salvarYelmo (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeYelmos.add(pixieArmadura);
    }
        
    public static void salvarHombreras (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeHombreras.add(pixieArmadura);
    }
    
    public static void salvarPeto (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDePetos.add(pixieArmadura);
    }
    
    public static void salvarPantalones (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDePantalones.add(pixieArmadura);
    }
    
    public static void salvarGuantes (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeGuantes.add(pixieArmadura);
    }
    
    public static void salvarBotas (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeBotas.add(pixieArmadura);
    }
    
    public static void salvarCapasTraseras (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeCapasTraseras.add(pixieArmadura);
    }
    
    public static void salvarCapasFrontales (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas, MiscData.PIXIE_Player_numFramesAnimacion, 
                                                            MiscData.PIXIE_Player_DuracionFrame, MiscData.PIXIE_Player_isEnlazado, MiscData.PIXIE_Player_isLooping);
        listaDeRazas.get(numRaza).listaDeCapasFrontales.add(pixieArmadura);
    }
    
    public static void salvarIcono (String nombreIcono)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_SpellIcons_LOC+nombreIcono));
        listaIconos.add(texture);
    }
    
    
    
    public static void salvarTronco (String nombreTronco, int X1, int Y1, int X2, int Y2, int X3, int Y3)
    {
        TroncoTemplate tronco = new TroncoTemplate ();
        
        tronco.textura = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Arboles_LOC+nombreTronco));
        tronco.enganche1.set(X1, Y1);
        tronco.enganche2.set(X2, Y2);
        tronco.enganche3.set(X3, Y3);
        
        listaDeTroncos.add(tronco);
    }
    
    public static void salvarCopa (String nombreCopa)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Arboles_LOC+nombreCopa));
        Pixie pixieCopa = new Pixie (texture, 1, 3, 3, 0.3f, false, true);
        listaDeCopas.add(pixieCopa);
    }
    
    public static void liberarRecursos ()
    {
        if (atlas != null) atlas.dispose();
        if (Mundo.tiledMap != null) Mundo.tiledMap.dispose();
        if (Mundo.mapRenderer != null) Mundo.mapRenderer.dispose(); 
    }
}
