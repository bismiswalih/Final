import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deluxe extends Savings implements Compound_Interest{
    public Deluxe(String customerNumber, String customerName, String initialDeposit, String numOfYears, String typeOfSavings) throws SQLException, ClassNotFoundException {
        super(customerNumber, customerName, initialDeposit, numOfYears, typeOfSavings);
    }
    final double interestrate =0.15;
    @Override
    public void generateTable() throws SQLException {
        DefaultTableModel df1 = (DefaultTableModel) table1.getModel();
        int index = table1.getSelectedRow();
        DefaultTableModel df = (DefaultTableModel) table2.getModel();
        int z = Integer.parseInt(getNumOfYears());
        String[][] array = new String[0][];
        if (z > 0) {
            array = new String[z][4];
        }
        int j = 0;
        double counter=1.0;
        double starting=Double.parseDouble((String) df1.getValueAt(index,2));
        double interest=0.0;
        while (j<z) {
            array[j][0] = Double.toString(counter);
            array[j][1] = Double.toString(starting);
            interest=Double.parseDouble(array[j][1])*interestrate;
            array[j][2] = Double.toString(interest);
            array[j][3] = Double.toString(starting+interest);
            starting=Double.parseDouble(array[j][3]);
            ++j;
        }
        String[] cols = {"Year", "Starting","Interest","Ending Value"};
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table2.setModel(model);
    }
}
