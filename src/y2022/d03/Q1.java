package y2022.d03;

import static java.lang.Character.isLowerCase;
import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Q1 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
		method3();
		method4();
		method5();
	}

	private static void method1() throws IOException {
		int t = 0;
		for (var line: Files.readAllLines(new File("src/y2022/d03/q1.txt").toPath())) {
			var s = new LinkedHashSet<Character>();
			int l = line.length(), h = l/2;
			//out.println(line + ": " + l + " " + h);
			for (int i=0; i<h; i+=1) {
				char ci = line.charAt(i);
				for (int j=h; j<l; j+=1) {
					char cj = line.charAt(j);
					if (ci != cj) continue;
					s.add(ci);
				}
			}
			//out.println(s);
			for (var c: s) t +=  isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}
	
	private static void method2() throws IOException {
		int t = 0;
		for (var line: Files.readAllLines(new File("src/y2022/d03/q1.txt").toPath())) {
			int h = line.length()/2;
			var s = toSet(line.substring(0, h));
			s.retainAll(toSet(line.substring(h)));
			for (var c: s) t +=  isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A');
		}
		out.println(t);
	}
	
	private static void method3() throws IOException {
		int t = 0;
		for (var line: Files.readAllLines(new File("src/y2022/d03/q1.txt").toPath())) {
			int h = line.length()/2;
			t += toSet(line.substring(0, h)).stream().filter(toSet(line.substring(h))::contains).mapToInt(c-> isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A')).sum();
		}
		out.println(t);
	}

	private static void method4() throws IOException {
		out.println(
			Files.readAllLines(new File("src/y2022/d03/q1.txt").toPath()).stream()
			.flatMap(
				line-> {
					int h = line.length()/2;
					return toSet(line.substring(0, h)).stream().filter(toSet(line.substring(h))::contains);
				}
			)
			.mapToInt(c-> isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A')).sum()
		);
	}

	private static Set<Character> toSet(String s) {
		return s.chars().mapToObj(c-> (char) c).collect(Collectors.toSet());
	}
	
	private static void method5() throws IOException {
		out.println(
			Files.readAllLines(new File("src/y2022/d03/q1.txt").toPath()).stream()
			.map(line-> { int h = line.length()/2; return new String[] { line.substring(0,h), line.substring(h) }; })
			.flatMap(s-> { return toSet(s[0]).stream().filter(toSet(s[1])::contains); }	)
			.mapToInt(c-> isLowerCase(c) ? (1 + c - 'a') : (27 + c - 'A'))
			.sum()
		);
	}

}
