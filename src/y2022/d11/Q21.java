package y2022.d11;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readString;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Q21 {
	
	static class Monkey extends ArrayList<Long> {
		private static final long serialVersionUID = 1L;
		int n, o, d, t, f, count; Integer a; Monkey mt, mf;
		Monkey(int n, int o, Integer a, int d, int t, int f, List<Long> ws) {
			this.n=n; this.o = o; this.a = a; this.d=d; this.t=t; this.f=f; addAll(ws);
		}
		Monkey(String s) { this(s.split("\n")); }
		Monkey(String[] d) {
			this(
				parseInt(d[0].replaceAll("\\D","")), // n
				d[2].charAt(23), // o
				((Function<String,Integer>) a-> a.equals("old") ? null : parseInt(a)).apply(d[2].substring(25)), // a
				parseInt(d[3].replaceAll("\\D","")), // d
				parseInt(d[4].replaceAll("\\D","")), // tt
				parseInt(d[5].replaceAll("\\D","")), // tf
				Arrays.stream(d[1].split(": ")[1].split(", ")).map(Long::parseLong).toList() // ws
			);
		}
		@Override public String toString() { return "Monkey " + n + ": " + stream().map(i-> i.toString()).collect(joining(",")); }
		void set(Monkey[] m) { mt=m[t]; mf=m[f]; }
		void turn() {
			for (long w: this) {
				long v = a == null ? w : a;
				w = switch(o) {case '+'-> w+v; case '*'-> w*v; default->{ throw new IllegalArgumentException(); }} % gcd;
				(w % d == 0 ? mt : mf).add(w);
				count += 1;
			}
			clear();
		}
	}
	
	static long gcd=1;
	
	public static void main(String[] args) throws Exception {
		var monkey = stream(readString(new File("src/y2022/d11/q1.txt").toPath()).split("\n\n")).map(Monkey::new).toArray(Monkey[]::new);
		for (var m: monkey) { m.set(monkey); gcd *= m.d; }
		for (int i=1; i<=10000; i+=1) for (var m: monkey) m.turn();
		//for (var m: monkey) out.println(m.n + " " + m.count);
		out.println(stream(monkey).mapToLong(m-> m.count).sorted().skip(monkey.length-2).reduce(1, (a,b)-> a*b));
	}
}
