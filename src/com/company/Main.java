package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Podaj ścieżkę katalogu: ");
            String path = input.nextLine();
            File folder = new File(path);
            File[] files = folder.listFiles();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\__raport.txt"));

            for (File f:files) {
                boolean flagClass = false;
                boolean flagMain = false;
                boolean flagMethod = false;
                boolean flagBrackets = false;
                int bracketRoundOpen = 0;
                int bracketRoundClose = 0;
                int bracketSquareOpen = 0;
                int bracketSquareClose = 0;
                int bracketBraceOpen = 0;
                int bracketBraceClose = 0;

                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("class ABC_projekt_X")) {
                        flagMain = true;
                    }
                    if (line.contains("public static void main(String[] args)")) {
                        flagClass = true;
                    }
                    if (line.contains("public static int[][][] mySort(int[] dane)")) {
                        flagMethod = true;
                    }
                    for (int i = 0; i < line.length(); i++){
                        char bracket = line.charAt(i);
                        switch (bracket) {
                            case '(':
                                bracketRoundOpen++;
                                break;
                            case ')':
                                bracketRoundClose++;
                                break;
                            case '[':
                                bracketSquareOpen++;
                                break;
                            case ']':
                                bracketSquareClose++;
                                break;
                            case '{':
                                bracketBraceOpen++;
                                break;
                            case '}':
                                bracketBraceClose++;
                                break;
                            default:
                                break;
                        }
                    }
                }
                if(bracketBraceOpen == bracketBraceClose){
                    if(bracketRoundOpen == bracketRoundClose){
                        if(bracketSquareOpen == bracketSquareClose){
                            flagBrackets = true;
                        }
                    }
                }
                String fileName = "Plik " + f.getName();
                String result = "";
                if(flagBrackets && flagClass && flagMain && flagMethod){
                    result = "ABC - ok";
                }
                else if(flagClass && flagMain && flagMethod){
                    result = "ABC - problem z nawiasami";
                }
                else if(flagBrackets && flagClass && flagMain){
                    result = "ABC - problem wymagana metoda";
                }
                else if(!flagClass){
                    result = "ABC - problem z klasa";
                }


                bw.append(fileName);
                bw.newLine();
                bw.append(result);
                bw.newLine();
                br.close();
            }
            bw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
}
