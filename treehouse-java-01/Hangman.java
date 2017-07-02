public class Hangman {

    public static void main(String[] args) {
      if (args.length == 0) {
        System.out.println("Please enter a word as an argument!");
        System.exit(0);
      }

      Game game = new Game(args[0].toLowerCase());
      Prompter prompter = new Prompter(game);
      prompter.play();
    }
}
