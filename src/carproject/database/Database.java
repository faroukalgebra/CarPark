package carproject.database;

import carproject.model.Parking;
import carproject.model.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database {  //class to handle all database actions like query, insert, connect, delete

    private Connection connection = null; //used in making connection to database
    private Statement statement = null; //used in making  sql statements
    private PreparedStatement preparedStatement = null; //used in executing insert,delete update statemets
    private ResultSet resultSet = null; //hold the result of query like select statement

    public Database() throws ClassNotFoundException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver"); //load mysql driver
    }

    public void connect() { //method to connect to database, database = project, user = root, password = root
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost/project?"
                            + "user=root&password=project&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            System.out.println("Databse connected");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }

    public void disconnect() { //method to close database connection
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                this.connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            System.out.println("Databse disconnected");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Person> queryAllPersons() { //method to query person table,return arraylist of person
        ArrayList<Person> persons = new ArrayList<>(); //our return value
        try {
            statement = connection.createStatement(); //we need to get connection
            resultSet = statement
                    .executeQuery("select * from person"); //execute query

            while (resultSet.next()) { //loop through resultset, we are looping through the rows
                Person person = new Person(); // we need to create a new person object while we loop
                person.setFirstName(resultSet.getNString(1)); //set firstname from first column
                person.setLastName(resultSet.getNString(2)); ///set lastName from second column
                person.setDiscount(resultSet.getNString(3)); //set discount from third column
                persons.add(person); //add person to arraylist
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); //if exception occurs
        }
        return persons; //return our arraylist
    }

    public void insertPerson(Person person) { //method to insert new person, 
        try {
            preparedStatement = connection  //we need connection to execute insert
                    .prepareStatement("insert into  person values (?, ?, ?)");
            preparedStatement.setString(1, person.getFirstName()); //first question mark
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getDiscount());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updatePerson(Person person) { //method to update person, his discount
        try {
            preparedStatement = connection
                    .prepareStatement("update person set discount = ?  where firstName = ? and lastName = ?");
            preparedStatement.setString(1, person.getDiscount());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Parking> queryParking() { //method to query parking table
        ArrayList<Parking> parkings = new ArrayList<>(); //our return value arraylist<parking>
        try {
            statement = connection.createStatement(); //we need connection to ex
            resultSet = statement
                    .executeQuery("select * from parking"); //execute query

            while (resultSet.next()) { //loop through the rows
                Parking parking = new Parking(); //create new person
                parking.setFirstName(resultSet.getNString(1));
                parking.setLastName(resultSet.getNString(2));
                parking.setCarBrand(resultSet.getNString(3));
                parking.setCarColor(resultSet.getNString(4));
                parking.setPlateNumber(resultSet.getNString(5));
                parking.setTimeOfArrival(resultSet.getNString(6));
                //parking.setGender(resultSet.getNString(7));
                parkings.add(parking);//add parking
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return parkings; //return our value
    }

    public void insertParking(Parking parking) { //method to insert new parking to database
         try {
            preparedStatement = connection //first get connection and write insert statement
                    .prepareStatement("insert into  parking values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, parking.getFirstName());
            preparedStatement.setString(2, parking.getLastName());
            preparedStatement.setString(3, parking.getCarBrand());
            preparedStatement.setString(4, parking.getCarColor());
            preparedStatement.setString(5, parking.getPlateNumber());
            preparedStatement.setString(6, parking.getTimeOfArrival());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteParking(Parking parking) {  //method to delete Parking
        try {
            preparedStatement = connection //get connection and write delete statement
                    .prepareStatement("delete from  parking where plateNumber = ?");
            preparedStatement.setString(1, parking.getPlateNumber()); //match question mark with platenumber
            preparedStatement.executeUpdate(); //execute update
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
