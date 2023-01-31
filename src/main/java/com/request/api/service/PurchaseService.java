//package com.request.request.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import com.request.request.dto.purchase.request.PurchaseRequestDTO;
//import com.request.request.dto.purchase.response.PurchaseResponseDTO;
//import com.request.request.model.Address;
//import com.request.request.model.Customer;
//import com.request.request.repository.ProductRepository;
//import com.request.request.repository.PurchaseRepository;
//import org.modelmapper.Converter;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class PurchaseService {
//
//	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
//
//	private static final String PURCHASE_WITH_ID_NOT_EXISTS = "pedido com id %s nao existe";
//
//	@Autowired
//	private PurchaseRepository purchaseRepository;
//
//	@Autowired
//	private ProductRepository productRepository;
//
//	@Autowired
//	private ModelMapper mapper;
//
//	public PurchaseResponseDTO savePurchase(PurchaseRequestDTO request) {
//
//		var address = mapper.map(request.getAddress(), Address.class);
//
//		var customer = Customer.builder().address(address).phoneNumber(request.getPhoneNumber()).build();
//
//		var products = getProductsPurchase(request.getProducts());
//
//		var purchaseToSave = Converter.toPurachase(request, customer, products);
//
//		var purchaseSaved = purchaseRepository.save(purchaseToSave);
//
//		var customerSaved = mapper.map(purchaseSaved.getCustomer(), CustomerResponseDTO.class);
//
////		var productsSaved = getProductsPurchaseResponseDTO(purchaseSaved.getProducts());
//
////		var response = Converter.toPurchaseResponseDTO(purchaseSaved, customerSaved, productsSaved);
//
////		log.debug("PurchaseService.savePurchase - Finish - Request [{}], Response:  [{}]", request, response);
//
//		return null;
//
//	}
//
//	public List<PurchaseResponseDTO> getAllpurchases() {
//
//		log.debug("PurchaseService.getAllpurchases - Start");
//
//		List<Purchase> allRequests = purchaseRepository.findAll();
//
//		List<PurchaseResponseDTO> response = allRequests.stream().map(request -> mapper.map(request, PurchaseResponseDTO.class)).collect(Collectors.toList());
//
//		log.debug("PurchaseService.getAllpurchases - Finish -  Response:  [{}]", response);
//
//
//		return response;
//
//	}
//
//	public PurchaseResponseDTO alterStatusPurchase(Long idPurchase) {
//
//		log.debug("PurchaseService.closePurchase - Start - alterStatusPurchase:  [{}]", idPurchase);
//
//		Purchase purchase = getPurchaseById(idPurchase);
//
//		Purchase purchaseSaved = purchaseRepository.save(purchase);
//
//		PurchaseResponseDTO response = mapper.map(purchaseSaved, PurchaseResponseDTO.class);
//
//		log.debug("PurchaseService.closePurchase - Finish - alterStatusPurchase [{}], Response:  [{}]", idPurchase, response);
//
//		return response;
//
//	}
//
//	private Purchase getPurchaseById(Long idPurchase) {
//
//		log.debug("PurchaseService.getPurchaseById - Start - idPurchase:  [{}]", idPurchase);
//
//		Purchase purchase = purchaseRepository.findById(idPurchase)
//				.orElseThrow(() -> new PurchaseNotExistsException(String.format(PURCHASE_WITH_ID_NOT_EXISTS, idPurchase)));
//
//		log.debug("PurchaseService.getPurchaseById - Finish - idPurchase [{}], Response:  [{}]", idPurchase, purchase);
//
//		return purchase;
//
//	}
//
//	private List<ProductReponseDTO> getProductsPurchaseResponseDTO(List<Product> products) {
//
//		log.debug("PurchaseService.getProductsPurchaseResponseDTOByListProducts - Start - Request:  [{}]", products);
//
//		List<ProductReponseDTO> response = products.stream().map(product -> mapper.map(product, ProductReponseDTO.class)).collect(Collectors.toList());
//
//		log.debug("PurchaseService.getProductsPurchaseResponseDTOByListProducts - Finish - Request:  [{}], Response: [{}]", products, response);
//
//		return response;
//
//	}
//
//	private List<Product> getProductsPurchase(List<ProductPurchaseDTO> products) {
//
//		log.debug("PurchaseService.getProductsPurchase - Start - ids:  [{}]", products);
//
//		var response = products.stream().map(productRequestDTO -> getProductById(productRequestDTO.getId())).collect(Collectors.toList());
//
//		log.debug("PurchaseService.getProductsPurchase - Finish - ids:  [{}], Response: [{}]", products, response);
//
//		return response;
//
//	}
//
//	private Product getProductById(Long id) {
//
//		log.debug("PurchaseService.getProductById - Start - id:  [{}]", id);
//
//		Optional<Product> product = productRepository.findById(id);
//
//		if(product.isPresent()) {
//
//			return product.get();
//
//		}
//
//		log.debug("PurchaseService.getProductById - Finish - id:  [{}]", id);
//
//		throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
//
//	}
//
//}
