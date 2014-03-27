package Skill;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class SkillRecursos 
{
    private static TextureAtlas atlas;  //Almacena el atlas que se usara para acceder a todos los graficos

    public static HashMap<String, Pixie> listaDePixieProyectiles = new HashMap<>();
    public static HashMap<String, Pixie> listaDePixieCasteos = new HashMap<>();
    public static HashMap<String, TextureRegion> listaDeSpellIconos = new HashMap<>();
    public static HashMap<String, TextureRegion> listaDeAuraIconos = new HashMap<>();


    public static void setAtlas (TextureAtlas atlas) { SkillRecursos.atlas = atlas; }
    
    public static void salvarCasteo(String nombreEfecto) 
    {
        TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
        Pixie spellCasteo = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
        spellCasteo.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
        spellCasteo.animaciones().get(0).animarYEliminar = true;
        listaDePixieCasteos.put(nombreEfecto, spellCasteo);
    }

    public static void salvarEfectoDeSpell(String nombreEfecto) 
    {
        TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
        Pixie spellEffect = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
        spellEffect.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
        listaDePixieProyectiles.put(nombreEfecto, spellEffect);
    }

    public static void salvarIconoSpell (String nombreIcono)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_SpellIcons_LOC+nombreIcono));
        listaDeSpellIconos.put(nombreIcono, texture);
    }

    public static void salvarIconoAura (String nombreIcono)
    {
        TextureRegion texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_AuraIcons_LOC+nombreIcono));
        listaDeAuraIconos.put(nombreIcono, texture);
    }
}
