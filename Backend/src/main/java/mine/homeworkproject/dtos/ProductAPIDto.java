package mine.homeworkproject.dtos;

import java.util.HashMap;
import java.util.Objects;

public class ProductAPIDto {
  private Long id;
  private String title;
  private Double price;
  private String description;
  private String category;
  private String image;
  private HashMap<String, Double> rating;

  public ProductAPIDto(Long id, String title, Double price, String description, String category,
      String image, HashMap<String, Double> rating) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.description = description;
    this.category = category;
    this.image = image;
    this.rating = rating;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public HashMap<String, Double> getRating() {
    return rating;
  }

  public void setRating(HashMap<String, Double> rating) {
    this.rating = rating;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductAPIDto that = (ProductAPIDto) o;
    return Objects.equals(id, that.id) && Objects.equals(title, that.title)
        && Objects.equals(price, that.price) && Objects.equals(description,
        that.description) && Objects.equals(category, that.category)
        && Objects.equals(image, that.image) && Objects.equals(rating,
        that.rating);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, price, description, category, image, rating);
  }
}
