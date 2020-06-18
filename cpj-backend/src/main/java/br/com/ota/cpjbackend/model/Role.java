package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.enums.RoleName;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 20)
	private RoleName name;

}

