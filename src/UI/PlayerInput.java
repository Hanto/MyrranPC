package UI;
// @author Ivan Delgado Huerta
import Main.Mundo;
import com.badlogic.gdx.InputProcessor;
public class PlayerInput implements InputProcessor
{
    @Override
    public boolean keyDown(int keycode) 
    {
        BarraSpells barraspells = Mundo.player.barraSpells;
                
        for (int i=0;i<barraspells.barra.size;i++)
        { if (barraspells.barra.get(i).keycode == keycode) Mundo.player.setSpellSeleccionado(barraspells.barra.get(i).spellID); }
        
        //Si seleccionamos el spell EditarTerreno tenemos que mostrar la barra de Terrenos:
        //Y si seleccionamos otro spell y la barra esta mostrada, hay que ocultarla:
        BarraTerrenos.mostrarOcultarBarraTerreno();
        return false;
    }
    
    @Override public boolean keyUp(int keycode)                                             { return false; }
    @Override public boolean keyTyped(char character)                                       { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button)   
    { 
        /*if (Mundo.player.getSpellSeleccionado() >=0) 
        {
            if (!Mundo.player.IsCasteando()) {Mundo.player.getPixiePC().setAnimacion(4, false, true, true); }
            Mundo.listaDeSpells.get(Mundo.player.getSpellSeleccionado()).castear(Mundo.player, Gdx.input.getX(), Gdx.input.getY());
        }*/
        return false;
    }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button)     { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer)            { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY)                           { return false; }
    @Override public boolean scrolled(int amount)                                           { return false; }
}
