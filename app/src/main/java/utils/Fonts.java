package utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ratnav on 22-05-2015.
 */
public class Fonts {
    Typeface font_bold, font_regular, font_card, font_arial, font_arial_bold;

    public Fonts(Context c) {
        font_arial_bold = Typeface.createFromAsset(c.getAssets(), "arialbd.ttf");
        font_bold = Typeface.createFromAsset(c.getAssets(), "asap_bold.ttf");
        font_regular = Typeface.createFromAsset(c.getAssets(), "asap_regular.ttf");
        font_card = Typeface.createFromAsset(c.getAssets(), "card_font.ttf");
        font_arial = Typeface.createFromAsset(c.getAssets(), "arial.ttf");
    }

    public Typeface font_bold() {
        return font_bold;
    }

    public Typeface arial_bold() {
        return font_arial_bold;
    }

    public Typeface font_regular() {
        return font_regular;
    }

    public Typeface font_card() {
        return font_card;
    }

    public Typeface font_arial() {
        return font_arial;
    }
}
