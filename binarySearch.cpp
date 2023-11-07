#include <bits/stdc++.h>

using namespace std;

const int MaxN = 1000000;

string s;
pair<string, string> dict[MaxN];
int cnt;

pair<string, string> binarySearch(string x)
{
    int l = 1, r = cnt;

    while (l <= r)
    {
        int mid = (l + r) / 2;

        if (dict[mid].first == x)
            return dict[mid];
        else if (dict[mid].first < x)
            l = mid + 1;
        else
            r = mid - 1;
    }

    return make_pair("Imitation", "Game");
}

int main()
{
    freopen("sortedWordWithoutSign.txt", "r", stdin);

    while (getline(cin, s))
    {
        string word = "";
        string meaning = "";
        int pos = -1;

        // Get word: from 0 to '\t' - 1 (aka Tab)
        // Get meaning: from '\t' + 1 to end of string
        for (int i = 0; i < s.size(); i++)
        {
            if (s[i] == '\t')
            {
                pos = i;
                break;
            }
        }

        word = s.substr(0, pos);
        meaning = s.substr(pos + 1, s.size() - pos - 1);

        dict[++cnt] = make_pair(word, meaning);
    }

    pair<string, string> ans;

    ans = binarySearch("cunt");
    cout << ans.first << '\t' << ans.second << '\n';

    ans = binarySearch("a");
    cout << ans.first << '\t' << ans.second << '\n';

    ans = binarySearch("zone");
    cout << ans.first << '\t' << ans.second << '\n';

    ans = binarySearch("Alan Turing");
    cout << ans.first << '\t' << ans.second << '\n';
}