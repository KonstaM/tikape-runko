package tikape.runko.domain;

import java.util.List;
import java.util.ArrayList;

public class Alue {
    private Integer id;
    private String nimi;
    private List<Keskustelu> keskustelut;
    
    public Alue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        keskustelut = new ArrayList<>();
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public void addKeskustelu(Keskustelu keskustelu) {
        keskustelut.add(keskustelu);
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}
