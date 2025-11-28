package com.example.me_tas;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Objects;

public class DataManager {
  //c3ni7563
  private static int alarm_managed_index=-1;
  private static String ravshatzcode="C";
  private static ArrayList<Pilot_Flight> pilotFlights;
  private static ArrayList<Undetected_Flight> undetectedFlights;

  public static ArrayList<Pilot_Flight> getPilotFlights() {
    if(pilotFlights==null){
      pilotFlights=new ArrayList<>();
    }
    return pilotFlights;
  }
  public static void setPilotFlights(ArrayList<Pilot_Flight> pilotFlights) {DataManager.pilotFlights = pilotFlights;}

  //gets all of the people currently in air
  public static ArrayList<Pilot_Flight> getIn_Air_Flights(){
    ArrayList<Pilot_Flight> pilotFlights1=new ArrayList<>();
    for(int i=0;i< pilotFlights.size();i++){
      if(pilotFlights.get(i).in_air || i==alarm_managed_index){
        pilotFlights1.add(pilotFlights.get(i));
      }
    }
    return pilotFlights1;
  }

  public static ArrayList<Pilot_Flight> getUsersFlights(){
    ArrayList<Pilot_Flight> pilotFlights1=new ArrayList<>();
    for (int i=0;i<getPilotFlights().size();i++){
      if(Objects.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pilotFlights.get(i).getEmail())){
        pilotFlights1.add(pilotFlights.get(i));
      }
    }
    return pilotFlights1;
  }
  //checks which flight of a user is in air
  public static int getUser_Flight_InAir() {
    for (int i = 0; i < getUsersFlights().size(); i++) {
      if(getUsersFlights().get(i).isIn_air()) {
        return i;
      }
    }
    return -1;
  }

  public static int getFlightIndex(Flight flight){
    for (int i = 0; i < pilotFlights.size(); i++) {
      if(flight== pilotFlights.get(i)) {
        return i;
      }
    }
    return -1;
  }

  //sets in air flight variables once it lands
  public static boolean setFlightEnd(LatLngWrapper latLngWrapper){
    boolean end_isset=false;
    int position=getUser_Flight_InAir();
    if (position!=-1){
      pilotFlights.get(position).setIn_air(false);
      pilotFlights.get(position).setEndlocation(latLngWrapper);
      end_isset=true;
    }
    return end_isset;
  }

  public static ArrayList<Undetected_Flight> getUndetectedFlights() {
    if(undetectedFlights==null){
      undetectedFlights=new ArrayList<>();
    }
    return undetectedFlights;
  }
  public static void setUndetectedFlights(ArrayList<Undetected_Flight> undetectedFlights) {DataManager.undetectedFlights = undetectedFlights;}

  public static int getAlarm_managed_index() {
    return alarm_managed_index;
  }

  public static void setAlarm_managed_index(int alarm_managed_index) {
    DataManager.alarm_managed_index = alarm_managed_index;
  }

  public static String getRavshatzcode() {
    return ravshatzcode;
  }
}
