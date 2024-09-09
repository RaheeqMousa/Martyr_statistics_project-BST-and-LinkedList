import java.util.Date;

public class BSTNode {
    private  Martyr martyr;
    private  BSTNode left;
    private BSTNode right;
    private MartyrLinkedList martyrs;
    
    public BSTNode() {
    	
    }
    
    public BSTNode(Martyr d) {
        this.martyr = d;
        this.left = null;
        this.right = null;
        martyrs=new MartyrLinkedList();
    }
    
    public BSTNode(Martyr d,BSTNode l,BSTNode r) {
        this.martyr =d ;
        this.left = l;
        this.right = r;
        martyrs=new MartyrLinkedList();
    }

	public Martyr getData() {
		return this.martyr;
	}

	public void setData(Martyr d) {
		this.martyr = d;
	}

	public BSTNode getLeft() {
		return left;
	}

	public void setLeft(BSTNode left) {
		this.left = left;
	}
//.....................................................................................
	
	
//......................................................................................
	
	public BSTNode getRight() {
		return right;
	}

	public void setRight(BSTNode right) {
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
		return "Node [Date=" + martyr + ", left=" + left + ", right=" + right + "]";
	}
    
    
}