package Actores.Mobs;
import Graficos.Pixie;
import Interfaces.Caster;
import Interfaces.Vulnerable;
import Interfaces.Debuffeable;
import Main.Mundo;
import Actores.Mob;
import Skill.Spell.Spell;
import Skill.Spell.TiposSpell.Bolt;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

//@author Ivan Delgado Huerta
public class Proyectil extends Mob
{
    protected Caster owner;                  //Quien castea el spell
    protected Spell spell;
   
    protected float duracionActual=0;           //Tiempo de vida que lleva el proyectil en el mundo
    protected float duracionMaxima;             //Tiempo maximo en segundos que permanece el proyectil
    protected PointLight luz;                   //Luz que genera el Bolt
    
    protected Group pixie = new Group();        //por ahora incluimos la animacion en un grupo para poder ejecutar acciones
    
    //SET:
    public void setDuracionMaxima (float DuracionMaxima)    { duracionMaxima = DuracionMaxima; }
    public void setOwner (Caster Owner)                     { owner = Owner; }
    public void setSpell (Spell spell)                      { this.spell = spell; }
    public void setPixie (Pixie pixie)                      { this.pixie.addActor(new Pixie(pixie)); }
    //GET:
    public Group getPixie()                                 { return pixie; }
    public Spell getSpell()                                 { return spell; }
    
    
    public Proyectil (Pixie pixie)
    {   //Creamos la animacion del proyectil y ajustamos su centro de gravedad, lo hacemos totalmente invisible 
        //y le ponemos una animacion de fade in para que aparezca suavemente
        this.pixie.addActor(new Pixie(pixie));
        this.pixie.setOrigin(pixie.getWidth()/2, pixie.getHeight()/2);
        this.pixie.setColor(0, 0, 0, 0);
        this.pixie.addAction(Actions.fadeIn(0.1f));
        luz = new PointLight(Mundo.rayHandler, 100, new Color(1,0.5f,0.5f,0.4f), 200, 0, 0);
    }
    
    public void expirar ()                                  { luz.remove(); Mundo.eliminarProyectil(this); }
    public void crear ()                                    { Mundo.añadirProyectil(this);  }
    public void consumirse(float delta)
    {
        duracionActual = duracionActual + delta;
        if (duracionActual > duracionMaxima) expirar();
    }
    
    public void setPosition(float origenX, float origenY)
    {   //tenemos que mover la entidad y su actor:
        x=origenX; y =origenY;
        pixie.setPosition(origenX, origenY);
    }
    
    @Override public void setDireccion (double d)
    {   //al cambiar la direccion del proyectil, tambien tenemos que alterar su rotacion
        super.setDireccion(d);
        this.pixie.rotate((float)Math.toDegrees(direccion));
    }
    
    public void procesarColision (Vulnerable target)
    {
        int daño = Math.round(spell.skillStats()[Bolt.STAT_Daño].valorBase);
        target.modificarHPs(daño, Color.RED);
        if (target instanceof Debuffeable)
        {
            spell.aplicarAuras(owner, (Debuffeable)target);
        }
    }
    
    public void moverse(float delta)
    {
        oldPosX = x;
        oldPosY = y;

        x=  (float)(x+ (Math.cos(direccion))*velocidad*velocidadMod*delta);
        y=  (float)(y+ (Math.sin(direccion))*velocidad*velocidadMod*delta);
        
        pixie.setPosition(x, y);
        luz.setPosition(x+pixie.getOriginX(), y+pixie.getOriginY());
    }
    
    public void actualizar (float delta)
    {
        consumirse(delta);
        moverse(delta);
    }
}
