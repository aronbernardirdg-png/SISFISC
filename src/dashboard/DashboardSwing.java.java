package dashboard;

import models.ResultadoConformidade;
import modules.BancoMock;
import modules.ModuloOCR;
import algorithm.AlgoritmoConformidade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardSwing extends JFrame {
    private JLabel lblTotalProcessados;
    private JLabel lblSimultaneos;
    private JLabel lblTaxaOCR;
    private JLabel lblIrregulares;
    private DefaultTableModel tableModel;
    private JTextArea areaLog;
    private JLabel lblAlerta;
    private int totalProcessados = 0;
    private int totalIrregulares = 0;

    private static final Color COR_FUNDO = new Color(15, 25, 35);
    private static final Color COR_CARDS = new Color(27, 40, 56);
    private static final Color COR_DESTAQUE = new Color(0, 180, 216);
    private static final Color COR_ALERTA = new Color(255, 68, 68);

    public DashboardSwing() {
        setTitle("🚛 SISFISC - Sistema de Fiscalização Integrada");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelMetricas = new JPanel(new GridLayout(1, 4, 15, 0));
        painelMetricas.setBackground(COR_FUNDO);
        painelMetricas.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblTotalProcessados = criarMetrica(painelMetricas, "Veículos Processados", "0", COR_DESTAQUE);
        lblSimultaneos = criarMetrica(painelMetricas, "Processamento Simultâneo", "0", COR_DESTAQUE);
        lblTaxaOCR = criarMetrica(painelMetricas, "Taxa Leitura OCR", "100%", COR_DESTAQUE);
        lblIrregulares = criarMetrica(painelMetricas, "Irregularidades Detectadas", "0", COR_ALERTA);

        add(painelMetricas, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(5, 5));
        painelCentral.setBackground(COR_FUNDO);
        painelCentral.add(criarPainelControles(), BorderLayout.NORTH);
        painelCentral.add(criarPainelTabela(), BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
        painelInferior.setBackground(COR_FUNDO);
        painelInferior.add(criarPainelAlerta(), BorderLayout.NORTH);
        painelInferior.add(criarPainelLog(), BorderLayout.CENTER);
        painelInferior.setPreferredSize(new Dimension(0, 200));
        add(painelInferior, BorderLayout.SOUTH);
    }

    private JLabel criarMetrica(JPanel pai, String titulo, String valor, Color corBorda) {
        JPanel card = new JPanel();
        card.setBackground(COR_CARDS);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, corBorda),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(new Color(136, 153, 170));
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblValor = new JLabel(valor);
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(5));
        card.add(lblValor);
        pai.add(card);
        return lblValor;
    }

    private JPanel criarPainelControles() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painel.setBackground(COR_FUNDO);

        JButton btnSimular = new JButton("🔄 Simular Leitura de Placa Única");
        btnSimular.setBackground(COR_DESTAQUE);
        btnSimular.setForeground(Color.BLACK);
        btnSimular.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimular.addActionListener(e -> processarVeiculoUnico());

        JButton btnLote = new JButton("⚡ Processar Lote (5 Veículos Simultâneos)");
        btnLote.setBackground(COR_DESTAQUE);
        btnLote.setForeground(Color.BLACK);
        btnLote.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLote.addActionListener(e -> processarLote(5));

        JButton btnLimpar = new JButton("🗑️ Limpar Dashboard");
        btnLimpar.setBackground(COR_DESTAQUE);
        btnLimpar.setForeground(Color.BLACK);
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLimpar.addActionListener(e -> limparDashboard());

        painel.add(btnSimular);
        painel.add(btnLote);
        painel.add(btnLimpar);
        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"Placa", "Tipo", "Peso Declarado", "Peso Máx.", "Conformidade", "Detalhes"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        tabela.setBackground(COR_CARDS);
        tabela.setForeground(new Color(224, 224, 224));
        tabela.setGridColor(new Color(42, 58, 74));
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setBackground(new Color(13, 27, 42));
        tabela.getTableHeader().setForeground(COR_DESTAQUE);
        tabela.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(COR_FUNDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JPanel criarPainelAlerta() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_CARDS);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        lblAlerta = new JLabel("✅ Sistema operacional. Nenhuma irregularidade crítica no momento.");
        lblAlerta.setForeground(new Color(0, 255, 136));
        lblAlerta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painel.add(lblAlerta, BorderLayout.CENTER);
        return painel;
    }

    private JScrollPane criarPainelLog() {
        areaLog = new JTextArea();
        areaLog.setBackground(new Color(13, 27, 42));
        areaLog.setForeground(new Color(136, 153, 170));
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaLog.setEditable(false);
        areaLog.setText("📋 Log de Processamento:\n> Sistema iniciado. Aguardando veículos...\n");
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBackground(COR_FUNDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private void processarVeiculoUnico() {
        String placa = ModuloOCR.capturarPlaca();
        if (placa == null) {
            adicionarLog("⚠️ Falha na leitura OCR. Tente novamente.");
            return;
        }
        processarPlaca(placa);
        atualizarMetricas();
    }

    private void processarLote(int quantidade) {
        adicionarLog("🔄 Iniciando processamento de " + quantidade + " veículos simultâneos...");
        new Thread(() -> {
            List<String> placas = ModuloOCR.processarMultiplosVeiculos(quantidade);
            SwingUtilities.invokeLater(() -> {
                for (String placa : placas) processarPlaca(placa);
                atualizarMetricas();
                adicionarLog("✅ Lote processado com sucesso!");
            });
        }).start();
    }

    private void processarPlaca(String placa) {
        adicionarLog("🔍 Processando placa: " + placa);
        var veiculo = BancoMock.consultarDetran(placa);
        if (veiculo != null) {
            adicionarLog("  ├─ DETRAN: " + veiculo.getTipo() + " encontrado.");
        } else {
            adicionarLog("  ├─ DETRAN: ⚠️ Veículo NÃO encontrado!");
        }
        var nfe = BancoMock.consultarNFe(placa);
        if (nfe != null) {
            adicionarLog("  ├─ NF-e: " + nfe.getCarga() + " - " + nfe.getPesoDeclarado() + " kg");
        } else {
            adicionarLog("  ├─ NF-e: ⚠️ NOTA NÃO ENCONTRADA!");
        }
        ResultadoConformidade resultado = AlgoritmoConformidade.verificar(veiculo, nfe);
        totalProcessados++;
        if (!resultado.isConforme()) totalIrregulares++;
        String status = resultado.isConforme() ? "✅ REGULAR" : "❌ IRREGULAR";
        tableModel.insertRow(0, new Object[]{
                resultado.getPlaca(), resultado.getTipoVeiculo(),
                String.format("%.0f kg", resultado.getPesoDeclarado()),
                String.format("%.0f kg", resultado.getPesoMaximo()),
                status, resultado.getMotivo()
        });
        if (!resultado.isConforme()) {
            adicionarLog("  └─ 🚨 IRREGULARIDADE DETECTADA: " + resultado.getMotivo());
            mostrarAlerta(resultado.getMotivo());
        } else {
            adicionarLog("  └─ ✅ Veículo regular.");
        }
    }

    private void mostrarAlerta(String mensagem) {
        lblAlerta.setText("🚨 ALERTA: " + mensagem);
        lblAlerta.setForeground(COR_ALERTA);
        new Thread(() -> {
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            SwingUtilities.invokeLater(() -> {
                lblAlerta.setText("✅ Sistema operacional. Nenhuma irregularidade crítica no momento.");
                lblAlerta.setForeground(new Color(0, 255, 136));
            });
        }).start();
    }

    private void atualizarMetricas() {
        lblTotalProcessados.setText(String.valueOf(totalProcessados));
        lblIrregulares.setText(String.valueOf(totalIrregulares));
        lblTaxaOCR.setText(String.format("%.1f%%", ModuloOCR.getTaxaAcerto()));
        lblSimultaneos.setText("Até 5");
    }

    private void adicionarLog(String mensagem) {
        areaLog.append("> " + mensagem + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private void limparDashboard() {
        tableModel.setRowCount(0);
        totalProcessados = 0;
        totalIrregulares = 0;
        atualizarMetricas();
        areaLog.setText("📋 Log limpo. Sistema reiniciado.\n");
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(DashboardSwing::new);
    }
}