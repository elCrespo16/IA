package src.com.project.state;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import src.com.project.AzamonInfo;

import java.util.ArrayList;
import java.util.List;

public class AzamonBoard implements SuccessorFunction, HeuristicFunction {
    private AzamonInfo azamonInfo;
    private int[] assignedOffer;
    private double[] actualWeight;

    public AzamonBoard(AzamonInfo aInfo) {
        this.azamonInfo = aInfo;
        assignedOffer = new int[aInfo.getPaquetes().size()];
        actualWeight = new double[aInfo.getTransporte().size()];
        for(double value : actualWeight) value=0.0D;
    }

    public AzamonState generateInicialState() {

        assignPackagesToOffers(0);
        assignPackagesToOffers(1);
        assignPackagesToOffers(2);

        return null;
    }

    private void assignPackagesToOffers(int priority) {
        int packagePointer = 0;
        for (Paquete paquete : azamonInfo.getPaquetes()) {
            if (paquete.getPrioridad()==priority) {
                int offerPointer = 0;
                for (Oferta oferta : azamonInfo.getTransporte()) {

                    double maxWeight = oferta.getPesomax();
                    double sumWeight = actualWeight[offerPointer] + paquete.getPeso();

                    boolean satisfablePriority = isPrioritySatisfable(oferta, paquete);
                    if (sumWeight <= maxWeight && satisfablePriority) {
                        actualWeight[offerPointer] = sumWeight;
                        assignedOffer[packagePointer] = offerPointer;
                        break;
                    } ++offerPointer;

                }
            } ++packagePointer;
        }
    }

    @Override
    public List getSuccessors(Object state) {
        updateState((AzamonState) state);
        List<Successor> list= new ArrayList<>();

        //GENERADORES DE OPERACIONES
        addMovableSuccessors(list);
        addSwappableSuccessors(list);
        //...

        return list;
    }



    @Override
    public double getHeuristicValue(Object state) {
        double heuristic=0;
        AzamonState oldState = updateState((AzamonState) state);

        //FUNCION HEURISTICA
        //  <--

        updateState(oldState);
        return heuristic;
    }
    //GETTERS

    int getAssignedOffer(int pack) {return assignedOffer[pack];}

    //FUNCIONES PRIVADAS

    private boolean isPrioritySatisfable(Oferta oferta, Paquete paquete) {
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference>=0 ) return true;
        /*
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference==0 || difference==1) return true;
        */
        return false;
    }

    private AzamonState updateState(AzamonState azamonState) {//REHACER
        if (azamonState==null) return null;
        return azamonState.updateBoard(this); //Devuelve el estado anterior
    }



    private void addMovableSuccessors(List<Successor> list) {
        int packagesSize = azamonInfo.getPaquetes().size();
        int offersSize = azamonInfo.getTransporte().size();

        for(int pack = 0; pack<packagesSize; ++pack) {
            for(int offer = 0; offer<offersSize; ++offer) {
                if (offer != assignedOffer[pack]) {
                    double packageWeight = azamonInfo.getPaquetes().get(pack).getPeso();
                    double offerMaxWeight = azamonInfo.getTransporte().get(offer).getPesomax();
                    boolean satisfablePriority =
                            isPrioritySatisfable(azamonInfo.getTransporte().get(offer), azamonInfo.getPaquetes().get(pack));
                    if (packageWeight+actualWeight[offer]<=offerMaxWeight && satisfablePriority) {
                        AzamonState newBoard = new AzamonMoveState(pack,offer);
                        list.add(new Successor("",newBoard));
                    }
                }
            }
        }
    }

    private void addSwappableSuccessors(List<Successor> list) {

    }
    //FUNCIONES FRIEND
    public void __move__(int pack, int offer) {
        double packageWeight = azamonInfo.getPaquetes().get(pack).getPeso();
        actualWeight[assignedOffer[pack]]-=packageWeight;
        actualWeight[offer]+=packageWeight;
        assignedOffer[pack]=offer;
    }
}
