import java.time.ZoneId;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class InsertMartyrPane{	

	
	private BorderPane addMartyrPane;
	private VBox tfPane;
	private TextField name;
	private ComboBox<Byte> age=new ComboBox<>();
	private DatePicker date_picker;
	private ComboBox<String> gender;
	private Button add;
	private HBox add_back;
	private Label response;
	private ComboBox<String > districts =new ComboBox<>();
	private ComboBox<String> locations=new ComboBox<>();
	private ObservableList<String>  array =FXCollections.observableArrayList(
			districts.getItems());
	Date martyr_date=null;
	
	//create objects in the constructors
	public InsertMartyrPane() {
		gender=new ComboBox<>();
		name=new TextField("Enter name");
		for (byte i = 1; i <= 126; i++) {
		    age.getItems().add(i);
		}
		age.setValue((byte)-1);
		
		response=new Label("Enter name, age, location, date, and gender for Martyr:");
		response.setFont(new Font(15) );
		date_picker=new DatePicker();
	}


	public Pane getPane(){
		
		FunctionsStage s=new FunctionsStage();
		locations.setItems(array);
		districts=FunctionsStage.getDistrictsInComboBox();	
		s.fillLocationsIntoArray(array,MainStage.dlist.getRoot());
		districts.setOnAction(act->{
			array.clear();
			s.fillLocationsIntoArray(array,MainStage.dlist.find(districts.getValue()) );
			System.out.println(locations.getItems());
		});
		
		districts.setValue("choose district");
		locations.setValue("choose location");
		
		gender.setValue("choose gender");
		gender.getItems().add("M");
		gender.getItems().add("F");
			
		
		
		date_picker.setOnAction(e->{
			 martyr_date= Date.from( date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			System.out.println(martyr_date);
		});
		
		addMartyrPane =new BorderPane();
		addMartyrPane.setPadding(new Insets(30,10,30,10));
		addMartyrPane.setStyle("-fx-background-color:LIGHTBLUE");
		
		tfPane=new VBox(10);		
		tfPane.setAlignment(Pos.CENTER);
		
		
		add_back=new HBox(5);	
		add=createButton("add martyr");
		add_back.getChildren().add(add);
		add_back.setAlignment(Pos.CENTER);
		tfPane.getChildren().addAll(name,age,locations,districts,date_picker,gender,add_back);
		
		
		addMartyrPane.setCenter(tfPane);
		addMartyrPane.setBottom(add_back);
		addMartyrPane.setTop(response);
		
		
		return addMartyrPane;
		
	}
	
	public Button createButton(String t) {
		/**
		 * @param 't' input parameter which refers to the label inside the button
		 * @return returns a button with the specified action for that button (The event has been approved  to button by the button's label)
		 */
		
		Button b =new Button(t);
		b.setOnAction(e->{ //this will add a martyr to the system using the texts which entered in the text fields
						  //and of course there is a check that the data is valid before adding the martyr object
			response.setText("");
			if(t.equals("add martyr")) {
				
				
				if(!FunctionsStage.isNumeric(name.getText()) && !name.getText().isEmpty() && (age.getValue()!=(-1)) && districts.getValue()!="choose district" &&locations.getValue()!="choose location"  && !gender.getValue().isEmpty()) {
					//first i should check if the text fields aren't empty
						
					
						try {
							System.out.println("before");
							String n=name.getText();
							byte a=age.getValue();
							char g=gender.getValue().charAt(0);
							String d=districts.getValue();
							String f=locations.getValue();
							
								
							//Martyr(String name, Date date_of_death, byte age, String location, String district, char gender)	
								Martyr m =new Martyr(n,martyr_date,a,f,d,g);	
								
								if(MainStage.dlist.find(districts.getValue()).getLocations().find(locations.getValue()).locationContainsMartyrData(m) ) {
									
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
							        alert.setTitle("MARTYR  \" HAS NOT \"  ADDED SUCCESSFULLY!");
							        alert.setContentText("THAT MARTYR DATA ALREADY EXISTS IN THE CURRENT LOCATION.");
							       
							        alert.show();
							        return;
								}
								
								MainStage.addDate(f, d, martyr_date);
								MainStage.addSortlyMartyrs(m, f, d, martyr_date);
								System.out.println("after");
								response.setText("Martyr added successfully");
								//the add is successfull so i will empty the text field again..
								name.setText("Enter name");
								gender.setValue("choose gender");
								age.setValue((byte)-1);
								
							}catch(NumberFormatException ex) {
								response.setText("oops you may entered string instead of integer for age");
							}catch(IllegalArgumentException x) {
								System.out.println(x.getCause()+ " "+x.getLocalizedMessage());
								response.setText("you may entered invalid gender , enter f or m\n"
										+ "or maybe the date is INVALID");
							}
				}else if(districts.getValue()=="choose district" || locations.getValue()=="choose location" || age.getValue()==-1) {
					response.setText("CHOOSE SOMETHING FROM COMBO BOXES!!");
				}
				else {
					response.setText("There is an empty text field!!"); }
			}
		});
		
		
		return b;
	}
	
	
//...............................................................................................................
	


	
	
	
}
