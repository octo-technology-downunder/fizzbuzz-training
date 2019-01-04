package Lags;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LagsService {

  List<Order> listOrder = new ArrayList<>();
  Console console = new Console();
  Scanner scanner = new Scanner();

  // read the order fil and calculate gross sales
  public void getFileOrder(String fileName) {
    try {
      File file = new File(fileName);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        String[] champs = line.split(";");
        String fld1 = champs[0];
        int fld2 = Integer.parseInt(champs[1]);
        int filed3 = Integer.parseInt(champs[2]);
        double fld4 = Double.parseDouble(champs[3]);
        Order order = new Order(fld1, fld2, filed3, fld4);
        listOrder.add(order);
      }
      br.close();
      fr.close();
    } catch (FileNotFoundException e) {
      console.println("CSV FILE NOT FOUND; CREATING ONE.");
      writeOrders(fileName);
    } catch (IOException e) {
    }
  }

  // write file order
  void writeOrders(String nomFich) {
    try {
      FileWriter writer = new FileWriter(new File(nomFich));
      for (Order order : listOrder) {
        String[] CSVline = new String[4];
        CSVline[0] = order.getId();
        CSVline[1] = Integer.toString(order.getStart());
        CSVline[2] = Integer.toString(order.getDuration());
        CSVline[3] = Double.toString(order.getPrice());
        writer.write(String.join(";", CSVline) + "\n");
      }
      writer.close();
    } catch (IOException e) {
      // TODO IMPLEMENTS
    }
  }

  // show order list
  public void list() {
    console.println("ORDERS LIST");
    console.println(String.format("%-8s %10s %5s %10s", "ID", "DEBUT", "DUREE", "PRIX"));
    console.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
    orders().stream().sorted((o1, o2) -> Integer.compare(o1.getStart(), o2.getStart()))
        .forEach(this::showOrder);
    console.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
  }

  List<Order> orders(){
    return listOrder;
  }

  public void showOrder(Order order) {
    console.println(String
        .format("%-8s %10d %5d %10f", order.getId(), order.getStart(), order.getDuration(),
            order.getPrice()));
  }

  // Add an order; GS is recalculated
  public void addOrder() throws IOException {
    console.println("ADD ORDER");
    console.println("FORMAT = ID;STARTT;END;PRICE");

    String line = scanner.scan();

    line = line.toUpperCase();
    String[] fields = line.split(";");
    String id = fields[0];
    int st = Integer.parseInt(fields[1]);
    int dur = Integer.parseInt(fields[2]);
    double pr = Double.parseDouble(fields[3]);
    Order order = new Order(id, st, dur, pr);
    listOrder.add(order);
    writeOrders("..\\ordres.csv");
  }

  //public void CalculateGS()
  //{
  //    console.println();("CALCULATING GS..");
  //    theList = theList.OrderBy(ordre => ordre.debut).ToList();
  //    double gs = GS(laListe);
  //    console.println();("GS: {0,10:N2}", ca);
  //}

  private double GS(List<Order> orders, boolean debug) {
    // No order,job done, TROLOLOLO..
    if (orders.size() == 0) {
      return 0.0;
    }
    Order orderr = orders.get(0);
    // Warning : doesn't work for order that span on two years
    // see PLAF ticket nO 4807
    List<Order> list = orders.stream()
        .filter(o -> o.getStart() >= (orderr.getStart() + orderr.getDuration()))
        .collect(Collectors.toList());
    List<Order> list2 = orders.subList(1, orders.size());




    double gs = orderr.getPrice() + GS(list, debug);
    // I can live.... with or withoooooout youuuuuu!
    double gs2 = GS(list2, debug);
    console.println(debug ? new DecimalFormat("#.##").format(Math.max(gs, gs2)) : ".");
    return Math.max(gs, gs2); // LOL
  }




  // file update
  public void suppress() throws IOException {
    console.println("DELETE ORDER");
    console.println("ID:");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String id = br.readLine();
    this.listOrder = listOrder.stream().filter(o -> !o.getId().equals(id.toUpperCase()))
        .collect(Collectors.toList());
    writeOrders("..\\ORDRES.CSV");
  }

  void calculateTheGS(boolean debug) {
    console.println("CALCULATING GS..");
    listOrder = listOrder
        .stream()
        .sorted((o1, o2) -> Integer.compare(o1.getStart(), o2.getStart()))
        .collect(Collectors.toList());
    double ca = GS(listOrder, debug);
    System.out.print("GS: ");
    console.printf(new DecimalFormat("#.##").format(ca));
    console.println("");
  }

}