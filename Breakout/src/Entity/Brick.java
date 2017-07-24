package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import core.Game;

public class Brick extends Entity {
	
	Color brickColor;

	public Brick(int x, int y, Game ga) {
		
		xPos = x;
		yPos = y;
		
		width = 40;
		height = 10;
		game = ga;
		
		createCollider(x,y,width,height);
		
		Random rCol = new Random();
		int red = rCol.nextInt(256); //Pseudo Random between 0 - 256
		int green = rCol.nextInt(256);//Colors are 255 bit numbers so we use 256
		int blue = rCol.nextInt(256);
		brickColor = new Color(red,green,blue);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(brickColor);
		g.fillRect(entCollision.x, entCollision.y, entCollision.width, entCollision.height);
		
	}

	@Override
	public void update() {
		if(entCollision.intersects(game.gameBall.entCollision))
			deadBrick = true;
			game.gameBall.changeVelocity(1, -1);
	}
	
}
