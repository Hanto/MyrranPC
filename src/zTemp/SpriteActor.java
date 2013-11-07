/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zTemp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Hanto
 */
public class SpriteActor extends Actor
{
    private Sprite sprite;
    
    public SpriteActor (TextureAtlas atlas, String spriteName)
    {
        sprite = atlas.createSprite(spriteName);
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        
        sprite.setPosition(this.getX(), this.getY());
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
        sprite.setRotation(this.getRotation());
        sprite.setScale(this.getScaleX(), this.getScaleY());
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = new Color (this.getColor().r, this.getColor().g, this.getColor().b, this.getColor().a * parentAlpha);
        
        batch.setColor(color);
        sprite.setColor(color);
        
        sprite.draw(batch);
    }
}
