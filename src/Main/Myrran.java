/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Constantes.MiscData;
import Screens.PantallaJuego;
import Screens.PantallaMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.LWJGLException;

/**
 * @author Ivan Delgado Huerta
 */

public class Myrran extends Game
{
    public static String LOG = Myrran.class.getSimpleName();
    public enum tipoPantalla { pantallaMenu, pantallaJuego }
    
    
    public static void main(String[] args) throws LWJGLException
    {
        inicializar ();
    }
    
    public static void inicializar ()
    {
        inicializarLibGDX();
    }
    
    public static void inicializarLibGDX ()
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Myrran";
        cfg.vSyncEnabled = true;
        cfg.useGL20 = true;
        cfg.width = MiscData.WINDOW_Horizontal_Resolution;
        cfg.height = MiscData.WINDOW_Vertical_Resolution;
        new LwjglApplication(new Myrran(), cfg);
    }
    
    public void navegarA (tipoPantalla pantalla)
    {
        Screen screen;
        switch (pantalla)
        {
            case pantallaMenu:      screen = new PantallaMenu(this); break;
            case pantallaJuego:     screen = new PantallaJuego(this); break;
            default:                screen = new PantallaMenu(this); break;
        }
        setScreen(screen);
    }
    
    @Override
    public void create() 
    {
        navegarA(tipoPantalla.pantallaJuego);
    }
}
