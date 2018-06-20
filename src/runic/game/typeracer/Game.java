package runic.game.typeracer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import runic.engine.entity.Camera;
import runic.engine.util.Keyboard;
import runic.engine.util.Mouse;
import runic.engine.util.math.MathUtil;
import runic.engine.util.timers.Delay;
import runic.engine.util.timers.Time;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public final static String TITLE = "Speed Test";
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static int TARGET_FRAME_RATE = 60;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private static JFrame frame;
	private boolean running = false;
	private Thread thread;

	private static Camera camera;
	private static Keyboard key;
	private static Mouse mouse;
	
	private ArrayList<String> wordList = new ArrayList<String>();
	private String dictionaryPath = "dictionary.txt";
	
	private ArrayList<WordObject> activeWords = new ArrayList<WordObject>();
	private Delay addDelay = new Delay(700, true);
	
	private static String typing = "";
	private Delay backspaceDelay = new Delay(75, true);
	private Delay enterDelay = new Delay(75, true);
	
	//private Font font = Font.decode("System Bold");
	private final int WORD_OBJECT_FONT_SIZE = 18;
	private final int STAT_FONT_SIZE = 14;
	private final int LOAD_FONT_SIZE = 16;
	private Font wordObjectFont = new Font("System Bold", Font.TRUETYPE_FONT, WORD_OBJECT_FONT_SIZE);
	private Font statFont = new Font("System Bold", Font.TRUETYPE_FONT, STAT_FONT_SIZE);
	private Font loadFont = new Font("Anonymous Pro", Font.TRUETYPE_FONT, LOAD_FONT_SIZE);
	
	
	private ArrayList<WordObject> removeWordQueue = new ArrayList<WordObject>();
	
	private Delay activeWordCountDelay = new Delay(100, true);
	private int maxWordsOnScreen = 20;
	private Delay wordSpeedDelay = new Delay(100, true);
	
	// Statistics
	private int wordsCompleted = 0;
	private static int charsTyped = 0;
	private int failedWords = 0;
	//private int misspelledWords = 0;
	//private int passedWords = 0;
	//private int timeSpent = 0;
	
	public Game() {
		frame = new JFrame();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
		
		key = new Keyboard();
		mouse = new Mouse();
		
		camera = null;
		
		// FIND Config File; read custom dictionary path; load;
		//File config = new File("config.txt"); // Should be local to the JAR // Dictionary should be local to the jar
		//
		
		parseFile(dictionaryPath);
		
	}
	
	String curChar = "/";
	String chars[] = {"/", "-", "\\"};
	int charIndex = 0;
	Delay loadDelay = new Delay(250, true);
	public void loadingThing() {
		if(loadDelay.isOver()) {
			charIndex += 1;
			loadDelay.start();
		}
		if(charIndex >= chars.length) {
			charIndex = 0;
		}
		curChar = chars[charIndex];
	}
	
	private void update() {
		Time.update();
		loadingThing();
		// Add new words ever time the addDelay is over if and only if the activeWords is less than or equal to 20
		if(addDelay.isOver() && activeWords.size() < maxWordsOnScreen) {
			String randWord = wordList.get(MathUtil.randInt(0, wordList.size()));
			WordObject newWord = new WordObject(randWord.trim());
			activeWords.add(newWord);
			addDelay.start();
		}
		
		keyBindings();
		
		// Checking the typed word for match
		for(WordObject w : activeWords) {
			w.update();
			// Checks if word is off screen
			if(w.getPosition().getX() > Game.WIDTH + 50) {
				removeWordQueue.add(w);
				System.out.println("removed word");
				failedWords += 1;
			}
		}
		
		// Replace undesired characters
		replaceCharacters();
		if(removeWordQueue.size() > 0) {
			activeWords.removeAll(removeWordQueue);
			removeWordQueue.clear();
		}
		if(camera != null) {
			//camera.update(player);
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.BLACK);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		if(camera != null) {
			g2d.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
		}
		
		////////////// Scene Rendering (ENTITIES AND TILES) \\\\\\\\\\\\\\
		g.setFont(wordObjectFont);
		g.setColor(Color.WHITE);
		for(WordObject w : activeWords) {
			w.render(g);
		}
		////////////// Scene Rendering (ENTITIES AND TILES) \\\\\\\\\\\\\\
		if(camera != null) {
			g2d.translate(camera.getPosition().getX(), camera.getPosition().getY());
		}
		
		////////////// GUI BELOW \\\\\\\\\\\\\\
		g.drawString(typing.trim(), 50, 50);
		//System.out.println(typing);

		g.setFont(statFont);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Words Completed: " + wordsCompleted, WIDTH / 8 * 7, 30);
		g.drawString("Characters Typed: " + charsTyped, WIDTH / 8 * 7, 50);
		g.drawString("Failed Words: " + failedWords, WIDTH / 8 * 7, 70);
		
		g.setFont(loadFont);
		g.drawString(curChar, WIDTH/2, HEIGHT /720*710);
		
		g.setFont(wordObjectFont);
		g.drawString("Word Speed: " + WordObject.speed, 25, HEIGHT/720*710);
		g.drawString("Max Words: " + maxWordsOnScreen, WIDTH-140, HEIGHT/720*710);
		////////////// GUI ABOVE \\\\\\\\\\\\\\

		g.dispose();
		bs.show();
	}
	
	// This is to replace certain keys
	private void replaceCharacters() {
		//typing = typing.replace("=", "");
		//typing = typing.replace("?", "");
		//typing = typing.replace("-", "");
		typing = typing.replace("", "");
		//typing = typing.replace("\\", "");
		//typing = typing.replace("/", "");
		//typing = typing.replace(".", "");
		//typing = typing.replace(",", "");
		//typing = typing.replace(";", "");
		//typing = typing.replace(":", "");
		//typing = typing.replace("]", "");
		//typing = typing.replace("[", "");
	}
	
	private void keyBindings() {
		if(key.isPressed(KeyEvent.VK_EQUALS)) {
			if(wordSpeedDelay.isOver()) {
				WordObject.speed += 0.5f; // This causes the speed of all words to change
				//wordSpeed += 0.5f;
			}
			wordSpeedDelay.start();
		}
		
		if(key.isPressed(KeyEvent.VK_MINUS)) {
			if(wordSpeedDelay.isOver()) {
				WordObject.speed -= 0.5f; // This causes the speed of all words to change
				//wordSpeed -= 0.5f;
			}
			if(WordObject.speed < 0) {
				WordObject.speed = 0.0f; // This causes the speed of all words to change
				//wordSpeed = 0;
			}
			wordSpeedDelay.start();
		}
		
		if(key.isPressed(KeyEvent.VK_PAGE_UP)) {
			if(activeWordCountDelay.isOver()) {
				maxWordsOnScreen += 1;
			}
			activeWordCountDelay.start();
		}
		
		if(key.isPressed(KeyEvent.VK_PAGE_DOWN)) {
			if(activeWordCountDelay.isOver()) {
				maxWordsOnScreen -= 1;
			}
			if(maxWordsOnScreen < 0) {
				maxWordsOnScreen = 0;
			}
			activeWordCountDelay.start();
			
		}
		
		boolean rightWordSpelling = false;
		if (key.isPressed(KeyEvent.VK_ENTER)) {
			if(enterDelay.isOver()) {
			for(WordObject w : activeWords) {
				//System.out.println("Typed: " + typing.trim());
				//System.out.println("Word: " + w.getWord().trim());
				// Check if the current typed word is equal to a activeWord WordObject
				if (typing.trim().equals(w.getWord().trim())) {
					System.out.println("Removing: " + w.getWord());
					rightWordSpelling = true;
					removeWordQueue.add(w);
					wordsCompleted += 1;
					typing = "";
					break;
				} else { 
					rightWordSpelling = false;
				}
			}
			if(!rightWordSpelling) {
				failedWords += 1;
				//rightWordSpelling = false;
			}
			// Reset the currently typed word whenever "ENTER" is pressed
			typing = "";
			} 
			enterDelay.start();
		}
		
		
		
		// Fixing the backspace so that it deletes 1 char everytime
		if(key.isPressed(KeyEvent.VK_BACK_SPACE)) {
			if(backspaceDelay.isOver() && typing.trim().length() != 0) {
				//System.out.println("backspace");
				//String temp = typing.trim();
				//temp = temp.substring(0, temp.length()-1);
				typing = typing.trim().substring(0, typing.trim().length()-1);
				//typing = temp;
			} 
			backspaceDelay.start();
		}
	}
	
	private void parseFile(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	wordList.add(line.trim());
		    }
		    
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args ) {
		Game game = new Game();
		
		game.addKeyListener(key);
		game.addMouseListener(mouse);
		game.addMouseMotionListener(mouse);
		game.addKeyListener(keyListener);
		frame.addKeyListener(keyListener);
		frame.addKeyListener(key);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		
		frame.add(game);
		frame.setTitle(TITLE);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		/*game.frame.add(game);
		game.frame.setTitle(TITLE);
		game.frame.pack();
		game.frame.setResizable(false);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);*/
		
		game.start();
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.setPriority(Thread.NORM_PRIORITY);
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	long lastTime = System.nanoTime();
	long timer = System.currentTimeMillis();
	final double ns = 1000000000.0 / TARGET_FRAME_RATE;
	double delta = 0;
	int frames = 0;
	int updates = 0;
	public void run() {
		Time.init();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS::" + frames + " UPS::" + updates);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	static KeyListener keyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_ALT) {
			} else {
				/*String temp;
				if(e.getKeyCode() == KeyEvent.VK_QUOTE) {
					System.out.println("QUOTATION");
					temp = typing + "\'"; 
				} else {
					temp = typing + e.getKeyChar();
					typing = temp;
				}*/
				typing = typing + e.getKeyChar();
				charsTyped += 1;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	};

}