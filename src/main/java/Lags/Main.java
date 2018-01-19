package Lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final PricingService service = new PricingService();
    private static final OrderService orderService = new OrderService();

    static final boolean debug = true;

    public static void main(String[] args) throws IOException
    {

        orderService.getFileOrder("..\\ORDRES.CSV");

        handleInput();
    }

    private static void handleInput() throws IOException {
        boolean flag = false;
        while (!flag)
        {
            char command = 'Z';
            while (command != 'A' && command != 'L' && command != 'S' && command != 'Q' && command != 'C')
            {
                System.out.println("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                command = br.readLine().charAt(0);
                command = Character.toUpperCase(command);
                System.out.println();
            }
            switch (command)
            {
                case 'Q':
                {
                    flag = true;
                    break;
                }
                case 'L':
                {
                    orderService.printOrders();
                    break;
                }
                case 'A':
                {
                    orderService.addOrder();
                    break;
                }
                case 'S':
                {
                    orderService.suppress();
                    break;
                }
                case 'C':
                {
                    service.calculateTheGS(debug,orderService.getListOrder());
                    break;
                }
            }
        }
    }
}