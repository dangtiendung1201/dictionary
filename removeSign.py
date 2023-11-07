from unidecode import unidecode

# read from file "sortedWords.txt"
with open("sortedWord.txt", "r") as f:
    lines = f.readlines()
    # remove accents from words
    lines = [unidecode(line) for line in lines]
    # write to file "sortedWordsWithoutSign.txt"
    with open("sortedWordWithoutSign.txt", "w") as f1:
        f1.writelines(lines)