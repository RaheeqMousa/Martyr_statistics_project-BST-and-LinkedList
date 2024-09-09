



import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import java.io.*;
import java.util.Date;

public class MainStage{
	
    public static DistrictBST dlist=new DistrictBST(); //i made it static in order to access it from other classes
    private static File file;
    private static DataInputStream input=null;
    private boolean isCSVFile=true;
    private Label response=new Label();
    private boolean append_on_existence=false;
    
    public static LBSTNode last_loaded_location;
    
    public static DBSTNode last_loaded_district;
    
    
    
	VBox main;
	Button click;

//...................................................................................................
	
	public VBox getPane() {
		
		main=new VBox(10);
		main.setAlignment(Pos.CENTER);
		
		click=new Button("click here to choose a file!");
		main.getChildren().addAll(response,click);
		main.setStyle("-fx-background-color:LIGHTBLUE;");
		
			
		click.setOnAction(e->{  // i will let the user choose the file he want to read from
			try {
				response.setText("");
				
				if(dlist.getRoot()!=null)  {  //if there exist data in the system
					Alert alert = new Alert(AlertType.CONFIRMATION);
			        alert.setTitle("Question Dialog");
			        alert.setHeaderText("Do you want to append on the existing data??");
			        alert.setContentText("Choose your option:");

			        // add "yes" and "no" buttons to the alert dialog
			        ButtonType yes = new ButtonType("yes");
			        ButtonType no = new ButtonType("no");
			        alert.getButtonTypes().setAll(yes, no);

			        // show the alert dialog and wait for user response
			        alert.showAndWait().ifPresent(response -> {
			            if (response == yes) {
			            	append_on_existence=true;
			            } else if (response == no) {
			            	append_on_existence=false;
			            }
			        });
				}else {
					
				}
				
				FileChooser fc=new FileChooser();
				Stage s=new Stage();
				if(fc!=null) {
					file= fc.showOpenDialog(s);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
			        alert.setTitle("file read successfully");
			        alert.setContentText("DATA READ FROM FILE HAS ADDED SUCCESSFULLY");
			        
			        alert.show();
				}
				else {//since no file choosen but there is an old loaded file, so we will work on it
					if(file==null) {
						System.out.println("no file has chosennn");
					}else {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
				        alert.setTitle("file read");
				        alert.setContentText("you had not chose file,\n file will remain with the last loaded file");
				        
				        alert.show();
						System.out.println("you had not chose file, file will remain with the last loaded file");
					}
				}
				fc.setTitle("choose a file to read from");
				readCSVFile();
				
				
				
			}catch(NullPointerException ex) {  //if the user opened the dialog and had not chose a file a null pointer exception will occur
				System.out.println("no file has chosennn");
			}		
		});
		
			return main;
	}
	
//...................................................................................................
	
	public void readCSVFile() {  //this method is to read data

		if(append_on_existence==false) //if the user chose not to append on the existence data then i will empty the tree
			dlist=new DistrictBST();
		
		isCSVFile=true;
		int count=0;
		int martyrs_counter=0;
		
		try {
		input=new DataInputStream(new FileInputStream(file) );
			String a=input.readLine(); //to skip the header line
			String line="";
			count=0;
			String file_path=file.getAbsolutePath();
			System.out.println(file_path);
			// to avoid any problems with reading a files not in csv 
			if(file_path.charAt(file_path.length()-1)!='v' &&  file_path.charAt(file_path.length()-2)!='s' && file_path.charAt(file_path.length()-3)!='c' ) {
				//the csv file path contains the characters c,s,v in the last of its path
				response.setFont(new Font(20));
				response.setText("this system reads only csv files!");
				isCSVFile=false;
				return; }
				
				
			while((line=input.readLine())!=null) {
				String[] array=line.split(",");
								
				if(array.length!=6) { //that means that the line (row) i read has missing information or more information
					System.out.println(line+" "+count);
					System.out.println("there is a martyr has not added since there is/are missing information");
					continue;
				}
					try {
						//Martyr(String name, Date date_of_death, byte age, String location, String district, char gender) 
						//the arg constructor of the martyr may throw an exceptions "IllegalArgumentException"				
						Date d =new Date(array[1]);
						Martyr r=new Martyr(array[0],d,Byte.parseByte(array[2]),array[3],array[4],array[5].charAt(0));
				
						//note :NumberFormatException is an unchecked exception, and a subclass of IllegalArgumentException.
					
						addDistrict(array[4]);
						addLocation(array[3],array[4]);
						addDate(array[3],array[4],d);
						addSortlyMartyrs(r,array[3],array[4],d);
						
						martyrs_counter++;
						
					}catch(NumberFormatException ex) {						
						count++;
					}catch(IllegalArgumentException e) {
						count++;
					}

			}
			
			dlist.printInorder();
			System.out.println(dlist.getRoot().martyrsInsideDIstrict());
			
			
			
			input.close();

		}catch(FileNotFoundException ex) {
			System.out.println(ex.getCause());
			System.out.println("file not found");
		}catch(IOException e) {
			System.out.println("IO exception");
		}finally {
		              //just to be sure that the input stream is closed!
		     try {
				input.close(); 
			 } catch (IOException e) {
				 e.printStackTrace();
				     }
	    }
		
		//this print statement just to know how many person hasn't problems with their information
		System.out.println("there are "+count+" martyr had problems with data, such as no age or invalid gender or ivalid date");
		//System.out.println("Number of districts ="+dlist.getSize());
		System.out.println("Number of martyrs ="+martyrs_counter);
	}
	
	
//...................................................................................................
	static String str="";
	public void writeOnCSVFile() { //this method is to write the data of martyrs
	
		 
		try(DataOutputStream d=new DataOutputStream(new FileOutputStream(file) )){	
			
			String first_line = "Name,Date,Age,location,District,Gender"+ "\n"; //write the header again
	        byte[] first_bytes = first_line.getBytes();
	        d.write(first_bytes);
			str="";
	       
	        inOrderDistrict(dlist.getRoot());
	        System.out.println(str);
	        byte[] second = str.getBytes();
	        d.write(second);
	        
	        
			System.out.println("Data saved to file successfully.");
			d.close();
		}catch(FileNotFoundException ex) {
			System.out.println("file is not found for write");
		}catch(IOException e) {
			System.out.println("io exception");
		}
	
}
	
	private void inOrderDistrict(DBSTNode d) {
		if(d==null)return;
		
		inOrderDistrict(d.getLeft());
		inOrderLocation(d.getLocations().getRoot());
		inOrderDistrict(d.getRight());
	}
	
	private void inOrderLocation(LBSTNode l) {
		if(l==null) return;
		
		inOrderLocation(l.getLeft());
		inOrderDate(l.getDateBST().getRoot());
		inOrderLocation(l.getRight());
	}
	
	private void inOrderDate(DateNode date) {
		if(date==null) return;
		
		inOrderDate(date.getLeft());
		traverseMartyr(date.getMartyrs().getHead());
		inOrderDate(date.getRight());
	}
	
	private void traverseMartyr(Node m) {
		if(m==null) return;
		Node move=m;
		
		while(move!=null) {
			str=str+m.getData().toString()+"\n";
			move=move.getNext();
		}
		
	}
//...........................................................................................................
	
	public void writeOnCSVFile(File file) { //this method is to write the data of martyrs
		
		 
		try(DataOutputStream d=new DataOutputStream(new FileOutputStream(file) )){	
			
			String first_line = "Name,Date,Age,location,District,Gender"+ "\n"; //write the header again
	        byte[] first_bytes = first_line.getBytes();
	        d.write(first_bytes);
			str="";
	       
	        inOrderDistrict(dlist.getRoot());
	        System.out.println(str);
	        byte[] second = str.getBytes();
	        d.write(second);
	        
	        
			System.out.println("Data saved to file successfully.");
			d.close();
		}catch(FileNotFoundException ex) {
			System.out.println("file is not found for write");
		}catch(IOException e) {
			System.out.println("io exception");
		}
	
}
	
//...........................................................................................................	
	
	public static void addDistrict(String dis_name) {
		if(!dlist.contains(dis_name)) {
			//System.out.println(array[4]);
			dlist.add(dis_name);
			
			if(dlist.getRoot().getRight()==null && dlist.getRoot().getLeft()==null) {
				last_loaded_district=dlist.getRoot();
			}
			System.out.println(last_loaded_district+" last loaded being first added");
		}
		
	}
	
	public static void addDistrictByObject(DBSTNode n) {
		if(n!=null && !dlist.contains(n.getDistrictName())) {
			//System.out.println(array[4]);
			dlist.add(n);
			if(dlist.getRoot().getRight()==null && dlist.getRoot().getLeft()==null) {
				last_loaded_district=dlist.getRoot();
			}
			
			System.out.println(last_loaded_district+" last loaded being first added");
		}
		
	}
	
	public static void addLocation(String loc_name,String dis_name) {
		DBSTNode d=dlist.find(dis_name);  //get the name of the district we want to add to
		if(!d.getLocations().contains(loc_name))  //add that location to the specified district IF THE LOCATION NAME DOES NOT EXIST IN THE CURRENT DISTRICT
			d.getLocations().add(loc_name);
		
		if(d.getLocations().getRoot().getLeft()==null && d.getLocations().getRoot().getRight()==null)
			last_loaded_location=d.getLocations().find(loc_name);
			
		System.out.println(d.getLocations().find(loc_name));
	}
	
	
	public static void addDate(String loc_name,String dis_name,Date date_of_death) {
		DBSTNode d=dlist.find(dis_name);
		LBSTNode l=d.getLocations().find(loc_name);
		if(!l.getDateBST().contains(date_of_death))
			l.getDateBST().add(date_of_death);
		
	}
	
	
	public static void addSortlyMartyrs(Martyr r , String loc_name, String dis_name, Date da) {
		DBSTNode d=dlist.find(dis_name);
		LBSTNode l=d.getLocations().find(loc_name);
		DateNode dn=l.getDateBST().find(da);
		
		if(!l.locationContainsMartyrData(r)) {
			if(dn!=null && dn.getMartyrs().size()==0) {
				dn.getMartyrs().addToFirst(r);
			}
			else {
				try {
						if( r.compareTo((Martyr)dn.getMartyrs().getFirst()) <= 0 ) {
							dn.getMartyrs().addToFirst(r);
						}
						else if(r.compareTo((Martyr)dn.getMartyrs().getLastElement()) >=0 ) {
							dn.getMartyrs().addToLast(r);
						}
						else {
								for(int i=0;i<dn.getMartyrs().size();i++) {
									if( r.compareTo((Martyr)dn.getMartyrs().get(i)) <= 0 ) {
										dn.getMartyrs().addByIndex(r, i);
										break;
									}
									
							}
						}
				}catch(NullPointerException e) {
					System.out.println("exception mar");
				}
				
			}
		}
	}
	
//..........................................................................................................................
	




}
