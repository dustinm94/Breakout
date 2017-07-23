package core;

import javax.swing.*;

import Entity.Ball;
import Entity.Player;
import Inputs.InputHandler;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;



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
	Ball gameBall;
	
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Canvas
		canvas = new Canvas();
		canvas.setSize(gameDim);
		frame.add(canvas);
		
		//Entity
		player = new Player(this);
		gameBall = new Ball(this);
		
		//Rendering
		canvas.createBufferStrategy(3);
		BS = canvas.getBufferStrategy();
		g = (Graphics2D) BS.getDrawGraphics();
		
		//Input
		keys = new InputHandler(canvas);
		canvas.requestFocus();
	}
	
	private void update() {
		player.update();
		gameBall.update();
	}
	
	private void render() {
		if(BS == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		g.drawImage(img, 0, 0, null);
		
		//important to render the player ONTOP of the background. so it gets rendered behind.
		player.render(g);
		gameBall.render(g);
		BS.show();
	}
	
}
