package Skills;
import Graficos.Pixie;
import com.badlogic.gdx.utils.Array;
//* @author Ivan Delgado Huerta
public class SpellStat
{
    public String nombre;                           //Nombre del SpellStat: por ej: "Daño, velocidad, Casting Time"
    public float valorBase;                         //Valor Base del SpellStat: por ej: 100 de Daño
    private boolean isMejorable = false;            //Indica si es un SpellStat mejorable por Talentos    
    public int talentoMaximo;                       //numero de Talentos maximos que se pueden gastar en este SpellStat
    public int costeTalento;                        //coste por mejorar cada punto de talento
    public int bonoTalento;                         //Valor con el que mejora el valorBase por punto de talento
    
    //GET:
    public boolean isMejorable()                    { return isMejorable; }
    
    public SpellStat (String nombre, float valor)
    {   //constructor:
        this.nombre = nombre;
        valorBase = valor;
        isMejorable = false;
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
    public static class SpellPixie
    {
        public String tipoAnimacion;
        public Array<Pixie> pixieArray = new Array<>(); 
    }
}
    
    