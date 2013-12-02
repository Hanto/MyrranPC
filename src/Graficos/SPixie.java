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

public class SPixie extends Actor
{
    public static class Animacion
    {
        private TextureRegion [] frames;
        public String nombreAnimacion;
        public float duracionFrame;     
        public boolean loop = true;
        public boolean reverse = true;
        public boolean ininterrumpible = false;
        public boolean animarYEliminar = false;
        //Constructor:
        public Animacion (int numFrames, boolean reverse)
        {   
            if (reverse) frames = new TextureRegion [numFrames*2-2]; 
            else frames = new TextureRegion [numFrames];
        }
    }
    
    private Array<Animacion> animaciones = new Array<>();
    private Animation animation;
    private TextureRegion frameActual;
    private Vector2 Offset = new Vector2(0,0);
    private float stateTime = 0f;
    private boolean isPausado = false;
    
    private int numAnimacion=-1;
    private float duracionFrame;
    private boolean loop = true;
    private boolean reverse = true;
    private boolean ininterrumpible = false;
    public boolean animarYEliminar = false;
    
    private TextureRegion texturaOriginal;
    private int filas;
    private int columnas;
    
    public Array<Animacion> animaciones()                               { return animaciones; }
    public int getNumFramesAnimacion()                                  { return animaciones.get(numAnimacion).frames.length; }
    
    public SPixie (TextureRegion texture, int filas, int columnas)
    {
        texturaOriginal = texture;
        this.filas = filas;
        this.columnas = columnas;
        
        int anchoFrame = texture.getRegionWidth()/columnas;
        int altoFrame = texture.getRegionHeight()/filas;
        this.setWidth(anchoFrame);
        this.setHeight(altoFrame);
    }
    
    public SPixie (TextureRegion texture, int filas, int columnas, int numFramesPorAnimacion, float duracionFrame)
    {
        texturaOriginal = texture;
        this.filas = filas;
        this.columnas = columnas;
        
        int anchoFrame = texture.getRegionWidth()/columnas;
        int altoFrame = texture.getRegionHeight()/filas;
        this.setWidth(anchoFrame);
        this.setHeight(altoFrame);
        
        TextureRegion[][] framesIndividuales = texture.split(anchoFrame, altoFrame);
        int animacionesPorFila = columnas/numFramesPorAnimacion;
        animaciones = new Array<>(animacionesPorFila*filas);
        
        int numFrame;
        Animacion animacion = new Animacion(numFramesPorAnimacion, true);
        for (int i=0; i<filas;i++)
        {
            for (int j=0; j<columnas;j++)
            {  
                numFrame = j%numFramesPorAnimacion;
                animacion.frames[numFrame] = framesIndividuales[i][j];
                if (numFrame >0 && numFrame<numFramesPorAnimacion-1)
                { animacion.frames[numFramesPorAnimacion-1+numFrame] = animacion.frames[numFrame]; }

                if (numFrame >= numFramesPorAnimacion-1) 
                {
                    animacion.duracionFrame = duracionFrame;
                    animaciones.add(animacion); 
                    animacion = new Animacion(numFramesPorAnimacion, true); 
                }
            }
        }
        
        animation = new Animation (animaciones.get(0).duracionFrame, animaciones.get(0).frames);
        this.duracionFrame = animaciones.get(0).duracionFrame;
        this.reverse = animaciones.get(0).reverse;
        this.loop = animaciones.get(0).loop;
        numAnimacion = 0;
        stateTime = 0f;
        
    }
    
    public SPixie (SPixie pixie)
    {
        filas = pixie.filas;
        columnas = pixie.columnas;
        texturaOriginal = pixie.texturaOriginal;
        
        numAnimacion = pixie.numAnimacion;
        duracionFrame = pixie.duracionFrame;
        loop = pixie.loop;
        reverse = pixie.reverse;
        
        for (int i=0;i<pixie.animaciones.size; i++)
        {
            Animacion origen = pixie.animaciones.get(i);
            Animacion destino = new Animacion(origen.frames.length, false);
            for (int j=0; j<origen.frames.length; j++)
            {
                destino.frames[j] = origen.frames[j];
            }
            destino.duracionFrame = origen.duracionFrame;
            destino.animarYEliminar = origen.animarYEliminar;
            destino.loop = origen.loop;
            destino.nombreAnimacion = origen.nombreAnimacion;
            destino.reverse = origen.reverse;
            destino.ininterrumpible = origen.ininterrumpible;
            destino.animarYEliminar = origen.animarYEliminar;
            animaciones.add(destino);
        }
        
        animation = new Animation (animaciones.get(0).duracionFrame, animaciones.get(0).frames);
        this.duracionFrame = animaciones.get(0).duracionFrame;
        this.reverse = animaciones.get(0).reverse;
        this.loop = animaciones.get(0).loop;
        numAnimacion = 0;
        stateTime = 0f;
    }
    
    public void añadirAnimacion (String nombreAnimacion, int[] numFramesAnimacion, float duracionFrame, boolean reverse)
    {
        Animacion animacion = new Animacion(numFramesAnimacion.length, reverse);
        animacion.nombreAnimacion = nombreAnimacion;
        animacion.duracionFrame = duracionFrame;
        animacion.frames = new TextureRegion [numFramesAnimacion.length*2-2];
        
        TextureRegion[][] framesIndividuales = texturaOriginal.split((int)this.getWidth(), (int)this.getHeight());
        
        for (int k=0; k<numFramesAnimacion.length;k++)
        {
            for (int i=0; i<filas;i++)
            {
                for (int j=0;j<columnas;j++)
                {
                    if (numFramesAnimacion[k] == i*columnas+j) animacion.frames[k] = framesIndividuales[i][j];
                }
            }
        }
        
        int numFramesAnimacionReverse = numFramesAnimacion.length-2;
        
        for (int i=0;i<numFramesAnimacionReverse;i++)
        {
            animacion.frames[numFramesAnimacion.length+i] = animacion.frames[numFramesAnimacion.length-i-2]; 
        }
        animaciones.add(animacion);
    }
    
    public void setAnimacion (int numAnimacion, boolean forzarAnimacion)
    {
        if (numAnimacion <0 || numAnimacion >= animaciones.size)    { return; }
        if (ininterrumpible && !forzarAnimacion)                    { return; }
        
        if (numAnimacion != this.numAnimacion)
        {
            Animacion animacion = animaciones.get(numAnimacion);
            duracionFrame = animacion.duracionFrame;
            loop = animacion.loop;
            reverse = animacion.reverse;
            ininterrumpible = animacion.ininterrumpible;
            animarYEliminar = animacion.animarYEliminar;
            
            animation = new Animation(duracionFrame, animacion.frames);
        }
    }
    
    @Override public void draw (Batch batch, float alpha)
    {
        Color oldColor = batch.getColor(); 
        Color color = batch.getColor();
        color.a = alpha;
        batch.setColor(color);
        
        int framesAnimacion = getNumFramesAnimacion();
        
        if (!isPausado) stateTime += Gdx.graphics.getDeltaTime();
        
        if (!reverse && stateTime > duracionFrame*(framesAnimacion+2)/2)
        {
            stateTime = 0f;
            if (ininterrumpible = true) ininterrumpible = false;
            if (animarYEliminar) this.getParent().removeActor(this);
        }
        if (reverse && stateTime > duracionFrame*(framesAnimacion))
        {
            if (ininterrumpible = true) ininterrumpible = false;
            if (animarYEliminar) this.getParent().removeActor(this);
        }
        frameActual = animation.getKeyFrame(stateTime, loop);
        batch.draw(frameActual, getX() + Offset.x, getY() + Offset.y);
        
        batch.setColor(oldColor);
    }
}
