package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Constantes.Skills.TipoSpellsData;
import Graficos.Pixie;
import Interfaces.Caster;
import Skill.SkillRecursos;
import Skill.SkillStat;
import Skill.SkillStat.SkillPixie;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.math.Vector2;

import static Constantes.Skills.SpellsData.*;
import static Constantes.Skills.TipoSpellsData.*;

public class Bolt extends TipoSpell
{
    public static final int STAT_Daño = 1;
    public static final int STAT_Velocidad = 2;
    public static final int STAT_Duracion = 3;
    
    public static final int PIXIE_Casteos = 0;
    public static final int PIXIE_Proyectiles = 1;
    
    public Bolt (String id)        { super(id); }
    public Bolt ()                 { }
    
    @Override public void inicializarSkillStats()
    {
        setIcono(TipoSpellsData.BOLT_Icono);
        //Siempre que se construya una objeto de tipo Firebolt se inicializan los Stats base que usara:
        //Creamos un array con el tamaño del numero de stats que vamos a introducir:
        skillStats = new SkillStat [4]; SkillStat stat;
        stat = new SkillStat  (BOLT_CastingTime_String, BOLT_CastingTime_Valor); skillStats[STAT_Cast]=stat;            //CAST
        stat = new SkillStat            (BOLT_Daño_String, BOLT_Daño_Valor); skillStats[STAT_Daño]=stat;                //DAÑO
        stat = new SkillStat            (BOLT_Velocidad_String, BOLT_Velocidad_Valor); skillStats[STAT_Velocidad]=stat; //VELOCIDAD
        stat = new SkillStat            (BOLT_Duracion_String, BOLT_Duracion_Valor); skillStats[STAT_Duracion]=stat;    //DURACION
    }
    
    @Override public void inicializarSkillPixies()
    {   //Creamos un array con el tamaño del numero de Pixies que vamos a introducir:
        //En cada slot del Array introducimos todas los pixies posibles para ese tipo de animacion
        skilllPixies = new SkillPixie[2];
        SkillStat.SkillPixie spixie = new SkillStat.SkillPixie();
        spixie.tipoAnimacion = "Casteos Bolt";
        spixie.pixieArray.add(SkillRecursos.listaDePixieCasteos.get(FIREBOLT_Pixie_Casteo));
        spixie.pixieArray.add(SkillRecursos.listaDePixieCasteos.get(FROSTBOLT_Pixie_Casteo));
        skilllPixies[PIXIE_Casteos]=spixie;
        
        spixie = new SkillPixie(); 
        spixie.tipoAnimacion = "Proyectiles Bolt";
        spixie.pixieArray.add(SkillRecursos.listaDePixieProyectiles.get(FIREBOLT_Pixie_Proyectil));
        spixie.pixieArray.add(SkillRecursos.listaDePixieProyectiles.get(FROSTBOLT_Pixie_Proyectil));
        skilllPixies[PIXIE_Proyectiles]=spixie;
    }
    
    @Override public void ejecutarCasteo (Spell spell, Caster caster, float destinoX, float destinoY)
    {   
        Vector2 destino, origen;
        double direccion;
        //Convertimos las coordenadas de pantalla del click en pantalla en coordenadas de mundo para comparar posiciones con el caster
        //y asi deducir la direccion de salida del pepo, en caso de ser un NPC no hace falta puesto que no estos no clickan en pantalla:
        destino = convertirCoordenadasDestino(caster, destinoX, destinoY);
        //Establecemos el punto de salida del pepo, suele ser el centro de gravedadad del Pixie (altura/2) (anchura/2):
        origen = convertirCoordenadasOrigen(caster);
        //Cargamos los pixies de las 2 animaciomes que necesita el spell, cada animacion tiene muchos pixies disponibles donde elegir
        //de indicarnos que pixie exacto usa este spell se encarga el array pixieSeleccionado:
        Pixie casteo = getPixie(PIXIE_Casteos, spell.pixieSeleccionado()[PIXIE_Casteos]);
        Pixie proyectil = getPixie(PIXIE_Proyectiles, spell.pixieSeleccionado()[PIXIE_Proyectiles]);
        //Ajustamos la posicion de origen y destino para que que esa posicion coincida con el centro de gravedad del pixie:
        ajustarCoordenadasPorProyectil(proyectil, origen, destino);
        //Calculamos la direccion del proyectil en funcion de todas las coordenas previamente ajustadas:
        direccion = calcularDireccion(origen, destino);
        
        //Creamos la entidad proyectil e inicializamos sus datos:
        Actores.Mobs.Proyectil pepo = new Actores.Mobs.Proyectil(proyectil);
        pepo.setSpell(spell);
        pepo.setPosition(origen.x, origen.y);
        pepo.setDireccion(direccion);
        pepo.setOwner(caster);
        pepo.setVelocidad(spell.skillStats()[STAT_Velocidad].valorBase);
        pepo.setDuracionMaxima(spell.skillStats()[STAT_Duracion].valorBase);
        //ponemos el pepo en el mundo, añadiendolo a la lista de entedidades y al Stage
        pepo.crear();
        //Animamos un pixie con el grafico del casteo correspondiente a este Spell
        animarCasteo(casteo, caster);
    }
}
