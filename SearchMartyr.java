
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SearchMartyr {

	private VBox vb;
	private TextField name;
	private Label response;
	private Button search;
	private HBox searchPane;
	private ComboBox<String> choice;
	private Button back=new Button("back");
	
	private static TableView<Martyr> martyrsTable=new TableView<>();
	private static ObservableList<Martyr> martyrsArray= FXCollections.observableArrayList();
	
	private static ComboBox<String> districts;
	private static ComboBox<String> locations;
	private static ObservableList<String> district_location_array ;
	
	public SearchMartyr(){
		 vb=new VBox(10);
		
		 name=new TextField("Enter Martyr Name to see its record");
		 searchPane=new HBox(30);
		 response=new Label("Enter Martyr name to see its record:");
		 response.setFont(new Font(15));
		 search=new Button("search");
		 choice=new ComboBox<>();
		 locations=new ComboBox<>();
		 locations.setValue("enter location");
		 choice.setValue("what do you about martyr?");
		 districts=FunctionsStage.getDistrictsInComboBox();
		 districts.setValue("enter district");
		 district_location_array= FXCollections.observableArrayList(districts.getItems());
	}
	
	public Pane getPaneSearchMartyr() {
		BorderPane mainPane=new BorderPane();
		
		//initially the main show is the choice check box
		mainPane.setCenter(choice);
		
		vb.setPadding(new Insets(10,20,10,20));
		vb.setAlignment(Pos.CENTER);
		choice.getItems().addAll("district, and martyr name","district,location,and martyr name","just her/his name");
		
		//every time we choose a district we must change the values in teh obsservable array in order to change the locations values of the check box locations
		districts.setOnAction(updateLocationComboBox->{
			district_location_array.clear();
				FunctionsStage s=new FunctionsStage();
				s.fillLocationsIntoArray(district_location_array, MainStage.dlist.find(districts.getValue()));
			});
		locations.setItems(district_location_array);
		
	choice.setOnAction(action->{
		
		//if the user just know the martyr's name -> the screen will show textfield,buttons (back,search)
			if(choice.getValue()=="just her/his name") {
				mainPane.getChildren().clear();
						
				vb.getChildren().addAll(response,name,searchPane);
				searchPane.getChildren().addAll(back,search);
				searchPane.setAlignment(Pos.CENTER);
				mainPane.setLeft(vb);	
				search.setOnAction(e->{		
					martyrsArray.clear();
					districtInOrderTraversal(MainStage.dlist.getRoot(),name.getText());
					
					mainPane.setCenter(martyrsTable);
				});
				
				back.setOnAction(e->{ // the screen will show the choice check box again
					searchPane.getChildren().clear();
					vb.getChildren().clear();
					choice.setValue("what do you knwo about martyr??");
					mainPane.setCenter(choice);
				});
								
			}
		
	//if the user just know the martyr's name,location, and its district -> the screen will show textfield,buttons (back,search), location and district combo boxes
			else if(choice.getValue()=="district,location,and martyr name") {	
				mainPane.getChildren().clear();
				searchPane.getChildren().addAll(back,districts,locations,search);
				vb.getChildren().addAll(response,name,searchPane);
		
				mainPane.setLeft(vb);
				
				search.setOnAction(e->{		
					martyrsArray.clear();
					martyrsInSpecificLocation(districts.getValue(),locations.getValue(),name.getText());
					mainPane.setCenter(martyrsTable);
				});
				
				back.setOnAction(e->{// the screen will show the choice check box again
					
					searchPane.getChildren().clear();
					vb.getChildren().clear();
					choice.setValue("what do you know about martyr?");
					mainPane.setCenter(choice);
				});
			}
		
	//if the user just know the martyr's name, and his/her district -> the screen will show textfield,buttons (back,search),district combo box	
			else if(choice.getValue()=="district, and martyr name") {
				mainPane.getChildren().clear();
				searchPane.getChildren().addAll(back,districts,search);
				vb.getChildren().addAll(response,name,searchPane);
				
				mainPane.setLeft(vb);
				
				search.setOnAction(e->{		
					martyrsArray.clear();
					martyrsInSpecificDistrict(districts.getValue(),name.getText());
					mainPane.setCenter(martyrsTable);
				});
				
				back.setOnAction(e->{	// the screen will show the choice check box again
					searchPane.getChildren().clear();
					vb.getChildren().clear();
					choice.setValue("what do you know about martyr?");
					mainPane.setCenter(choice);
				});
			}
		
		}) ;
	
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		return mainPane;
	}	
//...............................................................................................

//this is used when the user only know the location name,district name, and martyr name	
//this method fill the sraching for martyrs into a table view	
	public static void martyrsInSpecificLocation(String district_name,String location_name,String martyr_name) {
		DBSTNode searched_district_node= MainStage.dlist.find(district_name);
		LBSTNode searched_location_node=searched_district_node.getLocations().find(location_name);
		dateInOrderTraversal(searched_location_node.getDateBST().getRoot(),martyr_name);
	}
	
//...............................................................................................	
	
//this is used when the user only know the district name, and martyr name
//this method fill the sraching for martyrs into a table view
	public static void martyrsInSpecificDistrict(String district_name,String martyr_name) {
		DBSTNode searched_district_node= MainStage.dlist.find(district_name);
		locationInOrderTraversal(searched_district_node.getLocations().getRoot(),martyr_name);
	}
	
	
//................................................................................................	
	
//this is used when the user wants to search for the martyr'a part of name in the whole system	
//this method fill the sraching for martyrs into a table view
	public static void districtInOrderTraversal(DBSTNode current, String martyr_name) {
		if(current==null) return; 
				
		districtInOrderTraversal(current.getLeft(),martyr_name);		
		locationInOrderTraversal(current.getLocations().getRoot(),martyr_name);
		districtInOrderTraversal(current.getRight(),martyr_name);	
	}
	
	private static void locationInOrderTraversal(LBSTNode current, String martyr_name) {
		if(current==null) return;
		
		locationInOrderTraversal(current.getLeft(),martyr_name);		
		dateInOrderTraversal(current.getDateBST().getRoot(),martyr_name);
		locationInOrderTraversal(current.getRight(),martyr_name);	
	}
	
	private static void dateInOrderTraversal(DateNode current,String martyr_name) {
		if(current==null) return;
		
		dateInOrderTraversal(current.getLeft(),martyr_name);	
		Martyr r=(Martyr) current.getMartyrs().searchByName(martyr_name);
		if(r!=null) {
			tableDistrict(r) ;
		}
		dateInOrderTraversal(current.getRight(),martyr_name);	
	}
//.......................................................................................................
	

	
//.......................................................................................................
	
	
	public static VBox tableDistrict(Martyr r) {
		
		martyrsTable.setItems(martyrsArray); //now the table view points on "martyrArray", any change on the observable array wil appear on the table

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
			
			martyrsArray.add(r);
			
			
			martyrsTable.getColumns().clear();
			martyrsTable.getColumns().addAll(name,age,date,gender,Location,district);
			

			
			VBox vb=new VBox(10);
			vb.getChildren().addAll(martyrsTable);
			vb.setAlignment(Pos.CENTER);
			
			return vb;
		}	
}
