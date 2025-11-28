package com.example.me_tas;

//מחלקה לא משומשץ אולי אחזור אלייה כשאצטרך שימוש ממנה בfirebase
public class Pilot {
    protected String Email;

    public Pilot(String email) {
        this.Email = email;
    }

    public Pilot(){}

    public String getEmail() {return Email;}
    public void setEmail(String email) {Email = email;}
}

