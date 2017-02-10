import static org.junit.Assert.assertEquals;

/**
 * Created by julià on 01/02/2017.
 */
public class main {
    public static void main(String[] args) {
        Polynomial p1, p2;
//        Polynomial[] res;

        p1 = new Polynomial("x^4 - 6x^2 + 8");
        p2 = new Polynomial("x - 1");
        p1.div(p2);
    }
}
