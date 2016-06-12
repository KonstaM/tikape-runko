package tikape.runko.domain;

import java.util.List;
import java.util.ArrayList;

public class Keskustelu {
    private Integer id;
    private String otsikko;
    private List<Viesti> viestit;
    private Integer alue;
    
    public Keskustelu(Integer id, String otsikko, Integer alue) {
        this.id = id;
        this.otsikko = otsikko;
        this.alue = alue;
        viestit = new ArrayList<>();
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getOtsikko() {
        return otsikko;
    }
    
    public Integer getAlue() {
        return alue;
    }
    
    public void addViesti(Viesti viesti) {
        viestit.add(viesti);
    }

    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
}