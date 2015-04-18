package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder 
{
	private Id productId;
	private Money money;	
	private String name;	
	private Date snapshotDate;		
	private ProductType type;
	
	
	public ProductDataBuilder() 
	{
		productId = new Id("P1");
		money = new Money(10);
		name = "none";
		snapshotDate = new Date(2014);
		type = ProductType.STANDARD;
	}
	
	public ProductDataBuilder withProductId(Id productId)
	{
		this.productId = productId;
		return this;
	}
	
	public ProductDataBuilder withName(String name) 
	{
		this.name = name;
		return this;
	}
	
	public ProductDataBuilder withSnapshotDate(Date snapshotDate) 
	{
		this.snapshotDate = snapshotDate;
		return this;
	}
	
	public ProductDataBuilder withType(ProductType type) 
	{
		this.type = type;
		return this;
	}
	
	public ProductDataBuilder withMoney(int money) 
	{
		this.money = new Money(money);
		return this;
	}
	
	public ProductData build(){
		return new ProductData(productId, money, name, type, snapshotDate);
	}
}
