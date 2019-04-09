package SegComp.SDES;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
    	String arq = "Image/linux2.bmp";
    	
    	read_img img_bin = new read_img(arq);
        Scanner Sc = new Scanner(System.in);

        String key;
        String plaintext;
       
        try
        {
           key = "1100111000";
           img_bin.cipher_img(key);
           
        }
        catch (InputMismatchException e)
        {
            System.out.println("-- Error: Invalid Input --");
            System.out.println(e.toString());
        }
        catch (Exception e)
        {
            System.out.println("-- Error Ocurred: "+e.toString());
        }
    }
}
