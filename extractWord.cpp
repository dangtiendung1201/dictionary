#include <bits/stdc++.h>

using namespace std;

int cnt;
bool hasMeaning;
bool isValid;
string s;
string word;
string meaning;

vector<pair<string, string>> dictionary;

bool checkValid(string s)
{
	if (s.size() == 0)
		return false;

	for (int i = 0; i < s.size(); i++)
	{
		if (!(s[i] >= 'a' && s[i] <= 'z'))
		{
			return false;
		}
	}

	return true;
}

int main()
{
	freopen("input.txt", "r", stdin);
	freopen("sortedWord.txt", "w", stdout);

	while (getline(cin, s))
	{
		if (s[0] == '@')
		{
			hasMeaning = false;

			for (int i = 0; i < s.size(); i++)
			{
				if (s[i] == '/')
				{
					word = s.substr(1, i - 2);
					// cout << tmp << endl;
					break;
				}
				else if (i == s.size() - 1)
				{
					word = s.substr(1, i);
					// cout << tmp << endl;
					break;
				}
			}

			isValid = checkValid(word);
		}
		else if (s[0] == '-' && s[1] == ' ' && !hasMeaning)
		{
			hasMeaning = true;

			meaning = s.substr(2, s.size() - 1);
			// cout << word << endl;
			if (isValid)
				dictionary.push_back({word, meaning});

			word = "";
			meaning = "";
		}
	}

	sort(dictionary.begin(), dictionary.end());

	for (pair<string, string> s : dictionary)
	{
		cout << s.first << '\t' << s.second << endl;
		// cout << s.first << endl;
	}

	cout << dictionary.size() << endl;
}