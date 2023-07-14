import Model.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "https://dummyjson.com/products/category/smartphones"; // URL API untuk mendapatkan data JSON

        try {
            URL apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestProperty("X-Cons-ID", "harber123");
            connection.setRequestProperty("X-userkey", "_tabc4XbR");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            List<Product> products = parseJson(response.toString());

            // Mengurutkan data menggunakan selection sort berdasarkan harga terendah
            selectionSort(products);


            for (Product product : products) {
                System.out.println("ID: " + product.getId());
                System.out.println("Title: " + product.getTitle());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Price: $" + product.getPrice());
                System.out.println("Discount Percentage: " + product.getDiscountPercentage() + "%");
                System.out.println("Rating: " + product.getRating());
                System.out.println("Stock: " + product.getStock());
                System.out.println("Brand: " + product.getBrand());
                System.out.println("Category: " + product.getCategory());
                System.out.println("Thumbnail: " + product.getThumbnail());
                System.out.println("Images: " + product.getImages());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Product> parseJson(String jsonString) {
        List<Product> products = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray productsArray = jsonObject.getJSONArray("products");

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObject = productsArray.getJSONObject(i);

                int id = productObject.getInt("id");
                String title = productObject.getString("title");
                String description = productObject.getString("description");
                double price = productObject.getDouble("price");
                double discountPercentage = productObject.getDouble("discountPercentage");
                double rating = productObject.getDouble("rating");
                int stock = productObject.getInt("stock");
                String brand = productObject.getString("brand");
                String category = productObject.getString("category");
                String thumbnail = productObject.getString("thumbnail");

                JSONArray imagesArray = productObject.getJSONArray("images");
                List<String> images = new ArrayList<>();
                for (int j = 0; j < imagesArray.length(); j++) {
                    String imageUrl = imagesArray.getString(j);
                    images.add(imageUrl);
                }

                Product product = new Product(id, title, description, price, discountPercentage, rating, stock, brand, category, thumbnail, images);
                products.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return products;
    }

    private static void selectionSort(List<Product> products) {
        int n = products.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (products.get(j).getPrice() < products.get(minIndex).getPrice()) {
                    minIndex = j;
                }
            }
            Product temp = products.get(minIndex);
            products.set(minIndex, products.get(i));
            products.set(i, temp);
        }
    }
}
