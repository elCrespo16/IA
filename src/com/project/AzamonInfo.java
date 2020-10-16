package src.com.project;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AzamonInfo {
    private Paquetes paquetes;
    private Transporte transporte;

    public AzamonInfo() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de paquetes:");
        int npaquetes = Integer.parseInt(reader.readLine());
        System.out.println("Numero de semilla o rand:");
        String sd = reader.readLine();
        int seed = 0;
        if(sd.equals("rand")){
            Random myRandom=new Random();
            seed = myRandom.nextInt(101);
        }
        else {
            seed = Integer.parseInt(sd);
        }
        this.paquetes = new Paquetes(npaquetes,seed);
        this.transporte = new Transporte(paquetes,1.2D,seed);
    }

    public Paquetes getPaquetes() {return paquetes;}
    public Transporte getTransporte() {return transporte;}

}
