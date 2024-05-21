package com.vao.agenda.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
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


//    public void setPhone( Integer phone) {
//        String phoneStr = phone.toString();
//        if (phoneStr.length() < 6 || phoneStr.length() > 8) {
//            throw new RuntimeException("El número de teléfono debe tener entre 6 y 8 dígitos.");
//        }
//
//        this.phone = phone;
//    }

}
