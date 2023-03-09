package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Person", indexes = {
        @Index(name = "email_UNIQUE", columnList = "email", unique = true),
        @Index(name = "fk_Person_Address1_idx", columnList = "address_id")
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerson", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "person")
    private Set<Phone> phones = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "persons")
    private Set<Hobby> hobbies = new LinkedHashSet<>();

    public Person() {
    }

    public Person(String email, String firstName, String lastName,Set<Phone> phones, Address address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.address = address;
    }



    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id != null && Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}