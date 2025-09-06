# 📚 Library Management System (Console + JDBC)

A simple **Library Management System** built using **Java, JDBC, and MySQL**.  
Supports two roles: **Admin** and **Student**.

---

## 🚀 Features

### 🔑 Login System
- Secure login using `users` table (admin / student).
- Role-based access.

### 👨‍💼 Admin Functions
- View all books.
- Add new books.
- Update book details.
- Delete books.

### 🎓 Student Functions
- Search books (by title, author, or category).
- Borrow books.
- Return borrowed books.
- View "My Borrowed Books" list.

---

### student menu 
Enter username: mahesh
Enter password: student123
Login successful! Logged in as student

===== Student Menu =====
1. Search Books
2. Borrow Book
3. Return Book
4. My Borrowed Books
5. Logout
Enter choice: 1
Enter keyword (title/author/category): os

--- Search Results ---
ID: 1 | Title: Introduction to Java | Author: James Gosling | Category: Programming | Copies: 5

===== Student Menu =====
1. Search Books
2. Borrow Book
3. Return Book
4. My Borrowed Books
5. Logout
Enter choice: 4

--- My Borrowed Books ---
ID: 2 | Title: Database Systems | Author: C.J. Date | Category: Database | Borrowed On: 2025-09-04 18:47:51.0

===== Student Menu =====
1. Search Books
2. Borrow Book
3. Return Book
4. My Borrowed Books
5. Logout
Enter choice: 3
Enter Book ID to return: 2
Book returned successfully!

### admin menu 
Enter username: admin
Enter password: admin123
Login successful! Logged in as admin

===== Admin Menu =====
1. View Books
2. Add Book
3. Update Book
4. Delete Book
5. Logout
Enter choice: 1

--- Book List ---
ID: 1 | Title: Introduction to Java | Author: James Gosling | Category: Programming | Copies: 5
ID: 2 | Title: Database Systems | Author: C.J. Date | Category: Database | Copies: 3
ID: 3 | Title: Operating System Concepts | Author: Abraham Silberschatz | Category: Operating Systems | Copies: 4
ID: 4 | Title: Data Structures and Algorithms | Author: Robert Lafore | Category: Programming | Copies: 2
ID: 5 | Title: Computer Networks | Author: Andrew S. Tanenbaum | Category: Networking | Copies: 6

===== Admin Menu =====
1. View Books
2. Add Book
3. Update Book
4. Delete Book
5. Logout
Enter choice: 2
Enter Book Title: life things
Enter Book Author: mahesh
Enter Book Category: about life
Enter Available Copies: 5
Book added successfully!

===== Admin Menu =====
1. View Books
2. Add Book
3. Update Book
4. Delete Book
5. Logout
Enter choice: 1

--- Book List ---
ID: 1 | Title: Introduction to Java | Author: James Gosling | Category: Programming | Copies: 5
ID: 2 | Title: Database Systems | Author: C.J. Date | Category: Database | Copies: 3
ID: 3 | Title: Operating System Concepts | Author: Abraham Silberschatz | Category: Operating Systems | Copies: 4
ID: 4 | Title: Data Structures and Algorithms | Author: Robert Lafore | Category: Programming | Copies: 2
ID: 5 | Title: Computer Networks | Author: Andrew S. Tanenbaum | Category: Networking | Copies: 6
ID: 6 | Title: life things | Author: mahesh | Category: about life | Copies: 5

===== Admin Menu =====
1. View Books
2. Add Book
3. Update Book
4. Delete Book
5. Logout
Enter choice: 5
Logging out...


