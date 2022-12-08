package y2022.d06;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashSet;

public class Q1 {
	public static void main(String[] args) throws Exception {
		for (var line: Files.readAllLines(new File("src/y2022/d06/q1.txt").toPath())) {
			for (int i=0, e=line.length()-4; i<e; i+=1) {
				var set = new LinkedHashSet<Character>();
				for (int j=0; j<4; j+=1) set.add(line.charAt(i+j));
				//out.println(set);
				if (set.size() != 4) continue;
				out.println(i+4);
				break;
			};
		}
	}
}
