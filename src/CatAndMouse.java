import java.util.*;

public class CatAndMouse {
	public CatAndMouse() {
	}

	public GameStatus findWinner(Map<Integer, Set<Integer>> graph, Integer currentCatState, Integer currentMouseState,
			Agent turn, Agent perspective, Integer holeState) {
		if (graph == null) {
			return new GameStatus(404, "Graph not found.");
		}
		if (currentCatState == holeState) {
			return new GameStatus(500, "Invalid Input.");
		}

		Stack<Integer> isVisitedCat = new Stack<>();
		isVisitedCat.push(currentCatState);

		Stack<Integer> isVisitedMouse = new Stack<>();
		isVisitedMouse.push(currentMouseState);

		Set<State> setOfStates = new HashSet<>();
		
		Map<State, Integer> gameSet = new HashMap<>();


		Agent turn1 = turn;

		long startTime = System.nanoTime();
		int resultMiniMax = dfs(graph, isVisitedCat, isVisitedMouse, holeState, setOfStates, turn.equals(Agent.MOUSE), perspective.equals(Agent.MOUSE),0, gameSet);
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Time taken by Minimax Algorithm without Alpha Beta pruning is " + totalTime + " nano seconds");

		isVisitedCat = new Stack<>();
		isVisitedCat.push(currentCatState);

		isVisitedMouse = new Stack<>();
		isVisitedMouse.push(currentMouseState);

		setOfStates = new HashSet<>();
		gameSet = new HashMap<>();

		startTime = System.nanoTime();
		int resultAlphaBeta = dfs(graph, isVisitedCat, isVisitedMouse, holeState, setOfStates,
				turn1.equals(Agent.MOUSE), perspective.equals(Agent.MOUSE), 0, (int) Double.NEGATIVE_INFINITY,
				(int) Double.POSITIVE_INFINITY, gameSet);
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Time taken by Minimax Algorithm with Alpha Beta pruning is " + totalTime + " nano seconds");

		System.out.println("Result is :"+resultAlphaBeta);

		return new GameStatus(200, String.valueOf(resultMiniMax));

	}

	 private int dfs(Map<Integer, Set<Integer>> graph, Stack<Integer> isVisitedCat, Stack<Integer> isVisitedMouse,
			 Integer holeState, Set<State> setOfStates, boolean isMouseTurn, boolean isMousePerspective, int level, Map<State, Integer> gameSet) {
	
//		 System.out.println(level + " " + (isMouseTurn ? "mouse" : "cat") + " " + isVisitedMouse.peek() + " " + isVisitedCat.peek());
		 
		 int result = (isMouseTurn && isMousePerspective) ? -1 : 1;
	
		 if (graph == null) {
			 return 0;
		 }
	
		 State currentState = new State(isVisitedMouse.peek(),isVisitedCat.peek(), isMouseTurn);
		 
		 if (setOfStates.contains(currentState)) {
//			 System.out.println("Returning draw from level " + level + ". Returning 0.");
			 return 0;
		 }
		
		 if (isVisitedCat.peek() == isVisitedMouse.peek()) {
//			 System.out.println("Returning cat-mouse in same position from level " + level + ". Returning " + (isMousePerspective ? -1 : 1));
			 return isMousePerspective ? -1 : 1;
		 }
		
		 if (holeState == isVisitedMouse.peek()) {
//			 System.out.println("Returning mouse-hole in same position from level " +level + ". Returning " + (isMousePerspective ? 1 : -1));
			 return isMousePerspective ? 1 : -1;
		 }
		
		 setOfStates.add(currentState);
		
		 Integer current = isMouseTurn ? isVisitedMouse.peek() : isVisitedCat.peek();
		 Stack<Integer> visitedStack = (isMouseTurn) ? isVisitedMouse : isVisitedCat;
		 if (gameSet.getOrDefault(currentState, 0) == 0) {
			 for (Integer connection : graph.get(current)) {
				 if (!isMouseTurn && connection == holeState) {
					 // Cat and hole cannot be in same position.
					 continue;
				 }
				 visitedStack.push(connection);
				 int tempOutcome = dfs(graph, isVisitedCat, isVisitedMouse, holeState, setOfStates, !isMouseTurn, isMousePerspective, level + 1, gameSet);
			
				 result = (isMouseTurn && isMousePerspective) ? Math.max(result, tempOutcome) : Math.min(result, tempOutcome);
				 gameSet.put(currentState, result);
				 visitedStack.pop();
			 }
		 }else{
			 result = gameSet.get(currentState);
		 }
		
		 setOfStates.remove(currentState);
		
//		 System.out.println("Backtrack from level " + level + ". Returning " +result);
		 return result;
	 }

	private int dfs(Map<Integer, Set<Integer>> graph, Stack<Integer> isVisitedCat, Stack<Integer> isVisitedMouse,
			Integer holeState, Set<State> setOfStates, boolean isMouseTurn, boolean isMousePerspective, int level,
			int alpha, int beta, Map<State, Integer> gameSet) {
//		System.out.println(level + " " + isMouseTurn + " " + isVisitedMouse.peek() + " " + isVisitedCat.peek() + " " + alpha + " " + beta);

		int result = (isMouseTurn && isMousePerspective) ? -1 : 1;

		if (graph == null) {
			return 0;
		}

		State currentState = new State(isVisitedMouse.peek(), isVisitedCat.peek(), isMouseTurn);
		if (setOfStates.contains(currentState)) {
			// System.out.println("Returning draw from level " + level + ".
			// Returning 0."+" "+alpha+" "+beta);
			return 0;
		}

		if (isVisitedCat.peek() == isVisitedMouse.peek()) {
			// System.out.println("Returning cat-mouse in same position from
			// level " + level + ". Returning "
			// + (isMousePerspective ? -1 : 1)+" "+alpha+" "+beta);
			return isMousePerspective ? -1 : 1;
		}

		if (holeState == isVisitedMouse.peek()) {
			// System.out.println("Returning mouse-hole in same position from
			// level " + level + ". Returning "
			// + (isMousePerspective ? 1 : -1)+" "+alpha+" "+beta);
			return isMousePerspective ? 1 : -1;
		}

		// State state = new State(isVisitedMouse.peek(), isVisitedCat.peek(),
		// isMouseTurn);
		setOfStates.add(currentState);

		Integer current = isMouseTurn ? isVisitedMouse.peek() : isVisitedCat.peek();
		Stack<Integer> visitedStack = (isMouseTurn) ? isVisitedMouse : isVisitedCat;

		if (gameSet.getOrDefault(currentState, 0) == 0) {
			for (Integer connection : graph.get(current)) {

				if (!isMouseTurn && connection == holeState) {
					continue;
				}

				visitedStack.push(connection);

				int tempOutcome = dfs(graph, isVisitedCat, isVisitedMouse, holeState, setOfStates, !isMouseTurn,
						isMousePerspective, level + 1, alpha, beta, gameSet);

				if (isMouseTurn && isMousePerspective) {
					if (tempOutcome == 1) {
						visitedStack.pop();
						result = 1;
						break;
					}
				}
				if (!isMouseTurn && isMousePerspective) {
					if (tempOutcome == -1) {
						visitedStack.pop();
						result = -1;
						break;
					}
				}
				if (isMouseTurn && !isMousePerspective) {
					if (tempOutcome == -1) {
						visitedStack.pop();
						result = -1;
						break;
					}
				}
				if (!isMouseTurn && !isMousePerspective) {
					if (tempOutcome == 1) {
						visitedStack.pop();
						result = 1;
						break;
					}
				}

				result = (isMouseTurn && isMousePerspective) ? Math.max(result, tempOutcome)
						: Math.min(result, tempOutcome);
				gameSet.put(currentState, result);
				visitedStack.pop();
			}
		}else{
			result = gameSet.get(currentState);
		}

		setOfStates.remove(currentState);
		// System.out.println("Backtrack from level " + level + ". Returning " +
		// result+" "+alpha+" "+beta);
		return result;
	}

}
