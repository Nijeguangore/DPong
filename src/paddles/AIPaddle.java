package paddles;

public class AIPaddle {
	private float location = 0.25f;
	private float height = 0.25f;
	public float[] paddle = {0.8f,location,0.0f, 0.8f,location-height,0.0f, 1.0f,location,0.0f, 1.0f,location-height,0.0f};
	public int[] indices = {0,1,2, 1,3,2};
	public boolean collision(float ballTopLeftX, float ballTopLeftY) {
		if(ballTopLeftY < location && ballTopLeftY > location-height){
			return ballTopLeftX > 0.8f;
		}
		return false;
	}
}
