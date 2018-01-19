package Lags;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PricingService {

    private double GS(List<Order> orders, boolean debug) {
        if (orders.size() == 0)
            return 0.0;
        Order orderr = orders.get(0);
        // Warning : doesn't work for order that span on two years
        // see PLAF ticket nO 4807
        List<Order> list = orders.stream().filter(o -> o.getStart() >= (orderr.getStart() + orderr.getDuration())).collect(Collectors.toList());
        List<Order> list2 = orders.subList(1, orders.size());
        double gs = orderr.getPrice() + GS(list, debug);
        double gs2 = GS(list2, debug);
        System.out.println(debug ? new DecimalFormat("#.##").format(Math.max(gs, gs2)) : ".");
        return Math.max(gs, gs2);
    }



    public void calculateTheGS(boolean debug, List<Order> listOrder) {
        System.out.println("CALCULATING GS..");
        listOrder = listOrder
                .stream()
                .sorted(Comparator.comparingInt(Order::getStart))
                .collect(Collectors.toList());
        double ca = GS(listOrder, debug);
        System.out.print("GS: ");
        System.out.printf(new DecimalFormat("#.##").format(ca));
        System.out.println();
    }

}