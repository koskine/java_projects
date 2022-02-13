/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Iiro Koskinen H299947
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


public class Parameters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> parameters = new ArrayList<>(Arrays.asList(args));
        Collections.sort(parameters);
        //Selvitetaan pisin parametreista
        int longest = 0;
        for (String t : parameters){
            if (t.length() > longest){
                longest = t.length();
            }
        }
        // Suurin/levein rivinumero on viimeinen, joka on myos listan pituus
        int biggest_row = parameters.size();
        String row_str = Integer.toString(biggest_row);
        int row = 1;
        
        for (int i = 0; i<(row_str.length()+longest+6); i++){
            System.out.print("#");
        }
        System.out.println("#");
        String format = "# %" + row_str.length() + "d | " + "%-" + longest + "s #%n";
        for (String s : parameters){
            System.out.format(format, row, s);
            if (row!=parameters.size()){
                System.out.print("#");
                for (int i=0;i<(row_str.length()+2);i++){
                    System.out.print("-");
                }
                System.out.print("+");
                for (int i = 0;i<(longest+2);i++){
                    System.out.print("-");
                }
                System.out.println("#");
            }
            row++;
        }
        for (int i = 0; i<(row_str.length()+longest+6); i++){
            System.out.print("#");
        }
        System.out.println("#");
        
        
    }
}
