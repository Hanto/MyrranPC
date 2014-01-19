package zTemp;

import Constantes.MiscData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author Ivan Delgado Huerta
 */

//la clase GroupPixieOld extiende la clase grupo para poder aplicar sobre la clase PixieOld los efectos de Escalado y rotacion, ya que estos solo funcionan sobre grupos. De esta manera disponemos de una clase
//que hereda las propiedas de los grupos y dispone del campo PixieOld para disponer de Sprites animados
public class GroupPixieOld extends Group
{   
    public PixieOld pixie;                 //Campo con la animacion
    
    //CONSTRUCTOR PARA ANIMACIONES:
    public GroupPixieOld (TextureRegion texture, int filas, int columnas, int numFramesAnimacion, float duracionFrame, boolean isEnlazado)
    {
        pixie = new PixieOld (texture, filas, columnas, numFramesAnimacion, duracionFrame, isEnlazado);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    //CONSTRUCTORES ALTERNATICOS: asumiendo parametros por defecto
    public GroupPixieOld (TextureRegion texture, int filas, int columnas, int duracionFrame, boolean isEnlazado)
    {   this (texture, filas, columnas, columnas, duracionFrame, isEnlazado); }
    
    public GroupPixieOld (TextureRegion texture, int filas, int columnas)
    {   this (texture, filas, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false);}
    
    public GroupPixieOld (TextureRegion texture, int columnas)
    {   this (texture, 1, columnas, columnas, MiscData.PIXIE_DuracionFrame_Medio, false); }
    
    //CONSTRUCTOR PARA ESTATICOS: Si no se especifan numero de columnas, es que no hay animacion, y se considera estatico
    public GroupPixieOld (TextureRegion texture)
    {   
        pixie = new PixieOld (texture);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    
    //CONSTRUCTOR COPIA, que copia un GroupPixieOld a partir de otro que se pasa como parametro:
    public GroupPixieOld (GroupPixieOld gpixie)
    {
        pixie = new PixieOld (gpixie.pixie);
        this.setWidth(pixie.getWidth());
        this.setHeight(pixie.getHeight());
        this.addActor(pixie);
    }
    
    //CONSTRUCTOR TRANSFORMADOR: convierte un pixie en GroupPixieOld
    public GroupPixieOld (PixieOld pixie)
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
