package Skill.Aura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Texto;
import Interfaces.Caster;
import Interfaces.Debuffeable;
import Resources.Recursos;
import Skill.SkillStat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

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

        int textoX, textoY;

        for (int i=0;i<target.getListaDeAuras().size;i++)
        {
            BDebuff debuff = target.getListaDeAuras().get(i);

            if (debuff.isDebuff())
            {   //numero de Stacks:
                if (debuff.stacks>1)
                {
                    textoX = (int) (numDebuffs * debuff.icono.getWidth() + debuff.icono.getWidth() / 2);
                    textoY = (int) (-debuff.icono.getHeight());
                    Texto.printTexto(Integer.toString(debuff.stacks), Recursos.font8, Color.WHITE, Color.BLACK, textoX, textoY,
                            Align.center, Align.bottom, 0, target.getDebuffIcons());
                }
                //Icono:
                debuff.icono.setPosition(numDebuffs*debuff.icono.getWidth(), 0);
                target.getDebuffIcons().addActor(debuff.icono);
                numDebuffs++;
            }
            if (!debuff.isDebuff())
            {   //numero de Stacks:
                if (debuff.stacks >1)
                {
                    textoX = (int) (-numBuffs * debuff.icono.getWidth() + debuff.icono.getWidth() / 2);
                    textoY = (int) (-debuff.icono.getHeight());
                    Texto.printTexto(Integer.toString(debuff.stacks), Recursos.font8, Color.WHITE, Color.BLACK, textoX, textoY,
                            Align.center, Align.bottom, 0, target.getBuffIcons());
                }
                //Icono:
                debuff.icono.setX(-numBuffs*debuff.icono.getWidth());
                target.getBuffIcons().addActor(debuff.icono);
                numBuffs++;
            }
        }
    }
}
