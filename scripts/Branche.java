package scripts;

public class Branche {
    Noeud noeud_depart;
    Noeud noeud_arrive;
    String valeur_branche;

    public Branche(Noeud noeud_depart, String valeur_branche) {
        this.noeud_depart = noeud_depart;
        this.valeur_branche = valeur_branche;
    }

    public void setNoeud_arrive(Noeud noeud_arrive) {
        this.noeud_arrive = noeud_arrive;
    }

    public Noeud getNoeud_depart() {
        return noeud_depart;
    }

    public Noeud getNoeud_arrive() {
        return noeud_arrive;
    }

    public String getValeur_branche() {
        return valeur_branche;
    }

    @Override
    public String toString() {
        return "Branche [noeud_depart=" + noeud_depart + ", noeud_arrive=" + noeud_arrive + ", valeur_branche="
                + valeur_branche + "]";
    }

}
