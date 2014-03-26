package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Constantes.Skills.TipoSpellsData;
import Interfaces.Caster;
import Interfaces.Dañable;
import Interfaces.Debuffeable;
import Skill.SkillStat;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.graphics.Color;

public class Heal extends TipoSpell
{
    public static final int STAT_Curacion = 1;
    
    public Heal (String id)        { super(id); }
    public Heal ()                 { }
    
    @Override
    public void inicializarSkillStats() 
    {
        skillStats = new SkillStat [2];
        SkillStat stat;
        stat = new SkillStat (TipoSpellsData.HEAL_CastingTime_String, TipoSpellsData.HEAL_CastingTime_Valor); skillStats[STAT_Cast]=stat;//CAST
        stat = new SkillStat (TipoSpellsData.HEAL_Curacion_String, TipoSpellsData.HEAL_Curacion_Valor); skillStats[STAT_Curacion]=stat;  //CURACION
    }

    @Override
    public void inicializarSkillPixies() 
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override
    public void ejecutarCasteo(Spell spell, Caster caster, float targetX, float targetY)
    {
        float curacion = spell.skillStats()[STAT_Curacion].valorBase;
        if (caster instanceof Dañable)
        {
            ((Dañable)caster).modificarHPs(Math.round(curacion), Color.GREEN);

        }
        if (caster instanceof Debuffeable)
        {
            spell.aplicarAuras(caster, ((Debuffeable)caster));
        }
    }

}
