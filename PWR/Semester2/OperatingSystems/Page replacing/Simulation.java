import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    RAM ram;
    ArrayList<Page> pageRequests;
    Random rand;



    Simulation(){
        ram = new RAM();
        pageRequests = new ArrayList<>(Main.PAGE_REQUESTS_CAPACITY);
        rand = new Random();
    }

     int randomNumberFromInterval(int l, int r){
        return (int)(rand.nextDouble()*(r + 1 -l)) + l;
    }

    void setPageRequests(){
        pageRequests.clear();
        int firstPageId = 0;
        int localityRadius = 5;
        int pahseLength = 200;
        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            if(i%pahseLength == 0){

                firstPageId = randomNumberFromInterval(localityRadius, Main.DISK_SIZE - 1 - localityRadius);
                addPageRequest(firstPageId);
                continue;
            }

            int closePageId = randomNumberFromInterval(firstPageId - localityRadius, firstPageId + localityRadius);
            firstPageId += randomNumberFromInterval(-3, 3);
            addPageRequest(closePageId);
        }
    }

    void addPageRequest(int id){
        pageRequests.add(new Page(id));
    }

    int FIFO(){
        int pageFaults = 0;
        int currFrameId = -1;
        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            Page pageRequest = pageRequests.get(i);
            int pageRequestIdxInFrames = ram.isPageInFrames(pageRequest);
            if(pageRequestIdxInFrames == -1){
                currFrameId = (currFrameId + 1) % Main.RAM_SIZE;
                pageFaults++;
                ram.frames.set(currFrameId, new Page(pageRequest.pageId));            }
        }

        return pageFaults;
    }

    int ALRU(){
        int pageFaults = 0;
        int currFrameId = -1;
        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            Page pageRequest = pageRequests.get(i);
            int pageRequestIdxInFrames = ram.isPageInFrames(pageRequest);
            if(pageRequestIdxInFrames == -1){
                currFrameId = (currFrameId + 1) % Main.RAM_SIZE;
                pageFaults++;
                Page pageToReplace = ram.frames.get(currFrameId);
                while (pageToReplace.bit != 0){
                    pageToReplace.bit = 0;
                    currFrameId = (currFrameId + 1) % Main.RAM_SIZE;
                    pageToReplace = ram.frames.get(currFrameId);
                }
                Page newPage = new Page(pageRequest.pageId);
                newPage.bit = 1;
                ram.frames.set(currFrameId, newPage);          }
            else{
                ram.frames.get(pageRequestIdxInFrames).bit = 1;
            }
        }

        return pageFaults;
    }

    int LRU(){
        int pageFaults = 0;
        int freeFramesQuantity = Main.RAM_SIZE;
        ram.frames.clear();
        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            Page pageRequest = pageRequests.get(i);
            int pageRequestIdxInFrames = ram.isPageInFrames(pageRequest);
            if(pageRequestIdxInFrames == -1){
                if(freeFramesQuantity == 0) ram.frames.remove(0);
                else freeFramesQuantity --;
                ram.frames.add(new Page(pageRequest.pageId));
                pageFaults++;
            }
            else{
                ram.frames.remove(pageRequestIdxInFrames);
                ram.frames.add(new Page(pageRequest.pageId));
            }
        }

        return pageFaults;
    }

    int OPT(){
        int pageFaults = 0;
        ram.frames.clear();

        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            Page pageToAdd = pageRequests.get(i);
            int isPageInFrames = ram.isPageInFrames(pageToAdd);

            if(isPageInFrames == -1){
                if(ram.frames.size() < Main.RAM_SIZE){
                    ram.frames.add(new Page(pageToAdd.pageId));
                } else {
                    int idxToReplace = -1;
                    int farthestNextUse = -1;

                    for(int j = 0; j < ram.frames.size(); j++){
                        int currentPageId = ram.frames.get(j).pageId;
                        int nextUse = Integer.MAX_VALUE;

                        for(int k = i + 1; k < Main.PAGE_REQUESTS_CAPACITY; k++){
                            if(pageRequests.get(k).pageId == currentPageId){
                                nextUse = k;
                                break;
                            }
                        }

                        if(nextUse > farthestNextUse){
                            farthestNextUse = nextUse;
                            idxToReplace = j;
                        }
                    }

                    ram.frames.set(idxToReplace, new Page(pageToAdd.pageId));
                }

                pageFaults++;
            }
        }

        return pageFaults;
    }

    int RAND(){
        int pageFaults = 0;
        ram.frames.clear();

        for(int i = 0; i < Main.PAGE_REQUESTS_CAPACITY; i++){
            Page pageToAdd = pageRequests.get(i);
            int isPageInFrames = ram.isPageInFrames(pageToAdd);
            if(isPageInFrames == -1){
                if(ram.frames.size() == Main.RAM_SIZE) {
                    int idxToReplace = randomNumberFromInterval(0, Main.RAM_SIZE - 1);
                    ram.frames.set(idxToReplace, new Page(pageToAdd.pageId));                }
                else {
                    ram.frames.add(new Page(pageToAdd.pageId));
                }

                pageFaults++;
            }
        }
        return pageFaults;
    }
}
