package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.arraycopy;

//store the pure puzzle here.
//you can decide how to add state to each element you parsed

//State need to implement:
//CodeMatrix : every matrixCell in first row ->state: AVAILABLE, remaining tiles->state: UNAVAILABLE
//Sequence: every matrixCell in sequence->state: WAITING

public class Puzzle {

    private final CodeMatrix codeMatrix;
    private final List<Daemon> daemons = new ArrayList<>();
    private final Buffer buffer;

    private int bufferSize = 0;
    private final String[][] matrix = new String[6][6];
    private final List<String[]> seq = new ArrayList<>();
    private int matrixSpan = 0;
    private int mapSeed = 0;
    private final Random rand = new Random();

    private void readFile() {
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
    }

    public Puzzle(){
        readFile();
        //do not modify the definitions here
        MatrixCell temp;
        MatrixCell[][] emptyMatrix = new MatrixCell[matrixSpan][matrixSpan];

        codeMatrix = new CodeMatrix(emptyMatrix, matrixSpan);

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                if (row == 0) {
                    temp = new MatrixCell(matrix[row][col].toUpperCase());
                    temp.setAvailable(true);
                } else {
                    temp = new MatrixCell(matrix[row][col].toUpperCase());
                }
                codeMatrix.setCell(row,col,temp);
            }
        }

        DaemonCell temp2;
        for (String[] strings : seq) {
            ArrayList<DaemonCell> currentSeq = new ArrayList<>(strings.length);
            for (String s : strings) {
                temp2 = new DaemonCell(s.toUpperCase());
                currentSeq.add(temp2);
            }
            Daemon cookedSeq = new Daemon(currentSeq);
            daemons.add(cookedSeq);
        }
        buffer = new Buffer(bufferSize);
    }

//        public void print() {
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

    //Do not modify the gets
    public Buffer getBuffer(){return buffer;}

    public CodeMatrix getCodeMatrix() {return codeMatrix;}

    public List<Daemon> getSequences() {return daemons;}
}
