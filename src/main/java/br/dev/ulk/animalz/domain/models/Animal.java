package br.dev.ulk.animalz.domain.models;

import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ANIMALS")
public class Animal extends AbstractEntity {

    @Column(name = "SCIENTIFIC_NAME",
            nullable = false)
    private String scientificName;

    @Column(name = "SPECIE",
            nullable = false)
    private String specie;

    @Column(name = "SIZE",
            nullable = false)
    private Double size;

    @Column(name = "MASS",
            nullable = false)
    private Double mass;

    @Column(name = "STATUS",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

}