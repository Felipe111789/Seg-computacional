/*
 @Project: S-DES Algorithm
 @Author: Alberto Sardinha
 @Author: Felipe Cunha
 @Date: 04/Apr/2019
 */

package SegComp.SDES;

// java.util.Arrays for print array as a string
import java.util.Arrays;


//-----------------------------------------------------------------------------//


// Class for generate two 8-bit keys from 10-bit input key 
class KeyGenerator
{
    private int[] key = new int[10];
    private int[] k1 = new int[8];
    private int[] k2 = new int[8];
    private boolean flag = false;

// Get char by char of input string key, convert to an int array, and generate the subkeys
    void GenerateKey (String inKey)
    {
        int[] key = new int[10];
        char ch;
        String chS;

        try
        {
            for (int i=0; i<10; i++)
            {
                ch = inKey.charAt(i);
                chS = Character.toString(ch);
                key[i] = Integer.parseInt(chS);

                if (key[i]!=0 && key[i]!=1)
                {
                    System.out.println("-- Invalid Key --");
                    System.exit(0);
                    return;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("-- Invalid Key --");
            System.out.println(e.toString());
            System.exit(0);
            return;
        }

        this.key = key;

//        System.out.print("Input Key: ");
//        System.out.println(Arrays.toString(this.key));

        P10();

        LS1();

        this.k1 = P8();

        LS2();

        this.k2 = P8();

//        System.out.println("k1: " + Arrays.toString(k1));
//        System.out.println("k2: " + Arrays.toString(k2));

        flag = true;
    }

// Perform permutation P10 on 10-bit key
    private void P10()
    {
        int[] tmp = new int[10];

        tmp[0] = key[2];
        tmp[1] = key[4];
        tmp[2] = key[1];
        tmp[3] = key[6];
        tmp[4] = key[3];
        tmp[5] = key[9];
        tmp[6] = key[0];
        tmp[7] = key[8];
        tmp[8] = key[7];
        tmp[9] = key[5];

        key = tmp;
    }

// Perform permutation P8 on 10-bit key, return 8-bit subkey
    private int[] P8()
    {
        int[] tmp = new int[8];

        tmp[0] = key[5];
        tmp[1] = key[2];
        tmp[2] = key[6];
        tmp[3] = key[3];
        tmp[4] = key[7];
        tmp[5] = key[4];
        tmp[6] = key[9];
        tmp[7] = key[8];

        return tmp;
    }

 // Perform circular left-shift (LS1) separately between first and second five bits
    private void LS1()
    {
        int[] tmp = new int[10];

        tmp[0] = key[1];
        tmp[1] = key[2];
        tmp[2] = key[3];
        tmp[3] = key[4];
        tmp[4] = key[0];

        tmp[5] = key[6];
        tmp[6] = key[7];
        tmp[7] = key[8];
        tmp[8] = key[9];
        tmp[9] = key[5];

        key = tmp;
    }

// Perform double circular left-shift (LS2) separately between first and second five bits
    private void LS2()
    {
        int[] tmp = new int[10];

        tmp[0] = key[2];
        tmp[1] = key[3];
        tmp[2] = key[4];
        tmp[3] = key[0];
        tmp[4] = key[1];

        tmp[5] = key[7];
        tmp[6] = key[8];
        tmp[7] = key[9];
        tmp[8] = key[5];
        tmp[9] = key[6];

        key = tmp;
    }

// Return subkey k1
    public int[] K1()
    {
        if (flag)
            return k1;

        System.out.println("Error: Key not generated");
        return null;
    }
    
 // Return subkey k2
    public int[] K2()
    {
        if (flag)
            return k2;

        System.out.println("Error: Key not generated");
        return null;
    }
}


//-----------------------------------------------------------------------------//


// Class for make binary and decimal operations and convertions
class Binary
{
	
// Convert binary int array to decimal output
    static int BinToDec(int...bits)
    {
        int tmp=0;
        int base = 1;
        for(int i=bits.length-1; i>=0; i--)
        {
            tmp = tmp + (bits[i]*base);
            base = base * 2 ;
        }
        return tmp;
    }

 // Convert decimal number to binary int array output
    static int[] DecToBin(int number)
    {
        if(number==0)
        {
            int[] zero = new int[2];
            zero[0] = 0;
            zero[1] = 0;
            return zero;
        }

        int[] tmp1 = new int[10] ;
        int count = 0 ;

        for(int i=0 ; number!=0 ; i++)
        {
            tmp1[i] = number%2;
            number = number/2;
            count++;
        }

        int[] tmp2 = new int[count];

        for(int i=count-1, j=0; i>=0 && j<count; i--,j++)
        {
            tmp2[j] = tmp1[i];
        }

        if(count<2)
        {
            tmp1 = new int[2];
            tmp1[0] = 0;
            tmp1[1] = tmp2[0];
            return tmp1;
        }

        return tmp2;
    }
}


//-----------------------------------------------------------------------------//


// Class for generate encryption of a plaintext 
class Encryption
{
    private int[] K1 = new int[8];
    private int[] K2 = new int[8];
    private int[] text = new int[8];

// Convert 8-bit binary string input to binary int array
    void Params(String plaintext, int[] k1, int[] k2)
    {
        int[] text = new int[8];
        char ch;
        String chS;

        try
        {
            for (int i=0; i<8; i++)
            {
                ch = plaintext.charAt(i);
                chS = Character.toString(ch);
                text[i] = Integer.parseInt(chS);
                if (text[i]!=0 && text[i]!=1)
                {
                    System.out.println("-- Invalid Plaintext --");
                    System.exit(0);
                    return;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("-- Invalid Plaintext --");
            System.out.println(e.toString());
            System.exit(0);
            return;
        }

        this.text = text;
//        System.out.print("Input Plaintext Array: ");
//        System.out.println(Arrays.toString(this.text));

        this.K1 = k1;
        this.K2 = k2;
    }

// Perform Initial Permutation on 8-bit array
    void IP()
    {
        int[] tmp = new int[8];

        tmp[0] = text[1];
        tmp[1] = text[5];
        tmp[2] = text[2];
        tmp[3] = text[0];
        tmp[4] = text[3];
        tmp[5] = text[7];
        tmp[6] = text[4];
        tmp[7] = text[6];

        text = tmp;
    }
    
// Perform Inverse Initial Permutation on 8-bit array
    void iIP()
    {
        int[] tmp = new int[8];

        tmp[0] = text[3];
        tmp[1] = text[0];
        tmp[2] = text[2];
        tmp[3] = text[4];
        tmp[4] = text[6];
        tmp[5] = text[1];
        tmp[6] = text[7];
        tmp[7] = text[5];

        text =  tmp;
    }

// Perform expansion of 4-bit array, permutation with subkey and S0 & S1 box permutations
    int[] bfrF(int[] R, int[] SK)
    {
        int[] tmp = new int[8];

        tmp[0] = R[3];
        tmp[1] = R[0];
        tmp[2] = R[1];
        tmp[3] = R[2];
        tmp[4] = R[1];
        tmp[5] = R[2];
        tmp[6] = R[3];
        tmp[7] = R[0];

        tmp[0] = tmp[0] ^ SK[0];
        tmp[1] = tmp[1] ^ SK[1];
        tmp[2] = tmp[2] ^ SK[2];
        tmp[3] = tmp[3] ^ SK[3];
        tmp[4] = tmp[4] ^ SK[4];
        tmp[5] = tmp[5] ^ SK[5];
        tmp[6] = tmp[6] ^ SK[6];
        tmp[7] = tmp[7] ^ SK[7];

        final int[][] S0 = { {1,0,3,2} , {3,2,1,0} , {0,2,1,3} , {3,1,3,2} } ;
        final int[][] S1 = { {0,1,2,3},  {2,0,1,3}, {3,0,1,0}, {2,1,0,3}} ;

        int d11 = tmp[0];
        int d12 = tmp[1];
        int d13 = tmp[2];
        int d14 = tmp[3];

        int row1 = Binary.BinToDec(d11, d14);
        int col1 = Binary.BinToDec(d12, d13);

        int decOut1 = S0[row1][col1];
        int[] binOut1 = Binary.DecToBin(decOut1);

        int d21 = tmp[4];
        int d22 = tmp[5];
        int d23 = tmp[6];
        int d24 = tmp[7];

        int row2 = Binary.BinToDec(d21, d24);
        int col2 = Binary.BinToDec(d22, d23);

        int decOut2 = S1[row2][col2];
        int[] binOut2 = Binary.DecToBin(decOut2);

        int[] out = new int[4];
        out[0] = binOut1[0];
        out[1] = binOut1[1];
        out[2] = binOut2[0];
        out[3] = binOut2[1];

        int[] outP = new int[4];
        outP[0] = out[1];
        outP[1] = out[3];
        outP[2] = out[2];
        outP[3] = out[0];

        return outP;
    }

// Perform XOR permutation with L and 8-bit join with R
    int[] FK (int[] L, int[] R, int[] SK)
    {
        int[] tmp = new int[4];
        int[] out = new int[8];

        tmp = bfrF(R, SK);

        out[0] = L[0] ^ tmp[0];
        out[1] = L[1] ^ tmp[1];
        out[2] = L[2] ^ tmp[2];
        out[3] = L[3] ^ tmp[3];

        out[4] = R[0];
        out[5] = R[1];
        out[6] = R[2];
        out[7] = R[3];

        return out;
    }

// Switch function interchanges left and right 4 bits 
    int[] SW(int[] in)
    {
        int[] tmp = new int[8];

        tmp[0] = in[4];
        tmp[1] = in[5];
        tmp[2] = in[6];
        tmp[3] = in[7];
        tmp[4] = in[0];
        tmp[5] = in[1];
        tmp[6] = in[2];
        tmp[7] = in[3];

        return tmp;
    }

// Convert string plaintext to string chiphertext using S-DES
    String encrypt (String plaintext, int[] LK, int[] RK)
    {
        String[] words = plaintext.split(" ");
        String[] outputs = new String[words.length];

        for (int i=0; i<words.length; i++)
        {
            String output = "";

            for (int j=0; j < words[i].length(); j++)
            {
                char ch = words[i].charAt(j);
                int ascii = (int) ch;
                String binValue = String.format("%08d", Integer.parseInt(Integer.toBinaryString(ascii)));

                Params(binValue, LK, RK);
                IP();

                int[] LH = new int[4];
                int[] RH = new int[4];

                LH[0] = text[0];
                LH[1] = text[1];
                LH[2] = text[2];
                LH[3] = text[3];

                RH[0] = text[4];
                RH[1] = text[5];
                RH[2] = text[6];
                RH[3] = text[7];

                int[] r1 = new int[8];
                r1 = FK(LH, RH, K1);

                int[] tmp = new int[8];
                tmp = SW(r1);

                LH[0] = tmp[0];
                LH[1] = tmp[1];
                LH[2] = tmp[2];
                LH[3] = tmp[3];

                RH[0] = tmp[4];
                RH[1] = tmp[5];
                RH[2] = tmp[6];
                RH[3] = tmp[7];

                int[] r2 = new int[8];
                r2 = FK(LH, RH, K2);

                text = r2;

                iIP();

                binValue = "";
                for (int k=0; k<text.length; k++)
                    binValue += Integer.toString(text[k]);

                ascii = Integer.parseInt(binValue, 2);
                ch = (char) ascii;

                output += Character.toString(ch);
            }
            outputs[i] = output;
        }
        String chiphertext = String.join(" ", outputs);
        return chiphertext;
    }
}