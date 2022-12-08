package y2022.d07;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Q2 {
	
	static class Node extends LinkedHashMap<String, Node> {
		private static final long serialVersionUID = 1L;
		String name; Node parent; int size;
		Node(String name, Node parent) {
			this.name = name;
			this.parent = parent;
			if (parent == null) return; 
			parent.put(name, this);
		}
		@Override public String toString() {
			return name + " " + size + " " + (parent == null ? "null" : parent.name) + " " + values().stream().map(Node::toString).toList();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Node root = tree();
		sizes.clear();
		int min = /*30_000_000 - 70_000_000 +*/ size(root) - 40_000_000 ; 
		out.println(sizes.stream().mapToInt(i->i).filter(s-> s >= min).min().getAsInt());
	}
	
	private static Node tree() throws IOException {
		Node root = new Node("/", null), current = root; 
		for (var line: Files.readAllLines(new File("src/y2022/d07/q1.txt").toPath())) {
			//out.println(line);
			var part = line.split(" ");
			switch (part[0]) {
				case "$": switch (part[1]) {
						case "cd":
							switch (part[2]) {
								case "/": break;
								case "..": current = current.parent; break;
								default: current = current.get(part[2]); break;
							}
						case "ls": break;
					}
					break;
				case "dir": new Node(part[1], current);	break;
				default: current.size += Integer.parseInt(part[0]);
			} 
		}
		return root;
	}
	
	static List<Integer> sizes = new ArrayList();
	private static int size(Node node) {
		int size = node.size + node.values().stream().mapToInt(Q2::size).sum();
		sizes.add(size);
		//out.println(node.name + " " + size);
		return size;
	}
}
