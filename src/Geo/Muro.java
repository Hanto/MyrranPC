package Geo;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import Main.Mundo;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Muro extends Actor
{
    public Sprite muroBase;
    public Sprite muroMedio;
    public Sprite muroTecho;
    
    public int alturaMinima = 10;
    public int recorridoParallax = (MiscData.WINDOW_Vertical_Resolution-100)/2;
    
    public Muro (TextureRegion base, TextureRegion medio, TextureRegion techo)
    {
        muroBase = new Sprite(base);
        muroMedio = new Sprite (medio);
        muroTecho = new Sprite (techo);
        //muroTecho.setSize(24, 24);
    }
    
    @Override public void draw (Batch batch, float alpha)
    {
        ajustarPerspectiva();
        
        muroBase.draw(batch, alpha);
        muroMedio.draw(batch, alpha);
        muroTecho.draw(batch, alpha);
    }
    
    @Override public void act(float delta)
    {
        super.act(delta);
        muroBase.setPosition(this.getX(), this.getY());
        muroMedio.setPosition(this.getX(), this.getY());
        muroTecho.setPosition(this.getX(), this.getY()-3);
    }
    
    public float getAjustePerspectiva ()
    {
        if (Mundo.player.getY()<this.getY())
        {
            float diferencia = this.getY() - Mundo.player.getY();
            if (Math.abs(diferencia) > recorridoParallax) diferencia = recorridoParallax;
            return diferencia/recorridoParallax*(24-alturaMinima);
        }
        else
        {
            float diferencia = this.getY() - Mundo.player.getY();
            if (Math.abs(diferencia) > recorridoParallax) diferencia = -recorridoParallax;
            return (diferencia/recorridoParallax*24+alturaMinima);
        }
    }
    
    public void ajustarPerspectiva()
    {
        if (Mundo.player.getY()<this.getY())
        {
            float diferencia = this.getY() - Mundo.player.getY();
            if (Math.abs(diferencia) > recorridoParallax) diferencia = recorridoParallax;
            
            float perspectiva = diferencia/recorridoParallax*(24-alturaMinima);
            
            muroBase.setY(this.getY());
            muroMedio.setY(this.getY()+alturaMinima+perspectiva);
            muroTecho.setY(this.getY()+alturaMinima*2+perspectiva*2);
        }
        else
        {            
            float diferencia = this.getY() - Mundo.player.getY();
            if (Math.abs(diferencia) > recorridoParallax) diferencia = -recorridoParallax;
            
            float perspectiva = (diferencia/recorridoParallax*24);
            
            muroBase.setY(this.getY());
            muroMedio.setY(this.getY()+alturaMinima+perspectiva);
            muroTecho.setY(this.getY()+alturaMinima*2+perspectiva*2);
        }
    }
}
