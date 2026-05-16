package dsaa.lab08;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private int size;
	private class Node{
		T value;
		Node left,right,parent;
		public Node(T v) {
			value=v;
		}
		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}		
	private Node root=null;

	public BST() {
        this.size = 0;
	}

	public T getElement(T toFind) {
        Node n = findNode(toFind);
        if(n == null) return null;
        return n.value;
	}

	public T successor(T elem) {
        Node n = findNode(elem);
        if (n == null) return null;

        if (n.right != null) {
            Node successor = n.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            return successor.value;
        }
        return null;
	}


	public String toStringInOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root,sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 2);
        return sb.toString();
	}

	public String toStringPreOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(root,sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 2);
        return sb.toString();
	}

    public String toStringPostOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(root,sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public void preOrder(Node curr, StringBuilder sb){
        if(curr == null) return;
        sb.append(curr.value).append(", ");
        if(curr.left != null){
            preOrder(curr.left, sb);
        }
        if(curr.right != null){
            preOrder(curr.right, sb);
        }
    }

    public void postOrder(Node curr, StringBuilder sb){
        if(curr == null) return;
        if(curr.left != null){
            postOrder(curr.left, sb);
        }
        if(curr.right != null){
            postOrder(curr.right, sb);
        }
        sb.append(curr.value).append(", ");

    }

    public void inOrder(Node curr, StringBuilder sb){
        if(curr == null) return;
        if(curr.left != null){
            inOrder(curr.left, sb);
        }
        sb.append(curr.value).append(", ");
        if(curr.right != null){
            inOrder(curr.right, sb);
        }
    }






	public boolean add(T elem) {
        if(elem == null) return false;
		Node curr = root;
        if(curr == null){
            root = new Node(elem);
            size++;
            return true;
        }
        Comparable<T> e = (Comparable<T>)elem;

        while(true){
            if(e.compareTo(curr.value) == 0) return false;
            if(e.compareTo(curr.value) <= 0){
                if(curr.left != null){
                    curr = curr.left;
                    continue;
                }
                curr.left = new Node(elem);
                curr.left.parent = curr;
                size++;
                return true;
            }
            else {
                if(curr.right != null){
                    curr = curr.right;
                    continue;
                }
                curr.right = new Node(elem);
                curr.right.parent = curr;
                size++;
                return true;
            }
        }
	}


	public T remove(T value) {
        Node nodeToRemove = findNode(value);
        if(nodeToRemove == null) return null;
        T removeVal = nodeToRemove.value;
        removeNode(nodeToRemove);
        return removeVal;

	}

    private Node findNode(T value){
        if(root == null) return null;
        Comparable<T> val = (Comparable<T>) value;
        Node curr = root;

        while(curr != null){
            int cmp = val.compareTo(curr.value);
            if(cmp==0) return curr;
            if(cmp < 0) curr = curr.left;
            else curr = curr.right;
        }
        return curr;
    }

    private void removeNode(Node n) {
        if (n == null) return;

        if (n.left != null && n.right != null) {
            Node successor = n.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            n.value = successor.value;
            removeNode(successor);
            return;
        }


        Node child = (n.left != null) ? n.left : n.right;
        Comparable<T> val = (Comparable<T>) n.value;
        if(n == root) {
            root = child;
            if (root != null) root.parent = null;
            size--;
        }
        else {
            if(n == n.parent.left){
                n.parent.left = child;
            }
            else {
                n.parent.right = child;
            }

            if(child != null) {
                child.parent = n.parent;
            }
            size--;
        }
    }
	
	public void clear() {
		root = null;
        size = 0;
	}

	public int size() {
		return this.size;
	}

    public int twoChildren(){
        return twoChildren(root);
    }

    private int twoChildren(Node root){
        int res = 0;
        if(root == null) return res;
        Node curr = root;
        if(curr.left != null && curr.right != null) res++;
        if(curr.right != null) res+=twoChildren(curr.right);
        if(curr.left != null) res+=twoChildren(curr.left);
        return res;
    }

}
