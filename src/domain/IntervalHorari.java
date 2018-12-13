package domain;

public class IntervalHorari {

    private DiaSetmana d;
    private int horaIni;
    private int horaFi;

    public IntervalHorari (DiaSetmana d, int horaIni, int horaFi)
    {
        this.d = d;
        this.horaIni = horaIni;
        this.horaFi = horaFi;
    }

    public DiaSetmana getDia ()
    {
        return d;
    }

    public int getHoraIni ()
    {
        return horaIni;
    }

    public int getHoraFi ()
    {
        return horaFi;
    }

}
