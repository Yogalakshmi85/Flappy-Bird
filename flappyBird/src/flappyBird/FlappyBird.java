package flappyBird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
	
	int boardwidth=360;
	int boardheight=640;
	
	//Images that have used
	Image bgImage;
	Image birdImage;
	Image toppipeImage;
	Image bottompipeImage;
	
	// dimensions to place the bird  
	int birdx=boardwidth/8;
	int birdy=boardheight/2;
	int birdwidth=34;
	int birdheight=24;
	
	
	//to place a bird
	class Bird{
		int x=birdx;
		int y=birdy;
		int width=birdwidth;
		int height=birdheight;
		Image img;
		
		Bird(Image img){
			this.img=img;
		}
	}
	
	//dimensions to place pipes
	
	int pipex=boardwidth;
	int pipey=0;
	int pipewidth=64;
	int pipeheight=512;
	
	//to place pipe
	class Pipe{
		int x=pipex;
		int y=pipey;
		int width=pipewidth;
		int height=pipeheight;
		Image img;
		boolean passed=false;
		
		Pipe(Image img){
			this.img=img;
		}
	}
	
	Bird bird;
	
	//timer to move bird
	Timer gameloop;
	
	//timer to place pipes
	Timer placePipesTimer;
	
	boolean gameover=false;
	double score=0;
	
	int velocityX=-4;
	int velocityY=0;
	int gravity=1;
	
	//array to store different lengths of pipes
	ArrayList<Pipe> pipes;
	
	Random random=new Random();
	
	FlappyBird() {
		setPreferredSize(new Dimension(boardwidth, boardheight));
		
		setFocusable(true);
		addKeyListener(this);
		
		//fetching the images
		bgImage = new ImageIcon("D:\\java\\flappyBird\\flappyBirdbg.png").getImage();
	    birdImage = new ImageIcon("D:\\java\\flappyBird\\bird.png").getImage();
	    toppipeImage=new ImageIcon("D:\\java\\flappyBird\\toppipe.png").getImage();
	    bottompipeImage=new ImageIcon("D:\\java\\flappyBird\\bottompipe.png").getImage();
	    
	    
	    bird=new Bird(birdImage);
	    pipes=new ArrayList<Pipe>();
	    
	    placePipesTimer=new Timer(1500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				placePipes();
			}
		});
	    
	    
	    placePipesTimer.start();
	    
	    gameloop=new Timer(1000/60, this);
	    gameloop.start();
	}
	
	public void placePipes() {
		
		int randomPipeY=(int) (pipey-pipeheight/4 - Math.random()*(pipeheight/2));
		int openningspace=boardheight/4;
		
		Pipe topPipe=new Pipe(toppipeImage);
		topPipe.y=randomPipeY;
		pipes.add(topPipe);
		
		Pipe bottomPipe=new Pipe(bottompipeImage);
		bottomPipe.y= openningspace + pipeheight + topPipe.y;
		pipes.add(bottomPipe);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, 360, 640, null);
		
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
		
		for(int i=0;i<pipes.size();i++) {
			Pipe pipe=pipes.get(i);
			g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
		}
		
		
		g.setColor(new Color(228, 208, 10));
		g.setFont(new Font("Verdana", Font.PLAIN, 35));
		
		if(gameover) {
			g.drawString("Game over: " + String.valueOf((int) score), 10, 35);
		}
		else {
			g.drawString(String.valueOf((int) score), 10, 35);
		}
		
	}
	public void move() {
		velocityY+=gravity;
		bird.y+=velocityY;
		bird.y=Math.max(bird.y, 0);
		
		for(int i=0;i<pipes.size();i++) {
			Pipe pipe=pipes.get(i);
			pipe.x+=velocityX;
			
			if(!pipe.passed && bird.x > pipe.x +pipe.width) {
				pipe.passed=true;
				score+=0.5;
			}
			
			if(collision(bird, pipe)) {
				gameover=true;
			}
		}
		
		if(bird.y > boardheight) {
			gameover=true;
		}
	}
	
	
	public boolean collision(Bird a, Pipe b) {
		return a.x < b.x + a.width &&
				a.x + a.width > b.x &&
				a.y < b.y + b.height &&
				a.y + a.height > b.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		
		if(gameover) {
			placePipesTimer.stop();
			gameloop.stop();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			velocityY=-9;
			
			if(gameover) {
				bird.y=birdy;
				velocityY=0;
				pipes.clear();
				score=0;
				gameover=false;
				gameloop.start();
				placePipesTimer.start();
			}
		}
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
}
