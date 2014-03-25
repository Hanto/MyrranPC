package Recursos;
import Constantes.MiscData;
import Graficos.Pixie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    public static TextureRegion casillero;
    public static TextureRegion rebindButtonOn;
    public static TextureRegion rebindButtonOff;
    public static TextureRegion botonBorrarTerreno;
    public static TextureRegion spellSeleccionado;
    
    public static TextureRegion muroBase;
    public static TextureRegion muroMedio;
    public static TextureRegion muroTecho;
    
    public static Pixie polvoPasos;
    
    
    public static void crearRecursos()
    {
        crearAtlas();
        
        sombraPlayer = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Sombra"));
        nameplateTotal = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Nameplate"));
        nameplateActual = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"NameplateFondo"));
        troncon = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"Tronco1"));
        hojas = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"Hojas1"));
        sombraArbol1 = new Image(Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"SombraArbol1")); 
        font14 = new BitmapFont (Gdx.files.internal("fonts/14.fnt"), false);
        grid = new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"grid"));
        casillero = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_UI_LOC+"Casillero"));
        rebindButtonOn = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_UI_LOC+"RebindOn"));
        rebindButtonOff = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_UI_LOC+"RebindOff"));
        muroBase = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"MuroBase"));
        muroMedio = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"MuroMedio"));
        muroTecho = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"MuroTecho"));
        botonBorrarTerreno = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_UI_LOC+"Borrar"));
        spellSeleccionado = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_UI_LOC+"Select"));
        
        polvoPasos = new Pixie(new TextureRegion(Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Humo")),3,3);
        polvoPasos.añadirAnimacion("polvo lateral",    new int []{0,1,2},      0.15f, false);
        polvoPasos.animaciones().get(0).animarYEliminar = true;
    }
        
    public static void crearAtlas()
    {   //Creamos un atlas con todas las imagenes que tengamos sueltas, util para el modo edicion/desarrollador
        //TexturePacker2.process(MiscData.ATLAS_Carpeta_Imagenes_Origen, MiscData.ATLAS_Carpeta_Imagenes_Destino, MiscData.ATLAS_Atlas_Extension);
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
        Pixie pixieCuerpo = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieCuerpo.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieCuerpo.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieCuerpo.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieCuerpo.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieCuerpo.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieCuerpo.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieCuerpo.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieCuerpo.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeCuerpos.add(pixieCuerpo);
    }
    
    public static void salvarCabeza (int numRaza, String nombreCabeza)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+nombreCabeza));
        Pixie pixieCuerpo = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieCuerpo.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieCuerpo.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieCuerpo.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieCuerpo.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieCuerpo.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieCuerpo.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieCuerpo.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieCuerpo.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieCuerpo.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeCabezas.add(pixieCuerpo);
    }
    
    public static void salvarYelmo (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeYelmos.add(pixieArmadura);
    }
        
    public static void salvarHombreras (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie( texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeHombreras.add(pixieArmadura);
    }
    
    public static void salvarPeto (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDePetos.add(pixieArmadura);
    }
    
    public static void salvarPantalones (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDePantalones.add(pixieArmadura);
    }
    
    public static void salvarGuantes (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeGuantes.add(pixieArmadura);
    }
    
    public static void salvarBotas (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeBotas.add(pixieArmadura);
    }
    
    public static void salvarCapasTraseras (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeCapasTraseras.add(pixieArmadura);
    }
    
    public static void salvarCapasFrontales (int numRaza, String nombreArmadura)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Armaduras_LOC+nombreArmadura));
        Pixie pixieArmadura = new Pixie (texture, MiscData.PIXIE_Player_numFilas, MiscData.PIXIE_Player_numColumnas);
        pixieArmadura.añadirAnimacion("izquierda",    new int []{0,1,2},      0.15f, true);
        pixieArmadura.añadirAnimacion("derecha",      new int []{3,4,5},      0.15f, true);
        pixieArmadura.añadirAnimacion("arriba",       new int []{6,7,8},      0.15f, true);
        pixieArmadura.añadirAnimacion("abajo",        new int []{9,10,11},    0.15f, true);
        pixieArmadura.añadirAnimacion("castear",      new int []{12,13,14},   0.15f, false);
        pixieArmadura.añadirAnimacion("quieto",       new int []{15,16,17},   0.80f, true);
        pixieArmadura.añadirAnimacion("dispararO",    new int []{18,19,20},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararE",    new int []{21,22,23},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSO",   new int []{24,25,26},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararSE",   new int []{27,28,29},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararS",    new int []{30,31,32},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararN",    new int []{33,34,35},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNO",   new int []{36,37,38},   0.15f, true);
        pixieArmadura.añadirAnimacion("dispararNE",   new int []{39,40,41},   0.15f, true);
        pixieArmadura.animaciones().get(4).ininterrumpible = true;
        listaDeRazas.get(numRaza).listaDeCapasFrontales.add(pixieArmadura);
    }
    
    public static void salvarCopa (String nombreCopa)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_Arboles_LOC+nombreCopa));
        Pixie pixieCopa = new Pixie (texture, MiscData.PIXIE_Copas_numFilas, MiscData.PIXIE_Copas_numColumnas);
        pixieCopa.añadirAnimacion("viento",         new int[] {0,1,2},      1.30f, false);
        listaDeCopas.add(pixieCopa);
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
    
    public static void liberarRecursos ()
    {
        if (atlas != null) atlas.dispose();
        if (font14 != null) font14.dispose();
    }
}
