import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static class Student {
        private int id; private String name, course;
        Student(int id,String name,String course){this.id=id;this.name=name;this.course=course;}
        int getId(){return id;} String getName(){return name;} String getCourse(){return course;}
        Object[] row(){return new Object[]{id,name,course};}
    }
    static class Books {
        ArrayList<String[]> list=new ArrayList<>();
        HashMap<Integer,String[]> map=new HashMap<>();
        void add(int id,String title,String author){String[] b={title,author}; list.add(new String[]{String.valueOf(id),title,author}); map.put(id,b);}
    }
    static class EngineeringStudent extends Student {
        EngineeringStudent(int id,String name,String course){super(id,name,course);}
        @Override Object[] row(){return new Object[]{getId(),getName(),getCourse()+" (Engineering)"};}
    }
    static class ArtsStudent extends Student {
        ArtsStudent(int id,String name,String course){super(id,name,course);}
        @Override Object[] row(){return new Object[]{getId(),getName(),getCourse()+" (Arts)"};}
    }
    static class Account {
        private String holder; private double balance;
        Account(String h,double b){holder=h;balance=b;}
        void deposit(double x){balance+=x;} boolean withdraw(double x){if(x<=balance){balance-=x;return true;}return false;}
        double getBalance(){return balance;}
    }
    static class Employee {
        String name; double basic;
        Employee(String n,double b){name=n;basic=b;}
        double salary(){return basic;}
    }
    static ArrayList<Student> students=new ArrayList<>();
    static Books books=new Books();
    static Account account=new Account("Student Account",1000);
    static AtomicInteger availableTickets=new AtomicInteger(10);
    static JTextArea output=new JTextArea();

    static JPanel panel(){JPanel p=new JPanel(new BorderLayout(10,10));p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));return p;}
    static JButton button(String s){return new JButton(s);}

    static JPanel studentsPanel(){
        JPanel p=panel(); DefaultTableModel m=new DefaultTableModel(new String[]{"ID","Name","Course"},0);
        JTable t=new JTable(m); JTextField id=new JTextField(),name=new JTextField(),course=new JTextField();
        JPanel form=new JPanel(new GridLayout(2,3,8,8)); form.add(new JLabel("Student ID"));form.add(new JLabel("Name"));form.add(new JLabel("Course"));
        form.add(id);form.add(name);form.add(course);
        JButton add=button("Add Student"), clear=button("Clear");
        add.addActionListener(e->{try{int i=Integer.parseInt(id.getText());if(name.getText().isBlank()||course.getText().isBlank())throw new IllegalArgumentException();
            Student s = course.getText().toLowerCase().contains("it") || course.getText().toLowerCase().contains("cse")
                ? new EngineeringStudent(i,name.getText(),course.getText())
                : new ArtsStudent(i,name.getText(),course.getText());
            students.add(s);m.addRow(s.row());id.setText("");name.setText("");course.setText("");
        }catch(Exception ex){JOptionPane.showMessageDialog(p,"Enter valid student details.");}});
        clear.addActionListener(e->{id.setText("");name.setText("");course.setText("");});
        JPanel top=new JPanel(new BorderLayout(8,8));top.add(form,BorderLayout.CENTER);JPanel b=new JPanel();b.add(add);b.add(clear);top.add(b,BorderLayout.SOUTH);
        p.add(top,BorderLayout.NORTH);p.add(new JScrollPane(t),BorderLayout.CENTER);return p;
    }

    static JPanel booksPanel(){
        JPanel p=panel(); DefaultTableModel m=new DefaultTableModel(new String[]{"Book ID","Title","Author"},0); JTable t=new JTable(m);
        JTextField id=new JTextField(),title=new JTextField(),author=new JTextField();
        JPanel f=new JPanel(new GridLayout(2,3,8,8));f.add(new JLabel("Book ID"));f.add(new JLabel("Title"));f.add(new JLabel("Author"));f.add(id);f.add(title);f.add(author);
        JButton add=button("Add Book"),search=button("Search ID"),delete=button("Delete ID");
        add.addActionListener(e->{try{int i=Integer.parseInt(id.getText());books.add(i,title.getText(),author.getText());m.addRow(new Object[]{i,title.getText(),author.getText()});id.setText("");title.setText("");author.setText("");}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter valid book details.");}});
        search.addActionListener(e->{try{int i=Integer.parseInt(id.getText());String[] b=books.map.get(i);JOptionPane.showMessageDialog(p,b==null?"Book not found":"Book: "+b[0]+"\nAuthor: "+b[1]);}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter a book ID.");}});
        delete.addActionListener(e->{try{int i=Integer.parseInt(id.getText());for(int r=0;r<m.getRowCount();r++)if(Integer.parseInt(m.getValueAt(r,0).toString())==i){m.removeRow(r);break;}books.map.remove(i);}catch(Exception x){}}); 
        JPanel top=new JPanel(new BorderLayout(8,8));top.add(f,BorderLayout.CENTER);JPanel b=new JPanel();b.add(add);b.add(search);b.add(delete);top.add(b,BorderLayout.SOUTH);
        p.add(top,BorderLayout.NORTH);p.add(new JScrollPane(t),BorderLayout.CENTER);return p;
    }

    static JPanel bankPanel(){
        JPanel p=panel(); JLabel bal=new JLabel("Balance: ₹"+String.format("%.2f",account.getBalance()),SwingConstants.CENTER);bal.setFont(new Font("Arial",Font.BOLD,22));
        JTextField amt=new JTextField();JButton dep=button("Deposit"),wit=button("Withdraw");
        dep.addActionListener(e->{try{account.deposit(Double.parseDouble(amt.getText()));bal.setText("Balance: ₹"+String.format("%.2f",account.getBalance()));}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter a valid amount.");}});
        wit.addActionListener(e->{try{if(!account.withdraw(Double.parseDouble(amt.getText())))JOptionPane.showMessageDialog(p,"Insufficient balance.");else bal.setText("Balance: ₹"+String.format("%.2f",account.getBalance()));}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter a valid amount.");}});
        JPanel c=new JPanel(new GridLayout(3,1,10,10));c.add(new JLabel("Amount"));c.add(amt);JPanel b=new JPanel();b.add(dep);b.add(wit);c.add(b);p.add(bal,BorderLayout.NORTH);p.add(c,BorderLayout.CENTER);return p;
    }

    static JPanel salaryPanel(){
        JPanel p=panel();JTextField name=new JTextField(),basic=new JTextField(),allow=new JTextField();JLabel result=new JLabel("Calculated salary: ₹0.00",SwingConstants.CENTER);
        JPanel f=new JPanel(new GridLayout(3,2,8,8));f.add(new JLabel("Employee Name"));f.add(name);f.add(new JLabel("Basic Salary"));f.add(basic);f.add(new JLabel("Allowance"));f.add(allow);
        JButton calc=button("Process Salary");calc.addActionListener(e->{try{Employee emp=new Employee(name.getText(),Double.parseDouble(basic.getText()));double total=emp.salary()+Double.parseDouble(allow.getText());result.setText("Calculated salary: ₹"+String.format("%.2f",total));}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter valid salary details.");}});
        p.add(f,BorderLayout.NORTH);p.add(calc,BorderLayout.CENTER);p.add(result,BorderLayout.SOUTH);return p;
    }

    static JPanel marksPanel(){
        JPanel p=panel();JTextField mark=new JTextField();JLabel result=new JLabel("Enter a mark from 0 to 100",SwingConstants.CENTER);JButton check=button("Validate Mark");
        check.addActionListener(e->{try{int x=Integer.parseInt(mark.getText());if(x<0||x>100)throw new IllegalArgumentException();result.setText(x>=40?"Valid Mark: PASS":"Valid Mark: FAIL");}catch(Exception ex){result.setText("Invalid mark. Use 0–100.");}});
        JPanel c=new JPanel(new GridLayout(3,1,10,10));c.add(new JLabel("Student Mark"));c.add(mark);c.add(check);p.add(c,BorderLayout.CENTER);p.add(result,BorderLayout.SOUTH);return p;
    }

    static JPanel recordsPanel(){
        JPanel p=panel();JTextField data=new JTextField("101,Barani,B.Tech IT");JTextArea area=new JTextArea();area.setEditable(false);
        JButton save=button("Save Record"),read=button("Read File");
        save.addActionListener(e->{try(FileWriter w=new FileWriter("student_records.txt")){w.write(data.getText()+"\n");area.setText("Record saved to student_records.txt");}catch(IOException x){area.setText("File error: "+x.getMessage());}});
        read.addActionListener(e->{try(FileReader r=new FileReader("student_records.txt")){StringBuilder s=new StringBuilder();int c;while((c=r.read())!=-1)s.append((char)c);area.setText(s.toString());}catch(IOException x){area.setText("No saved record found.");}});
        JPanel b=new JPanel();b.add(save);b.add(read);p.add(new JLabel("Student Record:"),BorderLayout.NORTH);p.add(data,BorderLayout.CENTER);p.add(b,BorderLayout.SOUTH);p.add(new JScrollPane(area),BorderLayout.EAST);return p;
    }

    static JPanel ticketPanel(){
        JPanel p=panel();JLabel left=new JLabel("Available tickets: "+availableTickets.get(),SwingConstants.CENTER);left.setFont(new Font("Arial",Font.BOLD,22));
        JTextField count=new JTextField();JButton book=button("Book Tickets (Synchronized)");
        book.addActionListener(e->{try{int n=Integer.parseInt(count.getText());Thread t=new Thread(()->{synchronized(availableTickets){if(n>0&&availableTickets.get()>=n)availableTickets.addAndGet(-n);}});t.start();t.join();left.setText("Available tickets: "+availableTickets.get());}catch(Exception x){JOptionPane.showMessageDialog(p,"Enter a valid ticket count.");}});
        JPanel c=new JPanel(new GridLayout(3,1,10,10));c.add(new JLabel("Tickets to book"));c.add(count);c.add(book);p.add(left,BorderLayout.NORTH);p.add(c,BorderLayout.CENTER);return p;
    }

    static JPanel threadPanel(){
        JPanel p=panel();JTextArea area=new JTextArea();area.setEditable(false);JButton start=button("Start Producer / Consumer");
        start.addActionListener(e->{area.setText("");Thread producer=new Thread(()->{for(int i=1;i<=5;i++){final int n=i;SwingUtilities.invokeLater(()->area.append("Producer produced: "+n+"\n"));try{Thread.sleep(250);}catch(Exception x){}}});
            Thread consumer=new Thread(()->{for(int i=1;i<=5;i++){final int n=i;SwingUtilities.invokeLater(()->area.append("Consumer received: "+n+"\n"));try{Thread.sleep(350);}catch(Exception x){}}});producer.start();consumer.start();});
        p.add(new JScrollPane(area),BorderLayout.CENTER);p.add(start,BorderLayout.SOUTH);return p;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            JFrame f=new JFrame("College Management Application");f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);f.setSize(950,650);f.setLocationRelativeTo(null);
            JLabel title=new JLabel("COLLEGE MANAGEMENT APPLICATION",SwingConstants.CENTER);title.setFont(new Font("Arial",Font.BOLD,24));title.setBorder(BorderFactory.createEmptyBorder(15,5,15,5));
            JTabbedPane tabs=new JTabbedPane();tabs.addTab("Students",studentsPanel());tabs.addTab("Books",booksPanel());tabs.addTab("Bank",bankPanel());tabs.addTab("Salary",salaryPanel());tabs.addTab("Marks",marksPanel());tabs.addTab("Records",recordsPanel());tabs.addTab("Tickets",ticketPanel());tabs.addTab("Threads",threadPanel());
            f.add(title,BorderLayout.NORTH);f.add(tabs,BorderLayout.CENTER);f.setVisible(true);
        });
    }
}