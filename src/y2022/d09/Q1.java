package y2022.d09;

import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.util.LinkedList;

public class Q1 {
	
	record P(int r, int c) {
		@Override public String toString() { return "{" + r + " " + c + "}"; }
	};
	enum D{U, L, D, R};
	static P h = new P(0,0); 
	static P t = new P(0,0);

	public static void main(String[] args) throws Exception {
		LinkedList<P> tp = new LinkedList();
		tp.add(t);
		//out.printf("h%s t%s\n", h, t);
		for (var line: readAllLines(new File("src/y2022/d09/q1.txt").toPath())) {
			var part = line.split(" ");
			D d = D.valueOf(part[0]);
			int n = Integer.parseInt(part[1]);
			//out.printf("%s %d\n", d, n);
			while (n-- > 0) {
				h = move(d);
				//out.printf("h%s t%s\n", h, t);
				t = move();
				if (tp.getLast().equals(t)) continue;
				tp.add(t);
			}
		}
		out.println(tp.stream().distinct().count());
	}
	
	/*
	   h h     h     h h
	   h +1-1 +10 +1+1 h
	   h  0-1  t   0+1 h
	   h -1-1 -10 -1+1 h
	   h h     h     h h

	+2-2 +2-1 +20 +2+1 +2+2
	+1-2 +1-1 +10 +1+1 +1+2
	 0-2  0-1  t   0+1  0+2
	-1-2 -1-1 -10 -1+1 -1+2
	-2-2 -2-1 -20 -2+1 -2+2

	*/
	
	static int[][][] move = new int [][][] {
	/* R      C    0 -2    1 -1    2 0    3 +1    4 +2 	
	/*0 -2*/	{{+1,-1},{+1,-1},{+1,0},{+1,+1},{+1,+1}},
	/*1 -1*/	{{+1,-1},{ 0, 0},{ 0,0},{ 0, 0},{+1,+1}},
	/*2  0*/	{{ 0,-1},{ 0, 0},{ 0,0},{ 0, 0},{ 0,+1}},
	/*3 +1*/	{{-1,-1},{ 0, 0},{ 0,0},{ 0, 0},{-1,+1}},
	/*4 +2*/	{{-1,-1},{-1,-1},{-1,0},{-1,+1},{-1,+1}}
	};
	
	private static P move() {
		var ir = t.r - h.r;
		var ic = h.c - t.c;
		var mt = move[2+ir][2+ic];
		var p = new P(t.r + mt[0], t.c + mt[1]);
		//out.printf("%d %d {%+d,%+d} t%s\n",ir,ic,mt[0],mt[1],p);
		return p;
	}

	private static P move(D d) {
		return switch (d) {
			case U -> new P(h.r+1, h.c);
			case L -> new P(h.r,   h.c-1);
			case D -> new P(h.r-1, h.c);
			case R -> new P(h.r,   h.c+1);
		};
	}
}
