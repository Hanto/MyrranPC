package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Constantes.Skills.TipoSpellsData;
import Geo.Celda;
import Geo.Muro;
import Interfaces.Caster;
import Main.Mundo;
import Skill.SkillStat;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.math.Vector2;

public class EditarMuro extends TipoSpell
{
    public EditarMuro (String id)                   { super(id); }
    public EditarMuro ()                            { } 
    
    @Override public void inicializarSkillStats() 
    {
        skillStats = new SkillStat [1]; 
        SkillStat stat = new SkillStat  (TipoSpellsData.EDITARMURO_CastingTime_String, TipoSpellsData.EDITARMURO_CastingTime_Valor); skillStats[STAT_Cast]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Spell skill, Caster caster, float targetX, float targetY)
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
