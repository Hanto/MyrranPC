package Skills;
// @author Ivan Delgado Huerta

import Main.Mundo;
import Mobiles.Personaje;
import Skills.SpellStat.SpellPixie;
import Skills.TipoSkills.TipoAura;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Aura implements TipoAura
{     
    public static class BDebuff                         //Este es el objeto instanciado que acumulan los personajes cada vez
    {                                                   //que se les castea un Buff/debuff y contiene los datos del aura en si
        public int id;                                  //ID del Aura
        public float contadorTick = 0;                  //Si tienen efectos periodicos, solo se aplican cada Tick
        public float duracion = 0;                      //duracion actual del Aura
        public float duracionMax;                       //duracion maxima del Aura
        public Personaje caster;                        //Caster origen que creo el aura
        public Personaje target;                        //receptor del aura
        public boolean isDebuff;                        //booleano que identifica el aura como un Buff o un Debuff
        public TipoAura tipoAura;                       //es el interface que se encarga de ejecutar el metodo de actualizacion
    }                                                   //de tick por que cada Aura ejecuta su propio "programa"
    
    protected int id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
    
    protected SpellStat [] spellStats;
    protected SpellPixie [] spellPixies;
    protected Integer [] pixieSeleccionado;
    
    //SET
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    //GET
    public int getID()                                  { return id; }
    public String getNombre()                           { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public SpellStat [] spellStats ()                   { return spellStats; }
    public SpellPixie [] spellPixies ()                 { return spellPixies; }
    public Integer [] pixieSeleccionado ()              { return pixieSeleccionado; }
    public TextureRegion getIcono ()                    { return icono; }
    
    //CONSTRUCTOR:
    public Aura ()
    {   
        if (Mundo.listaDeAuras.size == 0) id = 0;
        else { id = Mundo.listaDeAuras.get(Mundo.listaDeAuras.size-1).id+1; }
    }
    
    public int auraExisteYEsDelCaster(Personaje caster, Personaje target)
    {   //recorremos todas las auras que tiene el target para ver si encontramos la que hemos aplicado
        for (int i=0; i<target.listaDeAuras.size;i++)
        {   //en caso de encontrarla, si el caster es el mismo, devolvemos su posicion para refrescar su duracion
            if (target.listaDeAuras.get(i).id == id && target.listaDeAuras.get(i).caster == caster)
            {   return i; }
        }
        //en todos los demas casos aplicaremos un nuevo debuff que stackeara con los anteriores, devolvemos -1 para indicar
        //que no hemos encontrado un aura que exista y sea del caster.
        return -1;
    }
}
