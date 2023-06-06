package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayement;
		if(hours <=12 ) {
			basicPayement = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPayement = pricePerDay * Math.ceil(hours/24);
		}
		
		double tax = taxService.tax(basicPayement);
		
		carRental.setInvoice(new Invoice(basicPayement, tax));
	}
	
	
}
