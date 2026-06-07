package model;

public class Country {
    private int id;
    private String nome;
    private String nomeOficial;
    private String capital;
    private String regiao;
    private String subRegiao;
    private long populacao;
    private double area;
    private String bandeiraUrl;

    public Country() {
    }

    public Country(String nome, String nomeOficial, String capital, String regiao, 
                   String subRegiao, long populacao, double area, String bandeiraUrl) {
        this.nome = nome;
        this.nomeOficial = nomeOficial;
        this.capital = capital;
        this.regiao = regiao;
        this.subRegiao = subRegiao;
        this.populacao = populacao;
        this.area = area;
        this.bandeiraUrl = bandeiraUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeOficial() {
        return nomeOficial;
    }

    public void setNomeOficial(String nomeOficial) {
        this.nomeOficial = nomeOficial;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getSubRegiao() {
        return subRegiao;
    }

    public void setSubRegiao(String subRegiao) {
        this.subRegiao = subRegiao;
    }

    public long getPopulacao() {
        return populacao;
    }

    public void setPopulacao(long populacao) {
        this.populacao = populacao;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getBandeiraUrl() {
        return bandeiraUrl;
    }

    public void setBandeiraUrl(String bandeiraUrl) {
        this.bandeiraUrl = bandeiraUrl;
    }

    @Override
    public String toString() {
        return String.format(
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "ID: %d\n" +
            "Nome: %s\n" +
            "Nome Oficial: %s\n" +
            "Capital: %s\n" +
            "Região: %s\n" +
            "Sub-região: %s\n" +
            "População: %,d habitantes\n" +
            "Área: %,.2f km²\n" +
            "Bandeira: %s\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
            id, nome, nomeOficial, capital, regiao, subRegiao, 
            populacao, area, bandeiraUrl
        );
    }

    public String toStringResumido() {
        return String.format("ID: %d | %s - %s | Pop: %,d", 
            id, nome, capital, populacao);
    }
}
