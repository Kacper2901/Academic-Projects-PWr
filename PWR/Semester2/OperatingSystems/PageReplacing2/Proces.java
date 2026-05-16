import java.util.ArrayList;

public class Proces {
    int id;
    int numPages;
    int allocatedFrames;
    int pageFaults;

    ArrayList<Integer> processFrames;

    public Proces(int id, int numPages) {
        this.id = id;
        this.numPages = numPages;
        this.allocatedFrames = 0;
        this.pageFaults = 0;
        this.processFrames = new ArrayList<>();
    }

    public void LRU(int pageId) {
        if (allocatedFrames == 0) return;

        if (!processFrames.contains(pageId)) {
            pageFaults++;
            if (processFrames.size() >= allocatedFrames) {
                processFrames.remove(0);
            }
            processFrames.add(pageId);
        } else {
            processFrames.remove((Integer) pageId);
            processFrames.add(pageId);
        }
    }
}