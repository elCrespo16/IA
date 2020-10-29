package src.com.project;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import src.com.project.state.HeuristicEnum;

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
        int npaquetes; int seed; double proportion, ponderacion, almacen;
        BufferedReader reader; String seedStr,heuristicoStr; Paquetes paquetes; Transporte transporte;
        HeuristicEnum heuristico = HeuristicEnum.COSTE;
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de la semilla o [random]:"); seedStr = reader.readLine();
        System.out.println("Numero de paquetes:"); npaquetes = Integer.parseInt(reader.readLine());
        System.out.println("Proporcion:"); proportion = Double.parseDouble(reader.readLine());
        boolean consulta=false;
        do {
            System.out.println("Heuristico [coste|felicidad]:");
            heuristicoStr = reader.readLine();
            if (heuristicoStr.equals("coste")) {heuristico = HeuristicEnum.COSTE;consulta=false;}
            else if (heuristicoStr.equals("felicidad")) {heuristico = HeuristicEnum.FELICIDAD;consulta=false;}
            else consulta=true;
        } while (consulta);
        ponderacion=1.0D;
        if (heuristico==HeuristicEnum.FELICIDAD) {
            System.out.println("Ponderación:");
            ponderacion = Double.parseDouble(reader.readLine());
        }
        almacen=0.25D;
        System.out.println("Costo almacen:"); almacen = Double.parseDouble(reader.readLine());
        if(seedStr.equals("random")) seed=(new Random()).nextInt(101);
        else seed = Integer.parseInt(seedStr);

        paquetes = new Paquetes(npaquetes,seed);
        transporte = new Transporte(paquetes,proportion,seed);


        return new AzamonInfo(paquetes,transporte, heuristico,ponderacion, 0x03);
    }

}
