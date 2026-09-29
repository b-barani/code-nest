class Employee {
    private int id; private String name; private double basic;
    Employee(int id,String name,double basic){this.id=id;this.name=name;this.basic=basic;}
    double calculateSalary(){return basic;}
    void display(){System.out.println("ID: "+id+" | Name: "+name+" | Salary: "+calculateSalary());}
}
class FullTimeEmployee extends Employee {
    private double allowance;
    FullTimeEmployee(int id,String name,double basic,double allowance){super(id,name,basic);this.allowance=allowance;}
    @Override double calculateSalary(){return super.calculateSalary()+allowance;}
}
class PartTimeEmployee extends Employee {
    private int hours; private double rate;
    PartTimeEmployee(int id,String name,double basic,int hours,double rate){
        super(id,name,basic);this.hours=hours;this.rate=rate;
    }
    @Override double calculateSalary(){return super.calculateSalary()+hours*rate;}
}
public class SalaryProcessingManagementSystem {
    public static void main(String[] args){
        System.out.println("=== Salary Processing Management System ===");
        Employee e1=new FullTimeEmployee(101,"Arun",30000,5000);
        Employee e2=new PartTimeEmployee(102,"Priya",15000,20,300);
        e1.display(); e2.display();
    }
}