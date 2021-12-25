package com.dao.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserAddress {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long address_Id;
	
	@Column
	private String country;
	
	@Column
	private String state;
	
	@Column
	private String district;
	
	@Column
	private String city;
	
	@Column
	private String localAddress;
	
	

}
