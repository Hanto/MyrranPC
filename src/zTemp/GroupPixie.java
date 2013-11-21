package zTemp;

import Constantes.MiscData;
import Graficos.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author Ivan Delgado Huerta
 */

//la clase GroupPixie extiende la clase grupo para poder aplicar sobre la clase Pixie los efectos de Escalado y rotacion, ya que estos solo funcionan sobre grupos. De esta manera disponemos de una clase
//que hereda las propiedas de los grupos y dispone del campo Pixie para disponer de Sprites animados
public class GroupPixie extends Group
{   
    public Pixie pixie;                 //Campo con la animacion
    
    //CONSTRUCTOR PARA ANIMACIONES:
    public GroupPixie (TextureRegion texture, int filas, int columnas, int numFramesAnimacion, float duracionFrame, boolean isEnlazado)
    {
        pixie = new Pixie (texture, filas, columnas, numFramesAnimacion, duracionFrame, isEnlazado);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    //CONSTRUCTORES ALTERNATICOS: asumiendo parametros por defecto
    public GroupPixie (TextureRegion texture, int filas, int columnas, int duracionFrame, boolean isEnlazado)
    {   this (texture, filas, columnas, columnas, duracionFrame, isEnlazado); }
    
    public GroupPixie (TextureRegion texture, int filas, int columnas)
    {   this (texture, filas, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false);}
    
    public GroupPixie (TextureRegion texture, int columnas)
    {   this (texture, 1, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false); }
    
    //CONSTRUCTOR PARA ESTATICOS: Si no se especifan numero de columnas, es que no hay animacion, y se considera estatico
    public GroupPixie (TextureRegion texture)
    {   
        pixie = new Pixie (texture);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    
    //CONSTRUCTOR COPIA, que copia un GroupPixie a partir de otro que se pasa como parametro:
    public GroupPixie (GroupPixie gpixie)
    {
        pixie = new Pixie (gpixie.pixie);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    
    //CONSTRUCTOR TRANSFORMADOR: convierte un pixie en GroupPixie
    public GroupPixie (Pixie pixie)
    {
        this.pixie = pixie;
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    
    //METODOS Principales:
    public void setAnimacion (int numAnimacion)
    {   pixie.setAnimacion(numAnimacion); }
}
