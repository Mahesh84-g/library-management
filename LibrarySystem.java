package lbms;
import java.sql.*;
import java.util.*;
public class LibrarySystem {
    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";      // change if needed
    static final String PASSWORD = "Mahe$h123";  // change if needed
    static Scanner sc = new Scanner(System.in);
    static int loggedInUserId;
    static String loggedInRole;

    public static void main(String[] args) {
        if (login()) {
            if (loggedInRole.equals("admin")) {
                adminMenu();
            } else {
                studentMenu();
            }
        } else {
            System.out.println("Login failed. Exiting...");
        }
    }

    // ---------- LOGIN ----------
    static boolean login() {
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("id");
                loggedInRole = rs.getString("role");
                System.out.println("Login successful! Logged in as " + loggedInRole);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    // ---------- ADMIN MENU ----------
    static void adminMenu() {
        int choice;
        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View Books");
            System.out.println("2. Add Book");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> addBook();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    // ---------- STUDENT MENU ----------
    static void studentMenu() {
        int choice;
        do {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Search Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. My Borrowed Books");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> searchBooks();
                case 2 -> borrowBook();
                case 3 -> returnBook();
                case 4 -> myBorrowedBooks();
                case 5 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    // ---------- BOOK FUNCTIONS ----------
    static void viewBooks() {
        String query = "SELECT * FROM books";
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n--- Book List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Author: %s | Category: %s | Copies: %d%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("available_copies"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addBook() {
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Book Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Book Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Available Copies: ");
        int copies = sc.nextInt();

        String query = "INSERT INTO books(title, author, category, available_copies) VALUES(?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, copies);
            ps.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateBook() {
        System.out.print("Enter Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter New Title: ");
        String title = sc.nextLine();
        System.out.print("Enter New Author: ");
        String author = sc.nextLine();
        System.out.print("Enter New Category: ");
        String category = sc.nextLine();
        System.out.print("Enter New Copies: ");
        int copies = sc.nextInt();

        String query = "UPDATE books SET title=?, author=?, category=?, available_copies=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, copies);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Book updated successfully!");
            else System.out.println("Book not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        String query = "DELETE FROM books WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Book deleted successfully!");
            else System.out.println("Book not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------- STUDENT FUNCTIONS ----------
    static void searchBooks() {
        System.out.print("Enter keyword (title/author/category): ");
        String keyword = sc.nextLine();
        String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Search Results ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Author: %s | Category: %s | Copies: %d%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("available_copies"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int id = sc.nextInt();

        String checkQuery = "SELECT available_copies, title FROM books WHERE id=?";
        String updateBook = "UPDATE books SET available_copies=available_copies-1 WHERE id=?";
        String insertTxn = "INSERT INTO transactions(user_id, book_id) VALUES(?, ?)";

        try (Connection con = getConnection();
             PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int copies = rs.getInt("available_copies");
                if (copies > 0) {
                    try (PreparedStatement updateStmt = con.prepareStatement(updateBook);
                         PreparedStatement txnStmt = con.prepareStatement(insertTxn)) {
                        updateStmt.setInt(1, id);
                        updateStmt.executeUpdate();

                        txnStmt.setInt(1, loggedInUserId);
                        txnStmt.setInt(2, id);
                        txnStmt.executeUpdate();

                        System.out.println("You borrowed: " + rs.getString("title"));
                    }
                } else {
                    System.out.println("Sorry, no copies available.");
                }
            } else {
                System.out.println("Book not found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();

        String checkTxn = "SELECT * FROM transactions WHERE book_id=? AND user_id=? AND return_date IS NULL";
        String updateBook = "UPDATE books SET available_copies=available_copies+1 WHERE id=?";
        String updateTxn = "UPDATE transactions SET return_date=NOW() WHERE book_id=? AND user_id=? AND return_date IS NULL";

        try (Connection con = getConnection();
             PreparedStatement txnStmt = con.prepareStatement(checkTxn)) {

            txnStmt.setInt(1, id);
            txnStmt.setInt(2, loggedInUserId);
            ResultSet rs = txnStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateBookStmt = con.prepareStatement(updateBook);
                     PreparedStatement updateTxnStmt = con.prepareStatement(updateTxn)) {
                    updateBookStmt.setInt(1, id);
                    updateBookStmt.executeUpdate();

                    updateTxnStmt.setInt(1, id);
                    updateTxnStmt.setInt(2, loggedInUserId);
                    updateTxnStmt.executeUpdate();

                    System.out.println("Book returned successfully!");
                }
            } else {
                System.out.println("You have not borrowed this book.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void myBorrowedBooks() {
        String query = "SELECT b.id, b.title, b.author, b.category, t.borrow_date " +
                "FROM transactions t JOIN books b ON t.book_id=b.id " +
                "WHERE t.user_id=? AND t.return_date IS NULL";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, loggedInUserId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- My Borrowed Books ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Author: %s | Category: %s | Borrowed On: %s%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getTimestamp("borrow_date"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------- DB CONNECTION ----------
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
