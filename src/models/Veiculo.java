package models;

public class Veiculo {
    private String placa;
    private String tipo;
    private double pesoBrutoTotal;
    private String proprietario;
    private int ano;
    private String municipio;

    public Veiculo(String placa, String tipo, double pesoBrutoTotal,
                   String proprietario, int ano, String municipio) {
        this.placa = placa;
        this.tipo = tipo;
        this.pesoBrutoTotal = pesoBrutoTotal;
        this.proprietario = proprietario;
        this.ano = ano;
        this.municipio = municipio;
    }

    public String getPlaca() { return placa; }
    public String getTipo() { return tipo; }
    public double getPesoBrutoTotal() { return pesoBrutoTotal; }
    public String getProprietario() { return proprietario; }
    public int getAno() { return ano; }
    public String getMunicipio() { return municipio; }

    @Override
    public String toString() {
        return String.format("Placa: %s | Tipo: %s | PBT: %.0f kg | Proprietário: %s | Ano: %d | Município: %s",
                placa, tipo, pesoBrutoTotal, proprietario, ano, municipio);
    }
}