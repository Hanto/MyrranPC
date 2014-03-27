package Skill.Aura.TiposAura;
// @author Ivan Delgado Huerta

import Constantes.Skills.TipoAurasData;
import Interfaces.Vulnerable;
import Skill.Aura.BDebuff;
import Skill.Aura.TipoAura;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.Color;

public class Dot extends TipoAura
{   
    public static final int STAT_Daño = 1;
    
    //Constructor:
    public Dot (String id)          { super(id); }
    public Dot ()                   { }
    
    @Override public void inicializarSkillStats() 
    {
        isDebuff = TipoAurasData.DOT_isDebuff;
        stacksMaximos = TipoAurasData.DOT_Stacks_Maximos;
        setIcono(TipoAurasData.DOT_Icono);
        
        skillStats = new SkillStat [2]; SkillStat stat;
        stat = new SkillStat (TipoAurasData.Dot_Duracion_String, TipoAurasData.DOT_Duracion_Valor); skillStats[STAT_Duracion] = stat;       //DURACION
        stat = new SkillStat (TipoAurasData.DOT_Daño_String, TipoAurasData.DOT_Daño_Valor); skillStats[STAT_Daño] = stat; //DAÑO
    }

    @Override public void inicializarSkillPixies() 
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override public void actualizarTick(BDebuff debuff) 
    {
        float dañoPorTick = debuff.skillStats()[STAT_Daño].valorBase * debuff.stacks;
        if (debuff.target instanceof Vulnerable)
        {
            ((Vulnerable)debuff.target).modificarHPs((int)-dañoPorTick, Color.RED);
        }

    }
} 