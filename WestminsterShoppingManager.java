import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class WestminsterShoppingManager implements ShoppingManager {
    public List<Product> getProducts() {
        return products;
    }

    int maxNoOfProducts = 50;
    //Declaring the arrayList to store the products
    private List<Product> products;
    //Declaring an integer variable to store the user's menu option
    int option1;



    // Creating a Scanner object for user input
    Scanner scanner = new Scanner(System.in);


//Constructor without parameters
    public WestminsterShoppingManager() {

        this.products = new ArrayList<>(); // Initialize the products ArrayList when an instance is created
    }
//Constructor with parameters
    public WestminsterShoppingManager(List < Product > products) {

        this.products = products; // Initialize the products ArrayList with the provided list

    }


    public void startMenu() {

        System.out.print("man or cus: ");
        String user = scanner.next();

        switch (user) {
            case "man":
                do {
                    // displaying the console menu
                    System.out.println("""
                            Welcome to Weerasyriya's shopping management system
                            *          ************        *
                            *****     Console Menu     *****
                            *          ************        *
                            1. Add a new product
                            2. Delete a product
                            3. Print the list of the products
                            4. save in a file
                            5. load file
                            6. Exit from console
                            ********************************
                            """);
                    System.out.print("Enter your choice (1,2,3,4,5 or 6) : ");
                    //reading user input as an integer

                    try {
                        option1 = scanner.nextInt();
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println(" your input is invalid. please enter value 1,2,3,4,5 or 6");
                        scanner.nextLine();
                        option1 = -1;
                    }


                    switch (option1) {
                        case 1:
                            addProduct();
                            break;
                        case 2:
                            System.out.println("Enter the specific product id to delete");
                            String deletesTheProduct = scanner.next();
                            deleteProduct(deletesTheProduct);
                            break;
                        case 3:
                            printProducts();
                            break;

                        case 4:
                            saveData();
                            break;

                        case 5:
                            loadData();
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;

                    }


                } while (option1 != 6);
                scanner.close();
                break;

            case "cus":
                SwingUtilities.invokeLater(() -> {
                    new Gui().createAndShowGUI();
                });
                break;

            default:
                System.out.println("Invalid Input");
                break;

        }
    }




// Method adding new product
        public void addProduct () {
        try{

            if (products.size() >= maxNoOfProducts) {
                System.out.println("Max limit exceeded! Cannot add any more products");
                return;
            }
            String productType;
            do {
//Prompt the user to enter the product type (Electronics or Clothing)
                System.out.println("Enter the product type - Electronics or Clothing");
                productType = scanner.nextLine().toLowerCase();
            } while (!productType.equals("electronics")&& !productType.equals("clothing"));



            System.out.println("Enter a product ID");
            String productId;
            do{
             productId = scanner.nextLine();
             if (isProductIdExists(productId)){
                 System.out.println("The product Id you entered already exists. Please enter a unique product id");
             }
            }while (isProductIdExists(productId));

            System.out.println("Enter a product name");
            String productName = scanner.nextLine();

            int noOfAvailableItems;

            noOfAvailableItems = validateUserInputsForInt("Available items: ");

            double price;
            price = validateUserInputsForDouble("Price: ");
            // Check the product type and create the corresponding product

            if (productType.equals("electronics")) {

                System.out.println("Enter the brand name");
                String brand = scanner.next();
                int warrantyPeriod;

                warrantyPeriod = validateUserInputsForInt("warranty: ");

                Product product1 = new Electronics(productId, productName, noOfAvailableItems, price, productType, brand, warrantyPeriod);
                products.add(product1);

            } else if (productType.equals("clothing")) {

                System.out.println("Enter the size of the cloth");
                int size = scanner.nextInt();


                System.out.println("Enter the colour of the cloth");
                String colour = scanner.next();

                Product product1 = new Electronics(productId, productName, noOfAvailableItems, price, productType, colour, size);
                products.add(product1);

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter valid details");
            scanner.nextLine();
        }
                }
                private boolean isProductIdExists(String productId){
                for (Product product : products){
                    if (product.getProductId().equals(productId)){
                        return true;
                    }
                }
                return false;

                }


                public int validateUserInputsForInt(String inputs){
                    int productDetails;
                    do {
                        productDetails=0;
                        try {
                            Scanner scan = new Scanner(System.in);
                            System.out.print(inputs);
                            productDetails = scan.nextInt();
                        }catch (Exception e){
                            System.out.println("Invalid input");
                        }
                    }while(productDetails<=0);
                    return productDetails;
                }
// Method to valiadate user input for double values
    public double validateUserInputsForDouble(String inputs){
        int productDetails;
        do {
            productDetails=0;
            try {
                Scanner scan = new Scanner(System.in);
                System.out.print(inputs);
                productDetails = scan.nextInt();
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }while(productDetails<=0);
        return productDetails;
    }

    public void deleteProduct (String productId) {
        try{
            Product deletingproduct = null;
            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    deletingproduct = product;
                    products.remove(product);
                    System.out.println("The product was successfully deleted");
                    if (deletingproduct instanceof Electronics) {
                        System.out.println("An electric product from the electronics class has been deleted");

                    } else if (deletingproduct instanceof Clothing) {
                        System.out.println("A clothing product from the clothing class has been deleted");
                    }
                    break;
                }
            }
            if(deletingproduct == null){
                System.out.println("No such product found");
            }
            try {
                System.out.println("The total number of products left is:" + products.size());
            }catch (Exception e){
                System.out.println("error");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter valid details");
            scanner.nextLine();
        }
    }
        public void printProducts(){
        try{
            if (products.isEmpty()){
                System.out.println("There is no products! All are empty");
                return;
            }
            List<Product> productsToBeSorted = new ArrayList<>(products);
            Collections.sort(productsToBeSorted);
            System.out.println("The products are sorted according to the product ID" );
            for (Product product : productsToBeSorted){
                if (product instanceof Electronics){
                    Electronics electronics = (Electronics) product;
                    System.out.println("The product type is- Electronics");
                    System.out.println("product ID:"+ electronics.getProductId());
                    System.out.println("product name:" + electronics.getProductName());
                    System.out.println("items available:" + electronics.getNoOfAvailableItems());
                    System.out.println("price:" + electronics.getPrice());
                    System.out.println("Brand" + electronics.getBrand());
                    System.out.println("warranty: " + electronics.getWarrantyPeriod() + "years");
                    System.out.println();
                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    System.out.println("The product type is- Clothing");
                    System.out.println("product ID:"+ clothing.getProductId());
                    System.out.println("product name:" + clothing.getProductName());
                    System.out.println("items available:" + clothing.getNoOfAvailableItems());
                    System.out.println("price:" + clothing.getPrice());
                    System.out.println("Size:" + clothing.getSize());
                    System.out.println("Colour:" + clothing.getColour());
                    System.out.println();

                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Enter valid details");
            scanner.nextLine();
        }
    }
// this method Writes the product data to a file,
// appending each product's details to a new line.
// It checks the type of each product and uses specialized methods (saveElectronicData() and saveClothingData()) to get the formatted data.
    public void saveData(){
        try(FileWriter dataFile = new FileWriter("dataFile.txt")){
            for(Product prod: products){
                if(prod instanceof Electronics){
                    dataFile.write(((Electronics) prod).saveElectronicData() + "\n");
                }else{
                    dataFile.write(((Clothing) prod).saveClothingData() + "\n");
                }
            }
            System.out.println("Data saved successfully");
        }catch(Exception e){
            System.out.println("An error occurred");
        }
    }


    public void loadData(){
        try{
            File dataFile = new File("dataFile.txt");
            Scanner fileScanner = new Scanner(dataFile);
            while(fileScanner.hasNextLine()){
                String product = fileScanner.nextLine();
                String[] productDetails = product.split("__", 8);
                if(productDetails[0].equals("Electronic")){
                    Electronics electronics = new Electronics(productDetails[1], productDetails[2], Integer.parseInt(productDetails[3]), Double.parseDouble(productDetails[4]), productDetails[5], productDetails[6], Integer.parseInt(productDetails[7]));
                    products.add(electronics);
                }else{
                    Clothing clothing = new Clothing(productDetails[1], productDetails[2], Integer.parseInt(productDetails[3]), Double.parseDouble(productDetails[4]), productDetails[5], Integer.parseInt(productDetails[6]), productDetails[7]);
                    products.add(clothing);
                }
            }
            System.out.println("data loaded successfully");

        }catch (Exception e){
            System.out.println("An error occurred");
        }
    }



}


