package y2022.d11;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readString;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Q1 {
	
	static class Monkey extends ArrayList<Integer> {
		private static final long serialVersionUID = 1L;
		int n, o, a, d, t, f, count; Monkey mt, mf;
		Monkey(int n, int o, int a, int d, int t, int f, List<Integer> ws) {
			this.n=n; this.o=o; this.a=a; this.d=d; this.t=t; this.f=f; addAll(ws);
		}
		Monkey(String s) { this( s.split("\n") ); }
		Monkey(String[] d) {
			this(
				parseInt(d[0].substring(7, 8)), // n
				(int) d[2].charAt(23), // o
				((Function<String,Integer>) a-> a.equals("old") ? 0 : parseInt(a)).apply(d[2].substring(25)), // a
				parseInt(d[3].substring(21)), // d
				parseInt(d[4].substring(29)), // tt
				parseInt(d[5].substring(30)), // tf
				Arrays.stream(d[1].substring(18).split(", ")).map(Integer::parseInt).toList() // ws
			);
		}
		@Override public String toString() { return "Monkey " + n + ": " + stream().map(i-> i.toString()).collect(joining(",")); }
		void set(Monkey[] m) { mt=m[t]; mf=m[f]; }
		void turn() {
			//out.println("monkey " + n);
			for (var w: this) {
				var v = a == 0 ? w : a;
				w =  switch(o) {case '+'-> w+v; case '*'-> w*v; default-> 0; } / 3;
				var m = w % d == 0 ? mt : mf;
				//out.printf("\tthrow %d to %d\n", w, m.n); 
				m.add(w);
				count+=1;
			}
			clear();
		}
	}
	
	public static void main(String[] args) throws Exception {
		/*
		var monkey = new Monkey[] {
				new Monkey(0, '*', 19, 23, 2, 3, of(79, 98)),
				new Monkey(1, '+', 6,  19, 2, 0, of(54, 65, 75, 74)),
				new Monkey(2, '*', 0,  13, 1, 3, of(79, 60, 97)),
				new Monkey(3, '+', 3,  17, 0, 1, of(74))
		};
		*/
		var monkey = Arrays.stream(readString(new File("src/y2022/d11/p1.txt").toPath()).split("\n\n")).map(Monkey::new).toArray(Monkey[]::new);
		for (var m: monkey) m.set(monkey);
		//for (var m: monkey) out.println(m);  out.println();
		for (int i=1; i<=20; i+=1) {
			//out.println("Round " + i);
			for (var m: monkey) m.turn();
			//for (var m: monkey) out.println(m);	out.println("\n");
		}
		//for (var m: monkey) out.println(m.n + " " + m.count);
		out.println(Arrays.stream(monkey).mapToInt(m-> m.count).sorted().skip(monkey.length-2).reduce(1, (a,b)-> a*b));
	}
}
