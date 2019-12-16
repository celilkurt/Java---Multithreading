


package yazlab2;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class YazLab2 {
    //main thread = Thread.currentThread()
    public static window w1;
    public static int limit = 10000;
    private static int rndLimit = 100;
    public static int count = 0;
    public static ArrayList<childThread> childList ;
    private static Random random = new Random();
    private static int meter = 0;
    private static controlThread cThread;
    /*
    -Main thread içinde rastgele sayı üretilecek ve count değişkenine atılacak
    -Main içerisinde bu count değişkenine değeri düzenli olarak azaltılacak
        -Main içerisinde oluşturulacak while döngüsü içinde rastgele sayı oluşturulup count'a eklenecek ve count'ın düzenli azaltması yapılacak
    -Main içerisinde iki alt sunucu oluşturulacak ve sunucu yoğunluğunu kontrol edecek, alt sunucuları kuyruklara ekleyecek thread'i oluşturacak
    -Alt sunucular count değişkeniden iş alıp kendi count değişkenlerine ekliyecekler.
    -Alt sunucular belli aralıklarla count değerini azaltacak
    */
    public static void main(String[] args) {
        childList = new ArrayList<>();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        
        start();//İki alt sunucuyu ve kontrol sunucusunu başlatır.
        increase();
        
        while(true){
            meter++;
            
            if(count < limit){
                
                if(meter%2==0){
                    decrease();
                    if(meter%5==0){
                        increase();
                    }
                }else if(meter%5==0){
                    increase();
                }
                
            }else{
                cThread.key=false;
                for (childThread tempThread : childList){
                    tempThread.key = false;//thread'ın durmasını sağlayacak.
                }
                break;
            }
            
            setProgressBar(getStatus());
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(YazLab2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public static int getStatus(){
        return (100*count)/limit;
    }
    
    public static void start(){
        w1 = new window();
        w1.setVisible(true);
        childThread R1 = new childThread();
        childList.add(R1);
        childThread R2 = new childThread();
        childList.add(R2);
        cThread = new controlThread();
        
    }
    
    public static void decrease(){//count'u azaltır.
        int rndNumber =  random.nextInt(rndLimit/2) + 1;
        if(count >= rndNumber)
            count -= rndNumber;
        else
            count = 0;
                
        System.out.println("Main-thread pro count: " + count);
   }
   
   public static void increase(){//count'u arttırır.
        int rndNumber =  random.nextInt(500) + 1;
        count += rndNumber;
        System.out.println("Main-thread pro count: " + count);
   }
   
   public static void setProgressBar(int progress){
       
        w1.barList.get(w1.barList.size()-1).setValue(progress);
        w1.barList.get(w1.barList.size()-1).setStringPainted(true);
       
    }
   
    
}

class childThread implements Runnable {
   private Thread t;
   public int count;
   private int limit = 5000;
   private int rndLimit = 100;
   public boolean key;//True olması yeni iş alabilmesi anlamına gelecek.
   private Random random = new Random();
   private int meter = 0;
   childThread() {
      count = 0;
      key = true;
      start();
      System.out.println("Creating " +  t.getName() );
      
   }
   
   childThread(int count) {
      this.count = count;
      key = true;
      start();
      System.out.println("Creating " +  t.getName() );
      
   }
   
   public int getStatus(){//Yüzde kaçının dolu olduğunu döndürür.
       //  count/limit = status/100
       return (100*count)/limit;
   }
   
   @Override
   public void run() {//50 ms de bir count artırılıyor, 20 ms de bir azaltılıyor.
        System.out.println("Running " +  t.getName() );
        increase();
        while(key){//Ana sunucudan iş alınması ve iş sayısının zamana bağlı azaltılması.
            meter++;
            //System.out.println(t.getName() + " meter:" + meter);
            if(meter%3 == 0){
                
                if(meter%5==0){
                    increase();
                }
                decrease();
                
            }else if(meter%5==0){
                increase();
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(YazLab2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(t.getName() + " pro count: " + count);
        }
      
      
   }
   
   public void start () {
       String name = "thread-" + YazLab2.childList.size();
      System.out.println("Starting " +  name );
      if (t == null) {
         t = new Thread (this, name);
         t.start ();//run fonk. çalıştırır.
      }
   }
   
   public void decrease(){//count'u azaltır.
       int rndNumber =  random.nextInt(rndLimit/2) + 1;
       if(count >= rndNumber){
            count -= rndNumber;
            System.out.println(t.getName() + " pro count: " + count);
        }else{
            count = 0;
            System.out.println(t.getName() + " pro count: " + count);
        }
   }
   
   public void increase(){//count'u arttırır.
       int rndNumber =  random.nextInt(150) + 1;
      
       if(YazLab2.count >= rndNumber){
            count += rndNumber;
            YazLab2.count -= rndNumber;
            System.out.println(t.getName() + " pro count: " + count);
        }else{
            count += YazLab2.count;
            YazLab2.count = 0;
            System.out.println(t.getName() + " pro count: " + count);
        }
   }
   
   public void setPriority(int num){
       if(num <= 10 && num >= 1)
        t.setPriority(num);
   }
}

class controlThread implements Runnable{
    private Thread t;
    public boolean key;
    private window w2;
    
    controlThread(){
        w2 = YazLab2.w1;
        key = true;
        t = new Thread (this, "control");
        t.start ();//run fonk. çalıştırır.
        System.out.println("Starting control thread");
           
    }
    
    @Override
    public void run() {
        t.setPriority(10);
       
        while(key){
            
           for (int i = 0; i < YazLab2.childList.size(); i++) 
            { //Kontrol edilen sunucu işlem süresince iş alması engellenebilir böylece %0 iken %0 kalması garantilenir öbür türlü 
              //%0 olan silinene iş alabilir.
                
                childThread tempThread = YazLab2.childList.get(i);
                
                synchronized(tempThread) {
                    if(tempThread.getStatus() >= 70){

                        childThread newThread = new childThread(tempThread.count/2);
                        YazLab2.childList.add(newThread);
                        tempThread.count /= 2;

                    }else if(tempThread.getStatus() == 0 && YazLab2.childList.size() >= 3){

                        w2.barList.get(YazLab2.childList.size()-1).setValue(0);
                        w2.barList.get(YazLab2.childList.size()-1).setStringPainted(false);

                        tempThread.key = false;
                        YazLab2.childList.remove(tempThread);
                        i--;
                        //YazLab2.childList.remove(tempThread);
                    }/**/else if(tempThread.getStatus() <= 15){
                        tempThread.setPriority(1);
                    }else if(tempThread.getStatus() >= 30){
                        tempThread.setPriority(9);
                    }
                 }
                
                
            }
           
           setProgressBar();
           
           /////UPDATE STATUS BAR///////
            //w1.progressBarUpdate(YazLab2.childList.get(0).getStatus());
          
        }   
    }
    
    public void setProgressBar(){
        
        for(int i = 0; i < YazLab2.childList.size() ;i++){
        
            w2.barList.get(i).setValue(YazLab2.childList.get(i).getStatus());
            w2.barList.get(i).setStringPainted(true);
            
        }
    }
    
}


