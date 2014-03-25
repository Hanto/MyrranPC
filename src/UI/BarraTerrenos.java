package UI;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Constantes.Skills.SpellsData;
import Resources.Recursos;
import Graficos.Texto;
import Main.Mundo;
import Pantallas.PantallaJuego;
import Save.SaveData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class BarraTerrenos extends Group
{
    private Table tablaTerrenos;
    private ScrollPane scrollpane;
    
    private Array<CasillaTerreno> barra = new Array<>();
    private Texto [] botonCapas = new Texto [MiscData.MAPA_Max_Capas_Terreno];
    private Texto botonSave;
    private Image botonBorrarTerreno;
    
    private int altoScrollPane=MiscData.TILESIZE*2*10;
    private static int numColumnas = 2;
    
    public static class CasillaTerreno
    {
        public Group apariencia = new Group();
        public int terrenoID;
    }
    
    public BarraTerrenos ()
    {
        tablaTerrenos = new Table().top().left();
        this.setY(Gdx.graphics.getHeight()/2-altoScrollPane/2);
        
        for (int i=0; i<Mundo.listaDeTerrenos.size;i++)
        {         
            final CasillaTerreno casilla = new CasillaTerreno();
            TextureRegion terreno = new TextureRegion (Mundo.listaDeTerrenos.get(i).getTextura(), 0, MiscData.TILESIZE*1,MiscData.TILESIZE*2,MiscData.TILESIZE*2);
            casilla.apariencia.addActor(new Image (terreno));
            casilla.terrenoID = i;
            //Guardamos todos los datos de cada elemento computado en el array barra, para que si tenemos que hacer cambios, usar estos datos precalculados
            barra.add(casilla);
            //Añadimos la apariencia de cada elemento en la tabla
            tablaTerrenos.add(casilla.apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            //Añadimos, en la tabla, una nueva fila cada numColumnas-elementos
            if ((i+1)%numColumnas == 0) tablaTerrenos.row();
            //A cada icono le activamos un clicklistener, que seleccionara ese tipo de terreno como el activo
            casilla.apariencia.addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
                {
                    Mundo.player.setTerrenoSeleccionado(casilla.terrenoID);
                    return true;
                }
            });   
        }
        scrollpane = new ScrollPane(tablaTerrenos);
        scrollpane.setBounds(0, 0, MiscData.TILESIZE*2*numColumnas, altoScrollPane);
        this.addActor(scrollpane);
        
        scrollpane.addListener(new InputListener() 
        {   //para que se puede hacer scroll de la barra de terreno con solo pasar el raton por encima:
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            { scrollpane.getStage().setScrollFocus(scrollpane); }
            
            //para que no haga scroll cuando el raton este fuera de la ventana de terrenos
            @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
            { scrollpane.getStage().setScrollFocus(null); } 
            
            //Añdimos un listener para el scroll, para configurar su velocidad, hay que parar su propagacion con event.stop() para que no salte el que esta programado de base
            @Override public boolean scrolled(InputEvent event, float x, float y, int amount) 
            { scrollpane.setScrollY(scrollpane.getScrollY()+MiscData.TILESIZE*3*amount); event.stop(); return true; }
        });
        
        //Selector de CAPAS:
        for (int i=0; i<MiscData.MAPA_Max_Capas_Terreno; i++)
        {   
            final int numCapa = i;
            botonCapas[i] = new Texto("Capa "+numCapa, Recursos.font14, Color.ORANGE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
            botonCapas[i].setPosition(4, altoScrollPane+48+17*numCapa);
            this.addActor(botonCapas[i]);
            
            botonCapas[i].addListener(new InputListener() 
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {   
                    Mundo.player.setCapaTerrenoSelecionada(numCapa);
                    for (int j=0; j<botonCapas.length; j++) 
                    {   botonCapas[j].setColorNormal(Color.ORANGE); }
                    botonCapas[numCapa].setColorNormal(Color.GREEN); 
                    return true;
                }
            });
        }
        botonCapas[0].setColorNormal(Color.GREEN);
        
        //Boton SALVAR mapa:
        botonSave = new Texto("Save", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        botonSave.setPosition(4, altoScrollPane+48+MiscData.MAPA_Max_Capas_Terreno*17);
        this.addActor(botonSave);
        
        botonSave.addListener(new InputListener() 
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   SaveData.saveMap(); return true; }
        });
        
        //Boton BORRAR:
        botonBorrarTerreno = new Image(Recursos.botonBorrarTerreno);
        botonBorrarTerreno.setPosition(0, altoScrollPane);
        this.addActor(botonBorrarTerreno);
        
        botonBorrarTerreno.addListener(new InputListener() 
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   Mundo.player.setTerrenoSeleccionado(-1); return true; }
        });
    }
    
    //Para reorganizar la barra en mas columnas:
    public void setNumColumnas(int columnas)
    {
        tablaTerrenos.clear();
        numColumnas = columnas;
                
        for (int i=0; i<barra.size;i++)
        {  
            tablaTerrenos.add(barra.get(i).apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            if ((i+1)%columnas == 0) tablaTerrenos.row();
            scrollpane.setBounds(scrollpane.getX(), scrollpane.getY(), MiscData.TILESIZE*2*columnas, altoScrollPane);
        }
    }
    
    //Por si añadimos mas terrenos y hay que recomputar y actualizar todo:
    public void actualizarBarraTerrenos ()
    {
        tablaTerrenos.clear();
        barra.clear();
        
        for (int i=0; i<Mundo.listaDeTerrenos.size;i++)
        {          
            final CasillaTerreno casilla = new CasillaTerreno();
            TextureRegion terreno = new TextureRegion (Mundo.listaDeTerrenos.get(i).getTextura(), 0, MiscData.TILESIZE*1,MiscData.TILESIZE*2,MiscData.TILESIZE*2);
            casilla.apariencia.addActor(new Image (terreno));
            casilla.terrenoID = i;
            
            barra.add(casilla);
            tablaTerrenos.add(casilla.apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            
            if ((i+1)%numColumnas == 0) tablaTerrenos.row();

            casilla.apariencia.addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
                {
                    Mundo.player.setTerrenoSeleccionado(casilla.terrenoID);
                    return true;
                }
            });
        }
    }
    
    //Oculta o muestra la barra de Terrenos
    public static void mostrarOcultarBarraTerreno ()
    {
        if (Mundo.player.getSpellSeleccionado() == SpellsData.TERRAFORMAR_ID)
        {
            if (Mundo.player.mostrarBarraTerrenos == false)
            {
                Mundo.barraTerrenos.clearActions();
                if (Mundo.barraTerrenos.getStage() == null)
                {
                    Mundo.barraTerrenos.setX(-MiscData.TILESIZE*2*numColumnas);
                    PantallaJuego.stageUI.addActor(Mundo.barraTerrenos);
                }
                Mundo.barraTerrenos.addAction(Actions.moveTo(5, Mundo.barraTerrenos.getY(), 0.5f, Interpolation.sine));
                Mundo.player.mostrarBarraTerrenos = true;
            }
        }
        else
        {
            if (Mundo.player.mostrarBarraTerrenos == true)
            {
                Mundo.barraTerrenos.clearActions();
                Mundo.barraTerrenos.addAction(Actions.sequence(Actions.moveTo(-MiscData.TILESIZE*2*numColumnas, Mundo.barraTerrenos.getY(), 0.5f, Interpolation.sine), Actions.removeActor()));
                Mundo.player.mostrarBarraTerrenos = false;
            }    
        }
    }
}
