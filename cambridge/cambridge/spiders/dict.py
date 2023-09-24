import scrapy

# load words from file wordList.txt


class DictSpider(scrapy.Spider):
    words = []

    with open('wordList.txt', 'r') as f:
        for line in f:
            words.append(line.strip())

    name = "dict"
    allowed_domains = ['www.tratu.soha.vn']
    start_urls = ['http://tratu.soha.vn/dict/en_vn/' + word for word in words]

    def parse(self, response):
        # get the English word
        englishWord = response.xpath('//h1/script[@language="javascript"]/text()').get()
        # find the first index of '=' and then extract the string between '=' and ';'
        englishWord = englishWord[englishWord.find('=') + 2:englishWord.find(';') - 1]
        # first character to lơwer case
        englishWord = englishWord[:1].lower() + englishWord[1:]

        # array of definition that contains speech and meaning
        definition = []

        speech = response.xpath('//h5/span[@class="mw-headline"]/b/font/text()').get()

        wordType = response.xpath('//h3/span[@class="mw-headline"][1]/text()').getall()
        wordType = [x for x in wordType if x != "Từ đồng nghĩa" and x != "Từ trái nghĩa" and x != " Nguồn khác "]

        meaning = response.xpath('//h5/span[@class="mw-headline"]/text()').getall()
        meaning = [x for x in meaning if x != "adverb" and x != "noun" and x != "verb" and x != "adjective" and x != "phrasal verb" and x != "/" and x != " " and x != "/  "]

        exampleTemp = response.xpath('//dl/dd/dl/dd/descendant-or-self::*/text()').getall()
        example = ''.join( _ for _ in exampleTemp).strip()
        example = example.split('\n')

        relatedWord = response.xpath('//div[@id="content-5"]/dl/dd/a/text()').getall()

        definition.append(speech)
        definition.append(wordType)
        definition.append(meaning)
        definition.append(example)
        definition.append(relatedWord)

        # word = response.xpath('//h5/span[@class="mw-headline"]/text()').getall()

        yield {
            englishWord: definition
        }
