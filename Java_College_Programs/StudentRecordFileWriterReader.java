import java.io.*;
public class StudentRecordFileWriterReader {
    public static void main(String[] args){
        String file="student_records.txt";
        try{
            FileWriter w=new FileWriter(file);
            w.write("101, Arun, B.Tech IT, 85\n");
            w.write("102, Priya, B.Tech CSE, 91\n");
            w.write("103, Kumar, B.Sc CS, 78\n");
            w.close();
            System.out.println("Records written successfully.\n");
            FileReader r=new FileReader(file);
            System.out.println("=== Student Records ===");
            int ch; while((ch=r.read())!=-1) System.out.print((char)ch);
            r.close();
        }catch(IOException e){System.out.println("File Error: "+e.getMessage());}
    }
}