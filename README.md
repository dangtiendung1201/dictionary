<h1 align="center">Dictionary data crawler using Python and Scrapy</h1>

**This branch is for crawler providing data for our dictionary app in Oriented Object Programming**

# Table of Contents
- [Requirements](#requirements)
- [Setup](#setup)
- [Usage](#usage)

## Requirements

```
Python 3.11.5
Scrapy 2.11.0
```
## Setup
```
pip install scrapy
```
## Usage
There is 2 crawler in this branch
### Crawl dictionary data
I took data from http://tratu.soha.vn/
```
cd cambridge
cd cambridge
cd spiders

scrapy crawl dict -o dataDictionary.json
```

All words that will be crawled stored in `wordList.txt` file

Crawled data will be stored in `dataDictionary.json` file

Because the crawler works like a pipeline, after crawling word, we sort them alphabetically.

### Crawl Multiple choice game data
I took data from https://sentence.yourdictionary.com
```
cd multiplechoice
cd multiplechoice
cd spiders

scrapy crawl example -o dataDictionary.json
```
All words that will be crawled stored in `wordList.txt` file

Crawled data will be stored in `dataDictionary.json` file

Because the crawler works like a pipeline, after crawling word, we sort them alphabetically.
