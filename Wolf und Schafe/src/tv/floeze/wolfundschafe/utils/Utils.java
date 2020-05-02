package tv.floeze.wolfundschafe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils {

	private static final List<String> rndNamesPrefixes = new ArrayList<String>(),
			rndNameAdjectives = new ArrayList<String>(), rndNameNames = new ArrayList<String>();

	private static final Random rng = new Random();

	// Initialisation
	static {
		Scanner rndNameSc = new Scanner(Utils.class.getResourceAsStream("/rsc/rndNames.txt"));
		int status = -1;
		while (rndNameSc.hasNextLine()) {
			String line = rndNameSc.nextLine();
			if (line.equals("#prepostfixes")) {
				status = 0;
				continue;
			} else if (line.equals("#adjectives")) {
				status = 1;
				continue;
			} else if (line.equals("#names")) {
				status = 2;
				continue;
			}

			if (status == 0) {
				rndNamesPrefixes.add(line);
			} else if (status == 1) {
				rndNameAdjectives.add(line);
			} else if (status == 2) {
				rndNameNames.add(line);
			}
		}
		rndNameSc.close();
		System.gc();
	}

	/**
	 * Gets a random name
	 * 
	 * @return random generated name
	 */
	public static String getRandomName() {
		String u = rng.nextBoolean() ? "_" : "";
		String number = rng.nextBoolean() ? String.valueOf(rng.nextInt(99)) : "";
		String namePrefix = rng.nextBoolean() ? rndNamesPrefixes.get(rng.nextInt(rndNamesPrefixes.size())) : "";
		String nameAdjective = rndNameAdjectives.get(rng.nextInt(rndNameAdjectives.size()));
		String nameName = rndNameNames.get(rng.nextInt(rndNameNames.size()));
		String namePostfix = new StringBuilder(namePrefix).reverse().toString();
		StringBuilder name = new StringBuilder();
		name.append(namePrefix);
		name.append(nameAdjective);
		name.append(u);
		name.append(nameName);
		if (!number.equals("")) {
			name.append(u);
			name.append(number);
		}
		name.append(namePostfix);
		u = null;
		number = null;
		namePrefix = null;
		nameAdjective = null;
		nameName = null;
		namePostfix = null;
		System.gc();
		return name.toString();
	}

	// private, because this contains only static methods
	private Utils() {
	}
}
