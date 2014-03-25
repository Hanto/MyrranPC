package Skill.Skill.TiposSkill;
// @author Ivan Delgado Huerta

import Skill.Skill.TipoSkill;
import static Constantes.Skills.SkillsData.*;
import static Constantes.Skills.TipoSkillsData.*;
import Graficos.Pixie;
import Mobiles.Mobs.Personaje;
import Skill.SkillRecursos;
import Skill.Skill.Skill;
import Skill.SkillStat;
import Skill.SkillStat.SkillPixie;
import com.badlogic.gdx.math.Vector2;

public class Proyectil extends TipoSkill
{
    public Proyectil (String id)        { super(id); }
    public Proyectil ()                 { }
    
    @Override public void inicializarSkillStats() 
    {   //Siempre que se construya una objeto de tipo Firebolt se inicializan los Stats base que usara:
        //Creamos un array con el tamaño del numero de stats que vamos a introducir:
        skillStats = new SkillStat [4]; 
        SkillStat stat = new SkillStat  (BOLT_CastingTime_String, BOLT_CastingTime_Valor); skillStats[0]=stat;  //CAST
        stat = new SkillStat            (BOLT_Daño_String, BOLT_Daño_Valor); skillStats[1]=stat;                //DAÑO
        stat = new SkillStat            (BOLT_Velocidad_String, BOLT_Velocidad_Valor); skillStats[2]=stat;      //VELOCIDAD
        stat = new SkillStat            (BOLT_Duracion_String, BOLT_Duracion_Valor); skillStats[3]=stat;        //DURACION
    }
    
    @Override public void inicializarSkillPixies()
    {   //Creamos un array con el tamaño del numero de Pixies que vamos a introducir:
        //En cada slot del Array introducimos todas los pixies posibles para ese tipo de animacion
        skilllPixies = new SkillPixie[2];
        SkillStat.SkillPixie spixie = new SkillStat.SkillPixie();
        spixie.tipoAnimacion = "Casteos Bolt";
        spixie.pixieArray.add(SkillRecursos.listaDeCasteos.get(FIREBOLT_Pixie_Casteo));
        spixie.pixieArray.add(SkillRecursos.listaDeCasteos.get(FROSTBOLT_Pixie_Casteo));
        skilllPixies[0]=spixie;
        
        spixie = new SkillPixie(); 
        spixie.tipoAnimacion = "Proyectiles Bolt";
        spixie.pixieArray.add(SkillRecursos.listaDeSpells.get(FIREBOLT_Pixie_Proyectil));
        spixie.pixieArray.add(SkillRecursos.listaDeSpells.get(FROSTBOLT_Pixie_Proyectil));
        skilllPixies[1]=spixie;
    }
    
    @Override public void ejecutarCasteo (Skill skill, Personaje caster, float destinoX, float destinoY)
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
        Pixie casteo = getPixie(0, skill.pixieSeleccionado()[0]);
        Pixie proyectil = getPixie(1, skill.pixieSeleccionado()[1]);
        //Ajustamos la posicion de origen y destino para que que esa posicion coincida con el centro de gravedad del pixie:
        ajustarCoordenadasPorProyectil(proyectil, origen, destino);
        //Calculamos la direccion del proyectil en funcion de todas las coordenas previamente ajustadas:
        direccion = calcularDireccion(origen, destino);
        
        //Creamos la entidad proyectil e inicializamos sus datos:
        Mobiles.Mobs.Proyectil pepo = new Mobiles.Mobs.Proyectil(proyectil);
        pepo.setPosition(origen.x, origen.y);
        pepo.setDireccion(direccion);
        pepo.setOwner(caster);
        pepo.setDaño(skill.skillStats()[1].valorBase);
        pepo.setVelocidad(skill.skillStats()[2].valorBase);
        pepo.setDuracionMaxima(skill.skillStats()[3].valorBase);
        //ponemos el pepo en el mundo, añadiendolo a la lista de entedidades y al Stage
        pepo.crear();
        //Animamos un pixie con el grafico del casteo correspondiente a este Spell
        animarCasteo(casteo, caster);
    }
}
