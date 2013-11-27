package Graficos;
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
    
    //Distancia desde el centro de la pantalla hacia arriba y hacia abajo en el que se produce el efecto de perspectiva
    public static int distanciaPerspectiva = (MiscData.WINDOW_Vertical_Resolution-100)/2;
    public static int anguloMaximo = 24;    //desplazamiento Maximo de los tiles en la perspectiva hacia arriba
    public static int anguloCentro = 12;    //desplazamiento base de los tiles en la perspectiva central
    public static int anguloMinimo = 0;    //desplazamiento maximo de los tiles en la perspectiva hacia abajo
    
    public float perspectiva;               //Offset que hay que sumar al muroMedio y Techo para dar el efecto de perspectiva
    public boolean texturaFlipeada = false; //cuando la perspectiva es negativa hay que flipear la textura, pero solo 1 vez
    
    
    public Muro (TextureRegion base, TextureRegion medio, TextureRegion techo)
    {
        muroBase = new Sprite(base);
        muroMedio = new Sprite (medio);
        muroTecho = new Sprite (techo);
        muroTecho.setSize(24, 28);
    }
    public Muro (Muro muroOrigen)
    {
        muroBase = new Sprite(muroOrigen.muroBase);
        muroMedio = new Sprite(muroOrigen.muroMedio);
        muroTecho = new Sprite(muroOrigen.muroTecho);
        muroTecho.setSize(24, 28);
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
            
    public void ajustarPerspectiva()
    {   
        float distanciaMuro = this.getY() - Mundo.player.getY();
        
        if (Mundo.player.getY()<this.getY())
        {   //el jugador esta debajo del Muro
            if (Math.abs(distanciaMuro) > distanciaPerspectiva) distanciaMuro = distanciaPerspectiva;
            perspectiva = (anguloMaximo-anguloCentro)*distanciaMuro/distanciaPerspectiva+anguloCentro;
        }
        else
        {   //el jugador esta debajo del Muro   
            if (Math.abs(distanciaMuro) > distanciaPerspectiva) distanciaMuro = -distanciaPerspectiva;
            perspectiva = (anguloMinimo+anguloCentro)*distanciaMuro/distanciaPerspectiva+anguloCentro;
        }
        //Si hay un cambio de perspectiva y no falta flipearla, se flipea, para que la parte de abajo de la textura siempre se vea abajo
        if (perspectiva > 0 && texturaFlipeada) { muroBase.flip(false, true); muroMedio.flip(false, true); texturaFlipeada = false; }
        if (perspectiva <=0 && !texturaFlipeada) { muroBase.flip(false, true); muroMedio.flip(false, true); texturaFlipeada = true;}
        
        muroBase.setY(this.getY());
        muroMedio.setY(this.getY()+perspectiva);
        muroTecho.setY(this.getY()+perspectiva*2);
    }
}
