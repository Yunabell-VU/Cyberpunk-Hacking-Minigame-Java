package entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;

import static java.lang.System.*;

//Parse the puzzle from .txt
//puzzle set should be stored in entity.Puzzle class
public class Parse {

    private int bufferSize;
    private final String[][] matrix;
    private final List<String[]> seq;
    private int matrixSpan;
    private int mapSeed;
    private final Random rand;


    public Parse() {
        bufferSize = 0;
        matrix = new String[6][6];
        seq = new ArrayList<>();
        matrixSpan = 0;
        mapSeed = 0;
        rand = new Random();
    }

//    public void print() {
//        System.out.println("Current map seed: " + mapSeed);
//        System.out.println("Current buffer size: " + bufferSize);
//        System.out.println();
//        System.out.println("Current matrix span: "+ matrixSpan);
//        System.out.println("Current matrix: ");
//        System.out.println();
//        for (int i = 0; i < matrixSpan; i++) {
//            for (int j = 0; j < matrixSpan; j++) {
//                System.out.print(matrix[i][j] + " ");
//                if (j == matrixSpan - 1) System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println("Current sequences: ");
//        System.out.println();
//        for (String[] strings : seq) {
//            for (int j = 0; j < strings.length; j++) {
//                System.out.print(strings[j] + " ");
//                if (j == strings.length - 1) System.out.println();
//            }
//        }
//    }

    public void readFile() {
        try {
            File dir = new File("src/main/java/txt");
            File[] files = dir.listFiles();
            assert files != null;

            mapSeed = rand.nextInt(files.length);
            File file = files[mapSeed];
            mapSeed++;

            Scanner reader = new Scanner(file);
            bufferSize = reader.nextInt();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.length() != 0) {
                    String[] firstLine = line.split(" ");
                    matrixSpan = firstLine.length;
                    arraycopy(firstLine, 0, matrix[0], 0, matrixSpan);
                    break;
                }
            }

            for (int i = 1; i < matrixSpan; i++)
                for (int j = 0; j < matrixSpan; j++)
                    matrix[i][j] = reader.next();

            while (reader.hasNextLine()) {
                String seqStr = reader.nextLine();
                String[] seqArr = seqStr.split(" ");
                if (seqStr.length() > 0) seq.add(seqArr);
            }
            //print();
            reader.close();
        } catch (FileNotFoundException e){
            //out.println("Error occurs when loading puzzles");
            e.printStackTrace();
        }
//        print();
    }
    public int getBufferSize() {return bufferSize;}

    public String[][] getMatrix() {return matrix;}

    public List<String[]> getSeq() {return seq;}

    public int getMatrixSpan() {return matrixSpan;}
}
