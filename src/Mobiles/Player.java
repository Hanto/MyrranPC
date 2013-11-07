package Mobiles;

import Constantes.MiscData;
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
    
    //Constructor, que crea un hilo independiente para controlar al Jugador
    public Player (final int numRaza)
    {
        super (numRaza);
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                inicializar(numRaza);
                while (true) 
                {
                    actualizar();
                    try {Thread.sleep(MiscData.MOB_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
            }
        }).start();
    }
    
    public void inicializar (int numRaza)
    {
        velocidadMax = 1.0;
        pixie.setPosition(0, 0);
        pixie.setUsarCoordenadasCamara(true);
    }
    
    public void moverse ()
    {       
        if (Gdx.input.isKeyPressed(teclaAbajo) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda))      
        {   //Sur
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(90d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(7, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda))
        {   //Norte
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(270d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(1, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaAbajo))       
        {   //Este
            X=  (float)(X+ (Math.cos(Math.toRadians(0d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(5, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaIzquierda) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaAbajo))       
        {   //Oeste
            X=  (float)(X+ (Math.cos(Math.toRadians(180d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(3, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaAbajo) && Gdx.input.isKeyPressed(teclaIzquierda))   
        {   //SurOeste
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(135d))*velocidadMax)*velocidadMod);
            X=  (float)(X+ (Math.cos(Math.toRadians(135d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(6, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaAbajo) && Gdx.input.isKeyPressed(teclaDerecha))   
        {   //SurEste
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(45d))*velocidadMax)*velocidadMod);
            X=  (float)(X+ (Math.cos(Math.toRadians(45d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(8, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && Gdx.input.isKeyPressed(teclaIzquierda))   
        {   //NorOeste
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(225d))*velocidadMax)*velocidadMod);
            X=  (float)(X+ (Math.cos(Math.toRadians(225d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(0, false);
            pixie.setPosition(X, Y);
        }
        else if (Gdx.input.isKeyPressed(teclaArriba) && Gdx.input.isKeyPressed(teclaDerecha))   
        {   //NorEste
            Y=  (float)(Y+ -(Math.sin(Math.toRadians(315d))*velocidadMax)*velocidadMod);
            X=  (float)(X+ (Math.cos(Math.toRadians(315d))*velocidadMax)*velocidadMod);
            pixie.setAnimacion(2, false);
            pixie.setPosition(X, Y);
        }       
        if (!Gdx.input.isKeyPressed(teclaAbajo) && !Gdx.input.isKeyPressed(teclaArriba) && !Gdx.input.isKeyPressed(teclaDerecha) && !Gdx.input.isKeyPressed(teclaIzquierda) && !Gdx.input.isButtonPressed(Buttons.LEFT))
        {   //Animacion de Iddle (Sin pulsar ninguna tecla)
            pixie.setAnimacion(4, false);
            pixie.setPosition(X, Y);
        }
    }
    
    public void castear()
    {
        if (Gdx.input.isButtonPressed(Buttons.LEFT))
        {
            double alpha = Math.atan2(Gdx.input.getY() -(MiscData.WINDOW_Vertical_Resolution/2)+48/2, Gdx.input.getX() -(MiscData.WINDOW_Horizontal_Resolution/2)-48/2);     
            double angulo;
            angulo = Math.toDegrees(alpha+2*(Math.PI));
            angulo = angulo%360;
                    
            if (67.5d<=angulo && angulo<112.5d)     { pixie.setAnimacion(16, false); } //Abajo
            if (22.5d<=angulo && angulo<67.5d)      { pixie.setAnimacion(17, false); } //AbajoDcha
            if (112.5d<=angulo && angulo<157.5d)    { pixie.setAnimacion(15, false); } //AbajoIzda
            if (157.5d<=angulo && angulo<202.5d)    { pixie.setAnimacion(12, false); } //Izda
            if (22.5>angulo && angulo>=0)           { pixie.setAnimacion(14, false); } //Dcha
            if (337.5<=angulo && angulo<=360)       { pixie.setAnimacion(14, false); } //Dcha
            if (247.5<=angulo && angulo<292.5)      { pixie.setAnimacion(10, false); } //Arriba
            if (292.5<=angulo && angulo<337.5)      { pixie.setAnimacion(11, false); } //ArribaDcha
            if (202.5<=angulo && angulo<247.5)      { pixie.setAnimacion(9, false);  } //ArribaIzda   
        }
    }
    
    public void actualizar ()
    {
        castear ();
        if (!isCasteando) moverse();
    }
    
}
