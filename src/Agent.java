public enum Agent {
	CAT("Cat"), MOUSE("Mouse");
	
	private String name;
	
	Agent(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}