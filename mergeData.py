# a map to save word from soha
# a map name "soha"
soha = {}
db = {}
game = {}

out = open('merged.txt', 'w')

f = open('official.txt', 'r')
for line in f:
    eachLine = line.strip().split('\t')
    word = {
        "word": eachLine[0],
        "pronounce": eachLine[1],
        "meaning": eachLine[2]
    }
    db[eachLine[0]] = word
f.close()

f = open('soha.txt', 'r')
for line in f:
    eachLine = line.strip().split('\t')
    word = {
        "word": eachLine[0],
        "example": eachLine[4],
        "related": eachLine[5]
    }
    soha[eachLine[0]] = word
f.close()

f = open('MultipleChoiceData.txt', 'r')
for line in f:
    eachLine = line.strip().split('\t')
    word = {
        "word": eachLine[0],
        "example": eachLine[1]
    }
    game[eachLine[0]] = word
f.close()

for i in db:
    result = i + '\t'
    if db[i]['pronounce'] != '':
        result += db[i]['pronounce'] + '\t'
    else:
        result += 'N/A\t'
    # if have ":" in meaning
    if ':' in db[i]['meaning']:
        # substring db[i]['meaning'] from 0 to before ":" and assign to wordType
        wordType = db[i]['meaning'][:db[i]['meaning'].index(':')]
        # substring db[i]['meaning'] from after ":" to the end and assign to meaning
        meaning = db[i]['meaning'][db[i]['meaning'].index(':') + 1:]
    else:
        wordType = 'danh từ'
        meaning = db[i]['meaning']

    result += wordType + '\t'
    result += meaning + '\t'

    if i in soha and soha[i]['example'] != 'None':
        result += soha[i]['example'] + '\t'
    else:
        if i in game:
            result += game[i]['example'] + '\t'
        else:
            result += 'N/A\t'
    if i in soha and soha[i]['related'] != 'None':
        result += soha[i]['related']
    else:
        result += 'N/A'
    out.write(result + '\n')
out.close()