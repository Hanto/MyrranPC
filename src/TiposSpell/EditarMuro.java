/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiposSpell;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Constantes.SpellData;
import Geo.Celda;
import Graficos.Muro;
import Main.Mundo;
import Mobiles.Personaje;
import Skills.Spell;
import Skills.SpellStat;

public class EditarMuro extends AbstractTipoSpell
{
    public EditarMuro ()
    {
        spellStats = new SpellStat [1]; 
        SpellStat stat = new SpellStat  (SpellData.EDITARMURO_CastingTime_String, SpellData.EDITARMURO_CastingTime_Valor); spellStats[0]=stat;//CAST
    }
    
    @Override public void ejecutarCasteo(Spell spell, Personaje caster, float targetX, float targetY) 
    {
        convertirCoordenadasDestino(caster, targetX, targetY);
        convertirCoordenadasANumeroDeTile();
        
        int x = (int)destinoX;
        int y = (int)destinoY;
        
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
