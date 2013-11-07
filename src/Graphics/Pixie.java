package Graphics;

import Constantes.MiscData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import static Screens.PantallaJuego.camara;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Ivan Delgado Huerta
 */

//La clase Pixie es un Sprite animado, pero puede usarse para dibujar imagenes estaticas tambien
public class Pixie extends Actor
{
    private TextureRegion[][] frames;           //lista con las distintas animaciones, hay una fila para cada una de las posibibles animaciones, y en cada una de ellas, en las numCols estan todos los frames de dichas animaciones
    private TextureRegion frameActual;          //Textura del frame actual
    private Animation animation;                //es la clase LibGDX que controla la animation
    private Vector2 Offset = new Vector2(0,0);  //Rectificacion de la posicion de la animacion
    
    private int numAnimacion;                   //es la animacion que se esta mostrando en este momento de todas las posibles que hay en el vector de animaciones (frames)
    private float duracionFrame;                //Es el tiempo que se mostrara cada frame de nuestra animacion
    private float stateTime = 0f;               //es el contador que controla cuanto tiempo tiene que mostrarse cada frame, se reinicia al final de la animation, el tiempo de cada animation se define en el constructor de la clase Animation
    private boolean isLooping = true;           //por si queremos que la animation se repita indefinidamente
    private boolean isAnimado = true;           //para cuando tratemos con texturas sin animacion
    private boolean isPausado = false;          //por si queremos pausar la animacion
    
    private boolean usarCoordenasCamara = false; //Hay veces que la camara sigue al Pixie, y como desde que la camara pilla las coordenadas del pixie hasta que se dibuja el pixie pasa un rato, suele suceder que la posicion de este ultimo se
                                                //actualiza, desincronizandose y produciendose el efecto parpadeo, para evitar esto, mientras se dibuja el frame actual y solo para el Pixie que esta siendo seguido por la camara hay que usar
                                                //las coordenadas tomadas en el momento de empezar a dibujar el frame (en este caso las coordenadas X,Y de la camara). Para cumplir con este cometido usamos este booleano que se encargara de coger
                                                //las coordenadas de la camara en lugar de las del pixie en caso de ser verdadero
    
    public void resetAnimacion ()                       { stateTime = 0; }
    public void setPausa()                              { isPausado = true; }
    public void setUsarCoordenadasCamara (Boolean b)    { usarCoordenasCamara = b; }
    public void setOffset (int X, int Y)                { Offset.set(X, Y); }
    
    public Vector2 getOffset ()                         { return Offset; }
    
    
    //CONSTRUCTOR PARA ANIMACIONES:
    //texture: es la textura que contiene el mapa con todas las animaciones
    //filas, numCols: es el numero de numFilas y numCols que tiene la matriz de las animaciones
    //numFramesAnimacion: es el numero de frames que tiene cada animacion, por si en una fila hay varias animaciones juntas
    //isLooping: para repetir la animacion sin parar
    //framesEnlazados: cuando el ultimo frame no enlaza con el primero de forma que la animacion es solo la mitad de esta, y la otra mitad es la simetrica de lo ya hecho, es decir, una animacion enlazada seria 1,2,3,4... y repetir 1,2,3,4... y una
    //animacion no enlazada seria 1,2,3,4 y luego 3,2 para volver a empezar con 1,2,3,4... y luego 3,2. Por tanto cuando los frames no estan enlazados hay que acabar de construir la animacion simetrica.
    
    public Pixie (TextureRegion texture, int filas, int columnas, int numFramesAnimacion, float duracionFrame, boolean isEnlazado, boolean isLooping)
    {
        this.isAnimado = true;
        this.isLooping = isLooping;
        this.duracionFrame = duracionFrame;
        //Calculamos el tamaño de cada frame sabiendo el tamaño total de la textura y el numero de numFilas y numCols
        int anchoFrame = texture.getRegionWidth()/columnas;
        int altoFrame = texture.getRegionHeight()/filas;
        int numFramesAnimacionEnlazada;
        //una vez calculado aplicamos esas propiedades al Actor Pixie
        this.setWidth(anchoFrame);
        this.setHeight(altoFrame);
        //Dividimos la textura en texturas mas pequeñas (una por frame), las reordenamos y las ponemos en un matriz en la que cada fila contenga una unica animation
        TextureRegion[][] tmp = texture.split(anchoFrame, altoFrame);
        //Como cada columna puede contener varias animaciones, tendremos que ponerlas en numFilas diferentes
        int animacionesPorFila = columnas/numFramesAnimacion;
        //Dependiendo de si la animacion esta enlazada con el principio o no, tendremos suficiente con la cantidad de frames de la animacion original en cada fila o el doble de estos menos 2 
        if (isEnlazado) numFramesAnimacionEnlazada = numFramesAnimacion;
        else numFramesAnimacionEnlazada = numFramesAnimacion*2-2;
        
        //Creamos el vector Frames que contendra todas las animaciones, y las copiamos desde el fichero original
        frames = new TextureRegion[filas*animacionesPorFila][numFramesAnimacionEnlazada];
        
        int filaFrame = 0; int colFrame = 0;
        
        for (int i=0; i<filas;i++)
        {   for (int j=0;j<columnas;j++)
            {   
                frames[filaFrame][colFrame] = tmp[i][j];
                int numFrame = j%numFramesAnimacion;
                if (!isEnlazado && numFrame>0 && numFrame<(numFramesAnimacion-1) )  
                {   //El primer y el ultimo frame nos los saltamos ya que si no los solapariamos 2 veces, 1,2,3,4...3,2... ademas hay que poner la animacion de forma simetrica, en la posicion 0 desde el final va el segundo frame
                    //y a partir de ahi todos los demas
                    frames[filaFrame][numFramesAnimacionEnlazada-1-(numFrame-1)] = tmp[i][j]; 
                }
                colFrame++;
                if (colFrame >= numFramesAnimacion) {colFrame = 0;filaFrame++;}
            }
        }
        //Cargamos una animacion por defecto, la primera:
        animation = new Animation (duracionFrame, frames[0]);
        numAnimacion = 0;
        stateTime = 0f;
    }
    //CONSTRUCTORES ALTERNATICOS: asumiendo parametros por defecto
    public Pixie (TextureRegion texture, int filas, int columnas, int duracionFrame, boolean isEnlazado, boolean looping)
    {   this (texture, filas, columnas, columnas, duracionFrame, isEnlazado, looping); }
    
    public Pixie (TextureRegion texture, int filas, int columnas)
    {   this (texture, filas, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false, true);}
    
    public Pixie (TextureRegion texture, int columnas)
    {   this (texture, 1, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false, true); }
    
    //CONSTRUCTOR PARA ESTATICOS: Si no se especifan numero de numCols, es que no hay animacion, y se considera estatico
    public Pixie (TextureRegion texture)
    {
        frameActual = texture;
        isAnimado = false;
        
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionHeight());
    }
    
    //CONSTRUCTOR COPIA, que construye un Pixie a partir de otro que se pasa como parametro, copiando todos sus datos.
    //Se hace asi puesto que los pixies tienen que ser independientes e instanciados para que no compartan las animaciones
    public Pixie (Pixie pixie)
    {
        numAnimacion = pixie.numAnimacion;
        duracionFrame = pixie.duracionFrame;
        isLooping = pixie.isLooping;
        isAnimado = pixie.isAnimado;
        
        if (isAnimado)
        {   //Si es una animacion hay que copiar todos los frames
            int numFilas = pixie.frames.length;
            int numCols = pixie.frames[0].length;
            frames = new TextureRegion[numFilas][numCols];

            for (int i=0; i<numFilas; i++)
            {   for (int j=0; j<numCols; j++)
                {   frames[i][j] = pixie.frames[i][j]; }
            }
            animation = new Animation (duracionFrame, frames[numAnimacion]);
        }
        else
        {   //si solo es una textura, basta copiar el frameActual
            frameActual = pixie.frameActual;
        }
    }
    
    //METODOS Principales:
    public void setAnimacion (int numAnimacion)
    {
        if (numAnimacion >= frames.length) { System.out.println("Animacion no existe: "+numAnimacion); }
        else 
        {
            if (numAnimacion != this.numAnimacion)
            {    
                animation = new Animation(duracionFrame, frames[numAnimacion]);
                this.numAnimacion = numAnimacion;
                stateTime = 0f;
            }
        }
    }
    
    @Override
    //Sobreescribimos el metodo draw de la clase Actor, para que sepa como dibujarse a si mismo cuando lo añadamos a un Stage
    public void draw (SpriteBatch batch, float alpha)
    {   //La unica forma de modificar el Alpha es cargar el color, modificar el canal alpha, dibujar el sprite y volver a restaurar el color
        Color oldColor = batch.getColor(); 
        Color color = batch.getColor();
        color.a = alpha;
        batch.setColor(color);
        
        if (isAnimado)
        {   //le añadimos al stateTime el tiempo que ha pasado desde el ultimo Render (llamado DeltaTime)
            if (!isPausado) { stateTime += Gdx.graphics.getDeltaTime(); }
            frameActual = animation.getKeyFrame(stateTime, isLooping);
                        
            if (usarCoordenasCamara == true) batch.draw(frameActual, camara.position.x + Offset.x, camara.position.y + Offset.y);
            else batch.draw(frameActual, getX() + Offset.x, getY() + Offset.y);
        }
        else 
        {   
            if (usarCoordenasCamara == true) batch.draw(frameActual, camara.position.x + Offset.x, camara.position.y + Offset.y);
            else batch.draw(frameActual, getX() + Offset.x, getY() + Offset.x); 
        }
        batch.setColor(oldColor);
    }
}
