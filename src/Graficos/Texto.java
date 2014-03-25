package Graficos;

import Pantallas.AbstractPantalla;
import Pantallas.PantallaJuego;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Ivan Delgado Huerta
 */

//clase para el tratamiento grafico de textos en pantalla:
public class Texto extends Group
{
    private LabelStyle estiloNormal;        //Fuente y Color del texto normal
    private LabelStyle estiloSombra;        //Fuente y Color del texto de la sombra
    
    private Label textoNormal;              //Resultado final del Texto final
    private Label textoSombra;              //Resultado final del Texto sombra
    
    private int centradoHorizontal;
    private int centradoVertical;
    private int relieveSombra;
        
    //CONSTRUCTOR Metodo para crear texto con un formato determinado y encapsularlo todo en un grupo:
    public Texto (String texto, BitmapFont fuente, Color colorNormal, Color colorSombra, float posX, float posY, int centradoHorizontal, int centradoVertical, int relieve )
    {   
        this.setX(posX);
        this.setY(posY);
        this.centradoHorizontal = centradoHorizontal;
        this.centradoVertical = centradoVertical;
        this.relieveSombra = relieve;
        
        //Creamos el estilo para los dos tipos de texto, el de la sombra y el del texto normal:
        estiloNormal = new LabelStyle (fuente, colorNormal);
        estiloSombra = new LabelStyle (fuente, colorSombra);
        //Creamos el texto segun segun los dos estilo (el normal y el sombra):
        textoNormal = new Label (texto, estiloNormal);
        textoSombra = new Label (texto, estiloSombra);
                
        switch (centradoHorizontal)
        {//Segun el tipo de centradoHorizontal ajustamos el eje de coordenadas X:
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { break; }
            default:            { break; }
        }
        
        switch (centradoVertical)
        {//Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { break; }
            default:            { break; }
        }
        //Situamos el texto normal y el texto sombra en las coordenadas generadas segun el tipo de centrado, y añadimos ambos textos al grupo grupoTexto:
        textoSombra.setPosition(posX+relieveSombra, posY-relieveSombra);
        textoNormal.setPosition(posX, posY);
        this.addActor(textoSombra);
        this.addActor(textoNormal);
    }
    
    public void setCentrado ( int centradoHorizontal, int centradoVertical)
    {
        float posX,posY;
        switch (centradoHorizontal)
        {//Segun el tipo de centradoHorizontal ajustamos el eje de coordenadas X:
            case Align.right:   { posX = this.getX() - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = this.getX() - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { posX = 0; break; }
            default:            { posX = 0; break; }
        }
        
        switch (centradoVertical)
        {//Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
            case Align.top:     { posY = this.getY() -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = this.getY() -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { posY = 0; break; }
            default:            { posY = 0; break; }
        }
        //Situamos el texto normal y el texto sombra en las coordenadas generadas segun el tipo de centrado, y añadimos ambos textos al grupo grupoTexto:
        textoSombra.setPosition(posX+relieveSombra, posY-relieveSombra);
        textoNormal.setPosition(posX, posY);
    }
    
    public void setTexto ( String texto )
    {   
        textoNormal.setText(texto);
        textoSombra.setText(texto);
        //por algun motivo si cambiamos el texto su tamaño no se actualiza, así que tenemos que generar un label nuevo con ese texto para calcular el tamaño
        Label ltexto = new Label (texto, estiloNormal);
        textoNormal.setWidth(ltexto.getWidth());
        textoSombra.setWidth(ltexto.getWidth());
        setCentrado (this.centradoHorizontal, this.centradoVertical);
    }
    
    public void setFuente ( BitmapFont fuente )
    {   //no se puede hacer un estiloNormal = new LabelStyle () con los nuevos parametros, hay que acceder a los campos y modificarlos. Si se hace un new, se crean nuevas referencias y los actores
        //no se actualizan.
        estiloNormal.font = fuente;
        estiloSombra.font = fuente;
        
        textoNormal.setStyle(estiloNormal);
        textoSombra.setStyle(estiloSombra);
    }
    
    public void setColorNormal ( Color color )
    {
        estiloNormal.fontColor = color;
        textoNormal.setStyle(estiloNormal);
    }
    
    public void setColorSombra ( Color color)
    {
        estiloSombra.fontColor = color;
        textoSombra.setStyle(estiloSombra);
    }
    
    public void scrollingCombatText (Stage stage, float duracion)
    {
        stage.addActor(this);
        this.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(duracion/4), Actions.delay(duracion/4*2), Actions.fadeOut(duracion/4)));
        this.addAction(Actions.sequence(Actions.moveBy(0f, 40f, duracion), Actions.removeActor()));
    }
    
    public void scrollingCombatText (Group group, float duracion)
    {
        this.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(duracion/4), Actions.delay(duracion/4*2), Actions.fadeOut(duracion/4)));
        this.addAction(Actions.sequence(Actions.moveBy(0f, 40f, duracion), Actions.removeActor()));
        group.addActor(this);
    }
    
    //Para cuando no queremos crear la entidad para su posterior modificabilidad, si no que queremos crearla rapido:
    public static void printTexto (String texto, BitmapFont fuente, Color colorNormal, Color colorSombra, float posX, float posY, int centradoHorizontal, int centradoVertical, int relieve, Group group)
    {   //Creamos el estilo para los dos tipos de texto, el de la sombra y el del texto normal:
        LabelStyle estiloNormal = new LabelStyle (fuente, colorNormal);
        LabelStyle estiloSombra = new LabelStyle (fuente, colorSombra);
        //Creamos el texto segun segun los dos estilo (el normal y el sombra):
        Label textoNormal = new Label (texto, estiloNormal);
        Label textoSombra = new Label (texto, estiloSombra);
                
        switch (centradoHorizontal)
        {//Segun el tipo de centradoHorizontal ajustamos el eje de coordenadas X:
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { break; }
            default:            { break; }
        }
        switch (centradoVertical)
        {//Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { break; }
            default:            { break; }
        }
        //Situamos el texto normal y el texto sombra en las coordenadas generadas segun el tipo de centrado, y añadimos ambos textos al grupo grupoTexto:
        textoSombra.setPosition(posX+relieve, posY-relieve);
        textoNormal.setPosition(posX, posY);
        group.addActor(textoSombra);
        group.addActor(textoNormal);
    } 
}
