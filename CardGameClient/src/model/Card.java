package model;

public class Card {
    String value ;
    String type ;
    
    public Card(String value, String type){
        this.value = value ; 
        this.type = type ;
    }
    
    public String toString(){
        return value + "-" + type ; 
    }
    
    public int getValue(){
        if("AJQK".contains(value)){
            if(value == "A"){
                return 1 ;
            }
            return 10 ; 
        }
        return Integer.parseInt(value) ;
    }
    
    public String getImagePath(){
        return "/run/cards/" + toString() + ".png";
    }
}
