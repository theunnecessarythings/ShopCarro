package com.example.meghamoondra.navigationbar.model;

import com.google.gson.annotations.SerializedName;

public class Stock{

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("noOfItems")
	private int noOfItems;

	@SerializedName("rating")
	private Double rating;

	@SerializedName("productPrice")
	private int productPrice;

	@SerializedName("merchantName")
	private String merchantName;

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setNoOfItems(int noOfItems){
		this.noOfItems = noOfItems;
	}

	public int getNoOfItems(){
		return noOfItems;
	}

	public void setRating(Double rating){
		this.rating = rating;
	}

	public Double getRating(){
		return rating;
	}

	public void setProductPrice(int productPrice){
		this.productPrice = productPrice;
	}

	public int getProductPrice(){
		return productPrice;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
 	public String toString(){
		return 
			"Stock{" + 
			"productId = '" + productId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",noOfItems = '" + noOfItems + '\'' + 
			",rating = '" + rating + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}