package Graficos;
import Resources.Recursos;
import static Resources.Recursos.listaDeRazas;
import Actores.Mobs.Personajes.PC;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
//* @author Ivan Delgado Huerta
public class PixiePC extends Actor
{
    protected PC pc;                                //Personaje que se esta dibujando, se necesita para acceder a los HPs, por ej
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
    protected Sprite sombra;                        //Imagen que Contiene la sombra del personaje            
    
    public int getNumAnimacion()                    { return numAnimacion; };
    
    //CONSTRUCTOR: inicializa y crea la animacion base, con el cuerpo de humano y el set desnudo
    public PixiePC (int numRaza, PC pc)
    {
        this.numRaza = numRaza;
        
        cuerpo = new Pixie (listaDeRazas.get(numRaza).listaDeCuerpos.get(0));
        cabeza = new Pixie (listaDeRazas.get(numRaza).listaDeCabezas.get(0));
        yelmo = new Pixie (listaDeRazas.get(numRaza).listaDeYelmos.get(0)); yelmo.setOffset(-24, -24);
        peto = new Pixie (listaDeRazas.get(numRaza).listaDePetos.get(0));
        pantalones = new Pixie (listaDeRazas.get(numRaza).listaDePantalones.get(0));
        guantes = new Pixie (listaDeRazas.get(numRaza).listaDeGuantes.get(0));
        botas = new Pixie (listaDeRazas.get(numRaza).listaDeBotas.get(0));
        hombreras = new Pixie (listaDeRazas.get(numRaza).listaDeHombreras.get(0));
        capaTrasera = new Pixie (listaDeRazas.get(numRaza).listaDeCapasTraseras.get(0)); capaTrasera.setOffset(-24, -24);
        capaFrontal = new Pixie (listaDeRazas.get(numRaza).listaDeCapasFrontales.get(0)); capaFrontal.setOffset(-24, -24);
        
        sombra = new Sprite (Recursos.sombraPlayer);
                
        this.setHeight(cuerpo.getHeight());
        this.setWidth(cuerpo.getWidth());
    }
    
    public void setAnimacion (int numAnimacion, boolean forzarAnimacion)
    {   //SELECCIONAR ANIMACION: Metodo para sincronizar todas las animaciones de todos los slots de armadura a la animacion que toque ejecutar segun el movimiento principal que ejecute el personaje
        if (this.numAnimacion == numAnimacion && !forzarAnimacion) return;  //Si la animacion no cambia, no hay que hacer nada, que todo se siga animando como siempre, asi se evita malgastar potencia de calculo
        this.numAnimacion = numAnimacion;                                   //En caso negativo, salvamos el valor de la nueva animacion para poderlo comprobar en un futuro, y aplicamos la nueva animacion a todas las partes
        
        cuerpo.setAnimacion(numAnimacion, forzarAnimacion);
        cabeza.setAnimacion(numAnimacion, forzarAnimacion);
        yelmo.setAnimacion(numAnimacion, forzarAnimacion);
        peto.setAnimacion(numAnimacion, forzarAnimacion);
        pantalones.setAnimacion(numAnimacion, forzarAnimacion);
        guantes.setAnimacion(numAnimacion, forzarAnimacion);
        botas.setAnimacion(numAnimacion, forzarAnimacion);
        hombreras.setAnimacion(numAnimacion, forzarAnimacion);
        capaTrasera.setAnimacion(numAnimacion, forzarAnimacion);
        capaFrontal.setAnimacion(numAnimacion, forzarAnimacion);
    }
    
    //VESTIR SLOT ARMADURA: Metodos para vestirse un slot de armadura, carga esa armadura de la lista de armadura de Mundo, creando una copia de la pieza que deseamos
    //Luego la movemos a la posicion donde esta el personaje, le damos los datos del offset y se tercia, y si debe usar las coordenadas de la camara para el caso que sea un Pixie seguido por esta
    //para finalmente activar la animacion que esta ejecutando todo el cuerpo, con booleano de forzarSincronizacion, para que reinicien todas las animaciones, y asi todas empiecen por el mismo frame
    public void setCuerpo (int numCuerpo)           //CUERPO:      
    {   cuerpo = new Pixie (listaDeRazas.get(numRaza).listaDeCuerpos.get(numCuerpo)); 
        cuerpo.setPosition(getX(), getY());
        setAnimacion(numAnimacion, true);
    }
    public void setCabeza (int numCabeza)           //CABEZA:
    {   cabeza = new Pixie (listaDeRazas.get(numRaza).listaDeCabezas.get(numCabeza)); 
        cabeza.setPosition(getX(), getY());
        setAnimacion(numAnimacion, true);
    }
    public void setYelmo (int numArmadura)          //YELMO:
    {   yelmo = new Pixie (listaDeRazas.get(numRaza).listaDeYelmos.get(numArmadura)); 
        yelmo.setPosition(getX(), getY());
        yelmo.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
    public void setPeto (int numArmadura)           //PETO:
    {   peto = new Pixie (listaDeRazas.get(numRaza).listaDePetos.get(numArmadura)); 
        peto.setPosition(getX(), getY()); 
        setAnimacion(numAnimacion, true); 
    }
    public void setPantalones (int numArmadura)     //PANTALONES:
    {   pantalones = new Pixie (listaDeRazas.get(numRaza).listaDePantalones.get(numArmadura)); 
        pantalones.setPosition(getX(), getY()); 
        setAnimacion(numAnimacion, true);
    }
    public void setGuantes (int numArmadura)        //GUANTES:
    {   guantes = new Pixie (listaDeRazas.get(numRaza).listaDeGuantes.get(numArmadura)); 
        guantes.setPosition(getX(), getY()); 
        setAnimacion(numAnimacion, true);
    }
    public void setBotas (int numArmadura)          //BOTAS:   
    {   botas = new Pixie (listaDeRazas.get(numRaza).listaDeBotas.get(numArmadura)); 
        botas.setPosition(getX(), getY()); 
        setAnimacion(numAnimacion, true);
    }
    public void setHombreras (int numArmadura)      //HOMBRERAS:
    {   hombreras = new Pixie (listaDeRazas.get(numRaza).listaDeHombreras.get(numArmadura));
        hombreras.setPosition(getX(), getY());
        setAnimacion(numAnimacion, true);
    }
    public void setCapaTrasera (int numArmadura)    //CAPA TRASERA:
    {   capaTrasera = new Pixie (listaDeRazas.get(numRaza).listaDeCapasTraseras.get(numArmadura));
        capaTrasera.setPosition(getX(), getY());
        capaTrasera.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
    public void setCapaFrontal (int numArmadura)    //CAPA FRONTAL:
    {   capaFrontal = new Pixie (listaDeRazas.get(numRaza).listaDeCapasFrontales.get(numArmadura));
        capaFrontal.setPosition(getX(), getY());
        capaFrontal.setOffset(-24, -24);
        setAnimacion(numAnimacion, true);
    }
       
    @Override
    public void act (float delta)
    {
        super.act(delta);
        sombra.setPosition(this.getX()+8, this.getY()-5);
    }
    
    @Override
    public void draw (Batch batch, float alpha)
    {   //El orden de dibujado cambia segun la direccion en la que te muevas, ya que las hombreras por ejemplo deben ser tapadas por la capa al moverse en las diagonales superiores:
        if (numAnimacion == 0 || numAnimacion == 1 || numAnimacion == 2 || numAnimacion == 10 )
        {   
            sombra.draw(batch, alpha);
            capaFrontal.draw(batch, alpha);
            cuerpo.draw(batch, alpha);
            cabeza.draw(batch, alpha);
            yelmo.draw(batch, alpha);
            peto.draw(batch, alpha);
            pantalones.draw(batch, alpha);
            guantes.draw(batch, alpha);
            hombreras.draw(batch, alpha);
            botas.draw(batch, alpha);
            capaTrasera.draw(batch, alpha);
        }
        else
        {   
            sombra.draw(batch, alpha);
            capaFrontal.draw(batch, alpha);
            capaTrasera.draw(batch, alpha);
            cuerpo.draw(batch, alpha);
            cabeza.draw(batch, alpha);
            yelmo.draw(batch, alpha);
            peto.draw(batch, alpha);
            pantalones.draw(batch, alpha);
            guantes.draw(batch, alpha);
            hombreras.draw(batch, alpha);
            botas.draw(batch, alpha);
        }
    }
}
