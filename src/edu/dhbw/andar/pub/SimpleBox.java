package edu.dhbw.andar.pub;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import edu.dhbw.andar.util.GraphicsUtil;

public class SimpleBox {
	private Bitmap bitmap;

	private FloatBuffer box;
	private FloatBuffer normals;
	private FloatBuffer tb;

	//private int[] pixelData;
	
	private boolean isTextureInitialized;
	
	private ByteBuffer imageBuffer;
	
	public SimpleBox() {
		bitmap = CustomActivity.bitmap;
		
		//pixelData = new int[bitmap.getHeight() * bitmap.getWidth()];
		
		isTextureInitialized = false;

		imageBuffer = ByteBuffer.allocateDirect(bitmap.getHeight() * bitmap.getWidth() * 4);
		imageBuffer.order(ByteOrder.nativeOrder());

		float boxf[] =  {
				// FRONT
				-25.0f, -25.0f,  25.0f,
				 25.0f, -25.0f,  25.0f,
				-25.0f,  25.0f,  25.0f,
				 25.0f,  25.0f,  25.0f,
				// BACK
				-25.0f, -25.0f, -25.0f,
				-25.0f,  25.0f, -25.0f,
				 25.0f, -25.0f, -25.0f,
				 25.0f,  25.0f, -25.0f,
				// LEFT
				-25.0f, -25.0f,  25.0f,
				-25.0f,  25.0f,  25.0f,
				-25.0f, -25.0f, -25.0f,
				-25.0f,  25.0f, -25.0f,
				// RIGHT
				 25.0f, -25.0f, -25.0f,
				 25.0f,  25.0f, -25.0f,
				 25.0f, -25.0f,  25.0f,
				 25.0f,  25.0f,  25.0f,
				// TOP
				-25.0f,  25.0f,  25.0f,
				 25.0f,  25.0f,  25.0f,
				 -25.0f,  25.0f, -25.0f,
				 25.0f,  25.0f, -25.0f,
				// BOTTOM
				-25.0f, -25.0f,  25.0f,
				-25.0f, -25.0f, -25.0f,
				 25.0f, -25.0f,  25.0f,
				 25.0f, -25.0f, -25.0f,
			};
		float normalsf[] =  {
				// FRONT
				0.0f, 0.0f,  1.0f,
				0.0f, 0.0f,  1.0f,
				0.0f, 0.0f,  1.0f,
				0.0f, 0.0f,  1.0f,
				// BACK
				0.0f, 0.0f,  -1.0f,
				0.0f, 0.0f,  -1.0f,
				0.0f, 0.0f,  -1.0f,
				0.0f, 0.0f,  -1.0f,
				// LEFT
				-1.0f, 0.0f,  0.0f,
				-1.0f, 0.0f,  0.0f,
				-1.0f, 0.0f,  0.0f,
				-1.0f, 0.0f,  0.0f,
				// RIGHT
				1.0f, 0.0f,  0.0f,
				1.0f, 0.0f,  0.0f,
				1.0f, 0.0f,  0.0f,
				1.0f, 0.0f,  0.0f,
				// TOP
				0.0f, 1.0f,  0.0f,
				0.0f, 1.0f,  0.0f,
				0.0f, 1.0f,  0.0f,
				0.0f, 1.0f,  0.0f,
				// BOTTOM
				0.0f, -1.0f,  0.0f,
				0.0f, -1.0f,  0.0f,
				0.0f, -1.0f,  0.0f,
				0.0f, -1.0f,  0.0f,
			};
		float[] tc = new float[]{	// Added
				1.0f, 1.0f,
				0.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f,

				1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, };
		tb = GraphicsUtil.makeFloatBuffer(tc);	// Added

		box = GraphicsUtil.makeFloatBuffer(boxf);
		normals = GraphicsUtil.makeFloatBuffer(normalsf);
	}
	
	// Added Method
	public int loadTextureFromBitmap(GL10 gl){
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		byte buffer[] = new byte[4];
		
//		for(int i = 0; i < bitmap.getHeight(); i++){
//			for(int j = 0; j < bitmap.getWidth(); j++){				
//				if(!isTextureInitialized){
//					pixelData[j + (i*(bitmap.getWidth()-1))] = bitmap.getPixel(j, i);
//				
//					Log.d("PixelData", "PixelData[" + i + "][" + j + "]:" + pixelData[j + (i*(bitmap.getWidth()-1))]);
//					
//					buffer[0] = (byte)Color.red(pixelData[j + (i*(bitmap.getWidth()-1))]);
//					buffer[1] = (byte)Color.green(pixelData[j + (i*(bitmap.getWidth()-1))]);
//					buffer[2] = (byte)Color.blue(pixelData[j + (i*(bitmap.getWidth()-1))]);
//					buffer[3] = (byte)Color.alpha(pixelData[j + (i*(bitmap.getWidth()-1))]);
//					imageBuffer.put(buffer);
//				}else{
//					break;
//				}
//			}
//		}
//		
//		if(!isTextureInitialized){
//			isTextureInitialized = true;
//		}
//		

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		if(!isTextureInitialized){
			imageBuffer.position(0);

			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){				
					int color = bitmap.getPixel(j, i);
					//pixelData[j + (i*(width-1))] = color;
				
					//Log.d("PixelData", "PixelData[" + i + "][" + j + "]:" + pixelData[j + (i*(width-1))]);
					Log.d("PixelData", "getPixel(" + i + ", " + j + "):" + color);
					
					buffer[0] = (byte)Color.red(color);
					buffer[1] = (byte)Color.green(color);
					buffer[2] = (byte)Color.blue(color);
					buffer[3] = (byte)Color.alpha(color);
					imageBuffer.put(buffer);
				}
			}
			
			isTextureInitialized = true;
		}
		
		imageBuffer.position(0);
		gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, width, height, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, imageBuffer);

		return textures[0];
	}	
	
	public final void draw(GL10 gl) {	
		gl.glEnable(GL10.GL_CULL_FACE);			// Added
		gl.glCullFace(GL10.GL_BACK);			// Added

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);	// Added
	    gl.glEnable(GL10.GL_TEXTURE_2D);						// Added
	    loadTextureFromBitmap(gl);		// Added

	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, box);

	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tb);	// Added

	    gl.glNormalPointer(GL10.GL_FLOAT,0, normals);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);	// Added
	    gl.glDisable(GL10.GL_TEXTURE_2D);						// Added
	}
}
