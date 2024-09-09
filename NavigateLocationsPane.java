
import java.io.File;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NavigateLocationsPane {
	
	private BorderPane n ;
	//private TextArea ta=new TextArea();
	private Button next;
	private Button previous;
	private Button load;
	private Label location_name;
	private StackLinkedList stack1;
	private StackLinkedList stack2;
	//private static LBSTNode last_loaded_location;

	
	private Stage stage=new Stage();
	
	private TableView<Martyr> s=new TableView<>();
	private ObservableList<Martyr> array= FXCollections.observableArrayList();
	
	public NavigateLocationsPane() {
		try {  //first location data should be shown first
			System.out.println(MainStage.last_loaded_district+" inside navigate location"+MainStage.last_loaded_district.getLocations().getRoot());
			MainStage.last_loaded_location=MainStage.last_loaded_district.getLocations().getRoot(); 
		}catch(NullPointerException ex) {
			System.out.println("no districts in the system");
		}
		 stack1=new StackLinkedList();
		 stack2=new StackLinkedList();
		 next=new Button("next location");
		 previous=new Button("previous location");
		 load=new Button("load location");
		 location_name=new Label();
		 location_name.setFont(new Font(15));
		 
		 try {
		 levelByLevelTraversalStack1(MainStage.last_loaded_district.getLocations().getRoot(),stack1);
		 }catch(NullPointerException ex) {
			 
		 }
		 
		 n =new BorderPane();
		 try{	
			 dateHasMaximumMartyrs(((LBSTNode)stack1.peek()).getDateBST().getRoot());
			 
			 System.out.println(stack1.peek());
			 Object b =(LBSTNode)stack1.pop();
			 stack2.push(b);
			 System.out.println(b);
			 location_name.setText("Location name= "+ ((LBSTNode)stack2.peek()).getLocationName()+"\nEarliest date that contains martyrs= "+
					 (((LBSTNode)stack2.peek()).getDateBST().findMin().getDate())+
					 "\nThe latest dates that has martyrs= "+(((LBSTNode)stack2.peek()).getDateBST().findMax().getDate())+
					 "\nThe date that has the maximum number of martyrs= "+date_has_max_martyrs
					 );	
			 
				load.setOnAction(act->{
					MainStage.last_loaded_location=(LBSTNode)stack2.peek();
					
					BorderPane n =new BorderPane();
					Label response=new Label("Number of dates for "+((LBSTNode)stack2.peek()).getLocationName()+" is "+((LBSTNode)stack2.peek()).getDateBST().number_of_dates);
					n.setCenter(tableLocation());
					n.setBottom(response);
					response.setFont(new Font(20));
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
			        alert.setTitle("location loaded successfully");
			        alert.setContentText("SINCE YOU LOADED THE Location "+((LBSTNode)stack2.peek()).getLocationName()+"\nSO THE DATE NAVIGATION WILL BE ONLY IN \n"+((LBSTNode)stack2.peek()).getLocationName()+"'S DATES");
			        
			        alert.show(); 				
					Scene scene=new Scene(n,400,300);
					stage.setScene(scene);
					stage.show();
				});
			 
		 } catch(NullPointerException exc) {
			 
			 System.out.println(stack2.peek()+" is empty?");
			 if(stack2.peek()!=null) {   //if the location does not has date inside it (does not has martyrs...)
				 location_name.setText("Location name= "+ ((LBSTNode)stack2.peek()).getLocationName()+"\nEarliest date that contains martyrs= NA"+
						 "\nThe latest dates that has martyrs= NA"+
						 "\nThe date that has the maximum number of martyrs= NA"
						 );	
			 }
			 else {  //if stack2 is empty that means that there is no locations in the whole district 
				 Alert alert = new Alert(Alert.AlertType.INFORMATION);
			        alert.setTitle("file read successfully");
			        alert.setContentText("NO LOCATIONS TO PRINT THEIR DATA");
			        
			        alert.show();
			 }
			 
		 }
		 
	}
	
	public BorderPane getPane(){

		next.setAlignment(Pos.CENTER_RIGHT);
		next.setPrefSize(100, 20);
		
		previous.setAlignment(Pos.CENTER_RIGHT);
		previous.setPrefSize(100, 20);
		
		HBox hb=new HBox(10);
		hb.getChildren().addAll(previous,next,load);
		hb.setAlignment(Pos.CENTER);
		
		

		
		n.setBottom(hb);
		n.setTop(location_name);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		
		next.setOnAction(e->{
			if(!stack1.isEmpty()) {
			stage.close();
			}
			if(!stack1.isEmpty()) {
				 try{		
					 stack2.push((LBSTNode)stack1.pop());
					 dateHasMaximumMartyrs(((LBSTNode)stack2.peek()).getDateBST().getRoot());
					location_name.setText("Location name= "+ ((LBSTNode)stack2.peek()).getLocationName()+"\nEarliest date that contains martyrs= "+
							 (((LBSTNode)stack2.peek()).getDateBST().findMin().getDate())+
							 "\nThe latest dates that has martyrs= "+(((LBSTNode)stack2.peek()).getDateBST().findMax().getDate())+
							 "\nThe date that has the maximum number of martyrs= "+date_has_max_martyrs
							 );		
				 } catch(NullPointerException exc) {  //this may occur when the location tree node does not have date tree  or when the district doesn't have a locations 
						 if(stack2.peek()!=null) {  //if the location does not has date inside it (does not has martyrs...)
							 location_name.setText("Location name= "+ ((LBSTNode)stack2.peek()).getLocationName()+"\nEarliest date that contains martyrs= NA"+
									 "\nThe latest dates that has martyrs= NA"+
									 "\nThe date that has the maximum number of martyrs= NA"
									 );	 
							 }
						 else { //if stack2 is empty that means that there is no locations in the whole district 
							 System.out.println("no locations to print their data");
						 }

					 }
				 
					load.setOnAction(act->{
						MainStage.last_loaded_location=(LBSTNode)stack2.peek();
						
						BorderPane n =new BorderPane();
						Label response=new Label("Number of dates for "+((LBSTNode)stack2.peek()).getLocationName()+" is "+((LBSTNode)stack2.peek()).getDateBST().number_of_dates);
						response.setFont(new Font(20));
						n.setCenter(tableLocation());
						n.setBottom(response);
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
				        alert.setTitle("location loaded successfully");
				        alert.setContentText("SINCE YOU LOADED THE Location "+((LBSTNode)stack2.peek()).getLocationName()+"\nSO THE DATE NAVIGATION WILL BE ONLY IN \n"+((LBSTNode)stack2.peek()).getLocationName()+"'S DATES");
				        
				        alert.show();
						
						Scene scene=new Scene(n,400,300);
						stage.setScene(scene);
						stage.show();
					});
			}
		});
		
		previous.setOnAction(e->{
			
			if(!stack2.isEmpty() && stack2.size()!=1) {
				System.out.println(((LBSTNode)stack2.peek()).getLocationName());
			stage.close();
			}
			if(!stack2.isEmpty() && stack2.size()!=1) {
				 try{		
					 stack1.push((LBSTNode)stack2.pop());
					 dateHasMaximumMartyrs(((LBSTNode)stack2.peek()).getDateBST().getRoot());
					 location_name.setText("Location name= "+((LBSTNode)stack2.peek()).getLocationName()+
							 "\nEarliest date that contains martyrs= "+ (((LBSTNode)stack2.peek()).getDateBST().findMin().getDate())+
							 "\nThe latest dates that has martyrs= "+(((LBSTNode)stack2.peek()).getDateBST().findMax().getDate())+
							 "\nThe date that has the maximum number of martyrs= "+date_has_max_martyrs
							 );	
				 } catch(NullPointerException exc) {  //this may occur when the location tree node does not have date tree  or when the district doesn't have a locations 
					 if(stack2.peek()!=null) {  //if the location does not has date inside it (does not has martyrs...)
						 location_name.setText("Location name= "+ ((LBSTNode)stack2.peek()).getLocationName()+"\nEarliest date that contains martyrs= NA"+
								 "\nThe latest dates that has martyrs= NA"+
								 "\nThe date that has the maximum number of martyrs= NA"
								 );	
					 }
					 else	//if stack2 is empty that means that there is no locations in the whole district 
					 System.out.println("no locations to print their data");
					 
				 }
				 
					load.setOnAction(act->{
						MainStage.last_loaded_location=(LBSTNode)stack2.peek();
						

						BorderPane n =new BorderPane();
						Label response=new Label("Number of dates for "+((LBSTNode)stack2.peek()).getLocationName()+" is "+((LBSTNode)stack2.peek()).getDateBST().number_of_dates);
						response.setFont(new Font(20));
						n.setCenter(tableLocation());
						n.setBottom(response);
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
				        alert.setTitle("location loaded successfully");
				        alert.setContentText("SINCE YOU LOADED THE Location "+((LBSTNode)stack2.peek()).getLocationName()+"\nSO THE DATE NAVIGATION WILL BE ONLY IN \n"+((LBSTNode)stack2.peek()).getLocationName()+"'S DATES");
				        
				        alert.show();
						
						Scene scene=new Scene(n,400,300);
						stage.setScene(scene);
						stage.show();
					});
			}
		});
		
		
		return n ;
	}
	
//................................................................................................
	Date date_has_max_martyrs=null;
	int max_martyrs=0;
	
	public void dateHasMaximumMartyrs(DateNode n) {  //this method changes the value of the date_has_max_martyr to which date hast the maximum martyrs
		if(n==null) return;
		
		dateHasMaximumMartyrs(n.getLeft());
		if(n.getMartyrs().size()>max_martyrs) {
			date_has_max_martyrs=n.getDate();
				max_martyrs=n.getMartyrs().getSize();
			}
		dateHasMaximumMartyrs(n.getRight());
		
	}
	
//................................................................................................. 	
	
	
	
	private int height(LBSTNode root) {
		if(root==null) return -1;
		else {
			int left=height(root.getLeft());
			int right=height(root.getRight());
			
			if(left>right)
				return left+1;
			else
				return right+1;
		}
	}
	
	public void levelByLevelTraversalStack1(LBSTNode root,StackLinkedList st) {
		int height=height(root);
		System.out.println(height);
		for(int i=0;i<=height;i++) {
			levelTraversal(root,i, st);
		}
		
		reverseStack(st);
		System.out.println();
	}
	
	private void levelTraversal(LBSTNode current,int level,StackLinkedList st) {
		if(current==null) return;
		if(level==0) st.push(current);
		else if(level>0) {
			levelTraversal(current.getLeft(), level - 1,st);
			levelTraversal(current.getRight(), level - 1,st);
		}
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
				
				getDateData(current.getLeft());
				getMartyrsForTable(current.getMartyrs().getHead());
				getDateData(current.getRight());
			}
			
		private void getMartyrsForTable(Node current) {
				if(current==null) return;
				Node move=current;
				
				while(move!=null) {
					array.add( ((Martyr)move.getData()) );
					move=move.getNext();
				}
			}

//.......................................................................................................
		
		public void read(LBSTNode d) {  //this mehod is to add the data to the obsarvable list...
			array.clear();// every time i read i clear the array
					
					DateNode l=d.getDateBST().getRoot();
					
					if(l==null) {
						Martyr r =new Martyr("NA",new Date("1/1/1"),(byte)0,d.getLocationName(),MainStage.last_loaded_district.getDistrictName(),'F');
						array.add(r);
					}
					else {
						getDateData(l);
					}				
					
		}
			
//.......................................................................................................
 			
		public VBox tableLocation() {
			
			s.setItems(array);

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
				
				TableColumn Location=new TableColumn("location name");
				Location.setMinWidth(50);
				Location.setCellValueFactory(
						new PropertyValueFactory<Martyr,String>("location")
						);
				
				TableColumn district=new TableColumn("ditrict name");
				district.setMinWidth(50);
				district.setCellValueFactory(
						new PropertyValueFactory<Martyr,String>("District")
						);
				
				read(MainStage.last_loaded_location);
				
				
				s.getColumns().clear();
				s.getColumns().addAll(name,age,date,gender,Location,district);
				

				
				VBox vb=new VBox(10);
				vb.getChildren().addAll(s);
				vb.setAlignment(Pos.CENTER);
				
				return vb;
			}			
}
