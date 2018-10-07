package domain;

import static domain.Tipus_Aula.LAB;

public class Aula_Laboratori extends Aula{

    private Tipus_Lab tipus_lab;

    public Aula_Laboratori(String id, int capacitat, Tipus_Lab tipus) {
        super(id, capacitat, LAB);
        this.tipus_lab = tipus;
    }

    public Tipus_Lab getTipus_lab() {
        return tipus_lab;
    }

    public void setTipus_lab(Tipus_Lab tipus_lab) {
        this.tipus_lab = tipus_lab;
    }

    @Override
    public void setTipus(Tipus_Aula tipus) {
        System.out.println("No pots canviar el tipus d'un Aula de Laboratori");
    }
}
