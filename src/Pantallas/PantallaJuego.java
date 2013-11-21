package Pantallas;
import Constantes.LoadData;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Mapa;
import Graficos.PixieArbol;
import Graficos.Recursos;
import Graficos.Texto;
import Main.Mundo;
import static Main.Mundo.player;
import Main.Myrran;
import Mobiles.Player;
import static Pantallas.AbstractPantalla.camara;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.util.Comparator;
// * @author Ivan Delgado Huerta
public class PantallaJuego extends AbstractPantalla
{
    public static Stage stageMundo;
    private static RayHandler rayHandler;
    private static World world;
    private Texto fps;
    
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
        //RayHandler.useDiffuseLight(true);
        //rayHandler = new RayHandler (world);
        //rayHandler.setCombinedMatrix(camara.combined);
        //rayHandler.setAmbientLight(1f);
        //new PointLight(rayHandler, 10000, new Color(1,1,1,0.7f), 1000, 0, 0);
        
        Recursos.crearRecursos();
        crearMapa();
        LoadData.cargarListaDeTiposSpell();
        LoadData.cargarListaDeSpells();
        
        Mapa.renderGrid = false;
        Mapa.crearTiledMap();
        
        player = Mundo.añadirPlayer(0, 0, 0, "Hanto");
        
        player.getPixiePC().setCuerpo(0);
        player.getPixiePC().setBotas(1);
        player.getPixiePC().setGuantes(1);
        player.getPixiePC().setPeto(1);
        player.getPixiePC().setPantalones(1);
        
        //player.getPixiePC().setCabeza(1);
        //player.getPixiePC().setYelmo(0);
        //player.getPixiePC().setHombreras(1);
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
        stageMundo.addActor(parbol);
        
        PixieArbol parbol2 = new PixieArbol (0);
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 200);
        stageMundo.addActor(parbol2);
        
        PixieArbol parbol3 = new PixieArbol(parbol);
        parbol3.setPosition(450, 150);
        stageMundo.addActor(parbol3);
                
        Texto texto= new Graficos.Texto("-125", Recursos.font14, Color.RED, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.scrollingCombatText(stageMundo, 2f);
        
        fps = new Graficos.Texto("fps", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        stageUI.addActor(fps);
                        
        //player.getActor().addAction(Actions.fadeOut(3.5f));
    }
    
    @Override
    public void show ()
    {
        super.show();
    }
    
    @Override
    public void render (float delta)
    {
        Mundo.actualizarPlayers(delta);
        Mundo.actualizarProyectiles(delta);
                
        super.render(delta);
        
        stageMundo.getActors().sort(new ComparatorActor());
        
        zoom();
                
        camara.position.x = player.getX()+player.getPixiePC().getWidth()/2;
        camara.position.y = player.getY()+player.getPixiePC().getWidth()/2;
        stageMundo.setCamera(camara);
        Mundo.mapRenderer.setView(camara);
        Mundo.mapRenderer.render();
        
        camara.update();
        batch.setProjectionMatrix(camara.combined);
        
        //renderGrid();
        
        batch.begin();
        
        //player.getGroupPixie().draw(batch, 1);
        
        batch.end();
        
        stageMundo.act(delta);  
        stageMundo.draw();
           
        //rayHandler.updateAndRender();
        
        stageUI.draw();
        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
        //modoEntrelazado();
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
        Recursos.liberarRecursos();
    }
    
    private void crearMapa()
    {
        for (int x = 0; x < 200; x++)
        {
            for (int y = 0; y < 200; y++)
            {
                Celda celda = new Celda();
                celda.getTerreno()[0]=0;
                Mundo.mapa[x][y]=celda;
            }
        }
        
        Celda celda2 = new Celda();
        celda2.getTerreno()[0]=0;
        celda2.getTerreno()[1]=1;
              
        dibujarCelda(4,5,celda2);
        dibujarCelda(3,5,celda2);
        dibujarCelda(2,5,celda2);
        dibujarCelda(1,5,celda2);
        dibujarCelda(5,5,celda2);
        dibujarCelda(6,5,celda2);
        dibujarCelda(6,6,celda2);
        dibujarCelda(6,4,celda2);
        dibujarCelda(7,5,celda2);
        dibujarCelda(7,4,celda2);
        dibujarCelda(7,3,celda2);
        dibujarCelda(6,3,celda2);
        dibujarCelda(8,4,celda2);
        dibujarCelda(8,3,celda2);
        dibujarCelda(8,5,celda2);
        dibujarCelda(9,4,celda2);
        dibujarCelda(10,4,celda2);
        dibujarCelda(11,3,celda2);
        dibujarCelda(11,4,celda2);
        dibujarCelda(11,5,celda2);
        dibujarCelda(7,2,celda2);
        Mundo.mapa[13][5]=celda2;
        Mundo.mapa[16][5]=celda2;
        
        dibujarCelda(2,2,celda2);
        Mundo.mapa[3][4]=celda2;
        Mundo.mapa[4][3]=celda2;
        Mundo.mapa[3][3]=celda2;
    }
    
    public void dibujarCelda (int x, int y, Celda celda)
    {
        Mundo.mapa[x*2][y*2]=celda;
        Mundo.mapa[x*2][y*2+1]=celda;
        Mundo.mapa[x*2+1][y*2]=celda;
        Mundo.mapa[x*2+1][y*2+1]=celda;
    }       
    
    public void zoom()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) 
        { 
            if (camara.viewportHeight >= MiscData.WINDOW_Vertical_Resolution) 
            { 
                camara.viewportHeight = MiscData.WINDOW_Vertical_Resolution;
                camara.viewportWidth = MiscData.WINDOW_Horizontal_Resolution;
            }
            else 
            {
                camara.viewportHeight = camara.viewportHeight*1.01f;
                camara.viewportWidth = camara.viewportWidth*1.01f; 
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) { camara.viewportHeight = camara.viewportHeight/1.01f; camara.viewportWidth = camara.viewportWidth/1.01f; }
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
