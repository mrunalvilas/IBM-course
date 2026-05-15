import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Serializable{
    private String appointmentTYpe;
    private LocalDate date;
    private LocalTime time;
    private String notes;

    public Appointment(String appType,String date, String time, String notes){
        this.appointmentTYpe = appType;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.notes = notes;
    }
    public String getAppointmentType(){
        return this.appointmentTYpe;
    }

    public String getNotes(){
        return this.notes;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public LocalTime getTime(){
        return this.time;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    public String toString(){
        return "----------------------"+
        "\nApponitment Type: "+this.appointmentTYpe+
        "| Date: "+this.date+" | Time: "+this.time
        +" | Notes: "+ this.notes+
        "\n------------------------------"; 
    }

}