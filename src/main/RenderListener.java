package main;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import paddles.AIPaddle;
import paddles.UserPaddle;

public class RenderListener implements GLEventListener {

	private String[] vertexSrc = {
			"#version 330\n",
			"layout (location = 0) in vec3 position;\n",
			"void main(){\n",
			"gl_Position = vec4(position, 1.0);\n",
			"}\n"
	};
	private String[] fragmentSrc = {
			"#version 330\n",
			"out vec4 color;\n",
			"void main(){\n",
			"color = vec4(0.0f,1.0f,0.0f,1.0f);\n",
			"}\n"
	};
	private int programID = -1;
	private UserPaddle uPaddle = new UserPaddle();
	private AIPaddle ePaddle = new AIPaddle();
	private float ballTopLeftX = 0.0f;
	private float ballTopLeftY = 0.02f;
	private float ballSize = 0.02f;
	private boolean up_down =true;
	private boolean left_right = true;
	
	public void updatePaddle(String dir){
		
		switch(dir){
		case "UP": uPaddle.moveUp();break; 
		case "DN": uPaddle.moveDown();break;
		}
	}
	@Override
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
		
		int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		gl.glShaderSource(vertexShader, vertexSrc.length, vertexSrc, null);
		gl.glCompileShader(vertexShader);
		
		int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragmentShader, fragmentSrc.length, fragmentSrc, null);
		gl.glCompileShader(fragmentShader);
		
		programID = gl.glCreateProgram();
		gl.glAttachShader(programID, vertexShader);
		gl.glAttachShader(programID, fragmentShader);
		gl.glLinkProgram(programID);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
		drawPaddle(gl);
		
		float[] ballArray = {ballTopLeftX+ballSize,ballTopLeftY,0.0f, ballTopLeftX,ballTopLeftY,0.0f, ballTopLeftX+ballSize,ballTopLeftY-ballSize,0.0f, ballTopLeftX,ballTopLeftY-ballSize,0.0f};
		int[] ballIndex = {0,1,3, 3,2,0};
		int[] VBO = new int[1];
		int[] EBO = new int[1];
		
		gl.glGenBuffers(1, VBO,0);
		gl.glGenBuffers(1, EBO,0);
		
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, ballIndex.length*4, IntBuffer.wrap(ballIndex), GL3.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, ballArray.length*4, FloatBuffer.wrap(ballArray), GL3.GL_STATIC_DRAW);
	
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glUseProgram(programID);
		
		gl.glDrawElements(GL3.GL_TRIANGLES, ballIndex.length, GL3.GL_UNSIGNED_INT, 0);
		
		drawOpposition(gl);
		adjustBall();
	}

	private void adjustBall() {
		if(up_down){
			ballTopLeftY += 0.0f;
			if(ballTopLeftY > 0.98f){
				up_down = false;
			}
		}
		else{
			ballTopLeftY -= 0.01f;
			if(ballTopLeftY < -0.98f){
				up_down = true;
			}
		}
		if(left_right){
			ballTopLeftX -= 0.01f;
			if(uPaddle.collision(ballTopLeftX,ballTopLeftY)){
				left_right = false;
			}
		}
		else{
			ballTopLeftX += 0.01f;
			if(ePaddle.collision(ballTopLeftX,ballTopLeftY)){
				left_right = true;
			}
		}
	}
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}
	
	public void drawOpposition(GL3 gl){
		int[] VBO = new int[1];
		int[] EBO = new int[1];
		
		gl.glGenBuffers(1, VBO,0);
		gl.glGenBuffers(1, EBO,0);
		
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, ePaddle.indices.length*4, IntBuffer.wrap(ePaddle.indices), GL3.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, ePaddle.paddle.length*4, FloatBuffer.wrap(ePaddle.paddle), GL3.GL_STATIC_DRAW);
	
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glUseProgram(programID);
		
		gl.glDrawElements(GL3.GL_TRIANGLES, ePaddle.indices.length, GL3.GL_UNSIGNED_INT, 0);
	}
	
	public void drawPaddle(GL3 gl){
		int[] VBO = new int[1];
		int[] EBO = new int[1];
		
		gl.glGenBuffers(1, VBO,0);
		gl.glGenBuffers(1, EBO,0);
		
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, uPaddle.indices.length*4, IntBuffer.wrap(uPaddle.indices), GL3.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, uPaddle.paddle.length*4, FloatBuffer.wrap(uPaddle.paddle), GL3.GL_STATIC_DRAW);
		
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glUseProgram(programID);
		
		gl.glDrawElements(GL3.GL_TRIANGLES, uPaddle.indices.length, GL3.GL_UNSIGNED_INT, 0);
	}
}
