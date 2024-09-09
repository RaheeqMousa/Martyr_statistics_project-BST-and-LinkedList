import java.util.Date;

public class DateNode {
    private  Date date;
    private  DateNode left;
    private DateNode right;
    private MartyrLinkedList martyrs;
    
    public DateNode() {
    	
    }
    
    public DateNode(Date d) {
        this.date = d;
        this.left = null;
        this.right = null;
        martyrs=new MartyrLinkedList();
    }
    
    public DateNode(Date d,DateNode l,DateNode r) {
        this.date = d;
        this.left = l;
        this.right = r;
        martyrs=new MartyrLinkedList();
    }

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public DateNode getLeft() {
		return left;
	}

	public void setLeft(DateNode left) {
		this.left = left;
	}
//.....................................................................................
	
	
//......................................................................................
	
	public DateNode getRight() {
		return right;
	}

	public void setRight(DateNode right) {
		this.right = right;
	}

	
	public MartyrLinkedList getMartyrs() {
		return martyrs;
	}

	public void setMartyrs(MartyrLinkedList martyrs) {
		this.martyrs = martyrs;
	}

	@Override
	public String toString() {
		return "Node [Date=" + date + ", left=" + left + ", right=" + right + "]";
	}
    
    
}