import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
class Book{
    private int book_id;
    private String title;
    private String author;
    private User Borrower;

    Book(int book_id,String title, String author){
        this.book_id=book_id;
        this.title=title;
        this.author=author;
        this.Borrower = null;
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
    User getBorrower(){
        return Borrower;
    }
    void setBorrower(User borrower){
        this.Borrower = borrower;
    }
}

class User{
    private int userId;
    private String name;
    User(int userId, String name){
        this.userId = userId;
        this.name=name;
    }

    int getUserId(){
        return userId;
    }

    String getName(){
        return name;
    }

    
}

class Student extends User{
    String course;
    Student(int userId, String name, String course){
        super(userId,name);
        this.course=course;
    }
    
}

class Teacher extends User{
    String department;
    Teacher(int userId, String name, String department){
        super(userId,name);
        this.department=department;
    }
} 

class Library{
    HashMap<Integer, Book> books = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();

    void addStudent(int userId, String name, String course){
        if(users.containsKey(userId)){
            System.out.println("User Already Exist.");
        }
        else{
            Student s = new Student(userId, name, course);
            users.put(userId , s);
            System.out.println("Student Added");
        }
    }

    void addTeacher(int userId, String name, String department){
        if(users.containsKey(userId)){
            System.out.println("User Already Exist.");
        }
        else{
            Teacher t = new Teacher(userId, name, department);
            users.put(userId , t);
            System.out.println("Teacher Added");
        }
    }

    void displayUsers(){
        for(User u : users.values()){
            System.out.println("UserId: "+u.getUserId()+"\nName: "+u.getName());
        }
    }

    void addBook(int bookId, String title, String author){
        if(books.containsKey(bookId)){
            System.out.println("Book Already Exists.");
        }
        else{
            Book b =new Book(bookId, title, author);
            books.put(bookId, b);
            System.out.println("Book Added.");
        }
    }

    void removeBook(int bookId){
        if(books.containsKey(bookId)){
            books.remove(bookId);
            System.out.println("Book Revomed.");
        }
        else{
            System.out.println("Book Not Found.");
        }
    }

    void searchBooks(String name){
        name = name.toLowerCase();
        System.out.println("Searching...");
        for(Integer bookId : books.keySet()){
            Book b = books.get(bookId);
            String borrowerName;
            if(b.getBorrower()!=null) borrowerName = b.getBorrower().getName();
            else borrowerName = "None";
            if(b.getTitle().toLowerCase().contains(name)) System.out.println("BookId: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued to: "+borrowerName);
        }
    }

    void displayBooks(){
        if(books.size()==0){
            System.out.println("NO books Available."); return;
        }
        for(Book b : books.values()){
            String borrowerName; if(b.getBorrower()==null) borrowerName = "none"; else borrowerName = b.getBorrower().getName();
            System.out.println("ID: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued to: "+borrowerName);
        }
        
    }

    void issueBook(int bookId, int userId){
        if(books.containsKey(bookId)){
            if(books.get(bookId).getBorrower()==null){
                if(users.containsKey(userId)){
                    books.get(bookId).setBorrower(users.get(userId));
                    System.out.println("Book Issued");
                }
                else System.out.println("User Don't Exist");
            }
            else System.out.println("Book Already issued to: "+books.get(bookId).getBorrower().getName());
        }
        else System.out.println("Book Not Found");
    }

    void returnBook(int bookId){
        if(books.containsKey(bookId)){
            if(books.get(bookId).getBorrower()!=null){
                books.get(bookId).setBorrower(null);
                System.out.println("Book Returned");
            }
            else System.out.println("Book Already Returned");
        }
        else System.out.println("Book Not Found");
    }



}
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Library l = new Library();
        while (true) {
            int userId;
            int bookId;
            System.out.print("1.Add Student\n2.Add Teacher\n3.Display Users\n4.Add Book\n5.Remove Book\n6.Search Book\n7.Display Books\n8.Issue Book\n9.Return Book\n10.Exit\n Choice: ");
            int choice = sc.nextInt();
            switch (choice){
                
                case 1:
                    System.out.print("Enter Student ID: ");
                    userId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();
                    l.addStudent(userId, name, course);
                    break;
                case 2:
                    System.out.print("Enter Teacher ID: ");
                    userId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Teacher Name: ");
                    name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();
                    l.addTeacher(userId, name, department);
                    break;
                case 3:
                    System.out.print("Users Using the library: \n");
                    l.displayUsers();
                    break;
                case 4:
                    System.out.print("Enter Book Id: ");
                    bookId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    
                    String title = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    
                    String author = sc.nextLine();
                    l.addBook(bookId,title,author);
                    break;
                
                case 5:
                    System.out.print("Enter Book Id: ");
                    bookId = sc.nextInt();
                    l.removeBook(bookId);
                    break;
                    
                case 6:
                    System.out.print("Enter Book name: ");
                    String bname = sc.next();
                    l.searchBooks(bname); 
                    break;
                    
                case 7:
                    System.out.println("Books Present in the Library.");
                    l.displayBooks();
                    break;

                case 8:
                    System.out.print("Enter Book Id: ");
                    bookId=sc.nextInt();
                    System.out.print("Enter User Id: ");
                    userId = sc.nextInt();
                    l.issueBook(bookId,userId);
                    break;

                case 9:
                    System.out.print("Enter Book Id: ");
                    bookId=sc.nextInt();
                    l.returnBook(bookId);
                    break;

                case 10:
                    System.out.println("Exiting...");
                    return;
                default:
                    break;
            }

        }

    }
}

