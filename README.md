<h1 align="center">Dictionary data crawler using Python and Scrapy</h1>

**This branch is for crawler providing data for our dictionary app in Oriented Object Programming**

# Table of Contents
- [Requirements](#requirements)
- [Setup](#setup)

## Requirements

```
Python 3.11.5
Scrapy 2.11.0
```
## Setup

Using this code to install Scrapy libraries and crawl
```
pip install scrapy

cd cambridge
cd cambridge
cd spiders

scrapy crawl dict -o dataDictionary.json
```

All words that will be crawled stored in `wordList.txt` file

Crawled data will be stored in `dataDictionary.json` file

Due to the fact that the crawled data was not sorted by key, so we can sort it by

## Usage