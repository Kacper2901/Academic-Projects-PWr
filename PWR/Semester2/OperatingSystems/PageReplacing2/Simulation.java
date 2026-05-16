import java.util.*;

class Simulation {
    public static final int PROCESS_QUANTITY = 20000;
    public static final int TOTAL_FRAMES = 50;
    int totalFrames;
    List<Proces> processes;
    List<Page> pageSequence;

    public Simulation(int totalFrames) {
        this.totalFrames = totalFrames;
        this.processes = new ArrayList<>();
        this.pageSequence = new ArrayList<>();
    }

    public void initializeSimulation() {
        Random rand = new Random();


        for (int i = 1; i <= 10; i++) {
            int pages;
            if (i <= 4) {
                pages = 10 + rand.nextInt(30); //process with a lot of pages
            } else {
                pages = 1 + rand.nextInt(5); //smaller
            }
            Proces p = new Proces(i, pages);
            processes.add(p);
        }

        //initialize process sequence
        for (int i = 0; i < PROCESS_QUANTITY; i++) {
            int procIndex;
            int r = rand.nextInt(100);

            if (r < 80) {
                procIndex = rand.nextInt(4);
            } else {
                procIndex = 4 + rand.nextInt(6);
            }

            Proces p = processes.get(procIndex);
            int pageId = (p.id * 100) + rand.nextInt(p.numPages);
            pageSequence.add(new Page(pageId, p.id));
        }
    }

    private void resetSimulation() {
        for (int i = 0; i < processes.size(); i++) {
            Proces p = processes.get(i);
            p.pageFaults = 0;
            p.processFrames.clear();
            p.allocatedFrames = 0;
        }
    }

    public void equalAllocation() {
        resetSimulation();
        int framesPerProcess = totalFrames / processes.size();

        for (int i = 0; i < processes.size(); i++) {
            Proces p = processes.get(i);
            p.allocatedFrames = framesPerProcess;
        }

        executeLRUforSequence();
        printResults("EQUAL ALLOCATION");
    }

    public void runProportionalAllocation() {
        resetSimulation();

        int totalPages = 0;
        for (int i = 0; i < processes.size(); i++) {
            totalPages += processes.get(i).numPages;
        }

        int allocatedPages = 0;
        for (int i = 0; i < processes.size(); i++) {
            Proces p = processes.get(i);
            double ratio = (double) p.numPages / totalPages;
            int frames = (int) (totalFrames * ratio);

            if (frames < 1) {
                frames = 1;
            }
            p.allocatedFrames = frames;
            allocatedPages += frames;
        }

        int remaining = totalFrames - allocatedPages;
        if (remaining > 0) {
            Proces largest = processes.get(0);
            for (int i = 1; i < processes.size(); i++) {
                if (processes.get(i).numPages > largest.numPages) {
                    largest = processes.get(i);
                }
            }
            largest.allocatedFrames += remaining;
        }

        System.out.println("--- PROPORTIONAL ALLOCATION TABLE ---");
        System.out.printf("%-15s | %-15s | %-15s\n", "Process ID", "Virtual Pages", "Allocated Frames");
        for (int i = 0; i < processes.size(); i++) {
            Proces p = processes.get(i);
            System.out.printf("%-15d | %-15d | %-15d\n", p.id, p.numPages, p.allocatedFrames);
        }
        System.out.println();

        executeLRUforSequence();
        printResults("PROPORTIONAL ALLOCATION");
    }

    public void pageFaultFrequency() {
        resetSimulation();
        int initialFramesRatio = totalFrames / processes.size();
        int freeFrames = totalFrames - (initialFramesRatio * processes.size());

        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).allocatedFrames = initialFramesRatio;
        }

        int[] lastFaults = new int[processes.size()]; //faults of every process

        for (int i = 0; i < pageSequence.size(); i++) {
            Page currSequencePage = pageSequence.get(i);
            Proces currProcess = processes.get(currSequencePage.processId - 1);

            currProcess.LRU(currSequencePage.id);

            if (i % 20 == 0 && i > 0) {
                for (int j = 0; j < processes.size(); j++) {
                    Proces currP = processes.get(j);
                    int faultsInWindow = currP.pageFaults - lastFaults[j];

                    if (faultsInWindow == 0 && currP.allocatedFrames > 2) {
                        currP.allocatedFrames--;
                        freeFrames++;
                    }
                }

                for (int j = 0; j < processes.size(); j++) {
                    Proces p = processes.get(j);
                    int faultsInWindow = p.pageFaults - lastFaults[j];

                    if (faultsInWindow > 3 && freeFrames > 0) {
                        p.allocatedFrames++;
                        freeFrames--;
                    }
                    lastFaults[j] = p.pageFaults;
                }
            }
        }
        printResults("PAGE FAULT FREQUENCY (PFF)");
    }

    public void zoneModel() {
        resetSimulation();

        int delta = (int) (TOTAL_FRAMES * 2);

        ArrayList<ArrayList<Integer>> history = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            history.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < pageSequence.size(); i++) {
            Page currPage = pageSequence.get(i);
            Proces p = processes.get(currPage.processId - 1);
            ArrayList<Integer> currWindow = history.get(p.id - 1);

            currWindow.add(currPage.id);
            if (currWindow.size() > delta) {
                currWindow.remove(0);
            }

            ArrayList<Integer> uniquePages = new ArrayList<>();
            for (int j = 0; j < currWindow.size(); j++) {
                int currPageInWindow = currWindow.get(j);
                boolean found = false;
                for (int k = 0; k < uniquePages.size(); k++) {
                    if (uniquePages.get(k) == currPageInWindow) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    uniquePages.add(currPageInWindow);
                }
            }

            int WSS = uniquePages.size();
            p.allocatedFrames = WSS;

            if (p.allocatedFrames < 1) {
                p.allocatedFrames = 1;
            }

            p.LRU(currPage.id);
        }
        printResults("ZONE MODEL (WORKING SET)");
    }

    private void executeLRUforSequence() {
        for (int i = 0; i < pageSequence.size(); i++) {
            Page currPage = pageSequence.get(i);
            Proces currProcess = processes.get(currPage.processId - 1);
            currProcess.LRU(currPage.id);
        }
    }

    private void printResults(String algorithmName) {
        System.out.println("--- RESULTS: " + algorithmName + " ---");

        int totalFaults = 0;
        for (int i = 0; i < processes.size(); i++) {
            Proces p = processes.get(i);
            totalFaults += p.pageFaults;
        }
        System.out.println("TOTAL GLOBAL PAGE FAULTS: " + totalFaults + "\n");
    }

}