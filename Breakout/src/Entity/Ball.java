package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import core.Game;

public class Ball extends Entity {

	public Ball(Game game){
		//balls stats
		this.game = game;
		
		xPos = 312;
		yPos = 140;
		
		width = 16;
		height = 16;
		
		bMove = 3;
		xMove = bMove;
		yMove = bMove;
		
		entCollision = new Rectangle((int) xPos, (int) yPos, width, height); //collision
		
	}
	
	public void render(Graphics g) {
		//handles the balls appearance
		g.setColor(Color.WHITE);
		g.fillOval((int) xPos, (int) yPos, width, height);
		
	}


	public void update() {
		
		// Ball unit collision with player and sides of the screen
		
		xPos += xMove;
		yPos += yMove;
		
		if(entCollision.intersects(game.player.entCollision)) {
			yMove = -bMove;
		}
		if(yPos <= 0) {
			yMove = bMove;
		}
		if(yPos + height >= game.canvas.getHeight()) {
			yMove = -bMove; 
		}
		if(xPos <= 0) {
			xMove = bMove;
		}
		if(xPos + width >= game.canvas.getWidth()) {
			xMove = -bMove; 
		}
		
		
		entCollision.setLocation( (int) xPos, (int) yPos);
		
	}
	public void changeVelocity(int x, int y) {
		// changes the velocity of the ball. this is the ground work for when it hits bricks etc.
		
		xMove *= x;
		yMove *= y;
	}

}
