package Skill.Skill.TiposSkill;
// @author Ivan Delgado Huerta

import Skill.Skill.TipoSkill;
import Constantes.MiscData;
import Constantes.Skills.TipoSkillsData;
import Geo.Celda;
import Geo.Muro;
import Main.Mundo;
import Mobiles.Mobs.Personaje;
import Skill.Skill.Skill;
import Skill.SkillStat;
import com.badlogic.gdx.math.Vector2;

public class EditarMuro extends TipoSkill
{
    public EditarMuro (String id)                   { super(id); }
    public EditarMuro ()                            { } 
    
    @Override public void inicializarSkillStats() 
    {
        skillStats = new SkillStat [1]; 
        SkillStat stat = new SkillStat  (TipoSkillsData.EDITARMURO_CastingTime_String, TipoSkillsData.EDITARMURO_CastingTime_Valor); skillStats[0]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Skill skill, Personaje caster, float targetX, float targetY) 
    {
        Vector2 destino = convertirCoordenadasDestino(caster, targetX, targetY);
        destino = convertirCoordenadasANumeroDeTile(destino);
        
        int x = (int)destino.x;
        int y = (int)destino.y;
        
        Celda celda = new Celda (Mundo.mapa[x][y]);
        
        if (celda.getMuroID() != 0)
        {
            Mundo.mapa[x][y] = celda;
            celda.setMuro(0);
            
            Muro muro = new Muro(Mundo.listaDeMuros.get(0));
            celda.muro = muro;
            Mundo.stageMundo.addActor(muro);
            muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
        }
    }
}
