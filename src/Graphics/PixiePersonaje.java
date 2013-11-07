package Graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static Graphics.Recursos.listaDeRazas;
import static Screens.PantallaJuego.camara;

/**
 * @author Ivan Delgado Huerta
 */
public class PixiePersonaje extends Actor
{
    //DATOS Animacion:
    protected int numRaza;                          //Raza a la que pertecenen las animaciones del jugador (cuerpos y equipo)
    protected int numAnimacion;                     //Animacion que se esta ejecutando en este momento
    //PIXIES PARTES DEL CUERPO:
    protected Pixie cuerpo;                         //Pixie que contiene las animaciones del cuerpo;
    protected Pixie cabeza;                         //Pixie que contiene las animaciones de la cabeza;
    protected Pixie yelmo;                          //Pixie que contiene las animaciones de la armadura: Yelmo
    protected Pixie hombreras;                      //Pixie que contiene las animaciones de la armadura: Hombreras
    protected Pixie peto;                           //Pixie que contiene las animaciones de la armadura: Peto
    protected Pixie pantalones;                     //Pixie que contiene las animaciones de la armadura: Pantalones
    protected Pixie guantes;                        //Pixie que contiene las animaciones de la armadura: Guantes
    protected Pixie botas;                          //Pixie que contiene las animaciones de la armadura: Botas
    protected Pixie capaTrasera;                    //Pixie que contiene las animaciones de la armadura: Capa Trasera
    protected Pixie capaFrontal;                    //Pixie que contiene las animaciones de la armadura: Capa Frontal
    //SPRITES ESPECIALES:
    protected Image sombra;                         //Imagen que Contiene la sombra del personaje
    //CENTRADO ESPECIAL DE Camara:
    protected boolean usarCoordenadasCamara = false;//Hay veces que la camara sigue al Pixie, y como desde que la camara pilla las coordenadas del pixie hasta que se dibuja el pixie pasa un rato, suele suceder que la posicion de este ultimo se
                                                    //actualiza, desincronizandose y produciendose el efecto parpadeo, para evitar esto, mientras se dibuja el frame actual y solo para el Pixie que esta siendo seguido por la camara hay que usar
                                                    //las coordenadas tomadas en el momento de empezar a dibujar el frame (en este caso las coordenadas X,Y de la camara). Para cumplir con este cometido usamos este booleano que se encargara de 
                                                    //coger las coordenadas de la camara en lugar de las del pixie en caso de ser verdadero
    
    //Actualiza todos los Pixies para usar las coordenadas de la camara en lugar de las suyas propias, asi no se produce desincronizacion mientras se dibuja el frame:
    public void setUsarCoordenadasCamara (Boolean b)    { usarCoordenadasCamara = b;}
    
    //CONSTRUCTOR: inicializa y crea la animacion base, con el cuerpo de humano y el set desnudo
    public PixiePersonaje (int numRaza)
    {
        this.numRaza = numRaza;
        cuerpo = new Pixie (listaDeRazas.get(numRaza).listaDeCuerpos.get(0)); cuerpo.setUsarCoordenadasCamara(usarCoordenadasCamara);
        cabeza = new Pixie (listaDeRazas.get(numRaza).listaDeCabezas.get(0)); cabeza.setUsarCoordenadasCamara(usarCoordenadasCamara);
        yelmo = new Pixie (listaDeRazas.get(numRaza).listaDeYelmos.get(0)); yelmo.setUsarCoordenadasCamara(usarCoordenadasCamara); yelmo.setOffset(-24, -24);
        peto = new Pixie (listaDeRazas.get(numRaza).listaDePetos.get(0)); peto.setUsarCoordenadasCamara(usarCoordenadasCamara);
        pantalones = new Pixie (listaDeRazas.get(numRaza).listaDePantalones.get(0)); pantalones.setUsarCoordenadasCamara(usarCoordenadasCamara);
        guantes = new Pixie (listaDeRazas.get(numRaza).listaDeGuantes.get(0)); guantes.setUsarCoordenadasCamara(usarCoordenadasCamara);
        botas = new Pixie (listaDeRazas.get(numRaza).listaDeBotas.get(0)); botas.setUsarCoordenadasCamara(usarCoordenadasCamara);
        hombreras = new Pixie (listaDeRazas.get(numRaza).listaDeHombreras.get(0)); hombreras.setUsarCoordenadasCamara(usarCoordenadasCamara);
        capaTrasera = new Pixie (listaDeRazas.get(numRaza).listaDeCapasTraseras.get(0)); capaTrasera.setUsarCoordenadasCamara(usarCoordenadasCamara); capaTrasera.setOffset(-24, -24);
        capaFrontal = new Pixie (listaDeRazas.get(numRaza).listaDeCapasFrontales.get(0)); capaFrontal.setUsarCoordenadasCamara(usarCoordenadasCamara); capaFrontal.setOffset(-24, -24); 
        sombra = Recursos.sombraPlayer;
    }
    
    public void setAnimacion (int numAnimacion, boolean forzarSincronizacion)
    {   //SELECCIONAR ANIMACION: Metodo para sincronizar todas las animaciones de todos los slots de armadura a la animacion que toque ejecutar segun el movimiento principal que ejecute el personaje
        if (this.numAnimacion == numAnimacion && !forzarSincronizacion) { return; }     //Si la animacion no cambia, no hay que hacer nada, que todo se siga animando como siempre, asi se evita malgastar potencia de calculo
        this.numAnimacion = numAnimacion;                                               //En caso negativo, salvamos el valor de la nueva animacion para poderlo comprobar en un futuro, y aplicamos la nueva animacion a todas las partes
        
        cuerpo.setAnimacion(numAnimacion);
        cabeza.setAnimacion(numAnimacion);
        yelmo.setAnimacion(numAnimacion);
        peto.setAnimacion(numAnimacion);
        pantalones.setAnimacion(numAnimacion);
        guantes.setAnimacion(numAnimacion);
        botas.setAnimacion(numAnimacion);
        hombreras.setAnimacion(numAnimacion);
        capaTrasera.setAnimacion(numAnimacion);
        capaFrontal.setAnimacion(numAnimacion);
    }
    
    //VESTIR SLOT ARMADURA: Metodos para vestirse un slot de armadura, carga esa armadura de la lista de armadura de Mundo, creando una copia de la pieza que deseamos
    //Luego la movemos a la posicion donde esta el personaje, le damos los datos del offset y se tercia, y si debe usar las coordenadas de la camara para el caso que sea un Pixie seguido por esta
    //para finalmente activar la animacion que esta ejecutando todo el cuerpo, con booleano de forzarSincronizacion, para que reinicien todas las animaciones, y asi todas empiecen por el mismo frame
    public void setCuerpo (int numCuerpo)           //CUERPO:      
    {   cuerpo = new Pixie (listaDeRazas.get(numRaza).listaDeCuerpos.get(numCuerpo)); 
        cuerpo.setPosition(getX(), getY()); 
        cuerpo.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true);
    }
    public void setCabeza (int numCabeza)           //CABEZA:
    {   cabeza = new Pixie (listaDeRazas.get(numRaza).listaDeCabezas.get(numCabeza)); 
        cabeza.setPosition(getX(), getY());
        cabeza.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true);
    }
    public void setYelmo (int numArmadura)          //YELMO:
    {   yelmo = new Pixie (listaDeRazas.get(numRaza).listaDeYelmos.get(numArmadura)); 
        yelmo.setPosition(getX(), getY());
        yelmo.setUsarCoordenadasCamara(usarCoordenadasCamara); 
        yelmo.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
    public void setPeto (int numArmadura)           //PETO:
    {   peto = new Pixie (listaDeRazas.get(numRaza).listaDePetos.get(numArmadura)); 
        peto.setPosition(getX(), getY()); 
        peto.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true); 
    }
    public void setPantalones (int numArmadura)     //PANTALONES:
    {   pantalones = new Pixie (listaDeRazas.get(numRaza).listaDePantalones.get(numArmadura)); 
        pantalones.setPosition(getX(), getY()); 
        pantalones.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true); 
    }
    public void setGuantes (int numArmadura)        //GUANTES:
    {   guantes = new Pixie (listaDeRazas.get(numRaza).listaDeGuantes.get(numArmadura)); 
        guantes.setPosition(getX(), getY()); 
        guantes.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true); 
    }
    public void setBotas (int numArmadura)          //BOTAS:   
    {   botas = new Pixie (listaDeRazas.get(numRaza).listaDeBotas.get(numArmadura)); 
        botas.setPosition(getX(), getY()); 
        botas.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true); 
    }
    public void setHombreras (int numArmadura)      //HOMBRERAS:
    {   hombreras = new Pixie (listaDeRazas.get(numRaza).listaDeHombreras.get(numArmadura));
        hombreras.setPosition(getX(), getY());
        hombreras.setUsarCoordenadasCamara(usarCoordenadasCamara);
        setAnimacion(numAnimacion, true); 
    }
    public void setCapaTrasera (int numArmadura)    //CAPA TRASERA:
    {   capaTrasera = new Pixie (listaDeRazas.get(numRaza).listaDeCapasTraseras.get(numArmadura));
        capaTrasera.setPosition(getX(), getY());
        capaTrasera.setUsarCoordenadasCamara(usarCoordenadasCamara);
        capaTrasera.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
    public void setCapaFrontal (int numArmadura)    //CAPA FRONTAL:
    {   capaFrontal = new Pixie (listaDeRazas.get(numRaza).listaDeCapasFrontales.get(numArmadura));
        capaFrontal.setPosition(getX(), getY());
        capaFrontal.setUsarCoordenadasCamara(usarCoordenadasCamara);
        capaFrontal.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
    
    @Override
    public void setPosition (float X, float Y)
    {   //Sobreescribimos el metodo para actualizar la posicion del actor para que de paso actualice la posicion de todos los Pixies hijos
        X = (int)X;
        Y = (int)Y;
        super.setPosition( X, Y);
        //Si no lo pasamos a INTEGER, aparecen artefactos graficos:
        cuerpo.setPosition( X, Y);
        cabeza.setPosition( X, Y);
        yelmo.setPosition( X, Y);
        peto.setPosition( X, Y);
        pantalones.setPosition( X, Y);
        guantes.setPosition( X, Y);
        botas.setPosition( X, Y);
        hombreras.setPosition( X, Y);
        capaTrasera.setPosition( X, Y);
        capaFrontal.setPosition( X, Y);
        sombra.setPosition( X +7, Y -4);
    }
    
    @Override
    public void draw (SpriteBatch batch, float alpha)
    {   //El orden de dibujado cambia segun la direccion en la que te muevas, ya que las hombreras por ejemplo deben ser tapadas por la capa al moverse en las diagonales superiores:
        if (numAnimacion == 0 || numAnimacion == 1 || numAnimacion == 2 || numAnimacion == 10 )
        {   //Como la sombra es una textura simple hay que hacer la sincronizacion de la camara de forma manual aqui mismo:
            if (usarCoordenadasCamara) sombra.getDrawable().draw(batch, camara.position.x +7, camara.position.y -4, sombra.getWidth(), sombra.getHeight());
            else sombra.draw(batch, alpha);
            capaFrontal.draw(batch, alpha);
            cuerpo.draw(batch, alpha);
            cabeza.draw(batch, alpha);
            yelmo.draw(batch, alpha);
            peto.draw(batch, alpha);
            pantalones.draw(batch, alpha);
            hombreras.draw(batch, alpha);
            guantes.draw(batch, alpha);
            botas.draw(batch, alpha);
            capaTrasera.draw(batch, alpha);
        }
        else
        {   //Como la sombra es una textura simple hay que hacer la sincronizacion de la camara de forma manual aqui mismo:
            if (usarCoordenadasCamara) sombra.getDrawable().draw(batch, camara.position.x +7, camara.position.y -4, sombra.getWidth(), sombra.getHeight());
            else sombra.draw(batch, alpha);
            capaFrontal.draw(batch, alpha);
            capaTrasera.draw(batch, alpha);
            cuerpo.draw(batch, alpha);
            cabeza.draw(batch, alpha);
            yelmo.draw(batch, alpha);
            peto.draw(batch, alpha);
            hombreras.draw(batch, alpha);
            pantalones.draw(batch, alpha);
            guantes.draw(batch, alpha);
            botas.draw(batch, alpha);
        }
    }
}
