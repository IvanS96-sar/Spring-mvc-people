package ru.maxima.dao;

import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ognl.DynamicSubscript.last;


@Component
public class PersonDAO {
    private Long PEOPLE_COUNT = 0L;


    private final String URL = "jdbc:postgresql://localhost:5432/maxima";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    private Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Person> getAllPeople()  {

        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQLQuery = "select * " +
                    "from person";

            ResultSet resultSet = statement.executeQuery(SQLQuery);

            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getLong("age"));

                people.add(person);


            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return people;
    }
    public Person findById(Long id){
        Person person = new Person();
        try {
            Statement statement = connection.createStatement();
            String SQLQuery = "select * " +
                    "from person where id = " + id;
            ResultSet resultSet = statement.executeQuery(SQLQuery);

            while (resultSet.next()){
            person.setId(resultSet.getLong("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getLong("age"));}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }
//        return people.stream()
//                .filter(p -> p.getId().equals(id))
//                .findAny()
//                .orElse(null);
//    }


    public void save(Person person){
       Optional<Long> last= getAllPeople().stream()
               .map(Person::getId).max(Comparator.naturalOrder());
       Long id = last.get();

        try {
            Statement statement = connection.createStatement();

            String SQLQuery = "insert into person(id, name, age) values ( " + ++id + ", '" + person.getName() + "'," + person.getAge() + ")";
            statement.executeUpdate(SQLQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        // people.add(person);
    }

    public void update(Person personFromView, Integer id) {

        try {
            Statement statement = connection.createStatement();

            String SQLQuery = "update person set name = '" + personFromView.getName()+"'," +
                    "age = " + personFromView.getAge()+
                    " where id =" +id;
            statement.executeUpdate(SQLQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Integer id){
//delete,from person,where id;

        try {
            Statement statement = connection.createStatement();

            String SQLQuery = "delete from person where id = " +id;
            statement.executeUpdate(SQLQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
