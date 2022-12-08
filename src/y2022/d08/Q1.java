package y2022.d08;

import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Q1 {
	
	public static void main(String[] args) throws Exception {
		method1();
		method2();
	}

	static int len, map[][], vis[][]; 
	
	private static void method1() throws IOException {
		List<int[]> list = new ArrayList();
		for (var line: readAllLines(new File("src/y2022/d08/q1.txt").toPath())) {
			list.add(stream(line.split("")).mapToInt(Integer::parseInt).toArray());
		}
		map = list.toArray(int[][]::new);
		//show(map);
		vis = stream(map).mapToInt(r->r.length).mapToObj(int[]::new).toArray(int[][]::new);
		len = map.length;
		for (int i=0; i<len; i+=1) {
			vis[i][0] = 1;
			for (int h=map[i][0], j=1; j<len; j+=1) {
				if (map[i][j] <= h) continue;
				vis[i][j] = 1;
				h = map[i][j];
			}
			vis[i][len-1] = 1;
			for (int h=map[i][len-1], j=len-2; j>=0; j-=1) {
				if (map[i][j] <= h) continue;
				vis[i][j] = 1;
				h = map[i][j];
			}
		}
		for (int i=0; i<len; i+=1) {
			vis[0][i] = 1;
			for (int h=map[0][i], j=1; j<len; j+=1) {
				if (map[j][i] <= h) continue;
				vis[j][i] = 1;
				h = map[j][i];
			}
			vis[len-1][i] = 1;
			for (int h=map[len-1][i], j=len-2; j>=0; j-=1) {
				if (map[j][i] <= h) continue;
				vis[j][i] = 1;
				h = map[j][i];
			}
		}
		//show(res);
		out.println(stream(vis).mapToInt(r-> stream(r).sum()).sum());
	}

	static int fst, lst; 
	
	private static void method2() throws IOException {
		List<int[]> list = new ArrayList();
		for (var line: readAllLines(new File("src/y2022/d08/q1.txt").toPath())) {
			list.add(stream(line.split("")).mapToInt(Integer::parseInt).toArray());
		}
		map = list.toArray(int[][]::new);
		//show(map);
		vis = stream(map).mapToInt(r-> r.length).mapToObj(int[]::new).toArray(int[][]::new);
		len = map.length; fst = 0; lst = len-1;
		for (int h, i=0; i<len; i+=1) {
			h = setVisible(i,fst); for (int j=fst+1; j<=lst; j+=1) if (map[i][j] > h) h = setVisible(i,j);
			h = setVisible(i,lst); for (int j=lst-1; j>=fst; j-=1) if (map[i][j] > h) h = setVisible(i,j); 
		}
		for (int h, i=0; i<len; i+=1) {
			h = setVisible(fst,i); for (int j=fst+1; j<=lst; j+=1) if (map[j][i] > h) h = setVisible(j,i);
			h = setVisible(lst,i); for (int j=lst-1; j>=fst; j-=1) if (map[j][i] > h) h = setVisible(j,i);
		}
		//show(res);
		out.println(stream(vis).mapToInt(r-> stream(r).sum()).sum());
	}

	private static int setVisible(int i, int j) {
		vis[i][j] = 1;
		return map[i][j];
	}
		
	@SuppressWarnings("unused")
	private static void show(int[][] map) {
		for (int i=0; i<len; i+=1) {
			for (int j=0; j<len; j+=1) {
				out.print(map[i][j]);
			}
			out.println();
		}
		out.println();
	}
}
