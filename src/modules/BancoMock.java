package modules;

import models.Veiculo;
import models.NotaFiscal;
import java.util.HashMap;
import java.util.Map;

public class BancoMock {
    private static final Map<String, Veiculo> baseDetran = new HashMap<>();
    private static final Map<String, NotaFiscal> baseNFe = new HashMap<>();

    static {
        baseDetran.put("ABC1234", new Veiculo("ABC1234", "Caminhão Volkswagen 24.280", 23000, "Transportadora Rapidao Ltda", 2020, "São Paulo-SP"));
        baseDetran.put("DEF5678", new Veiculo("DEF5678", "Carreta Scania R450", 40000, "Carga Pesada S.A.", 2022, "Campinas-SP"));
        baseDetran.put("GHI9012", new Veiculo("GHI9012", "Bitrem Volvo FH 540", 48500, "AgroTransportes Brasil", 2023, "Sorriso-MT"));
        baseDetran.put("JKL3456", new Veiculo("JKL3456", "Caminhão Mercedes Atego", 16000, "Entregas Expressas ME", 2019, "Rio de Janeiro-RJ"));
        baseDetran.put("MNO7890", new Veiculo("MNO7890", "Carreta Iveco Hi-Way", 42000, "Logistica Norte-Sul", 2021, "Cuiabá-MT"));
        baseDetran.put("PQR2468", new Veiculo("PQR2468", "Caminhão Ford Cargo 2629", 26000, "Transportes Centro-Oeste", 2023, "Goiânia-GO"));
        baseDetran.put("STU1357", new Veiculo("STU1357", "Bitrem Mercedes Axor 3344", 50000, "Grãos Brasil Ltda", 2022, "Primavera do Leste-MT"));

        baseNFe.put("ABC1234", new NotaFiscal("12.345.678/0001-99", "Soja em Grãos", 25000, "Sorriso-MT", "Santos-SP", "NFe-2024-001234"));
        baseNFe.put("DEF5678", new NotaFiscal("98.765.432/0001-11", "Eletrodomésticos", 35000, "São Paulo-SP", "Belo Horizonte-MG", "NFe-2024-005678"));
        baseNFe.put("GHI9012", new NotaFiscal("11.222.333/0001-44", "Milho a Granel", 50000, "Lucas do Rio Verde-MT", "Paranaguá-PR", "NFe-2024-009012"));
        baseNFe.put("JKL3456", new NotaFiscal("55.666.777/0001-88", "Móveis Planejados", 14000, "Curitiba-PR", "Florianópolis-SC", "NFe-2024-003456"));
        baseNFe.put("MNO7890", new NotaFiscal("33.444.555/0001-66", "Fertilizantes", 43000, "Uberaba-MG", "Porto Velho-RO", "NFe-2024-007890"));
        baseNFe.put("PQR2468", new NotaFiscal("77.888.999/0001-22", "Arroz em Casca", 24000, "Goiânia-GO", "São Paulo-SP", "NFe-2024-002468"));
        baseNFe.put("STU1357", new NotaFiscal("44.555.666/0001-33", "Algodão", 51000, "Primavera do Leste-MT", "Santos-SP", "NFe-2024-001357"));
    }

    public static Veiculo consultarDetran(String placa) { return baseDetran.get(placa.toUpperCase()); }
    public static NotaFiscal consultarNFe(String placa) { return baseNFe.get(placa.toUpperCase()); }
    public static String[] getTodasPlacas() { return baseDetran.keySet().toArray(new String[0]); }
}