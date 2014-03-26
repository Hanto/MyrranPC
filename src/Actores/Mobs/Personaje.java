package Actores.Mobs;

import Graficos.Nameplate;
import Actores.Mob;
import Graficos.Texto;
import Interfaces.Caster;
import Interfaces.Vulnerable;
import Interfaces.Debuffeable;
import Resources.Recursos;
import Skill.Aura.BDebuff;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

/**
 * @author Ivan Delgado Huerta
 */

// la clase Personaje incluye a todos los seres vivos del juego, sean controlados por el jugador o por la maquina
public class Personaje extends Mob implements Vulnerable, Debuffeable, Caster
{
    protected String nombre;
    protected int nivel;
    protected int actualHPs=1000;
    protected int maxHPs=1000;
    
    protected String spellSeleccionado = "";
    protected int terrenoSeleccionado = 0;
    protected int capaTerrenoSeleccionada = 0;
    
    public boolean isCasteando = false;
    public float actualCastingTime = 0;
    public float totalCastingTime = 0;
    
    protected Nameplate nameplate;                          //Extiende la clase grupo, y sabe autidibujarse
    protected Group debuffs = new Group();                  //Contiene los iconos de los debuffs
    protected Group buffs = new Group();                    //Contiene los iconos de los buffs
    
    public Array<BDebuff> listaDeAuras = new Array<>();
    
    //GET
    public String getNombre()                               { return nombre; }
    public int getActualHPs()                               { return actualHPs; }
    public int getMaxHPs()                                  { return maxHPs; }    
    public float getHPsPercent()                            { return ((float)actualHPs/(float)maxHPs); }
    public float getCastingTimePercent()                    { return (actualCastingTime/totalCastingTime); }
    public float getActualCastingTime ()                    { return actualCastingTime; }
    public float getTotalCastingTime ()                     { return totalCastingTime; }
    public String getSpellSeleccionado ()                   { return spellSeleccionado; }
    public int getTerrenoSeleccionado ()                    { return terrenoSeleccionado; }
    //SET
    public void setNombre(String s)                         { nombre = s; }
    public void setMaxHPs (int i)                           { maxHPs = i; }
    public void setIsCasteando (boolean b)                  { isCasteando = b; }
    public void setActualCastingTime (float i)              { actualCastingTime = i; }
    public void setTotalCastingTime (float i)               { totalCastingTime = i; }
    public void setCastingTime (float actual, float total)  { actualCastingTime = actual; totalCastingTime = total;}
    public void setSpellSeleccionado (String spellID)       { spellSeleccionado = spellID; }
    public void setTerrenoSeleccionado (int terrenoID)      { terrenoSeleccionado = terrenoID; }
    public void setCapaTerrenoSelecionada (int capaTerreno) { capaTerrenoSeleccionada = capaTerreno; }
        
    public void setActualHPs (int i)                        
    { 
        if (i>=maxHPs) {actualHPs = maxHPs;} 
        else if (i<= 0) {actualHPs = 0; }
        else {actualHPs = i;} 
    }

    @Override public void modificarHPs(int HPs, Color color)
    {
        setActualHPs(actualHPs+HPs);
        Texto texto = new Graficos.Texto(Integer.toString(HPs), Recursos.font14, color, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.setPosition(actor.getWidth()/2+(float)Math.random()*30-15, actor.getHeight()+15);
        texto.scrollingCombatText(actor, 2f);
    }

    @Override public Array<BDebuff> getListaDeAuras()
    {
        return listaDeAuras;
    }

    @Override public void setCastingTime(float totalCastingTime)
    {
        isCasteando = true;
        setCastingTime(0, totalCastingTime);
    }

    @Override public boolean isCasteando()                  { return isCasteando; }
    @Override public int getCapaTerrenoSeleccionada ()      { return capaTerrenoSeleccionada; }
    @Override public Group getDebuffIcons ()                { return debuffs; }
    @Override public Group getBuffIcons ()                  { return buffs; }
}
