package Skill;
import Graficos.Pixie;
import com.badlogic.gdx.utils.Array;
//* @author Ivan Delgado Huerta
public class SkillStat
{
    public String nombre;                           //Nombre del SkillStat: por ej: "Daño, velocidad, Casting Time"
    public float valorBase;                         //Valor Base del SkillStat: por ej: 100 de Daño
    private boolean isMejorable = false;            //Indica si es un SkillStat mejorable por Talentos    
    public int talentoMaximo;                       //numero de Talentos maximos que se pueden gastar en este SkillStat
    public int costeTalento;                        //coste por mejorar cada punto de talento
    public int bonoTalento;                         //Valor con el que mejora el valorBase por punto de talento
    
    //GET:
    public boolean isMejorable()                    { return isMejorable; }
    
    //CONSTRUCTOR:
    public SkillStat (String nombre, float valor)
    {   //constructor:
        this.nombre = nombre;
        valorBase = valor;
        isMejorable = false;
    }
    
    //CONSTRUCTOR: (constructor Copia)
    public SkillStat (SkillStat spellStat)
    {
        this.nombre = spellStat.nombre;
        this.valorBase = spellStat.valorBase;
        this.talentoMaximo = spellStat.talentoMaximo;
        this.costeTalento = spellStat.costeTalento;
        this.bonoTalento = spellStat.bonoTalento;
        this.isMejorable = spellStat.isMejorable;
    }
            
    public void setStat (String nombre, float valor)
    {
        this.nombre = nombre;
        valorBase = valor;
        isMejorable = false;
    }

    public void setTalentos (int max, int coste, int bono)
    {
        isMejorable = true;
        talentoMaximo = max;
        costeTalento = coste;
        bonoTalento = bono;
    }
        
    //SPELLPIXIE
    public static class SkillPixie
    {
        public String tipoAnimacion;
        public Array<Pixie> pixieArray = new Array<>(); 
    }
}
    
    