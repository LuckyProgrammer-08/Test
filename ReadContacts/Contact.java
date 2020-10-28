package appdevin.com.readcontacts;

import java.util.ArrayList;

class Contact {

    private String id;
    private String name;
    private ArrayList<String> number = new ArrayList<>();

    public Contact(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addNumber(String number) {
        this.number.add(number);
    }

    public String getId() {
        return id;
    }

    public boolean hasNumber(){
        return number.size() <= 0;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
