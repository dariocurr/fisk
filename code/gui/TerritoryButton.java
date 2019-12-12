package risk;

public class TerritoryButton {

	private int x;
	private int y;
	private int width;
	private int height;
	private Territory territory;

	public TerritoryButton ( Territory territory, int x, int y, int width, int height ){
		this.territory = territory;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX (){
		return this.x;
	}

	public int getY (){
		return this.y;
	}

	public int getWidth (){
		return this.width;
	}

	public int getHeight (){
		return this.height;
	}

	public Territory getTerritory (){
		return this.territory;
	}

	@Override
	public String toString (){
		return this.territory.getName() + " " + this.x + " " + this.y;
	}

}
