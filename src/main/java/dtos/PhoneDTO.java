package dtos;

import entities.Phone;

import java.util.HashSet;
import java.util.Set;

public class PhoneDTO {

    private int id;
    private String number;
    private String description;


    public PhoneDTO() {
    }

    public static Set<PhoneDTO> getDtos(Set<Phone> phones) {
        Set<PhoneDTO> phoneDTOS = new HashSet<>();
        for (Phone phone : phones) {
            PhoneDTO phoneDTO = new PhoneDTO(phone);
            phoneDTOS.add(phoneDTO);
        }
        return phoneDTOS;
    }

    public PhoneDTO(Phone phone){
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
