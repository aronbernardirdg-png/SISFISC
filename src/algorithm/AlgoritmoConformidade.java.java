package algorithm;

import models.Veiculo;
import models.NotaFiscal;
import models.ResultadoConformidade;

public class AlgoritmoConformidade {
    public static ResultadoConformidade verificar(Veiculo veiculo, NotaFiscal notaFiscal) {
        String placa = "DESCONHECIDA";
        String tipoVeiculo = "Não identificado";
        double pesoDeclarado = 0;
        double pesoMaximo = 0;
        String carga = "N/A", nfe = "N/A";

        if (veiculo == null) {
            return new ResultadoConformidade(placa, tipoVeiculo, 0, 0, false,
                    "❌ Veículo NÃO encontrado na base DETRAN. Possível placa clonada ou irregular.",
                    carga, nfe);
        }
        placa = veiculo.getPlaca();
        tipoVeiculo = veiculo.getTipo();
        pesoMaximo = veiculo.getPesoBrutoTotal();

        if (notaFiscal == null) {
            return new ResultadoConformidade(placa, tipoVeiculo, 0, pesoMaximo, false,
                    "❌ NF-e NÃO encontrada. Veículo sem manifesto fiscal registrado.",
                    carga, nfe);
        }
        pesoDeclarado = notaFiscal.getPesoDeclarado();
        carga = notaFiscal.getCarga();
        nfe = notaFiscal.getNumeroNFe();

        if (pesoDeclarado > pesoMaximo) {
            double excesso = pesoDeclarado - pesoMaximo;
            double percentual = (excesso / pesoMaximo) * 100;
            return new ResultadoConformidade(placa, tipoVeiculo, pesoDeclarado, pesoMaximo, false,
                    String.format("🚨 EXCESSO DE PESO! +%.0f kg acima do permitido (%.1f%% de sobrecarga).", excesso, percentual),
                    carga, nfe);
        }
        return new ResultadoConformidade(placa, tipoVeiculo, pesoDeclarado, pesoMaximo, true,
                "✅ Peso declarado dentro do limite permitido. Veículo em conformidade.",
                carga, nfe);
    }
}