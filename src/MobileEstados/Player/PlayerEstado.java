package MobileEstados.Player;
// @author Ivan Delgado Huertaimp
import Graficos.Pixie;
import Recursos.Recursos;
import Mobiles.Mobs.Personajes.PCs.Player;
import com.badlogic.gdx.Gdx;

public class PlayerEstado
{
    protected Estado estado;                    //Estado actual, hace que mismos inputs produzcan diferentes resultados
    protected Player player;                    //Player al que hace referencia, para poder consultar sus datos facilmente
    
    //Clase ESTADO: define cada uno de los estados en los que puede estar un player:
    public interface Estado 
    {   public void procesarInput (PlayerEstado playerE);   //reacciona a los diferentes inputs segun el estado en el que estemos
        public void actualizar (PlayerEstado playerE);      //se ejecuta cada ciclo de update del player
    }
    //Constructor:
    public PlayerEstado (Player player)         { this.player = player; estado = new Quieto(this); }
    public void procesarInput ()                { estado.procesarInput(this); }
    public void actualizar ()                   { estado.actualizar(this); }
    
    //Cada estado contiene un constructor con las restricciones iniciales de movimiento y con la animacion que usa ese estado
    //Desde cada estado un mismo Input produce efectos diferentes y conduce a estados diferentes tambien
    //cada estado dispone tambien de un metodo de Update, por si hay que actualizar barras de carga, o power ups, etc...
    
    //Estado QUIETO:
    //Cualquier accion que ejecutemos que no sea la de castear implicara un cambio de estado. Castear no es un estado en si
    //puesto que no dura nada, es instanteo, no es un estado. Todas las demas acciones duran tanto tiempo como se mantengan
    //el Casteo en cambio no, es una accion instantanea, por eso se decide no hacer de ella un Estado. Para que la animacion
    //del casteo se ejecute hasta el final se flagea como ininterrumpible, y para que no loopee sin parar, se le pone como respaldo
    //la base del estado en el que esta:
    public static class Quieto extends AbstractPEstado
    {   public Quieto (PlayerEstado playerE) 
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(5, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   //Castear no es un estado, solapa el estado actual, por tanto solo lanza el casteo y la animacion
                castear(playerE); //cargamos una segunda animacion, para que cuando acabe el casteo se muestre
                if (playerE.player.isCasteando)
                {   playerE.player.getPixiePC().setAnimacion(4, false); 
                    playerE.player.getPixiePC().setAnimacion(5, false);
                }
            }   //cualquier accion hace que cambie el estado:
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
            if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
            if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
            if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); }
        }
        @Override public void actualizar (PlayerEstado playerE) { }
    }
    
    //Estado DISPARANDO:
    //Mantenemos el estado actual mientras se mantenga el boton de Disparar, pero antes de mostrar la animacion
    //de disparar, miramos si casteamos, por que la animacion de casteo tiene preferencia visualmente
    //Si, es así, cargamos primero la de casteo, y luego como respaldo la de disparo, de no castear solo se cargaria
    //la de disparo, que como no tiene fin, no necesita respaldo.
    //En caso de no disparar, hay que cambiar de estado:
    public static class Disparando extends AbstractPEstado
    {   public Disparando (PlayerEstado playerE)
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(getAngulo(playerE), false);
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.disparar)
            {   
                if (playerE.player.castear && !playerE.player.isCasteando)
                {   
                    castear(playerE);  
                    if (playerE.player.isCasteando) { playerE.player.getPixiePC().setAnimacion(4, false); }
                }   //para averiguar que animacion de disparo cargar, debemos calcular el angulo entre player y puntero
                playerE.player.getPixiePC().setAnimacion(getAngulo(playerE), false);
            }  
            else if (!playerE.player.disparar)
            {   //si no disparamos hay que cambiar de estado:
                if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
                if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); return; }
                if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
            }
        }   //como mientras disparamos vamos apuntando, hay que actualizar la animacion todo el rato:
        @Override public void actualizar(PlayerEstado playerE) 
        {   procesarInput(playerE); }
    }
    
    //Estado ARRIBA:
    //Los estados de movimiento estan separados por punto cardinal, para que ese punto cardinal tenga preferencia cuando
    //luego se altera el segundo eje, para emular la animacion Zelda. Es decir, las diagonal NE por ej, podria tomar la
    //animacion de la animacion Norte o la animacion ESTE, en nuestro caso, manda la primera direccion que pulsemos primero.
    //si pulsamos N y luego E, se vera la animacion N, en cambio si pulsamos E y luego N, se vera la animacion E.
    //esto lo conseguimos con los estados. Mientras domine el de una direccion, alteramos el segundo eje, y si cambia,
    //cambiamos de estado
    public static class Norte extends AbstractPEstado
    {   public Norte (PlayerEstado playerE)    
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = false;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(2, false);    
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   //Castear no es un estado, solapa el estado actual, por tanto solo lanza el casteo y la animacion
                castear(playerE); //cargamos una segunda animacion, para que cuando acabe el casteo se muestre
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false); 
                    playerE.player.getPixiePC().setAnimacion(2, false);
                } 
            }   //cualquier accion que no implique ir en la direccion del Estado hara cambiar de estado:
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irAbajo)         { playerE.estado = new Sur(playerE); return; }
            if (!playerE.player.irArriba)
            {   
                if (playerE.player.irDerecha)   { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda) { playerE.estado = new Oeste(playerE); return; }
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
            if (playerE.player.irArriba)
            {   //Si vamos hacia arriba, pero cambia el movimiento lateral, mantenemos el estado y cambiamos el rumbo
                playerE.player.rumboOeste = playerE.player.irIzquierda;
                playerE.player.rumboEste = playerE.player.irDerecha;
            }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado ABAJO:
    public static class Sur extends AbstractPEstado
    {   public Sur(PlayerEstado playerE)        
        {   playerE.player.rumboNorte = false; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(3, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE)
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   //Castear no es un estado, solapa el estado actual, por tanto solo lanza el casteo y la animacion
                castear(playerE); //cargamos una segunda animacion, para que cuando acabe el casteo se muestre
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false); 
                    playerE.player.getPixiePC().setAnimacion(3, false);
                } 
            }   //cualquier accion que no implique ir en la direccion del Estado hara cambiar de estado:
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irArriba)        { playerE.estado = new Norte(playerE); return; }
            else if (!playerE.player.irAbajo)
            {
                if (playerE.player.irDerecha)   { playerE.estado = new Este(playerE); return; }
                if (playerE.player.irIzquierda) { playerE.estado = new Oeste(playerE); return; }
            }   
            else if (playerE.player.irAbajo)
            {   //Si vamos hacia la direccion del Estado, pero cambia el movimiento lateral, solo cambiamos el rumbo
                playerE.player.rumboOeste = playerE.player.irIzquierda;
                playerE.player.rumboEste = playerE.player.irDerecha;
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado IZQUIERDA:
    public static class Oeste extends AbstractPEstado
    {   public Oeste (PlayerEstado playerE)     
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = false; 
            playerE.player.rumboOeste = playerE.player.irIzquierda;
            playerE.player.getPixiePC().setAnimacion(0, false);
        }
        @Override public void procesarInput(PlayerEstado playerE) 
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   //Castear no es un estado, solapa el estado actual, por tanto solo lanza el casteo y la animacion
                castear(playerE); //cargamos una segunda animacion, para que cuando acabe el casteo se muestre
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false); 
                    playerE.player.getPixiePC().setAnimacion(0, false);
                } 
            }   //cualquier accion que no implique ir en la direccion del Estado hara cambiar de estado:
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irDerecha)       { playerE.estado = new Este(playerE); return; }
            else if (!playerE.player.irIzquierda)
            {
                if (playerE.player.irArriba)    { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)     { playerE.estado = new Sur(playerE); return; }
            }
            else if (playerE.player.irIzquierda)
            {   //Si vamos hacia la direccion del Estado, pero cambia el movimiento lateral, solo cambiamos el rumbo
                playerE.player.rumboNorte = playerE.player.irArriba;
                playerE.player.rumboSur = playerE.player.irAbajo;
            }
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        @Override public void actualizar(PlayerEstado playerE)  { }
    }
    
    //Estado DERECHA:
    public static class Este extends AbstractPEstado
    {   public Este (PlayerEstado playerE)      
        {   playerE.player.rumboNorte = playerE.player.irArriba; 
            playerE.player.rumboSur = playerE.player.irAbajo;
            playerE.player.rumboEste = playerE.player.irDerecha; 
            playerE.player.rumboOeste = false;
            playerE.player.getPixiePC().setAnimacion(1, false); 
        }
        @Override public void procesarInput(PlayerEstado playerE) 
        {   if (playerE.player.castear && !playerE.player.isCasteando)
            {   //Castear no es un estado, solapa el estado actual, por tanto solo lanza el casteo y la animacion
                castear(playerE); //cargamos una segunda animacion, para que cuando acabe el casteo se muestre
                if (playerE.player.isCasteando) 
                {   playerE.player.getPixiePC().setAnimacion(4, false); 
                    playerE.player.getPixiePC().setAnimacion(1, false);
                } 
            }   //cualquier accion que no implique ir en la direccion del Estado hara cambiar de estado:
            if (playerE.player.disparar)        { playerE.estado = new Disparando(playerE); return; }
            if (playerE.player.irIzquierda)     { playerE.estado = new Oeste(playerE); return; }
            else if (!playerE.player.irDerecha)
            {
                if (playerE.player.irArriba)    { playerE.estado = new Norte(playerE); return; }
                if (playerE.player.irAbajo)     { playerE.estado = new Sur(playerE); return; }
            }
            else if (playerE.player.irDerecha)
            {   //Si vamos hacia la direccion del Estado, pero cambia el movimiento lateral, solo cambiamos el rumbo
                playerE.player.rumboNorte = playerE.player.irArriba;
                playerE.player.rumboSur = playerE.player.irAbajo;
            }    
            if (estaQuieto(playerE))            { playerE.estado = new Quieto(playerE); }
        }
        
        private float contadorPolvo = 0;
        
        @Override public void actualizar(PlayerEstado playerE)  
        {   //para que deje polvo despues de caminar un segundo en la misma direccion
            contadorPolvo+=Gdx.graphics.getDeltaTime();
            if (contadorPolvo >= 1)
            {   
                contadorPolvo = 0;
                Pixie polvo = new Pixie(Recursos.polvoPasos);
                polvo.setPosition(playerE.player.getX(), playerE.player.getY());
                polvo.setAnimacion(0, true);
                playerE.player.getActor().getStage().addActor(polvo);
            }
        }
    }
}
