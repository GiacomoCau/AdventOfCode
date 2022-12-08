package y2022.d01;

import static java.lang.System.out;
import static java.util.Arrays.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;

public class Q2 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
	}

	private static void method1() throws IOException {
		int val=0;  var list = new ArrayList<Integer>();
		for (var line: Files.readAllLines(new File("src/y2022/d01/q1.txt").toPath())) {
			if (!line.isEmpty())
				val += Integer.parseInt(line);
			else {
				list.add(val);
				val = 0;
			}
		};
		out.println(list.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(i->i).sum());
	}
	
	private static void method2() throws IOException {
		String file = Files.readString(new File("src/y2022/d01/q1.txt").toPath());
		out.println(stream(file.split("\n\n")).map(s-> stream(s.split("\n")).mapToInt(Integer::parseInt).sum()).sorted(Comparator.reverseOrder()).limit(3).mapToInt(i->i).sum());
	}

}
