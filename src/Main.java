import controller.Sistema;
import view.LoginFrame;
import view.MainFrame;

import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {


        SwingUtilities.invokeLater(LoginFrame::new);

}
}
