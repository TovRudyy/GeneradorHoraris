package domain;

public class Corequisit extends Restriccio {
    private String assig1, assig2;

    public Corequisit(String id1, String id2) {
        if (id1.equals(id2)) {
            System.out.println("Una assignatura no pot ser corequisit amb ella mateixa");
            throw new IllegalArgumentException("Invalid argument");
        }
        this.assig1 = id1;
        this.assig2 = id2;
    }

    public void checkRestriccio(String my_assig, String my_group) {

    }
}
