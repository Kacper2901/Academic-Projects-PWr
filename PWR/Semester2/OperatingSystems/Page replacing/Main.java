public class Main {
    public static  int RAM_SIZE;
    public static  int DISK_SIZE = 1000;
    public static  int PAGE_REQUESTS_CAPACITY = 1000;
    public static int N = 30;

    public static void runTest(int ramSize, String text) {
        Main.RAM_SIZE = ramSize;
        Simulation sim = new Simulation();
        int fifoTotal = 0;
        int alruTotal = 0;
        int lruTotal = 0;
        int optTotal = 0;
        int randTotal = 0;
        for(int i = 0; i<N; i++){

            sim.setPageRequests();
            fifoTotal+=sim.FIFO();
            sim.ram.resetFrames();
            alruTotal += sim.ALRU();
            sim.ram.resetFrames();
            lruTotal += sim.LRU();
            sim.ram.resetFrames();
            optTotal += sim.OPT();
            sim.ram.resetFrames();
            randTotal += sim.RAND();
            sim.ram.resetFrames();
        }
        System.out.println(String.format("--- "+ text.toUpperCase() + " AVARAGE AFTER %d SIMULATIONS: RAM=%d ---",N, ramSize));
        System.out.printf("FIFO: %.2f%n", (double)fifoTotal/N);
        System.out.printf("ALRU: %.2f%n", (double)alruTotal/N);
        System.out.printf("LRU: %.2f%n" ,(double)lruTotal/N);
        System.out.printf("OPT: %.2f%n", (double)optTotal/N);
        System.out.printf("RAND: %.2f%n", (double)randTotal/N);
        System.out.println();
    }

    public static void main(String[] args) {
        runTest(14, "base case");
        runTest(7, "two times less ram");
        runTest(60, "four times more");


    }
}
