package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.arraycopy;

//store a set of puzzle here.

public class Puzzle implements Serializable {

    private final Buffer buffer;
    private final CodeMatrix codeMatrix;
    private final List<Daemon> daemons = new ArrayList<>();

    public Puzzle(int bufferOffset) {
        Parse.emptySeq();
        Parse.readFile();

        MatrixCell[][] emptyMatrix = new MatrixCell[Parse.getMatrixSpan()][Parse.getMatrixSpan()];
        buffer = new Buffer(Parse.getBufferSize() + bufferOffset);
        codeMatrix = new CodeMatrix(emptyMatrix, Parse.getMatrixSpan());

        initCodeMatrix();
        initDaemons();
    }

    private void
    initCodeMatrix() {
        MatrixCell matrixCell;
        for (int row = 0; row < Parse.getMatrixSpan(); row++) {
            for (int col = 0; col < Parse.getMatrixSpan(); col++) {
                matrixCell = new MatrixCell(Parse.getMatrix()[row][col].toUpperCase(), new Coordinate(row, col));

                if (row == 0) matrixCell.setAvailable(true);

                codeMatrix.setCell(row, col, matrixCell);
            }
        }
    }

    private void
    initDaemons() {
        DaemonCell daemonCell;
        for (String[] strings : Parse.getSeq()) {
            ArrayList<DaemonCell> currentSeq = new ArrayList<>(strings.length);
            for (String s : strings) {
                daemonCell = new DaemonCell(s.toUpperCase());
                currentSeq.add(daemonCell);
            }
            Daemon cookedSeq = new Daemon(currentSeq);
            daemons.add(cookedSeq);
        }
    }

    public Buffer
    getBuffer() {
        return buffer;
    }

    public CodeMatrix
    getCodeMatrix() {
        return codeMatrix;
    }

    public List<Daemon>
    getDaemons() {
        return daemons;
    }
}

class Parse {

    private static int bufferSize = 0;
    private static final String[][] matrix = new String[6][6];
    private static final List<String[]> seq = new ArrayList<>();
    private static int matrixSpan = 0;
    private static final Random rand = new Random();

    private Parse() {
        throw new IllegalStateException("Utility class");
    }

    public static void
    readFile() {
        try {
            File dir = new File("./resources/puzzles");
            File[] files = dir.listFiles();
            assert files != null;

            int mapSeed = rand.nextInt(files.length);
            File file = files[mapSeed];

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

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int
    getBufferSize() {
        return bufferSize;
    }

    public static String[][]
    getMatrix() {
        return matrix;
    }

    public static List<String[]>
    getSeq() {
        return seq;
    }

    public static int
    getMatrixSpan() {
        return matrixSpan;
    }

    public static void
    emptySeq() {
        seq.clear();
    }
}
