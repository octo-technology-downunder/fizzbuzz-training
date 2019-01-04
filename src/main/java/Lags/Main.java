package Lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  static final boolean debug = true;
  static Console console = new Console();
  // ==================
  // main function
  // ===================

  public static void main(String[] args) throws IOException {
    LagsService service = new LagsService();
    service.getFileOrder("..\\ORDRES.CSV");
    boolean flag = false;
    // While it's not the end
    while (!flag) {
      // command is now Z
      char command = 'Z';
      while (command != 'A' && command != 'L' && command != 'S' && command != 'Q'
          && command != 'C') {
        console.println("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        command = br.readLine().charAt(0);
        command = Character.toUpperCase(command);
        System.out.println();
      }
      switch (command) {
        case 'Q': {
          flag = true;
          break;
        }
        case 'L': {
          service.list();
          break;
        }
        case 'A': {
          service.addOrder();
          break;
        }
        case 'S': {
          service.suppress();
          break;
        }
        case 'C': {
          service.calculateTheGS(debug);
          break;
        }
      }
    }
  }

}