
public class test {
	public static void main(String[] args) {
		Demo d = new Demo();
		System.out.println(d.getPath());
	}
}

class Demo{
	String path = this.getClass().getResource(".").getPath();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
