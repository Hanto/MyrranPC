package Mobiles;
import Constantes.MiscData;
import MobileEstados.PlayerEstado;
import Pantallas.PantallaJuego;
import UI.BarraSpells;
import com.badlogic.gdx.Input.Keys;
//* @author Ivan Delgado Huerta
// Player representa de todos los personajes controlados por el jugador, el que esta siendo usado localmente desde esta maquina
public class Player extends PC
{
    public BarraSpells barraSpells = new BarraSpells(2, 9);
    public boolean mostrarBarraTerrenos = false;
    public int nivelDeZoom = 0;
    
    protected PlayerEstado estado;
    public int numAnimacion = 0;
    public boolean castear = false;
    public boolean disparar = false;
    public boolean irArriba = false;
    public boolean irAbajo = false;
    public boolean irIzquierda = false;
    public boolean irDerecha = false;
    
    public boolean rumboNorte = false;
    public boolean rumboSur = false;
    public boolean rumboEste = false;
    public boolean rumboOeste = false;
    
    //Teclas de Direccion para mover al personaje, remapeables:
    protected int teclaArriba = Keys.W;
    protected int teclaAbajo = Keys.S;
    protected int teclaIzquierda = Keys.A;
    protected int teclaDerecha = Keys.D;
    
    public void procesarInput()                 { estado.procesarInput(); }
    
    //CONSTRUCTOR:
    public Player (final int numRaza, int posX, int posY, String nombre)
    {   super(numRaza, nombre);
        inicializar (posX, posY);
        estado = new PlayerEstado(this);
    }
        
    private void inicializar (int posX, int posY)
    {   velocidadMax = MiscData.PLAYER_VelocidadMax_Pixeles_Sec;
        setPosition (posX, posY);
        barraSpells.setPosition(MiscData.WINDOW_Horizontal_Resolution/2-barraSpells.getWidth()/2, 5);
        PantallaJuego.stageUI.addActor(barraSpells);
    }
    
    public void setPosition (float X, float Y)
    {   x = X; y = Y;
        actor.setPosition((int)X, (int)Y);
    }
    public void setX (float X)
    {   x = X;
        actor.setX((int)X);
    }
    public void setY (float Y)
    {   y = Y;
        actor.setY((int)Y);
    }
    
    public void moverse (float delta)
    {   //Sur
        if (rumboSur && !rumboEste && !rumboOeste)      
        { setY((float)(y+ -(Math.sin(Math.toRadians(90d))*velocidadMax)*velocidadMod*delta)); }
        //Norte
        else if (rumboNorte && !rumboEste && !rumboOeste)
        { setY((float)(y+ -(Math.sin(Math.toRadians(270d))*velocidadMax)*velocidadMod*delta)); }
        //Este
        else if (rumboEste && !rumboNorte && !rumboSur)       
        { setX((float)(x+ (Math.cos(Math.toRadians(0d))*velocidadMax)*velocidadMod*delta)); }
        //Oeste
        else if (rumboOeste && !rumboNorte && !rumboSur)       
        { setX((float)(x+ (Math.cos(Math.toRadians(180d))*velocidadMax)*velocidadMod*delta)); }
        //SurOeste
        else if (rumboSur && rumboOeste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta)); }
        //SurEste
        else if (rumboSur && rumboEste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta)); }
        //NorOeste
        else if (rumboNorte && rumboOeste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta)); }
        //NorEste
        else if (rumboNorte && rumboEste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta)); }
    }
    
    public void actualizarCastingTime(float delta)
    {   //Actualizamos el cooldown de casteo:
        if ( isCasteando ) { actualCastingTime = actualCastingTime + delta; }
        if ( isCasteando && actualCastingTime >= totalCastingTime) { isCasteando = false; actualCastingTime = 0; totalCastingTime =0;}
    }
    
    public void castear ()
    {
        if (castear && !isCasteando && spellSeleccionado >= 0) { estado.procesarInput(); }
    }
    
    public void actualizar (float delta)
    {   
        estado.actualizar();
        moverse(delta);
        actualizarCastingTime (delta);
        castear();
    }
}