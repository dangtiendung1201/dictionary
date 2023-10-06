import scrapy


class ExampleSpider(scrapy.Spider):
    name = "example"
    allowed_domains = ["sentence.yourdictionary.com"]
    start_urls = ["https://sentence.yourdictionary.com"]

    def parse(self, response):
        pass
