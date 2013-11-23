package Interface;
// @author Ivan Delgado Huerta
import Graficos.Recursos;
import Graficos.Texto;
import Skills.Spell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;


public class BarraSkills extends Group
{
    public Array<Casillero> barraSkills = new Array<>();
    public DragAndDrop dad;
        
    public static class CasillaSkill
    {
        public Group apariencia = new Group();
        public TextureRegion icono;
        public String nombreSkill;
        public int spellIDAsociado;
        public int posicion;
        public char keyBind;
    }
    
    public static class Casillero
    {
        public Image apariencia;
        public int spellIDAsociado;
        public int posicion;
        public char keyBind;
    }
    
    public BarraSkills (int numSkills)
    {
        dad = new DragAndDrop();
        
        for (int i=0; i<numSkills; i++)
        {
            final Casillero casillero = new Casillero();
            casillero.apariencia = new Image (Recursos.casillero);
            casillero.apariencia.setColor(1f,1f,1f,0.5f);
            casillero.apariencia.setBounds(i*34, 0, 32, 32);
            casillero.posicion = i;
            
            dad.addTarget(new Target(casillero.apariencia) {

                @Override
                public boolean drag(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                { return true; }
                
                @Override
                public void reset (Source source, DragAndDrop.Payload payload) 
                { super.reset(source, payload); }
                
                @Override
                public void drop(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                {
                    source.getActor().setPosition(casillero.apariencia.getX(), casillero.apariencia.getY());
                    ((CasillaSkill)payload.getObject()).posicion = casillero.posicion;
                    System.out.println(((CasillaSkill)payload.getObject()).nombreSkill);
                    System.out.println(((CasillaSkill)payload.getObject()).posicion);           
                }
            });
                        
            this.addActor(casillero.apariencia);
            barraSkills.add(casillero);
        }
    }
    
    public void generarCasillaSkill (Spell spell, int numCasilla)
    {
        final CasillaSkill casilla = new CasillaSkill();
        casilla.icono = spell.getIcono();
        casilla.nombreSkill = spell.getNombre();
        casilla.spellIDAsociado = spell.getId();
        casilla.posicion = numCasilla;
        casilla.apariencia.addActor(new Image (casilla.icono));
        Texto.printTexto(casilla.nombreSkill, Recursos.font14, Color.WHITE, Color.BLACK, 16, -8, Align.center, Align.bottom, 2, casilla.apariencia);
        casilla.apariencia.setPosition((numCasilla*34),0);
        
        dad.addSource(new Source(casilla.apariencia) 
        {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) 
            {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                Image pim = new Image (casilla.icono);
                payload.setDragActor(pim);
                
                payload.setObject(casilla);
                return payload;
            }
        });
        
        dad.addTarget(new Target(casilla.apariencia) 
        {
            @Override
            public boolean drag(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
            { return true; }
            
            @Override
            public void reset (Source source, DragAndDrop.Payload payload) 
            { super.reset(source, payload); }
            
            @Override
            public void drop(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
            {
                float destinoX = getActor().getX();
                float destinoY = getActor().getY();
                this.getActor().setPosition(source.getActor().getX(), source.getActor().getY());
                source.getActor().setPosition(destinoX, destinoY);
                System.out.println(((CasillaSkill)payload.getObject()).nombreSkill);
                System.out.println(casilla.nombreSkill);
                int casillaDestino = casilla.posicion;
                casilla.posicion = ((CasillaSkill)payload.getObject()).posicion;
                ((CasillaSkill)payload.getObject()).posicion = casillaDestino;
                System.out.println(((CasillaSkill)payload.getObject()).posicion);
            }
        });
        
        this.addActor(casilla.apariencia);
    }
}
