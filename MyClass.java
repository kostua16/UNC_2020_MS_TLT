// Дана переменная text с текстом.
//
// Задача 1.
// Вывести на экран только правую половину текста переменной.
//
// Задача 2.
// Вывести на экран все символы из переменной в обратном порядке,
// через запятую с пробелом ( reverse() приветствуется, но как решение не засчитывается )
//
// Задача 3.
// Вывести на экран все символы из переменной, отсортированные по возрастанию, через запятую с пробелом, без дубликатов.

package example;

public class MyClass {

    public static void printHalfVariable(char[] textArray) {
        for (int i = 0; i < textArray.length / 2; i++) {
            System.out.print(textArray[i]);
        }
    }

    public static void printReversVariable(char[] textArray) {
        System.out.println();
        for (int i = textArray.length - 1; i > 0; i--) {
            if (textArray[i] != ' ') { // убрал пробелы
                System.out.print(textArray[i] + ", ");
            }
        }
        System.out.println(textArray[0]);
    }

    public static char[] createUniqueArray(char[] textArray) {
        char symbol;
        char[] uniqueArray = new char[textArray.length];
        boolean isDuplicate;
        uniqueArray[0] = textArray[0];

        for (int i = 0, k = 1; i < textArray.length; i++) {
            isDuplicate = false;
            symbol = textArray[i];
            for (int j = 0; j < i + 1; j++) {
                if (symbol == uniqueArray[j]) {
                    isDuplicate = true;
                }
            }
            if (!isDuplicate && symbol != ' ') {
                uniqueArray[k] = symbol;
                k++;
            }
        }

        return uniqueArray;
    }

    public static void printSortedSymbols(char[] textArray) {
        textArray = createUniqueArray(textArray);
        char symbol;

        for (int i = 0; i < textArray.length - 1; i++) {
            for (int j = i + 1; j < textArray.length; j++) {
                if (textArray[i] > textArray[j]) {
                    symbol = textArray[i];
                    textArray[i] = textArray[j];
                    textArray[j] = symbol;
                }
            }

        }

        String sortedSymbols = "";
        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i] != '\0') {
                sortedSymbols += textArray[i] + ", ";
            }
        }
        System.out.println(sortedSymbols.substring(0, sortedSymbols.length() - 2));
    }

    public static void main(String[] args) {
        String text = "I love Java I love SQL";

        char[] textArray = text.toCharArray();

        printHalfVariable(textArray);

        printReversVariable(textArray);

        printSortedSymbols(textArray);
    }
}

