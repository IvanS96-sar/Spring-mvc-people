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
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAllPeople() {

        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getLong("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person findById(Long id) {

        Person person = new Person();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id = ?");

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getLong("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }


    public void save(Person person) {
        Optional<Long> last = getAllPeople().stream()
                .map(Person::getId).max(Comparator.naturalOrder());
        Long id = last.get();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into person(id, name, age) values (?,?,?)");

            preparedStatement.setLong(1, ++id);
            preparedStatement.setString(2, person.getName());
            preparedStatement.setLong(3, person.getAge());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person personFromView, Integer id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update person set name = ?,age = ? where id = ?");

            preparedStatement.setString(1, personFromView.getName());
            preparedStatement.setLong(2, personFromView.getAge());
            preparedStatement.setLong(3, id);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Integer id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id = ?");

            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
