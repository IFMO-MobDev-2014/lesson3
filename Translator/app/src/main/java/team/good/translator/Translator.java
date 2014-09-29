package team.good.translator;

/**
 * Created by Artur on 29.09.2014.
 */
public class Translator {
    public String translate(String word) {
        TranslateTask translateTask = new TranslateTask();
        translateTask.execute(word);
        try {
            translateTask.get();
        } catch (Exception e) {
            return "Error";
        }
        return translateTask.getTranslation();
    }
}
