package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {
    private Integer id;
    private String sisalto;
    private Timestamp lahetysAika;
    private String lahettaja;
    private Integer keskustelu;

    public Viesti(Integer id, String sisalto, Timestamp lahetysAika, String lahettaja, Integer keskustelu) {
        this.sisalto = sisalto;
        this.lahetysAika = lahetysAika;
        this.id = id;
        this.lahettaja = lahettaja;
        this.keskustelu = keskustelu;
    }
    
    public Integer getId() {
        return id;
    }
    
    public Integer getKeskustelu() {
        return keskustelu;
    }
    
    public String getSisalto() {
        return sisalto;
    }
    
    public Timestamp getLahetysAika() {
        return lahetysAika;
    }
    
    public String getLahettaja() {
        return lahettaja;
    }
}