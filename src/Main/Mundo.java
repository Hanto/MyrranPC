/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import Estaticos.Terreno;
import Mobiles.Player;
import com.badlogic.gdx.utils.Array;

/**
 * @author Ivan Delgado Huerta
 */
public class Mundo 
{
    public static Player player;
    
    public static Array<Terreno> listaDeTerrenos = new Array<>();
    public static Array<Player> listaDePlayers = new Array<>();
    
    public static void actualizarPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        {
            listaDePlayers.get(i).actualizar(delta);
        }
    }
    
}
