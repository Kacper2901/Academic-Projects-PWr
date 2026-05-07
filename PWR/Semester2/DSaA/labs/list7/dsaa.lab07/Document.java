package dsaa.lab07;

import java.util.ListIterator;
import java.util.Scanner;

public class Document implements IWithName{
	private static final int MODVALUE=100000000;
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name) {
        this.name=name.toLowerCase();
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String line = scan.next();
            if (line.equals("eod")) break;
            if (line.toLowerCase().startsWith("link=")) {
                Link newLink = createLink(line.substring(5));
                if (newLink != null) {
                    link.add(newLink);
                }
            }
        }
    }
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)


    public static boolean isCorrectId(String id) {
        if (id == null || id.length() == 0) return false;
        if (!Character.isLetter(id.charAt(0))) {
            return false;
        }

        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_'){
                return false;
            }
        }
        return true;
    }

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        //TODO

        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');
        String id;
        int weight = 1;

        if (openBracket != -1 && closeBracket != -1 && closeBracket > openBracket) {
            id = link.substring(0, openBracket).toLowerCase();
            if(closeBracket != link.length() - 1) return null;
            try {
                weight = Integer.parseInt(link.substring(openBracket + 1, closeBracket));
                if(weight <=0 ) return null;
            } catch (NumberFormatException e) {
                return null;
            }
        } else if(openBracket == -1 && closeBracket == -1){
            id = link.toLowerCase();
        }
        else return null;

        if (isCorrectId(id) ) {
            return new Link(id, weight);
        }
        return null;
    }

	@Override
    public String toString() {
        String retStr = "Document: " + name;
        if (!link.isEmpty()) {
            retStr += "\n";
            for (int i = 0; i < link.size(); i++) {
                retStr += link.get(i).toString();
                if (i < link.size() - 1) {
                    retStr += " ";
                }
            }
        }
        return retStr;
    }

	public String toStringReverse() {
        String retStr = "Document: " + name;
        if (!link.isEmpty()) {
            retStr += "\n";
            ListIterator<Link> iter = link.listIterator();

            while(iter.hasNext()) iter.next();

            while(iter.hasPrevious()) {
                retStr += iter.previous().toString();
                if (iter.hasPrevious()) {
                    retStr += " ";
                }
            }
        }
        return retStr;
	}

	@Override
	public String getName() {
		return name;
	}

    @Override
    public boolean equals(Object doc){
        Document other = (Document) doc;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode(){
        int[] sequence = {7,11,13, 17, 19};
        char[] charArray = this.name.toCharArray();
        if(charArray.length == 0) return 0;
        int h = charArray[0];

        for(int i = 1; i < charArray.length; i++){
            int sequenceNext = sequence[(i-1)%sequence.length];
            h = ((h * sequenceNext) % MODVALUE + charArray[i]) % MODVALUE;
        }

        return h;
    }



}

