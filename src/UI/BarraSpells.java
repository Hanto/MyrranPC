package UI;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import Graficos.Recursos;
import Graficos.Texto;
import Main.Mundo;
import Skills.Spell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;

public class BarraSpells extends Group
{   
    public Array<Casilla> barra = new Array<>();            //Array que contiene el casillero
    private DragAndDrop dad = new DragAndDrop ();           //Clase que regula el drag and drop de las casillas
    private int anchoSlot;                                  //ancho de cada casilla
    private int altoSlot;                                   //alto de cada casilla
    private int numFilas;
    private int numColumnas;
    private boolean rebindearSkills = false;                //para cuando queremos rebindear los skills

    public void setRebindearSkills (boolean b)              { rebindearSkills = b; }
    public boolean getRebindearSkills ()                    { return rebindearSkills; }
    
    public static class Casilla
    {
        public Group apariencia = new Group();              //Icono que se muestra en pantalla para esa casilla
        public String keyBind;                              //Tecla bindeada a esa casilla
        public int keycode;                                 //Keycode que corresponde a ese bind
        public int spellID;                                 //spellID que corresponde a esa casilla
    }
    
    public BarraSpells (int numFilas, int numColumnas)
    {
        dad.setDragTime(0);
        this.numFilas = numFilas; this.numColumnas = numColumnas;
        
        for (int i=0; i<numFilas*numColumnas; i++)
        {
            final Casilla destino = new Casilla();
            
            Image islot = new Image (Recursos.casillero);
            anchoSlot = (int)islot.getWidth();
            altoSlot = (int)islot.getHeight();
            
            destino.spellID = -1;
            if (i<9){ destino.keyBind = String.valueOf(i+1); destino.keycode = i+8; }
            islot.setColor(0, 0, 0, 0.1f);
            destino.apariencia.addActor(islot);
            if (destino.keyBind != null) Texto.printTexto(String.valueOf(destino.keyBind), Recursos.font14, Color.ORANGE, Color.BLACK, 0, 20, Align.left, Align.bottom, 2, destino.apariencia);
            
            int columna = (i)/numColumnas;
            int fila = (i)%numColumnas;
            
            destino.apariencia.setPosition(fila*(anchoSlot+2), columna*(altoSlot+2)); //2 es el margen entre casillas
                        
            this.addActor(destino.apariencia);
            barra.add(destino);
            
            //Codigo para Rebindear Teclas
            destino.apariencia.addListener(new InputListener()
            {   //Hacemos que el stage acepte eventos de teclado del icono sobre el que el raton ha entrado
                @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
                { if (rebindearSkills) destino.apariencia.getStage().setKeyboardFocus(destino.apariencia);  }
                
                //Hacemos que deje de recibir eventos de teclado, puesto que el teclado ha salido
                @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
                { if (rebindearSkills) destino.apariencia.getStage().setKeyboardFocus(null); }
                
                //Capturamos que tecla aprieta el player para rebindearla
                @Override public boolean keyDown (InputEvent event, int keycode)
                {   //Solo rebindeamos los skills, si esta activado el boton de rebindear     
                    if (rebindearSkills) 
                    {   //Primero tenemos que buscar si ese bind ya existe en otro spell, en caso afirmativo, borrarlo
                        for (int i=0;i<barra.size;i++)
                        {
                            if (barra.get(i).keycode == keycode) 
                            {   //si ya existe, borramo sel bind, y actualizamos su apariencia
                                barra.get(i).keycode = 0;
                                barra.get(i).keyBind = "";
                                setApariencia(barra.get(i), barra.get(i).apariencia);
                            }
                       }//rebindeamos el skill, y actualizamos su apariencia, para que aparezca la tecla de bind:
                       destino.keycode = keycode;
                       destino.keyBind = MiscData.keycodeNames.get(keycode);
                       setApariencia(destino, destino.apariencia);
                   }
                   return true;
                }
            });
            final Image rebindButtonOff = new Image (Recursos.rebindButtonOn);
            this.addActor(rebindButtonOff);
            rebindButtonOff.setPosition(-rebindButtonOff.getWidth()-2, 0);
            rebindButtonOff.addListener(new InputListener() 
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {   //Switch para activar y desactivar el rebindeo de Skills
                    rebindearSkills = false;
                    BarraSpells.this.getStage().setKeyboardFocus(null);
                    rebindButtonOff.toBack();
                    return true;
                }
            });
            final Image rebindButtonOn = new Image (Recursos.rebindButtonOff);
            this.addActor(rebindButtonOn);
            rebindButtonOn.setPosition(-rebindButtonOn.getWidth()-2, 0);
            rebindButtonOn.addListener(new InputListener() 
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {   //Switch para activar y desactivar el rebindeo de Skills
                    rebindearSkills = true;
                    rebindButtonOn.toBack();
                    return true;
                }
            });
            
            //Codigo DRAG AND DROP:
            dad.addSource(new Source(destino.apariencia) 
            {
                @Override public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) 
                {
                    if (destino.spellID >= 0)
                    {   //Solo creamos un objeto de Payload si hay un spell en la destino, es decir si spellID >= 0
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        //Definimos el actor para cuando arrastramos el icono                        
                        payload.setDragActor(setApariencia(destino));
                        //Copiamos el objeto original para poder acceder a sus datos cuando aterricen en destino
                        payload.setObject(destino);
                        return payload;
                    } else return null;
                }
            });
            
            dad.addTarget(new Target(destino.apariencia) 
            {
                @Override public boolean drag(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                { return true; }
                
                @Override public void reset (Source source, DragAndDrop.Payload payload) 
                { super.reset(source, payload); }
                
                @Override public void drop(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                {   //Para que sea mas facil de manipular salvamos los datos del objeto origen en la variable origen
                    Casilla origen = ((Casilla)payload.getObject());
                    int destinoSpellID = destino.spellID;
                    //Intercambiamos los Spell IDs de destino y Origen
                    destino.spellID = origen.spellID;
                    origen.spellID = destinoSpellID;
                    //Modificamos el aspecto de ambos iconos
                    setApariencia(destino, destino.apariencia);
                    setApariencia(origen, origen.apariencia);  
                }
            });
        }
        this.setWidth(numColumnas*(anchoSlot+2));
        this.setHeight(numFilas*(altoSlot+2));
    }
    
    public void setSpell (int slot, Spell spell)
    {   //Cogemos la destino correspondiente a ese SLOT:
        Casilla casilla = barra.get(slot);
        //SpellID, salvamos el ID del spell en la destino:
        casilla.spellID = spell.getId();
        //Apariencia, generamos su nueva Aparariencia segun su spell ID:
        setApariencia(casilla, casilla.apariencia);   
    }
    
    public void setApariencia (Casilla casilla, Group group)
    {   //Genera la apariencia de la destino segun sus datos:
        group.clearChildren();
        if (casilla.spellID <0) { Image slotvacio = new Image (Recursos.casillero); slotvacio.setColor(0,0,0,0.1f);group.addActor(slotvacio); }
        else { group.addActor(new Image (Mundo.listaDeSpells.get(casilla.spellID).getIcono())); }
        if (casilla.spellID == Mundo.player.getSpellSeleccionado()) group.addActor(new Image(Recursos.spellSeleccionado));
        if (casilla.keyBind != null) Texto.printTexto(String.valueOf(casilla.keyBind), Recursos.font14, Color.ORANGE, Color.BLACK, 0, 20, Align.left, Align.bottom, 2, group);
    }
    
    public Group setApariencia (Casilla casilla)
    {
        Group group = new Group();
        setApariencia (casilla, group);
        return group;
    }
    
    public void actualizarApariencia (int spellID)
    {
        for (int i=0; i<barra.size;i++)
        {   if (barra.get(i).spellID == spellID) setApariencia(barra.get(i),barra.get(i).apariencia); }
    }
}
