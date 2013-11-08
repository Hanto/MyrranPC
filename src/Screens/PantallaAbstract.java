package Screens;

import Main.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * @author Ivan Delgado Huerta
 */

//Plantilla Screen Base sobre la que construir todas las Screens:
public abstract class PantallaAbstract implements Screen 
{
    protected final Myrran game;
    protected final SpriteBatch batch;
    protected final ShapeRenderer shape;
    protected final Stage stageUI;
    public static OrthographicCamera camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  //la OrthographicCamera se encarga de hacer la conversion entre las distancias de juego y los pixeles de pantalla
    
    public String getNombrePantalla()           { return getClass().getSimpleName(); }
    
    //CONSTRUCTOR:
    public PantallaAbstract (Myrran game)
    {
        this.game = game;                           //Es necesario disponer de la clase game, para poder por ejemplo cambiar de pantalla con el metodo game.navegarA(screen)
        this.batch = new SpriteBatch ();            //El SpriteBatch es el encargado de dibujar Bitmaps en pantalla, no es una variable, es un motor de dibujado, lo creamos para tenerlo listo   
        this.shape = new ShapeRenderer ();          //El shapeRenderer es como el anterior pero encargado de dibujar lineas
        this.stageUI = new Stage(0, 0, true);
    } 
    
    @Override
    public void show() //El metodo show se ejecuta una solo vez cuando se inicializa la pantalla
    {
        Gdx.app.log( Myrran.LOG, "SHOW (Inicializando Screen): " + getNombrePantalla());
        //Para decirle que acepte Inputs de los actores
        Gdx.input.setInputProcessor(stageUI);
        //Esto hay que activarlo solo para para conseguir un sistema de coordenanas Y-UP
        //camara.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    
    @Override
    public void render(float delta) //El metodo render se ejecuta 60 veces por segundo
    {   //limpia todo la pantalla con el color que le digamos
        Gdx.gl.glClearColor(0/2.55f, 0/2.55f, 0/2.55f, 1f); 
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stageUI.act(delta);   //para animar el Stage
        stageUI.draw();       //para dibujar el Stage
    }

    @Override
    public void resize(int anchura, int altura) 
    {
        Gdx.app.log( Myrran.LOG, "RESIZE (Redimensionando Screen): "+ getNombrePantalla() +" a: "+anchura+" x "+altura);
        stageUI.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    public void pause()
    { Gdx.app.log( Myrran.LOG, "PAUSE (Pausando pantalla): " + getNombrePantalla()); }

    @Override
    public void resume() 
    { Gdx.app.log( Myrran.LOG, "RESUME (Pantalla reanudada): " + getNombrePantalla()); }

    @Override
    public void hide() 
    {   //Despues de cerrar la pantalla es neccesario liberar la memoria de todas las texturas que hayamos usado, por eso llamamos al metodo Dispose
        Gdx.app.log( Myrran.LOG, "HIDE (Cerrando pantalla): "+ getNombrePantalla());
        dispose();
    }
    
    @Override
    public void dispose() 
    {
        Gdx.app.log( Myrran.LOG, "DISPOSE (Liberando memoria): "+ getNombrePantalla());
        //Antes de liberar los recursos nos aseguramos que esten llenos, si no, da error
        if (batch != null) batch.dispose();
        if (shape != null) shape.dispose();
        if (stageUI != null) stageUI.dispose();
    }
   
}
