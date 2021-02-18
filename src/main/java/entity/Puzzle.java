package entity;

import java.util.ArrayList;
import java.util.List;

//store the pure puzzle here.
//you can decide how to add state to each element you parsed

//State need to implement:
//CodeMatrix : every matrixCell in first row ->state: AVAILABLE, remaining tiles->state: UNAVAILABLE
//Sequence: every matrixCell in sequence->state: WAITING

public class Puzzle {

    //do not modify the definitions here
    private final int matrixSpan;
    private final MatrixCell[][] codeMatrix;
    private final List<Daemon> daemons = new ArrayList<>();
    private final int bufferSize;


    public Puzzle(){
        Parse map = new Parse();
        map.readFile();
        matrixSpan = map.getMatrixSpan();

        String[][] rawMatrix = map.getMatrix();
        MatrixCell temp;
        codeMatrix = new MatrixCell[matrixSpan][matrixSpan];
        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                if (row == 0) {
                    temp = new MatrixCell(rawMatrix[row][col].toUpperCase());
                    temp.setAvailable(true);
                } else {
                    temp = new MatrixCell(rawMatrix[row][col].toUpperCase());
                }
                codeMatrix[row][col] = temp;
            }
        }

        DaemonCell temp2;
        List<String[]> seq = map.getSeq();
        for (String[] strings : seq) {
            ArrayList<DaemonCell> currentSeq = new ArrayList<>(strings.length);
            for (String s : strings) {
                temp2 = new DaemonCell(s.toUpperCase());
                currentSeq.add(temp2);
            }
            Daemon cookedSeq = new Daemon(currentSeq);
            daemons.add(cookedSeq);
        }
        bufferSize = map.getBufferSize();
    }
    //Do not modify the gets
    public int getBufferSize() {return bufferSize;}

    public MatrixCell[][] getCodeMatrix() {return codeMatrix;}

    public List<Daemon> getSequences() {return daemons;}

    public int getMatrixSpan() {return matrixSpan;}
}
