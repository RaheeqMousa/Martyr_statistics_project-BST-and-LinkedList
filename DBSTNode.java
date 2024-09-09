import javafx.scene.control.ComboBox;

public class DBSTNode {
    private  String district_name;
    private  DBSTNode left;
    private DBSTNode right;
    private LocationBST locations;
    
    public DBSTNode() {
    	
    }
    
    public DBSTNode(String district_name) {
        this.district_name = district_name;
        this.left = null;
        this.right = null;
        locations=new LocationBST();
    }
    
    public DBSTNode(String district_name,DBSTNode l,DBSTNode r) {
        this.district_name = district_name;
        this.left = l;
        this.right = r;
        locations=new LocationBST();
    }

	public String getDistrictName() {
		return this.district_name;
	}

	public void setDistrictName(String d) {
		this.district_name = d;
	}

	public DBSTNode getLeft() {
		return left;
	}

	public void setLeft(DBSTNode left) {
		this.left = left;
	}

	public DBSTNode getRight() {
		return right;
	}

	public void setRight(DBSTNode right) {
		this.right = right;
	}
	
	public LocationBST getLocations() {
		return locations;
	}

	public void setLocations(LocationBST locations) {
		this.locations = locations;
	}

//....................................................................
	
	public int martyrsInsideDIstrict() {
		return this.getLocations().getNumberOfMartyrsInsideLocation();
	}
	
//.....................................................................
	
	//this code to check if the martyr exists depending on its district
	
	public boolean districtContainsMartyr(String str) {
		return locationContainsMartyr(this.getLocations().getRoot(), str);
	}
	
	public boolean locationContainsMartyr(LBSTNode l,String str) {
		if(l==null) return false;
		boolean a=locationContainsMartyr(l.getLeft(),str);
		boolean b=locationContainsMartyr(l.getRight(),str);
		return dateContainsMartyr(l.getDateBST().getRoot(),str)|| a||b;
		
	}
	
	public boolean dateContainsMartyr(DateNode l,String str) {
		if(l==null) return false;
		
		boolean a= dateContainsMartyr(l.getLeft(),str);
		boolean b= dateContainsMartyr(l.getRight(),str);
		return l.getMartyrs().contains(str) || a|| b;	
		
	}
	
//........................................................................
	
	
	public void deleteMartyrInsideDistrict(String name) {
		deleteMartyrInsideLocation(this.getLocations().getRoot(),name);
	}
	
	private void deleteMartyrInsideLocation(LBSTNode d,String name) {
		if(d==null) return;
		deleteMartyrInsideLocation(d.getLeft(),name);
		
		deleteMartyrInsideDate(d.getDateBST().getRoot(),name);
		
		deleteMartyrInsideLocation(d.getRight(),name);
	}
	
	private void deleteMartyrInsideDate(DateNode d,String name) {
		if(d==null) return;
		deleteMartyrInsideDate(d.getLeft(),name);
		
		if(d.getMartyrs().contains(name)) {
			System.out.println("martyr deletes successfully inside its district");
			d.getMartyrs().deleteMartyrByName(name);
		}
		
		deleteMartyrInsideDate(d.getRight(),name);
	}
	
//..........................................................................
	
	Martyr searched_for_martyr=null;
	
	public Martyr searchMartyrInsideDistrict(String name) {
		searched_for_martyr=null;
		searchMartyrInsideLocation(this.getLocations().getRoot(),name);
		return searched_for_martyr;
	}
	
	private void searchMartyrInsideLocation(LBSTNode d,String name) {
		if(d==null) return;
		searchMartyrInsideLocation(d.getLeft(),name);
		
		searchMartyrInsideDate(d.getDateBST().getRoot(),name);
		
		searchMartyrInsideLocation(d.getRight(),name);
	}
	
	private void searchMartyrInsideDate(DateNode d,String name) {
		if(d==null) return;
		deleteMartyrInsideDate(d.getLeft(),name);
		
		searched_for_martyr=(Martyr)d.getMartyrs().searchByFullName(name);
		
		deleteMartyrInsideDate(d.getRight(),name);
	}
	
//.......................................................................	
	
	public void setAllMartyrWithNewDistrictName(String new_distrrict_name) {
		traverseLocations(new_distrrict_name,this.getLocations().getRoot());
	}
	
	private void traverseLocations(String new_distrrict_name,LBSTNode l) {
		if(l==null) return;
		traverseLocations(new_distrrict_name,l.getLeft());
		traverseDates(new_distrrict_name,l.getDateBST().getRoot());
		traverseLocations(new_distrrict_name,l.getRight());
	}
	private void traverseDates(String new_distrrict_name,DateNode n ) {
		if(n==null) return;
		traverseDates(new_distrrict_name,n.getLeft());
		traverseMartyrs(new_distrrict_name,n.getMartyrs().getHead());
		traverseDates(new_distrrict_name,n.getRight());
	}
	private void traverseMartyrs(String new_distrrict_name,Node n ) {
		if(n==null) return;
		
		Node move=n;
		while(move!=null) {
			((Martyr)move.getData()).setDistrict(new_distrrict_name);
			move=move.getNext();
		}
	}
	
//.......................................................................
	
//	//search for martyr inside a district
//	
//	public Martyr searchedMartyr=null;
//	
//	public void searchMartyrInsideDistrict(DBSTNode d,String name) {
//		searchedMartyr=null;
//		searchMartyrInsideLocation(d.getLocations().getRoot(),name);
//	}
//	
//	public void searchMartyrInsideLocation(LBSTNode d,String name) {
//		searchMartyrInsideLocation(d.getLeft(),name);
//		
//		searchMartyrInsideDate(d.getDateBST().getRoot(),name);
//		
//		searchMartyrInsideLocation(d.getRight(),name);
//	}
//	
//	public void searchMartyrInsideDate(DateNode d,String name) {
//		searchMartyrInsideDate(d.getLeft(),name);
//		
//		searchedMartyr=(Martyr)d.getMartyrs().searchByFullName(name);
//		
//		searchMartyrInsideDate(d.getRight(),name);
//	}
	

	


	
//.....................................................................
	
	
	@Override
	public String toString() {
		return "Node [district_name=" + district_name + ", left=" + left + ", right=" + right + "]";
	}
    
    
}