package Skill;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SkillRecursos 
{   //Almacena el atlas que se usara para acceder a todos los graficos
    private static TextureAtlas atlas;
    public static Array<Pixie> listaDeSpells = new Array<>();
    public static Array<Pixie> listaDeCasteos = new Array<>();

    public static void setAtlas (TextureAtlas atlas) { SkillRecursos.atlas = atlas; }
    
    public static void salvarCasteo(String nombreEfecto) 
    {
        TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
        Pixie spellCasteo = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
        spellCasteo.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
        spellCasteo.animaciones().get(0).animarYEliminar = true;
        SkillRecursos.listaDeCasteos.add(spellCasteo);
    }

    public static void salvarEfectoDeSpell(String nombreEfecto) 
    {
        TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
        Pixie spellEffect = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
        spellEffect.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
        SkillRecursos.listaDeSpells.add(spellEffect);
    }

}
