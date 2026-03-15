/** Word categories game, Takes a list of words from
 * one category and adds in an imposter word. Asks
 * the user which word doesn't fit in.
 * @author Julia Jeng
 * @author Grace Ding
 */
import java.io.*;
import java.util.*;
public class CategoriesGame {
    private static Map<String, List<String>> allCategories;
    public static void main(String[] args) {
        
    }

    /** Sets up categories by reading from file */
    public static void initCategories() {
        allCategories = new HashMap<>();
        try {
            Scanner petp = new Scanner(new File("pets.txt")); //pet parser
            
        }
        catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        }
    }
}