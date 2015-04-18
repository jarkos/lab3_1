package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest 
{
	InvoiceFactory invoiceFactory;
	TaxPolicy taxPolicy;
	BookKeeper bookKeeper;
	ClientData clientData;
	
	@Before
	public void setUp() throws Exception 
	{
		invoiceFactory = mock(InvoiceFactory.class);
		taxPolicy = mock(TaxPolicy.class);

		bookKeeper = new BookKeeper(invoiceFactory);
		Id clientId = new Id("K1");
		clientData = new ClientData(Id.generate(), "Data");
		when(invoiceFactory.create(clientData)).thenReturn(new Invoice(clientId, clientData));
	}
	
	@Test
	public void giveInvoiceRequestWithOnePosition_shouldGetInvoiceWithOnePosition() 
	{
		ProductType productType = ProductType.STANDARD;
		Money money = new Money(10);
		
		when(taxPolicy.calculateTax(productType, money)).thenReturn(new Tax(money, "opis"));
		
		ProductData productData = new ProductDataBuilder().withName("phone").withMoney(10).build();
		
		RequestItem requestItem = new RequestItem(productData, 1,money);
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		invoiceRequest.add(requestItem);
		
		Invoice resultInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		int result = resultInvoice.getItems().size();
		assertThat(result, is(1));
		
	}
	
	@Test
	public void giveInvoiceRequestWithTwoPosition_shouldInvokeCalculateTaxMethodTwice() 
	{		
		ProductType productType = ProductType.STANDARD;
		BigDecimal denomination = new BigDecimal(10);
		Money money = new Money(denomination);
		when(taxPolicy.calculateTax(productType, money)).thenReturn(new Tax(money, "opis"));
		//product1
		ProductData productData1 = new ProductDataBuilder().withName("phone").withMoney(10).build();
		//product2
		ProductData productData2 = new ProductDataBuilder().withName("phone2").withMoney(10).build();
		
		RequestItem requestItem1 = new RequestItem(productData1, 1,money);
		RequestItem requestItem2 = new RequestItem(productData2, 1,money);
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		invoiceRequest.add(requestItem1);
		invoiceRequest.add(requestItem2);
		
		Invoice resultInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		Mockito.verify(taxPolicy, Mockito.times(2)).calculateTax(productType, money);
		
	}	
}
