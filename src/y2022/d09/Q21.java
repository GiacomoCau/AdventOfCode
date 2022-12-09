package y2022.d09;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.IntStream.range;
import static y2022.d09.Q21.D.valueOf;

import java.io.File;
import java.util.LinkedList;

public class Q21 {
	
	enum D {U, L, D, R};
	
	static class P {
		int r; int c;
		P(){} P(int r, int c) { this.r=r; this.c=c; } 
		@Override public String toString() { return "{" + r + " " + c + "}"; }
		@Override public boolean equals(Object obj) { return obj instanceof P p && p.c == c && p.r == r; }
		@Override protected P clone() throws CloneNotSupportedException { return new P(r,c); }
		P move(D d) {
			switch (d) { case U -> r+=1; case L -> c-=1; case D -> r-=1; case R -> c+=1; };
			return this;
		}
		P move(P h) {
			var mt = move[2+r-h.r][2+h.c-c];
			r += mt[0];
			c += mt[1];
			return this;
		}
		static P p(int r, int c) { return new P(r,c); }
	};
	
	static int len=9;
	static P head=new P(), tail[] = range(0, len).mapToObj(i-> new P()).toArray(P[]::new);

	public static void main(String[] args) throws Exception {
		LinkedList<P> tp = new LinkedList(); tp.add(new P());
		for (var line: readAllLines(new File("src/y2022/d09/q1.txt").toPath())) {
			var part = line.split(" ");
			D d = valueOf(part[0]);
			int n = parseInt(part[1]);
			while (n-- > 0) {
				P p = head.move(d);
				for (int i=0; i<len; i+=1) p = tail[i].move(p);
				if (!tp.contains(p)) tp.add(p.clone());
			}
		}
		out.println(tp.size());
	}
	
	/*
	   h h     h     h h
	   h +1-1 +10 +1+1 h
	   h  0-1  t   0+1 h
	   h -1-1 -10 -1+1 h
	   h h     h     h h
	*/
	
	static int[][][] move = new int [][][] {
		/* R      C    0:-2    1:-1    2:0    3:+1    4:+2 	
		/*0:-2*/	{{+1,-1},{+1,-1},{+1,0},{+1,+1},{+1,+1}},
		/*1:-1*/	{{+1,-1},{ 0, 0},{ 0,0},{ 0, 0},{+1,+1}},
		/*2: 0*/	{{ 0,-1},{ 0, 0},{ 0,0},{ 0, 0},{ 0,+1}},
		/*3:+1*/	{{-1,-1},{ 0, 0},{ 0,0},{ 0, 0},{-1,+1}},
		/*4:+2*/	{{-1,-1},{-1,-1},{-1,0},{-1,+1},{-1,+1}}
	};	
}
