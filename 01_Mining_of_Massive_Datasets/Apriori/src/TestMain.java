import java.io.InputStream;

public class TestMain {

    public static void main(String[] args) throws Exception {
        Apriori apriori = null;
        try (InputStream inputStream = TestMain.class.getResourceAsStream("input")) {
            apriori = new Apriori(inputStream);
        }
        System.out.println("Round 1");
        apriori.printResult();
        System.out.println("Round 2");
        apriori.iterate();
        apriori.printResult();
        System.out.println("Round 3");
        apriori.iterate();
        apriori.printResult(); 
    }
}
