package demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    @NotNull
    private String name;
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    public Person() {
    }

    public Person(@NotNull String name, Integer age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}