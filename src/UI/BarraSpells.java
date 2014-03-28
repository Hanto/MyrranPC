package UI;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Texto;
import Main.Mundo;
import Resources.Recursos;
import Skill.SkillBook;
import Skill.Spell.Spell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    private Group tooltip;



    public void setRebindearSkills (boolean b)              { rebindearSkills = b; }
    public boolean getRebindearSkills ()                    { return rebindearSkills; }
    
    public static class Casilla
    {
        public Group apariencia = new Group();              //Icono que se muestra en pantalla para esa casilla
        public String keyBind;                              //Tecla bindeada a esa casilla
        public int keycode;                                 //Keycode que corresponde a ese bind
        public String spellID;                              //spellID que corresponde a esa casilla
    }
    
    public BarraSpells (int numFilas, int numColumnas)
    {
        dad.setDragTime(0);
        this.numFilas = numFilas; this.numColumnas = numColumnas;
        
        for (int i=0; i<numFilas*numColumnas; i++)
        {
            final Casilla casilla = new Casilla();
            
            Image islot = new Image (Recursos.casillero);
            anchoSlot = (int)islot.getWidth();
            altoSlot = (int)islot.getHeight();
            
            casilla.spellID = "";
            if (i<9){ casilla.keyBind = String.valueOf(i+1); casilla.keycode = i+8; }
            islot.setColor(0, 0, 0, 0.1f);
            casilla.apariencia.addActor(islot);
            casilla.apariencia.setBounds(0,0, islot.getWidth(), islot.getHeight());
            if (casilla.keyBind != null) Texto.printTexto(String.valueOf(casilla.keyBind), Recursos.font14, Color.ORANGE, Color.BLACK, 0, 20, Align.left, Align.bottom, 2, casilla.apariencia);
            
            int columna = (i)/numColumnas;
            int fila = (i)%numColumnas;
            
            casilla.apariencia.setPosition(fila*(anchoSlot+2), columna*(altoSlot+2)); //2 es el margen entre casillas
                        
            this.addActor(casilla.apariencia);
            barra.add(casilla);
            
            //Codigo para Rebindear Teclas
            casilla.apariencia.addListener(new InputListener()
            {   //Hacemos que el stage acepte eventos de teclado del icono sobre el que el raton ha entrado
                @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
                {
                    if (rebindearSkills) casilla.apariencia.getStage().setKeyboardFocus(casilla.apariencia);
                    if (casilla.spellID.length() > 0 && pointer <0)
                    {   //por algun motivo absurdo si haces click reprocesa el evento de enter, si hay mouseclicks pointer >=0
                        if (!dad.isDragging())
                        {
                            if (tooltip != null)    { removeActor(tooltip); }   //Antes de añadir, borramos el que ya hay
                            tooltip = SpellTooltip.tooltip(SkillBook.listaDeSpells.get(casilla.spellID));
                            tooltip.setPosition(casilla.apariencia.getX(), casilla.apariencia.getY()+casilla.apariencia.getHeight()+4);
                            addActor(tooltip);
                        }
                    }
                }
                
                //Hacemos que deje de recibir eventos de teclado, puesto que el teclado ha salido
                @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
                {
                    if (rebindearSkills) casilla.apariencia.getStage().setKeyboardFocus(null);
                    if (casilla.spellID.length() > 0 && pointer <0)
                    {   //Por algun motivo absurdo al soltar cualquier boton del raton, lo interpreta como un exit, y el pointer es >=0
                        removeActor(tooltip);
                    }
                }
                
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
                       casilla.keycode = keycode;
                       casilla.keyBind = MiscData.keycodeNames.get(keycode);
                       setApariencia(casilla, casilla.apariencia);
                   }
                   return true;
                }

                //Con cualquier click de boton, hacemos desaparecer los Tooltips, si es el boton derecho crearemos una
                //ventana editable con los datos del spell para modificar los Talentos:
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    if (tooltip !=null) removeActor(tooltip);
                    return true;
                }

                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) { }
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
            dad.addSource(new Source(casilla.apariencia)
            {
                @Override public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) 
                {
                    if (casilla.spellID.length() > 0)
                    {   //Solo creamos un objeto de Payload si hay un spell en la casilla, es decir si spellID >= 0
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        //Definimos el actor para cuando arrastramos el icono                        
                        payload.setDragActor(setApariencia(casilla));
                        //Copiamos el objeto original para poder acceder a sus datos cuando aterricen en casilla
                        payload.setObject(casilla);
                        return payload;
                    } 
                    else return null;
                }
            });
            
            dad.addTarget(new Target(casilla.apariencia)
            {
                @Override public boolean drag(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                { return true; }
                
                @Override public void reset (Source source, DragAndDrop.Payload payload) 
                { super.reset(source, payload); }
                
                @Override public void drop(Source source, DragAndDrop.Payload payload, float x, float y, int pointer) 
                {   //Para que sea mas facil de manipular salvamos los datos del objeto origen en la variable origen
                    Casilla origen = ((Casilla)payload.getObject());
                    String destinoSpellID = casilla.spellID;
                    //Intercambiamos los Spell IDs de casilla y Origen
                    casilla.spellID = origen.spellID;
                    origen.spellID = destinoSpellID;
                    //Modificamos el aspecto de ambos iconos
                    setApariencia(casilla, casilla.apariencia);
                    setApariencia(origen, origen.apariencia);  
                }
            });
        }
        this.setWidth(numColumnas*(anchoSlot+2));
        this.setHeight(numFilas*(altoSlot+2));
    }
    
    public void setSkill (int slot, Spell skill)
    {   //Cogemos la destino correspondiente a ese SLOT:
        Casilla casilla = barra.get(slot);
        //SpellID, salvamos el ID del skill en la destino:
        casilla.spellID = skill.getId();
        //Apariencia, generamos su nueva Aparariencia segun su skill ID:
        setApariencia(casilla, casilla.apariencia);   
    }
    
    public void setApariencia (Casilla casilla, Group group)
    {   //Genera la apariencia de la destino segun sus datos:
        group.clearChildren();
        if (casilla.spellID.length() == 0) 
        { 
            Image slotvacio = new Image (Recursos.casillero); 
            slotvacio.setColor(0,0,0,0.1f);
            group.addActor(slotvacio); 
        }
        else { group.addActor(new Image (SkillBook.listaDeSpells.get(casilla.spellID).getIcono())); }
        if (casilla.spellID.equals(Mundo.player.getSpellSeleccionado())) group.addActor(new Image(Recursos.spellSeleccionado));
        if (casilla.keyBind != null) Texto.printTexto(String.valueOf(casilla.keyBind), Recursos.font14, Color.ORANGE, Color.BLACK, 0, 20, Align.left, Align.bottom, 2, group);
    }
    
    public Group setApariencia (Casilla casilla)
    {
        Group group = new Group();
        setApariencia (casilla, group);
        return group;
    }
    
    public void actualizarApariencia (String spellID)
    {
        for (int i=0; i<barra.size;i++)
        {   if (barra.get(i).spellID.equals(spellID)) setApariencia(barra.get(i),barra.get(i).apariencia); }
    }
}
