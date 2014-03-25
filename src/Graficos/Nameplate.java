package Graficos;
import Constantes.MiscData;
import Main.Mundo;
import Mobiles.Mobs.Personaje;
import Mobiles.Mobs.Personajes.PC;
import Recursos.Recursos;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
// @author Ivan Delgado Huerta
public class Nameplate extends Group
{
    protected Personaje personaje;
    //NAMEPLATES
    protected Sprite barraVidaTotal;                //Imagen que contiene el nameplateTotal de la vida del Player
    protected Sprite barraVidaActual;               //Imagen que contiene el fondo de la vida del nameplateTotal del Player
    protected float ultimosHPsRenderizados;
    protected Sprite barraCasteoTotal;
    protected Sprite barraCasteoActual;
    protected float ultimoCasteoRenderizado;
        
    public Nameplate (Personaje personaje)
    {
        this.personaje = personaje;
        
        barraVidaTotal = new Sprite (Recursos.nameplateTotal);
        barraVidaActual = new Sprite (Recursos.nameplateActual);
        barraCasteoTotal = new Sprite (Recursos.nameplateTotal);
        barraCasteoActual = new Sprite (Recursos.nameplateActual);
        barraVidaTotal.setColor(MiscData.NAMEPLATE_Player_Vida);
        barraCasteoTotal.setColor(MiscData.NAMEPLATE_Player_Casteo);

        int alto = (int)barraVidaTotal.getHeight();
        int ancho = (int)barraVidaTotal.getWidth();
        //colocamos la barra de vida encima de la de casteo, -1, para que los rebordes se fusionen y no quede doble reborde
        barraVidaTotal.setPosition(0, alto-1);
        barraVidaActual.setPosition(ancho-1, alto-1);
        barraCasteoActual.setPosition(ancho-1, 0);
        
        this.setWidth(ancho);
        this.setHeight(alto);
        this.setBounds(0, 0, ancho, alto);
    }
   
    @Override
    public void act(float delta)
    {
        float alto = this.getHeight();
        float ancho = this.getWidth();
        
        super.act(delta);
        barraVidaTotal.setPosition(this.getX(), this.getY()+alto-1);
        barraVidaActual.setPosition(this.getX()+ancho-1, this.getY()+alto-1);
        barraCasteoTotal.setPosition(this.getX(), this.getY());
        barraCasteoActual.setPosition(this.getX()+ancho-1, this.getY());        
    }
    
    @Override
    public void draw (Batch batch, float alpha)
    {
        if (personaje instanceof PC && Mundo.dibujarNameplatesPlayer) dibujarNameplate (batch, alpha);
    }
    
    public void dibujarNameplate (Batch batch, float alpha)
    {
        float ancho = this.getWidth();
        float alto = this.getHeight();
        
        int tamañoBarraVida = (int)((1-personaje.getHPsPercent())*ancho);
        int tamañoBarraCasteo = (int)((1-personaje.getCastingTimePercent())*ancho);
        
        barraVidaTotal.draw(batch, alpha);
        if (tamañoBarraVida != ultimosHPsRenderizados)
        {
            barraVidaActual.setSize(-tamañoBarraVida, alto);
            ultimosHPsRenderizados = tamañoBarraVida;
        }
        barraVidaActual.draw(batch, alpha);
        
        barraCasteoTotal.draw(batch, alpha);
        if (tamañoBarraCasteo != ultimoCasteoRenderizado)
        {
            barraCasteoActual.setSize(-tamañoBarraCasteo, alto);
            ultimoCasteoRenderizado = tamañoBarraCasteo;
        }
        barraCasteoActual.draw(batch, alpha);
    }
}
