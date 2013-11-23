package Interface;
// @author Ivan Delgado Huerta
import Graficos.Recursos;
import Main.Mundo;
import Skills.Spell;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;


public class Barra extends Group
{
    public Array<Casilla> barraSkills = new Array<>();
    private DragAndDrop dad;
    
    private int anchoSlot;
    private int altoSlot;
    
    public static class Casilla
    {
        public Group apariencia = new Group();
        public int slot;
        public int spellID;
        public char keyBind;
    }
    
    public Barra (int numSkills)
    {
        dad = new DragAndDrop();
        
        for (int i=0; i<numSkills; i++)
        {
            final Casilla casilla = new Casilla();
            
            Image islot = new Image (Recursos.casillero);
            anchoSlot = (int)islot.getWidth();
            altoSlot = (int)islot.getHeight();
            
            casilla.apariencia.addActor(islot);
            casilla.apariencia.setColor(1f,1f,1f,0.5f);
            casilla.apariencia.setPosition(i*(anchoSlot+2), 0);
            casilla.slot = i;
            casilla.spellID = -1;
            
            dad.addSource(new Source(casilla.apariencia) 
            {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) 
                {
                    if (casilla.spellID >= 0)
                    {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        Group pay = new Group();
                        setApariencia(casilla, pay);
                        
                        payload.setDragActor(pay);
                        
                        payload.setObject(casilla);
                        return payload;
                    }
                    else return null;
                }
            });
            
            dad.addTarget(new Target(casilla.apariencia) {

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
                    System.out.println(((Casilla)payload.getObject()).spellID);
                    System.out.println(casilla.spellID);
                    int casillaDestino = casilla.slot;
                    casilla.slot = ((Casilla)payload.getObject()).slot;
                    ((Casilla)payload.getObject()).slot = casillaDestino;
                    System.out.println(((Casilla)payload.getObject()).slot); 
                }
            });
                        
            this.addActor(casilla.apariencia);
            barraSkills.add(casilla);
        }
    }
    
    public void setSpell (int slot, Spell spell)
    {   //Cogemos la casilla correspondiente a ese SLOT:
        Casilla casilla = barraSkills.get(slot);
     
        //SpellID, salvamos el ID del spell en la casilla:
        casilla.spellID = spell.getId();
        //Apariencia, generamos su nueva Aparariencia segun su spell ID:
        setApariencia(casilla, casilla.apariencia);   
    }
    
    public void setApariencia (Casilla casilla, Group group)
    {   //Genera la apariencia de la casilla segun sus datos:
        group.clearChildren();
        group.addActor(new Image (Mundo.listaDeSpells.get(casilla.spellID).getIcono()));
        group.setPosition(casilla.slot*(anchoSlot+2), 0);
    }
}
