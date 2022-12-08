package y2022.d06;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.stream.IntStream;

public class Q2 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
		method3();
		method4();
	}

	private static void method1() throws IOException {
		for (var line: Files.readAllLines(new File("src/y2022/d06/q2.txt").toPath())) {
			for (int i=0, e=line.length()-14; i<e; i+=1) {
				var set = new LinkedHashSet<Character>();
				for (int j=0; j<14; j+=1) set.add(line.charAt(i+j));
				//out.println(set);
				if (set.size() != 14) continue;
				out.println(i+14);
				break;
			};
		}
	}
	
	private static void method2() throws IOException {
		int len = 14;
		for (var line: Files.readAllLines(new File("src/y2022/d06/q2.txt").toPath())) {
			for (int i=0, e=line.length()-len; i<e; i+=1) {
				if (line.substring(i, i+len).chars().distinct().count() != len) continue; 
				out.println(i+len);
				break;
			};
		}
	}
	
	private static void method3() throws IOException {
		var len = 14;
		for (var line: Files.readAllLines(new File("src/y2022/d06/q2.txt").toPath())) {
			out.println(IntStream.range(0, line.length()-len).filter(i-> line.substring(i, i+len).chars().distinct().count() == len).findFirst().getAsInt()+len);
		}
	}
	
	private static void method4() throws IOException {
		var len = 14;
		for (var line: Files.readAllLines(new File("src/y2022/d06/q2.txt").toPath())) {
			out.println(IntStream.range(len, line.length()).filter(i-> line.substring(i-len, i).chars().distinct().count() == len).findFirst().getAsInt());
		}
	}
}
