package Skill.Spell;
// @author Ivan Delgado Huerta

import Interfaces.Caster;
import Interfaces.Debuffeable;
import Skill.Aura.Aura;
import Skill.SkillBook;
import Skill.SkillRecursos;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Spell 
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
    
    protected TipoSpell tipoSpell;                      //Command Pattern: Codigo que se ejecuta al castear el skill
    
    protected SkillStat [] skillStats;                  //Stats concretos del skill
    protected Integer [] pixieSeleccionado;             //Pixies concretos que definen los graficos del spell
    protected Array<String> listaDeAurasQueAplica = new Array<>(); //IDs de las aura que se aplican cuando el spell hace efecto
    
    //SET
    public void setId (String id)                       { this.id = id; }
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    public void setIcono (String iconoID)               { icono = SkillRecursos.listaDeSpellIconos.get(iconoID); }
    //GET:
    public String getId ()                              { return id; }
    public String getNombre ()                          { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public TextureRegion getIcono ()                    { return icono; }
    public Array<String> getListaDeAurasQueAplica ()    { return listaDeAurasQueAplica; }
    
    public SkillStat [] skillStats ()                   { return skillStats; }
    public Integer [] pixieSeleccionado()               { return pixieSeleccionado; }
    
    
    //CONSTRUCTOR:
    public Spell (TipoSpell tipospell)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Spell
        tipoSpell = tipospell;
        
        //y se copian sus Stats base:
        skillStats = new SkillStat[tipospell.skillStat().length];
        for (int i=0; i<skillStats.length;i++)
        {
            SkillStat statSkill = new SkillStat(tipospell.skillStat()[i]);
            skillStats[i] = statSkill;       
        }
        icono = tipospell.getIcono();

        //Inicializamos el selector de Pixie, con el tamaño que tenga el array de pixies, y seleccionamos las animaciones
        if (tipospell.skilllPixie() != null)
        {
            pixieSeleccionado = new Integer [tipospell.skilllPixie().length];
            for (int i=0; i<pixieSeleccionado.length; i++) pixieSeleccionado[i]=0;
        }
    }
    
    public void castear (Caster caster, int targetX, int targetY)
    {
        if (caster.isCasteando()) { }
        else 
        {   //Marcamos al personaje como Casteando, y actualizamos su tiempo de casteo con el que marque el Spell (Stat Slot 0)
            caster.setCastingTime(skillStats[TipoSpell.STAT_Cast].valorBase);
            tipoSpell.ejecutarCasteo(this, caster, targetX, targetY);
        }
    }
    
    public void añadirAura (Aura aura)
    {   if (!listaDeAurasQueAplica.contains(aura.getID(), false)) listaDeAurasQueAplica.add(aura.getID()); }
    
    public void aplicarAuras (Caster caster, Debuffeable target)
    {   for (int i=0;i<listaDeAurasQueAplica.size;i++)
        {   SkillBook.listaDeAuras.get(listaDeAurasQueAplica.get(i)).aplicarAura(caster, target); }
    }
}
