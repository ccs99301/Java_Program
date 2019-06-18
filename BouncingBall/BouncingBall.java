import java.awt.Color;

public class BouncingBall {

	private int size = 40;
	private int xLocation;
	private int yLocation;
	private Color color;
	public BouncingBall(int xLocation, int yLocation, Color color) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getxLocation() {
		return xLocation;
	}
	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
	}
	public int getyLocation() {
		return yLocation;
	}
	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}	
}