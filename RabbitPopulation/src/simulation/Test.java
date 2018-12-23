package simulation;

import utilities.Constants;
import utilities.MT19937;
import utilities.NormalDistribution;
import utilities.UniformDistribution;

public class Test {

	public static void main(String[] args) {
		MT19937 mt = new MT19937(1);

		NormalDistribution nd = new NormalDistribution(mt);
		testNormalDistribution(nd);

		UniformDistribution ud = new UniformDistribution(mt);
		testUniformDistribution(ud);
		testUniformInt(ud);
		testRandom(ud);
	}

	public static void testNormalDistribution(NormalDistribution nd) {
		int[] dist = new int[5];
		int next;

		for (int i = 0; i < 1000000; i++) {
			next = nd.nextGaussian(4, 8, 6.0, 2.5);
			dist[next - 4]++;
		}

		for (int i = 0; i < 5; i++) {
			System.out.println(dist[i]);
		}
	}
	
	public static void testUniformDistribution(UniformDistribution ud) {
		for (int i = 0; i < 100; i++) {
			System.out.println(ud.nextDouble(0.4, 0.5));
		}
	}

	public static void testUniformInt(UniformDistribution ud) {
		int num3 = 0;
		int num4 = 0;
		int num5 = 0;
		int num6 = 0;
		int num;
		for (int i = 0; i < 1000000; i++) {
			num = ud.nextInt(3, 6);
			if (num == 3) {
				num3++;
			} else if (num == 4) {
				num4++;
			} else if (num == 5) {
				num5++;
			} else if (num == 6) {
				num6++;
			}
		}
		System.out.println(num3);
		System.out.println(num4);
		System.out.println(num5);
		System.out.println(num6);
	}

	public static void testRandom(UniformDistribution ud) {
		int male = 0;
		int female = 0;
		String sex;
		for (int i = 0; i < 1000000; i++) {
			sex = ud.nextDouble() < 0.5 ? Constants.MALE : Constants.FEMALE;
			if (sex.equals(Constants.MALE)) {
				male++;
			} else if (sex.equalsIgnoreCase(Constants.FEMALE)) {
				female++;
			}
		}
		System.out.println("male: " + male);
		System.out.println("female: " + female);
	}

}
