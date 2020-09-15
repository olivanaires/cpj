package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "additives")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Additive extends BaseEntity {

    @NotNull
    private LocalDate signatureDate;

    @NotNull
    private LocalDate signatureEndDate;

    @NotNull
    private BigDecimal paymentValue;

    @NotNull
    private Integer duration;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contract contract;

}
