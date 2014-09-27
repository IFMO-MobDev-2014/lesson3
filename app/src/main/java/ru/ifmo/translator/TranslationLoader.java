package ru.ifmo.translator;

import java.util.concurrent.ExecutionException;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class TranslationLoader {
    static String translate(String query) {
        String result = "Translation error";
        try {
            result = new GetTranslation().execute(query).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}