import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.*;

public class StatisticAnalise {

    public enum Type {
        BIGRAM,
        MONOGAM,
        THREEGRAM
    }

    private JPanel mPanel;

    private JScrollPane mSortedMonogram;
    private JScrollPane mUnSortedMonogram;
    private JScrollPane mSortedBigram;
    private JScrollPane mUnSortedBigram;
    private JScrollPane mSortedThreegram;
    private JScrollPane mUnSortedThreegram;


    public StatisticAnalise(String text) {
        JFrame frame = new JFrame("Statistic");
        frame.setContentPane(mPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        initGraphics(text.replace("\n", "").toLowerCase());
    }

    private void initGraphics(String text) {


        HashMap<String, Integer> sortedMono = getResult(text, Type.MONOGAM, true);
        HashMap<String, Integer> unSortedMono = getResult(text, Type.MONOGAM, false);

        HashMap<String, Integer> sortedBi = getResult(text, Type.BIGRAM, true);
        HashMap<String, Integer> unSortedBi = getResult(text, Type.BIGRAM, false);


        HashMap<String, Integer> sortedThree = getResult(text, Type.THREEGRAM, true);
        HashMap<String, Integer> unSortedThree = getResult(text, Type.THREEGRAM, false);

        mSortedMonogram.setViewportView(getGraphic(sortedMono));
        mUnSortedMonogram.setViewportView(getGraphic(unSortedMono));

        mSortedBigram.setViewportView(getGraphic(sortedBi));
        mUnSortedBigram.setViewportView(getGraphic(unSortedBi));

        mSortedThreegram.setViewportView(getGraphic(sortedThree));
        mUnSortedThreegram.setViewportView(getGraphic(unSortedThree));
    }

    private HashMap<String, Integer> getResult(String text, Type monogam, boolean sort) {
        HashMap<String, Integer> result = new HashMap<>();
        int count;
        switch (monogam) {
            case MONOGAM:
                count = 1;
                break;
            case BIGRAM:
                //text = text.replace(" ", "");
                count = 4;
                break;
            case THREEGRAM:
                //text = text.replace(" ", "");
                count = 5;
                break;
            default:
                count = 1;
                break;
        }
        for (int i = 0; i < text.length() - count; i++) {
            String key = text.substring(i, i + count);
            if (count > 2 && (!String.valueOf(key.charAt(0)).equals(" ") || !String.valueOf(key.charAt(count - 1)).equals(" ")))
                continue;
            if (!result.containsKey(key))
                result.put(key, 1);
            else result.put(key, result.get(key) + 1);
        }
        if (sort)
            return sort(result);
        return result;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private HashMap<String, Integer> sort(Map<String, Integer> map) {
        List<?> list = new LinkedList(map.entrySet());

        list.sort((Comparator<Object>) (o1, o2) -> ((Comparable) ((Map.Entry) (o2)).getValue())
                .compareTo(((Map.Entry) (o1)).getValue()));

        HashMap mapSort = new LinkedHashMap();
        for (Object aList : list) {
            Map.Entry entry = (Map.Entry) aList;
            mapSort.put(entry.getKey(), entry.getValue());
        }

        return mapSort;
    }

    private ChartPanel getGraphic(HashMap<String, Integer> data) {
        int printed = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> element : data.entrySet()) {
            if (element.getKey().length() > 1) {
                if (printed > 40)
                    break;
            }
            if (element.getKey().length() > 2) {
                if (printed > 20)
                    break;
            }
            dataset.addValue(element.getValue(), "Symbols",
                    element.getKey());
            printed++;
        }
        JFreeChart chart = ChartFactory.createBarChart("", "Symbol",
                "Quality", dataset, PlotOrientation.VERTICAL, false, true,
                false);
        return new ChartPanel(chart);
    }
}

