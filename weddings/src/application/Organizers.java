package application;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Organizers {
	private final StringProperty id; //StringProperty est une classe qui enveloppe une chaîne de caractères et permet une liaison bidirectionnelle avec des composants graphiques JavaFX.
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty price;

    public Organizers() {
    	id = new SimpleStringProperty(this, "id"); //instance de la classe StringProperty
        name = new SimpleStringProperty(this, "name");
        phone = new SimpleStringProperty(this, "phone");
        email = new SimpleStringProperty(this, "email");
        type = new SimpleStringProperty(this, "type");
        date = new SimpleStringProperty(this, "date");
        time = new SimpleStringProperty(this, "time");
        price = new SimpleStringProperty(this, "price");
    }


public StringProperty idProperty() { return id; }
public String getId() { return id.get(); }
public void setId(String newId) { id.set(newId); }

public StringProperty nameProperty() { return name; }
public String getName() { return name.get(); }
public void setName(String newName) { name.set(newName); }

public StringProperty phoneProperty() { return phone; }
public String getPhone() { return phone.get(); }
public void setPhone(String newPhone) { phone.set(newPhone); }

public StringProperty emailProperty() { return email; }
public String getEmail() { return name.get(); }
public void setEmail(String newEmail) { email.set(newEmail); }

public StringProperty typeProperty() { return type; }
public String getType() { return type.get(); }
public void setType(String newType) { type.set(newType); }

public StringProperty dateProperty() { return date; }
public String getDate() { return date.get(); }
public void setDate(String newDate) { date.set(newDate); }

public StringProperty timeProperty() { return time; }
public String getTime() { return time.get(); }
public void setTime(String newTime) { time.set(newTime); }

public StringProperty priceProperty() { return price; }
public String getPrice() { return price.get(); }
public void setPrice(String newPrice) { price.set(newPrice); }
}
