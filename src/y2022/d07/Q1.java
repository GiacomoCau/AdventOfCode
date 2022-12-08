package y2022.d07;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public class Q1 {
	
	static class Node {
		String name; Node parent; int size; Map<String, Node> children = new LinkedHashMap();
		Node(String name, Node parent) {
			this.name = name;
			this.parent = parent;
			if (parent == null) return; 
			parent.children.put(name, this);
		}
		@Override public String toString() {
			return name + " " + size + " " + (parent == null ? "null" : parent.name) + " " + children.values().stream().map(Node::toString).toList();
		}
	}
	
	public static void main(String[] args) throws Exception {
		method1();
		method2();
	}
	
	private static void method1() throws IOException {
		Node root = new Node("/", null), current = root;
		boolean ls = false;
		for (var line: Files.readAllLines(new File("src/y2022/d07/q1.txt").toPath())) {
			//out.println(line);
			var part = line.split(" ");
			if (part[0].equals("$")) {
				ls = false;
				switch (part[1]) {
					case "cd":
						switch (part[2]) {
							case "/": break;
							case "..": current = current.parent; break;
							default: current = current.children.get(part[2]); break;
						}
						break;
					case "ls":
						ls = true;
				}
			}
			else if (ls) {
				if (part[0].equals("dir")) {
					new Node(part[1], current);
				}
				else {
					current.size += Integer.parseInt(part[0]);
				}
			} 
		}
		t=0;
		size(root);
		out.println(t);
	}
	
	private static void method2() throws IOException {
		Node root = new Node("/", null), current = root;
		for (var line: Files.readAllLines(new File("src/y2022/d07/q1.txt").toPath())) {
			//out.println(line);
			var part = line.split(" ");
			switch (part[0]) {
				case "$":
					switch (part[1]) {
						case "cd":
							switch (part[2]) {
								case "/": break;
								case "..": current = current.parent; break;
								default: current = current.children.get(part[2]); break;
							}
							break;
						case "ls": break;
					}
					break;
				case "dir": new Node(part[1], current);	break;
				default: current.size += Integer.parseInt(part[0]);
			} 
		}
		t=0;
		size(root);
		out.println(t);
	}
	
	static int t = 0;
	private static int size(Node node) {
		int size = node.size + node.children.values().stream().mapToInt(Q1::size).sum();
		if (size < 100000) t += size;
		//out.println(node.name + " " + size);
		return size;
	}
}
