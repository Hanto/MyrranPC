package Geo;

import Constantes.MiscData;
import static Constantes.MiscData.TILESIZE;
import Resources.Recursos;
import Main.Mundo;
import static Main.Mundo.listaDeTerrenos;
import static Main.Mundo.mapa;
import static Main.Mundo.tiledMap;
import static Main.Mundo.mapRenderer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * @author Ivan Delgado Huerta
 */
public class Mapa 
{
    public static TextureRegion cuadranteNO;
    private static boolean NOizquierda;
    private static boolean NOdiagonal;
    private static boolean NOarriba;
    public static TextureRegion cuadranteNE;
    private static boolean NEarriba;
    private static boolean NEdiagonal;
    private static boolean NEderecha;
    public static TextureRegion cuadranteSE;
    private static boolean SEderecha;
    private static boolean SEdiagonal;
    private static boolean SEabajo;
    public static TextureRegion cuadranteSO;
    private static boolean SOabajo;
    private static boolean SOdiagonal;
    private static boolean SOizquierda;
    
    public static boolean renderGrid = true;
    
    public static void crearTiledMap()
    {
        //TextureRegion terreno.getTextura()Suelo = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo"));
        //TextureRegion terreno.getTextura()Suelo2 = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo2"));
        //StaticTiledMapTile tile = new StaticTiledMapTile(terreno.getTextura()Suelo);
        //StaticTiledMapTile tile2 = new StaticTiledMapTile(terreno.getTextura()Suelo2);
        //Array<StaticTiledMapTile> aTileArray = new Array<>(2);
        //aTileArray.add(tile);
        //aTileArray.add(tile2);
        //AnimatedTiledMapTile aTile = new AnimatedTiledMapTile(0.3f, aTileArray);
        Cell cell;
        StaticTiledMapTile tileNO;
        StaticTiledMapTile tileNE;
        StaticTiledMapTile tileSO;
        StaticTiledMapTile tileSE;
        
        for (int numCapa=0; numCapa<MiscData.MAPA_Max_Capas_Terreno; numCapa++)
        {
            TiledMapTileLayer suelo = new TiledMapTileLayer (MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE/2, MiscData.TILESIZE/2);
            for (int x = 0; x < MiscData.MAPA_Max_X; x++)
            {
                for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
                {   //si contiene un ID a un terreno valido cargamos su textura, si no, lo ignoramos
                    if (mapa[x][y].getTerrenoID()[numCapa] >=0) 
                    {
                        generarTexturaCelda(x, y, numCapa);

                        tileNO = new StaticTiledMapTile(cuadranteNO);
                        tileNE = new StaticTiledMapTile(cuadranteNE);
                        tileSO = new StaticTiledMapTile(cuadranteSO);
                        tileSE = new StaticTiledMapTile(cuadranteSE);

                        cell = new Cell();
                        cell.setTile(tileNO);
                        suelo.setCell(x*2, y*2+1, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileNE);
                        suelo.setCell(x*2+1, y*2+1, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileSO);
                        suelo.setCell(x*2, y*2, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileSE);
                        suelo.setCell(x*2+1, y*2, cell);
                    }
                }
            }
            tiledMap.getLayers().add(suelo);
        }
        if (renderGrid)
        {
            TiledMapTileLayer layerGrid = new TiledMapTileLayer (MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE, MiscData.TILESIZE);
            StaticTiledMapTile grid = new StaticTiledMapTile(Recursos.grid);
            cell = new Cell();

            for (int x = 0; x < MiscData.MAPA_Max_X*2; x++)
            {
                for (int y = 0; y < MiscData.MAPA_Max_Y*2; y++)
                {   //si contiene un ID a un terreno valido cargamos su textura, si no, lo ignoramos
                    cell.setTile(grid);
                    layerGrid.setCell(x, y, cell);
                }
            }
            tiledMap.getLayers().add(layerGrid);
        }
        mapRenderer = new OrthogonalTiledMapRenderer(Mundo.tiledMap);
    }
    
    public static void crearTile (int x, int y, int numCapa)
    {
        if (x<0 || y<0 || x>MiscData.MAPA_Max_X || y>MiscData.MAPA_Max_Y) { return; }
        
        int numTerreno = mapa[x][y].getTerrenoID()[numCapa];
        if (numTerreno<0) borrarCelda(x, y, numCapa);
        else
        {
            Mapa.generarTexturaCelda(x, y, numCapa);

            StaticTiledMapTile tileNO = new StaticTiledMapTile(cuadranteNO);
            StaticTiledMapTile tileNE = new StaticTiledMapTile(cuadranteNE);
            StaticTiledMapTile tileSO = new StaticTiledMapTile(cuadranteSO);
            StaticTiledMapTile tileSE = new StaticTiledMapTile(cuadranteSE);

            TiledMapTileLayer suelo = (TiledMapTileLayer)Mundo.tiledMap.getLayers().get(numCapa);
            Cell cell = new Cell();
            cell.setTile(tileNO);
            suelo.setCell(x*2, y*2+1, cell);

            cell = new Cell();
            cell.setTile(tileNE);
            suelo.setCell(x*2+1, y*2+1, cell);

            cell = new Cell();
            cell.setTile(tileSO);
            suelo.setCell(x*2, y*2, cell);

            cell = new Cell();
            cell.setTile(tileSE);
            suelo.setCell(x*2+1, y*2, cell);
        }
    }
    
    public static void borrarCelda(int x, int y, int numCapa)
    {
        TiledMapTileLayer suelo = (TiledMapTileLayer)Mundo.tiledMap.getLayers().get(numCapa);
        suelo.setCell(x*2, y*2+1, null);
        suelo.setCell(x*2+1, y*2+1, null);
        suelo.setCell(x*2, y*2, null);
        suelo.setCell(x*2+1, y*2, null);
    }
    
    public static void generarTexturaCelda (int X, int Y, int capa)
    {   //reseteamos sus adyacencias:
        resetAdyacencias();
        int numTerreno = mapa[X][Y].getTerrenoID()[capa];
        if (numTerreno <0) return;
        //Buscamos que terreno tiene esa celda
        Terreno terreno = listaDeTerrenos.get(numTerreno);        
        //Calculamos las adyacendias en todas las direcciones con terrenos de su mismo tipo
        if      ( Y+1 >= MiscData.MAPA_Max_Y)                                   { NOarriba = false; NEarriba = false; }
        else if ( mapa[X][Y].getTerrenoID()[capa] == mapa[X][Y+1].getTerrenoID()[capa])   { NOarriba = true; NEarriba = true; }
        if      (Y-1 < 0)                                                       { SOabajo = false; SEabajo = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X][Y-1].getTerrenoID()[capa])    { SOabajo = true; SEabajo = true; }
        if      (X-1 < 0)                                                       { NOizquierda = false; SOizquierda = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X-1][Y].getTerrenoID()[capa])    { NOizquierda = true; SOizquierda = true; }
        if      (X+1 >= MiscData.MAPA_Max_X)                                    { NEderecha = false; SEderecha = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X+1][Y].getTerrenoID()[capa])    { NEderecha = true; SEderecha = true; }
        if      (X+1 >= MiscData.MAPA_Max_X || Y+1 >= MiscData.MAPA_Max_Y)      { NEdiagonal = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X+1][Y+1].getTerrenoID()[capa])  { NEdiagonal = true; }
        if      (X-1<0 || Y-1<0)                                                { SOdiagonal = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X-1][Y-1].getTerrenoID()[capa])  { SOdiagonal = true; }
        if      (X-1 <0 || Y+1 >= MiscData.MAPA_Max_Y)                          { NOdiagonal = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X-1][Y+1].getTerrenoID()[capa])  { NOdiagonal = true; }
        if      (X+1 >= MiscData.MAPA_Max_X || Y-1<0)                           { SEdiagonal = false; }
        else if (mapa[X][Y].getTerrenoID()[capa] == mapa[X+1][Y-1].getTerrenoID()[capa])  { SEdiagonal = true; }
        //generamos las texturas de sus 4 cuadrantes segun sus adyacencias:
        generarTexturaCuadrantes(terreno);
    }
    
    private static void resetAdyacencias ()
    {
        NOizquierda = false;
        NOdiagonal = false;
        NOarriba = false;
        NEarriba = false;
        NEdiagonal = false;
        NEderecha = false;
        SEderecha = false;
        SEdiagonal = false;
        SEabajo = false;
        SOabajo = false;
        SOdiagonal = false;
        SOizquierda = false;
    }
    
    private static void generarTexturaCuadrantes(Terreno terreno)
    {
        generarTexturaNO(terreno);
        generarTexturaNE(terreno);
        generarTexturaSO(terreno);
        generarTexturaSE(terreno);
    }
    
    private static void generarTexturaNO(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;
        
        if (  NOizquierda &&  NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*4, cuadrante, cuadrante); }
        if (  NOizquierda &&  NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*2, cuadrante, cuadrante); }
        if (  NOizquierda && !NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*0, cuadrante, cuadrante); }
        if (  NOizquierda && !NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*2, cuadrante, cuadrante); }
        if ( !NOizquierda &&  NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*4, cuadrante, cuadrante); }
        if ( !NOizquierda &&  NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*2, cuadrante, cuadrante); }
        if ( !NOizquierda && !NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*4, cuadrante, cuadrante); }
        if ( !NOizquierda && !NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*2, cuadrante, cuadrante); }
    }
    
    private static void generarTexturaNE(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;

        if (  NEderecha &&  NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*4, cuadrante, cuadrante); }
        if (  NEderecha &&  NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*2, cuadrante, cuadrante); }
        if (  NEderecha && !NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*0, cuadrante, cuadrante); }
        if (  NEderecha && !NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*2, cuadrante, cuadrante); }
        if ( !NEderecha &&  NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*4, cuadrante, cuadrante); }
        if ( !NEderecha &&  NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*2, cuadrante, cuadrante); }
        if ( !NEderecha && !NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*4, cuadrante, cuadrante); }
        if ( !NEderecha && !NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*2, cuadrante, cuadrante); }
    }
    
    private static void generarTexturaSO(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;
        
        if (  SOizquierda &&  SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*3, cuadrante, cuadrante); }
        if (  SOizquierda &&  SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*5, cuadrante, cuadrante); }
        if (  SOizquierda && !SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*1, cuadrante, cuadrante); }
        if (  SOizquierda && !SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*2, cuadrante*5, cuadrante, cuadrante); }
        if ( !SOizquierda &&  SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*3, cuadrante, cuadrante); }
        if ( !SOizquierda &&  SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*5, cuadrante, cuadrante); }
        if ( !SOizquierda && !SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*3, cuadrante, cuadrante); }
        if ( !SOizquierda && !SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion (terreno.getTextura(), cuadrante*0, cuadrante*5, cuadrante, cuadrante); }
    }
    
    private static void generarTexturaSE(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;
        
        if (  SEderecha &&  SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*3, cuadrante, cuadrante); }
        if (  SEderecha &&  SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*5, cuadrante, cuadrante); }
        if (  SEderecha && !SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*1, cuadrante, cuadrante); }
        if (  SEderecha && !SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*1, cuadrante*5, cuadrante, cuadrante); }
        if ( !SEderecha &&  SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*3, cuadrante, cuadrante); }
        if ( !SEderecha &&  SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*5, cuadrante, cuadrante); }
        if ( !SEderecha && !SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*3, cuadrante, cuadrante); }
        if ( !SEderecha && !SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion (terreno.getTextura(), cuadrante*3, cuadrante*5, cuadrante, cuadrante); }
    }
}
