public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;



    public Electronics(String productId,String productName, int noOfAvailableItems, double price, String productType, String brand, int warrantyPeriod){
        super(productId, productName, noOfAvailableItems,price, productType);
        this.brand = brand;
        this.warrantyPeriod= warrantyPeriod;


    }

    public String saveElectronicData(){
        return("Electronic" + "__" + getProductId() + "__" + getProductName() + "__" + getNoOfAvailableItems() + "__" + getPrice() + "__" + getProductType() + "__" + getBrand() + "__" + getWarrantyPeriod());
    }




        public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" + "brand='" + brand + '\'' + ", warrantyPeriod=" + warrantyPeriod + '}';
    }
}
