package com.company;

import java.io.*;
import java.util.Scanner;

public class X_projekt_2 {

    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in); //klasa do odczytania wejścia konsoli
            System.out.println("Podaj ścieżkę katalogu: ");
            String path = input.nextLine(); //zapisanie wpisanej przez użytkownika ścieżki
            File folder = new File(path); //otwarcie folderu
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".java");
                }
            }); //wylistowanie plików java w folderze
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\__raport.txt")); //otwarcie bufera zapisu
            //potrzebne potem

            for (File f:files) { //foreach po wszystkich plikach w folderze
                //zestaw flag oznaczających spełnienie poszczególnych warunków
                boolean flagClass = false;
                boolean flagMain = false;
                boolean flagMethod = false;
                boolean flagBrackets = false;
                //zmienne do zliczania nawiasów
                int bracketRoundOpen = 0;
                int bracketRoundClose = 0;
                int bracketSquareOpen = 0;
                int bracketSquareClose = 0;
                int bracketBraceOpen = 0;
                int bracketBraceClose = 0;
                String name = f.getName(); //pobranie nazwy pliku
                String[] tabName = name.split("_"); //podział pliku względem znaku
                String className = tabName[0]; //wybranie pierwszego elementu nazwy czyli s12345
                String number = className.substring(1); //numer indeksu
                String[] splitTab = tabName[2].split("\\.");
                String projectNumber = splitTab[0]; //odczytanie numeru projektu

                BufferedReader br = new BufferedReader(new FileReader(f)); //bufer odczytu pliku
                String line;

                while ((line = br.readLine()) != null) {//czytanie linii w pliku
                    if (line.contains(String.format("class %s_projekt_%s", number, projectNumber))) { //warunek czy plik zawiera klasę
                        flagMain = true;
                    }
                    if (line.contains("public static void main(String[] args)")) { //czy plik zawiera metodę main
                        flagClass = true;
                    }
                    if (line.contains("public static int[][][] mySort(int[] dane)")) { //czy plik zawiera metodę mySort
                        flagMethod = true;
                    }
                    for (int i = 0; i < line.length(); i++){ //pętla do zliczania nawiasów
                        char bracket = line.charAt(i); //czytanie znaków po kolei i sprawdzanie czy jest nawiasem
                        switch (bracket) { //jeżeli znajdzie odpowiedni nawias - inkrementacja
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
                if(bracketBraceOpen == bracketBraceClose){ //czy nawiasy są sparowane
                    if(bracketRoundOpen == bracketRoundClose){
                        if(bracketSquareOpen == bracketSquareClose){
                            flagBrackets = true;
                        }
                    }
                }

                String result = ""; //przygotowanie zapisu
                if(flagBrackets && flagClass && flagMain && flagMethod){ //zależnie od spełnionych warunków dla pliku
                    //odpowiedni komunikat w zapisie
                    result += " - ok";
                }
                if(!flagBrackets){
                    result += " - problem z nawiasami";
                }
                if(!flagMethod){
                    result += " - problem wymagana metoda";
                }
                if(!flagClass || !flagMain){
                    result += " - problem z klasa";
                }

                bw.append(number + result);
                bw.newLine();
                br.close(); //zamknięcie bufera czytającego

            }
            bw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }
}
