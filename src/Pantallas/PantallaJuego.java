package Pantallas;
import Constantes.LoadData;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Mapa;
import Geo.Muro;
import Graficos.PixieArbol;
import Graficos.Recursos;
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
    public static RayHandler rayHandler;
    private static World world;
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
            if (o1 instanceof Muro && ((Muro)o1).getAjustePerspectiva()<0 )
                { o1Y = 768-((Muro)o1).muroTecho.getY(); }
            if (o2 instanceof Muro && ((Muro)o2).getAjustePerspectiva()<0 )
                { o2Y = 768-((Muro)o2).muroTecho.getY(); }
            return (o1Y < o2Y ? 1 : (o1Y == o2Y ? 1 : -1)); 
        }
    }
    
    //CONSTRUCTOR:
    public PantallaJuego (Myrran game)
    {
        super (game);
        
        Mundo.stageMundo = new Stage (0,0, true);
        world = new World (new Vector2 (0, -9.8f), false);
        
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler (world);
        rayHandler.setCombinedMatrix(camara.combined);
        rayHandler.setAmbientLight(1f);
        //luz = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 150, 0, 0);
        luzPlayer = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 1050, 0, 0);
        
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
        player.barraSpells.setPosition(60,5);
                
        player.getPixiePC().setCuerpo(0);
        player.getPixiePC().setBotas(1);
        player.getPixiePC().setGuantes(1);
        player.getPixiePC().setPeto(1);
        player.getPixiePC().setHombreras(0);
        player.getPixiePC().setPantalones(1);
        
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
        Mundo.stageMundo.addActor(parbol);
        
        PixieArbol parbol2 = new PixieArbol (0);
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 200);
        Mundo.stageMundo.addActor(parbol2);
        
        PixieArbol parbol3 = new PixieArbol(parbol);
        parbol3.setPosition(450, 150);
        Mundo.stageMundo.addActor(parbol3);
                
        Texto texto= new Graficos.Texto("-125", Recursos.font14, Color.RED, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.scrollingCombatText(Mundo.stageMundo, 2f);
        
        fps = new Graficos.Texto("fps", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        stageUI.addActor(fps);
        
        Mundo.barraTerrenos.setNumColumnas(1);
        
        Muro muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*2, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*3, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*4, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*5, 400);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400);
        Mundo.stageMundo.addActor(muro);
        
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400-24*5);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400-24*6);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400-24*7);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*7, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*8, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*9, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*10, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*11, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*12, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*8);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*7);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*6);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*5);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*2);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*1);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*0);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*-1);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*-2);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*13, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*12, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*11, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*10, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*9, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*8, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*7, 400-24*-3);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*7, 400-24*-2);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*7, 400-24*-1);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*7, 400-24*0);
        Mundo.stageMundo.addActor(muro);
       
        
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*2, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*3, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*4, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*5, 400-24*4);
        Mundo.stageMundo.addActor(muro);
        muro = new Muro(Recursos.muroBase, Recursos.muroMedio, Recursos.muroTecho);
        muro.setPosition(600+24*6, 400-24*4);
        Mundo.stageMundo.addActor(muro);
       
        
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
       
        Mundo.stageMundo.getActors().sort(new ComparatorActor());
        
        zoom();
                
        camara.position.x = player.getX()+player.getPixiePC().getWidth()/2;
        camara.position.y = player.getY()+player.getPixiePC().getWidth()/2;
        Mundo.stageMundo.setCamera(camara);
        Mundo.mapRenderer.setView(camara);
        Mundo.mapRenderer.render();
        
        camara.update();
        batch.setProjectionMatrix(camara.combined);
        
        //renderGrid();
        
        batch.begin();
        
        //player.getGroupPixie().draw(batch, 1);
        
        batch.end();
        
        Mundo.stageMundo.act(delta);  
        Mundo.stageMundo.draw();
           
        rayHandler.setCombinedMatrix(camara.combined);
        luzPlayer.setPosition(Mundo.player.getX(), Mundo.player.getY());
        //Vector3 vectorLuz = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        //camara.unproject(vectorLuz);
        //luz.setPosition(vectorLuz.x, vectorLuz.y);
        rayHandler.updateAndRender();

        stageUI.draw();
        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
        //modoEntrelazado();
    }
    
    @Override
    public void resize(int anchura, int altura) 
    {
        super.resize(anchura, altura);
        Mundo.stageMundo.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }
    
    @Override
    public void dispose ()
    {
        super.dispose();
        
        if (Mundo.stageMundo != null) Mundo.stageMundo.dispose();
        if (rayHandler != null) rayHandler.dispose();
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
        
        SaveData.loadMap();
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
