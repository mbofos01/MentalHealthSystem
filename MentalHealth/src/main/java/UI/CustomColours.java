package UI;

import java.awt.Color;

/**
 * This UI help class is used for us to have access to the Apple defined colors
 * for software development.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class CustomColours {
	/**
	 * Dark theme flag
	 */
	static boolean light = true;
	/**
	 * Red for light theme
	 */
	static Color red_light = new Color(215, 0, 21);
	/**
	 * Red for dark theme
	 */
	static Color red_dark = new Color(255, 105, 97);
	/**
	 * Orange for light theme
	 */
	static Color orange_light = new Color(201, 52, 0);
	/**
	 * Orange for dark theme
	 */
	static Color orange_dark = new Color(255, 179, 64);
	/**
	 * Yellow for light theme
	 */
	static Color yellow_light = new Color(160, 90, 0);
	/**
	 * Yellow for dark theme
	 */
	static Color yellow_dark = new Color(255, 212, 38);
	/**
	 * Green for light theme
	 */
	static Color green_light = new Color(0, 125, 27);
	/**
	 * Green for dark theme
	 */
	static Color green_dark = new Color(49, 222, 75);
	/**
	 * Mint for light theme
	 */
	static Color mint_light = new Color(12, 129, 123);
	/**
	 * Mint for dark theme
	 */
	static Color mint_dark = new Color(102, 212, 207);
	/**
	 * Teal for light theme
	 */
	static Color teal_light = new Color(0, 130, 153);
	/**
	 * Teal for dark theme
	 */
	static Color teal_dark = new Color(93, 230, 255);
	/**
	 * Cyan for light theme
	 */
	static Color cyan_light = new Color(0, 113, 164);
	/**
	 * Cyan for dark theme
	 */
	static Color cyan_dark = new Color(112, 215, 255);
	/**
	 * Blue for light theme
	 */
	static Color blue_light = new Color(0, 64, 221);
	/**
	 * Blue for dark theme
	 */
	static Color blue_dark = new Color(64, 156, 255);
	/**
	 * Indigo for light theme
	 */
	static Color indigo_light = new Color(54, 52, 163);
	/**
	 * Indigo for dark theme
	 */
	static Color indigo_dark = new Color(125, 122, 255);
	/**
	 * Purple for light theme
	 */
	static Color purple_light = new Color(173, 68, 171);
	/**
	 * Purple for dark theme
	 */
	static Color purple_dark = new Color(218, 143, 255);
	/**
	 * Pink for light theme
	 */
	static Color pink_light = new Color(211, 15, 69);
	/**
	 * Pink for dark theme
	 */
	static Color pink_dark = new Color(255, 100, 130);
	/**
	 * Brown for light theme
	 */
	static Color brown_light = new Color(127, 101, 69);
	/**
	 * Brown for dark theme
	 */
	static Color brown_dark = new Color(181, 148, 105);
	/**
	 * Gray for light theme
	 */
	static Color gray_light = new Color(105, 105, 110);
	/**
	 * Gray for dark theme
	 */
	static Color gray_dark = new Color(152, 152, 157);
	/**
	 * White
	 */
	static Color white = new Color(255, 255, 255);
	/**
	 * Black
	 */
	static Color black = new Color(29, 29, 30);

	/**
	 * This function checks your application theme.
	 * 
	 * @return True if dark theme is enabled, otherwise false
	 */
	public static boolean isDark() {
		if (light)
			return false;
		return true;
	}

	/**
	 * This function changes the application theme
	 */
	public static void changeTheme() {
		light = !light;
	}

	/**
	 * Get red color based on your application theme.
	 *
	 * @return Red color
	 */
	public static Color Red() {
		if (light)
			return red_light;
		return red_dark;

	}

	/**
	 * Get orange color based on your application theme.
	 *
	 * @return Orange color
	 */
	public static Color Orange() {
		if (light)
			return orange_light;
		return orange_dark;

	}

	/**
	 * Get yellow color based on your application theme.
	 *
	 * @return Yellow color
	 */
	public static Color Yellow() {
		if (light)
			return yellow_light;
		return yellow_dark;

	}

	/**
	 * Get green color based on your application theme.
	 *
	 * @return Green color
	 */
	public static Color Green() {
		if (light)
			return green_light;
		return green_dark;

	}

	/**
	 * Get mint color based on your application theme.
	 *
	 * @return Mint color
	 */

	public static Color Mint() {
		if (light)
			return mint_light;
		return mint_dark;

	}

	/**
	 * Get teal color based on your application theme.
	 *
	 * @return Teal color
	 */
	public static Color Teal() {
		if (light)
			return teal_light;
		return teal_dark;

	}

	/**
	 * Get cyan color based on your application theme.
	 *
	 * @return Cyan color
	 */
	public static Color Cyan() {
		if (light)
			return cyan_light;
		return cyan_dark;

	}

	/**
	 * Get blue color based on your application theme.
	 *
	 * @return Blue color
	 */
	public static Color Blue() {
		if (light)
			return blue_light;
		return blue_dark;

	}

	/**
	 * Get indigo color based on your application theme.
	 *
	 * @return Indigo color
	 */
	public static Color Indigo() {
		if (light)
			return indigo_light;
		return indigo_dark;

	}

	/**
	 * Get purple color based on your application theme.
	 *
	 * @return Purple color
	 */
	public static Color Purple() {
		if (light)
			return purple_light;
		return purple_dark;

	}

	/**
	 * Get pink color based on your application theme.
	 *
	 * @return Pink color
	 */
	public static Color Pink() {
		if (light)
			return pink_light;
		return pink_dark;

	}

	/**
	 * Get brown color based on your application theme.
	 *
	 * @return Brown color
	 */
	public static Color Brown() {
		if (light)
			return brown_light;
		return brown_dark;

	}

	/**
	 * Get gray color based on your application theme.
	 *
	 * @return Gray color
	 */
	public static Color Gray() {
		if (light)
			return gray_light;
		return gray_dark;

	}

	/**
	 * Get black color.
	 *
	 * @return Black color
	 */
	public static Color Black() {
		return black;

	}

	/**
	 * Get white color.
	 *
	 * @return White color
	 */
	public static Color White() {
		return white;

	}

}
