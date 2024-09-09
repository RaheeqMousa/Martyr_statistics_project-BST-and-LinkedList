
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

public class NavigateDistrictsPane {

	private BorderPane n;
	// private TextArea ta=new TextArea();
	private Button next;
	private Button previous;
	private Button load;
	private Label district_name;
	private StackLinkedList stack1;
	private StackLinkedList stack2;
	// public static DBSTNode last_loaded_district;

	private Stage stage = new Stage();

	private TableView<Martyr> table = new TableView<>();
	private ObservableList<Martyr> array = FXCollections.observableArrayList();

	public NavigateDistrictsPane() {
		MainStage.last_loaded_district = MainStage.dlist.getRoot(); // initially the last loaded district will be the
																	// root, so if the user hasn't loaded any
																	// district....
		stack1 = new StackLinkedList();
		stack2 = new StackLinkedList();
		next = new Button("next district");
		previous = new Button("previous district");
		load = new Button("load district");
		district_name = new Label();
		district_name.setFont(new Font(15));

		fillDisrictsIntoStack1(stack1);
		n = new BorderPane();
		try {
			System.out.println(stack1.peek());
			Object b = (DBSTNode) stack1.pop();  //when the screen is opened the top object of the stack must be shown,
			stack2.push(b);
			System.out.println(b);
			district_name.setText("District name= " + ((DBSTNode) stack2.peek()).getDistrictName()
					+ "\nNumber of Martyrs= " + ((DBSTNode) stack2.peek()).martyrsInsideDIstrict());

			//load button shows the martyrs of the district and the number of locations in the district
			load.setOnAction(act -> {
				MainStage.last_loaded_district = (DBSTNode) stack2.peek();

				BorderPane n = new BorderPane();
				Label response = new Label("Number of LOCATIONS for " + ((DBSTNode) stack2.peek()).getDistrictName()
						+ " is " + ((DBSTNode) stack2.peek()).getLocations().getNumberOfLocations());
				n.setCenter(tableDistrict((DBSTNode) stack2.peek()));
				n.setBottom(response);
				response.setFont(new Font(20));

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("district loaded successfully");
				alert.setContentText("SINCE YOU LOADED THE DISTRICT" + ((DBSTNode) stack2.peek()).getDistrictName()
						+ "\nSO THE LOCATIONS NAVIGATION WILL BE ONLY IN \n"
						+ ((DBSTNode) stack2.peek()).getDistrictName() + "'S LOCATIONS");

				alert.show();

				Scene scene = new Scene(n, 300, 300);
				stage.setScene(scene);
				stage.show();
			});

		} catch (NullPointerException exc) {   //if a null pointer exception occured that means that the system is empty of martyrs
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("district loaded successfully");
			alert.setContentText("NO DISTRICTS IN THE SYSTEM TO PRINT THEIR DATA");

			alert.show();
			System.out.println("no districts to print their data");
		}

	}

	public BorderPane getPane() {

		next.setAlignment(Pos.CENTER_RIGHT);
		next.setPrefSize(100, 20);

		previous.setAlignment(Pos.CENTER_RIGHT);
		previous.setPrefSize(100, 20);

		HBox hb = new HBox(10);
		hb.getChildren().addAll(previous, next, load);
		hb.setAlignment(Pos.CENTER);

		n.setBottom(hb);
		n.setTop(district_name);

		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		next.setOnAction(e -> {
			if (!stack1.isEmpty()) {/*this is useful to close the screen which contains
			 						  the district martyrs and number of location
			 						  when we go to the next district district ,
			 						  note:if stack1 became empty, means we reached the last element in districts
			 						that means that the stage should not be closed because the screen will not show a new district
				*/
				stage.close();
			}
			if (!stack1.isEmpty()) {
				try {
					stack2.push((DBSTNode) stack1.pop());//delete top of stack1 and put it in the other stack
					
					district_name.setText(((DBSTNode) stack2.peek()).getDistrictName() + "\nNumber of Martyrs= "
							+ ((DBSTNode) stack2.peek()).martyrsInsideDIstrict());
				} catch (NullPointerException exc) {
					System.out.println("no districts to print their data");
				}

				//load button shows the martyrs of the district and the number of locations in the district
				load.setOnAction(act -> {
					MainStage.last_loaded_district = (DBSTNode) stack2.peek();

					BorderPane n = new BorderPane();
					Label response = new Label("Number of LOCATIONS for " + ((DBSTNode) stack2.peek()).getDistrictName()
							+ " is " + ((DBSTNode) stack2.peek()).getLocations().getNumberOfLocations());
					response.setFont(new Font(20));
					n.setCenter(tableDistrict((DBSTNode) stack2.peek()));
					n.setBottom(response);

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("district loaded successfully");
					alert.setContentText("SINCE YOU LOADED THE DISTRICT" + ((DBSTNode) stack2.peek()).getDistrictName()
							+ "\nSO THE LOCATIONS NAVIGATION WILL BE ONLY IN \n"
							+ ((DBSTNode) stack2.peek()).getDistrictName() + "'S LOCATIONS");

					alert.show();

					Scene scene = new Scene(n, 300, 300);
					stage.setScene(scene);
					stage.show();
				});
			}
		});

		previous.setOnAction(e -> {

			if (!stack2.isEmpty() && stack2.size() != 1) {/*this is useful to close the screen which contains
							  the district martyrs and number of location
							  when we go to the previous district district ,
							  note:if stack2 became empty, means we reached the first element in districts
							that means that the stage should not be closed because the screen will not show a new district
			*/
				System.out.println(((DBSTNode) stack2.peek()).getDistrictName());
				stage.close();
			}
			if (!stack2.isEmpty() && stack2.size() != 1) {
				try {
					stack1.push((DBSTNode) stack2.pop());//delete top of stack2 and put it into stack1
					district_name.setText(((DBSTNode) stack2.peek()).getDistrictName() + "\nNumber of Martyrs= "
							+ ((DBSTNode) stack2.peek()).martyrsInsideDIstrict());
				} catch (NullPointerException exc) {
					System.out.println("no districts to print their data");
				}

				//load button shows the martyrs of the district and the number of locations in the district
				load.setOnAction(act -> {
					MainStage.last_loaded_district = (DBSTNode) stack2.peek();

					BorderPane n = new BorderPane();
					Label response = new Label("Number of LOCATIONS for " + ((DBSTNode) stack2.peek()).getDistrictName()
							+ " is " + ((DBSTNode) stack2.peek()).getLocations().getNumberOfLocations());
					response.setFont(new Font(20));
					n.setCenter(tableDistrict((DBSTNode) stack1.peek()));
					n.setBottom(response);

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("district loaded successfully");
					alert.setContentText("SINCE YOU LOADED THE DISTRICT" + ((DBSTNode) stack2.peek()).getDistrictName()
							+ "\nSO THE LOCATIONS NAVIGATION WILL BE ONLY IN \n"
							+ ((DBSTNode) stack2.peek()).getDistrictName() + "'S LOCATIONS");

					alert.show();

					Scene scene = new Scene(n, 300, 300);
					stage.setScene(scene);
					stage.show();
				});
			}
		});

		return n;
	}

//........................................................................................................	
	public void fillDisrictsIntoStack1(StackLinkedList st) {
		fillDisrictsIntoStack1(MainStage.dlist.getRoot(), st); 
		reverseStack(st); // i want the smallest district be in th top of the stack
		System.out.println("data filled in stack1 for district navigation");
	}

	//given the root of the bst i want to inorder traverse the districts bts
	private void fillDisrictsIntoStack1(DBSTNode node, StackLinkedList st) {
		if (node == null) // this is the base case....
			return;

		fillDisrictsIntoStack1(node.getLeft(), st);

		st.push(node);//push the current node into the stack

		fillDisrictsIntoStack1(node.getRight(), st);
	}

	private void reverseStack(StackLinkedList st) {//the stack top will contains the largest district's ascii
		StackLinkedList ll = new StackLinkedList();

		while (!st.isEmpty()) {// when this loop ends the stack ll top will contain the smallest district's ascii
			System.out.println(((DBSTNode) st.peek()).getDistrictName());
			ll.push(st.pop());
		}

		StackLinkedList aa = new StackLinkedList();

		while (!ll.isEmpty()) {// when this loop ends the stack aa top will contain the lagest district's ascii
			System.out.println(((DBSTNode) ll.peek()).getDistrictName());
			aa.push(ll.pop());
		}

		while (!aa.isEmpty()) {// when this loop ends the original stack (st) top will contain the smallest district's ascii
			System.out.println(((DBSTNode) aa.peek()).getDistrictName());
			st.push(aa.pop());
		}

		System.out.println(st.peek());
	}

//...........................................................................................................................

	
	//all of this methods helped filling data into table 
	private void putDistrictDataIntoTable(DBSTNode d) {
		getLocationData(d.getLocations().getRoot());// I want to traverse the district's locations BST
	}

	private void getLocationData(LBSTNode current) {//inorder traverse in locations
		if (current == null)
			return;

		getLocationData(current.getLeft());
		getDateData(current.getDateBST().getRoot());//in each location node i want to traverse its date bst
		getLocationData(current.getRight());
	}

	private void getDateData(DateNode current) {//inorder traverse in dates
		if (current == null)
			return;

		getDateData(current.getLeft());
		getMartyrsForTable(current.getMartyrs().getHead());//in each date node i want to traverse its martyr linked list
		getDateData(current.getRight());
	}

	private void getMartyrsForTable(Node current) {//traverse martyr linked list using the passed linked list front 
		if (current == null)
			return;
		Node move = current;

		while (move != null) {
			array.add(((Martyr) move.getData()));
			move = move.getNext();
		}
	}

//.......................................................................................................

	public void read(DBSTNode d) { // this mehod is to add the data to the obsarvable list...
		array.clear();// every time i read i clear the array

		LBSTNode l = d.getLocations().getRoot();

		// district does not have locations
		if (l == null) {
			Martyr r = new Martyr("NA", new Date("1/1/1"), (byte) 0, "NA", (String) d.getDistrictName(), 'F');
			array.add(r);
		} else {
			putDistrictDataIntoTable(d);
		}

	}

//.......................................................................................................

	public VBox tableDistrict(DBSTNode district_node) {

		table.setItems(array); //any changes on the observable array will occurr on the table

		TableColumn name = new TableColumn("martyr name");
		name.setMinWidth(50);
		name.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));

		TableColumn age = new TableColumn("martyr age");
		age.setMinWidth(50);
		age.setCellValueFactory(new PropertyValueFactory<Martyr, String>("age"));

		TableColumn gender = new TableColumn("gender");
		gender.setMinWidth(50);
		gender.setCellValueFactory(new PropertyValueFactory<Martyr, String>("gender"));

		TableColumn date = new TableColumn("date of death");
		date.setMinWidth(50);
		date.setCellValueFactory(new PropertyValueFactory<Martyr, Date>("date_of_death"));

		TableColumn district = new TableColumn("ditrict name");
		district.setMinWidth(50);
		district.setCellValueFactory(new PropertyValueFactory<Martyr, String>("District"));

		read(MainStage.last_loaded_district);

		table.getColumns().clear();
		table.getColumns().addAll(name, age, date, gender, district);

		VBox vb = new VBox(10);
		vb.getChildren().addAll(table);
		vb.setAlignment(Pos.CENTER);

		return vb;
	}
}
