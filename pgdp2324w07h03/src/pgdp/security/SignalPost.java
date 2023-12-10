package pgdp.security;

public abstract class SignalPost {
	private int postNumber;
	private String depiction;
	private int level;

	/**
	 * Diese Klasse ist nur da, damit keine Buildfails entstehen. Allerdings ist sie
	 * bei Weitem noch nicht vollständig.
	 * 
	 */

	public SignalPost(int postNumber) {
		this.postNumber = postNumber;
		this.level = 0;
		this.depiction = "";
	}

	public int getPostNumber() {
		return postNumber;
	}

	public String getDepiction() {
		return depiction;
	}

	public int getLevel() {
		return level;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public abstract boolean up(String type);

	public abstract boolean down(String type);

	public String toString() {
		return "Signal Post " + postNumber + ": " + level + " " + depiction;
	}
}
