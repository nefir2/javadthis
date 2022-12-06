//command, to open this one:
//linux -  /home/user/.jdks/openjdk-19.0.1/bin/java -javaagent:/home/user/intellij-idea/idea-IC-223.7571.182/lib/idea_rt.jar=39287:/home/user/intellij-idea/idea-IC-223.7571.182/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /home/user/IdeaProjects/consoleobjects/out/production/consoleobjects Main
//windows - /c/users/uc33_9/.jdks/openjdk-19.0.1/bin/java.exe $"-javaagent:C:\Program Files\JetBrains\Intellij IDEA Community Edition 2021.1.3\lib\idea_rt.jar=60971:C:\Program Files\JetBrains\Intellij IDEA Community Edition 2021.1.3\bin" -Dfile.encoding=UTF-8 -classpath /c/users/uc33_9/ideaprojects/idk/out/production/idk Main


import static java.lang.Math.*;
import java.util.Scanner;


public class Main {
	private static final short SCREEN_WIDTH = 270;
	private static final short SCREEN_HEIGHT = 63;
	private static final float ASPECT = (float)SCREEN_WIDTH / SCREEN_HEIGHT;
	private static final float PIXEL_ASPECT = 11.0f / 24.0f;
	private static final String GRADIENT = " .:!/r(l1Z4H9W8$@";
	private static final int GRADIENT_SIZE = GRADIENT.length() - 2; //а почему - 2 ?


	public static void main(String[] args) {
		final char LAST_SIGN = '\0';
		short size = SCREEN_WIDTH * SCREEN_HEIGHT;
		char[] screen = new char[size + 1];
		screen[size] = LAST_SIGN;


		for (int t = 0; t < 10000; t++) {
			//свет
			vec3 light = VecFunctions.norm(new vec3(-0.5f, 0.5f, -1.0f)); //new vec3(-0.5f, 0.5f, -1.0f) //new vec3((float)sin(t * 0.001f), (float)(cos(t * 0.001f)), -1.0f)
			for (int i = 0; i < SCREEN_WIDTH; i++) {
				for (int j = 0; j < SCREEN_HEIGHT; j++) {
					vec2 uv = new vec2(i, j).divide(new vec2(SCREEN_WIDTH, SCREEN_HEIGHT)).multiply(new vec2(2.0f)).minus(new vec2(1.0f));
					uv.setX(uv.getX() * ASPECT * PIXEL_ASPECT);
					vec3 ro = new vec3(-2, 0, 0);
					vec3 rd = VecFunctions.norm(new vec3(1, uv));
					char pixel = ' ';
					int color = 0;
					vec2 intersection = VecFunctions.sphere(ro, rd, 1);

					if (intersection.getX() > 0) {
						//свет
						//должно быть "ro + rd * intersection.x", но что в данном выражении, с учётом перегрузок оператора, идёт первым?
						vec3 itPoint = rd.multiply(new vec3(intersection.getX())).plus(ro); //первый вариант
						//vec3 itPoint = ro.plus(rd).multiply(new vec3(intersection.getX())); //второй вариант
						//но в итоге оба варианта нерабочие....
						vec3 n = VecFunctions.norm(itPoint);
						float diff = VecFunctions.dot(n, light);
						color = (int)diff * 20;
					}
					color = (int)clamp(color, 0, GRADIENT_SIZE);
					pixel = GRADIENT.charAt(color);
					screen[i + j * SCREEN_WIDTH] = pixel;
				}
			}
			System.out.print(screen);
		}

		new Scanner(System.in).nextLine();
	}
	public static float clamp(float value, float min, float max) { return Math.max(Math.min(value, max), min); }
}