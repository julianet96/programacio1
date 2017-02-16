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
            if (i == 0) {
                resultat = new Polynomial(this.cfs);
            }
            division[i] = resultat.cfs[0] / p2.coefi[0];
            divi[i] = (int) division[i];
            int expo;
            if (expon - p2.exp[0] < 0) {
                expo = 0;
            } else {
                expo = expon - p2.exp[0];
                expon = expo;
            }
            dviexp[i] = expo;
            if (dviexp[i] == 1) {
                cocient = new Polynomial(divi[i] + "x");
            } else {
                cocient = new Polynomial(divi[i] + "x^" + expo);
            }
            cocient = cocient.mult(p2);
            cocient = cocient.canviasigna();
            cocient = resultat.add(cocient);
            if (i != this.cfs.length - 1) {
                resultat = new Polynomial(cocient.toString());
            }
            if(resultat.exp[0]< p2.exp[0]) {
                break;
            }

        }

        if(resultat.exp[0]>=p2.exp[0]){
            division[this.cfs.length-2]=resultat.coefi[0]/p2.coefi[0];
            int divisi=(int)division[this.cfs.length-2];

            residu = new Polynomial(String.valueOf(divisi));
            residu = residu.mult(p2);

            residu = residu.canviasigna();
            residu = residu.add(resultat);
            float[] cfso = residu.eliminazero(residu.cfs);
            result[1]=new Polynomial(cfso);
            Polynomial sol = new Polynomial(division);
            result[0] = new Polynomial(sol.toString());

            return result;
        }else {
            float[]solu;
            solu = ordena(division, dviexp);
            Polynomial sol = new Polynomial(solu);
            result[0] = new Polynomial(sol.toString());
            result[1] = resultat;
            return result;
        }
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        for (int i = 0; i <this.coefi.length ; i++) {
            if(i<this.coefi.length-1){
               if( this.exp[i]<this.exp[i+1]){
                   int t = this.exp[i];
                   float t1=this.coefi[i];
                   this.exp[i]= this.exp[i+1];
                   this.coefi[i]=this.coefi[i+1];
                   this.exp[i+1]=t;
                   this.coefi[i+1]=t1;
               }
            }
        }

        float[] result;
        //Arrels amb Polinomis de grau 1
        if (this.exp[0]==1){
            result= new float[1];
           result[0]= this.coefi[1] / this.coefi[0];
            result[0]=result[0]*-1;
            return result;

         //Arrels amb polinomi de grau 2 pero amb un exponent
        }else if(this.coefi.length==2&&this.exp[0]==2&&this.exp[1]==0) {
            result=new float[2];
            this.coefi[1]=this.coefi[1]*-1;
            result[1]=(float)Math.sqrt(this.coefi[1]/this.coefi[0]);
            result[0]= result[1]*-1;
            return result;
         //Arrels amb polinomis de grau 2 amb mes de un exponent
        }else if(this.coefi.length==3&&this.exp[0]==2&&this.exp[1]==1){
            result = arrel(this.cfs);
            return result;
         //Arrels bicuadratique exponent elevat a 4
        }else if (this.coefi.length==3&&this.exp[0]==4&&this.exp[1]==2){
            float a = this.coefi[0];
            float b = this.coefi[1];
            float c = this.coefi[2];
            float d = (b*b)-(4*a*c);
            if (d<0){
                return null;
            }else if (d==0){
                float result1=(b*-1)/(2*a);
                if (result1 < 0){
                    return null;
                }else {
                    result = new float[2];
                    result[1]=(float)Math.sqrt(result1);
                    result[0]= result[1]*(-1);
                    return result;
                }

            }else {
                result = new float[4];
                double arrel1 = ((b*-1)+Math.sqrt(d))/(2*a);
                double arrel2 = ((b*-1)-Math.sqrt(d))/(2*a);
                double result1 = Math.sqrt(arrel1);
                double result2 = result1*-1;
                double result3 = Math.sqrt(arrel2);
                double result4 = result3*(-1);
                result[3]=(float)result1;
                result[2]=(float)result3;
                result[1]=(float)result4;
                result[0]=(float)result2;
                return result;
            }
          //arrels de polinomis amb un exponent elevat a cualsevol numero
          // pero el seguent no ha de tenir exponent x^3 + 2
        }else if(this.coefi.length==2&& exp[1]==0){
            //si l'exponent es par nomes podrem afer arrels de valors positius
            if(exp[0]%2==0){
                result = new float[2];
                if (coefi[1]*(-1)<0){
                    return null;
                }else {
                    float n = (float)exp[0];
                    result[1]=(float)Math.pow((coefi[1]*(-1)/coefi[0]),1.0/n);
                    result[0]=result[1]*(-1);
                    return result;
                }
            }else { //si l'exponent en que dividim es inpar se pot fer arrels negatives
                result = new float[1];
                float n = (float)exp[0];
                result[0]=(float)Math.pow((coefi[1]/coefi[0]),1.0/n);
                result[0]= result[0]*(-1);
                return result;
            }
        }else{
            float[] cof = rufini(this.cfs);
            result = new float[4];
            result[2]=res;
            if(cof.length==4){
                float[]cof1 = rufini(cof);
             float[] result1= arrel(cof1);
                result[0]=result1[0];
                result[1]=result1[1];
                result[3]=res;
            }else if(cof.length==3){
               result= arrel(cof);
            }

            return result;
        }

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
    private int[] divisors (float n){
        int cont = 0;
        if (n<0){
            n =n*(-1);
        }
        for (int i = 0; i < n ; i++) {
            if(n%i==0){
                cont++;
            }
        }
        int[] div = new int[cont];
        for (int i = 0, y=0; i < n ; i++) {
            if(n%i==0){
                div[y]=i;
                y++;
            }
        }
        return div;
    }
    float res;
    float[] rufini (float[] cfs){

        int[] divi = divisors(cfs[cfs.length-1]);
        float[] cof = new float[cfs.length-1];
        float[] cof2 = new float[cfs.length];
        for (int i = 0; i <divi.length ; i++) {
            for (int j = 0; j <cfs.length-1 ; j++) {
                if(j==0){
                    cof[j]=divi[i]*cfs[j];
                    cof[j]=cfs[j+1]+cof[j];
                    cof2[j]=cfs[j];
                    cof2[j+1]=cof[j];
                }else {
                    cof[j]=divi[i]*cof[j-1];
                    cof[j]=cfs[j+1]+cof[j];
                    cof2[j+1]=cof[j];
                }
            }
            if(cof[cfs.length-2]==0){
                for (int j = 0; j <cof2.length-1 ; j++) {
                    cof[j]=cof2[j];
                    res = divi[i];
                }
                break;
            }
        }
        return cof;
    }
    private float[] arrel(float[] cfs){
        float[]result;
        float a = cfs[0];
        float b = cfs[1];
        float c = cfs[2];
        float d = (b*b)-(4*a*c);
        if (d<0){
            return null;
        }else if (d==0){
            result = new float[1];
            result[0]=(b*-1)/(2*a);

            return result;
        }else {
            result = new float[2];
            double result1 = ((b*-1)+Math.sqrt(d))/(2*a);
            double result2 = ((b*-1)-Math.sqrt(d))/(2*a);
            result[1]=(float)result1;
            result[0]=(float)result2;
            return result;
        }

    }
}

