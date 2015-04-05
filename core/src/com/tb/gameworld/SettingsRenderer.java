package com.tb.gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tb.gameobjects.ColorSelector;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tb.cghelpers.AssetLoader;

public class SettingsRenderer {

	OrthographicCamera cam;
	SpriteBatch batcher;
	ShapeRenderer shapeRenderer;
	Vector2 midPoint;
	
	public SettingsRenderer(float gameHeight, float gameWidth, Vector2 midPoint){
    	cam = new OrthographicCamera();
    	cam.setToOrtho(true);
    	cam.viewportHeight = gameHeight;
    	cam.viewportWidth=gameWidth;
    	cam.position.set(cam.viewportWidth * .5f,cam.viewportHeight * .5f, 0f);
    	cam.update();
    	batcher = new SpriteBatch();
    	batcher.setProjectionMatrix(cam.combined);
    	shapeRenderer = new ShapeRenderer();
    	shapeRenderer.setProjectionMatrix(cam.combined);
    	this.midPoint = midPoint;
	}
	
	public void render(float delta, float runtime){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(0, 0, midPoint.x*2, midPoint.y*2);
		shapeRenderer.setColor(ColorSelector.gray);
		shapeRenderer.rect(0, 18, midPoint.x*2, 2);
		shapeRenderer.line(0, 42, midPoint.x*2, 42);
		shapeRenderer.line(0, 62, midPoint.x*2, 62);
		shapeRenderer.line(0, 85, midPoint.x*2, 85);
		shapeRenderer.line(0, 100, midPoint.x*2, 100);
		shapeRenderer.line(0, 120, midPoint.x*2, 120);
		shapeRenderer.end();
		batcher.begin();
		AssetLoader.settingsfont.draw(batcher, "Settings", midPoint.x-18, 5);
		batcher.draw(AssetLoader.email,2,22,18,18);
		batcher.draw(AssetLoader.link, midPoint.x*2-10, 26, 10, 10);
		AssetLoader.settingssmall.draw(batcher, "Email: timebotgames@gmail.com", 24, 28);
		batcher.draw(AssetLoader.facebook,3,45,15,15);
		batcher.draw(AssetLoader.link, midPoint.x*2-10, 47, 10, 10);
		AssetLoader.settingssmall.draw(batcher, "Facebook: TimeBot", 24, 50);
		batcher.draw(AssetLoader.star, 3, 103, 15,15);
		batcher.draw(AssetLoader.link, midPoint.x*2-10, 105, 10, 10);
		AssetLoader.settingssmall.draw(batcher, "Rate", 24, 106);
		batcher.draw(AssetLoader.twitter, 3, 68, 15,15);
		batcher.draw(AssetLoader.link, midPoint.x*2-10, 69, 10, 10);
		AssetLoader.settingssmall.draw(batcher, "Twitter", 24, 72);
		batcher.draw(AssetLoader.back, 0, 1, 15, 15);
		AssetLoader.settingssmall.draw(batcher, "Follow us for updates and new games!", 3, 91);
		batcher.end();
	}
}
