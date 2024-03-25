public class Clothing extends Product {
    private int size;
    private String colour;

    public Clothing(String productId, String productName, int noOfAvailableItems, double price, String productType, int size, String colour) {
        super(productId, productName, noOfAvailableItems, price, productType);
        this.size = size;
        this.colour = colour;
    }

    public String saveClothingData(){
        return("Clothing" + "__" + getProductId() + "__" + getProductName() + "__" + getNoOfAvailableItems() + "__" + getPrice() + "__" + getProductType() + "__" + getSize() + "__" + getColour());
    }

    public int getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Clothing{" + "size=" + size + ", colour='" + colour + '\'' + '}';
    }
}
