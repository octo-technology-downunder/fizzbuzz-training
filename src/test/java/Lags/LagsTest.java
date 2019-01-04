package Lags;

import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class LagsTest {

  @Spy
  private Console console;

  @Mock
  private Scanner scanner;

  @InjectMocks
  LagsService lagsService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testEmptyList() {
    lagsService.list();
    Mockito.verify(console).println("ORDERS LIST");
    Mockito.verify(console).println("ID            DEBUT DUREE       PRIX");
    Mockito.verify(console, Mockito.times(2)).println("--------    ------- ----- ----------");
  }

  @Test
  public void testAddOrder() throws IOException {
    Mockito.when(scanner.scan()).thenReturn("A001;2014360;2015002;99.99");
    lagsService.addOrder();
    List<Order> orderList = lagsService.orders();

    Assert.assertEquals(1, orderList.size());
    Assert.assertEquals(new Order("A001", 2014360, 2015002, 99.99d), orderList.get(0));
  }

  @Test
  public void testCalculateTheGS_withOneOrder() throws IOException {
    Mockito.when(scanner.scan()).thenReturn("A001;2014360;2015002;99.99");
    lagsService.addOrder();
    lagsService.calculateTheGS(false);
    Mockito.verify(console).printf("99.99");
  }

  @Test
  public void testCalculateTheGS_withTwoOrders() throws IOException {
    Mockito.when(scanner.scan()).thenReturn("A001;2014360;3;99.99");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("A022;2015360;15;599.99");
    lagsService.addOrder();
    lagsService.calculateTheGS(false);
    Mockito.verify(console).printf("699.98");
  }

  @Test
  public void testCalculateTheGS_withFiveOrders() throws IOException {
    Mockito.when(scanner.scan()).thenReturn("A001;2014360;6;99.99");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("A022;2015360;1;599.99");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("A026;2015360;30;89.10");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("A026;2010260;15;89.10");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("A026;2014360;35;789.10");
    lagsService.addOrder();

    lagsService.calculateTheGS(false);
    Mockito.verify(console).printf("1478.19");
  }

  @Test
  public void testTristansBug() throws IOException {
    Mockito.when(scanner.scan()).thenReturn("ID01;2015365;15;1000");
    lagsService.addOrder();

    Mockito.when(scanner.scan()).thenReturn("ID02;2016001;5;2000");
    lagsService.addOrder();

    lagsService.calculateTheGS(false);
    Mockito.verify(console).printf("2000");
  }
}
