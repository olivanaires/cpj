package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.vo.UserRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 15)
	private String username;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@JsonIgnore
	@NotBlank
	@Size(max = 100)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return authorities;
	}

	@JsonIgnore
	public String getTokenValue() {
		try {
			ObjectMapper obj = new ObjectMapper();
			return obj.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return Long.toString(this.getId());
		}
	}

	public UserRequest toUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setId(this.getId());
		userRequest.setRole(this.getRoles().iterator().next().getName());
		userRequest.setUsername(this.getUsername());
		userRequest.setEmail(this.getEmail());
		userRequest.setPassword(null);
		return userRequest;
	}

}