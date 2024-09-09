
import java.io.File;
import java.net.URI;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FunctionsStage extends Application {

	private NavigateDistrictsPane navigate;
	private PrintDistrict pd;

	public static ComboBox<String> cb = getDistrictsInComboBox();

	BorderPane mainPane;

	Stage stage;
//...................................................................................................

	@Override
	public void start(Stage s) {
		this.stage = s;
		MenuBar m = new MenuBar();

		m.getMenus().addAll(menuDistrict(), menuLocation(), menuMartyrs(), menuHelp(), FileOpenAndSave());

		mainPane = new BorderPane();
		mainPane.setTop(m);
		mainPane.setStyle("-fx-background-color:LIGHTBLUE");

		Scene sc = new Scene(mainPane, 350, 350);
		s.setScene(sc);
		s.setTitle("Palestenian Martyrs Statistics Program");
		s.show();

	}

//...................................................................................................	
	public Menu FileOpenAndSave() {
		Menu fileMenu = new Menu("file");

		MenuItem open = new MenuItem("open");
		open.setOnAction(e -> {
			MainStage main = new MainStage();
			mainPane.setCenter(main.getPane());
		});

		MenuItem save = new MenuItem("save");
		save.setOnAction(e -> {
			try {
				MainStage s = new MainStage();
				s.writeOnCSVFile();
			} catch (NullPointerException ex) {
				System.out.println("No File Choosen");
			}
		});

		MenuItem saveAs = new MenuItem("save as");
		saveAs.setOnAction(e -> {
			try {

				Stage stage = new Stage();
				FileChooser fc = new FileChooser();
				File f = fc.showOpenDialog(stage);

				MainStage s = new MainStage();
				s.writeOnCSVFile(f);
			} catch (NullPointerException ex) {
				System.out.println("No File Choosen");
			}
		});

		fileMenu.getItems().addAll(open, save, saveAs);

		return fileMenu;
	}
//...................................................................................................

	public Menu menuDistrict() {

		Menu district = new Menu("district");


		MenuItem addDistrict = new MenuItem("Add District");
		addDistrict.setOnAction(e -> {
			mainPane.setCenter(getPaneAddDistrict());
		});

		Menu deletedis = new Menu("delete district");
		deletedis.setOnAction(e -> {
			mainPane.setCenter(getPaneDeleteDistrict());
		});

		Menu navigateDistricts = new Menu("navigate");
		navigateDistricts.setOnAction(e -> {
			navigate = new NavigateDistrictsPane();
			mainPane.setCenter(navigate.getPane());
		});

		Menu updateMenu = new Menu("update district");
		updateMenu.setOnAction(e -> {
			mainPane.setCenter(getPaneUpdateDistrict());
		});

		Menu printDistrictMenu = new Menu("print district");
		printDistrictMenu.setOnAction(e -> {
			pd = new PrintDistrict();
			mainPane.setCenter(pd.getPane());
		});



		// district.getItems().addAll(viewDistricts,addDistrict,deletedis,updateMenu,navigateDistricts,printDistrictMenu,SearchByDateMenu);
		district.getItems().addAll(addDistrict, deletedis, updateMenu, navigateDistricts, printDistrictMenu);
		return district;

	}

//...................................................................................................

	public Menu menuLocation() {

		Menu location = new Menu("location");
		MenuItem addLocMenu = new MenuItem("Add Location");

		addLocMenu.setOnAction(e -> {
			mainPane.setCenter(getPaneAddLocation());
		});

		MenuItem updateLocationMenu = new MenuItem("Update Location");
		updateLocationMenu.setOnAction(e -> {
			mainPane.setCenter(getPaneUpdateLocation());
		});

		MenuItem deleteLocationMenu = new MenuItem("Delete Location");
		deleteLocationMenu.setOnAction(e -> {
			mainPane.setCenter(getPaneDeleteLocation());
		});


		MenuItem navigateLocation = new MenuItem("navigate locations");
		navigateLocation.setOnAction(e -> {
			NavigateLocationsPane n = new NavigateLocationsPane();
			mainPane.setCenter(n.getPane());
		});


		location.getItems().addAll(addLocMenu, deleteLocationMenu, updateLocationMenu, navigateLocation);
		return location;

	}

//...................................................................................................

	public Menu menuMartyrs() {

		Menu martyrs = new Menu("Martyr");

		MenuItem InsertMenuItem = new MenuItem("insert martyr");
		InsertMenuItem.setOnAction(e -> {
			InsertMartyrPane insert = new InsertMartyrPane();
			mainPane.setCenter(insert.getPane());
		});

		MenuItem searchMartyrMenuItem = new MenuItem("search martyr");
		searchMartyrMenuItem.setOnAction(e -> {
			SearchMartyr search = new SearchMartyr();
			mainPane.setCenter(search.getPaneSearchMartyr());
		});

		MenuItem navigateMartyrMenuItem = new MenuItem("navigate dates");
		navigateMartyrMenuItem.setOnAction(e -> {
			NavigateDates navigate = new NavigateDates();
			mainPane.setCenter(navigate.getPane());
		});

		MenuItem update_delete = new MenuItem("update/delete martyr");
		update_delete.setOnAction(e -> {
			UpdateDeleteMartyr a = new UpdateDeleteMartyr();
			mainPane.setCenter(a.getPaneSearchLocationMartyr());
		});

		martyrs.getItems().addAll(InsertMenuItem, searchMartyrMenuItem, navigateMartyrMenuItem, update_delete);
		return martyrs;

	}

//...................................................................................................

	public Menu menuHelp() {

		Menu help = new Menu("HELP");

		MenuItem instructions = new MenuItem("instructions");
		instructions.setOnAction(act -> {

			Label instLbl = new Label("" + "HERE ARE SOME INSTRUCTIONS THAT MAY HELP YOU:"
					+ "\n1)In district screen when you load a district and go the\nnavigation in the location screen \nYOU WILL NAVIGATE IN THE LAST LOADED DISTRICT"
					+ "\nIF YOU HAVE NOT LOADED ANY DISTRICT\n THE LAST LOADED DISTRICT WOULD BE THE \nFIRST READ DISTRICT FROM THE FILE"
					+ "\n\n2)In martyr screen you navigate dates\n in the last loaded location\n if no location is loaded then the navigation\n will be inside the first location in th first\n read district from file "
					+ "\n\n3)In order to make it easier for the user,\n when the user searches for a martyr by part of name\n he is only asked to enter the name of the martyr ");

			mainPane.setCenter(instLbl);
		});

//		MenuItem send_feedBack=new MenuItem("send FeedBack");
//		
//		send_feedBack.setOnAction(e->{
//			Label lb=new Label("SEND FEEDBACK");
//			
//			TextArea ta=new TextArea();
//			
//			Button send=new Button("send");
//			send.setOnAction(action->{
//				WebView webView = new WebView();
//				
//		        webView.getEngine().loadContent("<a href=\"mailto:raheeqmousa00@gmail.com?subject=Email%20from%20JavaFX%20Application&body=" + ta.getText() + "\">Click here to send email</a>");
//		        Scene scene = new Scene(webView);
//		        Stage stage = new Stage();
//		        stage.setScene(scene);
//		        stage.show();
//			});
//			
//			VBox vb=new VBox(10);
//			vb.getChildren().addAll(lb,ta,send);
//			mainPane.setCenter(vb);
//			
//		});

		help.getItems().addAll(instructions);
		return help;

	}


////.................................................................................................

	ObservableList<String> delDistrictArray = FXCollections.observableArrayList(cb.getItems());

	public BorderPane getPaneDeleteDistrict() {

		fillDisrictsIntoArray(delDistrictArray);
		cb.setItems(delDistrictArray);
		;

		BorderPane n = new BorderPane();
		Label response = new Label();
		Button m = new Button("Delete District");

		cb.setValue("choose district");
		n.setCenter(cb);
		n.setTop(response);
		n.setBottom(m);

		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		m.setOnAction(e -> {
			response.setText("");
			if (cb.getSelectionModel().getSelectedItem() != null) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("confirm delete district");
				alert.setContentText("WHEN YOU CLICK ON \"ok\" BUTTON THE DISTRICT\n"
						+ (String) cb.getValue().toUpperCase() + " WITH ALL ITS LOCATIONS AND DATA WILL BE DELETED.");

				Optional<ButtonType> result = alert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {
					String s = cb.getValue();
					System.out.println(s);
					MainStage.dlist.remove(s);
					response.setText("district deleted");
					response.setFont(new Font(20));
					delDistrictArray.clear();
					fillDisrictsIntoArray(delDistrictArray);
					cb.setValue("choose district");

				}
			}
		});

		return n;
	}

////..................................................................................................

	ComboBox<String> deleteLocationDistricts = new ComboBox<>();
	ComboBox<String> locationsForDelete = new ComboBox<>();
	ObservableList<String> dellocarray = FXCollections.observableArrayList(deleteLocationDistricts.getItems());

	public BorderPane getPaneDeleteLocation() {
		dellocarray.clear();
		Label response = new Label();
		TextField new_name = new TextField("Enter New Name of that Location");
		Button b = new Button("Delete Location");

		BorderPane n = new BorderPane();
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		deleteLocationDistricts = getDistrictsInComboBox();
		deleteLocationDistricts.setValue(MainStage.dlist.getRoot().getDistrictName());
		fillLocationsIntoArray(dellocarray, MainStage.dlist.getRoot());
		locationsForDelete.setItems(dellocarray);
		locationsForDelete.setValue(MainStage.dlist.getRoot().getLocations().getRoot().getLocationName());

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100, 20);

		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(deleteLocationDistricts, locationsForDelete, b);

		n.setCenter(hb);
		n.setTop(response);

		deleteLocationDistricts.setOnAction(act -> {
			dellocarray.clear();
			fillLocationsIntoArray(dellocarray, MainStage.dlist.find(deleteLocationDistricts.getValue()));
			System.out.println(locationsForDelete.getItems());
		});

		b.setOnAction(e -> {

			response.setFont(new Font(15));
			response.setText("");
			try {
				DBSTNode d = MainStage.dlist.find(deleteLocationDistricts.getValue());

				if (d != null) {

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("confirm update district");
					alert.setContentText("WHEN YOU CLICK ON \"ok\" BUTTON THE LOCATION\n"
							+ locationsForUpdate.getValue() + " WITH ALL ITS DATA WILL BE DELETED.");

					Optional<ButtonType> result = alert.showAndWait();

					if (result.isPresent() && result.get() == ButtonType.OK) {
						response.setText("LOCATION DELETED");
						d.getLocations().remove(locationsForDelete.getValue());
						dellocarray.clear();
						fillLocationsIntoArray(dellocarray, MainStage.dlist.find(deleteLocationDistricts.getValue()));
					}
				} else {
					response.setText("THIS DISTRICT DOES NOT EXIST IN THE SYSTEM");
				}
			} catch (NullPointerException exception) {

			}

		});

		return n;
	}

////.................................................................................................

	public BorderPane getPaneAddDistrict() {

		BorderPane n = new BorderPane();
		TextField tf = new TextField("Enter the name of that district");
		Label response = new Label();
		Button b = new Button("add district");

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(90, 20);

		tf.setPrefHeight(40);

		n.setCenter(tf);
		n.setBottom(b);
		n.setTop(response);

		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		b.setOnAction(e -> {
			response.setFont(new Font(15));
			if (tf.getText().isEmpty()) {
				response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
			} else if (MainStage.dlist.contains(tf.getText())) {
				response.setText("THAT DISTRICT NAME ALREADY EXIST");
			} else if (isNumeric(tf.getText())) {
				Alert error = new Alert(AlertType.ERROR);
		        error.setTitle("Error Dialog");
		        error.setHeaderText("error occurred!");
		        error.setContentText("NUMERC NAMES ARE NOT ALLOWED");
		
		        error.showAndWait();
			} else {

				response.setText("DISTRICT ADDED SUCCESSFULLY");
				MainStage.addDistrict(tf.getText());
			}

		});

		return n;
	}

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
//	
////...................................................................................................

	ComboBox<String> forLocation = new ComboBox<String>();

	public BorderPane getPaneAddLocation() {
		VBox vb = new VBox(10);
		forLocation.setValue("choose district");
		BorderPane n = new BorderPane();
		TextField tf = new TextField("Enter the name of that loation");
		Label response = new Label();
		Button b = new Button("add location");
		forLocation = getDistrictsInComboBox();

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100, 20);

		tf.setPrefHeight(40);

		vb.getChildren().addAll(tf, b);
		n.setCenter(vb);
		n.setLeft(forLocation);
		n.setTop(response);

		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		b.setOnAction(e -> {

			boolean same = false;
			if (isNumeric(tf.getText())) {
				Alert error = new Alert(AlertType.ERROR);
		        error.setTitle("Error Dialog");
		        error.setHeaderText("error occurred!");
		        error.setContentText("NUMERC NAMES ARE NOT ALLOWED");
		
		        error.showAndWait();
				return;
			} else if (!isNumeric(tf.getText()) && forLocation.getSelectionModel().getSelectedItem() != null
					&& MainStage.dlist.contains(forLocation.getSelectionModel().getSelectedItem())) {
				DBSTNode d = MainStage.dlist.find(forLocation.getValue());
				same = false;
				System.out.println(forLocation.getValue() + " " + d.getDistrictName());
				same = d.getLocations().contains(tf.getText());

				response.setFont(new Font(15));
				if (tf.getText().isEmpty()) {
					response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
				} else if (same == true) {
					response.setText("THAT LOCATION NAME ALREADY EXISTS");
				} else {
					response.setText("LOCATION ADDED SUCCESSFULLY!");
					System.out.println(d);
					MainStage.addLocation(tf.getText(), d.getDistrictName());
				}
			} else
				response.setText("CHOOSE DISTRICT FROM THE COMBO BOX!");

		});

		return n;
	}

//..................................................................................................

	ComboBox<String> updateLocationDistricts = new ComboBox<>();
	ComboBox<String> locationsForUpdate = new ComboBox<>();
	ObservableList<String> updLocArray = FXCollections.observableArrayList(locationsForUpdate.getItems());

	public BorderPane getPaneUpdateLocation() {

		updLocArray.clear();
		Label response = new Label("Enter New NAME of selected location\nfrom combo boxes");
		TextField new_name = new TextField("");
		Button b = new Button("Update Location");

		BorderPane n = new BorderPane();
		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		updateLocationDistricts = getDistrictsInComboBox();
		updateLocationDistricts.setValue(MainStage.dlist.getRoot().getDistrictName());
		fillLocationsIntoArray(updLocArray, MainStage.dlist.getRoot());
		locationsForUpdate.setItems(updLocArray);
		locationsForUpdate.setValue(MainStage.dlist.getRoot().getLocations().getRoot().getLocationName());

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(100, 20);

		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(updateLocationDistricts, locationsForUpdate, b);

		n.setCenter(hb);
		n.setTop(response);
		n.setBottom(new_name);

		updateLocationDistricts.setOnAction(act -> {
			updLocArray.clear();
			fillLocationsIntoArray(updLocArray, MainStage.dlist.find(updateLocationDistricts.getValue()));
			System.out.println(locationsForUpdate.getItems());
		});

		b.setOnAction(e -> {

			response.setFont(new Font(15));
			response.setText("");
			if (new_name.getText().isEmpty() ) {
				response.setText("ENTER SOMETHING INSIDE TEXT FIELD");
				return;
			} else {
				try {
					DBSTNode d = MainStage.dlist.find(updateLocationDistricts.getValue());

					if (d != null ) {

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm update district");
						alert.setContentText(
								"WHEN YOU CLICK ON \"ok\" BUTTON THE LOCATION\n" + locationsForUpdate.getValue()
										+ " WILL NAME WILL BE UPDATED\n and its data will remain the same.");

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK && !isNumeric(new_name.getText())) {
							response.setText("LOCATION updated");
							// save the reference of the date tree for the location before deleting the
							// location with the old name
							DateBST date_tree_for_location_before_delete = d.getLocations()
									.find(locationsForUpdate.getValue()).getDateBST();
							d.getLocations().remove(locationsForUpdate.getValue());
							// remove location node with the old name
							d.getLocations().add(new_name.getText());
							// add the location with the new name
							LBSTNode updated_location_node = d.getLocations().find(new_name.getText());
							// i got the location node whose name is the new_name
							updated_location_node.setDateBST(date_tree_for_location_before_delete);
							// all locations inside the that location will should be updatedwith the new
							//update location name
							updated_location_node.setAllMartyrWithNewLocationName(new_name.getText());
							updLocArray.clear();
							fillLocationsIntoArray(updLocArray,
									MainStage.dlist.find(updateLocationDistricts.getValue()));
						}else if(isNumeric(new_name.getText())) {
								
						        Alert error = new Alert(AlertType.ERROR);
						        error.setTitle("Error Dialog");
						        error.setHeaderText("error occurred!");
						        error.setContentText("NUMERC NAMES ARE NOT ALLOWED");
						
						        error.showAndWait();
							
						}
					} else {
						response.setText("THIS DISTRICT DOES NOT EXIST IN THE SYSTEM");
					}
				} catch (NullPointerException exception) {

				}

			}
		});

		return n;
	}

//..................................................................................................
	ComboBox<String> districts = new ComboBox<>();
	ObservableList<String> updDistrictArray = FXCollections.observableArrayList(districts.getItems());

	public BorderPane getPaneUpdateDistrict() {

		BorderPane n = new BorderPane();
		districts.setValue("select district");
		fillDisrictsIntoArray(updDistrictArray);
		districts.setItems(updDistrictArray);

		Label response = new Label("select a district to update");
		Button b = new Button("update district");

		b.setAlignment(Pos.CENTER_RIGHT);
		b.setPrefSize(90, 20);

		TextField name = new TextField("ENTER NEW NAME OF DISTRICT");
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(districts, name);
		n.setCenter(hb);
		n.setBottom(b);
		n.setTop(response);

		n.setStyle("-fx-Background-color:LIGHTBLUE");
		n.setPadding(new Insets(10, 30, 10, 30));

		b.setOnAction(e -> {
			response.setFont(new Font(15));

			if (name.getText().isEmpty())
				response.setText("ENTER SOMETHING INSIDE TAT TEXT FIELD");
			else if (districts.getSelectionModel().getSelectedItem() != null
					&& MainStage.dlist.contains(districts.getSelectionModel().getSelectedItem())
					&& !isNumeric(name.getText())) {

				response.setText("");
				DBSTNode node = MainStage.dlist.find(districts.getValue()); // first i searched for the district that
																			// user want to update
				System.out.println(node);
				System.out.println(name.getText());
				DBSTNode node_with_updated_values = new DBSTNode(name.getText()); // created a new district node with
																					// the new name
				node_with_updated_values.setLocations(node.getLocations());// i set the new node's locations the node's
																			// locations
				
				String last_district_name = node.getDistrictName();

				MainStage.addDistrictByObject(node_with_updated_values); // add the new node that have the new name
				node_with_updated_values.setAllMartyrWithNewDistrictName(name.getText());

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("confirm update district");
				alert.setContentText("WHEN YOU CLICK ON \"ok\" BUTTON THE DISTRICT\n" + node.getDistrictName()
						+ " WILL NAME WILL BE UPDATED\n and its data will remain the same.");

				Optional<ButtonType> result = alert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {
					MainStage.dlist.remove(last_district_name); // delete the district node that have the previous name
					response.setText("district updated");
					updDistrictArray.clear();
					fillDisrictsIntoArray(updDistrictArray);
					districts.setValue("choose district");
				}

				MainStage.dlist.printInorder();
				node_with_updated_values.getLocations().printInorder();

			}else if(isNumeric(name.getText())) {
				 Alert error = new Alert(AlertType.ERROR);
			        error.setTitle("Error Dialog");
			        error.setHeaderText("error occurred!");
			        error.setContentText("NUMERC NAMES ARE NOT ALLOWED");
			
			        error.showAndWait();
			} else {
				response.setText("CHOOSE SOMETHING FROM COMBO BOX ABOVE!!");
			}

		});

		return n;
	}

//..................................................................................................

	public static ComboBox<String> getDistrictsInComboBox() {
		ComboBox<String> cb = new ComboBox<>();
		fillDisrictsIntoComboBox(cb);
		return cb;
	}

	private static void fillDisrictsIntoComboBox(ComboBox<String> c) {
		fillDisrictsIntoComboBox(MainStage.dlist.getRoot(), c);
		System.out.println();
	}

	private static void fillDisrictsIntoComboBox(DBSTNode node, ComboBox<String> c) {
		if (node == null) // this is the base case....
			return;

		fillDisrictsIntoComboBox(node.getLeft(), c);

		c.getItems().add(node.getDistrictName());

		fillDisrictsIntoComboBox(node.getRight(), c);
	}

//..................................................................................................

	public static ComboBox<String> getLocationsInComboBox(DBSTNode district) {
		ComboBox<String> cb = new ComboBox<>();
		fillLocationsIntoComboBox(district.getLocations().getRoot(), cb);
		return cb;
	}

	private static void fillLocationsIntoComboBox(LBSTNode node, ComboBox<String> c) {
		if (node == null) // this is the base case....
			return;

		fillLocationsIntoComboBox(node.getLeft(), c);

		c.getItems().add(node.getLocationName());

		fillLocationsIntoComboBox(node.getRight(), c);
	}

//..................................................................................................

	public void fillDisrictsIntoArray(ObservableList<String> c) {
		fillDisrictsIntoArray(MainStage.dlist.getRoot(), c);
		System.out.println();
	}

	private void fillDisrictsIntoArray(DBSTNode node, ObservableList<String> c) {
		if (node == null) // this is the base case....
			return;

		// go to left subtree
		fillDisrictsIntoArray(node.getLeft(), c);

		c.add(node.getDistrictName());
		// go to right subtree
		fillDisrictsIntoArray(node.getRight(), c);
	}

//..................................................................................................

	public void fillLocationsIntoArray(ObservableList<String> c, DBSTNode n) {
		fillLocationsIntoArray(n.getLocations().getRoot(), c);
		System.out.println();
	}

	private void fillLocationsIntoArray(LBSTNode node, ObservableList<String> c) {
		if (node == null) // this is the base case....
			return;

		// left subtree
		fillLocationsIntoArray(node.getLeft(), c);

		c.add(node.getLocationName());
		// go to right subtree
		fillLocationsIntoArray(node.getRight(), c);
	}

//..................................................................................................

	public class UpdateDeleteMartyr { 

		ComboBox<String> districts_searchMartyr = new ComboBox<>();

		ComboBox<String> districts = new ComboBox<>();
		ComboBox<String> locations = new ComboBox<>();
		ObservableList<String> array = FXCollections.observableArrayList(locations.getItems());
		ComboBox<String> choice = new ComboBox<>();
		Button back = new Button("back");
		BorderPane main = new BorderPane();

		public UpdateDeleteMartyr() {
			districts_searchMartyr = new ComboBox<>();

			// WHEN USER KNOWS MARTYRS NAME, LOCATION,DISTRICT
			districts = new ComboBox<>();
			locations = new ComboBox<>();

			choice = new ComboBox<>();
			back = new Button("back");
			main = new BorderPane();
		}

		public Pane getPaneSearchLocationMartyr() {

			// i should add back button when uer click on it the combo box choice set to the
			// center again

			choice.setValue("what do you know about martyr?");
			choice.setPrefSize(220, 50);
			choice.getItems().addAll("district and name", "location, district, and name", "just his/her name");
			main.setCenter(choice);

			choice.setOnAction(e -> {

				// first choice is if the user know the martyr name and district name
				if (choice.getValue().equals("district and name")) {
					deleteUpdateMrtyrByDistrictAndMartyrName();
				}
				// when the user knows just the martyr name
				else if (choice.getValue().equals("just his/her name")) {
					deleteUpdateMartyrByMartyrsName();
				}
				// if user knows martyr's name,location, and district
				else if (choice.getValue().equals("location, district, and name")) {
					deleteUpdateByMartyrNameDistrictAndLocation();
				}

				back.setOnAction(a -> {
					choice.setValue("what do you know about martyr?");
					main.setCenter(choice);
					main.setBottom(null);
				});

			});

			return main;
		}
		
		

		// first choice is if the user know the martyr name and district name
		public void deleteUpdateMrtyrByDistrictAndMartyrName() {

			VBox vb = new VBox(5);
			districts_searchMartyr = getDistrictsInComboBox();
			TextField martyrName = new TextField("Enter martyr's full name");
			martyrName.setPrefSize(280, 50);
			ComboBox<String> d_u = deleteOrUpdate();
			Button done = new Button("done");
			back.setText("back");
			vb.setAlignment(Pos.CENTER);
			done.setPrefSize(80, 30);
			back.setPrefSize(80, 30);

			vb.getChildren().addAll(districts_searchMartyr, martyrName, done, d_u, back);
			main.setCenter(vb);

			done.setOnAction(action -> {

				// get the district name that is chocen from the combo box
				DBSTNode district_node = MainStage.dlist.find(districts_searchMartyr.getValue());
				if (district_node.districtContainsMartyr(martyrName.getText())) {

					if (d_u.getValue() == "delete") {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm delete Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO delete THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							district_node.deleteMartyrInsideDistrict(martyrName.getText());
							Alert s = new Alert(Alert.AlertType.INFORMATION);
							s.setTitle("Martyr deleted successfully");
							s.setContentText("Martyr deleted successfully");
							s.show();
						}
					}

					else if (d_u.getValue() == "update") {// delete martyr and add new martyr with its new data

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm update Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO Update THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							
							district_node.deleteMartyrInsideDistrict(martyrName.getText());
							InsertMartyrPane r = new InsertMartyrPane();
							main.setCenter(r.getPane());
							main.setBottom(back);
							back.setOnAction(backActionInUpdateMartyrByDistrictAndMartyrName -> { //when we go back to the main stage at teh choice "location, district, and name" 
								main.setCenter(vb);
								back.setOnAction(a -> {
									// when the operations is done just return the user to the main combo box
									choice.setValue("what do you know about martyr?");
									main.setCenter(choice);
									main.setBottom(null);
								});
							});

						}

					}

				}

				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Martyr not find in the chosen district");
					alert.setContentText("MARTYR YOU ARE LOOKING FOR DOES \n NOT EXIST IN THE DISTRICT NAME "
							+ district_node.getDistrictName());
					alert.show();
				}
			});

		}

		
		//when user ONLY knows martyrs name
		public void deleteUpdateMartyrByMartyrsName() {
			VBox vb = new VBox(5);
			TextField martyrName = new TextField("Enter martyr's full name");
			martyrName.setPrefSize(280, 50);
			martyrName.setAlignment(Pos.CENTER);
			ComboBox<String> d_u = deleteOrUpdate();
			Button done = new Button("done");
			done.setPrefSize(80, 30);
			back.setAlignment(Pos.CENTER_RIGHT);
			back.setPrefSize(80, 30);
			vb.setAlignment(Pos.CENTER);

			vb.getChildren().addAll(martyrName, done, d_u, back);
			main.setCenter(vb);

			done.setOnAction(action -> {
				DBSTNode district_node = MainStage.dlist.getDistrictWhichContainsMartyr(martyrName.getText());
				System.out.println(district_node);
				if (MainStage.dlist.isMartyrContainsInWholeSystem(martyrName.getText())) {

					if (d_u.getValue() == "delete") {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm delete Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO delete THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							district_node.deleteMartyrInsideDistrict(martyrName.getText());
							Alert s = new Alert(Alert.AlertType.INFORMATION);
							s.setTitle("Martyr deleted successfully");
							s.setContentText("Martyr deleted successfully");
							s.show();
						}
					}

					else if (d_u.getValue() == "update") {

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm update Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO Update THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							district_node.deleteMartyrInsideDistrict(martyrName.getText());
							InsertMartyrPane r = new InsertMartyrPane();
							main.setCenter(r.getPane());
							main.setBottom(back);
							back.setOnAction(backActionInUpdateMartyrByDistrictAndMartyrName -> {	
								main.setCenter(vb);
								back.setOnAction(a -> { 	// the back choice should set the center of the pane with the choice checkbox (reset the botton action to its original action)
									choice.setValue("what do you know about martyr?");
									main.setCenter(choice);
									main.setBottom(null);
								});
							});

						}
					}

				} else if (d_u.getValue()==null || d_u.getValue().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("FILL ALL FIELDS!");
					alert.setContentText("YOU ARE NOT ALLOWD TO REMAIN AN EMPTY FIELDs");
					alert.show();
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Martyr does not exist in the current location");
					alert.setContentText("MARTYR YOU ARE LOOKING FOR DOES \n NOT EXIST IN THE whole system");
					alert.show();
				}
			});
			// search in the whole system
			// if contains...
			// else show alert
		}

		//when user knows the martyr name and location and district
		public void deleteUpdateByMartyrNameDistrictAndLocation() {

			VBox vb = new VBox(5);
			TextField martyrName = new TextField("Enter martyr's full name");
			martyrName.setPrefSize(280, 50);
			martyrName.setAlignment(Pos.CENTER);
			try {
				districts = getDistrictsInComboBox();
				districts.setValue("enter district");
				fillLocationsIntoArray(array, MainStage.dlist.getRoot());
				locations.setItems(array);
				locations.setValue("enter location");
			} catch (NullPointerException ex) {

			}
			ComboBox<String> d_u = deleteOrUpdate();
			Button done = new Button("done");
			done.setPrefSize(80, 30);
			back.setAlignment(Pos.CENTER_RIGHT);
			back.setPrefSize(80, 30);
			vb.setAlignment(Pos.CENTER);

			vb.getChildren().addAll(districts, locations, martyrName, done, d_u, back);
			main.setCenter(vb);

			districts.setOnAction(act -> {
				array.clear();
				fillLocationsIntoArray(array, MainStage.dlist.find(districts.getValue()));
			});
			// i will show the locations depending on the chosen district
			// also check if the martyr inside that location or not, if not show alert

			done.setOnAction(act -> {
				LBSTNode location_node = MainStage.dlist.searchLocationInDistrict(districts.getValue(),
						locations.getValue());
				System.out.println(location_node);
				if (location_node != null && location_node.locationContainsMartyr(martyrName.getText())) {

					if (d_u.getValue() == "delete") {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm delete Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO delete THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							// I should delete the martyr from the specific location
							location_node.deleteMartyrInsideLocation(martyrName.getText());

							Alert s = new Alert(Alert.AlertType.INFORMATION);
							s.setTitle("Martyr deleted successfully");
							s.setContentText("Martyr deleted successfully");
							s.show();
						}
					}

					else if (d_u.getValue() == "update") {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("confirm update Martyr");
						alert.setContentText("ARE YOU SURE YOU WANT TO Update THE MARTYR " + martyrName.getText());

						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.OK) {
							location_node.deleteMartyrInsideLocation(martyrName.getText());
							InsertMartyrPane r = new InsertMartyrPane();
							main.setCenter(r.getPane());
							main.setBottom(back);
							back.setOnAction(backActionInUpdateMartyrByDistrictLocationAndMartyr -> {  
								main.setCenter(vb);                                                   
								back.setOnAction(a -> {			// the back choice should set the center of the pane with the choice checkbox (reset the botton action to its original action)
									choice.setValue("what do you know about martyr?");
									main.setCenter(choice);
									main.setBottom(null);
								});
							});

						}
					}

				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Martyr does not exist in the current location");
					alert.setContentText("MARTYR YOU ARE LOOKING FOR DOES \n NOT EXIST IN " + locations.getValue() + " "
							+ districts.getValue());
					alert.show();
				}

			});

		}

		public ComboBox<String> deleteOrUpdate() {
			ComboBox<String> cb = new ComboBox<>();

			cb.getItems().addAll("delete", "update");

			return cb;
		}

	}

//...................................................................................................
//	Date martyr_date;
//	public Pane getPaneUpdateMartyr(Martyr r) {
//		BorderPane mainPane =new BorderPane();
//		
//		ComboBox<String> choice=new ComboBox<>();
//		choice.setPrefSize(80, 30);  //add values to the combo box
//		choice.setValue("what you want to update?");
//		choice.getItems().add("name");
//		choice.getItems().add("date_of_death");
//		choice.getItems().add("district-location");//you cant change martyr's district with her/her location
//		choice.getItems().add("gender");
//		choice.getItems().add("age");
//		
//		mainPane.setCenter(choice);//initially the pane will contain the combo box at the center
//		
//		choice.setOnAction(e->{  //when user choose something from the combo box the nodes
//								//inside the mainPae will change depending on the choice
//			
//		
//			
//		//now check the value inside the check box
//			
//			if(choice.getValue()=="name") {//this to update name
//				//find location node which contains the martyr
//				LBSTNode location_node=MainStage.dlist.searchLocationInWholeSystem(r.getLocation());
//				
//				
//				TextField newName=new TextField("enter new name of martyr");
//				Button ok=new Button("done");
//				Button back=new Button("back");
//				VBox vb =new VBox(10);
//				vb.getChildren().addAll(newName,ok,back);
//				
//				mainPane.setCenter(vb);
//				back.setOnAction(updateName->{  //back will set the choice combo box to the center again
//					mainPane.setCenter(choice);
//				});
//				
//				ok.setOnAction(updateMartyrName->{
//					//check if the martyr with the updated data matches another martyr's data(name,age,gender,location,district,date)
//				   //Martyr(String name, Date date_of_death, byte age, String location, String district, char gender)
//						Martyr martyr=new Martyr(newName.getText(),r.getDate_of_death(),r.getAge(),r.getLocation(),r.getDistrict(),r.getGender());
//						if(!location_node.locationContainsMartyrData(martyr) && !newName.getText().isEmpty() && !FunctionsStage.isNumeric(newName.getText()) ) {
//							r.setName(newName.getText());
//							showInformationAllert("name",newName.getText());
//						}else if(newName.getText().isEmpty() && FunctionsStage.isNumeric(newName.getText())) {
//							showError();
//						}
//						else {
//							showErrorAllert();
//						}
//				});		
//			}
//			
//			else if(choice.getValue()=="date_of_death") {//this to update date_of_death
//				//find location node which contains the martyr
//				LBSTNode location_node=MainStage.dlist.searchLocationInWholeSystem(r.getLocation());
//				
//				
//				DatePicker newDate=new DatePicker();
//				Button ok=new Button("done");
//				Button back=new Button("back");
//				VBox vb =new VBox(10);
//				vb.getChildren().addAll(newDate,ok,back);
//				
//				mainPane.setCenter(vb);
//				back.setOnAction(updateDate_of_death->{  //back will set the choice combo box to the center again
//					mainPane.setCenter(choice);
//				});
//				
//				newDate.setOnAction(newDateAction->{
//					 martyr_date= Date.from( newDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
//					
//					System.out.println(martyr_date);
//				});
//				
//
//			 ok.setOnAction(updateDate->{	
//					//check if the martyr with the updated data matches another martyr's data(name,age,gender,location,district,date)
//					   //Martyr(String name, Date date_of_death, byte age, String location, String district, char gender)
//					Martyr martyr=new Martyr(r.getName(),martyr_date,r.getAge(),r.getLocation(),r.getDistrict(),r.getGender());
//					if(!location_node.locationContainsMartyrData(martyr)) {
//						System.out.println(r.getDate_of_death());
//						DateNode old_date =MainStage.dlist.getDateInWholSystem(r.getDate_of_death());
//						old_date.getMartyrs().deleteMartyrByWholeData(r);
//						
//						MainStage.addDate(r.getLocation(), r.getDistrict(), martyr_date);  // i have to add the date if it does not exist
//						DateNode new_date =MainStage.dlist.getDateInWholSystem(martyr_date);
//						System.out.println(r.getDistrict()+" "+r.getLocation());
//						MainStage.addSortlyMartyrs(r,  r.getLocation(),r.getDistrict(), martyr_date);
//						showInformationAllert(" date_of_death "," "+r.getDate_of_death());
//					}	
//					else {
//						showErrorAllert();
//							}
//				});
//				
//			}
//			
//			else if(choice.getValue()=="gender") { //this to update the martyr's gender
//				//find location node which contains the martyr
//				LBSTNode location_node=MainStage.dlist.searchLocationInWholeSystem(r.getLocation());
//				
//				
//				ComboBox<String> newGender=new ComboBox<>();
//				newGender.setValue("choose gender");
//				newGender.getItems().add("Female");
//				newGender.getItems().add("Male");
//				Button ok=new Button("ok");
//				Button back=new Button("back");
//				
//				VBox vb=new VBox(5);
//				vb.getChildren().addAll(newGender,ok,back);
//				
//				mainPane.setCenter(vb);
//				
//				ok.setOnAction(updateGender->{
//						//Martyr(String name, Date date_of_death, byte age, String location, String district, char gender)
//					
//					if((newGender.getValue()=="Male" || newGender.getValue()=="Female")) {
//													
//						//check if the martyr with the updated data matches another martyr's data(name,age,gender,location,district,date)
//						
//								Martyr martyr=new Martyr(r.getName(),r.getDate_of_death(),r.getAge(),r.getLocation(),r.getDistrict(),newGender.getValue().charAt(0));
//
//								if(martyr!=null && !location_node.locationContainsMartyrData(martyr)  ) {
//									if(newGender.getValue()=="Female")
//										r.setGender('F');
//									else if(newGender.getValue()=="Male")
//										r.setGender('M');
//									
//									showInformationAllert("gender",r.getGender()+"");		
//								}
//								else {
//									showErrorAllert();
//								}
//
//						}
//						
//				});
//				
//				back.setOnAction(b->{
//					mainPane.setCenter(choice);
//				});
//			}
//			
//			else if(choice.getValue()=="age") {//this to update the martyr's age
//				//find location node which contains the martyr
//				LBSTNode location_node=MainStage.dlist.searchLocationInWholeSystem(r.getLocation());
//				
//				
//	ComboBox<Byte> newAge=new ComboBox<>();
//	for (byte i = 1; i <= 126; i++) {
//	    newAge.getItems().add(i);
//	}
//
//				
//				Button back=new Button("back");
//				Button ok=new Button("done");
//				VBox vb=new VBox(5);
//				vb.getChildren().addAll(newAge,ok,back);
//				
//			
//				mainPane.setCenter(vb);
//				
//				ok.setOnAction(updateAge->{
//					Martyr martyr=new Martyr(r.getName(),r.getDate_of_death(),newAge.getValue(),r.getLocation(),r.getDistrict(),r.getGender());
//					System.out.println(martyr);
//					if(!location_node.locationContainsMartyrData(martyr) ) {
//						//delete martyr in location depending on his/her all data not just name					
//						location_node.deleteMartyrInsideLocation(r);
//						System.out.println("del");
//						r.setAge(newAge.getValue());
//						//add the martyr with his'her updated data to the system
//						MainStage.addSortlyMartyrs(martyr,martyr.getLocation(),martyr.getDistrict(), martyr.getDate_of_death());
//					}
//					else {
//						showErrorAllert();
//					}
//				});	
//				
//				back.setOnAction(b->{
//					mainPane.setCenter(choice);
//				});
//			}
//
//			else if(choice.getValue()=="district-location") {
//				try {
//					districts_ud = getDistrictsInComboBox();
//					districts_ud.setValue("enter district");
//					fillLocationsIntoArray(array_ud, MainStage.dlist.getRoot());
//					locations_ud.setItems(array_ud);
//					locations_ud.setValue("enter location");
//				} catch (NullPointerException ex) {
//
//				}
//				
//				districts_ud.setOnAction(act -> {
//					array_ud.clear();
//					fillLocationsIntoArray(array_ud, MainStage.dlist.find(districts_ud.getValue()));
//				});
//				VBox vb=new VBox(5);
//				Button ok=new Button("ok");
//				Button back=new Button("back");
//				vb.getChildren().addAll(districts_ud,locations_ud,ok,back);
//				
//				
//				mainPane.setCenter(vb);
//				ok.setOnAction(updateDistrictLoc->{	
//					LBSTNode loc_node=MainStage.dlist.searchLocationInDistrict(districts_ud.getValue(), locations_ud.getValue());
//					Martyr martyr=new Martyr(r.getName(),r.getDate_of_death(),r.getAge(),locations_ud.getValue(),districts_ud.getValue(),r.getGender());
//					
//					if(!loc_node.locationContainsMartyrData(martyr)) {
//						//delete martyr in location depending on his/her all data not just name					
//						loc_node.deleteMartyrInsideLocation(r);	
//						r.setDistrict(districts_ud.getValue());
//						r.setLocation(locations_ud.getValue());
//						 
//						MainStage.addDate(martyr.getLocation(), martyr.getDistrict(), martyr.getDate_of_death());
//						//add date because maybe the date does not exist in the new location and district
//						MainStage.addSortlyMartyrs(martyr,loc_node.getLocationName(),martyr.getDistrict(), martyr.getDate_of_death());
//						 showInformationAllert(" district and location ", r.getLocation()+" "+r.getDistrict()+" ") ;
//						
//					}
//					else {
//						showErrorAllert();
//					}
//				});
//				back.setOnAction(b->{
//					mainPane.setCenter(choice);
//				});
//			}
//			
//		});
//		
//		
//		return mainPane;
//	}
//	
//	
//	public void showErrorAllert() {
//  Alert alert = new Alert(AlertType.ERROR);
//  alert.setTitle("Error Dialog");
//  alert.setHeaderText("error occurred!");
//  alert.setContentText("THAT MARTYR DATA \n(NAME,AGE,GENDER,LOCATION,DISTRICT,DATE_OF_DEATH)\nAlready exists in the system.");
//
//  alert.showAndWait();
//}
//	
//	public void showError() {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Error Dialog");
//        alert.setHeaderText("error occurred!");
//        alert.setContentText("NUMERIC NAME ARE NOT ALLOWED\n EMPTY TEXT FIELDS ARE NOT ALLOWED");
//
//        alert.showAndWait();
//	}
//	
//	public void showInformationAllert(String s,String n) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("update Dialog");
//        alert.setHeaderText("updated successfully!");
//        alert.setContentText("marty "+s+ "updated successfully to "+n);
//
//        alert.showAndWait();
//	}
	
//..................................................................................................

	public static void main(String[] args) {
		launch(args);
	}

}
