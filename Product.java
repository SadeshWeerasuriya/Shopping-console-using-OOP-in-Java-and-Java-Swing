public abstract class Product implements Comparable<Product> {
    private String productId;
    private String productName;
    private int noOfAvailableItems;
    private double price;
    private String productType;
    public  Product(String productId,String productName, int noOfAvailableItems, double price, String productType){
        this.productId = productId;
        this.productName = productName;
        this. noOfAvailableItems = noOfAvailableItems;
        this.price = price;
        this.productType = productType;
    }
    public String getProductId(){
        return productId;
    }
    public String getProductName(){
        return productName;
    }
    public int getNoOfAvailableItems(){
        return noOfAvailableItems;
    }
    public double getPrice(){
        return price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    @Override
    public String toString() {
        return "Product{" + "productId='" + productId + '\'' + ", productName='" + productName + '\'' + ", noOfAvailableItems=" + noOfAvailableItems + ", price=" + price + ", productType='" + productType + '\'' + '}';
    }
    public int compareTo(Product otherProduct){
        return this.productId.compareTo(otherProduct.getProductId());
    }
}
