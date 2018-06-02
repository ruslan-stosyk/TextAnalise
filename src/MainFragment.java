import viginere.CodeVigenere;
import zesar.CaesarCode;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class MainFragment {

    private final static String mRegular = "%s \n %s  :  %s";

    private JPanel mMain;

    private JTextArea mAnaliseText;
    private JButton mAnaliseBtn;

    private JButton mCaesarBtn;
    private JTextArea mCaesarText;

    private JTextArea mVigenereText;
    private JTextField mKeyText;
    private JButton mCodeBtn;
    private JButton mDecodeBtn;


    private JTextArea mReplaceText;
    private JTextField mFromText;
    private JTextField mToText;
    private JButton mReplaceBtn;
    private JTextArea mHistoryText;

    private JTextField mSearchWord;
    private JButton mSearchBtn;
    private JTextPane mSearchText;

    public static void main(String[] args) {
        new MainFragment();
    }

    private MainFragment() {
        JFrame frame = new JFrame();
        frame.setTitle("Universal Decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mMain);
        frame.pack();
        frame.setVisible(true);
        initViews();
    }

    private void initViews() {
        mAnaliseBtn.addActionListener(actionEvent -> doStatisticAnalise());

        mCaesarBtn.addActionListener(actionEvent -> doCaesarCode());

        mCodeBtn.addActionListener(actionEvent -> doCodeVigenere());

        mDecodeBtn.addActionListener(actionEvent -> doDecodeVigenere());

        mReplaceBtn.addActionListener(actionEvent -> doReplace());
        mSearchBtn.addActionListener(actionEvent -> doSearch());
    }

    private void doStatisticAnalise() {
        String text = mAnaliseText.getText();
        new StatisticAnalise(text);
    }

    private void doCaesarCode() {
        CaesarCode zc = new CaesarCode(mCaesarText.getText());
        String[] result = zc.doAnalysis();
        String resultString = "";
        for (int i = 0; i < 31; i++) {
            resultString += "Text " + (i + 1) + ": " + result[i] + "\n\n";
        }
        mCaesarText.setText(resultString);
    }

    private void doCodeVigenere() {
        CodeVigenere vc = new CodeVigenere(mVigenereText.getText(), mKeyText.getText());
        String result = vc.code();
        mVigenereText.setText(result);
    }

    private void doDecodeVigenere() {
        CodeVigenere vc = new CodeVigenere(mVigenereText.getText(), mKeyText.getText());
        String result = vc.decode();
        mVigenereText.setText(result);
    }

    private void doReplace() {
        String fromText = mFromText.getText().toUpperCase();
        String toText = mToText.getText().toLowerCase();

        String newText = mReplaceText.getText();
        mReplaceText.setText(newText.replace(fromText, toText));


        String history = mHistoryText.getText();
        String newHistory = String.format(mRegular, history, fromText, toText);
        mHistoryText.setText(newHistory);
    }

    private void doSearch() {
        String target = mSearchWord.getText();
        String checkString = "";
        for (int i = 0; i < target.length(); i++) {
            checkString += "*";
        }

        char[] checkArray = mSearchText.getText().toLowerCase().replaceAll
                (target.toLowerCase(), checkString).toCharArray();

        char[] txt = mSearchText.getText().toLowerCase().toCharArray();

        SimpleAttributeSet set1 = new SimpleAttributeSet();
        StyleConstants.setForeground(set1, new Color(0, 0, 0));

        SimpleAttributeSet set2 = new SimpleAttributeSet();
        StyleConstants.setForeground(set2, new Color(255, 0, 0));

        mSearchText.setText("");

        Document doc = mSearchText.getStyledDocument();

        for (int i = 0; i < checkArray.length; i++)
            try {
                if (checkArray[i] != '*')
                    doc.insertString(doc.getLength(), "" + txt[i], set1);
                else
                    doc.insertString(doc.getLength(), ("" + txt[i]).toUpperCase(), set2);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
    }
}
