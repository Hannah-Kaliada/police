package il.pacukievich.police.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrimeUtils {

		public static boolean containsValidName(String description) {
				String regex = "([А-Я][а-я]+)\\s([А-Я][а-я]+)\\s([А-Я][а-я]+)";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(description);
				return matcher.find();
		}

		public static boolean containsPhoneNumber(String description) {
				String regex = "(\\+375|375|80)\\s?(\\(?(29|33|44|25)\\)?)\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(description);
				return matcher.find();
		}
}