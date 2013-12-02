package PlayerControl;
// @author Ivan Delgado Huertaimp
import Mobiles.Player;

public class PlayerControl
{
    protected EstadoPlayer estado;
    protected Player player;
    
    public interface EstadoPlayer 
    {
        public void procesarInput (PlayerControl playerControl);
        public void actualizar (PlayerControl playerControl, float delta);
    }
    
    public Player getPlayer()                       { return player; }
    public PlayerControl (Player player)            { this.player = player; estado = new Quieto(this); }
    public void procesarInput ()                    { estado.procesarInput(this); }
    public void actualizar (float delta)            { estado.actualizar(this, delta); }
        
    public static class Quieto extends AbstractEstado
    {   //QUIETO:
        public Quieto (PlayerControl playerControl) { inicializar(playerControl); }        
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(5, false, false, false); }
        
        @Override public void procesarInput(PlayerControl estado) 
        {   
            if (estado.player.castear)      { estado.estado = new Castear(estado); return; }
            if (estado.player.irEste)       { estado.estado = new Este(estado); return; }
            if (estado.player.irOeste)      { estado.estado = new Oeste(estado); return; }
            if (estado.player.irNorte)      { estado.estado = new Norte(estado); return; }
            if (estado.player.irSur)        { estado.estado = new Sur(estado); return; }
        }
        @Override public void actualizar (PlayerControl playerControl, float delta) { super.actualizar(playerControl, delta); }
    }
    
    public static class Castear extends AbstractEstado
    {   //CASTEAR:
        public Castear (PlayerControl playerControl) { inicializar(playerControl); }        
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(4, false, false, true); }
        
        @Override public void procesarInput(PlayerControl estado) 
        {   
            if (estado.player.getPixiePC().getAnimacionAcabada() == true)
            {
                if (estado.player.irEste)       { estado.estado = new Este(estado); return; }
                if (estado.player.irOeste)      { estado.estado = new Oeste(estado); return; }
                if (estado.player.irNorte)      { estado.estado = new Norte(estado); return; }
                if (estado.player.irSur)        { estado.estado = new Sur(estado); return; }
                if (!estado.player.irNorte && !estado.player.irSur && !estado.player.irEste && !estado.player.irOeste) 
                { estado.estado = new Quieto(estado); }
            }
        }
        @Override public void actualizar (PlayerControl playerControl, float delta) 
        { super.actualizar(playerControl, delta); if (playerControl.player.getPixiePC().getAnimacionAcabada() == true) procesarInput(playerControl); }
    }
    
    public static class Norte extends AbstractEstado
    {   //NORTE:
        public Norte (PlayerControl playerControl)    { inicializar(playerControl); }
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(2, false, false, false); }
        
        @Override public void procesarInput(PlayerControl estado)
        {   
            if (estado.player.castear)      { estado.estado = new Castear(estado); return; }
            if (estado.player.irSur)        { estado.estado = new Sur(estado); return; }
            else if (estado.player.irNorte)
            {
                if (estado.player.irEste)   { return; }
                if (estado.player.irOeste)  { return; }
            }
            else if (!estado.player.irNorte)
            {
                if (estado.player.irEste)   { estado.estado = new Este(estado); return; }
                if (estado.player.irOeste)  { estado.estado = new Oeste(estado); return; }
            }
            if (!estado.player.irNorte && !estado.player.irSur && !estado.player.irEste && !estado.player.irOeste) 
            { estado.estado = new Quieto(estado); }
        }
        @Override public void actualizar(PlayerControl estado, float delta) { super.actualizar(estado, delta); }
    }
    
    public static class Sur extends AbstractEstado
    {   //SUR:
        public Sur(PlayerControl playerControl)       { inicializar(playerControl); }
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(3, false, false, false); }
        
        @Override public void procesarInput(PlayerControl estado)
        {   
            if (estado.player.castear)    { estado.estado = new Castear(estado); return; }
            if (estado.player.irNorte)     { estado.estado = new Norte(estado); return; }
            else if (estado.player.irSur)
            {
                if (estado.player.irEste)  { return; }
                if (estado.player.irOeste) { return; }
            }
            else if (!estado.player.irSur)
            {
                if (estado.player.irEste)  { estado.estado = new Este(estado); return; }
                if (estado.player.irOeste) { estado.estado = new Oeste(estado); return; }
            }
            if (!estado.player.irNorte && !estado.player.irSur && !estado.player.irEste && !estado.player.irOeste) 
            { estado.estado = new Quieto(estado); }
        }
        @Override public void actualizar(PlayerControl estado, float delta) { super.actualizar(estado, delta); }
    }
    
    public static class Oeste extends AbstractEstado
    {   //OESTE:
        public Oeste (PlayerControl playerControl)    { inicializar(playerControl); }
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(0, false, false, false); }
        
        @Override public void procesarInput(PlayerControl estado) 
        {   
            if (estado.player.castear)    { estado.estado = new Castear(estado); return; }
            if (estado.player.irEste)      { estado.estado = new Este(estado); return; }
            else if (estado.player.irOeste)
            {
                if (estado.player.irNorte) { return; }
                if (estado.player.irSur)   { return; }
            }
            else if (!estado.player.irOeste)
            {
                if (estado.player.irNorte) { estado.estado = new Norte(estado); return; }
                if (estado.player.irSur)   { estado.estado = new Sur(estado); return; }
            }
            if (!estado.player.irNorte && !estado.player.irSur && !estado.player.irEste && !estado.player.irOeste) 
            { estado.estado = new Quieto(estado); }
        }
        @Override public void actualizar(PlayerControl estado, float delta) { super.actualizar(estado, delta); }
    }
    
    public static class Este extends AbstractEstado
    {   //SUR:
        public Este (PlayerControl playerControl)     { inicializar(playerControl); }
        @Override public final void inicializar (PlayerControl playerControl) 
        { playerControl.player.getPixiePC().setAnimacion(1, false, false, false); }
        
        @Override public void procesarInput(PlayerControl estado) 
        {   
            if (estado.player.castear)    { estado.estado = new Castear(estado); return; }
            if (estado.player.irOeste)     { estado.estado = new Oeste(estado); return; }
            else if (estado.player.irEste)
            {
                if (estado.player.irNorte) { return; }
                if (estado.player.irSur)   { return; }
            }
            else if (!estado.player.irEste)
            {
                if (estado.player.irNorte) { estado.estado = new Norte(estado); return; }
                if (estado.player.irSur)   { estado.estado = new Sur(estado); return; }
            }
            
            if (!estado.player.irNorte && !estado.player.irSur && !estado.player.irEste && !estado.player.irOeste) 
            { estado.estado = new Quieto(estado); }
        }
        @Override public void actualizar(PlayerControl estado, float delta) { super.actualizar(estado, delta); }
    }
}
