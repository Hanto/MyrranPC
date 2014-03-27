package Skill.Aura;
// @author Ivan Delgado Huerta

import Skill.SkillRecursos;
import Skill.SkillStat;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.UUID;

public abstract class TipoAura implements TipoAuraInterface
{
    public static final int STAT_Duracion = 0;
    
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
        
    protected SkillStat [] skillStats;
    protected SkillStat.SkillPixie [] skilllPixies;
    
    protected Boolean isDebuff = false;                 //booleano que identifica el aura como un Buff o un Debuff
    protected int stacksMaximos = 1;                    //numero de veces maximo que puede stackar el debuff consigo mismo
    
    //SET
    public void setId (String id)                       { this.id = id; }
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    public void setIcono (String iconoID)               { icono = SkillRecursos.listaDeAuraIconos.get(iconoID); }
    public void setIsDebuff (Boolean b)                 { isDebuff = b; }
    //GET
    public String getID()                               { return id; }
    public String getNombre()                           { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public TextureRegion getIcono ()                    { return icono; }
    public Boolean getIsDebuff ()                       { return isDebuff; }
    public int getStacksMaximos ()                      { return stacksMaximos; }
    
    public SkillStat [] skillStat ()                    { return skillStats; }
    public SkillStat.SkillPixie [] skilllPixie ()       { return skilllPixies; }
    
    //CONSTRUCTOR:
    public TipoAura (String id)
    {
        this.id = id;
        inicializarSkillStats();
        inicializarSkillPixies();
    }
    
    public TipoAura ()
    {   //Si no especificamos ID generamos un ID unico con la libreria UUID
        UUID idUUID = UUID.randomUUID();
        id = idUUID.toString();
        inicializarSkillStats(); 
        inicializarSkillPixies();
    }
}
