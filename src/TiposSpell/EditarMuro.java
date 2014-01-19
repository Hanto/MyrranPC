/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiposSpell;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Constantes.SpellData;
import Geo.Celda;
import Geo.Muro;
import Main.Mundo;
import Mobiles.Personaje;
import Skills.Spell;
import Skills.SpellStat;
import com.badlogic.gdx.math.Vector2;

public class EditarMuro extends AbstractTipoSpell
{
    public EditarMuro ()
    {
        spellStats = new SpellStat [1]; 
        SpellStat stat = new SpellStat  (SpellData.EDITARMURO_CastingTime_String, SpellData.EDITARMURO_CastingTime_Valor); spellStats[0]=stat;//CAST
    }
    
    @Override public void ejecutarCasteo(Spell spell, Personaje caster, float targetX, float targetY) 
    {
        Vector2 destino = convertirCoordenadasDestino(caster, targetX, targetY);
        destino = convertirCoordenadasANumeroDeTile(destino);
        
        int x = (int)destino.x;
        int y = (int)destino.y;
        
        Celda celda = new Celda (Mundo.mapa[x][y]);
        
        if (celda.getMuroID() != 0)
        {
            Mundo.mapa[x][y] = celda;
            celda.setMuro(0);
            
            Muro muro = new Muro(Mundo.listaDeMuros.get(0));
            celda.muro = muro;
            Mundo.stageMundo.addActor(muro);
            muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
        }
    }

}
