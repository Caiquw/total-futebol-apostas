import controller.Sistema;
import dao.InicializarDB;
import view.LoginFrame;
import view.MainFrame;
import dao.ClubeDAO;
import model.Clube;
import java.util.List;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        InicializarDB.inicializar();

        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
