package Pantallas;
import Constantes.LoadData;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Mapa;
import Graficos.Muro;
import Graficos.PixieArbol;
import Graficos.Recursos;
import static Graficos.Recursos.atlas;
import Graficos.SPixie;
import Graficos.Texto;
import Main.Mundo;
import static Main.Mundo.player;
import Main.Myrran;
import Mobiles.Player;
import static Pantallas.AbstractPantalla.camara;
import Save.SaveData;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.util.Comparator;
// * @author Ivan Delgado Huerta
public class PantallaJuego extends AbstractPantalla
{
    private Texto fps;
    private PointLight luz;
    private PointLight luzPlayer;
    
    static class ComparatorActor implements Comparator<Actor>
    {
        
        @Override
        public int compare(Actor o1, Actor o2) 
        {   
            float o1Y, o2Y;
            o1Y = o1.getY();
            o2Y = o2.getY();
            if (o1 instanceof Muro && ((Muro)o1).perspectiva < 0 )
                { o1Y = Muro.distanciaPerspectiva-((Muro)o1).muroTecho.getY(); }
            if (o2 instanceof Muro && ((Muro)o2).perspectiva < 0 )
                { o2Y = Muro.distanciaPerspectiva-((Muro)o2).muroTecho.getY(); }
            return (o1Y < o2Y ? 1 : (o1Y == o2Y ? 1 : -1)); 
        }
    }
    
    //CONSTRUCTOR:
    public PantallaJuego (Myrran game)
    {
        super (game);
        
        Mundo.stageMundo = new Stage (0,0, true);
        Mundo.world = new World (new Vector2 (0, -9.8f), false);
        
        RayHandler.useDiffuseLight(true);
        Mundo.rayHandler = new RayHandler (Mundo.world);
        Mundo.rayHandler.setCombinedMatrix(camara.combined);
        Mundo.rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        //luz = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 150, 0, 0);
        luzPlayer = new PointLight(Mundo.rayHandler, 500, new Color(0.3f,0.3f,0.3f,1.0f), 350, 0, 0);
        
        Recursos.crearRecursos();
        crearMapa();
        LoadData.cargarListaDeTiposSpell();
        LoadData.cargarListaDeSpells();
        
        Mapa.renderGrid = false;
        Mapa.crearTiledMap();
        
        player = Mundo.añadirPlayer(0, 0, 0, "Hanto");
        player.setPosition(500, 400);
        stageUI.addActor(player.barraSpells);
        
        player.barraSpells.setSpell(0, Mundo.listaDeSpells.get(0));
        player.barraSpells.setSpell(1, Mundo.listaDeSpells.get(1));
        player.barraSpells.setSpell(2, Mundo.listaDeSpells.get(2));
        player.barraSpells.setSpell(3, Mundo.listaDeSpells.get(3));
        player.barraSpells.setPosition(60,5);
                
        player.getPixiePC().setCuerpo(0);
        player.getPixiePC().setBotas(0);
        player.getPixiePC().setGuantes(0);
        player.getPixiePC().setPeto(0);
        player.getPixiePC().setHombreras(0);
        player.getPixiePC().setPantalones(0);
        
        //player.getPixiePC().setCabeza(1);
        //player.getPixiePC().setYelmo(0);
        //player.getPixiePC().setCapaFrontal(1);
        //player.getPixiePC().setCapaTrasera(1);
        
        /*Pixie fireball = new Pixie(Recursos.listaDeSpells.get(0));
        stageMundo.addActor(fireball);
        fireball.setPosition(50, 50);
        fireball.setRotation(135);
        fireball.addAction(Actions.moveTo(1900, 1080, 5f, Interpolation.linear));*/
        
        PixieArbol parbol = new PixieArbol (0);
        parbol.setCopas(0, 1, 2);
        parbol.setPosition(100, 300);       
        //Mundo.stageMundo.addActor(parbol);
        
        PixieArbol parbol2 = new PixieArbol (0);
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 200);
        //Mundo.stageMundo.addActor(parbol2);
        
        PixieArbol parbol3 = new PixieArbol(parbol);
        parbol3.setPosition(450, 150);
        //Mundo.stageMundo.addActor(parbol3);
                
        Texto texto= new Graficos.Texto("-125", Recursos.font14, Color.RED, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.scrollingCombatText(Mundo.stageMundo, 2f);
        
        fps = new Graficos.Texto("fps", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        stageUI.addActor(fps);
        
        Mundo.barraTerrenos.setNumColumnas(1);
        
        SPixie spixie = new SPixie (new TextureRegion (atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+"Golem")), 3, 6, 3, 0.5f);
        //spixie.añadirAnimacion("Caminar", new int []{0,1,2}, 2f);
        //spixie.setAnimacion(4);
        
        SPixie destino = new SPixie(spixie);
        Mundo.stageMundo.addActor(destino);
        
    }
      
    
    @Override public void show ()
    {
        super.show();
    }
    
    @Override public void render (float delta)
    {
        Mundo.actualizarPlayers(delta);
        Mundo.actualizarProyectiles(delta);
                
        super.render(delta);
       
        Mundo.stageMundo.getActors().sort(new ComparatorActor());
                        
        camara.position.x = player.getX()+player.getPixiePC().getWidth()/2;
        camara.position.y = player.getY()+player.getPixiePC().getWidth()/2;
        
        camara.update();
        batch.setProjectionMatrix(camara.combined);
        Mundo.rayHandler.setCombinedMatrix(camara.combined);
        Mundo.stageMundo.setCamera(camara);
        Mundo.mapRenderer.setView(camara);
        
        //renderGrid();
        
        batch.begin();
        
        batch.end();
        
        Mundo.mapRenderer.render();
        Mundo.stageMundo.act(delta);  
        Mundo.stageMundo.draw();
        
        luzPlayer.setPosition(Mundo.player.getX()+24, Mundo.player.getY());
        //Vector3 vectorLuz = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        //camara.unproject(vectorLuz);
        //luz.setPosition(vectorLuz.x, vectorLuz.y);
        Mundo.rayHandler.updateAndRender();

        stageUI.draw();
        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
        //modoEntrelazado();
    }
    
    @Override public void resize(int anchura, int altura) 
    {
        super.resize(anchura, altura);
        Mundo.stageMundo.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }
    
    @Override public void dispose ()
    {
        super.dispose();
        
        if (Mundo.stageMundo != null) Mundo.stageMundo.dispose();
        if (Mundo.rayHandler != null) Mundo.rayHandler.dispose();
        Recursos.liberarRecursos();
    }
    
    private void crearMapa()
    {
        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                Celda celda = new Celda();
                celda.getTerrenoID()[0]=0;
                Mundo.mapa[x][y]=celda;
            }
        }
        
        SaveData.loadMap();
        
        Muro muro;
        int muroID;
        
        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                muroID = Mundo.mapa[x][y].getMuroID();
                if (muroID >=0)
                {
                    muro = new Muro(Mundo.listaDeMuros.get(muroID));
                    Mundo.stageMundo.addActor(muro);
                    muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
                }
            }
        }
    }
    
    public void renderGrid ()
    {
        camara.position.x=Mundo.player.getX();
        camara.position.y=Mundo.player.getY();
        camara.update();
        
        shape.setProjectionMatrix(camara.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        
        Player player = Mundo.player;
        int Ancho= MiscData.WINDOW_Horizontal_Resolution/2;
        int Alto = MiscData.WINDOW_Vertical_Resolution/2;
        int TileSize = MiscData.TILESIZE;
        
        for (int TileY=(player.getY()-Alto)/TileSize; TileY<(player.getY()+Alto)/TileSize+1; TileY++)
        { 
            for (int i=(player.getX()-Ancho);i<(player.getX()+Ancho)+MiscData.TILESIZE;i=i+2)
            shape.line(i-player.getX()%30, TileY*MiscData.TILESIZE, i+1-player.getX()%30, TileY*MiscData.TILESIZE); 
        }
        for (int TileX=(player.getX()-Ancho)/TileSize; TileX<(player.getX()+Ancho)/TileSize+1; TileX++)
        { 
            for (int i=(player.getY()-Alto);i<(player.getY()+Alto)+MiscData.TILESIZE;i=i+2)
            { shape.line(TileX*MiscData.TILESIZE, i-player.getY()%30, TileX*MiscData.TILESIZE, i+1-player.getY()%30); }
        }
        shape.end();
    }
    
    public void modoEntrelazado ()
    {
        shape.setColor(Color.BLACK);
        shape.begin(ShapeRenderer.ShapeType.Line);
        
        for (int i=0; i<MiscData.WINDOW_Vertical_Resolution;i=i+3)
        {
            shape.line(0, i, MiscData.WINDOW_Horizontal_Resolution, i); 
        }
        shape.end();
    }
}
