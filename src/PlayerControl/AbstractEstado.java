/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerControl;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Main.Mundo;
import Mobiles.Player;
import PlayerControl.PlayerControl.EstadoPlayer;
import com.badlogic.gdx.Gdx;

public abstract class AbstractEstado implements EstadoPlayer
{
       
    public void inicializar (PlayerControl playerControl)
    {
        
    }
    
    @Override public void actualizar (PlayerControl estado, float delta)
    { 
        Player player = estado.player;
        
        if ( player.IsCasteando() ) { player.setActualCastingTime (player.getActualCastingTime()+delta); }
        if ( player.IsCasteando() && player.getActualCastingTime() >= player.getTotalCastingTime()) 
        { player.setIsCasteando(false); player.setActualCastingTime(0); player.setTotalCastingTime(0); }
        
        if (player.castear && !player.IsCasteando() && player.getSpellSeleccionado() >=0)
        {
            double alpha = Math.atan2(Gdx.input.getY() -(MiscData.WINDOW_Vertical_Resolution/2)+48/2, Gdx.input.getX() -(MiscData.WINDOW_Horizontal_Resolution/2)-48/2);     
            double angulo;
            angulo = Math.toDegrees(alpha+2*(Math.PI));
            angulo = angulo%360;
            Mundo.listaDeSpells.get(player.getSpellSeleccionado()).castear(player, Gdx.input.getX(), Gdx.input.getY());
            procesarInput(estado);
        }
    }
}
