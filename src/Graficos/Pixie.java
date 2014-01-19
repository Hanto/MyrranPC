package Graficos;
// @author Ivan Delgado Huerta
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Pixie extends Actor
{
    private Array<Animacion> animaciones = new Array<>();   //Array que contiene los datos de cada Animacion
    private Animation animation;                    //Animacion actual, de la que se extrae el frame que se dibujara
    private TextureRegion frameActual;              //Frame actual que se dibujara
    private Vector2 Offset = new Vector2(0,0);      //Rectificacion de la posicion de la animacion
    private float stateTime = 0f;                   //es el contador que controla cuanto tiempo tiene que mostrarse cada frame     
    private int numAnimacion = -1;                  //ID de la animacion que se esta mostrando en este momento de entre las que hay en el array de Animaciones
    private int siguienteNumAnimacion = -1;         //En el caso de ejecutarse un animacion ININTERRUMPIBLE, si se cambia de animacion, esta se pone en cola como siguienteNumAnimacion
    private float duracionFrame;                    //Define el tiempo que se mostrara cada frame, se indica en el constructor del animation
    
    private boolean ininterrumpible = false;        //Si queremos que una animacion se vea completa ininterrumpidamente, ninguna otra animacion puede interrumpirla hasta que no acabe, poniendose en cola y ejecutandose a su fin
    private boolean isPausado = false;              //por si queremos pausar la animacion
    private boolean loop = true;                    //por si queremos que la animacion se repita indefinidamente
    private boolean reverse = true;                 //siempre que se crea una animacion se generan frames adicionales para empalmarla con el inicio de la animacion, con Reverse = true, se recorreran esos frames, con false, no
    private boolean animarYEliminar = false;        //despues de mostrar la animacion, se eliminara el actor de su grupo/stage
    
    private TextureRegion texturaOriginal;          //textura original de la cual se han extraido los frames
    private int filas;                              //numero de filas que contiene la textura original
    private int columnas;                           //numero de columnas que contiene la textura original
    
    public static class Animacion                   //Cada animacion tiene sus propios datos de configuracion esto es sus propios parametros como, duracionFrame, interrumpible, etc...
    {                                               //Estos datos se cargan automaticamente al cargar la animacion en si
        private TextureRegion [] frames;            //Frames que componen la animacion:
        public String nombreAnimacion;              //Nombre de la animacion
        public float duracionFrame;                 //Duracion de la animacion
        public boolean loop = true;                 //Para que se repita indefinidamente
        public boolean reverse = true;              //Para que empalme con el inicio de la animacion
        public boolean ininterrumpible = false;     //Para que ninguna otra animacion pueda interrumpirla hasta acabar esta
        public boolean animarYEliminar = false;     //para autoeliminarse despues de animarse
        //Constructor:
        public Animacion (int numFrames, boolean generarReverse)
        {   //Constructor que inicializa el tamaño del array de frames. Este tamaño depende de si queremos que nos autocalcule el espacio para los frames de reverse o no
            if (generarReverse) frames = new TextureRegion [numFrames*2-2]; 
            else frames = new TextureRegion [numFrames];
        }
    }
    
    public Array<Animacion> animaciones()                               { return animaciones; }
    public int getNumFramesAnimacion()                                  { return animaciones.get(numAnimacion).frames.length; }
    public void setOffset (int X, int Y)                                { Offset.set(X, Y); }
    
    //CONSTRUCTOR PARA ANIMACIONES:
    //texture: es la textura que contiene el mapa con todas las animaciones
    //filas, numCols: es el numero de numFilas y numCols que tiene la textura
    
    public Pixie (TextureRegion texture, int filas, int columnas)
    {
        texturaOriginal = texture;
        this.filas = filas;
        this.columnas = columnas;
        //Calculamos el tamaño de cada frame sabiendo el tamaño total de la textura y el numero de numFilas y numCols
        int anchoFrame = texture.getRegionWidth()/columnas;
        int altoFrame = texture.getRegionHeight()/filas;
        //una vez calculado aplicamos esas propiedades al Actor Pixie
        this.setWidth(anchoFrame);
        this.setHeight(altoFrame);
    }
    
    //CONSTRUCTOR PARA ANIMACIONES:
    //texture: es la textura que contiene el mapa con todas las animaciones
    //filas, numCols: es el numero de numFilas y numCols que tiene la textura
    //numFramesAnimacion: es el numero de frames que tiene cada animacion, por si en una fila hay varias animaciones juntas
    //duracionFrame: Tiempo que se muestra cada frame
    
    public Pixie (TextureRegion texture, int filas, int columnas, int numFramesPorAnimacion, float duracionFrame)
    {
        texturaOriginal = texture;
        this.filas = filas;
        this.columnas = columnas;
        //Calculamos el tamaño de cada frame sabiendo el tamaño total de la textura y el numero de numFilas y numCols
        int anchoFrame = texture.getRegionWidth()/columnas;
        int altoFrame = texture.getRegionHeight()/filas;
        //una vez calculado aplicamos esas propiedades al Actor Pixie
        this.setWidth(anchoFrame);
        this.setHeight(altoFrame);
        //Dividimos la textura en texturas mas pequeñas (una por frame), y las juntamos por numFramesPorAnimacion para salvarlas juntas como animacion
        TextureRegion[][] framesIndividuales = texture.split(anchoFrame, altoFrame);
        int animacionesPorFila = columnas/numFramesPorAnimacion;
        animaciones = new Array<>(animacionesPorFila*filas);
        
        int numFrame;
        Animacion animacion = new Animacion(numFramesPorAnimacion, true);
        for (int i=0; i<filas;i++)
        {
            for (int j=0; j<columnas;j++)
            {   //de esta forma recorremos siempre cada uno de los frames de la animacion, por ej, 0,1,2
                numFrame = j%numFramesPorAnimacion;
                animacion.frames[numFrame] = framesIndividuales[i][j];
                //generamos los frames de la animacion de retorno (la que enlaza con el frame 0)
                if (numFrame >0 && numFrame<numFramesPorAnimacion-1)
                { animacion.frames[numFramesPorAnimacion-1+numFrame] = animacion.frames[numFrame]; }
                //una vez generados todos los frames, le damos su duracion, lo añadimos y creamos el siguiente
                if (numFrame >= numFramesPorAnimacion-1) 
                {   
                    animacion.duracionFrame = duracionFrame;
                    animaciones.add(animacion);
                    animacion = new Animacion(numFramesPorAnimacion, true); 
                }
            }
        }
        //Inicializamos el Pixie con una animacion por defecto para que no pete:
        setAnimacion(0, true);
    }
    
    //CONSTRUCTOR COPIA, que construye un Pixie a partir de otro que se pasa como parametro, copiando todos sus datos.
    //Se hace asi puesto que los pixies tienen que ser independientes e instanciados para que no compartan las animaciones
    
    public Pixie (Pixie pixie)
    {
        Offset.set(pixie.Offset.x, pixie.Offset.y);
        stateTime = 0f;
        siguienteNumAnimacion = -1;
        duracionFrame = pixie.duracionFrame;
        
        ininterrumpible = pixie.ininterrumpible;
        isPausado = pixie.isPausado;
        loop = pixie.loop;
        reverse = pixie.reverse;
        animarYEliminar = pixie.animarYEliminar;
        
        texturaOriginal = pixie.texturaOriginal;
        filas = pixie.filas;
        columnas = pixie.columnas;
        
        this.setHeight(pixie.getHeight());
        this.setWidth(pixie.getWidth());
        
        //Hay que copiar cada una de las animaciones una a una:
        for (int i=0;i<pixie.animaciones.size; i++)
        {
            Animacion origen = pixie.animaciones.get(i);
            Animacion destino = new Animacion(origen.frames.length, false);
            for (int j=0; j<origen.frames.length; j++)
            {
                destino.frames[j] = origen.frames[j];
            }
            destino.nombreAnimacion = origen.nombreAnimacion;
            destino.duracionFrame = origen.duracionFrame;
            destino.loop = origen.loop;
            destino.reverse = origen.reverse;
            destino.ininterrumpible = origen.ininterrumpible;
            destino.animarYEliminar = origen.animarYEliminar;
            animaciones.add(destino);
        }
        //Inicializamos el Pixie con una animacion por defecto para que no pete:
        setAnimacion(0, true);
    }
    
    public void añadirAnimacion (String nombreAnimacion, int[] numFramesAnimacion, float duracionFrame, boolean reverse)
    {   //Creamos la animacion, haciendo que cree espacio automaticamente para la animacion de retorno
        Animacion animacion = new Animacion(numFramesAnimacion.length, true);
        animacion.nombreAnimacion = nombreAnimacion;
        animacion.duracionFrame = duracionFrame;
        animacion.reverse = reverse;
        //Dividimos la textura que le pasamos originalmente para poder acceder a sus frames
        TextureRegion[][] framesIndividuales = texturaOriginal.split((int)this.getWidth(), (int)this.getHeight());
        
        //Recorremos el array que contiene el indice de los frames que queremos para la animacion
        //para cada uno de ellos recorremos toda la textura buscando el frame cuyo indice sea igual a numFila*columnas+numColumna
        //una vez encontrado lo salvamos como parte de nuestra animacion. Si ese frame no esta en los extremos (no es el 0, ni el ultimo)
        //entonces es que forma parte de la animacion de reverse, y lo salvamos tantas posiciones delante, como longitud de la animacion
        for (int k=0; k<numFramesAnimacion.length;k++)
        {   
            for (int i=0; i<filas;i++)
            {   
                for (int j=0;j<columnas;j++)
                {
                    if (numFramesAnimacion[k] == i*columnas+j) animacion.frames[k] = framesIndividuales[i][j];
                    if (k>0 && k<numFramesAnimacion.length-1)
                    { animacion.frames[numFramesAnimacion.length-1+k] = animacion.frames[k]; }
                }
            }
        }
        animaciones.add(animacion);
    }
    
    //Metodo que carga la animacion y todos sus parametros de entre todas las disponibles en el array de animaciones
    //Si intentamos cargar la misma animacion que se esta mostrando no debe hacer nada, puesto que se trata de lo mismo
    //a no ser que activemos el booleano de forzarAnimacion, en cuyo caso no se volveria a cargar pero se reiniciaria
    //Esto se usa sobre todo en PixiePC al vestir un Item nuevo, para resincronizar todas las animaciones.
    //Si no la forzamos, tampoco debe hacer nada si intentamos cargar una animacion mientras se reproduce una ininterrumpible,
    //en cuyo caso la marcariamos como siguienteNumAnimacion, para mostrarse una vez acabe la que estamos mostrando.
    
    public final void setAnimacion (int numAnimacion, boolean forzarAnimacion)
    {   //Animacion no disponible, que no pete
        if (numAnimacion <0 || numAnimacion >= animaciones.size)    { return; } 
        //se esta mostrando una animacion Ininterrumpible y se intenta cargar otra sin forzarla, por tanto se marca como siguiente
        if (ininterrumpible && !forzarAnimacion)                    { siguienteNumAnimacion = numAnimacion; return; }
        //si forzamos la nueva animacion, es que se tiene que mostrar por encima de la que se este mostrando, aunque sea la misma
        //la forma de emularlo, puesto que volverla a cargar es redundante, es reiniciarla, ponerla desde el principio
        if (forzarAnimacion)                                        { stateTime = 0f; }
        
        if (numAnimacion != this.numAnimacion)
        {
            Animacion animacion = animaciones.get(numAnimacion);
            duracionFrame = animacion.duracionFrame;
            reverse = animacion.reverse;
            loop = animacion.loop;
            ininterrumpible = animacion.ininterrumpible;
            animarYEliminar = animacion.animarYEliminar;
            animation = new Animation(duracionFrame, animacion.frames);
            this.numAnimacion = numAnimacion;
            stateTime = 0f;
        }
    }
    
    //DRAW:
    //Segun hayamos activado el flag reverse o no, calculamos cuanto tiempo hace falta para reproducir toda la animacion, y asi
    //comparandola con el stateTime actual saber si ya se ha reproducido toda o no para poder activar los distintos flags y sus efectos
    //REVERSE, ININTERRUMPIBLE, SIGUIENTENUANIMACION, ANIMARYELIMINAR.
    //segun el stateTime sacamos el frame de la animacion actual y lo dibujamos en pantalla con el alpha modificado, y 
    //finalmente restauramos el color para devolver el alpha al estado original
    
    @Override public void draw (Batch batch, float alpha)
    {   //La unica forma de modificar el Alpha es cargar el color, modificar el canal alpha, dibujar el sprite y volver a restaurar el color
        Color oldColor = batch.getColor(); 
        Color color = batch.getColor();
        color.a = alpha;
        batch.setColor(color);
        
        int framesAnimacion = getNumFramesAnimacion();
        if (!isPausado) stateTime += Gdx.graphics.getDeltaTime();
        if (!reverse && stateTime > duracionFrame*(framesAnimacion+2)/2)    { stateTime = 0f; chequearFlags(); }
        if (reverse && stateTime > duracionFrame*(framesAnimacion))         { chequearFlags(); }
        
        frameActual = animation.getKeyFrame(stateTime, loop);
        batch.draw(frameActual, getX() + Offset.x, getY() + Offset.y);
        
        //Restauramos el color original
        batch.setColor(oldColor);
    }
    
    public void chequearFlags()
    {   //FLAG Ininterrumpible:
        if (ininterrumpible = true) 
        {
            ininterrumpible = false;
            //FLAG SiguienteNumAnimacion:
            if (siguienteNumAnimacion >=0)
            {
                setAnimacion(siguienteNumAnimacion, true);
                siguienteNumAnimacion = -1;
            }
        }
        //FLAG AnimarYEliminar:
        if (animarYEliminar) this.getParent().removeActor(this);
    }
}
