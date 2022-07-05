/*
    Author: Iiro Koskinen H299947
*/

package fi.tuni.prog3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class sevenzipsearch {
    

   public static void main(String[] args) throws IOException {
       // We'll open the 7zip file and get its entries
       try(SevenZFile szf = new SevenZFile(new File(args[0]))) {
            Iterable<SevenZArchiveEntry> entries = szf.getEntries();
            for(SevenZArchiveEntry entry : entries) {
                String filename = entry.getName();
                // if the file is a .txt we open it
                if(filename.contains(".txt")) {
                    System.out.println(filename);
                    // opening the file
                    try(BufferedReader br = new BufferedReader(new InputStreamReader(szf.getInputStream(entry)))) {
                        String line;
                        int lineNumber = 1;
                        while((line = br.readLine()) != null) {
                            if(line.toLowerCase().contains(args[1].toLowerCase())) {
                                StringBuffer output = new StringBuffer(line);
                                int start = line.toLowerCase().indexOf(args[1].toLowerCase());
                                output.replace(start, start+args[1].length(), args[1].toUpperCase());
                                int outstart = line.toLowerCase().lastIndexOf(args[1].toLowerCase());
                                output.replace(outstart, outstart+args[1].length(), args[1].toUpperCase());
                                System.out.format("%d: %s%n", lineNumber, output);
                                
                            }
                            lineNumber++;
                        }
                    }
                }
                System.out.println();
            }
       }
   } 
}
