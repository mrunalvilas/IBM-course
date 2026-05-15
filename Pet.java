import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Pet implements Serializable {
    private String petID;
    private String name;
    private String breed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate regDate;
    private List<Appointment> appointments = new ArrayList<Appointment>(); 

    public Pet(String pID, String name, String breed, int age, String ownerName, String contact){
        this.petID = pID;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contact;
        this.regDate = LocalDate.now();
    }

    public String getPetID(){ return this.petID;}
    public String getName(){ return this.name;}
    public String getBreed(){ return this.breed;}
    public int getAge(){ return this.age;}
    public String getOwnerName(){ return this.ownerName;}
    public String getContactInfo(){ return this.contactInfo;}
    public LocalDate getRegDate(){ return this.regDate;}
    public List<Appointment> getAppointments(){ return appointments;}

    public void setAppointment(Appointment app){
        // if(appointments.contains(app)){
        //     System.out.println("Appointment already exixts!");
        //     return;
        // }
        LocalDate today = LocalDate.now();
        if(app.getDate().isBefore(today)){
            System.out.println("Appointment can not be of past date!");
            return; 
        }
        this.appointments.add(app);
    }
}
