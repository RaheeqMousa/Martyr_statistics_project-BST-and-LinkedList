

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrintDistrict {
	
	private BorderPane n ;
	private ComboBox<String> districts;
	private Label response;
	private String martyrs_data="";
	
	private TableView<Martyr> s=new TableView<>();
	private ObservableList<Martyr> array= FXCollections.observableArrayList();
	
	public PrintDistrict() {
		 n =new BorderPane();
		 response =new Label();
		 districts=new ComboBox<String>();
	}
	
	public BorderPane getPane(){

		districts.setValue("select district");
		districts.setPrefWidth(60);
		FunctionsStage f=new FunctionsStage();
		districts=f.getDistrictsInComboBox();
		
		n.setCenter(districts);
		
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10,30,10,30));
		
		districts.setOnAction(e->{
			response.setFont(new Font(15));
			if(districts.getValue().equalsIgnoreCase("select district")) {
				response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
			}
			else {
				DBSTNode D=MainStage.dlist.find(districts.getSelectionModel().getSelectedItem());
				
				HBox hb=new HBox(10);
				
				Button print =new Button("print district");
				print.setOnAction(action->{
					printDistrict(D);
				});
				
				hb.getChildren().addAll(districts,print);
				n.setCenter(tableDistrict(D));
				n.setBottom(hb);
			}
			
		});
		
		
		return n ;
	}
	
	public VBox tableDistrict(DBSTNode district_node) {
		
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
			
			district_node=MainStage.dlist.find(districts.getSelectionModel().getSelectedItem());
			read(district_node);
			
			
			s.getColumns().clear();
			s.getColumns().addAll(name,age,date,gender,Location,district);
			

			
			VBox vb=new VBox(10);
			vb.getChildren().addAll(s);
			vb.setAlignment(Pos.CENTER);
			
			return vb;
		}	
		
		public void read(DBSTNode d) {  //this mehod is to add the data to the obsarvable list...
			array.clear();// every time i read i clear the array
					
					LBSTNode l=d.getLocations().getRoot();
					
					//district does not have locations
					if(l==null) {
						Martyr r =new Martyr("NA",new Date("1/1/1"),(byte)0,"NA",(String)d.getDistrictName(),'F');
						array.add(r);
					}
					else {
						putDistrictDataIntoTable(d);
					}				
					
		}
		
		public void printDistrict(DBSTNode n ) {
			
			File f=new File((String)n.getDistrictName());
			
			if(!f.exists())
				try {
					f.createNewFile();  //create a file that is called with the name of the district
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			try(DataOutputStream s=new DataOutputStream(new FileOutputStream(f))){
				

				
				if(n.getLocations().getRoot()!=null) {  //if there exist locations in the district
					martyrs_data="";
					getMartyrsData(n);//this append the data i want to print on the file to the "martyr_data" string
					byte[] b= martyrs_data.getBytes();	
					s.write(b); 
				}
				else if(n.getLocations().getRoot()==null){  //if there does not exist locations in the district
						String str=n.getDistrictName()+" district : NO LOCATIONS AND MARTYRS DATA\n";
						byte[] b= str.getBytes();
						s.write(b);
						
					}					
					
					
					String r="Total Martyrs = "+n.martyrsInsideDIstrict();
					byte[] b= r.getBytes();
					s.write(b);
				
			}catch(IOException ex) {
				System.out.println();
			}
			
		}
		
//........................................................................................................................		
		private void getMartyrsData(DBSTNode d) {//the district is passed to the method
			getMartyrsDataInLocation(d.getLocations().getRoot());
		}
		
		private void getMartyrsDataInLocation(LBSTNode current) {  //traverse in all of the district locations
			if(current==null) return;
			
			getMartyrsDataInLocation(current.getLeft());
			getMartyrsDataInDate(current.getDateBST().getRoot()) ;//in each location traverse its dates bst
			getMartyrsDataInLocation(current.getRight());
		}
		
		private void getMartyrsDataInDate(DateNode current) {//traverse in the date bst
			if(current==null) return;
			
			getMartyrsDataInDate(current.getLeft());
			getMartyrsData(current.getMartyrs().getHead());//i want to traverse martyr in each date
			getMartyrsDataInDate(current.getRight());
		}
		
		private void getMartyrsData(Node current) {
			//now i will traverse the martyrs linked list depending on the passed front of linked list
			if(current==null) return;
			Node move=current;
			
			while(move!=null) {  //append the dtring result i want to print on the file....
				martyrs_data=martyrs_data+move.getData().toString()+"\n";
				move=move.getNext();
			}
		}
		
//...........................................................................................................................
		
		private void putDistrictDataIntoTable(DBSTNode d) {  //pass district node to the table
			getLocationData(d.getLocations().getRoot());//traverse in the district's locations
		}
		
		private void getLocationData(LBSTNode current) {//traverse in locations
			if(current==null) return;
			
			getLocationData(current.getLeft());
			getDateData(current.getDateBST().getRoot()) ;//  traverse in the location's date bst
			getLocationData(current.getRight());
		}
		
		private void getDateData(DateNode current) {
			if(current==null) return;
			
			getDateData(current.getLeft());
			getMartyrsForTable(current.getMartyrs().getHead());//traverss in the date's martyr linked list
			getDateData(current.getRight());
		}
		
		private void getMartyrsForTable(Node current) {
			//now i will traverse the martyrs linked list depending on the passed front of linked list
			if(current==null) return;
			Node move=current;
			
			while(move!=null) {//i will add each martyr i meet to the table view
				array.add( ((Martyr)move.getData()) );
				move=move.getNext();
			}
		}
		
		
		
//.......................................................................................................
		
}
