package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import core.Game;

public class Player extends Entity {
	//this is the PLAYER entity AKA the bar at the bottom in breakout.
	//constructors
	public Player(Game game) {
		this.game = game;
		
		xPos = 288;
		yPos = 280;
		
		width = 64;
		height = 16;
		
		bMove = 3;
		entCollision = new Rectangle((int) xPos, (int) yPos, width, height); 
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect( (int) xPos, (int) yPos, width, height);
		
	}
	public void update() {
		if(game.keys.left)
			xPos -= bMove;
		if(game.keys.right)
			xPos += bMove;
		
		entCollision.setLocation( (int) xPos, (int) yPos);
	}
}
