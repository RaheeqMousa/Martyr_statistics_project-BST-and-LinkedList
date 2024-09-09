public class StackLinkedList {
	Node top;
	int size;
	
	public StackLinkedList() {
		top=null;
		size=0;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return top==null;
	}
	
	public void push(Object element) {
		Node n=new Node(element);
		
		n.setNext(top);
		top=n;
		size++;
	}
	
	public Object pop() {
		
		if(!isEmpty()) {
			Node returned=top;
			top=top.getNext();
			size--;
			return returned.getData();
		}
		return null;
	}
	
	public Object peek() {
		if(!isEmpty())
			return top.getData();
		else
			return null;
	}
	

	
}