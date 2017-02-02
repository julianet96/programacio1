import java.text.DecimalFormat;
import java.util.Arrays;

public class Polynomial {
   public float cfs[];


    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
      cfs = new float[1];
        cfs[0]= 0;
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        this.cfs =new float[cfs.length];
        for (int i = 0; i < cfs.length ; i++) {
            this.cfs[i]=cfs[i];
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        cfs = new float[1];
        cfs[0]= 0;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        return null;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        return null;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {
       return null;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        return p.toString().equals(this.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int [] ccc=new int[cfs.length];
        int exp = cfs.length -1;
        for (int i = 0; i < this.cfs.length; i++) {
            ccc[i]=(int)cfs[i];
            int n = ccc[i];
            if(cfs.length == 1){
                sb.append((int) cfs[i]);
                continue;
            }
            if(i<this.cfs.length-1) {
                if(ccc[i]>0&&i>0){sb.append(" + ");}else if(ccc[i]<0&&i!=0) {ccc[i]=n*(-1);sb.append(" - "); }
                if(ccc[i]<0&& i != 0){ccc[i]=n*-1;}
                if(ccc[i]==0){exp--; continue;}
                if(ccc[i]==1&&exp==1){sb.append("x"); continue;}
                if(exp==1){sb.append(ccc[i]+"x"); continue;}
                sb.append(ccc[i] + "x^" + exp);
                exp--;
                continue;
            }
            if(ccc[i]>0){sb.append(" + ");}else if(ccc[i]<0) {ccc[i]=n*(-1);sb.append(" - ");}
            if(i==this.cfs.length-1){
                sb.append(ccc[i]);
            }
        }
        return sb.toString();
    }

}
