package TiposAura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Mobiles.Personaje;
import Skills.Aura;
import Skills.SpellStat;
import com.badlogic.gdx.Gdx;

public class Dot extends Aura
{
    public Dot ()
    {   //CONSTRUCTOR:
        spellStats = new SpellStat [2];
        SpellStat stat = new SpellStat  ("Duracion",10.0f); spellStats[0] = stat;       //DURACION
        stat = new SpellStat            ("Daño por Tick", 10.0f); spellStats[1] = stat; //DAÑO
        
        //Falta el SPELLPIXIE:
        //FALTA FALTA FALTA
        //FALTA FALTA FALTA
        //FALTA FALTA FALTA
    }
    
    @Override
    public void aplicarAura(Aura aura, Personaje caster, Personaje target) 
    {
        int numAura = auraExisteYEsDelCaster(caster, target);
        
        if (numAura == -1) //-1 significa que no la ha encontrado, por tanto aplicamos un aura nueva:
        {
            BDebuff debuff = new BDebuff();
            debuff.id = aura.getID();
            debuff.contadorTick = 0;
            debuff.duracion = 0;
            debuff.duracionMax = aura.spellStats()[0].valorBase;
            debuff.caster = caster;
            debuff.target = target;
            debuff.isDebuff = true;
            debuff.tipoAura = this;
            
            target.listaDeAuras.add(debuff);
        }
        else //en caso contrario es que hay un aura que pertecene al caster de ese tipo, deberemos refrescarla
        {   target.listaDeAuras.get(numAura).duracion = 0; }
    }
    
    @Override
    public void actualizarAura(BDebuff debuff) 
    {
        float dañoPorTick = this.spellStats()[1].valorBase;
        Personaje target = debuff.target;
                
        debuff.duracion = debuff.duracion + Gdx.graphics.getDeltaTime();
        debuff.contadorTick = debuff.contadorTick + Gdx.graphics.getDeltaTime();
        
        if (debuff.contadorTick >= MiscData.duracionTick)
        {
            debuff.contadorTick = debuff.contadorTick - MiscData.duracionTick;
            target.setActualHPs(target.getActualHPs()- (int)dañoPorTick);
            
            //Falta mostrar el SCROLLING COMBAT TEXT
            //FALTA FALTA FALTA
            //FALTA FALTA FALTA
            //FALTA FALTA FALTA
        }
        if (debuff.duracion >= debuff.duracionMax)  { target.listaDeAuras.removeValue(debuff, true); }
    }
} 
