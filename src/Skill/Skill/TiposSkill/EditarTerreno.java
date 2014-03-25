package Skill.Skill.TiposSkill;
// @author Ivan Delgado Huerta
import Constantes.MiscData;
import Constantes.Skills.TipoSkillsData;
import Geo.Celda;
import Geo.Mapa;
import Main.Mundo;
import Mobiles.Mobs.Personaje;
import Skill.Skill.Skill;
import Skill.Skill.TipoSkill;
import Skill.SkillStat;
import com.badlogic.gdx.math.Vector2;

public class EditarTerreno extends TipoSkill
{
    public EditarTerreno (String id)                { super(id); }
    public EditarTerreno ()                         { }
    
    @Override public void inicializarSkillStats() 
    {
        skillStats = new SkillStat [1]; 
        SkillStat stat = new SkillStat  (TipoSkillsData.EDITARTERRENO_CastingTime_String, TipoSkillsData.EDITARTERRENO_CastingTime_Valor); skillStats[0]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Skill skill, Personaje caster, float targetX, float targetY) 
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
        Mapa.crearTile(x+1, y-1, numCapa);}
}
