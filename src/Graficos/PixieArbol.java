package Graficos;

import Graficos.Recursos.TroncoTemplate;
import Main.Mundo;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Ivan Delgado Huerta
 */
public class PixieArbol extends Actor
{ 
    private Pixie copa1;                                                   //Pixie que contiene la animacion de las hojas de la copa1
    private Pixie copa2;                                                   //Pixie que contiene la animacion de las hojas de la copa2
    private Pixie copa3;                                                   //Pixie que contiene la animacion de las hojas de la copa3
    private Image sombra = new Image (Recursos.sombraArbol1.getDrawable()); //Textura que contiene la sombra del arbol
    private Tronco tronco;                                                  //Clase que contiene los datos del tronco del arbol:
    
    public static class Tronco
    {   //CLASE TRONCO: clase a medida para almacenar los datos del tronco, esto incluye la textura del tronco, y los puntos de anclaje a los que enganchar las texturas de las copas
        private Image textura;                                              //Textura que contiene el tronco del arbol
        private Vector2 enganche1 = new Vector2();                          //1º punto de enganche para el pixie de la copa1
        private Vector2 enganche2 = new Vector2();                          //2º punto de enganche para el pixie de la copa2
        private Vector2 enganche3 = new Vector2();                          //3º punto de engnache para el pixie de la copa3
        
        //CONSTRUCTOR sin argumentos para poder crear la clase sin especificar argumentos y poder pasarselos luego
        public Tronco () {}
        //CONSTRUCTOR que copia los datos de un tronco Template, que es una estructura tronco pero que en lugar de almacenar Images, almacena sus texturas
        public Tronco (TroncoTemplate tronco)
        {   textura = new Image (tronco.textura);
            enganche1.set(tronco.enganche1.x, tronco.enganche1.y);
            enganche2.set(tronco.enganche2.x, tronco.enganche2.y);
            enganche3.set(tronco.enganche3.x, tronco.enganche3.y);
        }
        //CONSTRUCTOR que copia los datos de un tronco:
        public Tronco (Tronco tronco)                                       
        {   textura = new Image (tronco.textura.getDrawable());
            enganche1.set(tronco.enganche1.x, tronco.enganche1.y);
            enganche2.set(tronco.enganche2.x, tronco.enganche2.y);
            enganche3.set(tronco.enganche3.x, tronco.enganche3.y);
        }
    }
    
    
    //CONSTRUCTOR COPIA: construye un arbol sin Copa, a partir de un Tronco Template, Tronco, o un entero que haga referencia a la lista de troncos de los Recursos
    public PixieArbol (TroncoTemplate troncoTemplate)       { tronco = new Tronco( troncoTemplate ); }
    public PixieArbol (Tronco troncoTemplate)               { tronco = new Tronco( troncoTemplate ); }
    public PixieArbol (int numTronco)                       { tronco = new Tronco( Recursos.listaDeTroncos.get(numTronco)); }
    
    //CONSTRUCTOR COPIA: construye un arbol copiandolo a partir del que se le pasa como parametro
    public PixieArbol (PixieArbol arbol)
    {
        tronco = new Tronco(arbol.tronco);
        copa1 = new Pixie(arbol.copa1);
        copa2 = new Pixie(arbol.copa2);
        copa3 = new Pixie(arbol.copa3);
    }
    
    //COPAS del arbol:
    public void setCopas (int copa1, int copa2, int copa3)  { setCopa1(copa1); setCopa2(copa2); setCopa3(copa3); }
    public void setCopa1 (int numCopa)                      { copa1 = new Pixie(Recursos.listaDeCopas.get(numCopa)); copa1.setPosition(getX() + tronco.enganche1.x, getY() + tronco.enganche1.y); }
    public void setCopa2 (int numCopa)                      { copa2 = new Pixie(Recursos.listaDeCopas.get(numCopa)); copa2.setPosition(getX() + tronco.enganche2.x, getY() + tronco.enganche2.y); }
    public void setCopa3 (int numCopa)                      { copa3 = new Pixie(Recursos.listaDeCopas.get(numCopa)); copa3.setPosition(getX() + tronco.enganche3.x, getY() + tronco.enganche3.y); }
    
    @Override
    public void setPosition (float X, float Y)
    {
        super.setPosition(X, Y);
        
        tronco.textura.setPosition(X, Y);
        copa1.setPosition(X + tronco.enganche1.x, Y + tronco.enganche1.y);
        copa2.setPosition(X + tronco.enganche2.x, Y + tronco.enganche2.y);
        copa3.setPosition(X + tronco.enganche3.x, Y + tronco.enganche3.y);
        sombra.setPosition(X -20, Y -11);
    }
    
    @Override
    public void draw (Batch batch, float alpha)
    {
        //Calculamos la distancia del arbol al player, si esta cerca haremos las hojas transparentes:
        double distancia = Math.abs(Math.sqrt(Math.pow(Mundo.player.getX()-getX(),2)+Math.pow(Mundo.player.getY()-getY()-80, 2)));
        double distanciaVision = 150;
        
        if (distancia < distanciaVision)
        {
            sombra.draw(batch, alpha);
            tronco.textura.draw(batch, alpha);
            copa1.draw(batch, (float)(distancia/distanciaVision));
            copa2.draw(batch, (float)(distancia/distanciaVision));
            copa3.draw(batch, (float)(distancia/distanciaVision));
            
        } else
        {
            sombra.draw(batch, alpha);
            tronco.textura.draw(batch, alpha);
            copa1.draw(batch, alpha);
            copa2.draw(batch, alpha);
            copa3.draw(batch, alpha);
        }   
    }
}
