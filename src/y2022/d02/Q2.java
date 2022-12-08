package y2022.d02;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;

public class Q2 {
	public static void main(String[] args) throws Exception {
		int t = 0;
		for (var line: Files.readAllLines(new File("src/y2022/d02/q1.txt").toPath())) {
			//1:rock 2:paper 3:scissor
			//x:perdere y:pareggiare z:vincere
			var o = 1 + "ABC".indexOf(line.charAt(0));
			var y = 1 + "XYZ".indexOf(line.charAt(2));
			var r = switch(y) {
				case 1-> { y = o - 1; if (y < 1) y = 3; yield 0; }
				case 2-> { y = o; yield 3; }
				case 3-> { y = o + 1; if (y > 3) y = 1; yield 6; }
				default-> { throw new Exception(); } 
			};
			//out.printf("%s %d %d %d %d\n",line, y, o, r, y+r);
			t += y + r;
		}
		out.println(t);
	}
}
