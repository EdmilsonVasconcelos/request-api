package com.request.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

	private String phoneNumber;

	@Embedded
	private Address address;
}
