package Skill.Skill;
// @author Ivan Delgado Huerta

import Recursos.Recursos;
import Mobiles.Mobs.Personaje;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Skill 
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
    
    protected TipoSkill tipoSkill;                      //Command Pattern: Codigo que se ejecuta al castear el skill
    
    protected SkillStat [] skillStats;                  //Stats concretos del skill
    protected Integer [] pixieSeleccionado;             //Pixies concretos que definen los graficos del spell
    
    //SET
    public void setId (String id)                       { this.id = id; }
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    public void setIcono (int iconoID)                  { icono = Recursos.listaIconos.get(iconoID); }
    //GET:
    public String getId ()                              { return id; }
    public String getNombre ()                          { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public TextureRegion getIcono ()                    { return icono; }
    
    public SkillStat [] skillStats ()                   { return skillStats; }
    public Integer [] pixieSeleccionado()               { return pixieSeleccionado; }
    
    
    //CONSTRUCTOR:
    public Skill (TipoSkill tiposkill)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Spell
        tipoSkill = tiposkill;
        
        //y se copian sus Stats base:
        skillStats = new SkillStat[tiposkill.skillStat().length];
        for (int i=0; i<skillStats.length;i++)
        {
            SkillStat statSkill = new SkillStat(tiposkill.skillStat()[i]);
            skillStats[i] = statSkill;       
        }
        //Inicializamos el selector de Pixie, con el tamaño que tenga el array de pixies, y seleccionamos las animaciones
        if (tiposkill.skilllPixie() != null)
        {
            pixieSeleccionado = new Integer [tiposkill.skilllPixie().length];
            for (int i=0; i<pixieSeleccionado.length; i++) pixieSeleccionado[i]=0;
        }     
    }
    
    public void castear (Personaje caster, int targetX, int targetY)
    {
        if (caster.isCasteando()) {}
        else 
        {   //Marcamos al personaje como Casteando, y actualizamos su tiempo de casteo con el que marque el Spell (Stat Slot 0)
            caster.setIsCasteando(true);                                    
            caster.setCastingTime(0f,skillStats[0].valorBase);
            tipoSkill.ejecutarCasteo(this, caster, targetX, targetY);
        }
    }
}
