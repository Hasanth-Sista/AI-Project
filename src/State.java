public class State {
	private Integer mouseValue;
	private Integer catValue;
	private boolean isMouse;
	private Integer previousResult;
	
	public State(Integer mouseValue, Integer catValue, boolean isMouse){
		this.mouseValue = mouseValue;
		this.catValue = catValue;
		this.isMouse = isMouse;
		this.previousResult = 0;
	}
	
	public Integer getMouseValue(){
		return this.mouseValue;
	}
	public Integer getCatValue(){
		return this.catValue;
	}
	public boolean isMouseValue(){
		return this.isMouse;
	}
	public void setPreviousResult(Integer previousResult){
		this.previousResult = previousResult;
	}
	public Integer getPreviousResult(){
		return this.previousResult;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if(obj instanceof State){
			State temporaryState = (State)obj;
			return (this.mouseValue.intValue() == temporaryState.getMouseValue().intValue() && this.catValue.intValue() == temporaryState.getCatValue().intValue());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return (this.mouseValue.hashCode() + this.catValue.hashCode());
	}
}
