import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public ShoppingCart(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }


    public double calculateTotalCost() {
        double totalCost = 0.0;


        if (products != null) {
            for (Product product : products) {
                if (product != null) {

                    double productPrice = product.getPrice();
                    totalCost += productPrice;
                } else {

                    System.out.println(" The product found is a null product!");
                }
            }
        } else {

            System.out.println("The product list is a null list");
        }

        return totalCost;
    }
}
