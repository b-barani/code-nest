class TicketCounter {
    private int available=3;
    public synchronized void book(String name,int count){
        System.out.println(name+" requested "+count+" ticket(s).");
        if(count<=available){
            available-=count;
            System.out.println("Booking successful for "+name);
            System.out.println("Tickets remaining: "+available);
        }else System.out.println("Booking failed for "+name+": Not enough tickets.");
        System.out.println();
    }
}
class Passenger extends Thread {
    private TicketCounter counter; private String name; private int count;
    Passenger(TicketCounter c,String name,int count){counter=c;this.name=name;this.count=count;}
    public void run(){counter.book(name,count);}
}
public class TicketBookingUsingMultithreadingSynchronization {
    public static void main(String[] args){
        System.out.println("=== Ticket Booking Using Multithreading & Synchronization ===");
        TicketCounter c=new TicketCounter();
        Thread p1=new Passenger(c,"Arun",2), p2=new Passenger(c,"Priya",2);
        p1.start(); p2.start();
        try{p1.join();p2.join();}catch(InterruptedException e){Thread.currentThread().interrupt();}
        System.out.println("Booking process completed.");
    }
}