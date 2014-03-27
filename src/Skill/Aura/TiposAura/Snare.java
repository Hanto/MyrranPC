package Skill.Aura.TiposAura;// Created by Hanto on 27/03/2014.

import Constantes.Skills.TipoAurasData;
import Skill.Aura.BDebuff;
import Skill.Aura.TipoAura;
import Skill.SkillStat;

public class Snare extends TipoAura
{
    public static final int STAT_Ralentizacion = 1;

    //Constructor:
    public Snare (String id)        { super (id); }
    public Snare ()                 { }

    @Override public void inicializarSkillStats()
    {
        isDebuff = TipoAurasData.SNARE_isDebuff;
        stacksMaximos = TipoAurasData.SNARE_Stacks_Maximos;
        setIcono(TipoAurasData.SNARE_Icono);

        skillStats = new SkillStat[2]; SkillStat stat;
        stat = new SkillStat (TipoAurasData.SNARE_Duracion_String, TipoAurasData.SNARE_Duracion_Valor); skillStats[STAT_Duracion]=stat;
        stat = new SkillStat (TipoAurasData.SNARE_Ralentizacion_String, TipoAurasData.SNARE_Ralentizacion_Valor); skillStats[STAT_Ralentizacion]=stat;

    }

    @Override public void inicializarSkillPixies ()
    {
        //FALTA FALTA FALTA:
        //FALTA FALTA FALTA:
    }

    @Override public void actualizarTick (BDebuff debuff)
    {


    }
}
