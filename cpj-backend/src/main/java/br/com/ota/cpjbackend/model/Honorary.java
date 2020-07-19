package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "honoraries")
@NoArgsConstructor
public class Honorary extends BaseEntity {

    @NotBlank
    @Size(max = 200)
    private String description;

    private Date date;

    @NotNull
    private BigDecimal paymentValue;

    private int parcels;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contract contract;

}
