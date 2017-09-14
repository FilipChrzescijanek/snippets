// http://ideone.com/RLPX3J
// http://ideone.com/WYKflE

/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Node n4 = new Node("n4");
		
		Node n3 = new Node("n3", n4);
		Node n5 = new Node("n5");
		Node n6 = new Node("n6");
		
		Node n1 = new Node("n1", n3);
		Node n2 = new Node("n2", n5, n6);
		
		Node root = new Node("root", n1, n2);
		
		root.breadthFirstSearch();
		root.depthFirstSearch();
		root.removeBranchesWithout(Arrays.asList("n1", "n5"));
		root.depthFirstSearch();
	}
}

class Node
{
	private String data;
	private List<Node> children = new ArrayList<>();
	private Node parent;

	public Node(String data, Node... children) {
		this.data = data;
		this.children.addAll(Arrays.asList(children));
		for (Node n : this.children) {
			n.parent = this;
		}
	}

	public void removeBranchesWithout(List<String> names) {
		if (names.isEmpty()) return;
		List<Node> toRemove = new ArrayList<>();
		for (Node n : children) {
			if (n.removeBranch(names)) {
				toRemove.add(n);
			} else if (!names.contains(n.data)) {
				n.removeBranchesWithout(names);
			}
		}
		children.removeAll(toRemove);
	}
	
	private boolean removeBranch(List<String> names) {
		return !names.contains(data) 
		&& children.stream().allMatch(n -> n.removeBranch(names));
	}
	
	public void breadthFirstSearch() {
		Queue<Node> queue = new LinkedList<>();
	    queue.add(this);
	    while(!queue.isEmpty()){
	        Node node = queue.remove();
	        System.out.println(String.join("", Collections.nCopies(node.depth(), "\t")) + node.data);
	        queue.addAll(node.children);
	    }
	}
	
	public void depthFirstSearch() {
		Stack<Node> stack = new Stack<>();
	    stack.add(this);
	    while(!stack.isEmpty()){
	        Node node = stack.pop();
	        System.out.println(String.join("", Collections.nCopies(node.depth(), "\t")) + node.data);
	        for (int i = node.children.size(); i > 0; --i) {
	        	stack.add(node.children.get(i - 1));
	        }
	    }
	}
	
	public int depth() {
		return parent == null ? 0 : parent.depth() + 1;
	}
	
}
