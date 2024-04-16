package scripts;

import java.util.ArrayList;

public class Noeud {
    private String value;
    private ArrayList<Branche> branches;

    public Noeud(String value) {
        this.value = value;
        this.branches = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public void addBranche(Branche branche) {
        branches.add(branche);
    }

    public ArrayList<Branche> getBranches() {
        return branches;
    }

    @Override
    public String toString() {
        return "Noeud [value=" + value + "]";
    }
}
