package y2022.d05;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Q1 {
	public static void main(String[] args) throws Exception {
		method1();
	}
	
	private static void method1() throws IOException {
		String line, lines[] = Files.readAllLines(new File("src/y2022/d05/q1.txt").toPath()).toArray(String[]::new);
		int i = 0;
		var stacks = new ArrayList<Stack>();
		for (int l=0; (l=(line=lines[i]).length()) > 0; i+=1) {
			l = (l-2)/4+1;
			for (int j=0; j<l; j+=1) {
				if (stacks.size() <= j) stacks.add(new Stack<Character>());
				var c = line.charAt(j*4+1);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(j).add(0,c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		for (i+=1; i< lines.length; i+=1) {
			line=lines[i];
			line=line.replace("move ", "");
			line=line.replace(" from ", " ");
			line=line.replace(" to ", " ");
			var istr = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]); 
			for (int j=istr[0]; j>=1; j-=1) {
				stacks.get(istr[2]-1).push(stacks.get(istr[1]-1).pop());
				//out.println(stacks);
			}
		}
		//out.println(stacks);
		var s=""; for (i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
}
