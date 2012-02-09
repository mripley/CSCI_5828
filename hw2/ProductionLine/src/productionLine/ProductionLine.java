package productionLine;

import java.util.LinkedList;
import java.util.List;

public class ProductionLine {

	private volatile List<Product> products;

	public ProductionLine() {
		products = new LinkedList<Product>();
	}

	public int size() {
		return products.size();
	}

	public void append(Product p) {
		products.add(p);
	}

	public Product retrieve() {
		return products.remove(0);
	}

}