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

public class Q2 {
	
	static class Monkey {
		int n, o, t, f, count, a, d; List<Long> ws=new ArrayList<>(); Monkey mt, mf;
		Monkey(int n, int o, int a, int d, int t, int f, Long ... ws) {
			this.n=n; this.o=o; this.a=a; this.d=d; this.t=t; this.f=f; this.ws.addAll(Arrays.stream(ws).toList());
		}
		Monkey(String s) { this(s.split("\n")); }
		Monkey(String[] d) {
			this(
				parseInt(d[0].substring(7, 8)), // n
				(int) d[2].charAt(23), // o
				((Function<String,Integer>) a-> a.equals("old") ? 0 : parseInt(a)).apply(d[2].substring(25)), // a
				parseInt(d[3].substring(21)), // d
				parseInt(d[4].substring(29)), // tt
				parseInt(d[5].substring(30)), // tf
				Arrays.stream(d[1].substring(18).split(", ")).map(Long::parseLong).toArray(Long[]::new) // ws
			);
		}
		@Override public String toString() { return "Monkey " + n + ": " + ws.stream().map(i-> i.toString()).collect(joining(",")); }
		void set(Monkey[] m) { mt=m[t]; mf=m[f]; }
		void turn() {
			for (long w: ws) {
				long v = a == 0 ? w : a;
				w = switch(o) {case '+'-> w+v; case '*'-> w*v; default->{ throw new IllegalArgumentException(); }} % gcd;
				var m = w % d == 0 ? mt : mf;
				m.ws.add(w);
				count += 1;
			}
			ws.clear();
		}
	}
	
	static long gcd=1;
	
	public static void main(String[] args) throws Exception {
		var monkey = stream(readString(new File("src/y2022/d11/q1.txt").toPath()).split("\n\n")).map(Monkey::new).toArray(Monkey[]::new);
		for (var m: monkey) { m.set(monkey); gcd *= m.d; }
		for (int i=1; i<=10000; i+=1) for (var m: monkey) m.turn();
		//for (var m: monkey) out.println(m.n + " " + m.count);
		out.println(Arrays.stream(monkey).mapToLong(m-> m.count).sorted().skip(monkey.length-2).reduce(1, (a,b)-> a*b));
	}
}
