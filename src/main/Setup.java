package main;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;

import paddles.KeyControl;

public class Setup {

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Pong VG");
		mainFrame.setSize(1200,900);
		
		GLCanvas context = new GLCanvas();
		RenderListener renderingContext = new RenderListener();

		KeyControl keyControl = new KeyControl(renderingContext);
		
		context.addKeyListener(keyControl);
		context.addGLEventListener(renderingContext);
		
		mainFrame.getContentPane().add(context);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true){
			context.display();
		}
	}

}
