package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Skill.Spell.TipoSpell;
import Constantes.MiscData;
import Constantes.Skills.TipoSpellsData;
import Geo.Celda;
import Geo.Muro;
import Main.Mundo;
import Actores.Mobs.Personaje;
import Skill.Spell.Spell;
import Skill.SkillStat;
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

    @Override public void ejecutarCasteo(Spell skill, Personaje caster, float targetX, float targetY) 
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
