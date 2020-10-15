package IA.Azamon;

public class AzamonBoard {
    private IA.Azamon.AzamonInfo azamonInfo;
    private int[] assignedOffer;
    private double[] actualWeight;

    //CONSTRUCTORES
    public AzamonBoard(IA.Azamon.AzamonInfo aInfo) {
        this.azamonInfo = aInfo;
        assignedOffer = new int[aInfo.getPaquetes().size()];
        actualWeight = new double[aInfo.getTransporte().size()];
        for(double value : actualWeight) value=0.0D;
    }

    public void generateInicialState() {
        int packagePointer = 0;
        for (Paquete paquete : azamonInfo.getPaquetes()) {
            int offerPointer = 0;
            for(Oferta oferta : azamonInfo.getTransporte()) {
                double maxWeight = oferta.getPesomax();
                double sumWeight = actualWeight[offerPointer] + paquete.getPeso();
                boolean satisfablePriority = isPrioritySatisfable(oferta, paquete);
                if (sumWeight <= maxWeight && satisfablePriority) {
                    actualWeight[offerPointer]=sumWeight;
                    assignedOffer[packagePointer]=offerPointer;
                    break;
                }
                ++offerPointer;
            }
            ++packagePointer;
        }
    }
    //CONSULTORAS

    public int getAssignedOffer(int i) { return assignedOffer[i]; }
    public double getActualWeight(int i) { return actualWeight[i]; }

    //MODIFICADORAS

    public boolean setPackageToOffer(int pack, int offer) {
        double packageWeight = azamonInfo.getPaquetes().get(pack).getPeso();
        double offerMaxWeight = azamonInfo.getTransporte().get(offer).getPesomax();

        if (packageWeight+actualWeight[assignedOffer[pack]]>offerMaxWeight) return false;

        actualWeight[assignedOffer[pack]]-=packageWeight;
        actualWeight[offer]+=packageWeight;
        assignedOffer[pack]=offer;
        return true;
    }


    //FUNCIONES PRIVADAS

    private boolean isPrioritySatisfable(Oferta oferta, Paquete paquete) {
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference==0 || difference==1) return true;
        return false;
    }
}