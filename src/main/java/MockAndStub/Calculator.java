package MockAndStub;

public class Calculator {
    private RandomNumberGenerator randomNumberGenerator;

    public Calculator() {
        this.randomNumberGenerator = new RandomNumberGenerator();
    }

    public Calculator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public String calculate(String input) {
        return "";
    }
}
