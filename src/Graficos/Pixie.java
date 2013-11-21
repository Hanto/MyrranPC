package Graficos;
import Constantes.MiscData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
//@author Ivan Delgado Huerta
//La clase Pixie es un Sprite animado, pero puede usarse para dibujar imagenes estaticas tambien
public class Pixie extends Group
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
    public boolean ininterrumpible = false;     //Si queremos que una animacion se vea completa ininterrumpidamente activamos este booleano. Ninguna otra animacion puede interrumpirla hasta que no acabe.
    private boolean mediaAnimacion= false;      //por si no queremos que empalme el final de la animacion con el inicio haciendo bucle. (para animaciones de disparo, salto, etc...)
    private boolean animarYEliminarActor = false;//Despues de mostrar la animacion, eliminar el actor de su grupo/stage
    
    public void resetAnimacion ()                       { stateTime = 0; }
    public void setPausa()                              { isPausado = true; }
    public void setOffset (int X, int Y)                { Offset.set(X, Y); }
    public void setAnimarYEliminarActor (Boolean b)     { animarYEliminarActor = b; }
 
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
        
        this.setHeight(pixie.getHeight());
        this.setWidth(pixie.getWidth());
                
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
    {   //Si se esta mostrando una animacion Ininterrumpible, no dejamos que la nueva haga nada
        if (ininterrumpible) return;
        
        mediaAnimacion = false;
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
    
    public void setAnimacion (int numAnimacion, boolean ininterrumpible, boolean mediaAnimacion)
    {   //Si se esta mostrando una animacion Ininterrumpible, no dejamos que la nueva haga nada
        if (this.ininterrumpible) return;
        
        setAnimacion (numAnimacion);
        this.ininterrumpible = ininterrumpible;
        this.mediaAnimacion = mediaAnimacion;
    }
    
    public void setAnimacion (int numAnimacion, boolean forzarAnimacion, boolean ininterrumpible, boolean mediaAnimacion)
    {
        if (this.ininterrumpible && !forzarAnimacion) return;
        this.ininterrumpible = false;
        this.mediaAnimacion = false;
        setAnimacion (numAnimacion, ininterrumpible, mediaAnimacion);
    }
    
    @Override
    //Sobreescribimos el metodo draw de la clase Actor, para que sepa como dibujarse a si mismo cuando lo añadamos a un Stage
    public void draw (Batch batch, float alpha)
    {   //La unica forma de modificar el Alpha es cargar el color, modificar el canal alpha, dibujar el sprite y volver a restaurar el color
        Color oldColor = batch.getColor(); 
        Color color = batch.getColor();
        color.a = alpha;
        batch.setColor(color);
        
        if (isAnimado)
        {   //le añadimos al stateTime el tiempo que ha pasado desde el ultimo Render (llamado DeltaTime)           
            if (!isPausado) { stateTime += Gdx.graphics.getDeltaTime(); }
            
            if (mediaAnimacion)
            {   //Si no queremos mostrar la animacion de retorno debemos mostrar los (N+2)/2 frames
                if (stateTime>duracionFrame*(frames[0].length+2)/2) 
                {   //Como ya se ha mostrado la mitad de la animacion, si es ininterrumpible, debemos desactivarlo, para que otras animaciones puedan activarse
                    stateTime = 0;
                    if (ininterrumpible = true) ininterrumpible = false;
                    if (animarYEliminarActor) this.getParent().removeActor(this);
                }
            }
            else
            {   //En caso contrario, si hemos indicado que sea ininterrumpible, como ya se ha mostrado toda, debemos desactivarlo, para que otras animaciones puedan activarse
                if (stateTime>duracionFrame*(frames[0].length))
                { 
                    if (ininterrumpible == true) ininterrumpible = false;
                    if (animarYEliminarActor) this.getParent().removeActor(this);
                }
            }
            //Cargamos el frame correspondiente a nuestro stateTime:
            frameActual = animation.getKeyFrame(stateTime, isLooping);          
            batch.draw(frameActual, getX() + Offset.x, getY() + Offset.y);
        } 
        else { batch.draw(frameActual, getX() + Offset.x, getY() + Offset.x); }
        
        batch.setColor(oldColor);
    }
}
