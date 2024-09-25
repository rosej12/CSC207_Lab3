package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static CountryCodeConverter countryCodeConverter;
    private static LanguageCodeConverter languageCodeConverter;

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {
        countryCodeConverter = new CountryCodeConverter();
        languageCodeConverter = new LanguageCodeConverter();

        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            final String quit = "quit";
            if (country.equals(quit)) {
                break;
            }

            String countryCode = countryCodeConverter.fromCountry(country);
            String language = promptForLanguage(translator, countryCode);
            if (language.equals(quit)) {
                break;
            }

            String languageCode = languageCodeConverter.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();

        // convert country codes to country names
        List<String> countryNames = new ArrayList<>();
        for (String country : countries) {
            countryNames.add(countryCodeConverter.fromCountryCode(country));
        }

        // sort country names alphabetically
        Collections.sort(countryNames);

        // print country names
        for (String countryName : countryNames) {
            System.out.println(countryName);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        System.out.println(translator.getCountryLanguages(country));

        // get list of language codes
        List<String> languageCodes = translator.getCountryLanguages(country);
        List<String> languageNames = new ArrayList<>();

        // convert to list of languages
        for (String languageCode : languageCodes) {
            languageNames.add(languageCodeConverter.fromLanguageCode(languageCode));
        }

        // sort languages alphabetically
        Collections.sort(languageNames);

        // print languages
        for (String languageName : languageNames) {
            System.out.println(languageName);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
