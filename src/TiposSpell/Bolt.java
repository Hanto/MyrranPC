package TiposSpell;
import static Constantes.SpellData.*;
import Graficos.Recursos;
import Graficos.Pixie;
import Mobiles.Personaje;
import Mobiles.Proyectil;
import Skills.Spell;
import Skills.SpellStat;
import Skills.SpellStat.SpellPixie;
import com.badlogic.gdx.math.Vector2;
//* @author Ivan Delgado Huerta

public class Bolt extends AbstractTipoSpell
{   
    public Bolt ()
    {   //CONSTRUCTOR: Siempre que se construya una objeto de tipo Firebolt se inicializan los Stats base que usara:
        //Creamos un array con el tamaño del numero de stats que vamos a introducir:
        spellStats = new SpellStat [4]; 
        SpellStat stat = new SpellStat  (BOLT_CastingTime_String, BOLT_CastingTime_Valor); spellStats[0]=stat;  //CAST
        stat = new SpellStat            (BOLT_Daño_String, BOLT_Daño_Valor); spellStats[1]=stat;                //DAÑO
        stat = new SpellStat            (BOLT_Velocidad_String, BOLT_Velocidad_Valor); spellStats[2]=stat;      //VELOCIDAD
        stat = new SpellStat            (BOLT_Duracion_String, BOLT_Duracion_Valor); spellStats[3]=stat;        //DURACION
        
        //Creamos un array con el tamaño del numero de Pixies que vamos a introducir:
        //En cada slot del Array introducimos todas los pixies posibles para ese tipo de animacion
        spellPixies = new SpellPixie[2];
        SpellPixie spixie = new SpellPixie();
        spixie.tipoAnimacion = "Casteos Bolt";
        spixie.pixieArray.add(Recursos.listaDeCasteos.get(FIREBOLT_Pixie_Casteo));
        spixie.pixieArray.add(Recursos.listaDeCasteos.get(FROSTBOLT_Pixie_Casteo));
        spellPixies[0]=spixie;
        
        spixie = new SpellPixie(); 
        spixie.tipoAnimacion = "Proyectiles Bolt";
        spixie.pixieArray.add(Recursos.listaDeSpells.get(FIREBOLT_Pixie_Proyectil));
        spixie.pixieArray.add(Recursos.listaDeSpells.get(FROSTBOLT_Pixie_Proyectil));
        spellPixies[1]=spixie;
    }
    
    //Metodo que ejecuta el casteo del Spell
    @Override public void ejecutarCasteo(Spell spell, Personaje caster, float destinoX, float destinoY) 
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
        Pixie casteo = getPixie(0, spell.pixieSeleccionado()[0]);
        Pixie proyectil = getPixie(1, spell.pixieSeleccionado()[1]);
        //Ajustamos la posicion de origen y destino para que que esa posicion coincida con el centro de gravedad del pixie:
        ajustarCoordenadasPorProyectil(proyectil, origen, destino);
        //Calculamos la direccion del proyectil en funcion de todas las coordenas previamente ajustadas:
        direccion = calcularDireccion(origen, destino);
        
        //Creamos la entidad proyectil e inicializamos sus datos:
        Proyectil pepo = new Proyectil(proyectil);
        pepo.setPosition(origen.x, origen.y);
        pepo.setDireccion(direccion);
        pepo.setOwner(caster);
        pepo.setDaño(spell.spellStats()[1].valorBase);
        pepo.setVelocidad(spell.spellStats()[2].valorBase);
        pepo.setDuracionMaxima(spell.spellStats()[3].valorBase);
        //ponemos el pepo en el mundo, añadiendolo a la lista de entedidades y al Stage
        pepo.crear();
        //Animamos un pixie con el grafico del casteo correspondiente a este Spell
        animarCasteo(casteo, caster);
    }       
}