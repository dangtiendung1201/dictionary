import json

result = []
with open('data.json') as f:
    data = json.load(f)
    # print all data
    # print(data)
    #  get data size
    for i in range(len(data)):
        # print(data[i])
        # print(data[0]['abroad'])
        #  print key of data[i] dict_key string only
        # dict_keys(['winter']) only winter
        englishWord = list(data[i].keys())[0]
        speech = data[i][englishWord]['speech']
        wordType = data[i][englishWord]['wordType']
        meaning = data[i][englishWord]['meaning']
        example = data[i][englishWord]['example']
        relatedWord = data[i][englishWord]['relatedWord']
        
        word = {
            "englishWord": englishWord,
            "speech": speech,
            "wordType": wordType,
            "meaning": meaning,
            "example": example,
            "relatedWord": relatedWord
        }

        result.append(word)
result.sort(key=lambda x: x['englishWord'])
f.close()

with open('data.txt', 'w') as g:
    for i in range(len(result)):
        if result[i]['speech'] is not None:
            result[i]['speech'] = str(result[i]['speech'])
            result[i]['speech'] = result[i]['speech'].replace('[', '')
            result[i]['speech'] = result[i]['speech'].replace(']', '')
            result[i]['speech'] = result[i]['speech'].replace("'", '')
        if result[i]['wordType'] is not None:
            result[i]['wordType'] = str(result[i]['wordType'])
            result[i]['wordType'] = result[i]['wordType'].replace('[', '')
            result[i]['wordType'] = result[i]['wordType'].replace(']', '')
            result[i]['wordType'] = result[i]['wordType'].replace("'", '')
        if result[i]['meaning'] is not None:
            result[i]['meaning'] = str(result[i]['meaning'])
            result[i]['meaning'] = result[i]['meaning'].replace('\', \'', ' | ')
            result[i]['meaning'] = result[i]['meaning'].replace('[\'', '')
            result[i]['meaning'] = result[i]['meaning'].replace('\']', '')
        if result[i]['example'] is not None:
            result[i]['example'] = str(result[i]['example'])
            result[i]['example'] = result[i]['example'].replace('\', \'', ' | ')
            result[i]['example'] = result[i]['example'].replace('[\'', '')
            result[i]['example'] = result[i]['example'].replace('\']', '')
        if result[i]['relatedWord'] is not None:
            result[i]['relatedWord'] = str(result[i]['relatedWord'])
            result[i]['relatedWord'] = result[i]['relatedWord'].replace('\', \'', ' | ')
            result[i]['relatedWord'] = result[i]['relatedWord'].replace('[\'', '')
            result[i]['relatedWord'] = result[i]['relatedWord'].replace('\']', '')
        
        g.write(result[i]['englishWord'] + '\t')
        # not null and size > 0
        if result[i]['speech'] is not None and len(result[i]['speech']) > 0 and result[i]['speech'] != '[]':
            g.write(result[i]['speech'] + '\t')
        else:
            g.write('None' + '\t')
        if result[i]['wordType'] is not None and len(result[i]['wordType']) > 0 and result[i]['wordType'] != '[]':
            g.write(result[i]['wordType'] + '\t')
        else:
            g.write('None' + '\t')
        if result[i]['meaning'] is not None and len(result[i]['meaning']) > 0 and result[i]['meaning'] != '[]':
            g.write(result[i]['meaning'] + '\t')
        else:
            g.write('None' + '\t')
        if result[i]['example'] is not None and len(result[i]['example']) > 0 and result[i]['example'] != '[]':
            g.write(result[i]['example'] + '\t')
        else:
            g.write('None' + '\t')
        if result[i]['relatedWord'] is not None and len(result[i]['relatedWord']) > 0 and result[i]['relatedWord'] != '[]':
            g.write(result[i]['relatedWord'] + '\n')
        else:
            g.write('None' + '\n')
        # g.write(result[i]['englishWord'] + '\t' + result[i]['speech'] + '\t' + result[i]['wordType'] + '\t' + result[i]['meaning'] + '\t' + result[i]['example'] + '\t' + result[i]['relatedWord'] + '\n')
g.close()