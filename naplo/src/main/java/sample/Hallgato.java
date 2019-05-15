package sample;

import java.util.List;
import java.util.Objects;

public class Hallgato {
    private String nev;
    private String hAZ;
    private List<Integer> d;
    private List<Integer> tz;
    private int k;

    public Hallgato() {
    }

    public Hallgato(String nev) {
        this.nev = nev;
    }

    public Hallgato(String nev, String hAZ, List<Integer> d, List<Integer> tz, int k) {
        this.nev = nev;
        this.hAZ = hAZ;
        this.d = d;
        this.tz = tz;
        this.k = k;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String gethAZ() {
        return hAZ;
    }

    public void sethAZ(String hAZ) {
        this.hAZ = hAZ;
    }

    public List<Integer> getD() {
        return d;
    }

    public void setD(List<Integer> d) {
        this.d = d;
    }

    public List<Integer> getTz() {
        return tz;
    }

    public void setTz(List<Integer> tz) {
        this.tz = tz;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "Hallgato{" +
                "nev='" + nev + '\'' +
                ", hAZ='" + hAZ + '\'' +
                ", d=" + d +
                ", tz=" + tz +
                ", k=" + k +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hallgato hallgato = (Hallgato) o;
        return k == hallgato.k &&
                Objects.equals(nev, hallgato.nev) &&
                Objects.equals(hAZ, hallgato.hAZ) &&
                Objects.equals(d, hallgato.d) &&
                Objects.equals(tz, hallgato.tz);
    }
}
