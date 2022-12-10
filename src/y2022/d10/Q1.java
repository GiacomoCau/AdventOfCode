package y2022.d10;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.io.IOException;

public class Q1 {
	
	public static void main(String[] args) throws Exception {
		method1();
		method2();
	}

	private static void method1() throws IOException {
		int strength=0;
		boolean next=true;
		String[] istr=null;
		var lines = readAllLines(new File("src/y2022/d10/p1.txt").toPath()).toArray(String[]::new);		
		for (int w=0, x=1, i=0, c=1; ; c+=1) {
			if (c == 20 + c/40*40) {
				//out.printf("%3d %2d = %4d\n", c, x, c*x);
				strength += c*x;
			}
			if (next) {
				if (i == lines.length) break;
				istr = lines[i].split(" ");
				switch (istr[0]) {
					case "noop": w=0; break;
					case "addx": w=1; break;
				};
				next = false;
			}
			//out.printf("%3d %3d %d %3d %s %s\n", c, x, wait, i, istr[0], istr.length==1 ? "" : istr[1]);  
			if (w > 0) {	w-=1; continue; }
			switch (istr[0]) {
				case "noop": break;
				case "addx": x+=parseInt(istr[1]); break;
			}
			next = true;
			i += 1;
		}
		out.println(strength);
	}
	
	static void method2() throws IOException {
		int strength = 0;
		int c=1, x=1;
		for (var line: readAllLines(new File("src/y2022/d10/p1.txt").toPath())) {
			strength += strength(c++, x);
			if (line.startsWith("noop")) continue;
			strength += strength(c++, x);
			x += parseInt(line.substring(5));
		}
		out.println(strength);
	}

	static int strength(int c, int x) {
		return (c+20) % 40 == 0 ? c*x : 0; 
	}
}
