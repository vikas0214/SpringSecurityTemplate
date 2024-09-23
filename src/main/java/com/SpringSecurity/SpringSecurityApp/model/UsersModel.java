package com.SpringSecurity.SpringSecurityApp.model;
import jakarta.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class UsersModel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	 @Column(nullable = false, unique=false)
	    private String name;
	 
	

	@Column(nullable = false, unique=true)
	    private String mobileNo;
	
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
   
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",  // Name of the join table
        joinColumns = @JoinColumn(name = "user_id"),  // Foreign key in users_roles pointing to users
        inverseJoinColumns = @JoinColumn(name = "role_id")  // Foreign key in users_roles pointing to roles
    )
    private Set<Role> roles;  // A user can have multiple roles

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "UsersModel [id=" + id + ", name=" + name + ", mobileNo=" + mobileNo + ", username=" + username
				+ ", password=" + password + ", roles=" + roles + "]";
	}

	public UsersModel(Long id, String name, String mobileNo, String username, String password, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNo = mobileNo;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public UsersModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
