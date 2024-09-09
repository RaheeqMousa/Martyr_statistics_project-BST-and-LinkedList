import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NavigateDates {
	private BorderPane n ;
	//private TextArea ta=new TextArea();
	private Button next;
	private Button previous;
	private Label date;
	private StackLinkedList stack1;
	private StackLinkedList stack2;
	
	private DateNode current_node;
	
	private Stage stage=new Stage();
	
	private TableView<Martyr> dateTable=new TableView<>();
	private ObservableList<Martyr> array= FXCollections.observableArrayList();
	
	public NavigateDates() {
		
		 stack1=new StackLinkedList();
		 stack2=new StackLinkedList();
		 next=new Button("next date");
		 previous=new Button("previous date");
		 date=new Label();
		 date.setFont(new Font(15));
		 
		 fillDatesIntoStack1(stack1);
		 n =new BorderPane();
		 try{	
			 read((DateNode)stack2.peek());
			 System.out.println(stack1.peek());
			 Object b =(DateNode)stack1.pop();
			 stack2.push(b);
			 System.out.println(b);
			 current_node=((DateNode)stack2.peek());
			 date.setText("Date = "+ ((DateNode)stack2.peek()).getDate() +"\nSmallest Martyr= "+
					 ((Martyr)((DateNode)stack2.peek()).getMartyrs().getFirst()).getName() +
					 "\nOldest Martyr= "+ ((Martyr)((DateNode)stack2.peek()).getMartyrs().getLastElement()).getName()+
					 "\nAverage martyrs age for this date= "+(((DateNode)stack2.peek()).getMartyrs().getAgeSum()/(double)((DateNode)stack2.peek()).getMartyrs().getSize())
					 );	

			 
		 } catch(NullPointerException exc) {
			 System.out.println("no districts to print their data"); }
		 
	}
	
	public BorderPane getPane(){

		next.setAlignment(Pos.CENTER_RIGHT);
		next.setPrefSize(100, 20);
		
		previous.setAlignment(Pos.CENTER_RIGHT);
		previous.setPrefSize(100, 20);
		
		HBox hb=new HBox(10);
		hb.getChildren().addAll(previous,next);
		hb.setAlignment(Pos.CENTER);
		
		

		
		n.setBottom(hb);
		n.setCenter(tableDate());
		n.setTop(date);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		
		next.setOnAction(e->{
			if(!stack1.isEmpty()) {
			stage.close();
			}
			if(!stack1.isEmpty()) {
				 try{	
					 stack2.push((DateNode)stack1.pop());
					 
					 read((DateNode)stack2.peek());
					 current_node=((DateNode)stack2.peek());
					 
					 date.setText("Date = "+ ((DateNode)stack2.peek()).getDate() +"\nSmallest Martyr= "+
							 ((Martyr)((DateNode)stack2.peek()).getMartyrs().getFirst()).getName() +
							 "\nOldest Martyr= "+ ((Martyr)((DateNode)stack2.peek()).getMartyrs().getLast().getData()).getName()+
							 "\nAverage martyrs age for this date= "+(((DateNode)stack2.peek()).getMartyrs().getAgeSum()/(double)((DateNode)stack2.peek()).getMartyrs().getSize())
							 );		
				 } catch(NullPointerException exc) {
					 System.out.println("no districts to print their data"); }
				 
//				 show_martyrs.setOnAction(act->{
//						
//						BorderPane n =new BorderPane();
//						Label response=new Label("Number of locations for "+((DBSTNode)stack2.peek()).getDistrictName()+" is "+((DBSTNode)stack2.peek()).getLocations().getNumberOfLocations());
//						response.setFont(new Font(20));
//						n.setCenter(tableDistrict((DBSTNode)stack2.peek()));
//						n.setBottom(response);
//						
//						
//						Scene scene=new Scene(n,300,300);
//						stage.setScene(scene);
//						stage.show();
//					});
			}
		});
		
		previous.setOnAction(e->{
			
			if(!stack2.isEmpty() && stack2.size()!=1) {

			stage.close();
			}
			if(!stack2.isEmpty() && stack2.size()!=1) {
				 try{	

					 stack1.push((DateNode)stack2.pop());
					 
					 current_node=((DateNode)stack2.peek());	
					 read((DateNode)stack2.peek());
					 
					 System.out.println(((Martyr)((DateNode)stack2.peek()).getMartyrs().getLast().getData()));
					 date.setText("Date = "+ ((DateNode)stack2.peek()).getDate() +"\nSmallest Martyr= "+
							 ((Martyr)((DateNode)stack2.peek()).getMartyrs().getFirst()).getName() +
							 "\nOldest Martyr= "+ ((Martyr)((DateNode)stack2.peek()).getMartyrs().getLast().getData()).getName()+
							 "\nAverage martyrs age for this date= "+(((DateNode)stack2.peek()).getMartyrs().getAgeSum()/(double)((DateNode)stack2.peek()).getMartyrs().getSize())
							 );	
				 } catch(NullPointerException exc) {
					 System.out.println("no districts to print their data"); }
				 

			}
		});
		
		
		return n ;
	}
//...................................................................................................................
	
	public void fillDatesIntoStack1(StackLinkedList st) {	
		if(MainStage.last_loaded_location==null) return;
		fillDatesIntoStack1(MainStage.last_loaded_location.getDateBST().getRoot(),st);
		reverseStack(st);  //i want the smallest district be in th top of the stack
		System.out.println("data filled in stack1 for district navigation");
	}
	
	private void fillDatesIntoStack1(DateNode node,StackLinkedList st) {
		if (node == null)  //this is the base case....
            return;
 
		fillDatesIntoStack1(node.getLeft(),st);
		
        st.push(node);
        	
        fillDatesIntoStack1(node.getRight(),st);
	}
	
	private void reverseStack(StackLinkedList st) {
		StackLinkedList ll=new StackLinkedList();
		
		while(!st.isEmpty()) {
			ll.push(st.pop());
		}
		
		StackLinkedList aa=new StackLinkedList();
		
		while(!ll.isEmpty()) {
			aa.push(ll.pop());
		}
		
		while(!aa.isEmpty()) {
			st.push(aa.pop());
		}

		System.out.println(st.peek());
	}
	
	
//...........................................................................................................................
			

			
		private void getDateData(DateNode current) {
				if(current==null) return;
				
				BST martyrsTree=new BST();
				
				fillLinkedListIntoBST(current.getMartyrs().getHead(),martyrsTree);
				
				fillMartyrsTreeIntoTable(martyrsTree.getRoot());
			}
			
		private void fillLinkedListIntoBST(Node current,BST martyrsTree) {
				if(current==null) return;
				Node move=current;
				
				while(move!=null) {
					martyrsTree.add((Martyr)move.getData());
					move=move.getNext();
				}
				
			}
		
		private void fillMartyrsTreeIntoTable(BSTNode current) {
			if(current==null) return;
			fillMartyrsTreeIntoTable(current.getLeft());
			array.add(current.getData());
			fillMartyrsTreeIntoTable(current.getRight());
		}
		
					
//.......................................................................................................
		
		public void read(DateNode d) {  //this mehod is to add the data to the obsarvable list...
			if(d==null) return;
			array.clear();// every time i read i clear the array
					
					
					//district does not have locations
					if(d.getMartyrs()==null) {
						Martyr r =new Martyr("NA",d.getDate(),(byte)0,MainStage.last_loaded_location.getLocationName(),MainStage.last_loaded_district.getDistrictName(),'F');
						array.add(r);
					}
					else {
						getDateData(d);
					}				
					
		}
			
//.......................................................................................................
 			
			public VBox tableDate() {
				
				dateTable.setItems(array);

					TableColumn name=new TableColumn("martyr name");
					name.setMinWidth(50);
					name.setCellValueFactory(
							new PropertyValueFactory<Martyr,String>("name")
							);
					
					TableColumn age=new TableColumn("martyr age");
					age.setMinWidth(50);
					age.setCellValueFactory(
							new PropertyValueFactory<Martyr,String>("age")
							);
					
					TableColumn gender=new TableColumn("gender");
					gender.setMinWidth(50);
					gender.setCellValueFactory(
							new PropertyValueFactory<Martyr,String>("gender")
							);
					
					TableColumn date=new TableColumn("date of death");
					date.setMinWidth(50);
					date.setCellValueFactory(
							new PropertyValueFactory<Martyr,Date>("date_of_death")
							);
					TableColumn district=new TableColumn("ditrict name");
					district.setMinWidth(50);
					district.setCellValueFactory(
							new PropertyValueFactory<Martyr,String>("District")
							);
					
					read(current_node);
					
					dateTable.getColumns().clear();
					dateTable.getColumns().addAll(name,age,date,gender,district);
					

					
					VBox vb=new VBox(10);
					vb.getChildren().addAll(dateTable);
					vb.setAlignment(Pos.CENTER);
					
					return vb;
				}				
}
