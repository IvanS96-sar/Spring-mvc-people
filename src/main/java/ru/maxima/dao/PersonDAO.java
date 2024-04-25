package ru.maxima.dao;

import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private Long PEOPLE_COUNT = 0L;
    public List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Sergey",32L));
        people.add(new Person(++PEOPLE_COUNT,"Pavel",27L));
        people.add(new Person(++PEOPLE_COUNT,"Tagir",28L));
    }

    public List<Person> getAllPeople() {
        return people;
    }
    public Person findById(Long id){
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);
    }


    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(Person personFromView, Integer id){
        Person toBeUpdated = findById(Long.valueOf(id));
        toBeUpdated.setName(personFromView.getName());
        toBeUpdated.setAge(personFromView.getAge());
    }
    public void delete(Integer id){
        people.removeIf(p->p.getId().equals(Long.valueOf(id)));
    }
}
