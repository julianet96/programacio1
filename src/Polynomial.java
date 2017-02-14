import org.hamcrest.core.SubstringMatcher;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Polynomial {
   public float cfs[];
   public int exp[];
   public float coefi[];
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
        String[] cffsc=new String[cont];
        for (int i = 0, y=0; i <cffs.length ; i++) {
            if(cffs[i]!= null){
                cffsc[y]=cffs[i];
                y++;
            }
        }
        //pot ser que hi hagui codi repetit
        exp = new int[cffsc.length];
        coefi=new float[cffsc.length];
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

                //si no conte x que me escrigui el valor normal
            }else {coefi[i]=Integer.parseInt( cffsc[i]);}

        }
        //volem sebre quin es la potencia mes alta
        //per poder crear un arrai de la dimensio
        cfs = ordena(coefi,exp);
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        Polynomial resultat;
        float suma[];
        if(p.cfs.length==this.cfs.length) {
           suma= new float[p.cfs.length];
            for (int i = 0; i < p.cfs.length; i++) {
                suma[i] = p.cfs[i] + this.cfs[i];
            }
            resultat = new Polynomial(suma);
            return resultat;

        }else if(p.cfs.length<this.cfs.length){
            suma=new float[this.cfs.length];
            for (int i = 0,y=0; i <this.cfs.length ; i++) {
                int comensa=this.cfs.length-p.cfs.length;
                if (i<comensa){
                    suma[i]=this.cfs[i];
                }else {
                    suma[i] = p.cfs[y] + this.cfs[i];
                    y++;
                }
            }
            resultat = new Polynomial(suma);
            return resultat;
        }else {
            suma=new float[p.cfs.length];
            for (int i = 0,y=0; i <p.cfs.length ; i++) {
                int comensa=p.cfs.length-this.cfs.length;
                if (i<comensa){
                    suma[i]=p.cfs[i];
                }else {
                    suma[i] = this.cfs[y] + p.cfs[i];
                    y++;
                }
            }
            resultat = new Polynomial(suma);
            return resultat;
        }
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        Polynomial result ;
        float[] coeficien;
        int[] expo;
        //calculam la llargada dels arrays
        coeficien = new float[this.coefi.length*p2.coefi.length];
        expo = new int[this.coefi.length*p2.coefi.length];
        for (int i = 0, d=0; i < this.coefi.length; i++) {
            for (int j = 0; j < p2.coefi.length; j++) {
                coeficien[d]=this.coefi[i]*p2.coefi[j];
                expo[d]=this.exp[i]+p2.exp[j];
                d++;
            }
        }
        float[] cfso=ordena(coeficien,expo);
        result= new Polynomial(cfso);
//        System.out.println(result.toString());
        return result;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {
        Polynomial[] result = new Polynomial[2];
        Polynomial residu;
        Polynomial resultat=new Polynomial();
        Polynomial cocient;
        float[]division = new float[this.cfs.length-1];
        int[] divi= new int[this.cfs.length-1];
        int[] dviexp = new int[this.cfs.length-1];
        int expon=this.exp[0];

        for (int i = 0; i <this.cfs.length-2 ; i++) {
            if(i==0){
                resultat= new Polynomial(this.cfs);
            }
//            if (i ==this.cfs.length-2){
//                division[this.cfs.length-2]=resultat.coefi[0]/p2.coefi[0];}
//            else {
                System.out.println(resultat.toString());
                division[i] = resultat.cfs[0] / p2.coefi[0];
//            }
            divi[i] = (int)division[i];
            int expo= expon - p2.exp[0];
            expon = expo;
            dviexp[i]=  expo;
            if (dviexp[i]==1){cocient=new Polynomial(divi[i]+"x");}
            else { cocient = new Polynomial(divi[i]+"x^"+dviexp[i]);}
            cocient= cocient.mult(p2);
            cocient= cocient.canviasigna();
            cocient = resultat.add(cocient);

            System.out.println(Arrays.toString(dviexp));
            System.out.println(Arrays.toString(divi));
            if (i!=this.cfs.length-1){
                resultat = new Polynomial(cocient.toString());
            }

        }
//        System.out.println(resultat.toString());
        if(resultat.exp[0]>=p2.exp[0]){
            division[this.cfs.length-2]=resultat.coefi[0]/p2.coefi[0];
            int divisi=(int)division[this.cfs.length-2];
            System.out.println(divisi);
            residu = new Polynomial(String.valueOf(divisi));
            residu = residu.mult(p2);
            System.out.println(residu);
            residu = residu.canviasigna();
            System.out.println(residu);
            residu = residu.add(resultat);
            System.out.println(residu);
            float[] cfso = residu.eliminazero(residu.cfs);
            result[1]=new Polynomial(cfso);
        }
//        division[this.cfs.length-2]=resultat.coefi[0]/p2.coefi[0];
//        int divisi=(int)division[this.cfs.length-2];
//        System.out.println(divisi);
//        residu = new Polynomial(String.valueOf(divisi));
//        residu = residu.mult(p2);
//        System.out.println(residu);
//        residu = residu.canviasigna();
//        System.out.println(residu);
//        residu = residu.add(resultat);
//        System.out.println(residu);
//        float[] cfso = residu.eliminazero(residu.cfs);
//        System.out.println(Arrays.toString(division));
        result[0]=new Polynomial(division);
//        result[1]=new Polynomial(cfso);
       return result;
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
                if(ccc[i]<0&&i==p&&exp>1&&ccc[i]!=-1){sb.append(ccc[i]+"x^"+exp);exp--;continue;}
                if(ccc[i]<0&&i==p&&exp==1&&ccc[i]!=-1){sb.append(ccc[i]+"x");exp--; continue;}
                if(ccc[i]>0&&i!=p){sb.append(" + ");}else if(ccc[i]<0&&i!=p) {ccc[i]=n*(-1);sb.append(" - "); }
                if(ccc[i]<0&& i != 0){ccc[i]=n*-1;}
                if(ccc[i]==0){exp--; continue;}
                if(ccc[i]==1&&exp==1){sb.append("x"); continue;}
                if(exp==1){sb.append(ccc[i]+"x"); continue;}
                if(ccc[i]==1&&i==0){sb.append("x^" + exp); exp--; continue;}
                if(ccc[i]==-1&&i==0){sb.append("-x^" + exp); exp--; continue;}
                if(ccc[i]==1){sb.append("x^"+ exp); exp--; continue;}
                sb.append(ccc[i] + "x^" + exp);
                exp--;
                continue;
            }
            if(ccc[i]>0){sb.append(" + ");}else if(ccc[i]<0 ) {ccc[i]=n*(-1);sb.append(" - ");}

            if(sb.toString().contains("x")&&ccc[i]==0){
                continue;
            }
            if(i==this.cfs.length-1){
                sb.append(ccc[i]);
            }
        }
        return sb.toString();
    }
    private float[] ordena(float[] cof,int[] ex){
        int x=0;
        for (int i = 0; i <ex.length ; i++) {
            if(ex[i]>x){x=ex[i];}
        }
        //colocam els verlors per els seus exponents
        float[] cfso=new float[x+1];
        for (int i = 0; i <cof.length; i++) {
            //contemp que si els exponens son repetits mels sumi
            if(cfso[ex[i]]==0){
                cfso[ex[i]] = cof[i];
            }else {
                cfso[ex[i]] = cof[i] + cfso[ex[i]];
            }
        }
       float[] cfst=new float[x+1];
        //Giram l'array per poderla pasar al toString
        for (int i = x, d=0; i >=0 ; i--, d++) {
            cfst[d]=cfso[i];
        }
        return cfst;
    }
    private Polynomial canviasigna(){
        for (int i = 0; i <this.cfs.length ; i++) {
              this.cfs[i]=this.cfs[i]*-1;
        }
        Polynomial result = new Polynomial(this.cfs);
        return result;
    }
    private float[] eliminazero(float[] cfsi){
        int cont =0;
        float[] cfso;
        if(cfsi[cfsi.length-1]!=0) {
            for (int i = 0; i < cfsi.length; i++) {
                if (cfsi[i] == 0) {
                    cont++;
                    continue;
                }

            }
             cfso = new float[cfsi.length - cont];
            for (int i = 0, d = cont; i < cfsi.length - cont; i++, d++) {
                cfso[i] = cfsi[d];
            }
            return cfso;
        }else {
            return cfsi;
        }
    }
}

