import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final String url = "jdbc:mysql://localhost:3306/librarydb";
    private final String username = "root";
    private final String password = "Sumesh@9406";

    Connection connection() throws SQLException{
            Connection connection = DriverManager.getConnection(url,username,password);
            return connection;
    }
    
    

    HashMap<Integer, Book> books = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    

    void addStudent(int userId, String name, String course){
        
        

        try{
            String addStudentQuery = "insert into users values(? ,? ,'Student' ,?)";
            PreparedStatement AddStudentStatement = connection().prepareStatement(addStudentQuery);
            AddStudentStatement.setInt(1,userId);
            AddStudentStatement.setString(2, name);
            AddStudentStatement.setString(3, course);
            AddStudentStatement.executeUpdate();
            System.out.println("Student Added");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void addTeacher(int userId, String name, String department){
        try{
            String AddTeacherQuery = "insert into users values(?, ?, 'Teacher', ?)";
            PreparedStatement AddTeacherStatment = connection().prepareStatement(AddTeacherQuery);
            AddTeacherStatment.setInt(1,userId);
            AddTeacherStatment.setString(2, name);
            AddTeacherStatment.setString(3, department);
            AddTeacherStatment.executeUpdate();
            System.out.println("Teacher Added");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void searchUsers(String partOfName){
        try{
            boolean found = false;
            System.out.println("Searching...");
            String searchUserQuery = "select userId, name from users where name like ?";
            PreparedStatement searchUserStatement = connection().prepareStatement(searchUserQuery);
            searchUserStatement.setString(1, '%'+partOfName+'%');
            ResultSet foundUsers = searchUserStatement.executeQuery();
            
            while(foundUsers.next()){
                System.out.println("UserId: "+foundUsers.getInt("userid")+"\nName: "+foundUsers.getString("name"));
                System.out.println("***************************************");
                found = true;
            }
            if(found==false) System.out.println("No Users Fpund matching with the input.");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void displayUsers(){
        try{
            String displayUsersQuery = "select * from users";
            PreparedStatement displayUserStatement = connection().prepareStatement(displayUsersQuery);
            ResultSet users = displayUserStatement.executeQuery();
            if(!users.next()) System.out.println("No Users Exist.");
            while(users.next()){
                System.out.println("UserId: "+users.getInt("userid")+"\nName: "+users.getString("name")+"\nType: "+users.getString("type")+"\nCourse/Department: "+users.getString("Course_or_designation"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void addBook(int bookId, String title, String author, int quantity){
        try{
            String addBookQuery = "insert into books values(?, ?, ?, ?)";
            PreparedStatement addBookStatement = connection().prepareStatement(addBookQuery);
            addBookStatement.setInt(1, bookId);
            addBookStatement.setString(2, title);
            addBookStatement.setString(3, author);
            addBookStatement.setInt(4, quantity);
            addBookStatement.executeUpdate();
            System.out.println("Book Added to Library");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void removeBook(int bookId){
        try{
            String bookIssueCheckQuery = "select userid from bookissued where bookid = ?";
            String bookDeleteQuery = "delete from books where bookid = ?";
            PreparedStatement bookIssueCheckStatement = connection().prepareStatement(bookIssueCheckQuery);
            PreparedStatement bookDeleteStatement = connection().prepareStatement(bookDeleteQuery);
            bookIssueCheckStatement.setInt(1, bookId);
            ResultSet bookIssue = bookIssueCheckStatement.executeQuery();
            if(!bookIssue.next()){
                bookDeleteStatement.setInt(1, bookId);
                bookDeleteStatement.executeUpdate();
                System.out.println("Book Removed from library");
                return;
            }
            System.out.println("Cannot Delete Book While Issued");
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    void searchBooks(String partOfTitle){
        System.out.println("SEarching...");
        try{
            boolean found = false;
            String searchBook = "select * from books where title like ?";
            PreparedStatement searchBookStatement = connection().prepareStatement(searchBook);
            searchBookStatement.setString(1, '%'+partOfTitle+'%');
            ResultSet booksFound = searchBookStatement.executeQuery();
            while(booksFound.next()){
                System.out.println("BookId: "+booksFound.getInt("bookid")+"\nTitle: "+booksFound.getString("title")+"\nAuthor: "+booksFound.getString("Author")+"\nQuantity: "+booksFound.getInt("quantity"));
                found = true;
            }
            if(found==false){
                System.out.println("No Book Found to The Matching Input.");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void displayBooks(){
        try{
            String displayBooksQuery = "select * from books";
            PreparedStatement displayBookStatement = connection().prepareStatement(displayBooksQuery);
            ResultSet booksPresent = displayBookStatement.executeQuery();
            while(booksPresent.next()){
                System.out.println("BookId: "+booksPresent.getInt("bookid")+"\nTitle: "+booksPresent.getString("title")+"\nAuthor: "+booksPresent.getString("author")+"\nQuntity: "+booksPresent.getInt("quantity"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    void displayIssuedBooks(){
        try{
            String retriveIssuedBooks = "select * from bookissued";
            String bookInfoQuery = "select title, author from books where bookid = ?";
            String borrowerInfoQuery = "select name, type from users  where userid = ?";
            PreparedStatement bookInfoStatement = connection().prepareStatement(bookInfoQuery);
            PreparedStatement borrowerInfoStatement = connection().prepareStatement(borrowerInfoQuery);
            PreparedStatement retriveIssuedBooksStatement = connection().prepareStatement(retriveIssuedBooks);
            ResultSet bookIssued = retriveIssuedBooksStatement.executeQuery();
            while (bookIssued.next()) {
                bookInfoStatement.setInt(1,bookIssued.getInt("bookid"));
                borrowerInfoStatement.setInt(1, bookIssued.getInt("userid"));
                ResultSet bookInfo = bookInfoStatement.executeQuery();
                ResultSet borrowerInfo = borrowerInfoStatement.executeQuery();
                System.out.println("Issued Id: "+bookIssued.getInt("issueid"));
                System.out.println("--------------------------");
                if(bookInfo.next()) System.out.println("\nBook Issued: \nBook Id: "+bookIssued.getInt("bookid")+"\nTitle: "+bookInfo.getString("title")+"\nAuthor: "+bookInfo.getString("author"));
                System.out.println("--------------------------");
                if(borrowerInfo.next()) System.out.println("Borrowed By: \nUserId: "+bookIssued.getInt("userid")+"\nName: "+borrowerInfo.getString("name")+"\nType: "+borrowerInfo.getString("type"));
                System.out.println("****************************************");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void displayAvailableBooks(){
        try{
            System.out.println("Books Available. \n============================");
            String availableBooksQuery = "select * from books where quantity > 0";
            PreparedStatement statement = connection().prepareStatement(availableBooksQuery);
            ResultSet availableBooks = statement.executeQuery();
            while(availableBooks.next()){
                System.out.println("BookId: "+availableBooks.getInt("bookid")+"\nTitle: "+availableBooks.getString("title")+"\nAuthor: "+availableBooks.getString("author")+"\nQuantity: "+availableBooks.getInt("quantity"));
                System.out.println("-------------------------------");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
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
                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    l.addBook(bookId,title,author,quantity);
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

