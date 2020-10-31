package src.com.project.classes;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import src.com.project.classes.enums.HeuristicEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AzamonInfo {
    public final Paquetes paquetes;
    public final Transporte transporte;
    public final HeuristicEnum heuristico;
    public final double ponderacion;
    public final int operadoresFlag;
    public final double almacen;

    public AzamonInfo(Paquetes paquetes, Transporte transporte, HeuristicEnum heuristico,double ponderacion,int operadoresFlag, double almacen) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = heuristico;
        this.operadoresFlag=operadoresFlag;
        this.ponderacion=ponderacion;
        this.almacen=almacen;
    }

    public AzamonInfo(Paquetes paquetes, Transporte transporte, HeuristicEnum heuristico,double ponderacion,int operadoresFlag) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = heuristico;
        this.operadoresFlag=operadoresFlag;
        this.ponderacion=ponderacion;
        this.almacen=0.25D;
    }

    public AzamonInfo(Paquetes paquetes, Transporte transporte, HeuristicEnum heuristico,int operadoresFlag) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = heuristico;
        this.operadoresFlag=operadoresFlag;
        this.ponderacion=1.0D;
        this.almacen=0.25D;
    }

    public AzamonInfo(Paquetes paquetes, Transporte transporte, HeuristicEnum heuristico) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = heuristico;
        this.operadoresFlag=0x03;
        this.ponderacion=1.0D;
        this.almacen=0.25D;
    }

    public AzamonInfo(Paquetes paquetes, Transporte transporte) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = HeuristicEnum.COSTE;
        this.operadoresFlag=0x03;
        this.ponderacion=1.0D;
        this.almacen=0.25D;
    }

    public static AzamonInfo reader() throws IOException {
        //variables
        int npaquetes; int seed, operadores; double proportion, ponderacion, almacen;
        BufferedReader reader; String seedStr,heuristicoStr; Paquetes paquetes; Transporte transporte;
        HeuristicEnum heuristico = HeuristicEnum.COSTE;
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Numero de la semilla o [random]: "); seedStr = reader.readLine();
        if(seedStr.equals("random")) seed=(new Random()).nextInt(1_000_000);
        else seed = Integer.parseInt(seedStr);

        System.out.print("Numero de paquetes: "); npaquetes = Integer.parseInt(reader.readLine());
        System.out.print("Proporcion: "); proportion = Double.parseDouble(reader.readLine());

        System.out.print("Heuristico [coste|felicidad]: "); heuristicoStr = reader.readLine();
        if (heuristicoStr.equals("coste")) heuristico = HeuristicEnum.COSTE;
        else if (heuristicoStr.equals("felicidad")) heuristico = HeuristicEnum.FELICIDAD;

        ponderacion=1.0D;
        if (heuristico==HeuristicEnum.FELICIDAD) {
            System.out.println("Ponderación: ");
            ponderacion = Double.parseDouble(reader.readLine());
        }
        almacen=0.25D;
        System.out.println("Costo almacen: "); almacen = Double.parseDouble(reader.readLine());
        System.out.println("Operadores: "); operadores = Integer.parseInt(reader.readLine());

        paquetes = new Paquetes(npaquetes,seed);
        transporte = new Transporte(paquetes,proportion,seed);

        return new AzamonInfo(paquetes,transporte, heuristico,ponderacion, operadores);
    }

}
