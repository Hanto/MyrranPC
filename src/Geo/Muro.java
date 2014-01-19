package Geo;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import Main.Mundo;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Muro extends Actor
{
    public Sprite muroBase;
    public Sprite muroMedio;
    public Sprite muroTecho;
    public Body muroBody;
    
    //Distancia desde el centro de la pantalla hacia arriba y hacia abajo en el que se produce el efecto de perspectiva
    public static int distanciaPerspectiva = (MiscData.WINDOW_Vertical_Resolution-100)/2;
    public static int anguloMaximo = MiscData.TILESIZE;     //desplazamiento Maximo de los tiles en la perspectiva hacia arriba
    public static int anguloCentro = 12;                    //desplazamiento base de los tiles en la perspectiva central
    public static int anguloMinimo = 0;                     //desplazamiento maximo de los tiles en la perspectiva hacia abajo
    
    public float perspectiva;               //Offset que hay que sumar al muroMedio y Techo para dar el efecto de perspectiva
    public boolean texturaFlipeada = false; //cuando la perspectiva es negativa hay que flipear la textura, pero solo 1 vez
            
    public Muro (TextureRegion base, TextureRegion medio, TextureRegion techo)
    {
        int anguloSubida = anguloMaximo-anguloCentro;
        int anguloBajada = anguloMinimo+anguloCentro;
        int correccionTecho = Math.max(anguloSubida, anguloBajada)/2;
        
        muroBase = new Sprite(base);
        muroMedio = new Sprite (medio);
        muroTecho = new Sprite (techo);
        muroTecho.setSize(MiscData.TILESIZE, MiscData.TILESIZE+correccionTecho);
        crearBody();
    }
    public Muro (Muro muroOrigen)
    {
        int anguloSubida = anguloMaximo-anguloCentro;
        int anguloBajada = anguloMinimo+anguloCentro;
        int correccionTecho = Math.max(anguloSubida, anguloBajada)/2;
        
        muroBase = new Sprite(muroOrigen.muroBase);
        muroMedio = new Sprite(muroOrigen.muroMedio);
        muroTecho = new Sprite(muroOrigen.muroTecho);
        muroTecho.setSize(MiscData.TILESIZE, MiscData.TILESIZE+correccionTecho);
        crearBody();
    }
    
    @Override public void draw (Batch batch, float alpha)
    {
        ajustarPerspectiva();    
        muroBase.draw(batch, alpha);
        muroMedio.draw(batch, alpha);
        muroTecho.draw(batch, alpha);
    }
            
    @Override public void setPosition (float x, float y)
    {
        super.setPosition(x, y);
        
        muroBase.setPosition(x, y);
        muroMedio.setPosition(x, y);
        muroTecho.setPosition(x, y);
        muroBody.setTransform(x+MiscData.TILESIZE/2, y+MiscData.TILESIZE/2, muroBody.getAngle());
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
        {   //el jugador esta encima del Muro   
            if (Math.abs(distanciaMuro) > distanciaPerspectiva) distanciaMuro = -distanciaPerspectiva;
            perspectiva = (anguloMinimo+anguloCentro)*distanciaMuro/distanciaPerspectiva+anguloCentro;            
        }
        //Si hay un cambio de perspectiva y no falta flipearla, se flipea, para que la parte de abajo de la textura siempre se vea abajo
        if (perspectiva > 0 && texturaFlipeada) { muroBase.flip(false, true); muroMedio.flip(false, true); texturaFlipeada = false; }
        if (perspectiva <=0 && !texturaFlipeada) { muroBase.flip(false, true); muroMedio.flip(false, true); texturaFlipeada = true;}
        
        muroMedio.setY(this.getY()+perspectiva);
        muroTecho.setY(this.getY()+perspectiva*2);
    }
    
    public void eliminarMuro()                      
    { 
        Mundo.stageMundo.getRoot().removeActor(this);
        Mundo.world.destroyBody(muroBody); 
    }
    
    private void crearBody ()
    {
        PolygonShape tileShape = new PolygonShape();
        tileShape.setAsBox(MiscData.TILESIZE/2, MiscData.TILESIZE/2);

        BodyDef tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = tileShape;
        fixtureDef.filter.groupIndex = 0;
        
        //tileBodyDef.position.set(getX(), getY());
        muroBody = Mundo.world.createBody(tileBodyDef);
        muroBody.createFixture(fixtureDef);
    
        tileShape.dispose();
    }
}
