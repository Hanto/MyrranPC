package Skill.Aura;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Interfaces.Caster;
import Interfaces.Debuffeable;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Aura
{   
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
    
    protected TipoAura tipoAura;
    
    protected SkillStat [] skillStats;
    protected Integer [] pixieSeleccionado;
    
    protected Boolean isDebuff = false;
    protected int stacksMaximos = 0;
    
    //SET
    public void setId (String id)                       { this.id = id; }
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    //GET
    public String getID()                               { return id; }
    public String getNombre()                           { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public SkillStat [] skillStats ()                   { return skillStats; }
    public Integer [] pixieSeleccionado ()              { return pixieSeleccionado; }
    public TextureRegion getIcono ()                    { return icono; }
    public boolean isDebuff ()                          { return isDebuff; }
    public int getStacksMaximos ()                      { return stacksMaximos; }
    
    //CONSTRUCTOR:
    public Aura (TipoAura tipoaura)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Aura
        tipoAura = tipoaura;
        
        //y se copian sus Stats base:
        skillStats = new SkillStat[tipoaura.skillStat().length];
        for (int i=0; i<skillStats.length;i++)
        {
            SkillStat statSkill = new SkillStat(tipoaura.skillStat()[i]);
            skillStats[i] = statSkill;       
        }
        isDebuff = tipoaura.getIsDebuff();
        stacksMaximos = tipoaura.getStacksMaximos();
        icono = tipoaura.getIcono();
        
        //Inicializamos el selector de Pixie, con el tamaño que tenga el array de pixies, y seleccionamos las animaciones
        if (tipoaura.skilllPixie() != null)
        {
            pixieSeleccionado = new Integer [tipoaura.skilllPixie().length];
            for (int i=0; i<pixieSeleccionado.length; i++) pixieSeleccionado[i]=0;
        } 
    }
    
    private int auraExisteYEsDelCaster(Caster caster, Debuffeable target)
    {   //recorremos todas las auras que tiene el target para ver si encontramos la que hemos aplicado
        for (int i=0; i<target.getListaDeAuras().size;i++)
        {   //en caso de encontrarla, si el caster es el mismo, devolvemos su posicion para refrescar su duracion
            if (target.getListaDeAuras().get(i).getID().equals(id) && target.getListaDeAuras().get(i).caster == caster)
            {   return i; }
        }
        //en todos los demas casos aplicaremos un nuevo debuff que stackeara con los anteriores, devolvemos -1 para indicar
        //que no hemos encontrado un aura que exista y sea del caster.
        return -1;
    }
    
    public void aplicarAura(Caster caster, Debuffeable target)
    {
        int numAura = auraExisteYEsDelCaster(caster, target);
        
        if (numAura == -1) //-1 significa que no la ha encontrado, por tanto aplicamos un aura nueva:
        {
            BDebuff debuff = new BDebuff();
            debuff.ticksAplicados = 0;
            debuff.stacks = 1;
            debuff.duracion = 0;
            debuff.duracionMax = skillStats()[0].valorBase;
            debuff.icono = new Image(icono);
            debuff.caster = caster;
            debuff.target = target;
            debuff.setAura(this);
            
            target.getListaDeAuras().add(debuff);
        }
        else //en caso contrario es que hay un aura que pertecene al caster de ese tipo, deberemos refrescarla
        {   //y si stackea consigo misma y esta por debajo del maximo, aumentar sus stacks
            BDebuff debuff = target.getListaDeAuras().get(numAura);
            debuff.ticksAplicados = 0;
            //Hacemos el modulo de la duracion del Tick, para que no se pierda el tiempo que lleva pasado desde el ultimo tick
            debuff.duracion = debuff.duracion%MiscData.duracionTick;
            if (debuff.stacks < stacksMaximos) {debuff.stacks++;}
        }
        BDebuff.actualizarIconosBDbuff(target);
    }
    
    public void actualizarTick (BDebuff debuff)
    {   tipoAura.actualizarTick(debuff); }
}
