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
    
    //BOMBA:
    public static final String  BOMBA_ID = "Bomba";
    public static final String  BOMBA_Duracion_String = "Tiempo de Activacion";
    public static final float   BOMBA_Duracion_Valor = 5*MiscData.duracionTick; //Duracion de 12 Ticks (12 Segundos)
    public static final String  BOMBA_Daño_String = "Daño";
    public static final float   BOMBA_Daño_Valor = 200f;
    public static final String  BOMBA_Icono = "Hot";
    
}
