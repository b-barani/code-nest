class Student {
    private int id; private String name; private String course;
    Student(int id,String name,String course){this.id=id;this.name=name;this.course=course;}
    void display(){System.out.println(id+" | "+name+" | "+course);}
}
class EngineeringStudent extends Student {
    private String specialization;
    EngineeringStudent(int id,String name,String course,String specialization){
        super(id,name,course); this.specialization=specialization;
    }
    @Override void display(){super.display();System.out.println("Specialization: "+specialization);}
}
class ArtsStudent extends Student {
    private String subject;
    ArtsStudent(int id,String name,String course,String subject){
        super(id,name,course); this.subject=subject;
    }
    @Override void display(){super.display();System.out.println("Subject: "+subject);}
}
public class StudentManagementSystem {
    public static void main(String[] args){
        System.out.println("=== Student Management System ===");
        Student s1=new EngineeringStudent(101,"Arun","B.Tech IT","Java");
        Student s2=new ArtsStudent(102,"Priya","B.A English","Literature");
        s1.display(); System.out.println(); s2.display();
    }
}