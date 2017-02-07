import org.hamcrest.core.SubstringMatcher;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Polynomial {
   public float cfs[];

    String[] cffsc;


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
        String[] cffs;
        cffs = s.split("\\s");
        int cont=0;
        //Eliminal els lignes i si es negatiu l'afarram al valor
        for (int i = 0; i <cffs.length ; i++) {
            //si no es positiu affaram el signa al numero
            if(cffs[i].contains("+")){ cffs[i]= null;}else if(cffs[i].contains("-")&&cffs[i].length()<2){
                cffs[i+1]="-"+cffs[i+1];
                cffs[i]=null;
            }
            if(cffs[i]!= null){cont++;}
        }
        //Eliminam els nulls
        cffsc=new String[cont];
        for (int i = 0, y=0; i <cffs.length ; i++) {
            if(cffs[i]!= null){
                cffsc[y]=cffs[i];
                y++;
            }
        }
        //pot ser que hi hagui codi repetit
       int[] exp = new int[cffsc.length];
        float [] coefi=new float[cffsc.length];
        for (int i = 0; i <cffsc.length ; i++) {
           int n= cffsc[i].indexOf("^");
            int p= cffsc[i].indexOf("x");
            n++;
            if (cffsc[i].contains("x^")){
                exp[i]=Integer.parseInt( cffsc[i].substring(n));
               //comprobam si la variable es 0 que me posi un 1 o -1
                if(cffsc[i].substring(0,p).equals("-")) {
                    coefi[i] = -1;
                    continue;
                } else if(p==0) {
                    coefi[i] = 1;
                    continue;
                }
                coefi[i]=Integer.parseInt( cffsc[i].substring(0,p));
                continue;
            }else if(cffsc[i].contains("x")){
                exp[i]=1;
                //comprobam si la variable es 0 que me posi un 1 o -1
                if(cffsc[i].substring(0,p).equals("-")) {
                    coefi[i] = -1;
                    continue;
                } else if(p==0) {
                coefi[i] = 1;
                continue;
                }
                coefi[i]=Integer.parseInt( cffsc[i].substring(0,p));


            }else {coefi[i]=Integer.parseInt( cffsc[i]);}

        }
        //volem sebre quin es la potencia mes alta
        //per poder crear un arrai de la dimensio
        int x=0;
        for (int i = 0; i <exp.length ; i++) {
            if(exp[i]>x){x=exp[i];}
        }
        //colocam els verlors per els seus exponents
       float[] cfso=new float[x+1];
        for (int i = 0; i <coefi.length; i++) {
            //contemp que si els exponens son repetits mels sumi
            if(cfso[exp[i]]==0){
                cfso[exp[i]] = coefi[i];
            }else {
                cfso[exp[i]] = coefi[i] + cfso[exp[i]];
            }

        }
        cfs=new float[x+1];
        //Giram l'array per poderla pasar al toString
        for (int i = x, d=0; i >=0 ; i--, d++) {
            cfs[d]=cfso[i];
        }
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        Polynomial resultat = new Polynomial();

        

        return resultat;
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
        int p=0;
        for (int i = 0; i < this.cfs.length; i++) {
            ccc[i]=(int)cfs[i];
            int n = ccc[i];
            if(cfs.length == 1){
                sb.append((int) cfs[i]);
                continue;
            }

            if(ccc[i]==0&&exp>0){
                exp--;
                p++;
                continue;
            }
            if(i<this.cfs.length-1) {
                if(ccc[i]>0&&i!=p){sb.append(" + ");}else if(ccc[i]<0&&i!=p) {ccc[i]=n*(-1);sb.append(" - "); }
                if(ccc[i]<0&& i != 0){ccc[i]=n*-1;}
                if(ccc[i]==0){exp--; continue;}
                if(ccc[i]==1&&exp==1){sb.append("x"); continue;}
                if(exp==1){sb.append(ccc[i]+"x"); continue;}
                if(ccc[i]==1&&i==0){sb.append("x^" + exp); exp--; continue;}
                if(ccc[i]==-1&&i==0){sb.append("-x^" + exp); exp--; continue;}
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
