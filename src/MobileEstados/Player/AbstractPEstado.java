package MobileEstados.Player;
// @author Ivan Delgado Huerta

import Main.Mundo;
import Actores.Mobs.Personajes.PCs.Player;
import Pantallas.PantallaJuego;
import MobileEstados.Player.PlayerEstado.Estado;
import Skill.SkillBook;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public abstract class AbstractPEstado implements Estado
{
    public void castear (PlayerEstado playerE)
    {
        Player player = playerE.player;
        if (player.castear && !player.isCasteando && player.getSpellSeleccionado().length() > 0)
        {   
            SkillBook.listaDeSpells.get(player.getSpellSeleccionado()).castear(player, Gdx.input.getX(), Gdx.input.getY());
        }
    }
    
    public void aplicarMovimiento (PlayerEstado playerE)
    {
        Player player = playerE.player; 
        player.rumboNorte = player.irArriba;
        player.rumboSur = player.irAbajo;
        player.rumboEste = player.irDerecha;
        player.rumboOeste = player.irIzquierda;
    }
    
    public boolean estaQuieto (PlayerEstado playerE)
    {
        Player player = playerE.player;
        return (!player.irArriba && !player.irAbajo && !player.irDerecha && !player.irIzquierda);
    }
    
    public int getAngulo(PlayerEstado playerE)
    {
        Vector3 destino = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        PantallaJuego.camara.unproject(destino);
        
        float origenX = playerE.player.getX()+playerE.player.getPixiePC().getWidth()/2;
        float origenY = playerE.player.getY()+playerE.player.getPixiePC().getHeight()/2;
     
        double alpha = Math.atan2(destino.y -origenY, destino.x -origenX);     
        double angulo = Math.toDegrees(alpha+2*(Math.PI))%360;
        
        if (67.5d<=angulo && angulo<112.5d)     { return 11; }  //Arriba
        if (22.5d<=angulo && angulo<67.5d)      { return 13; }  //ArribaDcha
        if (112.5d<=angulo && angulo<157.5d)    { return 12; }  //ArribaIzda
        if (157.5d<=angulo && angulo<202.5d)    { return 6; }   //Izda
        if (22.5>angulo && angulo>=0)           { return 7; }   //Dcha
        if (337.5<=angulo && angulo<=360)       { return 7; }   //Dcha
        if (247.5<=angulo && angulo<292.5)      { return 10; }  //Abajo
        if (292.5<=angulo && angulo<337.5)      { return 9; }   //AbajoDcha
        if (202.5<=angulo && angulo<247.5)      { return 8; }   //AbajoIzda 
        return 6;
    }
}
