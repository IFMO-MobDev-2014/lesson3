package com.abrafcm.translator.translator;

/**
 * Created by Nikita Yaschenko on 27.09.14.
 */
public class FakeTranslateProvider implements ITranslateProvider {
    @Override
    public String translate(String string) {
        return "I'll translate: `" + string + "`";
    }
}
