package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModuloOCR {
    private static final Random random = new Random();
    private static final String[] PLACAS_DEMO = BancoMock.getTodasPlacas();
    private static int totalLeituras = 0;
    private static int leiturasCorretas = 0;

    public static String capturarPlaca() {
        totalLeituras++;
        if (random.nextDouble() < 0.95) {
            leiturasCorretas++;
            String placa = PLACAS_DEMO[random.nextInt(PLACAS_DEMO.length)];
            System.out.println("[OCR] 📷 Placa detectada: " + placa);
            return placa;
        } else {
            System.out.println("[OCR] ⚠️ Falha na leitura da placa.");
            return null;
        }
    }

    public static List<String> processarMultiplosVeiculos(int quantidade) {
        System.out.println("\n[OCR] 🔄 Iniciando leitura de " + quantidade + " veículos simultâneos...");
        List<String> placas = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            try { Thread.sleep(300 + random.nextInt(700)); } catch (InterruptedException e) {}
            String placa = capturarPlaca();
            if (placa != null) placas.add(placa);
        }
        System.out.println("[OCR] ✅ Leituras concluídas: " + placas.size() + "/" + quantidade);
        return placas;
    }

    public static double getTaxaAcerto() {
        if (totalLeituras == 0) return 100.0;
        return (leiturasCorretas * 100.0) / totalLeituras;
    }
}