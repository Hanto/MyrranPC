package Mobiles;

import Graficos.Nameplate;

/**
 * @author Ivan Delgado Huerta
 */

// la clase Personaje incluye a todos los seres vivos del juego, sean controlados por el jugador o por la maquina
public class Personaje extends Mob
{
    protected String nombre;
    protected int nivel;
    protected int actualHPs=1000;
    protected int maxHPs=1000;
    
    protected int spellSeleccionado = 0;
    protected int terrenoSeleccionado = 0;
    protected int capaTerrenoSeleccionada = 0;
    
    public boolean isCasteando = false;
    public float actualCastingTime = 0;
    public float totalCastingTime = 0;
    
    protected Nameplate nameplate;
    
    //GET
    public String getNombre()                   { return nombre; }
    public int getActualHPs()                   { return actualHPs; }
    public int getMaxHPs()                      { return maxHPs; }    
    public float getHPsPercent()                { return ((float)actualHPs/(float)maxHPs); }
    public float getCastingTimePercent()        { return ((float)actualCastingTime/(float)totalCastingTime); }
    public boolean isCasteando()                { return isCasteando; }
    public float getActualCastingTime ()        { return actualCastingTime; }
    public float getTotalCastingTime ()         { return totalCastingTime; }
    public int getSpellSeleccionado ()          { return spellSeleccionado; }
    public int getTerrenoSeleccionado ()        { return terrenoSeleccionado; }
    public int getCapaTerrenoSeleccionada ()    { return capaTerrenoSeleccionada; }
    //SET
    public void setNombre(String s)             { nombre = s; }
    public void setActualHPs (int i)            { actualHPs = i; }
    public void setMaxHPs (int i)               { maxHPs = i; }
    public void setIsCasteando (boolean b)      { isCasteando = b; }
    public void setActualCastingTime (float i)  { actualCastingTime = i; }
    public void setTotalCastingTime (float i)   { totalCastingTime = i; }
    public void setCastingTime (float actual, float total)  { actualCastingTime = actual; totalCastingTime = total;}
    public void setSpellSeleccionado (int spellID)          { spellSeleccionado = spellID; }
    public void setTerrenoSeleccionado (int terrenoID)      { terrenoSeleccionado = terrenoID; }
    public void setCapaTerrenoSelecionada (int capaTerreno) { capaTerrenoSeleccionada = capaTerreno; }
}
