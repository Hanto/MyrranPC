package Screens;

import Constantes.MiscData;
import Graphics.PixieArbol;
import Graphics.Recursos;
import Main.Mundo;
import Main.Myrran;
import Mobiles.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import static Main.Mundo.player;
import box2dLight.RayHandler;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.Comparator;

/**
 * @author Ivan Delgado Huerta
 */

public class PantallaJuego extends PantallaAbstract
{
    public static Stage stageMundo;
    private static RayHandler rayHandler;
    private static World world;
    
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    
    private TextureRegion pruebaTron;
    private TextureRegion pruebaSom;
    private TextureRegion prueba3;
    
    static class ComparatorActor implements Comparator<Actor>
    {
        @Override
        public int compare(Actor o1, Actor o2) 
        { return (o1.getY() <= o2.getY() ? 1 : -1); }
    }
    
    //CONSTRUCTOR:
    public PantallaJuego (Myrran game)
    {
        super (game);
        
        stageMundo = new Stage (0,0, true);
        world = new World (new Vector2 (0, -9.8f), false);
        RayHandler.useDiffuseLight(true);
        //rayHandler = new RayHandler (world);
        //rayHandler.setCombinedMatrix(camara.combined);
        //rayHandler.setAmbientLight(1f);
        //new PointLight(rayHandler, 10000, new Color(1,1,1,0.7f), 1000, 0, 0);
        
        Recursos.crearRecursos();
        crearTileMap ();
        
        player = new Player (0);
        Mundo.listaDePlayers.add(player);
                
        //player.getGroupPixie().scale(4);
        
        player.getPixie().setCuerpo(0);
        player.getPixie().setCabeza(1);
        player.getPixie().setPeto(1);
        player.getPixie().setYelmo(0);
        player.getPixie().setBotas(1);
        player.getPixie().setPantalones(1);
        player.getPixie().setHombreras(1);
        player.getPixie().setGuantes(1);
        player.getPixie().setCapaFrontal(1);
        player.getPixie().setCapaTrasera(1);
        
        //stageMundo.addActor(player.getPixie());
        
        /*fireball = new GroupPixie(Recursos.listaDeSpells.get(0));
        stageMundo.addActor(fireball);
        fireball.setPosition(50, 50);
        fireball.setRotation(135);
        fireball.addAction(Actions.moveTo(1900, 1080, 5f, Interpolation.linear));*/
        
        PixieArbol parbol = new PixieArbol (0);
        parbol.setCopas(0, 1, 2);
        parbol.setPosition(100, 100);       
        stageMundo.addActor(parbol);
        
        PixieArbol parbol2 = new PixieArbol (0);
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 80);
        stageMundo.addActor(parbol2);
        
        PixieArbol parbol3 = new PixieArbol(parbol);
        parbol3.setPosition(160, 40);
        stageMundo.addActor(parbol3);
        
        stageMundo.addActor(player.getPixie());
    }
    
    @Override
    public void show ()
    {
        super.show();
    
        //pruebaTron= new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Arboles_LOC+"Tronco1"));
        
        pruebaSom = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Sombra"));      
        //pruebaTron.getTexture().draw(pam, 0, 0);
    }
    
    @Override
    public void render (float delta)
    {
        Mundo.actualizarPlayers(delta);
                
        stageMundo.getActors().sort(new ComparatorActor());
        
        super.render(delta);
                
        camara.position.x = player.getX();
        camara.position.y = player.getY();
        stageMundo.setCamera(camara);
        mapRenderer.setView(camara);
        mapRenderer.render();
        
        camara.update();
        batch.setProjectionMatrix(camara.combined);
        
        batch.begin();
          
        
        //player.getGroupPixie().draw(batch, 1);
        
        batch.end();
        
        stageMundo.act(delta);  
        stageMundo.draw();
           
        //rayHandler.updateAndRender();
    }
    
    @Override
    public void resize(int anchura, int altura) 
    {
        super.resize(anchura, altura);
        stageMundo.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }
    
    @Override
    public void dispose ()
    {
        super.dispose();
        
        if (stageMundo != null) stageMundo.dispose();
        if (map != null) map.dispose();
        if (mapRenderer != null) mapRenderer.dispose();
        Recursos.liberarRecursos();
    }
    
    private void crearTileMap()
    {
        map = new TiledMap ();
        TextureRegion texturaSuelo = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo"));
        TextureRegion texturaSuelo2 = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo2"));
        
        StaticTiledMapTile tile = new StaticTiledMapTile(texturaSuelo);
        StaticTiledMapTile tile2 = new StaticTiledMapTile(texturaSuelo2);
        
        Array<StaticTiledMapTile> aTileArray = new Array<StaticTiledMapTile>(3);
        aTileArray.add(tile);
        aTileArray.add(tile2);
        aTileArray.add(tile);
        
        AnimatedTiledMapTile aTile = new AnimatedTiledMapTile(1, aTileArray);
        //aTile.setAnimationTime(1f);
        
        TiledMapTileLayer layer = new TiledMapTileLayer (200, 200, 48, 48);
        for (int x = 0; x < 200; x++)
        {
            for (int y = 0; y < 200; y++)
            {
                Cell cell = new Cell();
                cell.setTile(tile);
                layer.setCell(x, y, cell);
            }
        }
        map.getLayers().add(layer);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }
}
