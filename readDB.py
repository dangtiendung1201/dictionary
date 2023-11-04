#  read dict.db file
import sqlite3

conn = sqlite3.connect('dict.db')
c = conn.cursor()
c.execute("SELECT * FROM av")
result = c.fetchall()
print(result)
# print(len(result))
