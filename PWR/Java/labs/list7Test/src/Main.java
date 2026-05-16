import java.io.FileWriter;
import java.io.IOException;

StringBuilder makeRandomString(int k){
    Random rand = new Random();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < k; i++){
        char letter =(char)(int)(rand.nextDouble()*26 + 97);
        sb.append(letter);
    }
    return sb;
}

void main() throws IOException{
    final int m = 1_000_000;
    final int k = 10;

    StringBuilder[] str = new StringBuilder[m];

    for (int i = 0; i < m; i++){
        str[i] = makeRandomString(k);
    }

    FileWriter w = new FileWriter("test.txt");

    for(int i = 0; i < m; i++){
        StringBuilder currString = str[i];
        w.write("ld " + currString.toString() + "\n");
        w.write("eod" + "\n");
    }

    for(int i = 0; i < m; i++){
        StringBuilder currString = str[i];
        w.write("getdoc " + currString.toString() + "\n");
        w.write("show" + "\n");
    }

    for(int i = 0; i < m; i++){
        String s = makeRandomString(k+1).toString();
        w.write("getdoc " + s + "\n");
        w.write("show" + "\n");
    }

    w.write("show\n");
    w.write("ht\n");
    w.write("maxLen\n");
    w.write("ha\n");
    w.close();


}
