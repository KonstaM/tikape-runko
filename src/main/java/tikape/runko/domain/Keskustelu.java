package tikape.runko.domain;

public class Keskustelu {
    private Integer id;
    private String otsikko;
    private Integer alue;
    
    public Keskustelu(Integer id, String otsikko, Integer alue) {
        this.id = id;
        this.otsikko = otsikko;
        this.alue = alue;
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

    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public void setAlue(Integer alue) {
        this.alue = alue;
    }
}
