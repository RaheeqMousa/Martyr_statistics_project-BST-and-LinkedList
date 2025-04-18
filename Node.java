




public class Node {
	private Object data;
	private Node next;
	
	
	public Node() {
		super();
	}
	public Node(Object data) {
		this(data,null);
	}
	public Node(Object data, Node next) {
		super();
		this.data = data;
		this.next = next;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	
	
	@Override
	public String toString() {
		return "data=" + data + ", next=" + next+ ".";
	}
	
	
}
