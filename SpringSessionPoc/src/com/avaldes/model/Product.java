package com.avaldes.model;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 2695828913725773456L;
	private String productId;
	private String name;
	private String description;
	private String imageUrl;
	private boolean isTaxable;
	private int qty;
	private float price;
	
	public Product(String productId, String name, String description,
			String imageUrl, boolean isTaxable, int qty, float price) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.isTaxable = isTaxable;
		this.qty = qty;
		this.price = price;
	}

	public Product() {}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	public boolean isTaxable() {
		return isTaxable;
	}

	public void setTaxable(boolean isTaxable) {
		this.isTaxable = isTaxable;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name
				+ ", description=" + description + ", imageUrl=" + imageUrl
				+ ", isTaxable=" + isTaxable + ", qty=" + qty + ", price="
				+ price + "]";
	}
}
