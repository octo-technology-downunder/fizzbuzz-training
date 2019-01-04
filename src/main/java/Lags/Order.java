package Lags;

import java.util.Objects;

public class Order {

  public Order(String id, int start, int duration, double price) {
    this.id = id;
    this.start = start; // YYYYDDD format example 25 feb 2015 = 2015056
    this.duration = duration;
    this.price = price;
  }

  //order id
  private String id;

  // start
  private int start;

  // end
  private int duration;

  // value
  private double price;

  // getter and setter
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double pricd) {
    this.price = pricd;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return start == order.start &&
        duration == order.duration &&
        Double.compare(order.price, price) == 0 &&
        Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, start, duration, price);
  }
}