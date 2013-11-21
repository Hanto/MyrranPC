package Interface;
// @author Ivan Delgado Huerta
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class BarraSkills 
{
    private Array<CasillaSkill> barraSkills = new Array<>();
    
    public static class CasillaSkill
    {
        public Group actor = new Group();
        public TextureRegion icono;
        public String nombreSkill;
        public char keyBind;
    }
    
    public void pim ()
    {
        
    }
}
