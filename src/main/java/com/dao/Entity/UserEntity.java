package com.dao.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dao.Query.UserQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="User",
uniqueConstraints = { 
  @UniqueConstraint(columnNames = "email") ,
  @UniqueConstraint(columnNames = "phone") 
})
@NamedQuery(name = "User.findAllInc", query = UserQuery.UserfindAllInc)


// Update
@NamedQuery(name = "User.updateTempValueaAndAccountStatus", query = UserQuery.UserfindAllInc)

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
    @Column
	private String fullName;
	
    @Column
	private String gender;
	
    @Column
	private Date dob;
	
    @Column
	private String email;
	
    @Column
	private String password;
	
    @Column
	private int accountStatus;
	
    @Column
    private String tempValue;
    
    
	@OneToOne
	@JoinColumn(name = "address_Id")
	private UserAddress userAddress;
	
	
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles", 
          joinColumns = @JoinColumn(name = "user_id"), 
          inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private String phone;
    
    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date(System.currentTimeMillis());
        if (this.updatedAt == null) updatedAt =	LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreRemove
    protected void preRemove() {
        this.updatedAt = LocalDateTime.now();
    }

}
