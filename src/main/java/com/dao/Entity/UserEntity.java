package com.dao.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="User")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable{
	
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
	private boolean deleteStatus;
	
	@OneToOne
	@JoinColumn(name = "address_Id")
	private UserAddress userAddress;
	
	
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
