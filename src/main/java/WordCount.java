import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordCount extends DataBase {

    private static String addLink() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите ссылку: ");
        return in.next();
    }

    protected void ParseHtml() throws IOException {
        Connection connection = Jsoup.connect(addLink());
        Document doc = connection.get();

        String docText = doc.body().text();
        Pattern patternSpace = Pattern.compile("([a-zа-яё\\\\d]+)([ЁА-ЯA-Z])");
        docText = docText.toUpperCase().replaceAll(patternSpace.pattern(), "$1 $2");
        Pattern pattern = Pattern.compile("(?<=\\W)([a-zA-Zа-яА-ЯёЁ]+)(?=\\W)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(docText);

        Set<String> words = new HashSet<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        Object[] lines = words.toArray();
        DataBase dataBase = new DataBase();
        WordCount wordCount = new WordCount();

        dataBase.connectDatabase();
        dataBase.insert(wordCount.countWords(lines, docText));
        dataBase.select();
        dataBase.close();
    }


    private Map<String, Integer> countWords(Object[] lines, String line) {

        HashMap<String, Integer> wordCard = new HashMap<>();
        Pattern pattern;
        Matcher matcher;
        for (Object o : lines) {
            int countWord = 0;
            pattern = Pattern.compile("(?<=\\W)" + o + "(?=\\W)", Pattern.UNICODE_CHARACTER_CLASS);
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                wordCard.put(matcher.group(), ++countWord);
            }
            matcher.reset();
        }
        return wordCard;
    }
}