package Skill.Aura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Interfaces.Caster;
import Interfaces.Debuffeable;
import Skill.SkillStat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BDebuff 
{
    public int ticksAplicados = 0;                  //Si tienen efectos periodicos, solo se aplican cada Tick
    public int stacks = 0;                          //numero de veces que el debuff ha stackeado consigo mismo
    public float duracion = 0;                      //duracion actual del Aura
    public float duracionMax;                       //duracion maxima del Aura
    public Image icono;
    public Caster caster;                           //Caster origen que creo el aura
    public Debuffeable target;                      //receptor del aura
    
    private Aura aura;                               //es el interface que se encarga de ejecutar el metodo de actualizacion

    public void setAura (Aura aura)                 { this.aura = aura; }

    public String getID ()                          { return aura.getID(); }
    public boolean isDebuff ()                      { return aura.isDebuff(); }
    public SkillStat [] skillStats ()               { return aura.skillStats(); }
    
    public void actualizarDebuff ()
    { 
        duracion = duracion + Gdx.graphics.getDeltaTime();
        
        int tickActual = (int)(duracion / MiscData.duracionTick);
        
        for (int i=ticksAplicados; i<tickActual;i++)
        {   
            ticksAplicados++;
            aura.actualizarTick(this); 
        }

        if (duracion >= duracionMax)
        {
            target.getListaDeAuras().removeValue(this, true);
            actualizarIconosBDbuff(target);
        }
    }

    public static void actualizarIconosBDbuff (Debuffeable target)
    {
        target.getBuffIcons().clearChildren();
        target.getDebuffIcons().clearChildren();

        int numDebuffs=0;
        int numBuffs = 1;

        for (int i=0;i<target.getListaDeAuras().size;i++)
        {
            BDebuff debuff = target.getListaDeAuras().get(i);

            if (debuff.isDebuff())
            {
                debuff.icono.setPosition(numDebuffs*debuff.icono.getWidth(), 0);
                target.getDebuffIcons().addActor(debuff.icono);
                numDebuffs++;
            }
            if (!debuff.isDebuff())
            {
                debuff.icono.setX(-numBuffs*debuff.icono.getWidth());
                target.getBuffIcons().addActor(debuff.icono);
                numBuffs++;
            }
        }
    }
}
