package y2022.d02;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;

public class Q1 {
	public static void main(String[] args) throws Exception {
		int t = 0;
		for (var line: Files.readAllLines(new File("src/y2022/d02/Q1.txt").toPath())) {
			//1:rock 2:paper 3:scissor
			var o = 1 + "ABC".indexOf(line.charAt(0));
			var y = 1 + "XYZ".indexOf(line.charAt(2));
			var r = y == o ? 3 : y == o + 1 || y == 1 && o == 3 ? 6 : 0;
			//out.printf("%s %d %d %d %d\n",line, y, o, r, y+r);
			t += y + r;
		}
		out.println(t);
	}
}
