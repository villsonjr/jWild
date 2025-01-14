package br.dev.ulk.animalz.domain.models;


import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
public class Animal {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "SCIENTIFIC_NAME")
    private String scientificName;

    @NotNull
    @Column(name = "SPECIE")
    private String specie;

    @NotNull
    @Column(name = "SIZE")
    private Double size;

    @NotNull
    @Column(name = "MASS")
    private Double mass;

    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;
}
