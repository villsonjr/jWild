package br.dev.ulk.animalz.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`GROUPS`")
public class Group extends AbstractEntity {

    @Column(name = "NAME",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Animal> animals;

}