package Lags;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public List<Order> getFiles(String fileName) throws IOException {
        List<Order> listOrder = new ArrayList<>();
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
            return listOrder;
    }
}
