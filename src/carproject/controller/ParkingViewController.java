package carproject.controller;

import carproject.database.Database;
import carproject.model.Parking;
import carproject.model.Person;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ParkingViewController implements Initializable {

    private Database database;
    
    private ObservableList<Parking> data = FXCollections.observableArrayList(); //like an arraylist, used in tables for java fx

    @FXML
    private TextField carBrand;
    @FXML
    private TextField carColor;
    @FXML
    private TextField plateNumber;
    @FXML
    private TextField user;

    @FXML
    private ComboBox discount;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    
    @FXML
    private TableView table;

    @FXML
    private TableColumn c1;
    @FXML
    private TableColumn c2;
    @FXML
    private TableColumn c3;
    @FXML
    private TableColumn c4;
    @FXML
    private TableColumn c5;
    @FXML
    private TableColumn c6;

    @Override
    public void initialize(URL url, ResourceBundle rb) { //first method to run
        try {
            database = new Database(); //instantiate database class
            database.connect(); // connect to database

            discount.setItems(FXCollections.observableArrayList( //set discount values
                    "BRONZE", "SILVER", "GOLD"
            ));

            c1.setCellValueFactory( //set first column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("firstName")
            );
            
            c2.setCellValueFactory( //set second column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("lastName")
            );
            
            c3.setCellValueFactory( //set third column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("carBrand")
            );
            
            c4.setCellValueFactory( //set fourth column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("carColor")
            );
            
            c5.setCellValueFactory( //set fifth column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("plateNumber")
            );
            
            c6.setCellValueFactory( //set sixth column property name using carproject.model.Parking fields
                    new PropertyValueFactory<>("timeOfArrival")
            );
            
            table.setItems(data);
            loadDataToTable();
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    public void onAddUser() { //method to handle Adduser button
        String fName = firstName.getText(); //get firstname
        String lName = lastName.getText(); //get lastname
        String disc = discount.getSelectionModel().getSelectedItem().toString(); //get value from combo box

        Person p = new Person();
        p.setFirstName(fName);
        p.setLastName(lName);
        p.setDiscount(disc);

        database.insertPerson(p); //insert new person
        refresh();  //called refresh method: refresh fields after insert
    }

    @FXML
    public void onPark() { //method to handle Park button
        String cBrand = carBrand.getText();
        String cColor = carColor.getText();
        String pNumber = plateNumber.getText();
        String usr = user.getText();

        Parking parking = new Parking();
        parking.setCarBrand(cBrand);
        parking.setCarColor(cColor);
        parking.setPlateNumber(pNumber);
        parking.setTimeOfArrival(LocalDate.now().toString());

        boolean flag = false;
        for (Person p : database.queryAllPersons()) { //loop through database, person table
            if (usr.equalsIgnoreCase(p.getFirstName()) || usr.equalsIgnoreCase(p.getLastName())) //if usr matches firstname or lastname
            {
                parking.setFirstName(p.getFirstName()); //we have found match
                parking.setLastName(p.getLastName());
                flag = true;
                break;
            }
        }

        if (!flag) { //if we dont find a match in database, we set lastname to unknown
            parking.setFirstName(usr);
            parking.setLastName("Unknown");
        }

        database.insertParking(parking);
        data.add(parking); //add parking to table
        refresh(); //call refresh, st fields to empty
    }

    public void refresh() { //method to clear fields
        firstName.setText("");
        lastName.setText("");
        discount.getSelectionModel().clearSelection();
        user.setText("");
        plateNumber.setText("");
        carColor.setText("");
        carBrand.setText("");
    }

    public void loadDataToTable() { //method to load data from database to table
        
        ArrayList<Parking> parkings = database.queryParking(); //query all parking from databse
        data.addAll(parkings);
    }

}
