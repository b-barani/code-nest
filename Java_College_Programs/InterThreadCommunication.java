class SharedData {
    private int value; private boolean available=false;
    synchronized void produce(int value){
        while(available) try{wait();}catch(InterruptedException e){Thread.currentThread().interrupt();return;}
        this.value=value; available=true; System.out.println("Producer produced: "+value); notify();
    }
    synchronized void consume(){
        while(!available) try{wait();}catch(InterruptedException e){Thread.currentThread().interrupt();return;}
        System.out.println("Consumer consumed: "+value); available=false; notify();
    }
}
class Producer extends Thread {
    private SharedData data; Producer(SharedData d){data=d;}
    public void run(){for(int i=1;i<=5;i++)data.produce(i);}
}
class Consumer extends Thread {
    private SharedData data; Consumer(SharedData d){data=d;}
    public void run(){for(int i=1;i<=5;i++)data.consume();}
}
public class InterThreadCommunication {
    public static void main(String[] args){
        System.out.println("=== Inter-Thread Communication ===");
        SharedData d=new SharedData(); Thread p=new Producer(d), c=new Consumer(d);
        p.start();c.start();
        try{p.join();c.join();}catch(InterruptedException e){Thread.currentThread().interrupt();}
        System.out.println("Communication completed.");
    }
}