package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Demonstration;
import model.Position2D;
import model.Trace;

public class TraceParser {

	public static ArrayList<Demonstration> parseFile(String path, int objectNumber) throws IOException{
		ArrayList<Demonstration> resp = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		int D = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < D; i++) {
			Demonstration d = new Demonstration(br.readLine());
			
			//Reading the arm trace
			String[] armLine = br.readLine().split("	");
			Trace t1 = new Trace(armLine[0]);
			for (int j = 1; j < armLine.length; j+=2) {
				t1.addPosition(new Position2D(Integer.parseInt(armLine[j]), Integer.parseInt(armLine[j+1])));
			}
			d.addTrace(t1);
			
			for (int j = 0; j < objectNumber; j++) {
				String[] line = br.readLine().split("	");
				Trace t = new Trace(line[0]);
				for (int k = 1; k < line.length; k+=2) {
					t.addPosition(new Position2D(Integer.parseInt(line[k]), Integer.parseInt(line[k+1])));
				}
				d.addTrace(t);
			}
			resp.add(d);
		}
		
		br.close();
		
		return resp;
	}
}
