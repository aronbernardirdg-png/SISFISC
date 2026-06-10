package models;

public class ResultadoConformidade {
    private String placa;
    private String tipoVeiculo;
    private double pesoDeclarado;
    private double pesoMaximo;
    private boolean conforme;
    private String motivo;
    private String carga;
    private String nfe;

    public ResultadoConformidade(String placa, String tipoVeiculo, double pesoDeclarado,
                                 double pesoMaximo, boolean conforme, String motivo,
                                 String carga, String nfe) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.pesoDeclarado = pesoDeclarado;
        this.pesoMaximo = pesoMaximo;
        this.conforme = conforme;
        this.motivo = motivo;
        this.carga = carga;
        this.nfe = nfe;
    }

    public String getPlaca() { return placa; }
    public String getTipoVeiculo() { return tipoVeiculo; }
    public double getPesoDeclarado() { return pesoDeclarado; }
    public double getPesoMaximo() { return pesoMaximo; }
    public boolean isConforme() { return conforme; }
    public String getMotivo() { return motivo; }
    public String getCarga() { return carga; }
    public String getNfe() { return nfe; }

    @Override
    public String toString() {
        String status = conforme ? "✅ REGULAR" : "❌ IRREGULAR";
        return String.format("[%s] Placa: %s | Tipo: %s | Peso Declarado: %.0f kg | Peso Máx: %.0f kg | %s",
                status, placa, tipoVeiculo, pesoDeclarado, pesoMaximo, motivo);
    }
}