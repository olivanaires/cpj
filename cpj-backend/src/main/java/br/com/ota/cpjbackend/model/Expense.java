package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "expenses")
@NoArgsConstructor
public class Expense extends BaseEntity {

    @NotBlank
    @Size(max = 200)
    private String description;

    private Date date;

    @NotNull
    private BigDecimal paymentValue;

}