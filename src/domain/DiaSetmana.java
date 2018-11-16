package domain;

/**
 * @author Olek
 */
public enum DiaSetmana {
    DILLUNS, DIMARTS, DIMECRES, DIJOUS, DIVENDRES;

    /**
     * @param s Una string amb el dia de la setmana
     * @return La enumeracio que concorda amb la string pasada.
     */
    public static DiaSetmana string_To_DiaSetmana(String s){
        s = s.toUpperCase();
        if(s.equals("DL") || s.equals("DILLUNS")) return DILLUNS;
        if(s.equals("DM") || s.equals("DIMARTS")) return DIMARTS;
        if(s.equals("DC") || s.equals("DIMECRES")) return DIMECRES;
        if(s.equals("DJ") || s.equals("DIJOUS")) return DIJOUS;
        if(s.equals("DV") || s.equals("DIVENDRES")) return DIVENDRES;
        throw new IllegalArgumentException();
    }
}
