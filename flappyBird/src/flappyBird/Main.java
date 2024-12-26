package flappyBird;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		int boardwidth=360;
		int boardheight=640;
		
		ImageIcon icon =new ImageIcon("letter.png");
		
		JFrame frame=new JFrame();
		frame.setSize(boardwidth,boardheight);
		frame.setResizable(false);
		frame.setTitle("Flabby Bird Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setLocationRelativeTo(null);
		frame.setIconImage(icon.getImage());
		
		FlappyBird fBird = new FlappyBird();
		frame.add(fBird);
		fBird.requestFocus();
		frame.pack();
		frame.setVisible(true);
	}

}
