package y2022.d01;

import static java.lang.System.out;
import static java.util.Arrays.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Q1 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
	}

	private static void method1() throws IOException {
		int val=0, max=0;
		for (var line : Files.readAllLines(new File("src/y2022/d01/Q1.txt").toPath())) {
			if (!line.isEmpty())
				val += Integer.parseInt(line);
			else {
				if (val > max) max = val;
				val = 0;
			}
		};
		out.println(max);
	}
	
	private static void method2() throws IOException {
		String file = Files.readString(new File("src/y2022/d01/Q1.txt").toPath());
		out.println(stream(file.split("\n\n")).mapToInt(s-> stream(s.split("\n")).mapToInt(Integer::parseInt).sum()).max().getAsInt());
	}
}
