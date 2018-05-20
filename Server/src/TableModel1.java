import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableModel1 implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<MyBean> beans;

    public TableModel1(List<MyBean> beans) {
        this.beans = beans;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Имя";
            case 1:
                return "Размер";
            case 2:
                return "Описание";
        }
        return "";
    }

    public int getRowCount() {
        return beans.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        MyBean bean = beans.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bean.getName();
            case 1:
                return bean.getSize();
            case 2:
                return bean.getDescription();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}

