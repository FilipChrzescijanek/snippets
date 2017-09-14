// http://ideone.com/9HsT4u

/* package whatever; // don't place package name! */
 
import java.util.*;
import java.lang.*;
import java.io.*;
 
/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println("" + new Node(0, 10, null).getMaxWeight());
	}
}
 
class Node {
	public int w;
	int v;
	Node p;
	List<Node> children = new ArrayList<>();
 
	Node(int w, int v, Node p) {
		this.w = w;
		this.v = v;
		this.p = p;
		if (getDepth() < 2) addChild(2, 0).addChild(4, 1).addChild(7, 3).addChild(8, 4);
		else if (getDepth() < 4) addChild(0, 0).addChild(2, 1).addChild(4, 3).addChild(6, 4).addChild(9, 5);
	}
 
	int getDepth() {
		return p == null ? 0 : 1 + p.getDepth();
	}
 
	Node addChild(int w, int d) {
		int nV = v - d;
		if (nV >= 0)
			children.add(new Node(w, nV, this));
		return this;
	}
 
	public int getMaxWeight() {
		return w + children.stream().mapToInt(n -> n.getMaxWeight()).max().orElse(0);
	}
 
}
