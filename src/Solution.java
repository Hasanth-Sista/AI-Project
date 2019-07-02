import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args){
		CatAndMouse catAndMouse = new CatAndMouse();
		File inputFile = new File(args[0]);
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		Integer catPosition = 2, mousePosition = 1, holePosition = 0;
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			while((line = br.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens[0] = tokens[0].trim();
				tokens[1] = tokens[1].trim();
				Integer destination = Integer.parseInt(tokens[1]);
				if (tokens[0].charAt(0) < '0' || tokens[0].trim().charAt(0) > '9') {
					if ("C".equals(tokens[0])) {
						catPosition = destination;
					} else if ("M".equals(tokens[0])) {
						mousePosition = destination;
					} else if ("H".equals(tokens[0])) {
						holePosition = destination;
					}
				} else {
					Integer source = Integer.parseInt(tokens[0]);
					Set<Integer> connections = graph.getOrDefault(source, new TreeSet<>());
					connections.add(destination);
					graph.put(source, connections);
				}
			}
			for(Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
				System.out.print(entry.getKey()+": ");
				for (Integer connection : entry.getValue()) {
					System.out.print(connection+",");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Cat Position: "+catPosition);
			System.out.println("Mouse Position: "+mousePosition);
			System.out.println("Hole Position: "+holePosition);
			System.out.println();
			Agent perspective = "MOUSE".equalsIgnoreCase(args[1]) ? Agent.MOUSE : Agent.CAT;
			Agent turn = "MOUSE".equalsIgnoreCase(args[2]) ? Agent.MOUSE : Agent.CAT;
			GameStatus status = catAndMouse.findWinner( /* graph= */ graph, 
									/* catPosition= */ catPosition, 
									/* mousePosition= */ mousePosition, 
									/* turn= */ turn, 
									/* perspective= */ perspective, 
									/* holePosition= */ holePosition);
			int outcome = 0;
			try {
				outcome = Integer.parseInt(status.message);
			} catch(NumberFormatException e) {
				System.out.println(status.status+": "+status.message);
				return;
			}
			if (outcome == -1) {
				System.out.println(((perspective == Agent.MOUSE) ? Agent.CAT : Agent.MOUSE) + " wins!");
			} else if (outcome == 1) {
				System.out.println(perspective + " wins!");
			} else {
				System.out.println("Draw!");
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
