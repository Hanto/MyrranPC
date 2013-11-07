/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Ivan Delgado Huerta
 */

//clase para el tratamiento grafico de textos en pantalla:
public class GroupText extends Group
{
    private Color colorNormal;
    private Color colorSombra;
    private LabelStyle estiloNormal;
    private LabelStyle estiloSombra;
    private Label textoNormal;
    private Label textoSombra;
    
    private int centradoHorizontal;
    private int centradoVertical;
    private int relieveSombra;
    
    private int X;
    private int Y;
    
        
    //CONSTRUCTOR Metodo para crear texto con un formato determinado y encapsularlo todo en un grupo:
    public GroupText (String texto, BitmapFont fuente, Color colorNormal, Color colorSombra, int posX, int posY, int centradoHorizontal, int centradoVertical, int relieve )
    {   
        this.colorNormal = colorNormal;
        this.colorSombra = colorSombra;
        this.X = posX;
        this.Y = posY;
        this.centradoHorizontal = centradoHorizontal;
        this.centradoVertical = centradoVertical;
        this.relieveSombra = relieve;
        
        //Creamos el estilo para los dos tipos de texto, el de la sombra y el del texto normal:
        estiloNormal = new LabelStyle (fuente, this.colorNormal);
        estiloSombra = new LabelStyle (fuente, this.colorSombra);
        //Creamos el texto segun segun los dos estilo (el normal y el sombra):
        textoNormal = new Label (texto, estiloNormal);
        textoSombra = new Label (texto, estiloSombra);
        //Segun el tipo de centradoHorizontal ajustamos el eje de coordenadas X:
        switch (centradoHorizontal)
        {
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { posX = posX; break; }
            default:            { posX = posX; break; }
        }
        //Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
        switch (centradoVertical)
        {
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { posY = posY; break; }
            default:            { posY = posY; break; }
        }
        //Situamos el texto normal y el texto sombra en las coordenadas generadas segun el tipo de centrado, y añadimos ambos textos al grupo grupoTexto:
        textoSombra.setPosition(posX+relieveSombra, posY-relieveSombra);
        this.addActor(textoSombra);
        textoNormal.setPosition(posX, posY);
        this.addActor(textoNormal);
    }
    
    //Metodos alternativos, asumiendo parametros por defecto:
    public GroupText ( String texto, BitmapFont fuente, Color colorNormal, Color colorSombra, int centradoHorizontal, int relieve )
    {   this ( texto, fuente, colorNormal, colorSombra, 0, 0, centradoHorizontal, Align.bottom, relieve ); }
    
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
    
    public void setTexto ( String texto )
    {
        textoNormal.setText(texto);
        //FALTA ESO!!!:
        //Al cambiar el texto, los metodos de calculo de tamaño devuelven el tamaño antiguo, no se por que.
        textoSombra.setText(texto);
        setCentrado ( centradoHorizontal, centradoVertical);
    }
    
    public void setCentrado ( int centradoHorizontal, int centradoVertical)
    {
        System.out.println(X);
        int posX = X;
        int posY = Y;
        int offsetSombra = 1;
        switch (centradoHorizontal)
        {
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { posX = posX; break; }
            default:            { posX = posX; break; }
        }
        //Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
        switch (centradoVertical)
        {
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { posY = posY; break; }
            default:            { posY = posY; break; }
        }
        System.out.println(posX);
        textoSombra.setPosition(posX+offsetSombra, posY-offsetSombra);
        textoNormal.setPosition(posX, posY);
    }
    
}
