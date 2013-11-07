package Estaticos;

import Constantes.MiscData;
import static Graphics.Recursos.atlas;
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
    private TextureRegion textura;                  //Textura de tipo Pixie que contiene el grafico del terreno, puede ser animado
    private boolean isSolido = false;               //Flag que controla si el terreno es solido o no, y por tanto atravesable por los objetos que pueblan el mundo
    
    private TextureRegion cuadranteNO;
    private boolean NOizquierda;
    private boolean NOdiagonalNO;
    private boolean NOarriba;
    private TextureRegion cuadranteNE;
    private boolean NEarriba;
    private boolean NEdiagonalNE;
    private boolean NEderecha;
    private TextureRegion cuadranteSE;
    private boolean SOderecha;
    private boolean SOdiagonalSE;
    private boolean SOabajo;
    private TextureRegion cuadranteSO;
    private boolean SEabajo;
    private boolean SEdiagonalSO;
    private boolean SEizquierda;
    
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
    
    public void generarTexturaNO()
    {
        int tileCuadrante = MiscData.TILESIZE/2;
        
        if ( NOizquierda && NOdiagonalNO && NOarriba )      { cuadranteNO = new TextureRegion (textura, tileCuadrante*2, tileCuadrante*4, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( NOizquierda && NOdiagonalNO && !NOarriba )     { cuadranteNO = new TextureRegion (textura, tileCuadrante*2, tileCuadrante*2, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( NOizquierda && !NOdiagonalNO && NOarriba )     { cuadranteNO = new TextureRegion (textura, tileCuadrante*2, tileCuadrante*0, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( NOizquierda && !NOdiagonalNO && !NOarriba )    { cuadranteNO = new TextureRegion (textura, tileCuadrante, tileCuadrante, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( !NOizquierda && NOdiagonalNO && NOarriba )     { cuadranteNO = new TextureRegion (textura, tileCuadrante, tileCuadrante, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( !NOizquierda && NOdiagonalNO && !NOarriba )    { cuadranteNO = new TextureRegion (textura, tileCuadrante, tileCuadrante, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( !NOizquierda && !NOdiagonalNO && NOarriba )    { cuadranteNO = new TextureRegion (textura, tileCuadrante, tileCuadrante, MiscData.TILESIZE, MiscData.TILESIZE); }
        if ( !NOizquierda && !NOdiagonalNO && !NOarriba )   { cuadranteNO = new TextureRegion (textura, tileCuadrante, tileCuadrante, MiscData.TILESIZE, MiscData.TILESIZE); }
    }
    
    public void generarTextura()
    {
        int tileCuadrante = MiscData.TILESIZE/2;
        
        generarTexturaNO();
        
        
        textura.setRegion(cuadranteNO, 0, 0, tileCuadrante, tileCuadrante);
        textura.setRegion(cuadranteNE, tileCuadrante, 0, tileCuadrante, tileCuadrante);
        textura.setRegion(cuadranteSE, tileCuadrante, tileCuadrante, tileCuadrante, tileCuadrante);
        textura.setRegion(cuadranteSE, 0, tileCuadrante, tileCuadrante, tileCuadrante);
        
    }
    
}
