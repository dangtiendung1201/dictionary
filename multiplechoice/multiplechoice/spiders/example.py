import scrapy


class ExampleSpider(scrapy.Spider):
    words = []

    with open('wordList.txt', 'r') as f:
        for line in f:
            words.append(line.strip())

    name = "example"
    allowed_domains = ["sentence.yourdictionary.com"]
    start_urls = ["https://sentence.yourdictionary.com/" + word for word in words]

    def parse(self, response):
        # englishWord
        englishWord = response.url.split("/")[-1]
        # example
        exampleTemp = response.xpath('//div[@class="sentence-item"]/descendant-or-self::*/text()').getall()
        example = ""

        for i in range(len(exampleTemp)):
            if exampleTemp[i] == " ":
                break
            else:
                example += exampleTemp[i]

        yield {
            englishWord: {
                'example': example
            }
        }


