package com.request.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.request.api.enums.MethodPayment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Purchase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Embedded
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	private MethodPayment methodPayment;

	private Integer quantity;

	private BigDecimal value;

	@ManyToMany
	private List<Product> products = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
	private LocalDateTime created;
		
    @LastModifiedDate
	private LocalDateTime updated;

}
