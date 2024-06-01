package theme;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

/**
 *
 * @author dulanjaya
 */
public class CustomMacDarkLaf extends FlatMacDarkLaf {
    public static boolean setup() {
        return setup(new CustomMacDarkLaf());
    }
    
    @Override
    public String getName() {
        return "CustomMacDarkLaf";
    }
}
