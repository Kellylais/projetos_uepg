package plplan.gui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class to manage externalized strings.
 * <p>
 * PLPLAN
 * Authors : Philippe Fournier-Viger and Ludovic lebel
 * <p>
 * This work is licensed under the Creative Commons Attribution 2.5 License. To
 * view a copy of this license, visit
 * http://creativecommons.org/licenses/by/2.5/ or send a letter to Creative
 * Commons, 543 Howard Street, 5th Floor, San Francisco, California, 94105, USA.
 * <p>
 * If you use PLPLAN, we ask you to mention our names and our webpage URL in your work. 
 * The PLPLAN software is copyrighted by Philippe Fournier-Viger and Ludovic Lebel (2005). 
 * Please read carefully the license to know what you can do and cannot do with this software. 
 * You can contact Philippe Fournier-Viger for special permissions. 
 * <p>
 * This sofware is provided "as is", without warranty of any kind. 
 * The user takes the entire risk as to the quality and performance of the software. 
 * The authors accept no responsibility for any problem the user encounters using this software.
 * <p>
 * @author Philippe Fournier-Viger and Ludovic Lebel
 */
public class Messages {
    private static String bundleName = "plplan.gui.messages_en"; //$NON-NLS-1$

    private static ResourceBundle ressourceBundle = ResourceBundle
            .getBundle(bundleName);

    private Messages() {
    }

    public static String getString(String key) {
        try {
//            System.out.println("request " + key + "  from Bundle : " + bundleName);
            return ressourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    static String getBundle_name() {
        return bundleName;
    }

    static void setBundle_name(String bundleName) {
        Messages.bundleName = bundleName;
        ressourceBundle = ResourceBundle.getBundle(bundleName);
        System.out.println("Bundle :  " + bundleName );
        System.out.println("---"  + Messages.getString("PLPlanGui_English.70") + "---");
    }
}
