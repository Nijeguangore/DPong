package paddles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.RenderListener;

public class KeyControl implements KeyListener {
	
	RenderListener controllable = null;
	public KeyControl(RenderListener renderingContext) {
		controllable = renderingContext;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'w'){
			controllable.updatePaddle("UP");
		}else if(e.getKeyChar() == 's'){
			controllable.updatePaddle("DN");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
