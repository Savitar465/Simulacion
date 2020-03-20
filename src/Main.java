import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner m=new Scanner(System.in);

        System.out.println("Cantidad de filas");
        int f=17;

        System.out.println("Cantidad de variables");
        int c=3;




        System.out.println("Ingrese y");
        //double[][] y=new double[f][1];
        double[][] y = {{72},{63},{65},{68},{78},{64},{56},{59},{60},{85},{67},{61},{65},{79},{61},{52},{65}};


		/*for(int i=0;i<f;i++)
		{
			for(int j=0;j<1;j++)
			{
				y[i][j]=m.nextInt();
			}
		}*/



        //System.out.println("Ingrese x");


        //double[][] x=new double[f][c+1];

        double[][] x= {{1,10,3,1},{1,5,1.5,1},{1,10,1,1},{1,20,2,1},{1,15,4,1},{1,10,2,1},{1,6,0,0},{1,25,1,1},{1,30,2,1},{1,10,1,1},{1,10,1,1},{1,20,1,1},{1,20,0.5,1},{1,20,2,1},{1,30,0.5,1},{1,20,1,0},{1,10,1,1}};

		/*for(int i=0;i<f;i++)
		{
			for(int j=0;j<(c+1);j++)
			{
				x[i][j]=m.nextInt();
			}
		}*/


        System.out.println("------------------------------------");
        System.out.println("La matriz de y");
        mostrar(y);

        System.out.println("------------------------------------");
        System.out.println("La matriz de x");
        mostrar(x);


        System.out.println("------------------------------------");
        System.out.println("(Xt*X):");
        mostrar(primera(x));

        System.out.println("------------------------------------");
        System.out.println("(Xt*Y):");
        mostrar(multiplicación(transpuesta(x),y));


        System.out.println("------------------------------------");
        System.out.println("(Xt*X)(Xt*Y)");
        double [][] w= multiplicación(primera(x),multiplicación(transpuesta(x),y));
        mostrarBn(w);

        mostrarMatriz(mostrarY(x,w));


    }
     public static double [][] mostrarY(double [][] x, double [][]h){
// double[][] x= {{1,10,3,1},{1,5,1.5,1},{1,10,1,1},{1,20,2,1},{1,15,4,1},{1,10,2,1},{1,6,0,0},{1,25,1,1},{1,30,2,1},{1,10,1,1},{1,10,1,1},{1,20,1,1},{1,20,0.5,1},{1,20,2,1},{1,30,0.5,1},{1,20,1,0},{1,10,1,1}};
         double [][]l={};
         for (int j=0;j<=3;j++){
             for (int i=0;i<=18;i++){
                 l[i][0]=x[i][j]+h[0][0]*x[i][1]+h[1][0]*x[i][2]+h[2][0]*x[i][3];
             }
         }


        return l;
    }
    public static void mostrarMatriz(double[][] x)
    {

        for(int i=0;i<=3;i++)
        {
            for(int j=0;j<=18;j++)
            {
                //Mostrar los Bn
                System.out.print(x[i][j]);

            }
            System.out.println();
        }
    }
    public static void mostrarBn(double[][] x)
    {

        for(int i=0;i<x.length;i++)
        {
            for(int j=0;j<x[0].length;j++)
            {
                //Mostrar los Bn
                System.out.printf("B%d = %.4f ",i ,x[i][j]);

            }
            System.out.println();
        }
    }

    public static void mostrar(double[][] x)
    {
        for(int i=0;i<x.length;i++)
        {
            for(int j=0;j<x[0].length;j++)
            {
                System.out.printf("%.4f\t", x[i][j]);
            }
            System.out.println();
        }
    }


    public static double[][] primera(double[][] x)
    {
        double[][] xt=transpuesta(x);

        double[][] prod=multiplicación(xt,x);

        double[][] inve=inversa(prod);

        return inve;
    }

    public static double[][] transpuesta(double[][] x)
    {
        //Resultado de la matriz transpuesta
        double[][] b=new double[x[0].length][x.length];


        for (int i=0; i < x.length; i++) {
            for (int j=0; j < x[i].length;j++) {
                b[j][i] = x[i][j];
            }
        }
        return b;
    }

    public static double[][] multiplicación(double [][] xt, double [][] x){

        //matriz donde se almacenara el resultado
        double [][] c= new double [xt.length][x[0].length];

        for (int i=0; i < c.length; i++) {
            for (int j=0; j < c[i].length; j++) {
                for (int k=0; k<xt[0].length; k++) {
                    c [i][j] += xt[i][k]*x[k][j];
                }
            }
        }

        return c;
    }


    public static double[][] inversa(double a[][])         {
        //Cantidad de filas y columnas: Matriz cuadrada
        int n = a.length;


        double x[][] = new double[n][n];
        double identidad[][] = new double[n][n];

        int index[] = new int[n];

        //Matriz identidad
        for (int i=0; i<n; ++i)
        {
            identidad[i][i] = 1;
        }

        // Transforma la matriz en un triángulo superior.

        gaussian(a, index);

        // Actualice la matriz b [i] [j] con las relaciones almacenadas
        for (int i=0; i<n-1; ++i)
        {
            for (int j=i+1; j<n; ++j)
            {
                for (int k=0; k<n; ++k)
                {
                    identidad[index[j]][k]-= a[index[j]][i]*identidad[index[i]][k];
                }
            }
        }


        // Realizar sustituciones hacia atrás.
        for (int i=0; i<n; ++i)             {
            x[n-1][i] = identidad[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = identidad[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Método para llevar a cabo el gaussiano de giro parcial.
// eliminación. Aquí el índice [] almacena orden pivotante.

    public static void gaussian(double a[][], int index[])  {

        int n = index.length;
        double c[] = new double[n];


        // Inicializar el índice
        for (int i=0; i<n; i++)
        {
            index[i] = i;

        }

        // Encuentra los factores de reescalamiento, uno de cada fila
        for (int i=0; i<n; i++) {
            double c1 = 0;
            for (int j=0; j<n; j++) {

                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) {
                    c1 = c0;
                }
            }
            c[i] = c1;
        }

        // Busca el elemento pivotante de cada columna.
        int k = 0;
        for (int j=0; j<n-1; j++) {

            double pi1 = 0;
            for (int i=j; i<n; i++) {

                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Intercambiar filas según el orden de giro.
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; i++) {

                double pj = a[index[i]][j]/a[index[j]][j];

                // Registrar las relaciones de giro por debajo de la diagonal
                a[index[i]][j] = pj;

                // Modificar otros elementos en consecuencia.
                for (int l=j+1; l<n; l++)
                {
                    a[index[i]][l] -= pj*a[index[j]][l];

                }
            }
        }
    }



    public static void r2error(double a[][], double b[][]) {



    }
}