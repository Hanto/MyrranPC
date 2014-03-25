package Skill.Aura.TiposAura;
// @author Ivan Delgado Huerta

import Constantes.Skills.TipoAurasData;
import Actores.Mobs.Personaje;
import Skill.Aura.TipoAura;
import Skill.Aura.BDebuff;
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
        isDebuff = true;
        stacksMaximos = 3;
        
        skillStats = new SkillStat [2];
        SkillStat stat = new SkillStat  (TipoAurasData.Dot_Duracion_String, TipoAurasData.DOT_Duracion_Valor); skillStats[STAT_Duracion] = stat;       //DURACION
        stat = new SkillStat            (TipoAurasData.DOT_Daño_String, TipoAurasData.DOT_Daño_Valor); skillStats[STAT_Daño] = stat; //DAÑO
    }

    @Override public void inicializarSkillPixies() 
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override public void actualizarTick(BDebuff debuff) 
    {
        float dañoPorTick = debuff.skillStats()[STAT_Daño].valorBase * debuff.stacks;
        Personaje target = debuff.target;
                
        target.modificarHps((int)-dañoPorTick, Color.RED);
    }
} 