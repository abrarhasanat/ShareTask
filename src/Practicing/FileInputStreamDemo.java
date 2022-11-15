package Practicing;

import java.io.File;
import java.io.FileInputStream;

public class FileInputStreamDemo {
    public static void main(String[] args) {
        try {
            File file;
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/Practicing/FileInputStreamDemo.java");
            int size = 0;
            System.out.println("Total available bytes " + (size = fis.available()));
            int n = size / 10;
            System.out.println("Fiest "+ n + " bytes of the file one read at a time");
            for(int i = 0 ; i < n; ++i){
                System.out.print((char)fis.read());
            }
            System.out.println("\nStill available " + (fis.available()));
            System.out.println("Reading the next "+ n + "bytes with with one bytes[n]");
            byte [] bytes = new byte[n];
            if(fis.read(bytes) != n)
                System.out.println("Couldnt read " + n + "bytes" );
            else {
                System.out.println(new String(bytes , 0 ,n));
            }
            System.out.println("\nStill available " + (size = fis.available()));
            System.out.println("skipping half of the ramaining size  with f.skip()" );
            fis.skip(size/2);
            System.out.println("Still availabe "+ (size = fis.available()));
            System.out.println("Reading " + n/2 + " into the end of the array") ;
            if(fis.read(bytes, n/2, n/2) != n/2)
                System.out.println("couldnt read");
            System.out.println(new String(bytes , 0 ,bytes.length));
            System.out.println("\nSTill available " + fis.available());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
