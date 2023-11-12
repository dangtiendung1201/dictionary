package game;

public class GameManagement {
	public static enum State {
		WIN, LOSE, PLAYING
	}

	protected State state = State.PLAYING;
	protected int point;
	protected int health;

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void increasePoint() {
		point++;
	}

	public void decreaseHealth() {
		health--;
	}
}