package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import Constantes.Skills.TipoSpellsData;
import Geo.Celda;
import Geo.Mapa;
import Main.Mundo;
import Actores.Mobs.Personaje;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import Skill.SkillStat;
import com.badlogic.gdx.math.Vector2;

public class EditarTerreno extends TipoSpell
{
    public EditarTerreno (String id)                { super(id); }
    public EditarTerreno ()                         { }
    
    @Override public void inicializarSkillStats() 
    {
        skillStats = new SkillStat [1]; 
        SkillStat stat = new SkillStat  (TipoSpellsData.EDITARTERRENO_CastingTime_String, TipoSpellsData.EDITARTERRENO_CastingTime_Valor); skillStats[STAT_Cast]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Spell skill, Personaje caster, float targetX, float targetY) 
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
