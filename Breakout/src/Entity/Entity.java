package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import core.Game;

public abstract class Entity {
	
	Rectangle entCollision;
	//allows me to create entities within the game. Allows me to set their position on X and Y axis as well as their size with width and height.
	Game game;
	float xPos;
	float yPos;
	int width;
	int height;
	int bMove;
	int xMove;
	int yMove;
	
	
	
	public abstract void render(Graphics G);
	public abstract void update();
}
