
package SegComp.SDES;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class read_img {
	   BufferedImage image;
	   int width;
	   int height;
	   
	   public read_img(String s){
	      try 
	      {   
	         File input = new File(s);
	         image = ImageIO.read(input);
	         width = image.getWidth();
	         height = image.getHeight();
	      }
	      catch (Exception e) {
	    	  System.out.println("Erro 1");
	      }
	   }
	   
	   void cipher_img(String key) throws NumberFormatException, IOException {
		   
		   KeyGenerator KG = new KeyGenerator();
	       Encryption Enc = new Encryption();
	       String chiphertext1, chiphertext2, chiphertext3, chiphertext0;
	       
	       KG.GenerateKey(key);

		   for(int i=0; i<height; i++) {
	         	for(int j=0; j<width; j++) {
	         		
	         		Color c = new Color(image.getRGB(j, i));
	         		
	         		if(c.getRed()!=32 && c.getGreen()!=32 && c.getBlue() != 32) {
	         			
	         			chiphertext1 = Enc.encrypt( Character.toString((char) c.getRed()), KG.K2(), KG.K1());
	         			
	         			chiphertext2 = Enc.encrypt(Character.toString((char) c.getGreen()), KG.K2(), KG.K1());

	         			chiphertext3 = Enc.encrypt(Character.toString((char) c.getBlue()), KG.K2(), KG.K1());
	         			
	         		
	         			write_img(i, j, (int) chiphertext1.charAt(0), (int) chiphertext2.charAt(0), (int) chiphertext3.charAt(0));
	         		}
	         	}
		   }
		   System.out.println("Terminou cifra");
	   }
	   
	   void write_img(int i, int j, int red, int green, int blue) throws IOException{
		    
		   int rgb = (0 | red| green| blue);
           image.setRGB(j, i, rgb);
           ImageIO.write(image, "bmp", new File("Image/linux2.bmp"));
	   }
}
