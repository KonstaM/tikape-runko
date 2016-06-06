package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {
    private Integer id;
    private String sisalto;
    private String lahettaja;
    private Timestamp lahetysAika;
    private Integer keskustelu;
    
    public Viesti(Integer id, String sisalto, String lahettaja, Timestamp lahetysAika, Integer keskustelu) {
        this.sisalto = sisalto;
        this.lahettaja = lahettaja;
        this.keskustelu = keskustelu;
        this.lahetysAika = lahetysAika;
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getSisalto() {
        return sisalto;
    }
    
    public String getLahettaja() {
        return lahettaja;
    }
    
    public Timestamp getLahetysAika() {
        return lahetysAika;
    }
    
    public Integer getKeskustelu() {
        return keskustelu;
    }
}