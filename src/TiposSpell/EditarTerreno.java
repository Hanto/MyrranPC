package TiposSpell;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import static Constantes.SpellData.*;
import Geo.Celda;
import Geo.Mapa;
import Interface.BarraTerrenos;
import Main.Mundo;
import Mobiles.Personaje;
import Pantallas.AbstractPantalla;
import Pantallas.PantallaJuego;
import Skills.Spell;
import Skills.SpellStat;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class EditarTerreno extends AbstractTipoSpell
{
    public EditarTerreno ()
    {
        spellStats = new SpellStat [1]; 
        SpellStat stat = new SpellStat  (EDITARTERRENO_CastingTime_String, EDITARTERRENO_CastingTime_Valor); spellStats[0]=stat;//CAST
    }
    
    @Override
    public void ejecutarCasteo(Spell spell, Personaje caster, float targetX, float targetY) 
    {
        convertirCoordenadasDestino(caster, targetX, targetY);
        convertirCoordenadasANumeroDeTile();
        
        int x = (int)destinoX;
        int y = (int)destinoY;
        
        int numCapa = caster.getCapaTerrenoSeleccionada();
        
        Celda celda = new Celda ();
        for (int i=0; i<Mundo.mapa[x][y].getTerreno().length;i++)
        { celda.getTerreno()[i] = Mundo.mapa[x][y].getTerreno()[i];}
        
        Mundo.mapa[x][y] = celda;
        celda.getTerreno()[numCapa]=Mundo.player.getTerrenoSeleccionado();
        
        Mapa.crearTile(x, y, numCapa);
        Mapa.crearTile(x+1, y, numCapa);
        Mapa.crearTile(x-1, y, numCapa);
        Mapa.crearTile(x, y+1, numCapa);
        Mapa.crearTile(x, y-1, numCapa);
        Mapa.crearTile(x+1, y+1, numCapa);
        Mapa.crearTile(x-1, y-1, numCapa);
        Mapa.crearTile(x-1, y+1, numCapa);
        Mapa.crearTile(x+1, y-1, numCapa);
    }
}
