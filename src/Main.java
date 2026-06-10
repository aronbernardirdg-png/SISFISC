import modules.BancoMock;
import modules.ModuloOCR;
import algorithm.AlgoritmoConformidade;
import dashboard.DashboardSwing;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("🚛 SISFISC - Sistema de Fiscalização Integrada");
        System.out.println("=".repeat(60));
        System.out.println("\nEscolha o modo de execução:");
        System.out.println("  1 - Modo Terminal (demonstração rápida)");
        System.out.println("  2 - Dashboard Gráfico (Swing - interface completa)");
        System.out.print("\nOpção: ");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();
        scanner.close();

        switch (opcao) {
            case 1 -> executarModoTerminal();
            case 2 -> executarDashboard();
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void executarModoTerminal() {
        System.out.println("\n📋 INICIANDO DEMONSTRAÇÃO NO TERMINAL...\n");
        System.out.println("[ETAPA 1] MÓDULO OCR - Captura Simultânea de Placas");
        System.out.println("-".repeat(40));
        List<String> placas = ModuloOCR.processarMultiplosVeiculos(5);

        System.out.println("\n[ETAPA 2] CONSULTAS DETRAN + NF-e");
        System.out.println("[ETAPA 3] ALGORITMO DE CONFORMIDADE");
        System.out.println("-".repeat(40));

        int regulares = 0, irregulares = 0;

        for (String placa : placas) {
            System.out.println("\n🔍 Placa: " + placa);
            var veiculo = BancoMock.consultarDetran(placa);
            if (veiculo != null) {
                System.out.println("   ├─ DETRAN: " + veiculo.getTipo() + " (PBT: " + veiculo.getPesoBrutoTotal() + " kg)");
            } else {
                System.out.println("   ├─ DETRAN: ❌ Não encontrado!");
            }
            var nfe = BancoMock.consultarNFe(placa);
            if (nfe != null) {
                System.out.println("   ├─ NF-e: " + nfe.getCarga() + " (" + nfe.getPesoDeclarado() + " kg)");
            } else {
                System.out.println("   ├─ NF-e: ❌ Não encontrada!");
            }
            var resultado = AlgoritmoConformidade.verificar(veiculo, nfe);
            System.out.println("   └─ RESULTADO: " + resultado);
            if (resultado.isConforme()) regulares++;
            else irregulares++;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 RESUMO DO PROCESSAMENTO");
        System.out.println("=".repeat(60));
        System.out.println("Total processados: " + placas.size());
        System.out.println("✅ Regulares: " + regulares);
        System.out.println("❌ Irregulares: " + irregulares);
        System.out.println("Taxa OCR: " + String.format("%.1f%%", ModuloOCR.getTaxaAcerto()));
        System.out.println("=".repeat(60));
    }

    private static void executarDashboard() {
        System.out.println("\n🖥️  Iniciando Dashboard Gráfico...");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(DashboardSwing::new);
    }
}