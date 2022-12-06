package y2022.d03;

import static java.lang.Character.isLowerCase;
import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Q2 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
		method3();
		method4();
		method5();
	}

	private static void method1() throws IOException {
		int t = 0;
		String[] lines = Files.readAllLines(new File("src/y2022/d03/Q1.txt").toPath()).toArray(String[]::new);
		for (int i=0; i<lines.length; i+=3) {
			var s = set(lines[i]); s.retainAll(set(lines[i+1])); s.retainAll(set(lines[i+2]));
			for (var c: s) t +=  isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}
	
	private static Set<Character> set(String s) {
		return s.chars().mapToObj(chr-> (char) chr).collect(Collectors.toSet());
	}
	
	private static void method2() throws IOException {
		int t = 0;
		var lines = Files.readAllLines(new File("src/y2022/d03/Q1.txt").toPath());
		for (int i=0; i<lines.size(); i+=3) {
			var s = set(lines, i); s.retainAll(set(lines, i+1)); s.retainAll(set(lines, i+2));
			for (var c: s) t +=  isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}

	private static Set<Character> set(List<String> lines, int i) {
		return lines.get(i).chars().mapToObj(chr-> (char) chr).collect(Collectors.toSet());
	}	

	private static void method3() throws IOException {
		int t = 0;
		var lines = Files.readAllLines(new File("src/y2022/d03/Q1.txt").toPath());
		for (int i=0; i<lines.size(); i+=3) {
			var s = interset(set(lines, i), set(lines, i+1), set(lines, i+2)); 
			for (var c: s) t +=  isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}
	
	private static Set<Character> interset(Set<Character> set, Set<Character> ... sets) {
		for (var setn: sets) set.retainAll(setn); return set;
	}
	
	private static void method4() throws IOException {
		int t = 0;
		var lines = Files.readAllLines(new File("src/y2022/d03/Q1.txt").toPath());
		for (int i=0; i<lines.size(); i+=3)
			t += interset(set(lines, i), set(lines, i+1), set(lines, i+2)).stream().mapToInt(c-> isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A')).sum(); 
		out.println(t);
	}

	private static void method5() throws IOException {
		int t = 0;
		var lines = Files.readAllLines(new File("src/y2022/d03/Q1.txt").toPath());
		for (int i=0; i<lines.size(); i+=3) {
			var s = set(lines, i); for (int j=1; j<3; j+=1) s.retainAll(set(lines, i+j)); 
			for (var c: s) t += isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}
}
