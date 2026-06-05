package view;

import controller.Sistema;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
public class MainFrame extends JFrame {

private final Sistema sistema = Sistema.getInstancia();

private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

private final boolean isAdm;

private JTextArea taLog;

    public MainFrame(boolean isAdm) {
        this.isAdm = isAdm;
        super("Sistema de Apostas - Campeonatos de Futebol" + (isAdm ? "ADMIN" : "USUARIO"));
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
