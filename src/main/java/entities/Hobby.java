package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Hobby")
@NamedQueries({
        @NamedQuery(name = "Hobby.deleteAll", query = "delete from Hobby")
})
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHobby", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "wikiLink", nullable = false, length = 100)
    private String wikiLink;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Person> persons = new LinkedHashSet<>();


    public Hobby() {
    }

    public Hobby(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public Hobby(int id, String name, String wikiLink, String category, String type) {
        this.id = id;
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "wikiLink = " + wikiLink + ", " +
                "category = " + category + ", " +
                "type = " + type + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hobby hobby = (Hobby) o;
        return id != null && Objects.equals(id, hobby.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}