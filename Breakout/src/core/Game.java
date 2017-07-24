package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entity.Ball;
import Entity.Brick;
import Entity.Player;
import Inputs.InputHandler;



public class Game implements Runnable {
	
	//Game Threads
	Thread thread = new Thread(this);
	boolean running = false;
	
	//JFrame 
	JFrame frame;
	public static String windowName = "D&D Arcade: Breakout!";
	public static int width = 640;
	public static int height = width / 16 * 9;
	public static Dimension gameDim = new Dimension(width, height);
	
	
	//Rendering
	public Canvas canvas; //canvas is different from JFrame think of painting the images on a canvas not a window.
	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	BufferStrategy BS;
	Graphics2D g;
	
	//Entity
	public Player player;
	public Ball gameBall;
	public ArrayList<Brick> brickList;
	
	//Inputs
	public InputHandler keys;
	
	
	public void run() {
		
		while(true) {
			update();
			render();
			try {
				Thread.sleep(1000/60); // FPS Controller.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void start() {
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
		try {
			running = false;
			g.dispose();
			thread.join();
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Game() {
		init(); // new game object
	}
	
	// constructors
	private void init() {
		//Creating Frame
		frame = new JFrame(windowName);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Canvas
		canvas = new Canvas();
		canvas.setSize(gameDim);
		canvas.setPreferredSize(gameDim);
		canvas.setMaximumSize(gameDim);
		canvas.setMinimumSize(gameDim);
		frame.add(canvas);
		
		//Entity
		player = new Player(this);
		gameBall = new Ball(this);
		brickList = new ArrayList<Brick>();
		spawnBricks();
		
		//Rendering
		canvas.createBufferStrategy(3);

		
		//Input
		keys = new InputHandler(canvas);
		canvas.requestFocus();
	}
	
	private void spawnBricks() {
		//spawns bricks in random colors 16x10 for a total of 160
		for(int x = 0; x < 16; ++x) {
			for(int y = 0; y < 10; ++y) {
				brickList.add(new Brick(x * 40, y * 12, this));
			}
		}
	}
	
	private void update() {
		player.update();
		gameBall.update();
		for(Brick b : brickList) {
			b.update();
		}
		for(int i = 0; i < brickList.size(); ++i) {
			if(brickList.get(i).deadBrick)
				brickList.remove(i);
		}
	}
	
	private void render() {
		BS = canvas.getBufferStrategy();
		g = (Graphics2D) BS.getDrawGraphics();
		if(BS == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		g.drawImage(img, 0, 0, null);
		
		//important to render the player ONTOP of the background. 
		player.render(g);
		gameBall.render(g);
		for(Brick b : brickList)
			b.render(g);
		
		BS.show();
	}
	
}
