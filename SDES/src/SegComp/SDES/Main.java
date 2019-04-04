package SegComp.SDES;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        KeyGenerator KG = new KeyGenerator();
        Encryption Enc = new Encryption();
        Scanner Sc = new Scanner(System.in);

        String key;
        String plaintext;
        String chiphertext;

        try
        {
            System.out.print("Enter 10-bit key: ");
            key = Sc.nextLine();
            
            System.out.print("Enter Plaintext: ");
            plaintext = Sc.nextLine();

            KG.GenerateKey(key);
            
            chiphertext = Enc.encrypt(plaintext, KG.K1(), KG.K2());
            System.out.println("Enc: "+chiphertext);
            
            plaintext = Enc.encrypt(chiphertext, KG.K2(), KG.K1());
            System.out.println("Dec: "+plaintext);

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
