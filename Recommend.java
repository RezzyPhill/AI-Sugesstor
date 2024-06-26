import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Recommend{
    
    public static void main(String[] args){

        try {
        Scanner scan = new Scanner(System.in);
        System.out.println("What would you like to get recommended?\n1 = Book\n2 = Movie\n3 = Show");
        String cat;
        int recommended = scan.nextInt();
        
            switch (recommended) {
            case 1:
            cat = "Movie";
            System.out.println("What genre book would you like?");
            String gg = scan.nextLine();
            String genre = scan.nextLine();
            System.out.println("How many chapters would you like the book to be?");
            int chapter = scan.nextInt();
            System.out.println("Do you have a author preference? If yes enter their name if no type \"no\"");
            String ggg = scan.nextLine();
            String author = scan.nextLine();
            if (author.equals("no")) {
                author = "No author preference";
            }
            Book book = new Book(genre, chapter, author);
            ProcessBuilder builder = new ProcessBuilder("python", "ai.py", String.valueOf(cat), String.valueOf(genre), String.valueOf(chapter),String.valueOf(author), String.valueOf("null"), String.valueOf("null"));
                   
                        Process process = builder.start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                    in.close();
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.out.println(line);
            }
            errorReader.close();
            //System.out.println(book);
            break;
            case 2: 
           cat = "Movie";
           System.out.println(cat);
           Movie movie = new Movie("horror", 18, "isaac", null, "Bill");
           //String genre = movie.genreIs(); 
           System.out.println(movie);
            break;
            case 3:
            cat = "Show";
            System.out.println(cat);
            break;
            default:
            System.out.println("Please enter a number between 1 and 3!");
            recommended = scan.nextInt();
            
        }
        
                 } catch (IOException | NumberFormatException e) {
                        
                        e.printStackTrace();
                    }  
    }
}
