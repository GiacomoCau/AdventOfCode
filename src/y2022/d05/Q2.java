package y2022.d05;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Q2 {
	public static void main(String[] args) throws Exception {
		method1();
		method2();
		method3();
		method4();
		method5();
	}
	
	private static void method1() throws IOException {
		int i = 0;
		var stacks = new ArrayList<Stack>();
		String line, lines[] = Files.readAllLines(new File("src/y2022/d05/Q1.txt").toPath()).toArray(String[]::new);
		for (int l=0; (l=(line=lines[i]).length()) > 0; i+=1) {
			l = 1+(l-2)/4;
			for (int j=0; j<l; j+=1) {
				if (stacks.size() <= j) stacks.add(new Stack<Character>());
				var c = line.charAt(1+j*4);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(j).add(0, c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		for (i+=1; i<lines.length; i+=1) {
			line=lines[i];
			line=line.replace("move ", "");
			line=line.replace(" from ", " ");
			line=line.replace(" to ", " ");
			var istr = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]);
			
			var to = stacks.get(istr[2]-1);
			for (int tos=to.size(), j=istr[0]; j>=1; j-=1) to.add(tos, stacks.get(istr[1]-1).pop());
		}
		//out.println(stacks);
		var s=""; for (i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
	
	private static void method2() throws IOException {
		int i = 0;
		var stacks = new ArrayList<Stack>();
		String line, lines[] = Files.readAllLines(new File("src/y2022/d05/Q1.txt").toPath()).toArray(String[]::new);
		for (int l=0; (l=(line=lines[i]).length()) > 0; i+=1) {
			for (int e=1+l/4, j=0; j<e; j+=1) {
				if (stacks.size() <= j) stacks.add(new Stack<Character>());
				var c = line.charAt(1+j*4);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(j).add(0, c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		for (i+=1; i<lines.length; i+=1) {
			var istr = Arrays.stream(lines[i].substring(5).replaceAll(" (from|to) ", " ").split(" ")).mapToInt(Integer::parseInt).map(n-> n-1).toArray();
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]);
			var to = stacks.get(istr[2]);
			for (int tos=to.size(), j=istr[0]; j>=0; j-=1) to.add(tos, stacks.get(istr[1]).pop());
		}
		//out.println(stacks);
		var s=""; for (i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
	
	private static void method3() throws IOException {
		var stacks = new ArrayList<Stack>();
		String[] part = Files.readString(new File("src/y2022/d05/Q1.txt").toPath()).split("\n\n");
		for (var line: part[0].split("\n")) {
			for (int e=1+line.length()/4, i=0, j=1; i<e; i+=1, j+=4) {
				if (stacks.size() <= i) stacks.add(new Stack<Character>());
				var c = line.charAt(j);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(i).add(0, c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		for (var line: part[1].split("\n")) {
			var istr = Arrays.stream(line.substring(5).replaceAll(" (from|to) ", " ").split(" ")).mapToInt(Integer::parseInt).map(n-> n-1).toArray();
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]);
			var to = stacks.get(istr[2]);
			var from = stacks.get(istr[1]);
			for (int tos=to.size(), i=istr[0]; i>=0; i-=1) to.add(tos, from.pop());
		}
		//out.println(stacks);
		var s=""; for (int i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
	
	private static void method4() throws IOException {
		var stacks = new ArrayList<Stack>();
		String[] part = Files.readString(new File("src/y2022/d05/Q1.txt").toPath()).split("\n\n");
		for (var line: part[0].split("\n")) {
			for (int e=line.length()/4+1, i=0, j=1; i<e; i+=1, j+=4) {
				if (stacks.size() <= i) stacks.add(new Stack<Character>());
				var c = line.charAt(j);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(i).add(0, c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		Pattern PARSE_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
		for (var line: part[1].split("\n")) {
	        var matcher = PARSE_PATTERN.matcher(line);
	        matcher.find();
	        var istr = IntStream.range(1, matcher.groupCount()+1).mapToObj(matcher::group).mapToInt(Integer::parseInt).map(n-> n-1).toArray();
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]);
			var to = stacks.get(istr[2]);
			var from = stacks.get(istr[1]);
			for (int tos=to.size(), j=istr[0]; j>=0; j-=1) to.add(tos, from.pop());
		}
		//out.println(stacks);
		var s=""; for (int i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
	
	private static void method5() throws IOException {
		var stacks = new ArrayList<Stack>();
		String[] part = Files.readString(new File("src/y2022/d05/Q1.txt").toPath()).split("\n\n");
		for (var line: part[0].split("\n")) {
			for (int e=line.length()/4+1, i=0, j=1; i<e; i+=1, j+=4) {
				if (stacks.size() <= i) stacks.add(new Stack<Character>());
				var c = line.charAt(j);
				if (c == ' ' || Character.isDigit(c)) continue;
				stacks.get(i).add(0, c);
				//out.println(stacks);				
			}
		}
		//out.println(stacks);
		record Istr(int qtn, int from, int to) {
			static Pattern PARSE_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
			static Istr get(String line) { 
				var matcher = PARSE_PATTERN.matcher(line);
				matcher.find();
		        var istr = IntStream.range(1, matcher.groupCount()+1).mapToObj(matcher::group).mapToInt(Integer::parseInt).map(n-> n-1).toArray();
				return new Istr(istr[0], istr[1], istr[2]); 
			}
		}
		for (var line: part[1].split("\n")) {
			var istr = Istr.get(line);
			//out.println("move " + istr[0] + " from " + istr[1] + " to " + istr[2]);	
			var to = stacks.get(istr.to);
			var from = stacks.get(istr.from);
			for (int tos=to.size(), j=istr.qtn; j>=0; j-=1) to.add(tos, from.pop());
		}
		//out.println(stacks);
		var s=""; for (int i=0; i<stacks.size(); i+=1) s += stacks.get(i).peek();
		out.println(s);
	}
}
