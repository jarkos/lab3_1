package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;


import java.math.BigDecimal;
import java.util.Date;

import org.hamcrest.core.IsSame;
import org.junit.Test;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest 
{

	@Test
	public void giveInvoiceRequestWithOnePosition_shouldGetInvoiceWithOnePosition() 
	{

		InvoiceFactory invoiceFactory = mock(InvoiceFactory.class);
		TaxPolicy taxPolicy = mock(TaxPolicy.class);
		
		BookKeeper bookKeeper = new BookKeeper(invoiceFactory);
		Id clientId = new Id("K1");
		ClientData clientData = new ClientData(Id.generate(), "Data");
		when(invoiceFactory.create(clientData)).thenReturn(new Invoice(clientId, clientData));
		
		ProductType productType = ProductType.STANDARD;
		BigDecimal denomination = new BigDecimal(10);
		Money money = new Money(denomination);
		when(taxPolicy.calculateTax(productType, money)).thenReturn(new Tax(money, "opis"));
		
		Id productId = new Id("P2");
		Date date = new Date(2014);
		ProductData productData = new ProductData(productId,money,"phone",productType,date);		
		
		RequestItem requestItem = new RequestItem(productData, 1,money);
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		invoiceRequest.add(requestItem);
		
		Invoice resultInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		int result = resultInvoice.getItems().size();
		assertThat(result, is(1));
		
	}
	
	
	
}
