package task.view.filter;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class DocumentFilterOnlyLetters extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fp, int offset, String stringToFilter, AttributeSet aset) throws BadLocationException {
        int len = stringToFilter.length();
        if (Character.isLetter(stringToFilter.charAt(len - 1)))
            super.insertString(fp, offset, stringToFilter, aset);
        else
            Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fp, int offset, int length, String stringToFilter, AttributeSet aset) throws BadLocationException {
        int len = stringToFilter.length();
        if (Character.isLetter(stringToFilter.charAt(len - 1)) || stringToFilter.charAt(len - 1) == ' ')
            super.replace(fp, offset, length, stringToFilter, aset);
        else
            Toolkit.getDefaultToolkit().beep();
    }
}
