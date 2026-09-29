import java.util.Scanner;
public class StudentMarkValidation {
    static void validate(int mark){
        if(mark<0||mark>100) throw new IllegalArgumentException("Mark must be between 0 and 100.");
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("=== Student Mark Validation ===");
        System.out.print("Enter student name: "); String name=sc.nextLine();
        System.out.print("Enter mark: "); int mark=sc.nextInt();
        try{
            validate(mark);
            System.out.println("Student: "+name);
            System.out.println("Valid Mark: "+mark);
            System.out.println(mark>=50?"Result: Pass":"Result: Fail");
        }catch(IllegalArgumentException e){System.out.println("Validation Error: "+e.getMessage());}
        sc.close();
    }
}