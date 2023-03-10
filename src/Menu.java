import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {
    public static Trie trie;
    public static String fileName = "dictionary.txt";
    // public static LinkedList<String> permutationList = new LinkedList<String>();

    public static void main(String[] args) {
        int choice;
        Scanner input = new Scanner(System.in);
        displayMenu();
        choice = integerReader();
        choicePicker(choice);

    }

    // to display the menu
    public static void displayMenu() {
        System.out.println("TRIE PROJECT: Enter your choice?\n" +
                "       1) Create an empty trie\n" +
                "       2) Create a trie with initial letters\n" +
                "       3) Insert a word\n" +
                "       4) Delete a word\n" +
                "       5) List all words that begin with a prefix\n" +
                "       6) Size of the trie\n" +
                "       7) End\n");

    }

    // to read integer input
    public static int integerReader() {
        Scanner input = new Scanner(System.in);
        int num = 0;
        String stInput;
        boolean valid = false;

        while (valid == false) {
            System.out.print("Enter choice: ");
            stInput = input.nextLine();
            try {
                num = Integer.parseInt(stInput);
                if (num >= 1 && num <= 7) {
                    valid = true;
                } else {
                    System.out.println("Error: input must be a number between 1 and 7");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: input must be a number between 1 and 7");
            }
        }
        return num;
    }

    // to read string input
    public static String stringReader(String promText) {
        Scanner input = new Scanner(System.in);
        String stInput = "";
        boolean valid = false;

        while (valid == false) {
            System.out.print(promText + ": ");
            stInput = input.nextLine();
            try {
                stInput = stInput.toUpperCase();
                valid = true;

            } catch (Exception e) {
                System.out.println("Error: input must be letter");
            }
        }
        return stInput;

    }

    // to create a list of letters that are not in the wanted word
    public static LinkedList<Character> notWantedLetters(String wantedWord) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        LinkedList<Character> alphabetList = new LinkedList<Character>();

        for (Character c : alphabet) {
            alphabetList.add(c);
        }

        for (int i = 0; i < wantedWord.length(); i++) {
            alphabetList.remove(((Character) wantedWord.charAt(i)));
        }
        return alphabetList;
    }

    // for option 1
    public static void createEmptyTrie() {
        trie = new Trie();
        System.out.println("Empty trie has been created.");
        System.out.println();
    }

    // for option 2
    public static void createTrieWithWord() {
        trie = new Trie();

        try {
            String word = stringReader("Enter a word to make a trie");
            FileInputStream file = new FileInputStream(fileName);
            Scanner reader = new Scanner(file);
            System.out.println("loading from the dictionary...");

            LinkedList<String> decWordsList = new LinkedList<>();
            while (reader.hasNext()) {
                decWordsList.add(reader.nextLine());
            }

            char[] charArray = word.toCharArray();
            LinkedList<Character> alphabetList = notWantedLetters(word);

            for (String s : decWordsList) {
                int counter = 0;
                boolean valid = true;
                while (valid) {
                    for (Character character : alphabetList) {
                        if (!valid) {
                            break;
                        }
                        if (s.indexOf(character) != -1) {
                            counter++;
                            valid = false;
                            break;
                        }
                    }
                    // check for reputation
                    for (Character c : charArray) {
                        if (s.indexOf(c) != -1) {
                            int indexOfCharacter = s.indexOf(c);
                            String subString = s.substring(indexOfCharacter + 1);
                            if (subString.indexOf(c) != -1) {
                                counter++;
                                valid = false;
                            } else {
                                valid = false;
                            }
                        }
                    }
                }
                if (counter < 1) {
                    trie.insert(s);
                }

            }

            System.out.println("trie has been made using the word " + word);
            System.out.println();
        } catch (FileNotFoundException e8) {
            System.out.println("Error: file not found.");
        }
    }

    // for option 3
    public static void insertWord() {
        boolean valid = false;
        int num = 0;
        while (valid == false) {
            String word = stringReader("Enter a word to insert");
            try {
                // to check if the input is a word or not
                num = Integer.parseInt(word);
                System.out.println("Error: input must be a word.");
            } catch (NumberFormatException e) {
                try {
                    trie.insert(word.toUpperCase());
                    System.out.println("The word " + "'" + word.toUpperCase() + "'" + " has been added.");
                    System.out.println();
                    valid = true;
                } catch (NullPointerException e2) {
                    System.out.println("Error: trie has not been made yet.");
                    System.out.println();
                    valid = true;
                }
            }
        }

    }

    // for option 4
    public static void deleteWord() {
        boolean valid2 = false;
        int num2 = 0;
        while (valid2 == false) {
            String word2 = stringReader("Enter a word to delete");
            try {
                // to check if the input is a word or not
                num2 = Integer.parseInt(word2);
                System.out.println("Error: input must be a word.");
            } catch (NumberFormatException e) {
                try {
                    if (trie.contains(word2.toUpperCase())) {
                        trie.delete(word2.toUpperCase());
                        System.out.println("The word " + "'" + word2.toUpperCase() + "'" + " has been deleted.");
                        System.out.println();
                        valid2 = true;
                    } else {
                        System.out.println("Error: the trie dose not contain the word " + word2.toUpperCase());
                        System.out.println();
                        valid2 = true;
                    }

                } catch (NullPointerException e2) {
                    System.out.println("Error: trie has not been made yet.");
                    System.out.println();
                    valid2 = true;
                }
            }
        }
    }

    // for option 5
    public static void allPrefix() {
        boolean valid3 = false;
        int num3 = 0;
        while (valid3 == false) {
            String word3 = stringReader("Enter a word to search for");
            try {
                num3 = Integer.parseInt(word3);
                System.out.println("Error: input must be a word.");
            } catch (NumberFormatException e3) {
                try {
                    String[] array = trie.allWordsPrefix(word3);
                    System.out.print("Found the following words: ");
                    for (int i = 0; i < array.length; i++) {
                        System.out.print(" " + array[i] + ",");
                    }

                    System.out.println();
                    System.out.println();
                    valid3 = true;
                } catch (NullPointerException e4) {
                    System.out.println("Trie dose not contain any word with the prefix " + "'" + word3 + "'"
                            + " or the trie has not ben made yet");
                    System.out.println();
                    valid3 = true;
                }
            }
        }
    }

    // for option 6
    public static void sizeOfTrie() {
        try {
            System.out.println("The size of the trie is " + trie.size() + " node");
            System.out.println();
        } catch (NullPointerException e6) {
            System.out.println("Error: trie has not been made yet");
            System.out.println();
        }
    }

    // for option 7
    public static void end() {
        System.out.println("END");
        System.exit(0);
    }

    // take the user input from the menu and use it accordingly
    public static void choicePicker(int choice) {
        switch (choice) {
            case 1:
                createEmptyTrie();
                break;

            case 2:
                createTrieWithWord();
                break;

            case 3:
                insertWord();
                break;

            case 4:
                deleteWord();
                break;

            case 5:
                allPrefix();
                break;

            case 6:
                sizeOfTrie();
                break;

            case 7:
                end();
                break;
        }

        while (choice != 7) {
            displayMenu();
            choice = integerReader();
            choicePicker(choice);
        }
    }
}
