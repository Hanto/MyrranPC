package PlayerControl;
// @author Ivan Delgado Huertaimp
import Mobiles.Player;

public class PlayerEstado
{
    protected Estado estado;                                //Estado actual
    protected Player player;                                //Player al que hace referencia
    //Clase ESTADO: define cada uno de los estados en los que puede estar un player:
    public interface Estado 
    {   public void procesarInput (PlayerEstado playerE);   //reacciona a los diferentes inputs segun el estado en el que estemos
        public void actualizar (PlayerEstado playerE);      //se ejecuta cada ciclo de update del player
    }
    //Constructor:
    public PlayerEstado (Player player)         { this.player = player; estado = new Quieto(this); }
    public void procesarInput ()                { estado.procesarInput(this); }
    public void actualizar ()                   { estado.actualizar(this); }
    
    //Estado QUIETO:
    public static class Quieto extends AbstractEstado
    {   public Quieto (PlayerEstado playerE) 
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(5, false, false, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   
                castear(playerE);
                playerE.player.getPixiePC().setAnimacion(4, false, true, true); 
                playerE.player.getPixiePC().setAnimacion(5, false, false, false);
            } 
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
            if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
            if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
            if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); }
        }
        @Override public void actualizar (PlayerEstado playerE) { }
    }
    
    //Estado DISPARANDO:
    public static class Disparando extends AbstractEstado
    {   public Disparando (PlayerEstado playerE)
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(getAngulo(playerE), false, false, false);
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.disparar)            
            {
                if (playerE.player.castear && !playerE.player.isCasteando)
                {   
                    castear(playerE);
                    if (playerE.player.isCasteando) { playerE.player.getPixiePC().setAnimacion(4, false, true, true); }
                }
                playerE.player.getPixiePC().setAnimacion(getAngulo(playerE), false, false, false);
            }
            else if (!playerE.player.disparar)
            {   
                if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
                if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); return; }
                if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
            }
        }
        @Override public void actualizar(PlayerEstado playerE) 
        {   procesarInput(playerE); }
    }
    
    //Estado ARRIBA:
    public static class Norte extends AbstractEstado
    {   public Norte (PlayerEstado playerE)    
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(2, false, false, false);    
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   
                castear(playerE); 
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false, true, true); 
                    playerE.player.getPixiePC().setAnimacion(2, false, false, false);
                } 
            }
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); return; }
            else if (!playerE.player.irArriba)
            {   
                if (playerE.player.irDerecha)   { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda) { playerE.estado = new Oeste(playerE); return; }
            }
            else if (playerE.player.irArriba)
            {
                playerE.player.rumboOeste = playerE.player.irIzquierda;
                playerE.player.rumboEste = playerE.player.irDerecha;
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado ABAJO:
    public static class Sur extends AbstractEstado
    {   public Sur(PlayerEstado playerE)        
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(3, false, false, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   
                castear(playerE); 
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false, true, true); 
                    playerE.player.getPixiePC().setAnimacion(3, false, false, false);
                } 
            }
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
            else if (!playerE.player.irAbajo)
            {
                if (playerE.player.irDerecha)   { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda) { playerE.estado = new Oeste(playerE); return; }
            }
            else if (playerE.player.irAbajo)
            {
                playerE.player.rumboOeste = playerE.player.irIzquierda;
                playerE.player.rumboEste = playerE.player.irDerecha;
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado IZQUIERDA:
    public static class Oeste extends AbstractEstado
    {   public Oeste (PlayerEstado playerE)     
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(0, false, false, false);
        }
        @Override public void procesarInput(PlayerEstado playerE) 
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   
                castear(playerE); 
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false, true, true); 
                    playerE.player.getPixiePC().setAnimacion(0, false, false, false);
                } 
            }
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
            else if (!playerE.player.irIzquierda)
            {
                if (playerE.player.irArriba)    { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)     { playerE.estado = new Sur(playerE); return; }
            }
            else if (playerE.player.irIzquierda)
            {
                playerE.player.rumboNorte = playerE.player.irArriba;
                playerE.player.rumboSur = playerE.player.irAbajo;
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado DERECHA:
    public static class Este extends AbstractEstado
    {   public Este (PlayerEstado playerE)      
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(1, false, false, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE) 
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   
                castear(playerE); 
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false, true, true); 
                    playerE.player.getPixiePC().setAnimacion(1, false, false, false);
                } 
            }
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
            else if (!playerE.player.irDerecha)
            {
                if (playerE.player.irArriba)    { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)     { playerE.estado = new Sur(playerE); return; }
            }
            else if (playerE.player.irDerecha)
            {
                playerE.player.rumboNorte = playerE.player.irArriba;
                playerE.player.rumboSur = playerE.player.irAbajo;
            }    
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
}
