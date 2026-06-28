import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
class Book{
    private int book_id;
    private String title;
    private String author;
    private User Borrower;
    private Date issueDate;

    Book(int book_id,String title, String author){
        this.book_id=book_id;
        this.title=title;
        this.author=author;
        this.Borrower = null;
        this.issueDate = null;
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
    Date getIssueDate(){
        return issueDate;
    }
    void setBorrower(User borrower){
        this.Borrower = borrower;
    }
    void setIssueDate(Date issueDate){
        this.issueDate = issueDate;
    }
}

class Date{
    int day;
    int month;
    int year;
    Date(int day, int month, int year){
        if(day<1 || day>31 || month<1 || month>12 || year<0){
            System.out.println("Invalid Date");
        }
        else{
            this.day=day;
            this.month=month;
            this.year=year;
        }
    }
    int getDay(){
        return day;
    }
    int getMonth(){
        return month;
    }
    int getYear(){
        return year;
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
    private String course;
    Student(int userId, String name, String course){
        super(userId,name);
        this.course=course;
    }
    
}

class Teacher extends User{
    private String department;
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

    void searchUsers(String name){
        name = name.toLowerCase();
        System.out.println("Searching...");
        for(User u : users.values()){
            if(u.getName().toLowerCase().contains(name)) System.out.println("UserId: "+u.getUserId()+"\nName: "+u.getName());
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
            if(books.get(bookId).getBorrower()!=null){
                System.out.println("Book Cannot be removed While Issued.");
            }
            else{
                books.remove(bookId);
                System.out.println("Book Removed.");
            }
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
            if(b.getTitle().toLowerCase().contains(name)) System.out.println("BookId: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued to: "+borrowerName );
        }
    }

    void displayBooks(){
        if(books.size()==0){
            System.out.println("NO books Available."); return;
        }
        for(Book b : books.values()){
            String borrowerName; if(b.getBorrower()==null) borrowerName = "none"; else borrowerName = b.getBorrower().getName();
            System.out.println("Book Id: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued to: "+borrowerName);
        }
        
    }

    void displayIssuedBooks(){
        for(Book b : books.values()){
            if(b.getBorrower()!=null)
                System.out.println("Book Id: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssued to: "+b.getBorrower().getName()+"\nIssue Date: "+b.getIssueDate().getDay()+"/"+b.getIssueDate().getMonth()+"/"+b.getIssueDate().getYear());
        }
        
    }

    void displayAvailableBooks(){
        for(Book b : books.values()){
            if(b.getBorrower()==null)
                System.out.println("Book Id: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor());
        }
        
    }

    void issueBook(int bookId, int userId, Date issueDate){
        if(books.containsKey(bookId)){
            if(books.get(bookId).getBorrower()==null){
                if(users.containsKey(userId)){
                    books.get(bookId).setBorrower(users.get(userId));
                    books.get(bookId).setIssueDate(issueDate);
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
                books.get(bookId).setIssueDate(null);
                System.out.println("Book Returned");
            }
            else System.out.println("Book Already Returned");
        }
        else System.out.println("Book Not Found");
    }

    void UserBorrowedBooks(int userId){
        if(users.containsKey(userId)){
            for(Book b : books.values()){
                if(b.getBorrower()!=null && b.getBorrower().getUserId()==userId){
                    System.out.println("Book Id: "+b.getId()+"\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nIssue Date: "+b.getIssueDate().getDay()+"/"+b.getIssueDate().getMonth()+"/"+b.getIssueDate().getYear());
                }
            }
        }
        else{
            System.out.println("User Not Found");
            return;
        }
    }



}
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Library l = new Library();
        while (true) {
            int userId;
            int bookId;
            System.out.print("1.Add Student\n2.Add Teacher\n3.Search User \n4.Display Users\n5.Add Book\n6.Remove Book\n7.Search Book\n8.Display Books\n9.Issue Book\n10.Return Book\n11.Display Issued Books\n12.Display Available Books\n13.Books Issued to User\n14.Exit\n Choice: ");
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
                    System.out.print("Enter User Name: ");
                    sc.nextLine();
                    String uname = sc.nextLine();
                    l.searchUsers(uname);
                    break;
                case 4:
                    System.out.print("Users Using the library: \n");
                    l.displayUsers();
                    break;
                case 5:
                    System.out.print("Enter Book Id: ");
                    bookId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    
                    String title = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    
                    String author = sc.nextLine();
                    l.addBook(bookId,title,author);
                    break;
                
                case 6:
                    System.out.print("Enter Book Id: ");
                    bookId = sc.nextInt();
                    l.removeBook(bookId);
                    break;
                    
                case 7:
                    System.out.print("Enter Book name: ");
                    sc.nextLine();
                    String bname = sc.nextLine();
                    l.searchBooks(bname); 
                    break;
                    
                case 8:
                    System.out.println("Books Present in the Library.");
                    l.displayBooks();
                    break;

                case 9:
                    System.out.print("Enter Book Id: ");
                    bookId=sc.nextInt();
                    System.out.print("Enter User Id: ");
                    userId = sc.nextInt();
                    System.out.print("Enter Issue date (DD MM YYYY): ");
                    int day = sc.nextInt();
                    int month = sc.nextInt();
                    int year = sc.nextInt();
                    Date issueDate = new Date(day, month, year);
                    l.issueBook(bookId,userId,issueDate);
                    break;

                case 10:
                    System.out.print("Enter Book Id: ");
                    bookId=sc.nextInt();
                    l.returnBook(bookId);
                    break;

                case 11:
                    System.out.println("Books Issued:");
                    l.displayIssuedBooks();
                    break;

                case 12:
                    System.out.println("Books Available:");
                    l.displayAvailableBooks();
                    break;

                case 13:
                    System.out.println("Enter User Id: ");
                    userId = sc.nextInt();
                    System.out.println("Books Borrowed by User Id: "+userId);
                    l.UserBorrowedBooks(userId);
                    break;
                case 14:
                    System.out.println("Exiting...");
                    return;
                default:
                    break;
            }

        }

    }
}

