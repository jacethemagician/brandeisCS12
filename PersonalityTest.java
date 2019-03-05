/* Problem Assignment 1
 * 
 * Created by: Jace Yang
 */
import java.util.*; //needed for Scanner
import java.io.*; //needed for file

public class PersonalityTest {
    public static void main(String[]args)
        throws FileNotFoundException {
        
        //prompt user for input file name
        Scanner console = new Scanner(System.in); //create a Scanner object to read from user
        System.out.print("Input file name: "); 
        //re-prompt the user when an invalid input file name is entered
        File f;
        String fileName;
        do {
            fileName = console.next();
            f = new File(fileName);
            System.out.println();
            if (!f.exists()) {
                System.out.print("File not found. Try again: ");
            }
        } while (!f.exists());
        
        //create a scanner to extract data from file
        Scanner inputFile = new Scanner(new File(fileName)); 
        System.out.print("Output file name: "); //print output to a destination file
        File fOutput = new File(console.next());
        PrintStream output = new PrintStream(fOutput);
        
        //data extraction block of code
        while (inputFile.hasNextLine()) { 
            //process input data
            String name = inputFile.nextLine();
            String answer = inputFile.nextLine();
            int[] allA = new int[4]; //two arrays in charge of storing raw data
            int[] allB = new int[4];
            processAnswer(answer, allA, allB);
            int[] percentB = getPercentage(allA, allB);//a new array to store B percentage
            String type = getType(percentB);
            printOutput(output, allA, allB, percentB, name, type);
        }
    }
    
    /* method that put String answer into two arrays, one of A and one of B, each contains 4 items, in following order:
     * Extrovert/Introvert, Sensing/iNtuition, Thinking/Feeling, Judging/Perceiving
     */
    private static void processAnswer(String str, int[] a, int[] b) {
        for (int i = 0; i < str.length(); i++) {
            int index;
            //determine where to put in the string created
            if (i % 7 == 0) {
                index = 0;
            } else if (i % 7 == 1 || i % 7 == 2) {
                index = 1;
            } else if (i % 7 == 3 || i % 7 == 4) {
                index = 2;
            } else {
                index = 3;
            }
            //put A's into Array with A, B's into Array with B
            if (str.charAt(i) == 'A' || str.charAt(i) == 'a') {
                a[index]++;
            }
            if (str.charAt(i) == 'B' || str.charAt(i) == 'b') {
                b[index]++;
            }
        }
    }
    
    //method returns an array by calculating the B percentage (rounded to the nearest whole percent)
    private static int[] getPercentage(int[] a, int[] b) {
        int[] percent = new int[4];
        for (int i = 0; i < percent.length; i++) {
            percent[i] = (int)Math.round((double)b[i] / (a[i] + b[i]) * 100);
        }
        return percent;
    }
    
    //method that returns a String representing the four-letter persoanlity type
    private static String getType(int[] arr) {
        String str = "";
        String A = "ESTJ";
        String B = "INFP";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 50) {
                str += Character.toString(B.charAt(i));
            } else if (arr[i] < 50) {
                str += Character.toString(A.charAt(i));
            } else 
                str += "X";
        }
        return str;
    }
    
    //method that output to a file
    private static void printOutput(PrintStream out, int[] a, int[] b, int[] percent, String name, String type) {
        out.println(name + ":");
        for (int i = 0; i < a.length; i++) {
            out.print(a[i] + "A-" + b[i] + "B ");
        }
        out.println();
        out.print("[");
        for (int i = 0; i < percent.length; i++) {
            if (i == 3) {
                out.print(percent[i] + "%] = ");
            } else {
                out.print(percent[i] + "%, ");
            }
        }
        out.println(type);
        out.println();
    }
    
}