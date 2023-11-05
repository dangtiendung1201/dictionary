#  read dict.db file
import sqlite3

result = []

conn = sqlite3.connect('dict.db')
c = conn.cursor()
# read WorldList.txt and query dict.db and then write to official.txt
f = open('WordList.txt', 'r')
g = open('official.txt', 'w')
for line in f:
    word = line.strip()
    pronounce = c.execute('SELECT pronounce FROM av WHERE word = ?', (word,)).fetchone()
    meaning = c.execute('SELECT description FROM av WHERE word = ?', (word,)).fetchone()

    if pronounce is None:
        pronounce = ['Not available']

    if meaning is None:
        meaning = ['Not available']

    g.write(word + '\t' + pronounce[0] + '\t' + meaning[0] + '\n')
f.close()

# f = open('official.txt', 'w')
# for i in range(len(result)):
#     f.write(str(result[i]) + '\n')
# f.close()
# print(len(result))
