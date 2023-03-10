package dtos;

import entities.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class PersonDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<PhoneDTO> phoneDTOS;
    private AddressDTO addressDTO;
    private Set<HobbyDTO> hobbyDTOS;

    public PersonDTO() {
    }

    public PersonDTO(Person person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phoneDTOS = PhoneDTO.getDtos(person.getPhones());
        this.addressDTO = new AddressDTO(person.getAddress());
        this.hobbyDTOS = HobbyDTO.getDtos(person.getHobbies());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<PhoneDTO> getPhoneDTOS() {
        return phoneDTOS;
    }

    public Set<Phone> getPhones() {
        Set<Phone> phones = new HashSet<>();
        for (PhoneDTO phoneDTO : phoneDTOS) {
            Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
            phones.add(phone);
        }
        return phones;
    }

    public Set<Hobby> getHobbies(){
        Set<Hobby> hobbies = new HashSet<>();
        for (HobbyDTO hobbyDTO : hobbyDTOS) {
            Hobby hobby = new Hobby(hobbyDTO.getId(),hobbyDTO.getName(),hobbyDTO.getWikiLink(),hobbyDTO.getCategory(),hobbyDTO.getType());
            hobbies.add(hobby);
        }
        return hobbies;
    }

    public Address getAddresses(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("CityInfo.findCity", CityInfo.class);
        query.setParameter("zipCode", addressDTO.getZipCode());
        CityInfo cityInfo = (CityInfo) query.getSingleResult();
        Address address = new Address(addressDTO.getStreet(),addressDTO.getAdditionalInfo(),cityInfo);
        return address;
    }

    public void setPhoneDTOS(Set<PhoneDTO> phoneDTOS) {
        this.phoneDTOS = phoneDTOS;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public Set<HobbyDTO> getHobbyDTOS() {
        return hobbyDTOS;
    }

    public void setHobbyDTOS(Set<HobbyDTO> hobbyDTOS) {
        this.hobbyDTOS = hobbyDTOS;
    }
}
