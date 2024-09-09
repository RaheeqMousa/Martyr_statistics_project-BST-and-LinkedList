
public class MartyrLinkedList {
	private Node head;
	private  int size=0;
	private Node last;
	private int male_counter,female_counter;
	private  long age_sum;
	
	public MartyrLinkedList(){
		this.head=null;   //empty linked list
		this.last=null;
		this.male_counter=0;
		this.female_counter=0;
		this.age_sum=0;
	}

//...................................................................................
	
	public Object getFirst(){
		return head.getData();
	}

	public Object getLastElement(){
		return last.getData();
	}

	public int size(){
		return size;
	}
	
//...................................................................................
	
	public void addToLast(Object s){
		
		
		if(((Martyr)s ).getGender()=='M'|| ((Martyr)s).getGender()=='m'  ) {
			this.male_counter++;}
		else if(((Martyr)s ).getGender()=='F'|| ((Martyr)s).getGender()=='f'  ) {
			this.female_counter++; }
		
		this.age_sum=age_sum+((Martyr)s).getAge();
			
			
			
		Node n=new Node(s);
		if(head==null){  //empty linked list
			head=n;
			last=n;
			size++;
		}
		else{
			last.setNext(n);
			last=last.getNext();
			size++;
		}
	}
	
	
//...................................................................................
	public void addToFirst(Object s){
		
		if(((Martyr)s ).getGender()=='M'|| ((Martyr)s).getGender()=='m'  ) {
			this.male_counter++;
			}
		else if(((Martyr)s ).getGender()=='F'|| ((Martyr)s).getGender()=='f'  ) {
			this.female_counter++;
		}
		
		this.age_sum=age_sum+((Martyr)s).getAge();
		
		
		Node node=new Node(s);
		if(head==null){  //empty linked list
			head=node;
			last=node;
			size++;
		}
		else{
			node.setNext(head);
			head=node;
			size++;
		}
	}
	
//...................................................................................
	
	public Node findMinName() {
		Node result=head;
		Node move=head;
		
		while(move!=null) {
			if(((Martyr)move.getData()).getName().toLowerCase().compareTo(((Martyr)result.getData()).getName().toLowerCase()) <0)
				result=move;
			
			move=move.getNext();
		}
		
		return result;
	}

//...................................................................................
	
	public boolean deleteFirst(){
		if(head==null) return false;
		else{
			
			if(head.getData() instanceof Martyr &&  ((Martyr)head.getData() ).getGender()=='M'|| ((Martyr)head.getData()).getGender()=='m' ) {				
				this.male_counter--;}
			else if(head.getData() instanceof Martyr &&  ((Martyr)head.getData() ).getGender()=='F'|| ((Martyr)head.getData()).getGender()=='f' ) {
				this.female_counter--;}
			
			this.age_sum=age_sum-((Martyr)head.getData()).getAge();  //there is a martyr that is deleted so i will minus the total sum of martyrs location
			
			
			
			head=head.getNext();
			size--;
			
			return true;
		}
	}

//...................................................................................	
	
	public boolean deleteLast(){
		if(head==null) return false;  //no elements in the linked list
		else if(head==last) {  //one elemnt in the linked list
			head=last=null;
			return true;
		}
		else{			  //more than one element in the list
			Node current=head;
			for(int i=0;i<size-2;i++){
				current=current.getNext();
			}

			if(last.getData() instanceof Martyr &&  ((Martyr)last.getData() ).getGender()=='M'|| ((Martyr)last.getData()).getGender()=='m' ) {
				this.male_counter--;}
			else if(last.getData() instanceof Martyr &&  ((Martyr)last.getData() ).getGender()=='F'|| ((Martyr)last.getData()).getGender()=='f' ) {
				this.female_counter--;
				}
			
			this.age_sum=age_sum-((Martyr)current.getData()).getAge();
			current.setNext(null);
			last=current;
			size--;
			
						
			return true;
		}
	}

//...................................................................................	
	
	public Object get(int i){
		if(head==null) return null;
		else if(i>(size-1) || i<0) return null;
		else if(i==0) getFirst();
		else if(i==(size-1)) getLastElement();

			Node current =head;
			for(int j=0;j<i;j++) {
				current=current.getNext();
			}
			return current.getData();

	}

//...................................................................................	
	
	public Object searchByName(String r){
		if(head==null) return null;
		else {
			Node current=head;
			
			while(current!=null){
				System.out.println(current.getData());
				if(((Martyr)current.getData()).getName().toLowerCase().contains(r.toLowerCase())  )
					return current.getData();
				current=current.getNext();
			}
			
			return null;
		}

	}

//...................................................................................
	
	public Object searchByFullName(String r){
		if(head==null) return null;
		else {
			Node current=head;
			
			while(current!=null){
				System.out.println(current.getData());
				if(((Martyr)current.getData()).getName().equalsIgnoreCase(r)  )
					return current.getData();
				current=current.getNext();
			}
			
			return null;
		}

	}
			
//...................................................................................	
	
	public boolean deleteByIndex(int index){
		if(head==null){  //empty linked list
			return false;
		}
		else if(index>(size-1) || index<0) return false; //invalis index
		else if(index==size-1){
			deleteLast();
			return true;
		}
		else if(index==0){
			deleteFirst();
			return true;
		}
		else{
			Node current = head;
			for(int i=0;i<index-1;i++){
				current=current.getNext();
			}
			
			if(current.getData() instanceof Martyr && ( ((Martyr)head.getData() ).getGender()=='M'|| ((Martyr)head.getData()).getGender()=='m' )) {
				this.male_counter--;}
			else if(current.getData() instanceof Martyr && ( ((Martyr)head.getData() ).getGender()=='F'|| ((Martyr)head.getData()).getGender()=='f' )) {
				this.female_counter--;}
			
			age_sum=age_sum-((Martyr)current.getNext().getData()).getAge();
			
			current.setNext(current.getNext().getNext());
			size--;
			
			
			
			return true;

		}
	}

	
//...................................................................................
	
	public boolean addByIndex(Object obj,int i){
		if(i>(size-1) || i<0)  return false;
		else if(i==0){
			addToFirst(obj);
			return true;
		}
		else if (i>=size()) {
			addToLast(obj);
			return true;	}
		else {
			Node current =head;
			for(int j=0;j<i-1;j++){
				current=current.getNext();
			}
			Node newNode=new Node(obj);
			
			if(newNode.getData() instanceof Martyr && ( ((Martyr)newNode.getData() ).getGender()=='M'|| ((Martyr)newNode.getData()).getGender()=='m') ) {
				this.male_counter++;}
			else if(newNode.getData() instanceof Martyr &&  (((Martyr)newNode.getData() ).getGender()=='F'|| ((Martyr)newNode.getData()).getGender()=='f') ) {
				this.female_counter++;}
			
			age_sum=age_sum+((Martyr)newNode.getData()).getAge();
			
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
			return true;
		}
	}
	
	


//....................................................................................................		
	
	public int lastIndexOf(String o) {
		if(head==null) return -1;
		else {
			Node current =head;
			//System.out.println(current.getData());
			int last_index=-1;
			for(int i=0;i<size;i++) {
				
				//System.out.println(current.getData()+" ,,  "+o);
				if(((Martyr)current.getData()).getName().equalsIgnoreCase( (String)o ) ) 
					last_index=i;
				//System.out.println(last_index);
				current=current.getNext();
			}
			
			return last_index;
		}
		
	}

//....................................................................................................		
	
	public boolean contains(String o) {
		System.out.println(o);
		if(head==null) return false;
		else {
			if(lastIndexOf(o) >=0) return true;
			
			else return false;
		}
		
	}
//.....................................................................................................
	
	public int lastIndexOf(Martyr o) {
		if(head==null) return -1;
		else {
			Node current =head;
			//System.out.println(current.getData());
			int last_index=-1;
			for(int i=0;i<size;i++) {
				
				System.out.println(current.getData()+" ,,  "+o);
				if(((Martyr)current.getData()).equals(o) ) 
					last_index=i;
				System.out.println(last_index);
				current=current.getNext();
			}
			
			return last_index;
		}
		
	}

//....................................................................................................		
	
	public boolean contains(Martyr o) {
		//System.out.println(o);
		if(head==null) return false;
		else {
			if(lastIndexOf(o) >=0) {System.out.println("contains in martyr"); 
			return true;			
			}
			else return false;
		}
		
	}	
	
//....................................................................................................		
	
	public boolean deleteMartyrByName(Object obj) {
		if(head==null) return false;
		else {

				if( ((Martyr)getHead().getData()).getName().equalsIgnoreCase((String) obj) ) {
					deleteFirst();
					return true;
				}
				else if(((Martyr)getLast().getData()).getName().equalsIgnoreCase((String) obj) ) {
					deleteLast(); 
					return true;
				}
				else {
					Node nn=head;
					Node prev =head;
					for(int i=0;i<size;i++) {					
						if(((Martyr)nn.getData()).getName().equalsIgnoreCase((String)obj))
							break;
						prev=nn;
						nn=nn.getNext();
					}
					
					if(nn==null) {  //this means that user tried to delete a martyr that doesn't exist in the current location
						return false;
					}

					prev.setNext(nn.getNext());
					size--;
					return true;
				}
			}
			
	}
	
//...................................................................................................
	
	public boolean deleteMartyrByWholeData(Martyr obj) {
		if(head==null) return false;
		else {
System.out.println(((Martyr)getHead().getData()).equals(obj) );
				if( ((Martyr)getHead().getData()).equals(obj) ) {
					deleteFirst();
					System.out.println("deleted successfully");
					return true;
				}
				else if(((Martyr)getLast().getData()).equals( obj) ) {
					deleteLast(); 
					return true;
				}
				else {
					Node nn=head;
					Node prev =head;
					
					
					for(int i=0;i<size;i++) {					
						if(((Martyr)nn.getData()).equals(obj))
							break;
						prev=nn;
						nn=nn.getNext();
					}
					
					if(nn==null) {  //this means that user tried to delete a martyr that doesn't exist in the current location
						System.out.println("not found");
						return false;
					}

					prev.setNext(nn.getNext());
					System.out.println("martyr delete successfully inside martyr linked list");
					size--;
					return true;
				}
			}
			
	}
	
	
//....................................................................................................		
	
	public void printInReverseOrder() {

		for(int i=size-1;i>=0;i--) {
			System.out.print(get(i)+" -> ");
		}
		System.out.print("null");
		System.out.println();
	}
	
//....................................................................................................		
	public void print(){
		Node n =head;
		System.out.println(n);
		while(n!=null) {
			System.out.print(n.getData()+" -> ");
			n=n.getNext();
		}

		System.out.println("null");
	}
	

//....................................................................................................		
	
	
	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMale_counter() {
		return this.male_counter;
	}

	public void setMale_counter(int male_counter) {
		this.male_counter = male_counter;
	}

	public int getFemale_counter() {
		return female_counter;
	}

	public void setFemale_counter(int female_counter) {
		this.female_counter = female_counter;
	}

	public byte getLargest_age() {
		return ((Martyr)last.getData()).getAge();  //the martyrs are added in ascending order to the list , so the last element in the list has the largest age
	}

	public byte getSmallest_age() {
		return ((Martyr)head.getData()).getAge();
	}
	
	public long getAgeSum() {
		return this.age_sum;
	}
	
	public void setAgeSym(long h ) {
		this.age_sum=h;
	}

	public void setLast(Node last) {
		this.last = last;
	}
	
	public Node getLast() {
		return last;
	}
	
	public double getAverage(){
		double res=0;
		try{
		res= (age_sum/size);
		}catch(ArithmeticException e) {
			
		}
		return res;
	}

//....................................................................................................		
	
	@Override
	public String toString() {
		return "MyLinkedList [head=" + head + "]";
	}


}
