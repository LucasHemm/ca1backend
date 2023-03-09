package dtos;

import entities.Person;
import entities.Phone;

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
        this.id = person.getId();
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
        Set<Phone> phones = null;
        for (PhoneDTO phoneDTO : phoneDTOS) {
            Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
            phones.add(phone);
        }
        return phones;
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
