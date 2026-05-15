import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.*;

public class PetCareScheduler{
    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, Pet> pets = new HashMap<>();

    // @SuppressWarnings("unchecked")
    // private static void loadDataFromFile(){
    //     try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("schedule.ser"))){
    //         pets = (HashMap<String, Pet>) reader.readObject();
    //     } catch(FileNotFoundException e){
    //         System.out.println("file not found");
    //     } catch(Exception e){
    //         System.out.println("Error");
    //     }
    // }
    @SuppressWarnings("unchecked")
    private static void loadDataFromFile() {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream("schedule.ser"))) {
            // Read it as a Pet object instead of a HashMap
            pets = (HashMap<String, Pet>) reader.readObject();
            
            // Put it into your existing map (assuming 'pets' is initialized elsewhere) 
            System.out.println("loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        loadDataFromFile();
        while(true){
            System.out.println("~~~~~~ Make a choice ~~~~~~~~~~"+
        "\n1.Register a pet."+"\n2. Schedule an appointment. \n3.Store data \n4.Display Records"+
        "\n5.Generate Reports \n6.Exit");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1":
                System.out.println("Enter pet ID: ");
                String p2ID = sc.nextLine().trim();
                if(pets.containsKey(p2ID)){
                    System.out.println("Pet already registered!");
                    break;
                }
                System.out.println("Enter Name: ");
                String name = sc.nextLine().trim();
                System.out.println("Enter Breed: ");
                String breed = sc.nextLine().trim();
                System.out.println("Enter owner name: ");
                String owner = sc.nextLine().trim();
                System.out.println("Enter owner contact: ");
                String contact = sc.nextLine().trim();
                System.out.println("Enter pets age: ");
                int age = sc.nextInt();
                registerPet(p2ID,name,breed,age,owner,contact);
                break;
            case "2": 
                try{
                    System.out.println("Enter pet ID: ");
                    String pID = sc.nextLine().trim();
                    System.out.println("Enter appointment type: ");
                    String type = sc.nextLine().trim();
                    System.out.println("Enter appointment date (yyyy-MM-dd): ");
                    String date = sc.nextLine().trim();
                    System.out.println("Enter appointment time (HH:mm:ss): ");
                    String time = sc.nextLine().trim();
                    System.out.println("Enter appointment notes: ");
                    String notes = sc.nextLine().trim();
                    getAppointment(pID, type,date,time,notes);
                } catch (DateTimeParseException e){
                    System.out.println("Invalid date format");
                }
                break;
            case "3":
                writeToaFile();
                break;
            case "4":
                System.out.println("select : \nA.Show pets \n B.Show Appointment");
                String show = sc.nextLine().trim();
                if(show.equalsIgnoreCase("A")){
                    displayPets();
                }
                if(show.equalsIgnoreCase("B")){
                    System.out.println("Enter pet ID: ");
                    String ppID = sc.nextLine().trim();
                    displayAppointments(ppID);
                }
                break;
            case "5":
                generateReport();
                break;
            case "6":
                writeToaFile();
                return;
        }
        }
        
    }

    private static void registerPet(String pID, String name, String breed, int age, String ownerName, String contact){
        Pet p1 = new Pet(pID, name,breed,age,ownerName,contact);
        pets.put(pID, p1);
        System.out.println(name+" registered successfully!");
    }

    private static void getAppointment(String pId,String type, String date, String time, String notes){
        Pet p = pets.get(pId);
        if(p == null){
            System.out.println("No records found for this pet!");
            return;
        }
        Appointment a1 = new Appointment(type, date, time, notes);
        p.setAppointment(a1);
        System.out.println("Appointment scheduled successfully!");
    }

    private static void writeToaFile(){
        if(pets.isEmpty()){
            System.out.println("No data found!");
            return;
        }
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("schedule.ser"))) {
        // Save the entire map object, not individual pets
        writer.writeObject(pets);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private static void displayPets(){
        if(pets.isEmpty()){
            System.out.println("No data found!");
            return;
        }
        System.out.println("~~~~~ PET Details ~~~~~~~~");
        for(Pet pet:pets.values()){
            System.out.println("\n"+ pet.getName()+" | "+pet.getBreed()+
            " | age:"+pet.getAge()+ " | owner: "+ pet.getOwnerName()+ " | contact info:"+pet.getContactInfo());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void displayAppointments(String pID){
        if(pets.isEmpty()){
            System.out.println("No data found!");
            return;
        }
        Pet p = pets.get(pID);
        System.out.println("Appointments\n");
        System.out.println(p.getAppointments());
    }

    private static void upcomingAppointments(){
        LocalDate today = LocalDate.now();
        System.out.println("~~~~~ Upcoming Appointments ~~~~~~~~");
        for(Pet p:pets.values()){
            for(Appointment a:p.getAppointments()){
                if(a.getDate().isAfter(today)){
                    System.out.println(p.getName()+" | "+a.getAppointmentType()+" | "+a.getDate());
                }
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void pastAppointments(){
        LocalDate today = LocalDate.now();
        System.out.println("~~~~~ Past Appointments ~~~~~~~~");
        for(Pet p:pets.values()){
            for(Appointment a:p.getAppointments()){
                if(a.getDate().isBefore(today)){
                    System.out.println(p.getName()+" | "+a.getAppointmentType()+" | "+a.getDate());
                }
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void generateReport(){
        int aged = 0;
        String agedPet = "";
        for(Pet p:pets.values()){
            if(p.getAge() > aged){
                agedPet = p.getName();
                aged = p.getAge();
            }
        }
        System.out.println("Most Aged Pet is: "+agedPet+" | "+aged+" yrs old.");
        System.out.println("Pets with appointment in next week:");
        LocalDate today = LocalDate.now();
        for(Pet p:pets.values()){
            for(Appointment a:p.getAppointments()){
                if(a.getDate().isAfter(today) && a.getDate().isBefore(today.plusDays(7))){
                    System.out.println(p.getName()+" | "+a.getAppointmentType()+" | "+a.getDate());
                }

            }
        }
        System.out.println("Pets with visit overdue from 6 months");
        for(Pet p:pets.values()){
            for(Appointment a:p.getAppointments()){
                if(a.getDate().isBefore(today.minusMonths(6))){
                    System.out.println(p.getName()+" | "+a.getAppointmentType()+" | "+a.getDate());
                }

            }
        }
    }

}
