package y2022.d07;

import static java.lang.System.out;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Q21 {
	
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
		int min = size(root) /* 30_000_000 - 70_000_000 = */ - 40_000_000; 
		out.println(nodes(root).mapToInt(n-> n.size).filter(s-> s >= min).min().getAsInt());
		out.println(nodesSize(root).filter(s-> s >= min).min().getAsInt());
		out.println(nodesToInt(root, n-> n.size).filter(s-> s >= min).min().getAsInt());
	}
	
	private static Node tree() throws IOException {
		Node root = new Node("/", null), current = root; 
		for (var line: Files.readAllLines(new File("src/y2022/d07/p1.txt").toPath())) {
			//out.println(line);
			var part = line.split(" ");
			switch (part[0]) {
				case "$":
					switch (part[1]) {
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
	
	private static int size(Node node) {
		return node.size += node.values().stream().mapToInt(Q21::size).sum();
	}
	
	static Stream<Node> nodes(Node node) {
	    return concat(of(node), node.values().stream().flatMap(Q21::nodes));
	}
	static IntStream nodesSize(Node node) {
	    return IntStream.concat(IntStream.of(node.size), node.values().stream().flatMapToInt(Q21::nodesSize));
	}
	static IntStream nodesToInt(Node node, Function<Node,Integer> f) {
	    return IntStream.concat(IntStream.of(f.apply(node)), node.values().stream().flatMapToInt(n-> nodesToInt(n, f)));
	}
}
