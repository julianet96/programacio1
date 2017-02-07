import static org.junit.Assert.assertEquals;

/**
 * Created by julià on 01/02/2017.
 */
public class main {
    public static void main(String[] args) {
        Polynomial p1,p2;
        p1 = new Polynomial("-x^2 + 10");
        p2 = new Polynomial("73x^8 + 3x^4");
       p1.add(p2).toString();

    }
}
