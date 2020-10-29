package src.com.project;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import src.com.project.state.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Experimentos {

    private static DecimalFormat df = setupDF();

    private static DecimalFormat setupDF() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df;
    }
    private static ArrayList<Integer> generarSeeds(){
        System.out.println("Muestras:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int muestras = 0;
        try { muestras = Integer.parseInt(reader.readLine()); }
        catch (IOException e) { e.printStackTrace();}

        ArrayList<Integer> seeds = new ArrayList<Integer>(muestras);
        Random random = new Random();
        for(int i=0;i<muestras;++i) seeds.add(random.nextInt(1000000));
        return seeds;
    }

    public static void primero() throws Exception {
        ArrayList<Integer> seeds = generarSeeds();
        System.out.println("seed"+(char)9+"Operador Move"+(char)9+"Operador Swap"+(char)9+"Operador Both");
        for(int seed:seeds) {
            Paquetes paquetes = new Paquetes(100,seed);
            Transporte transporte = new Transporte(paquetes,1.2,seed);
            int operadores[] = {0x01, 0x02, 0x03};
            System.out.print(seed+" ");
            for(int operador:operadores) {
                AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, operador);
                AzamonBoard aBoard = new AzamonBoard(aInfo);
                aBoard.generateInicialState(GenerateEnum.BASIC);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem,search);
                System.out.print((char)9+df.format(aBoard.getCost(null))+" ");
            }
            System.out.println();
        }
    }

    public static void segundo() throws Exception {
        ArrayList<Integer> seeds = generarSeeds();
        System.out.println("seed"+(char)9+"Operador Move"+(char)9+"Operador Swap");
        for(int seed:seeds) {
            Paquetes paquetes = new Paquetes(100,seed);
            Transporte transporte = new Transporte(paquetes,1.2,seed);
            GenerateEnum generaciones[] = {GenerateEnum.BASIC,GenerateEnum.PRIORITY_ORDERED};
            System.out.print(seed+" ");
            for(GenerateEnum generacion:generaciones) {
                AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, 0x03);
                AzamonBoard aBoard = new AzamonBoard(aInfo);
                aBoard.generateInicialState(generacion);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem,search);
                System.out.print((char)9+df.format(aBoard.getCost(null))+" ");
            }
            System.out.println();
        }
    }

    private static void terceroPasos(Search search, AzamonInfo aInfo) {
        List list = search.getPathStates();
        AzamonBoardAnneling aBoardTest = new AzamonBoardAnneling(aInfo);
        aBoardTest.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
        System.out.println("Pasos" + (char) 9 + "Coste");
        System.out.println(0+" " + (char) 9 + df.format(aBoardTest.getCost(null)));
        int paso=1;
        for(Object action: list) {
            if (action!=null) ((AzamonState)action).updateBoard(aBoardTest);
            System.out.println(paso+" "+ (char) 9 + df.format(aBoardTest.getCost(null)));
            ++paso;
        }
        System.out.println();
    }

    public static void tercero() throws Exception {
        System.out.println("Grafica [step|stiter|k|lamb|lamb2]:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String option = reader.readLine();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        int seed = (new Random()).nextInt(1_000_000);
        int stepList[] = {1000, 10_000, 100_000, 1_000_000, 10_000_000};
        int stiterList[] = {10, 20, 50, 100, 200};
        int kList[] = {1, 5, 25, 125, 600};
        double lambList[] = {0.1, 0.01, 0.001, 0.0001, 0.000_01};
        Paquetes paquetes = new Paquetes(100, seed);
        Transporte transporte = new Transporte(paquetes, 1.2, seed);
        AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, 0x03);
        //steps
        if (option.equals("step")) {
            for (int step : stepList) {
                System.out.println("Iteraciones: " + step + ", Stiter: " + step / stiterList[1] + ", K: " + kList[1] + ", Lamb: " + lambList[1]);
                AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new SimulatedAnnealingSearch(step, stiterList[1], kList[1], lambList[1]);
                SearchAgent agent = new SearchAgent(problem, search);

                terceroPasos(search, aInfo);
            }
        }
        //stiter
        else if (option.equals("stiter")) {
            for (int stiter : stiterList) {
                System.out.println("Iteraciones: " + stepList[1] + ", Stiter: " + stepList[1] / stiter + ", K: " + kList[1] + ", Lamb: " + lambList[1]);
                AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new SimulatedAnnealingSearch(stepList[1], stepList[1] / stiter, kList[1], lambList[1]);
                SearchAgent agent = new SearchAgent(problem, search);

                terceroPasos(search, aInfo);
            }
        }
        //k
        else if (option.equals("k")) {
            for (int k : kList) {
                System.out.println("Iteraciones: " + stepList[1] + ", Stiter: " + stepList[1] / stiterList[1] + ", K: " + k + ", Lamb: " + lambList[1]);
                AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new SimulatedAnnealingSearch(stepList[1], stepList[1] / stiterList[1], k, lambList[1]);
                SearchAgent agent = new SearchAgent(problem, search);

                terceroPasos(search, aInfo);
            }
        }
        //lamb
        else if (option.equals("lamb")) {
            for (double lamb : lambList) {
                System.out.println("Iteraciones: " + stepList[1] + ", Stiter: " + stepList[1] / stiterList[1] + ", K: " + kList[1] + ", Lamb: " + lamb);
                AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new SimulatedAnnealingSearch(stepList[1], stepList[1] / stiterList[1], kList[1], lamb);
                SearchAgent agent = new SearchAgent(problem, search);

                terceroPasos(search, aInfo);
            }
        }
        else if (option.equals("lamb2")) {
            for (double lamb : lambList) {
                System.out.println("Iteraciones: " + stepList[2] + ", Stiter: " + stepList[2] / stiterList[1] + ", K: " + kList[1] + ", Lamb: " + lamb);
                AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new SimulatedAnnealingSearch(stepList[2], stepList[2] / stiterList[1], kList[1], lamb);
                SearchAgent agent = new SearchAgent(problem, search);

                terceroPasos(search, aInfo);
            }
        }
    }

    public static void cuarto_quinto() throws Exception {
        System.out.println("Incrementar [paquetes|proporcion]:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String option=reader.readLine();
        if (option.equals("paquetes")) {
            int seed = (new Random()).nextInt(1000000);
            System.out.println("Paquetes" + (char) 9 + "Tiempo (ms)");
            for(int npaquetes=100;npaquetes<=10_100;npaquetes+=50) {
                Paquetes paquetes = new Paquetes(npaquetes, seed);
                Transporte transporte = new Transporte(paquetes, 1.2, seed);
                AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, 0x03);
                AzamonBoard aBoard = new AzamonBoard(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new HillClimbingSearch();

                Date d1,d2; Calendar c1,c2;
                d1=new Date();
                SearchAgent agent = new SearchAgent(problem,search);
                d2=new Date();
                c1=Calendar.getInstance(); c2=Calendar.getInstance();
                c1.setTime(d1); c2.setTime(d2);
                System.out.println(npaquetes+" " + (char) 9 + (c2.getTimeInMillis()-c1.getTimeInMillis()));
            }
        } else if (option.equals("proporcion")) {
            int seed = (new Random()).nextInt(1000000);
            System.out.println("Proporcion" + (char) 9 + "Tiempo (ms)"+ (char) 9 + "Transporte"+ (char) 9 + "Almacen"+ (char) 9 + "Total");
            Paquetes paquetes = new Paquetes(100, seed);
            for(double proporcion=1.2;proporcion<=21.2;proporcion+=0.2D) {
                Transporte transporte = new Transporte(paquetes, proporcion, seed);
                AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, 0x03);
                AzamonBoard aBoard = new AzamonBoard(aInfo);
                aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
                Problem problem = new Problem(null, aBoard, null, aBoard);
                Search search = new HillClimbingSearch();

                Date d1,d2; Calendar c1,c2;
                d1=new Date();
                SearchAgent agent = new SearchAgent(problem,search);
                d2=new Date();
                c1=Calendar.getInstance(); c2=Calendar.getInstance();
                c1.setTime(d1); c2.setTime(d2);
                double transport = aBoard.getTransportCost(null);
                double almacen = aBoard.getAlmacenCost(null);
                System.out.println(df.format(proporcion)+" " + (char) 9 + (c2.getTimeInMillis()-c1.getTimeInMillis())
                        +" " + (char) 9 + df.format(transport)+" " + (char) 9 + df.format(almacen)+" " + (char) 9 + df.format(transport+almacen));
            }
        }
    }

    public static void sexto() throws Exception {
        int seed = (new Random()).nextInt(1000000);
        Paquetes paquetes = new Paquetes(100, seed);
        Transporte transporte = new Transporte(paquetes, 1.2, seed);
        System.out.println("Ponderacion" + (char) 9 + "Tiempo (ms)" + (char) 9 + "Transporte" + (char) 9 + "Almacen" + (char) 9 + "Total");
        for (double ponderacion = 0.05D; ponderacion < 5.05D; ponderacion += 0.05D) {
            AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.FELICIDAD, ponderacion, 0x03);
            AzamonBoard aBoard = new AzamonBoard(aInfo);
            aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
            Problem problem = new Problem(null, aBoard, null, aBoard);
            Search search = new HillClimbingSearch();

            Date d1, d2;
            Calendar c1, c2;
            d1 = new Date();
            SearchAgent agent = new SearchAgent(problem, search);
            d2 = new Date();
            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            double transport = aBoard.getTransportCost(null);
            double almacen = aBoard.getAlmacenCost(null);
            System.out.println(df.format(ponderacion) + " " + (char) 9 + (c2.getTimeInMillis() - c1.getTimeInMillis())
                    + " " + (char) 9 + df.format(transport) + " " + (char) 9 + df.format(almacen) + " " + (char) 9 + df.format(transport + almacen));
        }
    }

    public static void septimo() throws Exception {
        int seed = (new Random()).nextInt(1000000);
        Paquetes paquetes = new Paquetes(100, seed);
        Transporte transporte = new Transporte(paquetes, 1.2, seed);
        System.out.println("Ponderacion" + (char) 9 + "Tiempo (ms)" + (char) 9 + "Transporte" + (char) 9 + "Almacen" + (char) 9 + "Total");
        for (double ponderacion = 0.05D; ponderacion < 5.05D; ponderacion += 0.05D) {
            AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.FELICIDAD, ponderacion, 0x03);
            AzamonBoard aBoard = new AzamonBoard(aInfo);
            aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
            Problem problem = new Problem(null, aBoard, null, aBoard);
            Search search = new SimulatedAnnealingSearch(200_000,500 ,300 ,0.0003D ); //sin acabar

            Date d1, d2;
            Calendar c1, c2;
            d1 = new Date();
            SearchAgent agent = new SearchAgent(problem, search);
            d2 = new Date();
            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            AzamonState aState= (AzamonState) search.getGoalState();
            double transport = aBoard.getTransportCost(aState);
            double almacen = aBoard.getAlmacenCost(aState);
            System.out.println(df.format(ponderacion) + " " + (char) 9 + (c2.getTimeInMillis() - c1.getTimeInMillis())
                    + " " + (char) 9 + df.format(transport) + " " + (char) 9 + df.format(almacen) + " " + (char) 9 + df.format(transport + almacen));
        }
    }

    public static void octavo() throws Exception {
        int seed = (new Random()).nextInt(1000000);
        Paquetes paquetes = new Paquetes(100, seed);
        Transporte transporte = new Transporte(paquetes, 1.2, seed);
        System.out.println("Precio/kg" + (char) 9 + "Tiempo (ms)" + (char) 9 + "Transporte" + (char) 9 + "Almacen" + (char) 9 + "Total");
        for(double precio=0.01D;precio<=1.501D;precio+=0.01D) {
            AzamonInfo aInfo = new AzamonInfo(paquetes, transporte, HeuristicEnum.COSTE, 1.0D, 0x03,precio);
            AzamonBoard aBoard = new AzamonBoard(aInfo);
            aBoard.generateInicialState(GenerateEnum.PRIORITY_ORDERED);
            Problem problem = new Problem(null, aBoard, null, aBoard);
            Search search = new HillClimbingSearch();

            Date d1, d2;
            Calendar c1, c2;
            d1 = new Date();
            SearchAgent agent = new SearchAgent(problem, search);
            d2 = new Date();
            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            double transport = aBoard.getTransportCost(null);
            double almacen = aBoard.getAlmacenCost(null);
            System.out.println(df.format(precio) + " " + (char) 9 + (c2.getTimeInMillis() - c1.getTimeInMillis())
                    + " " + (char) 9 + df.format(transport) + " " + (char) 9 + df.format(almacen) + " " + (char) 9 + df.format(transport + almacen));
        }
    }

    public static void noveno() {

    }
}
