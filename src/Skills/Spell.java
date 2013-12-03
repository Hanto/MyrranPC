package Skills;

import Graficos.Recursos;
import Main.Mundo;
import Mobiles.Personaje;
import Skills.SpellStat.SpellPixie;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Ivan Delgado Huerta
 */
public class Spell 
{
    public interface TipoSpell
    {
        public void ejecutarCasteo (Spell spell, Personaje caster, float targetX, float targetY);
        public SpellStat [] getSpellStats ();
        public SpellPixie [] getSpellPixies();
    }
    
    protected int id;                                   //ID del Spell
    protected String nombre;                            //Nombre del Spell
    protected String descripcion;                       //Descripcion del Spell
    protected TipoSpell tipoSpell;                      //Tipo de Spell que determina el metodo que se ejecuta al castear
    protected TextureRegion icono;                      //Icono del Spell
    protected SpellStat [] spellStats;                  //Stats que componen el Spell
    protected Integer [] pixieSeleccionado;             //numero de animaciones(Pixie) diferentes que ejecuta el Spell
    //SET
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    public void setIcono (int iconoID)                  { icono = Recursos.listaIconos.get(iconoID); }
    //GET:
    public int getId ()                                 { return id; }
    public String getNombre ()                          { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public SpellStat [] spellStats()                    { return spellStats; }
    public SpellPixie [] spellPixies()                  { return tipoSpell.getSpellPixies(); }
    public Integer [] pixieSelecionado()                { return pixieSeleccionado; }
    public TextureRegion getIcono ()                    { return icono; }
    
    //CONSTRUCTOR:
    public Spell (TipoSpell tipospell)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Spell, y se copian sus Stats base
        tipoSpell = tipospell;
        spellStats = tipoSpell.getSpellStats();
        //Inicializamos el selecctor de Pixie, con el tamaño que tenga el array de pixies, y seleccionamos las animaciones 0dw
        if (tipoSpell.getSpellPixies() != null)
        {
            pixieSeleccionado = new Integer [tipoSpell.getSpellPixies().length];
            for (int i=0; i<pixieSeleccionado.length; i++) pixieSeleccionado[i]=0;
        }        
        if (Mundo.listaDeSpells.size == 0) id=0;
        else { id = Mundo.listaDeSpells.get(Mundo.listaDeSpells.size-1).id+1;}
    }
    
    public void castear (Personaje caster, int targetX, int targetY)
    {
        if (caster.isCasteando()) {}
        else 
        {   //Marcamos al personaje como Casteando, y actualizamos su tiempo de casteo con el que marque el Spell (Stat Slot 0)
            caster.setIsCasteando(true);                                    
            caster.setCastingTime(0f,spellStats[0].valorBase);
            tipoSpell.ejecutarCasteo(this, caster, targetX, targetY);
        }
    }
}
