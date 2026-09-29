import java.util.*;
class Book {
    private int id; private String title; private String author;
    Book(int id,String title,String author){this.id=id;this.title=title;this.author=author;}
    int getId(){return id;}
    public String toString(){return id+" | "+title+" | "+author;}
}
public class BookManagementSystem {
    public static void main(String[] args){
        ArrayList<Book> books=new ArrayList<>();
        books.add(new Book(101,"Java Basics","James"));
        books.add(new Book(102,"Python Guide","Guido"));
        books.add(new Book(103,"Database Systems","Korth"));
        System.out.println("=== Book Management System ===");
        System.out.println("All books (enhanced for loop):");
        for(Book b:books) System.out.println(b);
        HashMap<Integer,Book> map=new HashMap<>();
        for(Book b:books) map.put(b.getId(),b);
        System.out.println("\nREAD ID 102: "+map.get(102));
        map.put(102,new Book(102,"Advanced Python","Guido"));
        System.out.println("UPDATE ID 102: "+map.get(102));
        map.remove(101);
        System.out.println("DELETE ID 101:");
        for(Book b:map.values()) System.out.println(b);
    }
}