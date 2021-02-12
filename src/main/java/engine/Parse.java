package engine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;

//Parse the puzzle from .txt
//puzzle set should be stored in entity.Puzzle class
public class Parse {

    public int bufferSize;
    public String[][] matrix;
    public ArrayList<String[]> seq;

    public Parse(){
        bufferSize = 0;
        matrix = new String[5][5];
        seq = new ArrayList<>();
    }

    public void readFile() {
        try {
            File dir = new File("src/main/java/txt");
            File[] files = dir.listFiles();
            assert files != null;

            Random rand = new Random();
            int mapSeed = rand.nextInt(files.length);
            File file = files[mapSeed];
            mapSeed++;
            System.out.println("Current map seed: " + mapSeed);

            Scanner reader = new Scanner(file);
            bufferSize = reader.nextInt();
            System.out.println(bufferSize);
            System.out.println();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    matrix[i][j] = reader.next();
                    System.out.print(matrix[i][j] + " ");
                    if (j == 4) System.out.println();
                }
            }

            while (reader.hasNextLine()) {
                String a = reader.nextLine();
                System.out.println(a);
                String[] b = a.split(" ");
                if (b.length > 1) seq.add(b);
            }
            reader.close();
        } catch (FileNotFoundException e){
            System.out.println("Error occurs when loading puzzles");
            e.printStackTrace();
        }
    }
}
