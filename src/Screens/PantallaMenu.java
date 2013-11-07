package Screens;

import Graphics.GroupPixie;
import Graphics.Recursos;
import Graphics.GroupText;
import Graphics.Pixie;
import Main.Myrran;
import Mobiles.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Ivan Delgado Huerta
 */

public class PantallaMenu extends PantallaAbstract
{
    private GroupPixie pixi;
    private GroupPixie pixi2;
    private Pixie pixi3;
    private GroupPixie prueba;
    private GroupPixie phombreras;
    
    public static TextureRegion Personaje;
    public static TextureRegion Hombreras;
    
    
    public PantallaMenu (Myrran game)
    {
        super (game);
    }
    
    @Override
    public void show ()
    {
        super.show();
        
        FileHandle fontFile = Gdx.files.internal("fonts/RawengulkSans.ttf");
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator (fontFile);
        BitmapFont fuente = fontGen.generateFont(15);
        BitmapFont fuente2 = fontGen.generateFont(60);
        
        GroupText texto = new GroupText ("-1589",fuente, Color.GREEN, Color.GRAY, 140, 200, Align.center, Align.bottom, 1);
        texto.addAction(Actions.moveBy(0, 100, 3f, Interpolation.linear));
        texto.addAction(Actions.sequence(Actions.moveBy(25, 0, 0.50f, Interpolation.linear),Actions.moveBy(-50, 0, 0.50f, Interpolation.linear),Actions.moveBy(50, 0, 0.50f, Interpolation.linear), 
                                               Actions.moveBy(-50, 0, 0.50f, Interpolation.linear),Actions.moveBy(50, 0, 0.50f, Interpolation.linear),Actions.moveBy(-50, 0, 0.50f, Interpolation.linear)));
        texto.addAction(Actions.sequence(Actions.fadeOut(3f), Actions.removeActor()));
        stageUI.addActor(texto);
        
        texto.addAction(Actions.scaleTo(3f, 3f, 3f));
        texto.addAction(Actions.rotateBy(-90,6f));
    }
    
   
    
}
