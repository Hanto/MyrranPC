package TiposSpell;
import Constantes.MiscData;
import Graficos.Pixie;
import Mobiles.PC;
import Mobiles.Personaje;
import Mobiles.Player;
import Pantallas.PantallaJuego;
import Skills.Spell.TipoSpell;
import Skills.SpellStat;
import Skills.SpellStat.SpellPixie;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//@author Ivan Delgado Huerta
public abstract class AbstractTipoSpell implements TipoSpell
{
    protected float origenX;
    protected float origenY;
    protected float destinoX;
    protected float destinoY;
    protected double direccion;
    protected Personaje caster;
    
    protected SpellStat [] spellStats;
    protected SpellPixie [] spellPixies;
    //SET-GET:
    @Override public SpellStat [] getSpellStats()                   { return spellStats; }
    @Override public SpellPixie [] getSpellPixies()                 { return spellPixies; }
    
    public Pixie getPixie (int numAnimacion, int numPixie)
    {   //De entre todas las animaciones que tiene este spell selecciona el grupo que necesitamos y extrae el pixie que queremos 
        return spellPixies[numAnimacion].pixieArray.get(numPixie); 
    }
    
    protected void convertirCoordenadasDestino (Personaje caster, float destinoX, float destinoY)
    {   //Convertimos las coordenadas de pantalla del click en pantalla en coordenadas de mundo para comparar posiciones con el caster
        //y asi deducir la direccion de salida del pepo, en caso de ser un NPC no hace falta puesto que no estos no clickan en pantalla:
        if (caster instanceof Player)
        {   //Si es un player las coordenadas son coordenadas de pantalla, y hay que pasarlas a coordendas de mundo
            Vector3 destino = new Vector3(destinoX, destinoY, 0);
            PantallaJuego.camara.unproject(destino);
            this.destinoX = destino.x;
            this.destinoY = destino.y;
        }
        else
        {   //Si es un mob las coordenadas son coordenadas de mundo, y no hay que convertirlas. Los npc no clickan en pantalla.
            this.destinoX = destinoX;
            this.destinoY = destinoY;
        }
    }
    
    protected void convertirCoordenadasOrigen (Personaje caster)
    {   //Establecemos el punto de salida del pepo, suele ser el centro de gravedadad del Pixie (altura/2) (anchura/2):
        if (caster instanceof PC)
        {   //Personaje Jugador:
            PC pc = ((Player)caster);
            {
                origenX = pc.getX()+pc.getPixiePC().getWidth()/2; 
                origenY = pc.getY()+pc.getPixiePC().getHeight()/2;
            }
        } else { origenX = caster.getX(); origenY = caster.getY(); } //NPC
    }
    
    protected void ajustarCoordenadasPorProyectil(Pixie proyectil)
    {   //Ajustamos la posicion de origen y destino para que que esa posicion coincida con el centro de gravedad del pixie:
        this.origenX = this.origenX- proyectil.getWidth()/2;
        this.origenY = this.origenY- proyectil.getHeight()/2;
        this.destinoY = this.destinoY- proyectil.getHeight()/2;
        this.destinoX = this.destinoX- proyectil.getWidth()/2;
    }
    
    protected void convertirCoordenadasANumeroDeTile ()
    {
        destinoX = (int)destinoX/MiscData.TILESIZE;
        destinoY = (int)destinoY/MiscData.TILESIZE;
    }
    protected void calcularDireccion ()
    { direccion = Math.atan2(this.destinoY-origenY, this.destinoX-origenX); }
    
    protected void animarCasteo (Pixie pixie)
    {
        Pixie casteo = new Pixie (pixie);
        casteo.setAnimarYEliminarActor(true);
        caster.getActor().addActor(casteo);
    }
}
