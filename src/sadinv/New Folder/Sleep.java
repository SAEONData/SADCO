public class Sleep {

    public static void main(String[] args) {

        long stoptime = 2000; //2 Seconds
        System.out.println("Going to sleep...");
        try {
            Thread.sleep(stoptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Woke up again!");
    } // main
} // Sleep
