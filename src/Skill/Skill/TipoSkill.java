package Skill.Skill;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Pixie;
import Mobiles.Mobs.Personajes.PC;
import Mobiles.Mobs.Personaje;
import Mobiles.Mobs.Personajes.PCs.Player;
import Pantallas.PantallaJuego;
import Skill.SkillInterface.TipoSkillInterface;
import Skill.SkillStat;
import Skill.SkillStat.SkillPixie;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.UUID;

public abstract class TipoSkill implements TipoSkillInterface 
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TextureRegion icono;
    
    protected SkillStat [] skillStats;
    protected SkillPixie [] skilllPixies;
    
    //SET
    public void setId (String id)                       { this.id = id; }
    public void setNombre (String nombre)               { this.nombre = nombre; }
    public void setDescripcion (String descripcion)     { this.descripcion = descripcion; }
    public void setIcono (TextureRegion texture)        { icono = texture; }
    //GET
    public String getID()                               { return id; }
    public String getNombre()                           { return nombre; }
    public String getDescripcion ()                     { return descripcion; }
    public SkillStat [] skillStat ()                    { return skillStats; }
    public SkillPixie [] skilllPixie ()                 { return skilllPixies; }
    public TextureRegion getIcono ()                    { return icono; }
    
    //CONSTRUCTOR:
    public TipoSkill (String id)
    {   //Si especificamos un ID, lo usamos como ID del tipo
        this.id = id;
        inicializarSkillStats(); 
        inicializarSkillPixies();
    }
    
    public TipoSkill ()
    {   //Si no especificamos ID generamos un ID unico con la libreria UUID
        UUID idUUID = UUID.randomUUID();
        id = idUUID.toString();
        inicializarSkillStats(); 
        inicializarSkillPixies();
    }
    
    //De entre todas las animaciones que tiene este spell selecciona el grupo que necesitamos y extrae el pixie que queremos 
    public Pixie getPixie (int numAnimacion, int numPixie)
    {   return skilllPixies[numAnimacion].pixieArray.get(numPixie);}
    
    //Convertimos las coordenadas de pantalla del click en pantalla en coordenadas de mundo para comparar posiciones con el caster
    //y asi deducir la direccion de salida del pepo, en caso de ser un NPC no hace falta puesto que no estos no clickan en pantalla:
    protected Vector2 convertirCoordenadasDestino (Personaje caster, float destinoX, float destinoY)
    {   
        if (caster instanceof Player)
        {   //Si es un player las coordenadas son coordenadas de pantalla, y hay que pasarlas a coordendas de mundo
            Vector3 destino = new Vector3(destinoX, destinoY, 0);
            PantallaJuego.camara.unproject(destino);
            return new Vector2(destino.x, destino.y);
        }
        else
        {   //Si es un mob las coordenadas son coordenadas de mundo, y no hay que convertirlas. Los npc no clickan en pantalla.
            return new Vector2(destinoX, destinoY);
        }
    }
    
    protected Vector2 convertirCoordenadasOrigen (Personaje caster)
    {   //Establecemos el punto de salida del pepo, suele ser el centro de gravedadad del Pixie (altura/2) (anchura/2):
        if (caster instanceof PC)
        {   //Personaje Jugador:
            PC pc = ((Player)caster);
            return new Vector2 (pc.getX()+pc.getPixiePC().getWidth()/2, pc.getY()+pc.getPixiePC().getHeight()/2);
        } 
        else //NPC  
        {   return new Vector2(caster.getX(), caster.getY());}
    }
    
    //Ajustamos la posicion de origen y destino para que que esa posicion coincida con el centro de gravedad del pixie:
    protected void ajustarCoordenadasPorProyectil(Pixie proyectil, Vector2 origen, Vector2 destino)
    {   
        origen.x = origen.x - proyectil.getWidth()/2;
        origen.y = origen.y - proyectil.getHeight()/2;
        destino.x = destino.x - proyectil.getWidth()/2;
        destino.y = destino.y - proyectil.getHeight()/2;
    }
    
    //dadas unas coordenadas de mundo, las transforma a su numero de tile, empezando por el cero:
    protected Vector2 convertirCoordenadasANumeroDeTile (Vector2 destino)
    {   return new Vector2((int)destino.x/MiscData.TILESIZE,(int)destino.y/MiscData.TILESIZE); }
    
    //Calcula la direccion que toma la recta que une el punto origen con el destino:
    protected double calcularDireccion (Vector2 origen, Vector2 destino)
    {   return (Math.atan2(destino.y-origen.y, destino.x-origen.x)); }
    
    protected void animarCasteo (Pixie pixie, Personaje caster)
    {
        Pixie casteo = new Pixie (pixie);
        caster.getActor().addActor(casteo);
    }
}
