package Constantes.Skills;
// @author Ivan Delgado Huerta

import Constantes.MiscData;

public class TipoAurasData 
{
    //DOT:
    public static final String  DOT_ID = "Dot";
    public static final String  Dot_Duracion_String = "Duracion";
    public static final float   DOT_Duracion_Valor = 10*MiscData.duracionTick;  //Duracion de 10 Ticks (10 Segundos)
    public static final String  DOT_Daño_String = "Daño por Tick";
    public static final float   DOT_Daño_Valor = 10f;
    public static final String  DOT_Icono = "Dot";
    public static final boolean DOT_isDebuff = true;
    public static final int     DOT_Stacks_Maximos = 3;
    
    //BOMBA:
    public static final String  BOMBA_ID = "Bomba";
    public static final String  BOMBA_Duracion_String = "Tiempo Act.";
    public static final float   BOMBA_Duracion_Valor = 5*MiscData.duracionTick; //Duracion de 12 Ticks (12 Segundos)
    public static final String  BOMBA_Daño_String = "Daño";
    public static final float   BOMBA_Daño_Valor = 200f;
    public static final String  BOMBA_Icono = "Hot";
    public static final boolean BOMBA_isDebuff = true;
    public static final int     BOMBA_Stacks_Maximos = 3;

    //SNARE:
    public static final String  SNARE_ID = "Snare";
    public static final String  SNARE_Duracion_String = "Duracion";
    public static final float   SNARE_Duracion_Valor = 10*MiscData.duracionTick;
    public static final String  SNARE_Ralentizacion_String = "Ralentizacion";
    public static final float   SNARE_Ralentizacion_Valor = 0.5f;
    public static final String  SNARE_Icono = "Snare";
    public static final boolean SNARE_isDebuff = true;
    public static final int     SNARE_Stacks_Maximos = 3;

    
}
