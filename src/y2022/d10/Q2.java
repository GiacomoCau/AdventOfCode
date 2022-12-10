package y2022.d10;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;

import java.io.File;

public class Q2 {
	
	public static void main(String[] args) throws Exception {
		var crt = "";
		int c=1, x=1;
		for (var line: readAllLines(new File("src/y2022/d10/q1.txt").toPath())) {
			crt += ctr(c++, x);
			if (line.startsWith("noop")) continue;
			crt += ctr(c++, x);
			x += parseInt(line.substring(5));
		}
		out.println(crt);
	}
	
	static String ctr(int c, int x) {
		c = (c-1) % 40;
		return (c==0 ? "\n" : "") + (c > x-2 && c < x+2 ? '#' : ' ');
	}
}
