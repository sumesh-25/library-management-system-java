import java.util.Scanner;
import java.util.ArrayList;
class Book{
    private int book_id;
    private String title;
    private String author;
    private boolean isIssued;

    Book(int book_id,String title, String author){
        this.book_id=book_id;
        this.title=title;
        this.author=author;
        this.isIssued = false;
    }

    int getId(){
        return book_id;
    }
    String getTitle(){
        return title;
    }
    String getAuthor(){
        return author;
    }
    boolean isIssued(){
        return isIssued;
    }
    void setIssue(boolean issue){
        this.isIssued = issue;
    }
}

class User{
    private String username;
    String Name;
}

class Student extends User{
    String course;
}

class Teacher extends User{
    String department;
} 

class Library{
    ArrayList<Book> books = new ArrayList<>();

    void addBook(int id, String title, String author){
        for(Book b: books){
            if(b.getId()==id){
                System.out.println("Id Already Exist.");
                return;
            }
        }
        Book b1 = new Book(id,title,author); 
        books.add(b1);
        System.out.println("Book Added");
    }



    void searchBooks(String name){
        name = name.toLowerCase();
        System.out.println("Searching...");
        for(Book b : books){
            String title = b.getTitle().toLowerCase();

            if(title.contains(name)){System.out.println("ID: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor());} 
        }
        
    }

    void displayBooks(){
        if(books.size()==0){
            System.out.println("NO books Available."); return;
        }
        for(Book b : books){
            System.out.println("ID: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued: "+b.isIssued());
        }
        
    }

    void issueBook(int id){
        for(Book b : books){
            if(b.getId()==id){
                if(b.isIssued()==false){
                    b.setIssue(true);
                    System.out.println("Book issued");
                    return;
                }
                else{
                    System.out.println("Book Already Issued/Not Available.");
                    return;
                }
            }
        }
        System.out.println("Book Not Found");
    }

    void returnBook(int id){
        for(Book b : books){
            if(b.getId()==id){
                if(b.isIssued()==true){
                    b.setIssue(false);
                    System.out.println("Book Returned");
                    return;
                }
                else{
                    System.out.println("Book Already Returned.");
                    return;
                }
            }
        }
        System.out.println("Book Not Found");
    }



}
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Library l = new Library();
        while (true) {
            System.out.print("1.Add Book\n2.Remove Book\n3.Search Book\n4.Display Books\n5.Issue Book\n6.Return Book\n7.Exit\n Choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    
                    String title = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    
                    String author = sc.nextLine();
                    l.addBook(id,title,author);
                    break;
                
                case 2:
                    System.out.print("Enter Book Id: ");
                    id = sc.nextInt();
                    l.removeBook(id);
                    break;
                    
                case 3:
                    System.out.print("Enter Book name: ");
                    String name = sc.next();
                    l.searchBooks(name); 
                    break;
                    
                case 4:
                    System.out.println("Books Present int he Library.");
                    l.displayBooks();
                    break;

                case 5:
                    System.out.print("Enter Book Id: ");
                    id=sc.nextInt();
                    l.issueBook(id);
                    break;

                case 6:
                    System.out.print("Enter Book Id: ");
                    id=sc.nextInt();
                    l.returnBook(id);
                    break;

                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    break;
            }
        }

    }
}

