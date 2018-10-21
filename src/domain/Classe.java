package domain;

public class Classe {

    private DiaSetmana dia;
    private int hora_inici;
    private int hora_fi;
    private Aula aula;

    public Classe(DiaSetmana dia, int inici, Aula aula) {
        this.dia = dia;
        this.hora_inici = inici;
        this.aula = aula;
        this.hora_fi = -1;
    }

    public Classe(DiaSetmana dia, int inici, int fi, Aula aula) {
        this.dia = dia;
        if (hora_inici < hora_fi) {
            this.hora_inici = inici;
            this.hora_fi = fi;
        }
        else {
            System.out.println("Error: hora_inici és major que hora_fi!");
        }
        this.aula = aula;
    }

    DiaSetmana getDia() {
        return this.dia;
    }

    public int getHoraInici() {
        return this.hora_inici;
    }

    public int getHoraFi() {
        return this.hora_fi;
    }

    public Aula getAula() {
        return this.aula;
    }

    public void setHoraFi(int hora) {
        if (hora_inici < hora) {
            this.hora_fi = hora;
        }
        else {
            System.out.println("Error: hora_fi és menor que hora_inici!");
        }
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

}
