package UI;

import java.awt.Color;

public class CustomColours {
	static boolean light = true;
	// Red
	static Color red_light = new Color(215, 0, 21);
	static Color red_dark = new Color(255, 105, 97);
	// Orange
	static Color orange_light = new Color(201, 52, 0);
	static Color orange_dark = new Color(255, 179, 64);
	// Yellow
	static Color yellow_light = new Color(160, 90, 0);
	static Color yellow_dark = new Color(255, 212, 38);
	// Green
	static Color green_light = new Color(0, 125, 27);
	static Color green_dark = new Color(49, 222, 75);
	// Mint
	static Color mint_light = new Color(12, 129, 123);
	static Color mint_dark = new Color(102, 212, 207);
	// Teal
	static Color teal_light = new Color(0, 130, 153);
	static Color teal_dark = new Color(93, 230, 255);
	// Cyan
	static Color cyan_light = new Color(0, 113, 164);
	static Color cyan_dark = new Color(112, 215, 255);
	// Blue
	static Color blue_light = new Color(0, 64, 221);
	static Color blue_dark = new Color(64, 156, 255);
	// Indigo
	static Color indigo_light = new Color(54, 52, 163);
	static Color indigo_dark = new Color(125, 122, 255);
	// Purple
	static Color purple_light = new Color(173, 68, 171);
	static Color purple_dark = new Color(218, 143, 255);
	// Pink
	static Color pink_light = new Color(211, 15, 69);
	static Color pink_dark = new Color(255, 100, 130);
	// Brown
	static Color brown_light = new Color(127, 101, 69);
	static Color brown_dark = new Color(181, 148, 105);
	// Gray
	static Color gray_light = new Color(105, 105, 110);
	static Color gray_dark = new Color(152, 152, 157);
	// White
	static Color white = new Color(255, 255, 255);
	static Color black = new Color(29, 29, 30);

	public static boolean isDark() {
		if (light)
			return false;
		return true;
	}

	public static void changeTheme() {
		light = !light;
	}

	public static Color Red() {
		if (light)
			return red_light;
		return red_dark;

	}

	public static Color Orange() {
		if (light)
			return orange_light;
		return orange_dark;

	}

	public static Color Yellow() {
		if (light)
			return yellow_light;
		return yellow_dark;

	}

	public static Color Green() {
		if (light)
			return green_light;
		return green_dark;

	}

	public static Color Mint() {
		if (light)
			return mint_light;
		return mint_dark;

	}

	public static Color Teal() {
		if (light)
			return teal_light;
		return teal_dark;

	}

	public static Color Cyan() {
		if (light)
			return cyan_light;
		return cyan_dark;

	}

	public static Color Blue() {
		if (light)
			return blue_light;
		return blue_dark;

	}

	public static Color Indigo() {
		if (light)
			return indigo_light;
		return indigo_dark;

	}

	public static Color Purple() {
		if (light)
			return purple_light;
		return purple_dark;

	}

	public static Color Pink() {
		if (light)
			return pink_light;
		return pink_dark;

	}

	public static Color Brown() {
		if (light)
			return brown_light;
		return brown_dark;

	}

	public static Color Gray() {
		if (light)
			return gray_light;
		return gray_dark;

	}

	public static Color Black() {
		return black;

	}

	public static Color White() {
		return white;

	}

	public static void main(String[] args) {
		System.out.println(Color.DARK_GRAY);
	}

}
