package UI;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import Main.Mundo;
import static Pantallas.AbstractPantalla.camara;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
public class PlayerInput implements InputProcessor
{
    @Override
    public boolean keyDown(int keycode) 
    {
        BarraSpells barraspells = Mundo.player.barraSpells;
                
        for (int i=0;i<barraspells.barra.size;i++)
        { if (barraspells.barra.get(i).keycode == keycode) 
            {
                Mundo.player.setSpellSeleccionado(barraspells.barra.get(i).spellID);
                BarraTerrenos.mostrarOcultarBarraTerreno(); //Si seleccionamos el spell EditarTerreno tenemos que mostrar la barra de Terrenos:
            }                                               //Y si seleccionamos otro spell y la barra esta mostrada, hay que ocultarla:
        }
        
        switch (keycode)
        {
            case Keys.W:    Mundo.player.irNorte = true; break;
            case Keys.S:    Mundo.player.irSur = true; break;
            case Keys.A:    Mundo.player.irOeste = true; break;
            case Keys.D:    Mundo.player.irEste = true; break;
        }
        Mundo.player.playerControl.procesarInput();
        return false;
    }
    
    @Override public boolean keyUp(int keycode)                                             
    { 
        switch (keycode)
        {
            case Keys.W:    {Mundo.player.irNorte = false; break; }
            case Keys.S:    {Mundo.player.irSur = false; break;}
            case Keys.A:    {Mundo.player.irOeste = false; break;}
            case Keys.D:    {Mundo.player.irEste = false; break;}
        }
        Mundo.player.playerControl.procesarInput();
        return false;
    }
    
    @Override public boolean keyTyped(char character)                                       { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button)   
    { Mundo.player.castear = true; return false; }
    
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button)     
    { Mundo.player.castear = false; return false; }
    
    @Override public boolean touchDragged(int screenX, int screenY, int pointer)            { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY)                           { return false; }
    @Override public boolean scrolled(int amount)                                           
    { 
        if (amount>0)   { Mundo.player.nivelDeZoom++; }
        else            { Mundo.player.nivelDeZoom--; }
        
        switch (Mundo.player.nivelDeZoom)
        {
            case 0:     camara.zoom = 1.0f; break;
            case -1:    camara.zoom = 1/2f; break;
            case -2:    camara.zoom = 1/3f; break;
            case -3:    camara.zoom = 1/4f; break;
            case -4:    camara.zoom = 1/5f; break;
            case -5:    camara.zoom = 1/6f; break;
            case -6:    camara.zoom = 1/7f; break;
            case -7:    camara.zoom = 1/8f; break;
            case -8:    camara.zoom = 1/9f; break;
            case -9:    camara.zoom = 1/10f; break;
            case -10:   camara.zoom = 1/11f; break;
        }
        if (Mundo.player.nivelDeZoom >0 ) Mundo.player.nivelDeZoom = 0;
        if (Mundo.player.nivelDeZoom <-10) Mundo.player.nivelDeZoom = -10;
        return false; 
    }
}