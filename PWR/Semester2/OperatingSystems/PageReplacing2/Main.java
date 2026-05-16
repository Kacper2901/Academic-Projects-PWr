public class Main {
    public static void main(String[] args) {
        System.out.println("TOTAL FRAMES: " + Simulation.TOTAL_FRAMES);
        System.out.println("SEQUENCE LENGTH: " + Simulation.PROCESS_QUANTITY);
        System.out.println("--------------------------------------------------\n");

        Simulation sim = new Simulation(Simulation.TOTAL_FRAMES);
        sim.initializeSimulation();

        sim.equalAllocation();
        sim.runProportionalAllocation();
        sim.pageFaultFrequency();
        sim.zoneModel();
    }
}