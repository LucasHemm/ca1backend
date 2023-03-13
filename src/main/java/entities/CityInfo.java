package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "CityInfo.findByOrderByCityDesc", query = "select c from CityInfo c order by c.city DESC"),
        @NamedQuery(name = "CityInfo.deleteAllRows", query = "delete from CityInfo"),
        @NamedQuery(name = "CityInfo.findCity", query = "select c from CityInfo c where c.zipCode = :zipCode")
})
public class CityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCityInfo", nullable = false)
    private Integer id;

    @Column(name = "zipCode", nullable = false, length = 45)
    private String zipCode;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @OneToMany(mappedBy = "cityInfo")
    private Set<Address> addresses = new LinkedHashSet<>();

    public CityInfo() {
    }
    public CityInfo(String zip) {
        this.zipCode = zip;
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }
    public CityInfo(int id, String zipCode, String city) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}