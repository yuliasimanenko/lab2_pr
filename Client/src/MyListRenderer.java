import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

class MyListRenderer extends DefaultListCellRenderer
{
    public static int alpha = 255;
    public static ArrayList<Integer> toChangeAlpha = new ArrayList<>();


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (toChangeAlpha.contains(index)) {
            Color bgc = getBackground();
            setBackground(new Color(bgc.getRed(), bgc.getGreen(), bgc.getBlue(), alpha));

            Color fgc = getForeground();
            setForeground(new Color(fgc.getRed(), fgc.getGreen(), fgc.getBlue(), alpha));
        }

//        if(value.toString().contains("INCOMPLETE") ||  value.toString().contains("COMPLETED")) {
//            Color fg = value.toString().contains("COMPLETED") ? Color.green : Color.red;
//            setForeground(fg);
//        } else {
//            setForeground(list.getForeground());
//        }
        return this;
    }

}
