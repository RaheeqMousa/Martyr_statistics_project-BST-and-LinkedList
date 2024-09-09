import java.util.Date;

public class Martyr  implements Comparable<Martyr>, Cloneable{
	private String name;
	private Date date_of_death;
	private byte age;
	private String location;
	private String district;
	private char gender;
	
	
	public Martyr() {
		date_of_death=new Date();
	}
	
	public Martyr(String name, Date date_of_death, byte age, String location, String district, char gender) {
		super();
		this.name = name;
		this.date_of_death = date_of_death;
		this.age = age;
		this.location = location;
		this.district = district;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate_of_death() {
		return date_of_death;
	}
	public void setDate_of_death(Date date_of_death) {
		this.date_of_death = date_of_death;
	}
	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	
//............................................................................	
	@Override
	public int compareTo(Martyr o) {
		if(this.getAge() > o.getAge())
			return 1;
		else if(this.getAge() < o.getAge())
			return -1;
		else
			return 0;
	}

//...........................................................................	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("clone not supported exception");
			return null;
		}
		
	}
	
	
	
//............................................................................
	@Override
	public boolean equals(Object r) {
		if(r==null) return false;
		
		Martyr mar;
			if(r instanceof Martyr) {
			 mar=(Martyr)r;
			}else {
				System.out.println("parameter is not instance of martyr");
				return false;				
			}
		if(this.date_of_death.equals(mar.getDate_of_death())  && this.getAge()==mar.getAge() && 
			this.name.equalsIgnoreCase(mar.getName()) && this.location.equalsIgnoreCase(mar.getLocation()) && 
			this.district.equalsIgnoreCase(mar.getDistrict()) && this.getGender()==mar.getGender() )
			return true;
		
		return false;
	}
	
//............................................................................
	
	@Override
	public String toString() {
		return name +","+ (date_of_death.getMonth()+1)+"/"+date_of_death.getDate()+"/"+(date_of_death.getYear()+1900) + "," + age + "," + location
				+ "," + district + "," + gender;
	}

}
