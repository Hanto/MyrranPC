package Mobiles;

import Constantes.MiscData;
import Constantes.SpellData;
import Main.Mundo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 * @author Ivan Delgado Huerta
 */

// Player representa de todos los personajes controlados por el jugador, el que esta siendo usado localmente desde esta maquina
public class Player extends PC
{
    //Teclas de Direccion para mover al personaje, remapeables:
    protected int teclaArriba = Keys.W;
    protected int teclaAbajo = Keys.S;
    protected int teclaIzquierda = Keys.A;
    protected int teclaDerecha = Keys.D;
    
    //CONSTRUCTOR:
    public Player (final int numRaza, int posX, int posY, String nombre)
    {
        super(numRaza, nombre);
        inicializar (posX, posY);
    }
        
    private void inicializar (int posX, int posY)
    {
        velocidadMax = MiscData.PLAYER_VelocidadMax_Pixeles_Sec;
        setPosition (posX, posY);
    }
    
    public void setPosition (float X, float Y)
    {
        x = X; y = Y;
        actor.setPosition((int)X, (int)Y);
    }
    public void setX (float X)
    {
        x = X;
        actor.setX((int)X);
    }
    public void setY (float Y)
    {
        y = Y;
        actor.setY((int)Y);
    }
    
    public void moverse (float delta)
    {       
        if (Gdx.input.isKeyPressed(teclaAbajo) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda))      
        {   //Sur
            setY((float)(y+ -(Math.sin(Math.toRadians(90d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(3, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda))
        {   //Norte
            setY((float)(y+ -(Math.sin(Math.toRadians(270d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(2, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaAbajo))       
        {   //Este
            setX((float)(x+ (Math.cos(Math.toRadians(0d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(1, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaIzquierda) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaAbajo))       
        {   //Oeste
            setX((float)(x+ (Math.cos(Math.toRadians(180d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(0, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaAbajo) && Gdx.input.isKeyPressed(teclaIzquierda))   
        {   //SurOeste
            setY((float)(y+ -(Math.sin(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta));
            setX((float)(x+ (Math.cos(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(0, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaAbajo) && Gdx.input.isKeyPressed(teclaDerecha))   
        {   //SurEste
            setY((float)(y+ -(Math.sin(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta));
            setX((float)(x+ (Math.cos(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(1, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && Gdx.input.isKeyPressed(teclaIzquierda))   
        {   //NorOeste
            setY((float)(y+ -(Math.sin(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta));
            setX((float)(x+ (Math.cos(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(0, false, false, false);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && Gdx.input.isKeyPressed(teclaDerecha))   
        {   //NorEste
            setY((float)(y+ -(Math.sin(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta));
            setX((float)(x+ (Math.cos(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta));
            pixiePC.setAnimacion(1, false, false, false);
        }       
        if (!Gdx.input.isKeyPressed(teclaAbajo) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda))
        {   //Animacion de Iddle (Sin pulsar ninguna tecla)
            pixiePC.setAnimacion(5, false, false, false);
        }
    }
    
    public void castear(float delta)
    {   //Actualizamos el cooldown de casteo:
        if ( isCasteando ) { actualCastingTime = actualCastingTime + delta; }
        if ( isCasteando && actualCastingTime >= totalCastingTime) { isCasteando = false; actualCastingTime = 0; totalCastingTime =0;}
        
        if (Gdx.input.isButtonPressed(Buttons.LEFT))
        {
            double alpha = Math.atan2(Gdx.input.getY() -(MiscData.WINDOW_Vertical_Resolution/2)+48/2, Gdx.input.getX() -(MiscData.WINDOW_Horizontal_Resolution/2)-48/2);     
            double angulo;
            angulo = Math.toDegrees(alpha+2*(Math.PI));
            angulo = angulo%360;
             
            if (!isCasteando) {pixiePC.setAnimacion(4, false, true, true); }
            Mundo.listaDeSpells.get(SpellData.FIREBOLT_ID).castear(this, Gdx.input.getX(), Gdx.input.getY());
            //Mundo.listaDeSpells.get(SpellData.TERRAFORMAR_ID).castear(this, Gdx.input.getX(), Gdx.input.getY());
            /*
            if (67.5d<=angulo && angulo<112.5d)     { pixiePC.setAnimacion(16, false); } //Abajo
            if (22.5d<=angulo && angulo<67.5d)      { pixiePC.setAnimacion(17, false); } //AbajoDcha
            if (112.5d<=angulo && angulo<157.5d)    { pixiePC.setAnimacion(15, false); } //AbajoIzda
            if (157.5d<=angulo && angulo<202.5d)    { pixiePC.setAnimacion(12, false); } //Izda
            if (22.5>angulo && angulo>=0)           { pixiePC.setAnimacion(14, false); } //Dcha
            if (337.5<=angulo && angulo<=360)       { pixiePC.setAnimacion(14, false); } //Dcha
            if (247.5<=angulo && angulo<292.5)      { pixiePC.setAnimacion(10, false); } //Arriba
            if (292.5<=angulo && angulo<337.5)      { pixiePC.setAnimacion(11, false); } //ArribaDcha
            if (202.5<=angulo && angulo<247.5)      { pixiePC.setAnimacion(9, false);  } //ArribaIzda   
            */
        }
    }
    
    public void actualizar (float delta)
    {        
        moverse(delta);
        castear (delta);
    }
    
}
