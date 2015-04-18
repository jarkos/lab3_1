package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Tax;


public class RequestItemBuilder 
{
	private int quantity;
	private Money money;
    private Tax tax;
	private Date date;
    private ProductData productdata;
    
    public RequestItemBuilder()
    {
    	quantity = 1;
    	money = new Money(10);
        tax = new Tax(money,"tax");
    	date = new Date(2014);
        productdata =  new ProductDataBuilder().withName("phone").withMoney(10).build();
    }
    
	public RequestItemBuilder withMoney(Money money)
	{
		this.money=money;
		return this;
	}
    
	public RequestItem build()
	{
		return new RequestItem(productdata,10,money);
		
	}
	
	public RequestItemBuilder withQuantity(int quantity)
	{
		this.quantity = quantity;
		return this;
	}
	
	public RequestItemBuilder withTax(Tax tax)
	{
		this.tax = tax;
		return this;
	}
	
	public RequestItemBuilder withDate(Date date)
	{
		this.date = date;
		return this;
	}

	public RequestItemBuilder withProductData(ProductData productData)
	{
		this.productdata = productdata;
		return this;
	}

}
