package TiposSpell;
// @author Ivan Delgado Huerta
import static Constantes.SpellData.*;
import Geo.Celda;
import Geo.Mapa;
import Main.Mundo;
import Mobiles.Personaje;
import Skills.Spell;
import Skills.SpellStat;

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
        
        Celda celda2 = new Celda();
        celda2.getTerreno()[0]=1;
        celda2.getTerreno()[1]=1;
        
        Mundo.mapa[x][y] = celda2;
        
        Mapa.crearTile(x, y, 1);
        Mapa.crearTile(x+1, y, 1);
        Mapa.crearTile(x-1, y, 1);
        Mapa.crearTile(x, y+1, 1);
        Mapa.crearTile(x, y-1, 1);
        Mapa.crearTile(x+1, y+1, 1);
        Mapa.crearTile(x-1, y-1, 1);
        Mapa.crearTile(x-1, y+1, 1);
        Mapa.crearTile(x+1, y-1, 1);
    }
}
