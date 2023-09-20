import scrapy


class OxfordSpider(scrapy.Spider):
    name = "oxford"
    allowed_domains = ["www.oxfordlearnersdictionaries.com"]
    start_urls = ["https://www.oxfordlearnersdictionaries.com/definition/english"]

    def parse(self, response):
        pass
