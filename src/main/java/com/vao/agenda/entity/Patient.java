package com.vao.agenda.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String name;
    @NonNull
    private String lastName;
    @NonNull
    private Integer cod;
    @NonNull
    private Integer phone;
    @NonNull
    private String email;


    @Override
    public String toString() {
        return " {" +
                "id=" + id +
                ", nombre='" + lastName + " " + name + '\'' +
                ", telefono='" + cod +" " + phone + '\'' +
                '}';
    }

}
