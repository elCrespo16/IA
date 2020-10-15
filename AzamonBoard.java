package IA.Azamon;

import java.util.ArrayList;

public class AzamonBoard {
    private IA.Azamon.AzamonInfo aInfo;
    private ArrayList<Integer> assignedOfer;
    private ArrayList<Double> actualOferWeight;
    
    
    public AzamonBoard(IA.Azamon.AzamonInfo aInfo) {
        this.aInfo = aInfo;
        assignedOfer = new ArrayList<Integer>(aInfo.getPaquetes().size());
        actualOferWeight = new ArrayList<Double>(aInfo.getTransporte().size());
        fillOfDoubleZero(actualOferWeight);
    }

    private void fillOfDoubleZero(ArrayList list) {
        for(int i=0;i<aInfo.getTransporte().size();++i) {
            list.add(0.0D);
        }
    }

    private boolean isPrioritySatisfable(Oferta oferta, Paquete paquete) {
        int dias = oferta.getDias();
        int prioridad = paquete.getPrioridad();
        if (prioridad==0 && dias==1) return true;
        if (prioridad==1 && (dias==2 || dias==3)) return true;
        if (prioridad==2 && (dias>3)) return true;
        return false;
    }

    public void generateInicialState() {
        for (Paquete paquete : aInfo.getPaquetes()) {
            int oferPoint = 0;
            for(Oferta oferta : aInfo.getTransporte()) {
                double maxWeight = oferta.getPesomax();
                double sumWeight = actualOferWeight.get(oferPoint) + paquete.getPeso();
                boolean satisfablePriority = isPrioritySatisfable(oferta, paquete);
                if (sumWeight <= maxWeight && satisfablePriority) {
                    actualOferWeight.set(oferPoint, sumWeight);
                    assignedOfer.add(oferPoint);
                    break;
                }
                ++oferPoint;
            }
        }
    }


}