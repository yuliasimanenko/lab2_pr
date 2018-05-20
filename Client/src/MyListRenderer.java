import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

class MyListRenderer extends DefaultListCellRenderer
{
    public static int alpha = 255;
    public static ArrayList<Integer> toChangeAlpha = new ArrayList<>();
    public static ArrayList<lab1.Color> itemColors = new ArrayList<>();


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        setBackground(new Color(itemColors.get(index).getR(),
                                itemColors.get(index).getG(),
                                itemColors.get(index).getB()));

        if (toChangeAlpha.contains(index)) {
            Color bgc = getBackground();
            setBackground(new Color(bgc.getRed(), bgc.getGreen(), bgc.getBlue(), alpha));

            Color fgc = getForeground();
            setForeground(new Color(fgc.getRed(), fgc.getGreen(), fgc.getBlue(), alpha));
        }

//        if(value.toString().contains("INCOMPLETE") ||  value.toString().contains("COMPLETED")) {
//            lab1.Color fg = value.toString().contains("COMPLETED") ? lab1.Color.green : lab1.Color.red;
//            setForeground(fg);
//        } else {
//            setForeground(list.getForeground());
//        }
        return this;
    }

}
