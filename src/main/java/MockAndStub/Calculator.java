package MockAndStub;

public class Calculator {
    private SomeStuff someStuff;

    public Calculator() {
        this.someStuff = new SomeStuff();
    }

    public String calculate(String input) {

        if(someStuff.handleInput(input).equals("")) {
            return "Handled";
        } else {
            return "Something";
        }
    }
}
