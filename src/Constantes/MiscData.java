package Constantes;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Ivan Delgado Huerta
 */
public class MiscData 
{
    public final static int WINDOW_Horizontal_Resolution = 1024;
    public final static int WINDOW_Vertical_Resolution = 768;
    
    //MUNDO
    public final static int TILESIZE = 24;
    public final static int MAPA_Max_X = 200;
    public final static int MAPA_Max_Y = 200;
    
    //TERRENO:
    public final static String TERRENO_Nombre_Nuevo = "Terreno";
    public final static int MAPA_Max_Capas_Terreno = 3;
    
    //PIXIE
    public final static float PIXIE_DuracionFrame_Medio = 0.15f;
    public final static int PIXIE_Player_numFilas = 3;
    public final static int PIXIE_Player_numColumnas = 6;
    public final static float PIXIE_Player_DuracionFrame = 0.15f; //en segundos
    public final static int PIXIE_Player_numFramesAnimacion = 3;
    public final static boolean PIXIE_Player_isEnlazado = false;
    public final static boolean PIXIE_Player_isLooping = true;
    
    //RESOURCES (ATLAS):
    public final static String ATLAS_Carpeta_Imagenes_Origen = "images/";
    public final static String ATLAS_Carpeta_Imagenes_Destino = "atlas/";
    public final static String ATLAS_Atlas_Extension = "atlas";
        
    public final static String ATLAS_NPCSprites_LOC = "NPC Sprites/";
    public final static String ATLAS_SpellSprites_LOC = "Spell Sprites/";
    public final static String ATLAS_PlayerSprites_LOC = "Player Sprites/";
    public final static String ATLAS_SpellIcons_LOC = "Spell Icons/";
    public final static String ATLAS_Armaduras_LOC = "Armaduras/";
    public final static String ATLAS_Terrenos_LOC = "Terrenos/";
    public final static String ATLAS_Arboles_LOC = "Arboles/";
    public final static String ATLAS_UI_LOC = "UI/";
    
    //MOBILES:
    public final static float PLAYER_VelocidadMax_Pixeles_Sec = 80.0f;
    
    //NAMEPLATES
    public final static Color NAMEPLATE_Player_Vida = Color.GREEN;
    public final static Color NAMEPLATE_Player_Casteo = Color.RED;
    
}
