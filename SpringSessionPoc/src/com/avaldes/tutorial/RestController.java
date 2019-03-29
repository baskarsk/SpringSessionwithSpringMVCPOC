package com.avaldes.tutorial;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avaldes.model.Product;
import com.avaldes.model.StatusMessage;

@Controller
public class RestController {

	private static final Logger logger = LoggerFactory
			.getLogger(RestController.class);
	public static final String APPLICATION_HTML = "text/html";
	public static final String APPLICATION_JSON = "application/json";
	private Map<String, Product> storeInventory = new HashMap<String, Product>();

	public RestController() {
		logger.info("Starting RestController...");

		storeInventory.put("B01M18UZF5",
				new Product("B01M18UZF5",
						"ASUS ZenBook UX330UA-AH54 13.3-inch Ultra-Slim Laptop",
						"ASUS Craftsmanship delivers an Ultra-Thin 0.5-inch "
						+ "profile with a 2-pound lightweight unibody design "
						+ "Our Toughest ZenBook designed with Premium "
						+ "Aerospace-grade Aluminum and Corning Gorilla Glass 4",
						"http://www.asus.com/zenbook/img/07-ux501-a.png", true,
						13, 679.95f));
		storeInventory.put("B015P3SKHQ",
				new Product("B015P3SKHQ",
						"Dell Inspiron i7359-8404SLV 13.3 Inch 2-in-1 "
						+ "Touchscreen Laptop",
						"Intel Dual Core i7-6500U 2.5 GHz Processor, 8 GB DDR3L "
						+ "SDRAM, 256 GB SSD Storage; Optical Drive Not included, "
						+ "13.3 Inch FHD (1920 x 1080 pixels) LED-lit Truelife "
						+ "Touchscreen",
						"http://ecx.images-amazon.com/images/I/414xadUGA5L._AC_SL230_.jpg",
						true, 7, 728.50f));
		storeInventory.put("B012DTEMQ8",
				new Product("B012DTEMQ8",
						"Microsoft Surface Pro 3 Tablet (12-Inch, 128 GB, Intel "
						+ "Core i5)",
						"Windows 10, 12-Inch Display, Intel Core i5 1.9 GHz "
						+ "Processor, 128 GB Flash Storage, 4 GB RAM, 1.76 pounds, "
						+ "36W Power Supply and Surface Pen Included; Keyboard "
						+ "sold separately",
						"https://images-na.ssl-images-amazon.com/images/I/81hXY3b5jgL._SL1500_.jpg",
						true, 11, 544.60f));
		storeInventory.put("B01EIUEGXO",
				new Product("B01EIUEGXO",
						"Apple MacBook MLHA2LL/A 12-Inch Laptop with Retina Display",
						"1.1GHz Dual Core Intel m3, 8GB RAM, 256GB HD, OS X",
						"http://pisces.bbystatic.com/image2/BestBuy_US/images/products/5229/5229700_sd.jpg",
						true, 3, 1249.00f));
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = APPLICATION_HTML)
	public @ResponseBody String status() {
		logger.info("Inside of status() method...");
		return "application OK...";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addProductToCart", 
	   method = RequestMethod.POST, consumes = APPLICATION_JSON)
	
	public @ResponseBody Map<String, Product> addProductToCart(
			HttpSession session, @RequestBody Product product) {

		logger.info("Inside of addProductToCart() method...");
		Map<String, Product> myShoppingCart = null;
		myShoppingCart = (Map<String, Product>) session
				.getAttribute("cart");
		if (myShoppingCart == null) {
			logger.info("myShoppingCart is empty...");
			myShoppingCart = new HashMap<String, Product>();
		}
		if (product != null) {
			if (myShoppingCart.containsKey(product.getProductId())) {
				Product cProd = myShoppingCart.get(product.getProductId());
				int cQty = cProd.getQty() + 1;
				product.setQty(cQty);
				logger.info("product Key found..: " + product.getProductId() + ", Qty..: " + cQty);
				myShoppingCart.remove(product.getProductId());
				myShoppingCart.put(product.getProductId(), product);
			} else {
				logger.info("Inserting product into myShoppingCart...");
				myShoppingCart.put(product.getProductId(), product);
			}
			logger.info("myShoppingCart..: " + myShoppingCart);
			session.setAttribute("developer", "Amaury");
			session.setAttribute("cart", myShoppingCart);
			showSessionAttributes(session);
		}
		return myShoppingCart;
	}

	@RequestMapping(value = "/emptyCart", 
	   method = RequestMethod.DELETE)
	
	public @ResponseBody StatusMessage emptyCart(
			HttpSession session) {

		logger.info("Inside of emptyCart() method...");
		session.removeAttribute("cart");
		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setStatus(200);
		statusMessage.setMessage("Successfully emptied cart.");
		
		return statusMessage;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCart", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Product> getCart(
			HttpSession session) {

		logger.info("Inside of getCart() method...");
		Map<String, Product> myShoppingCart = null;
		myShoppingCart = (Map<String, Product>) session
				.getAttribute("cart");

		if (myShoppingCart == null) {
			myShoppingCart = new HashMap<String, Product>();
		}
 
		return new ArrayList<Product>(myShoppingCart.values());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/removeProductFromCart", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Product> removeCart(
			@RequestParam("productId") String productId, HttpSession session) {

		logger.info("Inside of removeCart() method...");
		Map<String, Product> myShoppingCart = null;
		myShoppingCart = (Map<String, Product>) session
				.getAttribute("cart");

		if (myShoppingCart == null) {
			myShoppingCart = new HashMap<String, Product>();
		}

		if (productId != null) {
			if (myShoppingCart.containsKey(productId)) {
				logger
						.info("Found product with key " + productId + ", removing... ");
				myShoppingCart.remove(productId);
				session.setAttribute("cart", myShoppingCart);
			}
		}

		return myShoppingCart;
	}
	
	@RequestMapping(value = "/getAllInventory", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Product> getAllInventory() {

		logger.info("Inside of getAllInventory() method...");
		ArrayList<Product> inventoryList = new ArrayList<Product>(
				storeInventory.values());
		return inventoryList;
	}

	private void showSessionAttributes(HttpSession session) {

		logger.info("Inside of showSessionAttributes() method...");
		Enumeration<String> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			logger.info(key);
		}
	}
}