package Skill.Aura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Mobiles.Mobs.Personaje;
import Skill.SkillStat;
import com.badlogic.gdx.Gdx;

public class BDebuff 
{
    public int ticksAplicados = 0;                  //Si tienen efectos periodicos, solo se aplican cada Tick
    public int stacks = 0;                          //numero de veces que el debuff ha stackeado consigo mismo
    public float duracion = 0;                      //duracion actual del Aura
    public float duracionMax;                       //duracion maxima del Aura
    public Personaje caster;                        //Caster origen que creo el aura
    public Personaje target;                        //receptor del aura
    
    private Aura aura;                               //es el interface que se encarga de ejecutar el metodo de actualizacion
    
    public String getID ()                          { return aura.getID(); }
    public void setAura (Aura aura)                 { this.aura = aura; }
    public SkillStat [] skillStats ()               { return aura.skillStats(); }
    
    public void actualizarDebuff ()                     
    { 
        duracion = duracion + Gdx.graphics.getDeltaTime();
        
        int tickActual = Math.round(duracion / MiscData.duracionTick);
        
        for (int i=ticksAplicados; i<tickActual;i++)
        {   aura.actualizarTick(this); }
        
        if (duracion >= duracionMax)  { target.listaDeAuras.removeValue(this, true); }
    }
}
