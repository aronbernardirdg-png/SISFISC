package models;

public class NotaFiscal {
    private String cnpjEmitente;
    private String carga;
    private double pesoDeclarado;
    private String origem;
    private String destino;
    private String numeroNFe;

    public NotaFiscal(String cnpjEmitente, String carga, double pesoDeclarado,
                      String origem, String destino, String numeroNFe) {
        this.cnpjEmitente = cnpjEmitente;
        this.carga = carga;
        this.pesoDeclarado = pesoDeclarado;
        this.origem = origem;
        this.destino = destino;
        this.numeroNFe = numeroNFe;
    }

    public String getCnpjEmitente() { return cnpjEmitente; }
    public String getCarga() { return carga; }
    public double getPesoDeclarado() { return pesoDeclarado; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public String getNumeroNFe() { return numeroNFe; }

    @Override
    public String toString() {
        return String.format("NF-e: %s | CNPJ: %s | Carga: %s | Peso: %.0f kg | Origem: %s | Destino: %s",
                numeroNFe, cnpjEmitente, carga, pesoDeclarado, origem, destino);
    }
}