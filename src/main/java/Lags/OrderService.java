package Lags;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> listOrder = new ArrayList<>();
    private FileService fileService = new FileService();

    public void getFileOrder(String fileName) {
        try {
            listOrder = fileService.getFiles(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("CSV FILE NOT FOUND; CREATING ONE.");
            writeOrders(fileName);
        } catch (IOException e) {
        }
    }

    private void writeOrders(String nomFich) {
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
            // TODO CRU what do you want to do here ?
        }
    }

    public void suppress() throws IOException {
        System.out.println("DELETE ORDER");
        System.out.println("ID:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String id = br.readLine();
        this.listOrder = listOrder.stream().filter(o -> !o.getId().equals(id.toUpperCase())).collect(Collectors.toList());
        writeOrders("..\\ORDRES.CSV");
    }


    // show order printOrders
    public void printOrders() {
        System.out.println("ORDERS LIST");
        System.out.println(String.format("%-8s %10s %5s %10s", "ID", "DEBUT", "DUREE", "PRIX"));
        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
        listOrder.stream().sorted(Comparator.comparingInt(Order::getStart)).forEach(this::showOrder);
        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
    }

    private void showOrder(Order order) {
        System.out.println(order.toString());
    }

    // Add an order; GS is recalculated
    public void addOrder() throws IOException {
        System.out.println("ADD ORDER");
        System.out.println("FORMAT = ID;STARTT;END;PRICE");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();

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

    public List<Order> getListOrder() {
        return listOrder;
    }

}
