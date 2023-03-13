package dtos;

import entities.Hobby;

import java.util.HashSet;
import java.util.Set;

public class HobbyDTO {

    private int id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public HobbyDTO() {
    }
    public HobbyDTO(String name) {
        this.name = name;
    }

    public HobbyDTO(Hobby hobby){
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.wikiLink = hobby.getWikiLink();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
    }




    public static Set<HobbyDTO> getDtos(Set<Hobby> hobbies) {
        Set<HobbyDTO> hobbyDTOS = new HashSet<>();
        for (Hobby hobby : hobbies) {
            HobbyDTO hobbyDTO = new HobbyDTO(hobby);
            hobbyDTOS.add(hobbyDTO);
        }
        return hobbyDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
