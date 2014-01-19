package TiposSpell;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import static Constantes.SpellData.*;
import Geo.Celda;
import Geo.Mapa;
import Main.Mundo;
import Mobiles.Personaje;
import Skills.Spell;
import Skills.SpellStat;
import com.badlogic.gdx.math.Vector2;

public class EditarTerreno extends AbstractTipoSpell
{
    public EditarTerreno ()
    {
        spellStats = new SpellStat [1]; 
        SpellStat stat = new SpellStat  (EDITARTERRENO_CastingTime_String, EDITARTERRENO_CastingTime_Valor); spellStats[0]=stat;//CAST
    }
    
    @Override public void ejecutarCasteo(Spell spell, Personaje caster, float targetX, float targetY) 
    {
        Vector2 destino = convertirCoordenadasDestino(caster, targetX, targetY);
        destino = convertirCoordenadasANumeroDeTile(destino);
        
        int x = (int)destino.x;
        int y = (int)destino.y;
        
        if (x<0 || y<0 || x>MiscData.MAPA_Max_X || y>MiscData.MAPA_Max_Y) { return; }
        
        int numCapa = caster.getCapaTerrenoSeleccionada();
        
        Celda celda = new Celda (Mundo.mapa[x][y]);
        Mundo.mapa[x][y] = celda;
        
        celda.getTerrenoID()[numCapa]= Mundo.player.getTerrenoSeleccionado();
        
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
