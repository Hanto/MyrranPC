package Constantes;
 //* @author Ivan Delgado Huerta

public class SpellData 
{
    //TIPOSSPELL:
    //BOLT: (el casting time siempre debe ir primero)
    public static final int     BOLT_ID = 0;
    public static final String  BOLT_CastingTime_String = "Casting Time";
    public static final float   BOLT_CastingTime_Valor = 0.6f;
    public static final String  BOLT_Daño_String = "Daño";
    public static final float   BOLT_Daño_Valor = 100f;
    public static final String  BOLT_Velocidad_String = "Velocidad";
    public static final float   BOLT_Velocidad_Valor = 550f;
    public static final String  BOLT_Duracion_String = "Duracion";
    public static final float   BOLT_Duracion_Valor = 2.0f;
    public static final float   BOLT_Duracion_Animaciones = 0.15f;
    
    //EDITAR TERRENO:
    public static final int     EDITARTERRENO_ID = 1;
    public static final String  EDITARTERRENO_CastingTime_String = "Casting Time";
    public static final float   EDITARTERRENO_CastingTime_Valor = 0.01f;
    
    //EDITAR MURO:
    public static final int     EDITARMURO_ID = 2;
    public static final String  EDITARMURO_CastingTime_String = "Casting Time";
    public static final float   EDITARMURO_CastingTime_Valor = 0.005f;
    
    //SPELLS:
    //FIREBOLT:
    public static final int     FIREBOLT_ID = 0;
    public static final String  FIREBOLT_Nombre = "Fire";
    public static final String  FIREBOLT_Descripcion = "Poderosa rafaga de energia concentrada en una bola de calor que abrasara a los enemigos del conjurador";
    public static final int     FIREBOLT_Icono = 0;
    public static final int     FIREBOLT_Pixie_Casteo = 0;
    public static final int     FIREBOLT_Pixie_Proyectil = 0;
    
    //FROSTBOLT:
    public static final int     FROSTBOLT_ID = 1;
    public static final String  FROSTBOLT_Nombre = "Frost";
    public static final String  FROSTBOLT_Descripcion = "Poderosa rafaga de energia concentrada en una bola de calor que abrasara a los enemigos del conjurador";
    public static final int     FROSTBOLT_Icono = 1;
    public static final int     FROSTBOLT_Pixie_Casteo = 1;
    public static final int     FROSTBOLT_Pixie_Proyectil = 1;
    
    //Terraformar
    public static final int     TERRAFORMAR_ID = 2;
    public static final String  TERRAFORMAR_Nombre = "Terra";
    public static final String  TERRAFORMAR_Descripcion = "Terraforma el terreno";
    public static final int     TERRAFORMAR_Icono = 2;
    
    //Muroformar
    public static final int     MUROFORMAR_ID = 3;
    public static final String  MUROFORMAR_Nombre = "Muro";
    public static final String  MUROFORMAR_Descripcion = "Muroforma el terreno";
    public static final int     MUROFORMAR_Icono = 3;
}
