package Skill.Aura.TiposAura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Constantes.Skills.TipoAurasData;
import Interfaces.Vulnerable;
import Skill.Aura.BDebuff;
import Skill.Aura.TipoAura;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.Color;

public class Bomba extends TipoAura
{
    public static final int STAT_Daño = 1;
    
    //Constructor:
    public Bomba (String id)            { super(id); }
    public Bomba ()                     { }
    
    @Override
    public void inicializarSkillStats() 
    {
        isDebuff = true;
        stacksMaximos = 3;
        setIcono(TipoAurasData.BOMBA_Icono);
        
        skillStats = new SkillStat [2];
        SkillStat stat = new SkillStat  (TipoAurasData.BOMBA_Duracion_String, TipoAurasData.BOMBA_Duracion_Valor); skillStats[STAT_Duracion] = stat;       //DURACION
        stat = new SkillStat            (TipoAurasData.BOMBA_Daño_String, TipoAurasData.BOMBA_Daño_Valor); skillStats[STAT_Daño] = stat; //DAÑO
    }

    @Override
    public void inicializarSkillPixies() 
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override
    public void actualizarTick(BDebuff debuff) 
    {
        if (debuff.ticksAplicados == (int)(debuff.duracionMax/MiscData.duracionTick))
        {
            float daño = debuff.skillStats()[STAT_Daño].valorBase * debuff.stacks;
            if (debuff.target instanceof Vulnerable)
            {
                ((Vulnerable)debuff.target).modificarHPs((int) -daño, Color.RED);
            }
        }
    }
}
