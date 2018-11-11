package domain;

public enum DiaSetmana {
    DILLUNS, DIMARTS, DIMECRES, DIJOUS, DIVENDRES;

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
