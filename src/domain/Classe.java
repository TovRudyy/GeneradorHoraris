package domain;

public class Classe {

    private String id_assig;
    private String id_grup;
    private DiaSetmana dia;
    private int hora_inici;
    private int hora_fi;
    private String id_aula;

    public Classe(String id_assig, String id_grup, DiaSetmana dia, int inici, int fi, String id_aula) {
        this.id_assig = id_assig;
        this.id_grup = id_grup;
        this.dia = dia;
        if (inici < fi) {
            this.hora_inici = inici;
            this.hora_fi = fi;
        }
        else {
            System.out.println(id_assig +" "+ id_grup);
            System.out.println("Error: hora_inici és major que hora_fi!");
        }
        this.id_aula = id_aula;
    }


    public DiaSetmana getDia() {
        return this.dia;
    }


    public int getHoraInici() {
        return this.hora_inici;
    }


    public int getHoraFi() {
        return this.hora_fi;
    }


    public String getId_assig () {
        return this.id_assig;
    }


    public String getId_grup () {
        return this.id_grup;
    }


    public String getIdAula() {
        return id_aula;
    }


    public int getDurada() {
        if (hora_fi != -1) {
            return hora_fi - hora_inici;
        }
        else {
            System.out.println("Error: hora_fi no està inicialitzat!");
            return 0;
        }
    }


 // NO CALDRIA PERQUE JA TENIM EL DRIVER QUE LES TESTEJA UNA A UNA
    public void showClasse () {
        System.out.println(id_assig + " " + id_grup + " "+ id_aula + " " + dia+ " " + hora_inici + " " + hora_fi);
    }


}
