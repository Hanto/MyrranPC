package Skill.Aura.TiposAura;
// @author Ivan Delgado Huerta

import Constantes.Skills.TipoAurasData;
import Mobiles.Mobs.Personaje;
import Skill.Aura.TipoAura;
import Skill.Aura.BDebuff;
import Skill.SkillStat;

public class Dot extends TipoAura
{   //Constructor:
    public Dot (String id)          { super(id); }
    public Dot ()                   { }
    
    @Override public void inicializarSkillStats() 
    {
        isDebuff = true;
        stacksMaximos = 2;
        
        skillStats = new SkillStat [2];
        SkillStat stat = new SkillStat  (TipoAurasData.Dot_Duracion_String, TipoAurasData.DOT_Daño_Valor); skillStats[0] = stat;       //DURACION
        stat = new SkillStat            (TipoAurasData.DOT_Daño_String, TipoAurasData.DOT_Daño_Valor); skillStats[1] = stat; //DAÑO
    }

    @Override public void inicializarSkillPixies() 
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override public void actualizarTick(BDebuff debuff) 
    {
        float dañoPorTick = debuff.skillStats()[1].valorBase * debuff.stacks;
        Personaje target = debuff.target;
                
        target.setActualHPs(target.getActualHPs()- (int)dañoPorTick);
            
        //Falta mostrar el SCROLLING COMBAT TEXT
        //FALTA FALTA FALTA
        //FALTA FALTA FALTA
    }
} 
